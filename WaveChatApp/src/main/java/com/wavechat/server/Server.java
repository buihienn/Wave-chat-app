package com.wavechat.server;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class Server {
    private static JTextArea textArea;

    public static void main(String[] args) {
        int port = 1234;
        JFrame frame = createServerGUI(); // Tạo giao diện
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logMessage("Server is running on port " + port);
            ExecutorService pool = Executors.newFixedThreadPool(10);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logMessage("Client connected: " + clientSocket.getInetAddress());
                pool.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            logMessage("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Tạo giao diện server
    private static JFrame createServerGUI() {
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
        return frame;
    }

    // Ghi log vào JTextArea
    public static void logMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
        });
    }
}