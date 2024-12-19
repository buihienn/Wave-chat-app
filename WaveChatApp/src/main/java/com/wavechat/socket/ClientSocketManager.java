package com.wavechat.socket;

import com.wavechat.component.ChatBody;
import java.io.*;
import java.net.*;
import javax.swing.SwingUtilities;

public class ClientSocketManager {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ClientSocketManager(String serverAddress, int port) throws IOException {
        this.socket = new Socket(serverAddress, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    // Gửi message đến server
    public void sendMessage(String message) throws IOException {
        writer.write(message + "\n");
        writer.flush();
    }

    // Nhận message từ server
    public String readMessage() throws IOException {
        return reader.readLine();
    }
    
    // Lắng nghe tin nhắn từ server trong luồng riêng
    public void listenForMessages(ChatBody chatBody) {
        new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    if (message.startsWith("NEW_MESSAGE:")) {
                        String[] parts = message.split(" FROM: ");
                        String contentMessage = parts[0].substring("NEW_MESSAGE:".length()).trim();
                        String senderID = parts[1].split(" IN_CONVERSATION: ")[0].trim();

                        SwingUtilities.invokeLater(() -> {
                            chatBody.updateNewUsername(senderID);
                            chatBody.updateNew(contentMessage);
                        });
                    } else if (message.startsWith("MESSAGE_SENT")) {
                        System.out.println("Message sent successfully.");
                    } else {
                        System.out.println("Unknown message type received: " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error in listening for messages: " + e.getMessage());
            }
        }).start();
    }

    // Đóng connection
    public void close() throws IOException {
        socket.close();
        reader.close();
        writer.close();
    }
}