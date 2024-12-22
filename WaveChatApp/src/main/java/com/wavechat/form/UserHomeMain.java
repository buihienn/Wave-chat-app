package com.wavechat.form;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.UserBUS;
import com.wavechat.contentPanel.UserFindFriendPanel;
import com.wavechat.contentPanel.UserFriendPanel;
import com.wavechat.contentPanel.UserHomePanel;
import com.wavechat.contentPanel.UserProfilePanel;
import com.wavechat.dto.UserDTO;
import com.wavechat.socket.ClientSocketManager;
import java.io.IOException;

public class UserHomeMain extends javax.swing.JFrame {
    private final ClientSocketManager clientSocket;
    
    public UserHomePanel userHomePanel = new UserHomePanel();    
    public UserFriendPanel userFriendPanel = new UserFriendPanel();   
    public UserFindFriendPanel userFindFriendPanel = new UserFindFriendPanel();
    public UserProfilePanel userProfilePanel = new UserProfilePanel();

    public UserHomeMain(ClientSocketManager clientSocket) {
        initComponents();
        this.setLocationRelativeTo(null);
        
        this.clientSocket = clientSocket;
        
        contentContainer.add(userHomePanel);          
        contentContainer.add(userFriendPanel);  
        contentContainer.add(userFindFriendPanel);           
        contentContainer.add(userProfilePanel);        
      
        userHomePanel.open(clientSocket);
                    
        userHomePanel.setVisible(true);        
        userFriendPanel.setVisible(false);
        userFindFriendPanel.setVisible(false);        
        userProfilePanel.setVisible(false);
        
        UserBUS userBUS = new UserBUS();
        UserDTO userDTO;
        
        navAdminButton.setVisible(false);
        try {
            userDTO = userBUS.getUserByID(GlobalVariable.getUserID());
            boolean isAdmin = userBUS.isAdmin(userDTO.getUserName());
            if (isAdmin) {
                navAdminButton.setVisible(true);
            } else {
                navAdminButton.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showChatPanel() {
        navChatButton.setBackground(new java.awt.Color(153, 255, 255));
        navChatButton.setForeground(new java.awt.Color(0, 0, 0));
        navChatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat_black.png")));

        navFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/friend_white.png")));


        navFindFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFindFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFindFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_white.png")));

        navProfileButton.setBackground(new java.awt.Color(26, 41, 128));
        navProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        navProfileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile_white.png")));

        logoutButton.setBackground(new java.awt.Color(26, 41, 128));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout_white.png")));

        userHomePanel.open(clientSocket);

        userHomePanel.setVisible(true);        
        userFriendPanel.setVisible(false);
        userFindFriendPanel.setVisible(false);        
        userProfilePanel.setVisible(false);
    }
    
    public void showFriendPanel() {
        navFriendButton.setBackground(new java.awt.Color(153, 255, 255));
        navFriendButton.setForeground(new java.awt.Color(0, 0, 0));
        navFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/friend_black.png")));

        navChatButton.setBackground(new java.awt.Color(26, 41, 128));
        navChatButton.setForeground(new java.awt.Color(255, 255, 255));
        navChatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat_white.png")));


        navFindFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFindFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFindFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_white.png")));

        navProfileButton.setBackground(new java.awt.Color(26, 41, 128));
        navProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        navProfileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile_white.png")));

        logoutButton.setBackground(new java.awt.Color(26, 41, 128));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout_white.png")));

        userHomePanel.setVisible(false);        
        userFriendPanel.setVisible(true);
        userFindFriendPanel.setVisible(false);        
        userProfilePanel.setVisible(false);
    }
    
    public void showFindFriendPanel() {
        navFindFriendButton.setBackground(new java.awt.Color(153, 255, 255));
        navFindFriendButton.setForeground(new java.awt.Color(0, 0, 0));
        navFindFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_black.png")));

        navFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/friend_white.png")));


        navChatButton.setBackground(new java.awt.Color(26, 41, 128));
        navChatButton.setForeground(new java.awt.Color(255, 255, 255));
        navChatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat_white.png")));

        navProfileButton.setBackground(new java.awt.Color(26, 41, 128));
        navProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        navProfileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile_white.png")));

        logoutButton.setBackground(new java.awt.Color(26, 41, 128));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout_white.png")));

        userHomePanel.setVisible(false);        
        userFriendPanel.setVisible(false);
        userFindFriendPanel.setVisible(true);        
        userProfilePanel.setVisible(false);
    }
        
    public void showProfilePanel() {
        navProfileButton.setBackground(new java.awt.Color(153, 255, 255));
        navProfileButton.setForeground(new java.awt.Color(0, 0, 0));
        navProfileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile_black.png")));

        navFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/friend_white.png")));


        navFindFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFindFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFindFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_white.png")));

        navChatButton.setBackground(new java.awt.Color(26, 41, 128));
        navChatButton.setForeground(new java.awt.Color(255, 255, 255));
        navChatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat_white.png")));

        logoutButton.setBackground(new java.awt.Color(153, 255, 255));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout_black.png")));

        userHomePanel.setVisible(false);     
        userFriendPanel.setVisible(false);
        userFindFriendPanel.setVisible(false);        
        userProfilePanel.setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navBarContainer = new javax.swing.JPanel();
        logoContainer = new javax.swing.JLabel();
        profileContainer = new javax.swing.JPanel();
        navProfileButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        navChatButton = new javax.swing.JButton();
        navFriendButton = new javax.swing.JButton();
        navFindFriendButton = new javax.swing.JButton();
        navAdminButton = new javax.swing.JButton();
        contentContainer = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wave - Home");
        setPreferredSize(new java.awt.Dimension(930, 600));
        setSize(new java.awt.Dimension(930, 600));

        navBarContainer.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 2, new java.awt.Color(0, 0, 0)));
        navBarContainer.setPreferredSize(new java.awt.Dimension(188, 600));

        logoContainer.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        logoContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoSmall.png"))); // NOI18N
        logoContainer.setText("Wave");
        logoContainer.setPreferredSize(new java.awt.Dimension(165, 40));

        profileContainer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        navProfileButton.setBackground(new java.awt.Color(26, 41, 128));
        navProfileButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        navProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        navProfileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile_white.png"))); // NOI18N
        navProfileButton.setText("Profile");
        navProfileButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        navProfileButton.setIconTextGap(8);
        navProfileButton.setPreferredSize(new java.awt.Dimension(125, 40));
        navProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navProfileButtonActionPerformed(evt);
            }
        });
        profileContainer.add(navProfileButton);

        logoutButton.setBackground(new java.awt.Color(26, 41, 128));
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout_white.png"))); // NOI18N
        logoutButton.setPreferredSize(new java.awt.Dimension(40, 40));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        profileContainer.add(logoutButton);

        navChatButton.setBackground(new java.awt.Color(204, 255, 255));
        navChatButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        navChatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat_black.png"))); // NOI18N
        navChatButton.setText("Chat");
        navChatButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        navChatButton.setIconTextGap(12);
        navChatButton.setPreferredSize(new java.awt.Dimension(165, 40));
        navChatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navChatButtonActionPerformed(evt);
            }
        });

        navFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFriendButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        navFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/friend_white.png"))); // NOI18N
        navFriendButton.setText("Friend");
        navFriendButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        navFriendButton.setIconTextGap(12);
        navFriendButton.setPreferredSize(new java.awt.Dimension(165, 40));
        navFriendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navFriendButtonActionPerformed(evt);
            }
        });

        navFindFriendButton.setBackground(new java.awt.Color(26, 41, 128));
        navFindFriendButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        navFindFriendButton.setForeground(new java.awt.Color(255, 255, 255));
        navFindFriendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_white.png"))); // NOI18N
        navFindFriendButton.setText("Find Friend");
        navFindFriendButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        navFindFriendButton.setIconTextGap(12);
        navFindFriendButton.setPreferredSize(new java.awt.Dimension(165, 40));
        navFindFriendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navFindFriendButtonActionPerformed(evt);
            }
        });

        navAdminButton.setBackground(new java.awt.Color(26, 41, 128));
        navAdminButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        navAdminButton.setForeground(new java.awt.Color(255, 255, 255));
        navAdminButton.setText("AdminMode");
        navAdminButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        navAdminButton.setIconTextGap(12);
        navAdminButton.setPreferredSize(new java.awt.Dimension(165, 40));
        navAdminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navAdminButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navBarContainerLayout = new javax.swing.GroupLayout(navBarContainer);
        navBarContainer.setLayout(navBarContainerLayout);
        navBarContainerLayout.setHorizontalGroup(
            navBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navBarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navChatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navFindFriendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navFriendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navAdminButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navBarContainerLayout.setVerticalGroup(
            navBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navBarContainerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(logoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(navChatButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navFriendButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navFindFriendButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 312, Short.MAX_VALUE)
                .addComponent(navAdminButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(navBarContainer, java.awt.BorderLayout.LINE_START);

        contentContainer.setLayout(new java.awt.CardLayout());
        getContentPane().add(contentContainer, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        try {
            // Gửi message cho server xử lí
            if (clientSocket != null) {
                clientSocket.sendMessage("LOGOUT: " + GlobalVariable.getUserID()); // Gửi lệnh LOGOUT tới server
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Tạo socket mới
        try {
            ClientSocketManager newClientSocket = new ClientSocketManager("localhost", 1234);
            this.dispose();
            AuthenticationMain navFrame = new AuthenticationMain(newClientSocket);
            navFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Could not connect to server. Please try again.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void navFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navFriendButtonActionPerformed
        showFriendPanel();
    }//GEN-LAST:event_navFriendButtonActionPerformed

    private void navChatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navChatButtonActionPerformed
        showChatPanel();
    }//GEN-LAST:event_navChatButtonActionPerformed

    private void navFindFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navFindFriendButtonActionPerformed
        showFindFriendPanel();
    }//GEN-LAST:event_navFindFriendButtonActionPerformed

    private void navProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navProfileButtonActionPerformed
        showProfilePanel();
    }//GEN-LAST:event_navProfileButtonActionPerformed

    private void navAdminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navAdminButtonActionPerformed
        this.dispose();
        AdminHomeMain navFrame = new AdminHomeMain(clientSocket); 
        navFrame.setVisible(true); 
    }//GEN-LAST:event_navAdminButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane contentContainer;
    private javax.swing.JLabel logoContainer;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton navAdminButton;
    private javax.swing.JPanel navBarContainer;
    private javax.swing.JButton navChatButton;
    private javax.swing.JButton navFindFriendButton;
    private javax.swing.JButton navFriendButton;
    private javax.swing.JButton navProfileButton;
    private javax.swing.JPanel profileContainer;
    // End of variables declaration//GEN-END:variables
}
