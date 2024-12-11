package com.wavechat.contentPanel;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.UserBUS;
import com.wavechat.dto.UserDTO;

public class UserProfilePanel extends javax.swing.JPanel {
    public UserProfilePanel() {
        initComponents();
        
        updateProfile();
    }
    
    // Phương thức để mở phần edit profile
    public void openEditProfileDialog() {
        String fullname = fullnameDataLabel.getText();
        String gender = genderDataLabel.getText();
        String birth = birthDataLabel.getText();
        String address = addressDataLabel.getText();

        setDataOfEditProfileDialog(fullname, gender, birth, address);

        editProfileDialog.setLocationRelativeTo(this);
        editProfileDialog.setVisible(true);
    }
    
    // Để lấy data từ form bỏ vào dialog
    private void setDataOfEditProfileDialog(String fullname, String gender, String birth, String address) {
        fullnameEditDataLabel.setText(fullname);
        birthEditDataLabel.setText(birth);
        addressEditDataLabel.setText(address);

        // Chọn RadioButton dựa trên giá trị gender
        if ("Male".equalsIgnoreCase(gender)) {
            maleRadioButton.setSelected(true);
        } else if ("Female".equalsIgnoreCase(gender)) {
            femaleRadioButton.setSelected(true);
        } else if ("Prefer not to say".equalsIgnoreCase(gender)) {
            preferRadioButton.setSelected(true);
        }
    }
    
