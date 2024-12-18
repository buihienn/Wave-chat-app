package com.wavechat.bus;

import com.wavechat.component.ChatHeader;
import com.wavechat.dto.UserDTO;
import com.wavechat.form.UserHomeMain;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MemberGroupPanel extends javax.swing.JPanel {
    private UserDTO userDTO;
    private int groupID;
    private UserHomeMain userHomeMain;
    private ChatHeader chatHeader;
    
    public MemberGroupPanel(UserDTO user, int groupID, UserHomeMain userHomeMain, ChatHeader chatHeader) {
        initComponents();
         
        this.userDTO = user;
        this.groupID = groupID;
        this.userHomeMain = userHomeMain;
        this.chatHeader = chatHeader;
        
        fullNameLabel.setText(userDTO.getFullName());
        userNameLabel.setText(userDTO.getUserName());
    }
    
    public void addSetAdminButtonListener() {
        // Loại bỏ tất cả ActionListener (nếu có)
        for (ActionListener listener : setAdminButton.getActionListeners()) {
                   setAdminButton.removeActionListener(listener); // Loại bỏ tất cả listener cũ
        }
        setAdminButton.addActionListener(evt -> handleSetAdmin());
    }
        
    private void handleSetAdmin() {
        GroupChatBUS groupChatBUS = new GroupChatBUS();
        boolean isSuccess = groupChatBUS.changeAdmin(userDTO.getUserID(), groupID); 
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Admin successfully changed to: " + userDTO.getFullName(), "Success", JOptionPane.INFORMATION_MESSAGE);
            // Update conversation
            // Đóng dialog hiện tại
            java.awt.Window dialog = SwingUtilities.getWindowAncestor(this);
            if (dialog instanceof JDialog) {
                ((JDialog) dialog).dispose();
            }

            // Mở lại conversation từ UserHomeMain
            if (userHomeMain != null) {
                userHomeMain.showChatPanel();  
                ConversationBUS conversationBUS = new ConversationBUS();
                userHomeMain.userHomePanel.openConversationForGroupChat(
                    groupChatBUS.getGroupChatByID(groupID), 
                    conversationBUS.getConversationGroupByID(groupID)
                );
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to change admin. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addDeleteButtonListener() {
        // Loại bỏ tất cả ActionListener (nếu có)
        for (ActionListener listener : deleteButton.getActionListeners()) {
                   deleteButton.removeActionListener(listener); // Loại bỏ tất cả listener cũ
        }
        deleteButton.addActionListener(evt -> handleDelete());
    }
    
    private void handleDelete() {
        String memberID = this.userDTO.getUserID();

        // Xác nhận trước khi xóa
        int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to remove this member?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            GroupChatBUS groupChatBUS = new GroupChatBUS();
            boolean isDeleted = groupChatBUS.deleteMemberFromGroup(memberID, groupID);

            if (isDeleted) {
                JOptionPane.showMessageDialog(this, "Member removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Update UI
                java.awt.Window dialog = SwingUtilities.getWindowAncestor(this);
                if (dialog instanceof JDialog) {
                    ((JDialog) dialog).dispose();
                }
                
                chatHeader.openGroupMemberPanel(groupID);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove member. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userAvatar = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        fullNameLabel = new javax.swing.JLabel();
        buttonContainer = new javax.swing.JPanel();
        setAdminButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(0, 75));
        setPreferredSize(new java.awt.Dimension(690, 75));

        userAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        userAvatar.setText("Avatar1");
        userAvatar.setPreferredSize(new java.awt.Dimension(54, 54));

        userNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        userNameLabel.setText(" ");

        fullNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        fullNameLabel.setText(" ");

        setAdminButton.setBackground(new java.awt.Color(26, 41, 128));
        setAdminButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        setAdminButton.setForeground(new java.awt.Color(255, 255, 255));
        setAdminButton.setText("Set admin");
        setAdminButton.setMaximumSize(new java.awt.Dimension(144, 40));
        setAdminButton.setMinimumSize(new java.awt.Dimension(144, 40));
        setAdminButton.setPreferredSize(new java.awt.Dimension(144, 40));
        buttonContainer.add(setAdminButton);

        deleteButton.setBackground(new java.awt.Color(26, 41, 128));
        deleteButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete");
        deleteButton.setMaximumSize(new java.awt.Dimension(144, 40));
        deleteButton.setMinimumSize(new java.awt.Dimension(144, 40));
        deleteButton.setPreferredSize(new java.awt.Dimension(144, 40));
        buttonContainer.add(deleteButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                    .addComponent(fullNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 589, Short.MAX_VALUE)
                .addComponent(buttonContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fullNameLabel)
                        .addGap(10, 10, 10)
                        .addComponent(userNameLabel))
                    .addComponent(userAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonContainer;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel fullNameLabel;
    private javax.swing.JButton setAdminButton;
    private javax.swing.JLabel userAvatar;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables

}
