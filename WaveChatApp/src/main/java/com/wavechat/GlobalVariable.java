package com.wavechat;

public class GlobalVariable {
    private static String userID;

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        GlobalVariable.userID = userID;
    }
}