    // check logic của các trường mà người dùng nhập
    private boolean checkLogicEdit() {
        String fullName = fullnameEditDataLabel.getText();
        String birthDate = birthEditDataLabel.getText();
        String address = addressEditDataLabel.getText();

        StringBuilder message = new StringBuilder();

        // Kiểm tra fullname
        if (fullName.isEmpty()) {
            message.append("Fullname cannot be empty.\n");
        }

        // Kiểm tra birth
        if (birthDate.isEmpty()) {
            message.append("Date of birth cannot be empty.\n");
        } else if (!birthDate.matches("\\d{2}/\\d{2}/\\d{4}")) { // Định dạng dd/MM/yyyy
            message.append("Birthdate must be in format dd/MM/yyyy.\n");
        }

        // Kiểm tra address
        if (address.isEmpty()) {
            message.append("Address cannot be empty.\n");
        }

        // Hiển thị thông báo lỗi nếu có
        if (message.length() > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, message.toString(), "Validation Errors", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Trả về true nếu không có lỗi
        return true;
    }
    
    private void editUser() {
        // Lấy thông tin từ dialog
        String userIDData = GlobalVariable.getUserID();
        String fullNameData = fullnameEditDataLabel.getText(); 
        String birthData = birthEditDataLabel.getText(); 
        String addressData = addressEditDataLabel.getText(); 

        // Xác định gender từ RadioButton
        String genderData = null;
        if (maleRadioButton.isSelected()) {
            genderData = "Male";
        } else if (femaleRadioButton.isSelected()) {
            genderData = "Female";
        } else if (preferRadioButton.isSelected()) {
            genderData = "Prefer not to say";
        }

        UserBUS bus = new UserBUS();

        try {
            // Xử lí date
            java.text.SimpleDateFormat inputFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date birthDay = inputFormat.parse(birthData);
            
            UserDTO user = new UserDTO(
                userIDData,       
                fullNameData,    
                addressData,      
                birthDay,        
                genderData      
            );

            // Gọi phương thức để cập nhật thông tin
            if (bus.editUser(user)) {
                System.out.println("User updated successfully!");

                // Cập nhật hiển thị profile sau khi update user thành công
                updateProfile();
            } else {
                System.out.println("Failed to update user.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateProfile() {
        String userID = GlobalVariable.getUserID();
        userIDDataLabel.setText(userID);
        // Khởi tạo đối tượng userBUS
        UserBUS bus = new UserBUS();

        try {
            // Lấy thông tin user từ database thông qua userBUS
            UserDTO user = bus.getUserByID(userID);

            if (user != null) {
                // Cập nhật các label với thông tin lấy được
                fullnameDataLabel.setText(user.getFullName());
                genderDataLabel.setText(user.getGender());

                // Định dạng ngày sinh (nếu cần)
                java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
                String formattedBirthDate = outputFormat.format(user.getBirthDay());
                birthDataLabel.setText(formattedBirthDate);

                addressDataLabel.setText(user.getAddress());
                
                usernameDataLabel.setText(user.getUserName());                
                emailDataLabel.setText(user.getEmail());

            } else {
                // Hiển thị thông báo nếu không tìm thấy user
                javax.swing.JOptionPane.showMessageDialog(this, "User not found!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // Hiển thị lỗi nếu có vấn đề xảy ra
            javax.swing.JOptionPane.showMessageDialog(this, "Error fetching user data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
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

        genderChoose = new javax.swing.ButtonGroup();
        editProfileDialog = new javax.swing.JDialog();
        popupEditProfile = new javax.swing.JPanel();
        genderEditLabel = new javax.swing.JLabel();
        maleRadioButton = new javax.swing.JRadioButton();
        femaleRadioButton = new javax.swing.JRadioButton();
        preferRadioButton = new javax.swing.JRadioButton();
        fullnameEditDataLabel = new javax.swing.JTextField();
        userAvatar1 = new javax.swing.JLabel();
        changeAvatarButton = new javax.swing.JButton();
        birthEditLabel = new javax.swing.JLabel();
        birthEditDataLabel = new javax.swing.JTextField();
        addressEditLabel = new javax.swing.JLabel();
        addressEditDataLabel = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        profileLabel = new javax.swing.JLabel();
        userContainer = new javax.swing.JPanel();
        userAvatar = new javax.swing.JLabel();
        fullnameDataLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        birthLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        genderLabel = new javax.swing.JLabel();
        editProfileButton = new javax.swing.JButton();
        changePasswordButton = new javax.swing.JButton();
        jSeparator = new javax.swing.JSeparator();
        emailDataLabel = new javax.swing.JLabel();
        genderDataLabel = new javax.swing.JLabel();
        birthDataLabel = new javax.swing.JLabel();
        usernameDataLabel = new javax.swing.JLabel();
        addressDataLabel = new javax.swing.JLabel();
        IDLabel = new javax.swing.JLabel();
        userIDDataLabel = new javax.swing.JLabel();

        editProfileDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        editProfileDialog.setTitle("Wave - Edit Profile");
        editProfileDialog.setMinimumSize(new java.awt.Dimension(731, 320));
        editProfileDialog.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        editProfileDialog.setResizable(false);
        editProfileDialog.setSize(new java.awt.Dimension(731, 330));

        popupEditProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        popupEditProfile.setMinimumSize(new java.awt.Dimension(719, 300));

        genderEditLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        genderEditLabel.setText("Gender:");

        genderChoose.add(maleRadioButton);
        maleRadioButton.setText("Male");

        genderChoose.add(femaleRadioButton);
        femaleRadioButton.setText("Female");

        genderChoose.add(preferRadioButton);
        preferRadioButton.setText("Prefer not to say");
        preferRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preferRadioButtonActionPerformed(evt);
            }
        });

        fullnameEditDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        fullnameEditDataLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullnameEditDataLabelActionPerformed(evt);
            }
        });

        userAvatar1.setText("Avatar1");
        userAvatar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        userAvatar1.setPreferredSize(new java.awt.Dimension(54, 54));

        changeAvatarButton.setBackground(new java.awt.Color(26, 41, 128));
        changeAvatarButton.setFont(new java.awt.Font("Montserrat", 0, 10)); // NOI18N
        changeAvatarButton.setForeground(new java.awt.Color(255, 255, 255));
        changeAvatarButton.setText("Change");

        birthEditLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        birthEditLabel.setText("Date of birth:");

        birthEditDataLabel.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        birthEditDataLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthEditDataLabelActionPerformed(evt);
            }
        });

        addressEditLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addressEditLabel.setText("Address:");

        addressEditDataLabel.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        addressEditDataLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressEditDataLabelActionPerformed(evt);
            }
        });

        confirmButton.setBackground(new java.awt.Color(26, 41, 128));
        confirmButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        confirmButton.setText("Confirm");
        confirmButton.setPreferredSize(new java.awt.Dimension(165, 40));
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout popupEditProfileLayout = new javax.swing.GroupLayout(popupEditProfile);
        popupEditProfile.setLayout(popupEditProfileLayout);
        popupEditProfileLayout.setHorizontalGroup(
            popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupEditProfileLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(changeAvatarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(popupEditProfileLayout.createSequentialGroup()
                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(popupEditProfileLayout.createSequentialGroup()
                        .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addressEditLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(birthEditLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(genderEditLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addressEditDataLabel)
                            .addGroup(popupEditProfileLayout.createSequentialGroup()
                                .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(birthEditDataLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, popupEditProfileLayout.createSequentialGroup()
                                        .addComponent(maleRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(femaleRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(preferRadioButton)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupEditProfileLayout.createSequentialGroup()
                        .addComponent(fullnameEditDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(198, 198, 198)))
                .addGap(21, 21, 21))
        );
        popupEditProfileLayout.setVerticalGroup(
            popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(popupEditProfileLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(popupEditProfileLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(genderEditLabel)
                            .addComponent(maleRadioButton)
                            .addComponent(femaleRadioButton)
                            .addComponent(preferRadioButton))
                        .addGap(20, 20, 20)
                        .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(birthEditLabel)
                            .addComponent(birthEditDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(popupEditProfileLayout.createSequentialGroup()
                        .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fullnameEditDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changeAvatarButton)))
                .addGap(26, 26, 26)
                .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addressEditLabel)
                    .addComponent(addressEditDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(popupEditProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        editProfileDialog.getContentPane().add(popupEditProfile, java.awt.BorderLayout.CENTER);

        profileLabel.setFont(new java.awt.Font("Montserrat", 0, 28)); // NOI18N
        profileLabel.setText("Profile");
        profileLabel.setPreferredSize(new java.awt.Dimension(214, 32));

        userContainer.setPreferredSize(new java.awt.Dimension(714, 364));

        userAvatar.setText("Avatar1");
        userAvatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        userAvatar.setPreferredSize(new java.awt.Dimension(54, 54));

        fullnameDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N

        statusLabel.setText("Online");

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        usernameLabel.setText("Username:");
        usernameLabel.setPreferredSize(new java.awt.Dimension(41, 22));

        addressLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addressLabel.setText("Address:");

        logoutButton.setBackground(new java.awt.Color(153, 255, 255));
        logoutButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        logoutButton.setText("Log out");
        logoutButton.setPreferredSize(new java.awt.Dimension(165, 40));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        birthLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        birthLabel.setText("Date of birth:");

        emailLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        emailLabel.setText("Email:");

        genderLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        genderLabel.setText("Gender:");

        editProfileButton.setBackground(new java.awt.Color(26, 41, 128));
        editProfileButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        editProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        editProfileButton.setText("Edit profile");
        editProfileButton.setPreferredSize(new java.awt.Dimension(165, 40));
        editProfileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editProfileButtonMouseClicked(evt);
            }
        });
        editProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });

        changePasswordButton.setBackground(new java.awt.Color(26, 41, 128));
        changePasswordButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        changePasswordButton.setForeground(new java.awt.Color(255, 255, 255));
        changePasswordButton.setText("Change password");
        changePasswordButton.setPreferredSize(new java.awt.Dimension(165, 40));

        jSeparator.setForeground(new java.awt.Color(0, 0, 0));

        emailDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        genderDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        birthDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        usernameDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        addressDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        IDLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IDLabel.setText("ID:");

        userIDDataLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        userIDDataLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout userContainerLayout = new javax.swing.GroupLayout(userContainer);
        userContainer.setLayout(userContainerLayout);
        userContainerLayout.setHorizontalGroup(
            userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(userContainerLayout.createSequentialGroup()
                        .addComponent(IDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userIDDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(userAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userContainerLayout.createSequentialGroup()
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(userContainerLayout.createSequentialGroup()
                                .addComponent(fullnameDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(41, 41, 41)
                                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(228, 228, 228))
                            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(userContainerLayout.createSequentialGroup()
                                .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(birthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(genderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(usernameDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                                    .addComponent(addressDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                                    .addComponent(birthDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                                    .addComponent(genderDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                                    .addComponent(emailDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(216, 216, 216))
                            .addGroup(userContainerLayout.createSequentialGroup()
                                .addComponent(editProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(224, 224, 224)))
                        .addContainerGap())
                    .addGroup(userContainerLayout.createSequentialGroup()
                        .addComponent(jSeparator)
                        .addGap(158, 158, 158))))
        );
        userContainerLayout.setVerticalGroup(
            userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userContainerLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(userAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(userContainerLayout.createSequentialGroup()
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullnameDataLabel)
                            .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusLabel)))
                .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userContainerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(emailDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameDataLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genderLabel)
                            .addGroup(userContainerLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(genderDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(birthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(birthDataLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addressLabel)
                            .addComponent(addressDataLabel))
                        .addGap(33, 33, 33)
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(editProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(changePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(156, 156, 156))
                    .addGroup(userContainerLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(userContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IDLabel)
                            .addComponent(userIDDataLabel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void editProfileButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editProfileButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_editProfileButtonMouseClicked

    private void editProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileButtonActionPerformed
        // Mở dialog editprofile
        openEditProfileDialog();
    }//GEN-LAST:event_editProfileButtonActionPerformed

    private void fullnameEditDataLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnameEditDataLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnameEditDataLabelActionPerformed

    private void birthEditDataLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthEditDataLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthEditDataLabelActionPerformed

    private void addressEditDataLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressEditDataLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressEditDataLabelActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        if (!checkLogicEdit()) {
            return; // Nếu có lỗi, không tiếp tục
        }
        // Thực hiện cập nhật thông tin người dùng
        editUser();

        editProfileDialog.dispose();
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        editProfileDialog.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void preferRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preferRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_preferRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IDLabel;
    private javax.swing.JLabel addressDataLabel;
    private javax.swing.JTextField addressEditDataLabel;
    private javax.swing.JLabel addressEditLabel;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JLabel birthDataLabel;
    private javax.swing.JTextField birthEditDataLabel;
    private javax.swing.JLabel birthEditLabel;
    private javax.swing.JLabel birthLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton changeAvatarButton;
    private javax.swing.JButton changePasswordButton;
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JDialog editProfileDialog;
    private javax.swing.JLabel emailDataLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JRadioButton femaleRadioButton;
    private javax.swing.JLabel fullnameDataLabel;
    private javax.swing.JTextField fullnameEditDataLabel;
    private javax.swing.ButtonGroup genderChoose;
    private javax.swing.JLabel genderDataLabel;
    private javax.swing.JLabel genderEditLabel;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JButton logoutButton;
    private javax.swing.JRadioButton maleRadioButton;
    private javax.swing.JPanel popupEditProfile;
    private javax.swing.JRadioButton preferRadioButton;
    private javax.swing.JLabel profileLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel userAvatar;
    private javax.swing.JLabel userAvatar1;
    private javax.swing.JPanel userContainer;
    private javax.swing.JLabel userIDDataLabel;
    private javax.swing.JLabel usernameDataLabel;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
