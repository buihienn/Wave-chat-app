package com.wavechat;
import com.wavechat.form.*;

public class WaveChatApp {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Tạo và hiển thị JFrame Login
            Login frame = new Login();
            frame.setVisible(true);
        });
    }
}
