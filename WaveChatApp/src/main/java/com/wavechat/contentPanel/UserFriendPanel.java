package com.wavechat.contentPanel;

import com.wavechat.bus.BlockBUS;
import com.wavechat.bus.FriendBUS;
import com.wavechat.component.ButtonEditor;
import com.wavechat.component.ButtonRenderer;
import com.wavechat.dao.UserDAO;
import com.wavechat.dto.FriendDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

public class UserFriendPanel extends javax.swing.JPanel {

    /**
     * Creates new form UserFriendPanel
     */
    public UserFriendPanel() {
        initComponents();
    }

    private void addFriendData(javax.swing.JTable table, List<FriendDTO> friendsList, String userID) {
        // Thông tin các cột
        String[] columnNames = {"FullName", "Username", "Status", "Unfriend", "Block"};

        // Dữ liệu cho bảng (mỗi hàng là một đối tượng FriendDTO)
        Object[][] data = new Object[friendsList.size()][5];  

        // Điền dữ liệu vào bảng
        for (int i = 0; i < friendsList.size(); i++) {
            FriendDTO friend = friendsList.get(i);
            data[i][0] = friend.getFullName();      
            data[i][1] = friend.getUserName();      
            data[i][2] = friend.isOnlineStatus() ? "Online" : "Offline"; 
        }

        // Tạo DefaultTableModel và gán cho bảng
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);

        // Đặt chiều cao cho tất cả các hàng
        table.setRowHeight(30);

