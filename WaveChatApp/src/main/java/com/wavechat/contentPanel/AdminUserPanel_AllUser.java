/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.wavechat.contentPanel;

import com.wavechat.bus.UserBUS;
import com.wavechat.dao.DBconnector;
import com.wavechat.dto.UserDTO;
import com.wavechat.form.AdminAllUserAddUserForm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author buihi
 */
public class AdminUserPanel_AllUser extends javax.swing.JPanel {
    private DefaultTableModel tableModel;
    /**
     * Creates new form AdminUserPanelAllUser
     */
    public AdminUserPanel_AllUser() {
        initComponents();  
        
        tableModel = new DefaultTableModel(new Object[]{"Username", "Name", "Address", "BirthDay", "Gender", "Email", "OnlineStatus", "Day Created"}, 0);
        userInformation.setModel(tableModel);
    }
    
    public void updateUserTable(List<UserDTO> userList) {
        tableModel.setRowCount(0);

        // Thêm từng dòng dữ liệu vào bảng
        for (UserDTO user : userList) {
            tableModel.addRow(new Object[]{
                user.getUserName(), 
                user.getFullName(),       
                user.getAddress(),        
                user.getBirthDay() != null ? user.getBirthDay().toString() : "",
                user.getGender(),         
                user.getEmail(),
                user.isOnlineStatus() ? "Online" : "Offline",
                user.getCreatedDate()
            });
        }
    }
    
    private void applyFilter() {
        String txt = filter.getText(); 
        String status = (String) onlineStatus.getSelectedItem(); 

        // Xử lý bộ lọc
        DefaultTableModel dtm = (DefaultTableModel) userInformation.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(dtm);
        userInformation.setRowSorter(sorter);

        // Tạo bộ lọc cho cột Username và Name
        RowFilter<TableModel, Object> textFilter = RowFilter.regexFilter("(?i)" + txt, 0, 1); // Cột 0: Username, Cột 1: Name

        // Tạo bộ lọc cho cột OnlineStatus
        RowFilter<TableModel, Object> statusFilter = null;
        if (status != null && !status.isEmpty()) {
            // Lọc theo Online/Offline cho cột OnlineStatus (cột 6)
            statusFilter = RowFilter.regexFilter("(?i)" + status, 6);
        }

        // Kết hợp các bộ lọc
        if (statusFilter != null) {
            // Nếu có bộ lọc trạng thái, kết hợp nó với bộ lọc văn bản
            sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(textFilter, statusFilter)));
        } else {
            // Nếu không có bộ lọc trạng thái, chỉ lọc theo văn bản
            sorter.setRowFilter(textFilter);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        functionAddUser = new javax.swing.JMenuItem();
        Delete = new javax.swing.JMenuItem();
        functionContainer = new javax.swing.JPanel();
        delete = new javax.swing.JButton();
        add = new javax.swing.JButton();
        filter = new javax.swing.JTextField();
        onlineStatus = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userInformation = new javax.swing.JTable();

        functionAddUser.setText("Add");
        jPopupMenu1.add(functionAddUser);

        Delete.setText("jMenuItem2");
        jPopupMenu1.add(Delete);

        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterActionPerformed(evt);
            }
        });
        filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                filterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterKeyReleased(evt);
            }
        });

        onlineStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "Online", "Offline" }));
        onlineStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineStatusActionPerformed(evt);
            }
        });

        jLabel1.setText("Search");

        jLabel2.setText("Online Status");

        javax.swing.GroupLayout functionContainerLayout = new javax.swing.GroupLayout(functionContainer);
        functionContainer.setLayout(functionContainerLayout);
        functionContainerLayout.setHorizontalGroup(
            functionContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, functionContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filter, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(onlineStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        functionContainerLayout.setVerticalGroup(
            functionContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(functionContainerLayout.createSequentialGroup()
                .addGroup(functionContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        userInformation.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        userInformation.setComponentPopupMenu(jPopupMenu1);
        userInformation.setShowGrid(false);
        userInformation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userInformationMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(userInformation);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(functionContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(functionContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteActionPerformed

    private void filterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterKeyPressed
        // TODO add your handling code here:
//        DefaultTableModel dtm = (DefaultTableModel) userInformation.getModel();
//        final TableRowSorter<TableModel> sorter = new TableRowSorter<> (dtm);
//        userInformation.setRowSorter(sorter);
//        
//        String txt = filter.getText();
//        if (txt.length() == 0){
//            sorter.setRowFilter(null);
//        } else{
//            sorter.setRowFilter(RowFilter.regexFilter(txt, 0, 1)); // 0 laf Username - 1 la 
//        }
    }//GEN-LAST:event_filterKeyPressed

    private void filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterActionPerformed

    private void filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterKeyReleased
        // TODO add your handling code here:
        applyFilter();
    }//GEN-LAST:event_filterKeyReleased

    private void onlineStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineStatusActionPerformed
        // TODO add your handling code here:
        applyFilter();
    }//GEN-LAST:event_onlineStatusActionPerformed

    private void userInformationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInformationMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_userInformationMouseClicked

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // TODO add your handling code here:
        AdminAllUserAddUserForm addUserForm = new AdminAllUserAddUserForm();
        addUserForm.setVisible(true);
        addUserForm.pack();
        addUserForm.setLocationRelativeTo(null);
        addUserForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_addActionPerformed
   
    public static void addNewUser (Object [] data) {
        
        // Giả sử username là phần tử thứ 0 và email là phần tử thứ 7 trong mảng data
        String username = (String) data[0];  // userName
        String fullname = (String) data[1];
        String email = (String) data[5];     // email
        // Sử dụng phương thức trong UserBUS để kiểm tra sự tồn tại của username và email
        UserBUS userBUS = new UserBUS();
        if (userBUS.isUserNameExist(username)) {
            System.out.println("Username already exists.");
            return; // Nếu username đã tồn tại, dừng lại
        }

        if (userBUS.isEmailExist(email)) {
            System.out.println("Email already exists.");
            return; // Nếu email đã tồn tại, dừng lại
        }
        
        if (userBUS.insertUser(username, fullname,email)){
            DefaultTableModel model = (DefaultTableModel) userInformation.getModel();
            model.addRow(data);
        }else {
            System.out.println("Error when addUSer");
        }
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Delete;
    private javax.swing.JButton add;
    private javax.swing.JButton delete;
    private javax.swing.JTextField filter;
    private javax.swing.JMenuItem functionAddUser;
    private javax.swing.JPanel functionContainer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> onlineStatus;
    private static javax.swing.JTable userInformation;
    // End of variables declaration//GEN-END:variables
}
