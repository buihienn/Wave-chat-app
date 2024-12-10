/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.wavechat.form;

import com.wavechat.contentPanel.AdminDashBoard;
import com.wavechat.contentPanel.AdminUserPanel;

/**
 *
 * @author buihi
 */
public class AdminHomeMain extends javax.swing.JFrame {

    /**
     * Creates new form AdminDashBoard
     */
    AdminDashBoard dashboardPanel = new AdminDashBoard();
    AdminUserPanel userPanel = new AdminUserPanel();
    
    public AdminHomeMain() {
        initComponents();
        content.add(dashboardPanel);
        content.add(userPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        notiButton = new javax.swing.JButton();
        usetModeButton = new javax.swing.JButton();
        userButton = new javax.swing.JButton();
        navContainer = new javax.swing.JPanel();
        dashboardNav = new javax.swing.JButton();
        userNav = new javax.swing.JButton();
        groupchatNav = new javax.swing.JButton();
        spamReportNav = new javax.swing.JButton();
        loginHistoryNav = new javax.swing.JButton();
        content = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 600));

        Header.setPreferredSize(new java.awt.Dimension(178, 60));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoSmall.png"))); // NOI18N
        jLabel2.setText("WAVE");

        notiButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/notification.png"))); // NOI18N
        notiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notiButtonActionPerformed(evt);
            }
        });

        usetModeButton.setText("User mode");
        usetModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usetModeButtonActionPerformed(evt);
            }
        });

        userButton.setText("User");
        userButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 454, Short.MAX_VALUE)
                .addComponent(notiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(usetModeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(userButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notiButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(usetModeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        navContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        navContainer.setPreferredSize(new java.awt.Dimension(188, 496));

        dashboardNav.setBackground(new java.awt.Color(26, 41, 128));
        dashboardNav.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dashboardNav.setForeground(new java.awt.Color(255, 255, 255));
        dashboardNav.setText("Dashboard");
        dashboardNav.setPreferredSize(new java.awt.Dimension(165, 40));
        dashboardNav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardNavActionPerformed(evt);
            }
        });

        userNav.setBackground(new java.awt.Color(26, 41, 128));
        userNav.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        userNav.setForeground(new java.awt.Color(255, 255, 255));
        userNav.setText("User");
        userNav.setMaximumSize(new java.awt.Dimension(165, 40));
        userNav.setMinimumSize(new java.awt.Dimension(165, 40));
        userNav.setPreferredSize(new java.awt.Dimension(165, 40));
        userNav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNavActionPerformed(evt);
            }
        });

        groupchatNav.setBackground(new java.awt.Color(26, 41, 128));
        groupchatNav.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        groupchatNav.setForeground(new java.awt.Color(255, 255, 255));
        groupchatNav.setText("Login History");
        groupchatNav.setPreferredSize(new java.awt.Dimension(165, 40));

        spamReportNav.setBackground(new java.awt.Color(26, 41, 128));
        spamReportNav.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        spamReportNav.setForeground(new java.awt.Color(255, 255, 255));
        spamReportNav.setText("Spam Report");
        spamReportNav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spamReportNavActionPerformed(evt);
            }
        });

        loginHistoryNav.setBackground(new java.awt.Color(26, 41, 128));
        loginHistoryNav.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        loginHistoryNav.setForeground(new java.awt.Color(255, 255, 255));
        loginHistoryNav.setText("Group Chat");

        javax.swing.GroupLayout navContainerLayout = new javax.swing.GroupLayout(navContainer);
        navContainer.setLayout(navContainerLayout);
        navContainerLayout.setHorizontalGroup(
            navContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardNav, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
            .addComponent(userNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(groupchatNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(loginHistoryNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(spamReportNav, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        navContainerLayout.setVerticalGroup(
            navContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navContainerLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(dashboardNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupchatNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginHistoryNav, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spamReportNav, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(257, Short.MAX_VALUE))
        );

        content.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        content.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(navContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(content)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                    .addComponent(content))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void notiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notiButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_notiButtonActionPerformed

    private void usetModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usetModeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usetModeButtonActionPerformed

    private void userButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userButtonActionPerformed

    private void dashboardNavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardNavActionPerformed
        // TODO add your handling code here:
        dashboardPanel.setVisible(true);
        userPanel.setVisible(false);
    }//GEN-LAST:event_dashboardNavActionPerformed

    private void userNavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNavActionPerformed
        // TODO add your handling code here:
        dashboardPanel.setVisible(false);
        userPanel.setVisible(true);
    }//GEN-LAST:event_userNavActionPerformed

    private void spamReportNavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spamReportNavActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spamReportNavActionPerformed

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
            java.util.logging.Logger.getLogger(AdminHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHomeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHomeMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JLayeredPane content;
    private javax.swing.JButton dashboardNav;
    private javax.swing.JButton groupchatNav;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginHistoryNav;
    private javax.swing.JPanel navContainer;
    private javax.swing.JButton notiButton;
    private javax.swing.JButton spamReportNav;
    private javax.swing.JButton userButton;
    private javax.swing.JButton userNav;
    private javax.swing.JButton usetModeButton;
    // End of variables declaration//GEN-END:variables
}