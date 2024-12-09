package com.wavechat.contentPanel;

import com.wavechat.bus.UserBUS;
import com.wavechat.form.AuthenticationMain;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class AuthenticationRegisterPanel extends javax.swing.JPanel {

    public AuthenticationRegisterPanel() {
        initComponents();
    }
    
    private void handleRegister() {
        String userName = usernameInput.getText();
        String email = emailInput.getText();
        String password = passwordInput.getText(); 
        String confirmPassword = confirmPasswordInput.getText(); 

        UserBUS userBUS = new UserBUS(); 
        // Kiểm tra các trường đầu vào
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill full information!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra xem tên người dùng và email đã tồn tại chưa
        if (userBUS.isUserNameExist(userName)) {
            JOptionPane.showMessageDialog(this, "Username existed!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!userBUS.isEmailValid(email)) {
            JOptionPane.showMessageDialog(this, "Email is not valid!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userBUS.isEmailExist(email)) {
            JOptionPane.showMessageDialog(this, "Email existed!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!userBUS.isPasswordValid(password)) {
            JOptionPane.showMessageDialog(this, "Password must have at least 8 characters, including 1 uppercase letter, 1 lowercase letter, and 1 number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!confirmPassword.equals(password)) {
            JOptionPane.showMessageDialog(this, "Password and confirm password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = userBUS.addNewUser(userName, email, password);

        if (success) {
            // Hiển thị thông báo đăng ký thành công và nút quay lại đăng nhập
            int option = JOptionPane.showOptionDialog(this,
                    "Register successful!",
                    "successful",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[] { "Back to login" }, "Back to login");

            // Nếu người dùng nhấn "Quay lại đăng nhập"
            if (option == 0) {
                // Navigate qua login
                parentFrame.showLoginPanel();
            }
        } else {
            JOptionPane.showMessageDialog(this, "There is error when register. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private AuthenticationMain parentFrame;

    // Constructor nhận tham chiếu đến AuthenticationMain
    public AuthenticationRegisterPanel(AuthenticationMain parent) {
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

        register = new java.awt.Panel();
        loginContainer = new java.awt.Panel();
        logo = new javax.swing.JLabel();
        login = new javax.swing.JLabel();
        slogan = new javax.swing.JLabel();
        emailInput = new javax.swing.JTextField();
        usernameInput = new javax.swing.JTextField();
        passwordInput = new javax.swing.JTextField();
        confirmPasswordInput = new javax.swing.JTextField();
        registerButton = new javax.swing.JButton();
        donothaveacc = new javax.swing.JLabel();
        navLoginButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(500, 600));
        setLayout(new java.awt.BorderLayout());

        register.setBackground(new java.awt.Color(246, 246, 246));
        register.setPreferredSize(new java.awt.Dimension(500, 600));
        register.setLayout(new java.awt.GridBagLayout());

        loginContainer.setBackground(new java.awt.Color(246, 246, 246));
        loginContainer.setLayout(new java.awt.GridBagLayout());

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        loginContainer.add(logo, gridBagConstraints);

        login.setFont(new java.awt.Font("Montserrat", 0, 36)); // NOI18N
        login.setText("Register");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(login, gridBagConstraints);

        slogan.setText("Join to connect and share your waves.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        loginContainer.add(slogan, gridBagConstraints);

        emailInput.setBackground(new java.awt.Color(246, 246, 246));
        emailInput.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        emailInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Email address", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat", 1, 12))); // NOI18N
        emailInput.setPreferredSize(new java.awt.Dimension(294, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(emailInput, gridBagConstraints);

        usernameInput.setBackground(new java.awt.Color(246, 246, 246));
        usernameInput.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        usernameInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Username", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat", 1, 12))); // NOI18N
        usernameInput.setPreferredSize(new java.awt.Dimension(294, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(usernameInput, gridBagConstraints);

        passwordInput.setBackground(new java.awt.Color(246, 246, 246));
        passwordInput.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        passwordInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat", 1, 12))); // NOI18N
        passwordInput.setPreferredSize(new java.awt.Dimension(294, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(passwordInput, gridBagConstraints);

        confirmPasswordInput.setBackground(new java.awt.Color(246, 246, 246));
        confirmPasswordInput.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        confirmPasswordInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Confirm password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat", 1, 12))); // NOI18N
        confirmPasswordInput.setPreferredSize(new java.awt.Dimension(294, 35));
        confirmPasswordInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmPasswordInputKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(confirmPasswordInput, gridBagConstraints);

        registerButton.setBackground(new java.awt.Color(26, 41, 128));
        registerButton.setFont(new java.awt.Font("Montserrat", 0, 16)); // NOI18N
        registerButton.setForeground(new java.awt.Color(255, 255, 255));
        registerButton.setText("Register");
        registerButton.setPreferredSize(new java.awt.Dimension(294, 35));
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 35, 0);
        loginContainer.add(registerButton, gridBagConstraints);

        donothaveacc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        donothaveacc.setText("Already have an account?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        loginContainer.add(donothaveacc, gridBagConstraints);

        navLoginButton.setBackground(new java.awt.Color(26, 41, 128));
        navLoginButton.setFont(new java.awt.Font("Montserrat", 0, 16)); // NOI18N
        navLoginButton.setForeground(new java.awt.Color(255, 255, 255));
        navLoginButton.setText("Login");
        navLoginButton.setPreferredSize(new java.awt.Dimension(100, 35));
        navLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navLoginButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        loginContainer.add(navLoginButton, gridBagConstraints);

        register.add(loginContainer, new java.awt.GridBagConstraints());

        add(register, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        handleRegister();
    }//GEN-LAST:event_registerButtonActionPerformed

    private void navLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navLoginButtonActionPerformed
        parentFrame.showLoginPanel();
    }//GEN-LAST:event_navLoginButtonActionPerformed

    private void confirmPasswordInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmPasswordInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            handleRegister();
        }
    }//GEN-LAST:event_confirmPasswordInputKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField confirmPasswordInput;
    private javax.swing.JLabel donothaveacc;
    private javax.swing.JTextField emailInput;
    private javax.swing.JLabel login;
    private java.awt.Panel loginContainer;
    private javax.swing.JLabel logo;
    private javax.swing.JButton navLoginButton;
    private javax.swing.JTextField passwordInput;
    private java.awt.Panel register;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel slogan;
    private javax.swing.JTextField usernameInput;
    // End of variables declaration//GEN-END:variables
}
