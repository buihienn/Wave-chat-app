package com.wavechat;
import com.wavechat.form.*;

public class WaveChatApp {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Tạo và hiển thị frame
            UserProfile frame = new UserProfile();
            frame.setVisible(true);
        });
    }
}
