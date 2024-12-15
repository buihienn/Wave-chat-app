package com.wavechat.form;

import com.wavechat.contentPanel.UserFindFriendPanel;
import com.wavechat.contentPanel.UserFriendPanel;
import com.wavechat.contentPanel.UserHomePanel;
import com.wavechat.contentPanel.UserProfilePanel;

public class UserHomeMain extends javax.swing.JFrame {

    UserHomePanel userHomePanel = new UserHomePanel();    
    UserFriendPanel userFriendPanel = new UserFriendPanel();   
    UserFindFriendPanel userFindFriendPanel = new UserFindFriendPanel();
    UserProfilePanel userProfilePanel = new UserProfilePanel();

    public UserHomeMain() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        contentContainer.add(userHomePanel);          
        contentContainer.add(userFriendPanel);  
        contentContainer.add(userFindFriendPanel);           
        contentContainer.add(userProfilePanel);        
      
        userHomePanel.open();
                    
        userHomePanel.setVisible(true);        
        userFriendPanel.setVisible(false);
        userFindFriendPanel.setVisible(false);        
        userProfilePanel.setVisible(false);

    }
    
    private void changeModeButton(String mode) {
        if (mode == "Chat") {
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
            
            userHomePanel.open();
            
            userHomePanel.setVisible(true);        
            userFriendPanel.setVisible(false);
            userFindFriendPanel.setVisible(false);        
            userProfilePanel.setVisible(false);

        }
        else if (mode == "Friend") {
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
        else if (mode == "Find") {
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
        else if (mode == "Profile") {
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
                    .addComponent(navFriendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(profileContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(navBarContainer, java.awt.BorderLayout.LINE_START);

        contentContainer.setLayout(new java.awt.CardLayout());
        getContentPane().add(contentContainer, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void navFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navFriendButtonActionPerformed
        changeModeButton("Friend");
    }//GEN-LAST:event_navFriendButtonActionPerformed

    private void navChatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navChatButtonActionPerformed
        changeModeButton("Chat");
    }//GEN-LAST:event_navChatButtonActionPerformed

    private void navFindFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navFindFriendButtonActionPerformed
        changeModeButton("Find");
    }//GEN-LAST:event_navFindFriendButtonActionPerformed

    private void navProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navProfileButtonActionPerformed
        changeModeButton("Profile");
    }//GEN-LAST:event_navProfileButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserHomeMain().setVisible(true);
            }
        });
    }

    private String mode = "";
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane contentContainer;
    private javax.swing.JLabel logoContainer;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel navBarContainer;
    private javax.swing.JButton navChatButton;
    private javax.swing.JButton navFindFriendButton;
    private javax.swing.JButton navFriendButton;
    private javax.swing.JButton navProfileButton;
    private javax.swing.JPanel profileContainer;
    // End of variables declaration//GEN-END:variables
}
