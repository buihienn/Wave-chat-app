package com.wavechat.component;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.ConversationBUS;
import com.wavechat.bus.FriendBUS;
import com.wavechat.bus.GroupChatBUS;
import com.wavechat.bus.MemberGroupPanel;
import com.wavechat.dto.FriendDTO;
import com.wavechat.dto.UserDTO;
import com.wavechat.form.UserHomeMain;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class ChatHeader extends javax.swing.JPanel {
    private javax.swing.JMenuItem changeGroupNameMenuItem;
    private javax.swing.JMenuItem addMemberMenuItem;
    private javax.swing.JMenuItem manageMemberMenuItem;
    
    public ChatHeader() {
        initComponents();
        groupMemberPanel.setLayout(new MigLayout("fillx"));
    }
    
    public void setInfor(String name, boolean isOnline) {
        nameLabel.setText(name);
        if (isOnline) {
            onlineLabel.setText("Online");
        }
        else { onlineLabel.setText("Offline"); }
    }
    
    public void setUpChatPopupMenu(int groupID) {
        chatPopupMenu.removeAll();
        // Tạo các menu item
        changeGroupNameMenuItem = new JMenuItem("Change group name");
        addMemberMenuItem = new JMenuItem("Add member");
        manageMemberMenuItem = new JMenuItem("Manage member");

        // Thêm các menu item vào popup menu
        chatPopupMenu.add(changeGroupNameMenuItem);
        chatPopupMenu.add(addMemberMenuItem);

        // Gán sự kiện cho các menu item
        changeGroupNameMenuItem.addActionListener(e -> openChangeGroupNameDialog(groupID));
        addMemberMenuItem.addActionListener(e -> openFriendListPanel(groupID));
        manageMemberMenuItem.addActionListener(e -> openGroupMemberPanel(groupID));

        GroupChatBUS groupChatBUS = new GroupChatBUS();
        boolean isAdmin = groupChatBUS.isAdmin(groupID);

        if (isAdmin) {
            chatPopupMenu.add(manageMemberMenuItem);
        }

    }
    
    private void openChangeGroupNameDialog(int groupID) {
        addConfirmButtonListener(groupID);
        
        UserHomeMain userHomeMain = (UserHomeMain) SwingUtilities.getWindowAncestor(this);
        changeGroupNameDialog.setLocationRelativeTo(userHomeMain);
        changeGroupNameDialog.setVisible(true);
    }
    
    // Gán sự kiện cho nút Create Group
    public void addConfirmButtonListener(int groupID) {
        // Loại bỏ tất cả ActionListener (nếu có)
        for (ActionListener listener : confirmButton.getActionListeners()) {
                   confirmButton.removeActionListener(listener); // Loại bỏ tất cả listener cũ
        }
        confirmButton.addActionListener(evt -> onCreateGroupConfirmButtonClicked(groupID));
    }
    
    private void onCreateGroupConfirmButtonClicked(int groupID) {                                           
        changeGroupNameDialog.dispose();
        String groupChatName = groupNameEditLabel.getText();
        groupNameEditLabel.setText("");
        handleChangeNameGroup(groupID, groupChatName);
    }  
    
    public void handleChangeNameGroup(int groupID, String newGroupName) {
        GroupChatBUS groupChatBUS = new GroupChatBUS();
    
        // Gọi phương thức trong BUS để thay đổi tên nhóm
        boolean isUpdated = groupChatBUS.changeGroupName(groupID, newGroupName);

        if (isUpdated) {
            // Nếu cập nhật thành công, thông báo cho người dùng và cập nhật UI nếu cần
            UserHomeMain userHomeMain = (UserHomeMain) SwingUtilities.getWindowAncestor(this);  // Lấy tham chiếu đến UserHomeMain
            userHomeMain.showChatPanel();  
            ConversationBUS conversationBUS = new ConversationBUS();
            userHomeMain.userHomePanel.openConversationForGroupChat(groupChatBUS.getGroupChatByID(groupID), conversationBUS.getConversationGroupByID(groupID));
        }
    }
    
    public void openGroupMemberPanel(int groupID) {
        GroupChatBUS groupChatBUS = new GroupChatBUS();
        List<UserDTO> members = groupChatBUS.getMemberOfGroup(groupID);
        UserHomeMain userHomeMain = (UserHomeMain) SwingUtilities.getWindowAncestor(this);
    
        groupMemberPanel.removeAll();
        for (UserDTO user : members) {
            if (!user.getUserID().equals(GlobalVariable.getUserID())) {
                MemberGroupPanel memberPanel = new MemberGroupPanel(user, groupID, userHomeMain, this);
                memberPanel.addSetAdminButtonListener();
                memberPanel.addDeleteButtonListener();
                groupMemberPanel.add(memberPanel, "wrap, w ::100%");
            }
        }
        groupMemberDialog.setTitle("Member list");
        
        groupMemberDialog.setLocationRelativeTo(userHomeMain);
        groupMemberDialog.setVisible(true);
    }
    
    private void openFriendListPanel(int groupID) {
        GroupChatBUS groupChatBUS = new GroupChatBUS();
        FriendBUS friendBUS = new FriendBUS();
        List<FriendDTO> friends = friendBUS.getFriends(GlobalVariable.getUserID());

        groupMemberPanel.removeAll();
        for (FriendDTO user : friends) {
            if (!groupChatBUS.isMemberOf(groupID, user.getUserID())) {
                FriendPanel memberPanel = new FriendPanel(user);
                groupMemberPanel.add(memberPanel, "wrap, w ::100%");
            }
        }
        groupMemberDialog.setTitle("Friend list");
        
        UserHomeMain userHomeMain = (UserHomeMain) SwingUtilities.getWindowAncestor(this);
        groupMemberDialog.setLocationRelativeTo(userHomeMain);
        groupMemberDialog.setVisible(true);
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        chatPopupMenu = new javax.swing.JPopupMenu();
        changeGroupNameDialog = new javax.swing.JDialog();
        popupEditProfile1 = new javax.swing.JPanel();
        groupNameLabel = new javax.swing.JLabel();
        groupNameEditLabel = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        groupMemberDialog = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        groupMemberPanel = new javax.swing.JPanel();
        leftContainer = new javax.swing.JPanel();
        userAvatar1 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        onlineLabel = new javax.swing.JLabel();
        rightContainer = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        moreButton = new javax.swing.JButton();

        changeGroupNameDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        changeGroupNameDialog.setTitle("Wave - Create group chat");
        changeGroupNameDialog.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        changeGroupNameDialog.setResizable(false);
        changeGroupNameDialog.setSize(new java.awt.Dimension(500, 150));

        popupEditProfile1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        popupEditProfile1.setMaximumSize(new java.awt.Dimension(500, 120));
        popupEditProfile1.setMinimumSize(new java.awt.Dimension(500, 120));
        popupEditProfile1.setPreferredSize(new java.awt.Dimension(500, 150));
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

        changeGroupNameDialog.getContentPane().add(popupEditProfile1, java.awt.BorderLayout.CENTER);

        groupMemberDialog.setMinimumSize(new java.awt.Dimension(726, 400));
        groupMemberDialog.setPreferredSize(new java.awt.Dimension(726, 400));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        groupMemberPanel.setMinimumSize(new java.awt.Dimension(726, 400));
        groupMemberPanel.setPreferredSize(new java.awt.Dimension(726, 400));

        javax.swing.GroupLayout groupMemberPanelLayout = new javax.swing.GroupLayout(groupMemberPanel);
        groupMemberPanel.setLayout(groupMemberPanelLayout);
        groupMemberPanelLayout.setHorizontalGroup(
            groupMemberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 726, Short.MAX_VALUE)
        );
        groupMemberPanelLayout.setVerticalGroup(
            groupMemberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(groupMemberPanel);

        groupMemberDialog.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        setMaximumSize(new java.awt.Dimension(2147483647, 73));
        setMinimumSize(new java.awt.Dimension(500, 73));
        setPreferredSize(new java.awt.Dimension(500, 73));
        setLayout(new java.awt.GridBagLayout());

        leftContainer.setPreferredSize(new java.awt.Dimension(200, 70));

        userAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        userAvatar1.setText("Avatar1");
        userAvatar1.setPreferredSize(new java.awt.Dimension(54, 54));

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        nameLabel.setText("User1");

        onlineLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        onlineLabel.setText("Online");

        javax.swing.GroupLayout leftContainerLayout = new javax.swing.GroupLayout(leftContainer);
        leftContainer.setLayout(leftContainerLayout);
        leftContainerLayout.setHorizontalGroup(
            leftContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(leftContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        leftContainerLayout.setVerticalGroup(
            leftContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftContainerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(onlineLabel))
            .addGroup(leftContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(leftContainer, new java.awt.GridBagConstraints());

        rightContainer.setMinimumSize(new java.awt.Dimension(0, 0));
        rightContainer.setPreferredSize(new java.awt.Dimension(294, 32));
        rightContainer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING, 5, 0));

        searchButton.setBackground(new java.awt.Color(26, 41, 128));
        searchButton.setFont(new java.awt.Font("Montserrat", 0, 10)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.setMaximumSize(new java.awt.Dimension(72, 32));
        searchButton.setMinimumSize(new java.awt.Dimension(72, 32));
        searchButton.setPreferredSize(new java.awt.Dimension(68, 32));
        rightContainer.add(searchButton);

        moreButton.setBackground(new java.awt.Color(26, 41, 128));
        moreButton.setFont(new java.awt.Font("Montserrat", 0, 10)); // NOI18N
        moreButton.setForeground(new java.awt.Color(255, 255, 255));
        moreButton.setText("More");
        moreButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        moreButton.setPreferredSize(new java.awt.Dimension(68, 32));
        moreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreButtonActionPerformed(evt);
            }
        });
        rightContainer.add(moreButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 301;
        gridBagConstraints.ipady = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(23, 0, 0, 6);
        add(rightContainer, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void moreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreButtonActionPerformed
        chatPopupMenu.show(moreButton, 0, moreButton.getHeight());
    }//GEN-LAST:event_moreButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        changeGroupNameDialog.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JDialog changeGroupNameDialog;
    private javax.swing.JPopupMenu chatPopupMenu;
    private javax.swing.JButton confirmButton;
    private javax.swing.JDialog groupMemberDialog;
    private javax.swing.JPanel groupMemberPanel;
    private javax.swing.JTextField groupNameEditLabel;
    private javax.swing.JLabel groupNameLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel leftContainer;
    private javax.swing.JButton moreButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel onlineLabel;
    private javax.swing.JPanel popupEditProfile1;
    private javax.swing.JPanel rightContainer;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel userAvatar1;
    // End of variables declaration//GEN-END:variables
}
