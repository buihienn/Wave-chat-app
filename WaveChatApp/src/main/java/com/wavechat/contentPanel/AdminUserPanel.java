/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.wavechat.contentPanel;

import com.wavechat.bus.UserBUS;
import com.wavechat.dto.UserDTO;
import java.awt.Font;
import java.util.*;

/**
 *
 * @author buihi
 */
public class AdminUserPanel extends javax.swing.JPanel {

    /**
     * Creates new form AdminUserPanel
     */
//    AdminDashBoard dashboardPanel = new AdminDashBoard();
    AdminUserPanel_AllUser allUserPanel = new AdminUserPanel_AllUser();
    AdminUserPanel_Friends friendPanel = new AdminUserPanel_Friends();
    AdminUserPanel_ActivityLog activityLogPanel = new AdminUserPanel_ActivityLog();
    AdminUserPanel_NewRegister newRegisterPanel = new AdminUserPanel_NewRegister();
    AdminUserPanel_Insight insightPanel = new AdminUserPanel_Insight();
    
    public AdminUserPanel() {
        initComponents();
        dataPanel.add(allUserPanel);
        dataPanel.add(friendPanel);
        dataPanel.add(activityLogPanel);
        dataPanel.add(newRegisterPanel);
        dataPanel.add(insightPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuUserBar = new javax.swing.JPanel();
        newRegister = new javax.swing.JLabel();
        activityLog = new javax.swing.JLabel();
        inSig = new javax.swing.JLabel();
        allUser = new javax.swing.JLabel();
        friends = new javax.swing.JLabel();
        dataPanel = new javax.swing.JLayeredPane();

        menuUserBar.setBorder(new javax.swing.border.MatteBorder(null));

        newRegister.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        newRegister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newRegister.setText("New register");
        newRegister.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        newRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newRegisterMouseClicked(evt);
            }
        });

        activityLog.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        activityLog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        activityLog.setText("Activity Log");
        activityLog.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        activityLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activityLogMouseClicked(evt);
            }
        });

        inSig.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        inSig.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inSig.setText("Insight");
        inSig.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        inSig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inSigMouseClicked(evt);
            }
        });

        allUser.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        allUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        allUser.setText("All Users");
        allUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        allUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allUserMouseClicked(evt);
            }
        });

        friends.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        friends.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        friends.setText("Friends");
        friends.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        friends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menuUserBarLayout = new javax.swing.GroupLayout(menuUserBar);
        menuUserBar.setLayout(menuUserBarLayout);
        menuUserBarLayout.setHorizontalGroup(
            menuUserBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuUserBarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(allUser, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(friends, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(newRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(activityLog, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inSig, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        menuUserBarLayout.setVerticalGroup(
            menuUserBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuUserBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuUserBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inSig)
                    .addComponent(activityLog)
                    .addComponent(newRegister)
                    .addComponent(allUser)
                    .addComponent(friends))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataPanel)
                    .addComponent(menuUserBar, javax.swing.GroupLayout.PREFERRED_SIZE, 686, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuUserBar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataPanel))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void resetFontStyle() {
        friends.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        friends.setText("Friends");

        allUser.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        allUser.setText("All Users");

        newRegister.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        newRegister.setText("New Register");

        activityLog.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        activityLog.setText("Activity Log");

        inSig.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 18));
        inSig.setText("Insights");
    }
    
    private void friendsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendsMouseClicked
        // TODO add your handling code here:
        allUserPanel.setVisible(false);
        friendPanel.setVisible(true);
        activityLogPanel.setVisible(false);
        newRegisterPanel.setVisible(false);
        insightPanel.setVisible(false);
        
        resetFontStyle();
        
        friends.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); 
        friends.setText("<html><u>Friends</u></html>"); 
    }//GEN-LAST:event_friendsMouseClicked

    private void allUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allUserMouseClicked
        // TODO add your handling code here:
        allUserPanel.setVisible(true);
        friendPanel.setVisible(false);
        activityLogPanel.setVisible(false);
        newRegisterPanel.setVisible(false);
        insightPanel.setVisible(false);
        
        resetFontStyle();
        
        allUser.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); 
        allUser.setText("<html><u>All Users</u></html>");
        
        UserBUS userBUS = new UserBUS();
        List<UserDTO> userDTOs = userBUS.getAll();
        
        allUserPanel.updateUserTable(userDTOs);
    }//GEN-LAST:event_allUserMouseClicked

    private void newRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newRegisterMouseClicked
        // TODO add your handling code here:
        allUserPanel.setVisible(false);
        friendPanel.setVisible(false);
        activityLogPanel.setVisible(false);
        newRegisterPanel.setVisible(true);
        insightPanel.setVisible(false);
        
        resetFontStyle();
        
        newRegister.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); 
        newRegister.setText("<html><u>New Register</u></html>"); 
    }//GEN-LAST:event_newRegisterMouseClicked

    private void activityLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activityLogMouseClicked
        // TODO add your handling code here:
        allUserPanel.setVisible(false);
        friendPanel.setVisible(false);
        activityLogPanel.setVisible(true);
        newRegisterPanel.setVisible(false);
        insightPanel.setVisible(false);
        
        resetFontStyle();
        
        activityLog.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); 
        activityLog.setText("<html><u>Activity Log</u></html>");
    }//GEN-LAST:event_activityLogMouseClicked

    private void inSigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inSigMouseClicked
        // TODO add your handling code here:
        allUserPanel.setVisible(false);
        friendPanel.setVisible(false);
        activityLogPanel.setVisible(false);
        newRegisterPanel.setVisible(false);
        insightPanel.setVisible(true);
        
        resetFontStyle();
        
        inSig.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); 
        inSig.setText("<html><u>Insights</u></html>");
    }//GEN-LAST:event_inSigMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activityLog;
    private javax.swing.JLabel allUser;
    private javax.swing.JLayeredPane dataPanel;
    private javax.swing.JLabel friends;
    private javax.swing.JLabel inSig;
    private javax.swing.JPanel menuUserBar;
    private javax.swing.JLabel newRegister;
    // End of variables declaration//GEN-END:variables
}
