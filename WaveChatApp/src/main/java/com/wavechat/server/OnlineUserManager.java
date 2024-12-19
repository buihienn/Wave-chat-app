package com.wavechat.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineUserManager {
    private static final Set<String> onlineUsers = Collections.synchronizedSet(new HashSet<>());
    private static final Map<String, Socket> userSockets = new ConcurrentHashMap<>();
    private static final Map<String, String> activeConversations = new ConcurrentHashMap<>(); // Lưu conversationID mà người dùng online đang mở

    public static void updateActiveConversation(String userID, String conversationID) {
        activeConversations.put(userID, conversationID);
    }

    public static String getActiveConversation(String userID) {
        return activeConversations.get(userID);
    }

    public static void removeActiveConversation(String userID) {
        activeConversations.remove(userID);
    }

    public static void addUser(String userID, Socket socket) {
        onlineUsers.add(userID);
        userSockets.put(userID, socket);
    }

    public static void removeUser(String userID) {
        onlineUsers.remove(userID);
        userSockets.remove(userID);
    }

    public static boolean isUserOnline(String userID) {
        return onlineUsers.contains(userID);
    }

    public static Socket getClientSocket(String userID) {
        return userSockets.get(userID);
    }
    
    public static List<String> getOnlineUsers() {
        synchronized (onlineUsers) {
            return new ArrayList<>(onlineUsers); // Trả về bản sao để tránh sửa đổi trực tiếp
        }
    }
}