package com.wavechat;
import com.wavechat.form.*;

public class WaveChatApp {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Tạo và hiển thị frame
            AdminSpamReport frame = new AdminSpamReport();
            frame.setVisible(true);
        });
    }
}
