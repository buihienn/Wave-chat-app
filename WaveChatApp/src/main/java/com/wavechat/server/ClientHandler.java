package com.wavechat.server;

import com.wavechat.bus.ChatMessageBUS;
import com.wavechat.bus.ConversationBUS;
import com.wavechat.bus.GroupChatBUS;
import com.wavechat.bus.UserBUS;
import com.wavechat.dto.ConversationDTO;
import com.wavechat.dto.UserDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private String curUserID; // Lưu userID hiện tại
    private boolean isLogoutHandled = false; // Kiểm tra logout đã được xử lí chưa

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received message: " + message);

                if (message.startsWith("LOGIN_SUCCESS:")) {
                    handleLoginSuccess(message);
                } 
                
                if (message.startsWith("LOGOUT:")) {
                    handleLogout();
                }
                
                if (message.startsWith("SEND_MESSAGE:")) {
                    handleSendMessage(message, writer);
                }
                
                if (message.startsWith("SEND_GROUP_MESSAGE:")) {
                    handleSendGroupMessage(message, writer);
                }
                
                if (message.startsWith("OPEN_CONVERSATION:")) {
                    handleOpenConversation(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Xử lý trạng thái offline khi kết nối bị đóng bất ngờ
                if (!isLogoutHandled) {
                    handleLogout();
                }
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    // Xử lí login
    private void handleLoginSuccess(String message) {
        String userID = message.split(":")[1];
        this.curUserID = userID;
        UserBUS userBUS = new UserBUS();
        boolean isUpdate = userBUS.updateStatus(userID, true);
        if (isUpdate) {
            System.out.println("User " + userID + " is now online.");
            OnlineUserManager.addUser(userID, clientSocket);
        } else {
            System.out.println("Failed to update status for user: " + userID);
        }
    }

    // Xử lí logout
    private void handleLogout() {
        if (curUserID != null) {
            UserBUS userBUS = new UserBUS();
            boolean isUpdate = userBUS.updateStatus(curUserID, false);
            if (isUpdate) {
                System.out.println("User " + curUserID + " is now offline.");
                OnlineUserManager.removeUser(curUserID);
            } else {
                System.out.println("Failed to update status for user: " + curUserID);
            }
        }
        isLogoutHandled = true;
    }
    
    // Xử lí mở 1 conversation
    private void handleOpenConversation(String message) {
        String conversationID = message.split(":")[1].trim();
        OnlineUserManager.updateActiveConversation(curUserID, conversationID);
        System.out.println("User " + curUserID + " is now active in conversation: " + conversationID);
    }
    
    // Xử lí gửi tin nhắn
    private void handleSendMessage(String message, BufferedWriter writer) {
        try {
            // Parse message từ client
            String[] parts = message.split(" TYPE:");
            if (parts.length != 2) {
                writer.write("MESSAGE_FAILED\n");
                writer.flush();
                return;
            }

            String[] contentParts = parts[0].split(" TO: ");
            if (contentParts.length != 2) {
                writer.write("MESSAGE_FAILED\n");
                writer.flush();
                return;
            }

            String contentMessage = contentParts[0].substring("SEND_MESSAGE:".length()).trim();
            String receiverID = contentParts[1].trim();
            String conversationType = parts[1].trim();
            String senderID = curUserID;

            // Kiểm tra conversation
            ConversationBUS conversationBUS = new ConversationBUS();
            ConversationDTO conversation = conversationBUS.checkConversationExists(receiverID, senderID);

            if (conversation == null) {
                if ("friend".equalsIgnoreCase(conversationType)) {
                    conversation = conversationBUS.addFriendConversation(receiverID, senderID);
                } else if ("stranger".equalsIgnoreCase(conversationType)) {
                    conversation = conversationBUS.addStrangerConversation(receiverID, senderID);
                }
            }

            // Gửi tin nhắn
            ChatMessageBUS messageBUS = new ChatMessageBUS();
            boolean success = messageBUS.addMessage(senderID, receiverID, contentMessage, conversation.getConversationID());

            if (success) {
                updateUIForActiveUsers(conversation.getConversationID(), contentMessage, senderID);
                writer.write("MESSAGE_SENT\n"); // Gửi phản hồi cho client gửi
            } else {
                writer.write("MESSAGE_FAILED\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                writer.write("MESSAGE_FAILED\n");
                writer.flush();
            } catch (IOException ignored) {}
        }
    }


    // Xử lí gửi tin nhắn group
    private void handleSendGroupMessage(String message, BufferedWriter writer) {
        try {
            String[] parts = message.split(" TO: ");
            if (parts.length != 2) {
                writer.write("MESSAGE_FAILED\n");
                writer.flush();
                return;
            }

            String contentMessage = parts[0].substring("SEND_GROUP_MESSAGE:".length()).trim();
            int groupID = Integer.parseInt(parts[1].trim());
            String senderID = curUserID;

            GroupChatBUS groupChatBUS = new GroupChatBUS();
            List<UserDTO> groupMembers = groupChatBUS.getMemberOfGroup(groupID);

            if (groupMembers == null || groupMembers.isEmpty()) {
                writer.write("MESSAGE_FAILED\n");
                writer.flush();
                return;
            }

            ConversationBUS conversationBUS = new ConversationBUS();
            ChatMessageBUS messageBUS = new ChatMessageBUS();

            boolean allMessagesSent = true; // Kiểm tra nếu tất cả tin nhắn gửi thành công

            for (UserDTO member : groupMembers) {
                String memberID = member.getUserID();
                if (memberID.equals(senderID)) continue;

                ConversationDTO conversation = conversationBUS.checkConversationGroupExists(memberID, groupID);
                if (conversation == null) {
                    conversation = conversationBUS.addGroupConversation(memberID, groupID);
                    if (conversation == null) {
                        allMessagesSent = false;
                        continue;
                    }
                }

                boolean success = messageBUS.addMessageGroup(senderID, groupID, contentMessage, conversation.getConversationID());
                if (!success) {
                    allMessagesSent = false;
                } else {
                    updateUIForActiveUsers(conversation.getConversationID(), contentMessage, senderID);
                }
            }

            // Gửi phản hồi duy nhất cho người gửi
            if (allMessagesSent) {
                writer.write("MESSAGE_SENT\n");
            } else {
                writer.write("MESSAGE_FAILED\n");
            }
            writer.flush();
        } catch (Exception e) {
            try {
                writer.write("MESSAGE_FAILED\n");
                writer.flush();
            } catch (IOException ignored) {}
        }
    }

    // Update UI cho user online
    private void updateUIForActiveUsers(String conversationID, String messageContent, String senderID) {
        OnlineUserManager.getOnlineUsers().forEach(userID -> {
            String activeConversationID = OnlineUserManager.getActiveConversation(userID);
            if (conversationID.equals(activeConversationID)) {
                Socket userSocket = OnlineUserManager.getClientSocket(userID);
                if (userSocket != null) {
                    try {
                        BufferedWriter userWriter = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));
                        userWriter.write("NEW_MESSAGE: " + messageContent + " FROM: " + senderID + " IN_CONVERSATION: " + conversationID + "\n");
                        userWriter.flush();
                    } catch (IOException e) {
                        System.out.println("Failed to update UI for user: " + userID);
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}