package com.wavechat;
import com.wavechat.form.*;
import com.wavechat.socket.ClientSocketManager;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class WaveChatApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String serverAddress = "localhost";
                int port = 1234;

                ClientSocketManager clientSocket = new ClientSocketManager(serverAddress, port);
//                GlobalVariable.setUserID("U008");
//                UserHomeMain frame = new UserHomeMain(clientSocket);
                AuthenticationMain frame = new AuthenticationMain(clientSocket);

                frame.setVisible(true);
                System.out.println("Connected to server at " + serverAddress + ":" + port);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Could not connect to server. Please check the server status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}