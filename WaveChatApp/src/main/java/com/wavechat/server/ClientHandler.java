package com.wavechat.server;

import com.wavechat.bus.ChatMessageBUS;
import com.wavechat.bus.ConversationBUS;
import com.wavechat.bus.GroupChatBUS;
import com.wavechat.bus.UserBUS;
import com.wavechat.dto.ChatMessageDTO;
import com.wavechat.dto.ConversationDTO;
import com.wavechat.dto.UserDTO;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
                Server.logMessage("Received message: " + message); // Thay System.out.println
                if (message.startsWith("LOGIN_SUCCESS:")) {
                    handleLoginSuccess(message);
                } else if (message.startsWith("LOGOUT:")) {
                    handleLogout();
                } else if (message.startsWith("SEND_MESSAGE:")) {
                    handleSendMessage(message, writer);
                } else if (message.startsWith("SEND_GROUP_MESSAGE:")) {
                    handleSendGroupMessage(message, writer);
                } else if (message.startsWith("OPEN_CONVERSATION:")) {
                    handleOpenConversation(message);
                }
            }
        } catch (IOException e) {
            Server.logMessage("Error handling client: " + e.getMessage());
        } finally {
            try {
                if (!isLogoutHandled) {
                    handleLogout();
                }
                clientSocket.close();
            } catch (IOException e) {
                Server.logMessage("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void handleLoginSuccess(String message) {
        String userID = message.split(":")[1];
        this.curUserID = userID;
        UserBUS userBUS = new UserBUS();
        boolean isUpdate = userBUS.updateStatus(userID, true);
        if (isUpdate) {
            Server.logMessage("User " + userID + " is now online.");
            OnlineUserManager.addUser(userID, clientSocket);
        } else {
            Server.logMessage("Failed to update status for user: " + userID);
        }
    }

    private void handleLogout() {
        if (curUserID != null) {
            UserBUS userBUS = new UserBUS();
            boolean isUpdate = userBUS.updateStatus(curUserID, false);
            if (isUpdate) {
                Server.logMessage("User " + curUserID + " is now offline.");
                OnlineUserManager.removeUser(curUserID);
            } else {
                Server.logMessage("Failed to update status for user: " + curUserID);
            }
        }
        isLogoutHandled = true;
    }

    private void handleOpenConversation(String message) {
        String conversationID = message.split(":")[1].trim();
        OnlineUserManager.updateActiveConversation(curUserID, conversationID);
        Server.logMessage("User " + curUserID + " is now active in conversation: " + conversationID);
    }

    private void handleSendMessage(String message, BufferedWriter writer) {
        try {
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

            ConversationBUS conversationBUS = new ConversationBUS();
            ConversationDTO conversation = conversationBUS.checkConversationExists(receiverID, senderID);

            if (conversation == null) {
                if ("friend".equalsIgnoreCase(conversationType)) {
                    conversation = conversationBUS.addFriendConversation(receiverID, senderID);
                } else if ("stranger".equalsIgnoreCase(conversationType)) {
                    conversation = conversationBUS.addStrangerConversation(receiverID, senderID);
                }
            }

            ChatMessageBUS messageBUS = new ChatMessageBUS();
            ChatMessageDTO newChat = messageBUS.addMessage(senderID, receiverID, contentMessage, conversation.getConversationID());

            if (newChat != null) {
                updateUIForActiveUsers(conversation.getConversationID(), contentMessage, senderID);
                writer.write("MESSAGE_SENT\n");
            } else {
                writer.write("MESSAGE_FAILED\n");
            }
            writer.flush();
        } catch (IOException e) {
            Server.logMessage("Error handling SEND_MESSAGE: " + e.getMessage());
        }
    }

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

            for (UserDTO member : groupMembers) {
                String memberID = member.getUserID();
                if (memberID.equals(senderID)) continue;

                ConversationDTO conversation = conversationBUS.checkConversationGroupExists(memberID, groupID);
                if (conversation == null) {
                    conversation = conversationBUS.addGroupConversation(memberID, groupID);
                    if (conversation == null) continue;
                }

                ChatMessageDTO newChat = messageBUS.addMessageGroup(senderID, groupID, contentMessage, conversation.getConversationID());

                if (newChat != null) {
                    updateUIForActiveUsers(conversation.getConversationID(), contentMessage, senderID);
                }
            }

            writer.write("MESSAGE_SENT\n");
            writer.flush();
        } catch (Exception e) {
            Server.logMessage("Error handling SEND_GROUP_MESSAGE: " + e.getMessage());
        }
    }

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
                        Server.logMessage("Failed to update UI for user: " + userID);
                    }
                }
            }
        });
    }
}