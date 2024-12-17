package com.wavechat.component;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.ConversationBUS;
import com.wavechat.bus.GroupChatBUS;
import com.wavechat.dto.ConversationDTO;
import com.wavechat.dto.GroupChatDTO;
import com.wavechat.dto.UserDTO;
import com.wavechat.form.UserHomeMain;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class UserSearchPanel extends javax.swing.JPanel {
    
    public UserSearchPanel(UserDTO userDTO) {
        initComponents();
        
        fullNameLabel.setText(userDTO.getFullName());
        userNameLabel.setText(userDTO.getUserName());
    }
    
    public void addAddFriendButton() {
        buttonContainer.add(addFriendButton);
        buttonContainer.revalidate();
        buttonContainer.repaint();
    }
    
    public void addCreateGroupButton() {
        buttonContainer.add(createGroupButton);
        buttonContainer.revalidate();
        buttonContainer.repaint();
    }
    
    // Gán sự kiện cho nút Chat
    public void addChatButtonListenerForFriend(String friendID) {
        chatButton.addActionListener(evt -> onChatWithFriendButtonClicked(friendID));
    }

    // Gán sự kiện cho nút Chat
    public void addChatButtonListenerForStranger(String strangerID) {
        chatButton.addActionListener(evt -> onChatWithStrangerButtonClicked(strangerID));
    }
    
    // Gán sự kiện cho nút Add Friend
    public void addAddFriendButtonListener(String friendID) {
        addFriendButton.addActionListener(evt -> onAddFriendButtonClicked(friendID));
    }

    // Gán sự kiện cho nút Create Group
    public void addCreateGroupButtonListener(String friendID) {
        createGroupButton.addActionListener(evt -> onCreateGroupButtonClicked(friendID));
    }
    
    private void onChatWithFriendButtonClicked(String friendID) {                                           
        System.out.println("Chat with friend " + friendID);
        handleChatForFriend(friendID);
    }  
    
    private void onChatWithStrangerButtonClicked(String strangerID) {                                           
        System.out.println("Chat with stranger " + strangerID);
        handleChatForStranger(strangerID);
    }  

    private void onAddFriendButtonClicked(String friendID) {                                                
        System.out.println("Add friend " + friendID);
    }                                               

    private void onCreateGroupButtonClicked(String friendID) {                                                  
        System.out.println("Create group with " + friendID);
        addConfirmButtonListener(friendID);
        createGroupDialog.setLocationRelativeTo(this);
        createGroupDialog.setVisible(true);
    }  
    
    private void handleChatForFriend(String friendID) {
        // Tạo conversation nếu chưa có
        ConversationBUS conversationBUS = new ConversationBUS();
        // Kiểm tra xem conversation đã tồn tại giữa currentUserID và friendID chưa
        ConversationDTO conversationDTO = conversationBUS.checkConversationExists(friendID);

        if (conversationDTO == null) {
            // Nếu không tồn tại conversation, tạo 1 cái mới
            conversationDTO = conversationBUS.addFriendConversation(friendID);
        }
        
        // Mở chat
        UserHomeMain userHomeMain = (UserHomeMain) SwingUtilities.getWindowAncestor(this);  // Lấy tham chiếu đến UserHomeMain
        userHomeMain.showChatPanel();  
        userHomeMain.userHomePanel.openConversation(conversationDTO);
    }
    
    private void handleChatForStranger(String strangerID) {
        // Tạo conversation nếu chưa có
        ConversationBUS conversationBUS = new ConversationBUS();
        // Kiểm tra xem conversation đã tồn tại giữa currentUserID và strangerID chưa
        ConversationDTO conversationDTO = conversationBUS.checkConversationExists(strangerID);

        if (conversationDTO == null) {
            // Nếu không tồn tại conversation, tạo 1 cái mới
            conversationDTO = conversationBUS.addStrangerConversation(strangerID);
        }
        
        // Mở chat
        UserHomeMain userHomeMain = (UserHomeMain) SwingUtilities.getWindowAncestor(this);  // Lấy tham chiếu đến UserHomeMain
        userHomeMain.showChatPanel();  
        userHomeMain.userHomePanel.openConversation(conversationDTO);
    }
    
    private void handleCreateGroup(String friendID, String groupChatName) {
        GroupChatBUS groupChatBUS = new GroupChatBUS();
        GroupChatDTO newGroupChat = groupChatBUS.createGroupChat(groupChatName);

        if (newGroupChat != null) {
            String currentUserID = GlobalVariable.getUserID();  
            groupChatBUS.addMember(currentUserID, newGroupChat.getGroupID(), true);  
            groupChatBUS.addMember(friendID, newGroupChat.getGroupID(), false);  

            ConversationBUS conversationBUS = new ConversationBUS();
            ConversationDTO conversationDTO = conversationBUS.addGroupConversation(newGroupChat.getGroupID());
            
            // Mở group chat
            UserHomeMain userHomeMain = (UserHomeMain) SwingUtilities.getWindowAncestor(this);  // Lấy tham chiếu đến UserHomeMain
            userHomeMain.showChatPanel();  
            userHomeMain.userHomePanel.openConversationForGroupChat(newGroupChat, conversationDTO);
        } else {
            JOptionPane.showMessageDialog(this, "Error creating group chat.");
        }
    }
    
    // Gán sự kiện cho nút Create Group
    public void addConfirmButtonListener(String friendID) {
        // Loại bỏ tất cả ActionListener (nếu có)
        for (ActionListener listener : confirmButton.getActionListeners()) {
                   confirmButton.removeActionListener(listener); // Loại bỏ tất cả listener cũ
        }
        confirmButton.addActionListener(evt -> onCreateGroupConfirmButtonClicked(friendID));
    }
    
    private void onCreateGroupConfirmButtonClicked(String friendID) {                                           
        createGroupDialog.dispose();
        String groupChatName = groupNameEditLabel.getText();
        handleCreateGroup(friendID, groupChatName);
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addFriendButton = new javax.swing.JButton();
        createGroupButton = new javax.swing.JButton();
        createGroupDialog = new javax.swing.JDialog();
        popupEditProfile1 = new javax.swing.JPanel();
        groupNameLabel = new javax.swing.JLabel();
        groupNameEditLabel = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        userAvatar = new javax.swing.JLabel();
        fullNameLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        buttonContainer = new javax.swing.JPanel();
        chatButton = new javax.swing.JButton();

        addFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        addFriendButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        addFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        addFriendButton.setText("Add friend");
        addFriendButton.setMaximumSize(new java.awt.Dimension(144, 40));
        addFriendButton.setMinimumSize(new java.awt.Dimension(144, 40));
        addFriendButton.setPreferredSize(new java.awt.Dimension(144, 40));

        createGroupButton.setBackground(new java.awt.Color(26, 41, 128));
        createGroupButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        createGroupButton.setForeground(new java.awt.Color(255, 255, 255));
        createGroupButton.setText("Create group");
        createGroupButton.setMaximumSize(new java.awt.Dimension(144, 40));
        createGroupButton.setMinimumSize(new java.awt.Dimension(144, 40));
        createGroupButton.setPreferredSize(new java.awt.Dimension(144, 40));

        createGroupDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        createGroupDialog.setTitle("Wave - Create group chat");
        createGroupDialog.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        createGroupDialog.setResizable(false);
        createGroupDialog.setSize(new java.awt.Dimension(500, 250));

        popupEditProfile1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        popupEditProfile1.setMaximumSize(new java.awt.Dimension(500, 120));
        popupEditProfile1.setMinimumSize(new java.awt.Dimension(500, 120));
        popupEditProfile1.setRequestFocusEnabled(false);

        groupNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        groupNameLabel.setText("Group name:");

        groupNameEditLabel.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        confirmButton.setBackground(new java.awt.Color(26, 41, 128));
        confirmButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("Confirm");
        confirmButton.setPreferredSize(new java.awt.Dimension(165, 40));

        cancelButton.setBackground(new java.awt.Color(26, 41, 128));
        cancelButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Cancel");
        cancelButton.setPreferredSize(new java.awt.Dimension(165, 40));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout popupEditProfile1Layout = new javax.swing.GroupLayout(popupEditProfile1);
        popupEditProfile1.setLayout(popupEditProfile1Layout);
        popupEditProfile1Layout.setHorizontalGroup(
            popupEditProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupEditProfile1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(popupEditProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(popupEditProfile1Layout.createSequentialGroup()
                        .addComponent(groupNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(popupEditProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupNameEditLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        popupEditProfile1Layout.setVerticalGroup(
            popupEditProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupEditProfile1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(popupEditProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupNameLabel)
                    .addComponent(groupNameEditLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(popupEditProfile1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        createGroupDialog.getContentPane().add(popupEditProfile1, java.awt.BorderLayout.CENTER);

        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        setPreferredSize(new java.awt.Dimension(1300, 75));

        userAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        userAvatar.setText("Avatar1");
        userAvatar.setPreferredSize(new java.awt.Dimension(54, 54));

        fullNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        fullNameLabel.setText(" ");

        userNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        userNameLabel.setText(" ");

        chatButton.setBackground(new java.awt.Color(26, 41, 128));
        chatButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        chatButton.setForeground(new java.awt.Color(255, 255, 255));
        chatButton.setText("Chat");
        chatButton.setMaximumSize(new java.awt.Dimension(144, 40));
        chatButton.setMinimumSize(new java.awt.Dimension(144, 40));
        chatButton.setPreferredSize(new java.awt.Dimension(144, 40));
        buttonContainer.add(chatButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fullNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(fullNameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(userNameLabel))
                        .addComponent(userAvatar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        createGroupDialog.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFriendButton;
    private javax.swing.JPanel buttonContainer;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton chatButton;
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton createGroupButton;
    private javax.swing.JDialog createGroupDialog;
    private javax.swing.JLabel fullNameLabel;
    private javax.swing.JTextField groupNameEditLabel;
    private javax.swing.JLabel groupNameLabel;
    private javax.swing.JPanel popupEditProfile1;
    private javax.swing.JLabel userAvatar;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
