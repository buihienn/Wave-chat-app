package com.wavechat.contentPanel;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.UserBUS;
import com.wavechat.form.AdminHomeMain;
import com.wavechat.form.AuthenticationMain;
import com.wavechat.form.UserHomeMain;
import com.wavechat.socket.ClientSocketManager;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JOptionPane;

public class AuthenticationLoginPanel extends javax.swing.JPanel {
    public ClientSocketManager clientSocket;
    
    public AuthenticationLoginPanel(ClientSocketManager clientSocket) {
        initComponents();
        
        this.clientSocket = clientSocket;
    }

    // Hàm xử lí login
    private void handleLogin() {
        String emailOrUsername = emailOrUsernameInput.getText(); 
        String password = passwordInput.getText(); 

        UserBUS userBUS = new UserBUS();

        // Kiểm tra xem người dùng có nhập đủ thông tin không
        if (emailOrUsername.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill full login information", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Kiểm tra nếu người dùng nhập vào email
        if (userBUS.isEmail(emailOrUsername)) {
            // Nếu là email, kiểm tra trong db
            if (userBUS.isEmailExist(emailOrUsername)) {
                // Kiểm tra password
                if (userBUS.validateUser(emailOrUsername, password)) {
                    String userID = GlobalVariable.getUserID();

                    // Gửi thông báo tới server
                    try {
                        clientSocket.sendMessage("LOGIN_SUCCESS:" + userID);
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to notify server.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    // Kiểm tra cập nhật thông tin nếu đăng nhập lần đầu
                    if (userBUS.getFullNameByID(userID) == null || userBUS.getFullNameByID(userID).isEmpty()) {
                        UserProfilePanel panel = new UserProfilePanel();
                        panel.openEditProfileDialog();
                    }
                    
                    boolean isAdmin = userBUS.isAdmin(emailOrUsername);
                    if (isAdmin) {
                        // Chuyển tới trang Admin
                        parentFrame.dispose();
                        AdminHomeMain navFrame = new AdminHomeMain(); 
                        navFrame.setVisible(true); 
                        
                        
                    } else {
                        // Chuyển tới trang User
                        parentFrame.dispose();
                        UserHomeMain navFrame = new UserHomeMain(clientSocket); 
                        navFrame.setVisible(true); 
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Wrong password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else {
                JOptionPane.showMessageDialog(this, "Wrong login information.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        // Kiểm tra nếu người dùng nhập vào username
        else {
            // Nếu là username, kiểm tra trong db
            if (userBUS.isUserNameExist(emailOrUsername)) {
                // Kiểm tra password
                if (userBUS.validateUser(emailOrUsername, password)) {
                    // Kiểm tra cập nhật thông tin nếu đăng nhập lần đầu
                    String userID = GlobalVariable.getUserID();
                    
                    // Gửi thông báo tới server
                    try {
                        clientSocket.sendMessage("LOGIN_SUCCESS:" + userID);
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to notify server.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    if (userBUS.getFullNameByID(userID) == null || userBUS.getFullNameByID(userID).isEmpty()) {
                        UserProfilePanel panel = new UserProfilePanel();
                        panel.openEditProfileDialog();
                    }
                    
                    boolean isAdmin = userBUS.isAdmin(emailOrUsername);
                    if (isAdmin) {
                        // Chuyển tới trang Admin
                        parentFrame.dispose();
                        AdminHomeMain navFrame = new AdminHomeMain(); 
                        navFrame.setVisible(true); 
                        
                    } else {
                        // Chuyển tới trang User
                        parentFrame.dispose();
                        UserHomeMain navFrame = new UserHomeMain(clientSocket); 
                        navFrame.setVisible(true); 
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Wrong password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else {
                JOptionPane.showMessageDialog(this, "Wrong login information.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private AuthenticationMain parentFrame;

    // Constructor nhận tham chiếu đến AuthenticationMain
    public AuthenticationLoginPanel(AuthenticationMain parent, ClientSocketManager clientSocket) {
        initComponents();
        this.parentFrame = parent;
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

        loginPanel = new java.awt.Panel();
        loginContainer = new java.awt.Panel();
        logo = new javax.swing.JLabel();
        login = new javax.swing.JLabel();
        slogan = new javax.swing.JLabel();
        emailOrUsernameInput = new javax.swing.JTextField();
        passwordInput = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        donothaveacc = new javax.swing.JLabel();
        navRegisterButton = new javax.swing.JButton();
        forgotpassButton = new javax.swing.JPanel();
        forgotPassButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        loginPanel.setBackground(new java.awt.Color(246, 246, 246));
        loginPanel.setPreferredSize(new java.awt.Dimension(500, 600));
        loginPanel.setLayout(new java.awt.GridBagLayout());

        loginContainer.setBackground(new java.awt.Color(246, 246, 246));
        loginContainer.setLayout(new java.awt.GridBagLayout());

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        loginContainer.add(logo, gridBagConstraints);

        login.setFont(new java.awt.Font("Montserrat", 0, 36)); // NOI18N
        login.setText("Log in");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(login, gridBagConstraints);

        slogan.setText("Join to connect and share your waves.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        loginContainer.add(slogan, gridBagConstraints);

        emailOrUsernameInput.setBackground(new java.awt.Color(246, 246, 246));
        emailOrUsernameInput.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        emailOrUsernameInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Email address or username", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat", 1, 12))); // NOI18N
        emailOrUsernameInput.setPreferredSize(new java.awt.Dimension(294, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(emailOrUsernameInput, gridBagConstraints);

        passwordInput.setBackground(new java.awt.Color(246, 246, 246));
        passwordInput.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        passwordInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat", 1, 12))); // NOI18N
        passwordInput.setPreferredSize(new java.awt.Dimension(294, 35));
        passwordInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordInputKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(passwordInput, gridBagConstraints);

        loginButton.setBackground(new java.awt.Color(26, 41, 128));
        loginButton.setFont(new java.awt.Font("Montserrat", 0, 16)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Log in");
        loginButton.setPreferredSize(new java.awt.Dimension(294, 35));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 35, 0);
        loginContainer.add(loginButton, gridBagConstraints);

        donothaveacc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        donothaveacc.setText("Don't have an account?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        loginContainer.add(donothaveacc, gridBagConstraints);

        navRegisterButton.setBackground(new java.awt.Color(26, 41, 128));
        navRegisterButton.setFont(new java.awt.Font("Montserrat", 0, 16)); // NOI18N
        navRegisterButton.setForeground(new java.awt.Color(255, 255, 255));
        navRegisterButton.setText("Register");
        navRegisterButton.setPreferredSize(new java.awt.Dimension(100, 35));
        navRegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navRegisterButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(navRegisterButton, gridBagConstraints);

        forgotpassButton.setBackground(new java.awt.Color(246, 246, 246));
        forgotpassButton.setPreferredSize(new java.awt.Dimension(200, 22));
        forgotpassButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        forgotPassButton.setBackground(new java.awt.Color(246, 246, 246));
        forgotPassButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        forgotPassButton.setText("Forgot password?");
        forgotPassButton.setBorder(null);
        forgotPassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forgotPassButtonActionPerformed(evt);
            }
        });
        forgotpassButton.add(forgotPassButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(forgotpassButton, gridBagConstraints);

        loginPanel.add(loginContainer, new java.awt.GridBagConstraints());

        add(loginPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        handleLogin();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void navRegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navRegisterButtonActionPerformed
        parentFrame.showRegisterPanel();
    }//GEN-LAST:event_navRegisterButtonActionPerformed

    private void forgotPassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forgotPassButtonActionPerformed
        parentFrame.showForgotPassPanel();
    }//GEN-LAST:event_forgotPassButtonActionPerformed

    private void passwordInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            handleLogin();
        }
    }//GEN-LAST:event_passwordInputKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel donothaveacc;
    private javax.swing.JTextField emailOrUsernameInput;
    private javax.swing.JButton forgotPassButton;
    private javax.swing.JPanel forgotpassButton;
    private javax.swing.JLabel login;
    private javax.swing.JButton loginButton;
    private java.awt.Panel loginContainer;
    private java.awt.Panel loginPanel;
    private javax.swing.JLabel logo;
    private javax.swing.JButton navRegisterButton;
    private javax.swing.JTextField passwordInput;
    private javax.swing.JLabel slogan;
    // End of variables declaration//GEN-END:variables
}
