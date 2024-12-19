package com.wavechat.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);
            ExecutorService pool = Executors.newFixedThreadPool(10);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                pool.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
