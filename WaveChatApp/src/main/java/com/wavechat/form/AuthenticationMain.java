package com.wavechat.form;

import com.wavechat.contentPanel.AuthenticationForgotPassPanel;
import com.wavechat.contentPanel.AuthenticationLoginPanel;
import com.wavechat.contentPanel.AuthenticationRegisterPanel;
import com.wavechat.contentPanel.AuthenticationThankYouPanel;
import com.wavechat.socket.ClientSocketManager;

public class AuthenticationMain extends javax.swing.JFrame {
    private final ClientSocketManager clientSocket;
    
    AuthenticationLoginPanel loginPanel = new AuthenticationLoginPanel(this, null);
    AuthenticationRegisterPanel registerPanel = new AuthenticationRegisterPanel(this);
    AuthenticationForgotPassPanel forgotPassPanel = new AuthenticationForgotPassPanel(this);
    AuthenticationThankYouPanel thankYouPanel = new AuthenticationThankYouPanel(this);
    
    public AuthenticationMain(ClientSocketManager clientSocket) {
        initComponents();
        this.setLocationRelativeTo(null);
        
        contentContainer.add(loginPanel);
        contentContainer.add(registerPanel);
        contentContainer.add(forgotPassPanel);
        contentContainer.add(thankYouPanel);
        
        loginPanel.setVisible(true);        
        registerPanel.setVisible(false);
        forgotPassPanel.setVisible(false);
        thankYouPanel.setVisible(false);
        
        this.clientSocket = clientSocket;
        loginPanel.clientSocket = this.clientSocket;
    }
    
    public void showLoginPanel() {
        loginPanel.setVisible(true);        
        registerPanel.setVisible(false);
        forgotPassPanel.setVisible(false);
        thankYouPanel.setVisible(false);
    }

    public void showRegisterPanel() {
        loginPanel.setVisible(false);        
        registerPanel.setVisible(true);
        forgotPassPanel.setVisible(false);
        thankYouPanel.setVisible(false);
    }
    
    public void showForgotPassPanel() {
        loginPanel.setVisible(false);        
        registerPanel.setVisible(false);
        forgotPassPanel.setVisible(true);
        thankYouPanel.setVisible(false);
    }
    
    public void showThankYouPanel() {
        loginPanel.setVisible(false);        
        registerPanel.setVisible(false);
        forgotPassPanel.setVisible(false);
        thankYouPanel.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentContainer = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wave");
        setPreferredSize(new java.awt.Dimension(500, 630));
        setResizable(false);

        contentContainer.setLayout(new java.awt.CardLayout());
        getContentPane().add(contentContainer, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane contentContainer;
    // End of variables declaration//GEN-END:variables
}