        // Không cho phép chọn nhiều cột và hàng
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);

        // Không cho phép edit trên bảng
        table.setDefaultEditor(Object.class, null);

        // Tạo ButtonRenderer và ButtonEditor cho các cột Unfriend và Block
        ButtonRenderer.Unfriend unfriendRenderer = new ButtonRenderer.Unfriend();
        ButtonRenderer.Block blockRenderer = new ButtonRenderer.Block();

        ButtonEditor unfriendEditor = new ButtonEditor(new JCheckBox());
        ButtonEditor blockEditor = new ButtonEditor(new JCheckBox());

        // Đặt hành động cho Unfriend button
        unfriendEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // Lấy row được chọn
                String userName = (String) table.getValueAt(row, 0); // Lấy UserName của hàng hiện tại

                // Giả sử bạn có userID của người đang thực hiện hành động
                String friendUserID = getUserIDByUsername(userName); // Hàm này sẽ lấy userID dựa vào username

                // Gọi hàm xử lý Unfriend từ FriendBUS
                FriendBUS friendBUS = new FriendBUS();
                boolean success = friendBUS.unfriend(userID, friendUserID);
                if (success) {
                    System.out.println("Successfully unfriended " + userName);
                    addFriendData(table, friendsList, userID); // Reload lại bảng sau khi unfriended
                } else {
                    System.out.println("Failed to unfriend " + userName);
                }
            }
        });

        // Đặt hành động cho Block button
        blockEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // Lấy row được chọn
                String userName = (String) table.getValueAt(row, 0); // Lấy UserName của hàng hiện tại

                String blockUserID = getUserIDByUsername(userName); // Hàm này sẽ lấy userID dựa vào username

                // Gọi hàm xử lý Block từ BlockBUS
                FriendBUS friendBUS = new FriendBUS();
                BlockBUS blockBUS = new BlockBUS();

                boolean success = blockBUS.blockUser(userID, blockUserID);
                if (success) {
                    System.out.println("Successfully blocked " + userName);
                    // Gọi hàm xử lý Unfriend từ FriendBUS
                    boolean successUn = friendBUS.unfriend(userID, blockUserID);
                    if (successUn) {
                        System.out.println("Successfully unfriended " + userName);
                        addFriendData(table, friendsList, userID); // Reload lại bảng sau khi block và unfriended
                    } else {
                        System.out.println("Failed to unfriend " + userName);
                    }
                } else {
                    System.out.println("Failed to block " + userName);
                }
            }
        });

        // Gán renderer và editor cho các cột Unfriend và Block
        table.getColumnModel().getColumn(3).setCellRenderer(unfriendRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(blockRenderer);

        table.getColumnModel().getColumn(3).setCellEditor(unfriendEditor);
        table.getColumnModel().getColumn(4).setCellEditor(blockEditor);
    }
    

    // Hàm lấy userID theo username
    private String getUserIDByUsername(String username) {
        UserDAO userDAO = new UserDAO(); 
        return userDAO.getUserIDByUsername(username); 
    }

    private void customizeTableRequest(javax.swing.JTable table) {
        // Đặt chiều cao cho tất cả các hàng
        table.setRowHeight(30); 

        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);

        table.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer.Accept());
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer.Delete());
    }
    
    private void customizeTableOnline(javax.swing.JTable table) {

        // Đặt chiều cao cho tất cả các hàng
        table.setRowHeight(30); 

        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);

        table.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer.Unfriend());
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer.Block());
    }
    
    private void changeModeButton(String mode) {
        if (mode == "All") {
            allButton.setBackground(new java.awt.Color(153, 255, 255));
            allButton.setForeground(new java.awt.Color(0, 0, 0));
            requestButton.setBackground(new java.awt.Color(26, 41, 128));
            requestButton.setForeground(new java.awt.Color(255, 255, 255));
            onlineButton.setBackground(new java.awt.Color(26, 41, 128));
            onlineButton.setForeground(new java.awt.Color(255, 255, 255));
        }
        else if (mode == "Request") {
            allButton.setBackground(new java.awt.Color(26, 41, 128));
            allButton.setForeground(new java.awt.Color(255, 255, 255));
            requestButton.setBackground(new java.awt.Color(153, 255, 255));
            requestButton.setForeground(new java.awt.Color(0, 0, 0));
            onlineButton.setBackground(new java.awt.Color(26, 41, 128));
            onlineButton.setForeground(new java.awt.Color(255, 255, 255));
        }
        else if (mode == "Online") {
            allButton.setBackground(new java.awt.Color(26, 41, 128));
            allButton.setForeground(new java.awt.Color(255, 255, 255));
            requestButton.setBackground(new java.awt.Color(26, 41, 128));
            requestButton.setForeground(new java.awt.Color(255, 255, 255));
            onlineButton.setBackground(new java.awt.Color(153, 255, 255));
            onlineButton.setForeground(new java.awt.Color(0, 0, 0));
        }
    }
    
    // Hàm lọc bạn bè theo từ khóa
    private void filterFriends(String searchQuery, String userID) {
        // Lấy danh sách bạn bè từ database hoặc từ một danh sách có sẵn
        FriendBUS friendBUS = new FriendBUS();
        List<FriendDTO> allFriends = friendBUS.getFriends(userID); // Ví dụ, lấy danh sách bạn bè của người dùng với userID "U001"

        List<FriendDTO> filteredFriends = new ArrayList<>();

        for (FriendDTO friend : allFriends) {
            // Kiểm tra xem tên đầy đủ của bạn bè có chứa từ khóa tìm kiếm không
            if (friend.getFullName().toLowerCase().contains(searchQuery)) {
                filteredFriends.add(friend); // Nếu có, thêm vào danh sách kết quả lọc
            }
        }

        // Cập nhật bảng với danh sách bạn bè đã lọc
        addFriendData(allTable, filteredFriends, userID);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        onlineCard = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        onlineTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        allCard = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        allTable = new javax.swing.JTable();
        allSearch = new javax.swing.JTextField();
        filterAllLabel = new javax.swing.JLabel();
        searchAllButton = new javax.swing.JButton();
        requestCard = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        requestTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        contentContainer = new javax.swing.JPanel();
        navFriendContainer = new javax.swing.JPanel();
        allButton = new javax.swing.JButton();
        requestButton = new javax.swing.JButton();
        onlineButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        onlineTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"User1", "", ""},
                {"User2", "", ""},
                {"User3", "", ""},
                {"User4", "", ""},
                {"User5", null, null},
                {"User6", null, null},
                {"User7", null, null},
                {"User8", null, null},
                {"User9", null, null},
                {"User10", null, null},
                {"User11", null, null},
                {"User12", null, null},
                {"User13", null, null},
                {"User14", null, null},
                {"User15", null, null},
                {"User16", null, null},
                {"User17", null, null},
                {"User18", null, null},
                {"User19", null, null},
                {"User20", null, null}
            },
            new String [] {
                "Username", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        onlineTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(onlineTable);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Filter:");

        javax.swing.GroupLayout onlineCardLayout = new javax.swing.GroupLayout(onlineCard);
        onlineCard.setLayout(onlineCardLayout);
        onlineCardLayout.setHorizontalGroup(
            onlineCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            .addGroup(onlineCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        onlineCardLayout.setVerticalGroup(
            onlineCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, onlineCardLayout.createSequentialGroup()
                .addGroup(onlineCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        allTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        allTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(allTable);

        allSearch.setText("Enter fullname");
        allSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                allSearchKeyPressed(evt);
            }
        });

        filterAllLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        filterAllLabel.setText("Filter:");

        searchAllButton.setBackground(new java.awt.Color(26, 41, 128));
        searchAllButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        searchAllButton.setForeground(new java.awt.Color(255, 255, 255));
        searchAllButton.setText("Search");
        searchAllButton.setToolTipText("");
        searchAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout allCardLayout = new javax.swing.GroupLayout(allCard);
        allCard.setLayout(allCardLayout);
        allCardLayout.setHorizontalGroup(
            allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            .addGroup(allCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterAllLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchAllButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        allCardLayout.setVerticalGroup(
            allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, allCardLayout.createSequentialGroup()
                .addGroup(allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, allCardLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filterAllLabel))
                    .addGroup(allCardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(allSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(searchAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        requestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"User1", "", null},
                {"User2", "", null},
                {"User3", "", null},
                {"User4", "", null},
                {"User5", null, null},
                {"User6", null, null},
                {"User7", null, null},
                {"User8", null, null},
                {"User9", null, null},
                {"User10", null, null},
                {"User11", null, null},
                {"User12", null, null},
                {"User13", null, null},
                {"User14", null, null},
                {"User15", null, null},
                {"User16", null, null},
                {"User17", null, null},
                {"User18", null, null},
                {"User19", null, null},
                {"User20", null, null}
            },
            new String [] {
                "Username", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        requestTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(requestTable);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Filter:");

        javax.swing.GroupLayout requestCardLayout = new javax.swing.GroupLayout(requestCard);
        requestCard.setLayout(requestCardLayout);
        requestCardLayout.setHorizontalGroup(
            requestCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            .addGroup(requestCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        requestCardLayout.setVerticalGroup(
            requestCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestCardLayout.createSequentialGroup()
                .addGroup(requestCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setPreferredSize(new java.awt.Dimension(741, 600));

        contentContainer.setLayout(new java.awt.BorderLayout());

        allButton.setBackground(new java.awt.Color(153, 255, 255));
        allButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        allButton.setText("All");
        allButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allButtonActionPerformed(evt);
            }
        });

        requestButton.setBackground(new java.awt.Color(26, 41, 128));
        requestButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        requestButton.setForeground(new java.awt.Color(255, 255, 255));
        requestButton.setText("Request");
        requestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestButtonActionPerformed(evt);
            }
        });

        onlineButton.setBackground(new java.awt.Color(26, 41, 128));
        onlineButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        onlineButton.setForeground(new java.awt.Color(255, 255, 255));
        onlineButton.setText("Online");
        onlineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navFriendContainerLayout = new javax.swing.GroupLayout(navFriendContainer);
        navFriendContainer.setLayout(navFriendContainerLayout);
        navFriendContainerLayout.setHorizontalGroup(
            navFriendContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navFriendContainerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(allButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(requestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(onlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navFriendContainerLayout.setVerticalGroup(
            navFriendContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navFriendContainerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(navFriendContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(requestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 28)); // NOI18N
        jLabel1.setText("Friend");
        jLabel1.setPreferredSize(new java.awt.Dimension(214, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(navFriendContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navFriendContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void allButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allButtonActionPerformed
        changeModeButton("All");

        String userID = "U001";
        FriendBUS friendBUS = new FriendBUS();
        List<FriendDTO> friendsList = friendBUS.getFriends(userID); // Lọc danh sách bạn bè
        addFriendData(allTable, friendsList, userID); // Gọi hàm addFriendData với danh sách đã lọc

        contentContainer.removeAll();
        contentContainer.add(allCard);
        contentContainer.revalidate();
        contentContainer.repaint();
    }//GEN-LAST:event_allButtonActionPerformed

    private void requestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestButtonActionPerformed
        changeModeButton("Request");

        customizeTableRequest(requestTable);

        contentContainer.removeAll();
        contentContainer.add(requestCard);
        contentContainer.revalidate();
        contentContainer.repaint();
    }//GEN-LAST:event_requestButtonActionPerformed

    private void onlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineButtonActionPerformed
        changeModeButton("Online");

        customizeTableOnline(onlineTable);

        contentContainer.removeAll();
        contentContainer.add(onlineCard);
        contentContainer.revalidate();
        contentContainer.repaint();
    }//GEN-LAST:event_onlineButtonActionPerformed

    private void allSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_allSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchQuery = allSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
            String userID = "U001";
            filterFriends(searchQuery, userID); // Gọi hàm lọc bạn bè
        }
    }//GEN-LAST:event_allSearchKeyPressed

    private void searchAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAllButtonActionPerformed
        String searchQuery = allSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
        String userID = "U001";
        filterFriends(searchQuery, userID); // Gọi hàm lọc bạn bè
    }//GEN-LAST:event_searchAllButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allButton;
    private javax.swing.JPanel allCard;
    private javax.swing.JTextField allSearch;
    private javax.swing.JTable allTable;
    private javax.swing.JPanel contentContainer;
    private javax.swing.JLabel filterAllLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel navFriendContainer;
    private javax.swing.JButton onlineButton;
    private javax.swing.JPanel onlineCard;
    private javax.swing.JTable onlineTable;
    private javax.swing.JButton requestButton;
    private javax.swing.JPanel requestCard;
    private javax.swing.JTable requestTable;
    private javax.swing.JButton searchAllButton;
    // End of variables declaration//GEN-END:variables
}
