/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.wavechat.form;
import com.wavechat.bus.BlockBUS;
import com.wavechat.bus.FriendBUS;
import com.wavechat.component.ButtonEditor;
import com.wavechat.component.ButtonRenderer;
import com.wavechat.dao.UserDAO;
import com.wavechat.dto.FriendDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class UserFriend extends javax.swing.JFrame {

    /**
     * Creates new form UserFriend
     */
    public UserFriend() {
        initComponents();
        customizeTableAll(allTable);        
        customizeTableRequest(requestTable);        
        customizeTableOnline(onlineTable);
    }
    
    private void customizeTableAll(javax.swing.JTable table) {
        // Ví dụ: danh sách dữ liệu
        FriendBUS friendBUS = new FriendBUS();
        List<FriendDTO> friendsList = friendBUS.getFriends("U0001"); // Ví dụ userID là "U0001"

        // Cấu trúc cột cho bảng
        String[] columnNames = {"Username", "Status", "Unfriend", "Block"};

        // Dữ liệu cho bảng (mỗi hàng là một đối tượng FriendDTO)
        Object[][] data = new Object[friendsList.size()][4];  // 4 cột: UserName, OnlineStatus, Unfriend, Block

        // Điền dữ liệu vào bảng
        for (int i = 0; i < friendsList.size(); i++) {
            FriendDTO friend = friendsList.get(i);
            data[i][0] = friend.getUserName();      // User Name
            data[i][1] = friend.isOnlineStatus() ? "Online" : "Offline"; // Online Status
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
                String currentUserID = "U0001"; // ID của người dùng hiện tại
                String friendUserID = getUserIDByUsername(userName); // Hàm này sẽ lấy userID dựa vào username

                // Gọi hàm xử lý Unfriend từ FriendBUS
                boolean success = friendBUS.unfriend(currentUserID, friendUserID);
                if (success) {
                    System.out.println("Successfully unfriended " + userName);
                } else {
                    System.out.println("Failed to unfriend " + userName);
                }
            }
        });

        BlockBUS blockBUS = new BlockBUS();
        // Đặt hành động cho Block button
        blockEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // Lấy row được chọn
                String userName = (String) table.getValueAt(row, 0); // Lấy UserName của hàng hiện tại

                // Giả sử bạn có userID của người đang thực hiện hành động
                String currentUserID = "U0001"; // ID của người dùng hiện tại
                String blockUserID = getUserIDByUsername(userName); // Hàm này sẽ lấy userID dựa vào username

                // Gọi hàm xử lý Block từ BlockBUS
                boolean success = blockBUS.blockUser(currentUserID, blockUserID);
                if (success) {
                    System.out.println("Successfully blocked " + userName);
                } else {
                    System.out.println("Failed to block " + userName);
                }
            }
        });

        // Gán renderer và editor cho các cột Unfriend và Block
        table.getColumnModel().getColumn(2).setCellRenderer(unfriendRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(blockRenderer);

        table.getColumnModel().getColumn(2).setCellEditor(unfriendEditor);
        table.getColumnModel().getColumn(3).setCellEditor(blockEditor);
    }
    
    // Hàm giả lập để lấy userID dựa trên username (Bạn cần cài đặt trong UserDAO)
    private String getUserIDByUsername(String username) {
        UserDAO userDAO = new UserDAO(); // DAO để lấy dữ liệu người dùng
        return userDAO.getUserIDByUsername(username); // Hàm truy vấn userID dựa trên username
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        onlineFriendMenu = new javax.swing.JPopupMenu();
        chatMenuItem = new javax.swing.JMenuItem();
        createGroupMenuItem = new javax.swing.JMenuItem();
        onlineCard = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        onlineTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        allCard = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        allTable = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        requestCard = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        requestTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        container = new javax.swing.JPanel();
        navBarContainer = new javax.swing.JPanel();
        logoContainer = new javax.swing.JLabel();
        profileContainer = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        chatButton = new javax.swing.JButton();
        chatButton1 = new javax.swing.JButton();
        chatButton2 = new javax.swing.JButton();
        navChatContainer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        navFriendContainer = new javax.swing.JPanel();
        allButton = new javax.swing.JButton();
        requestButton = new javax.swing.JButton();
        blockButton = new javax.swing.JButton();
        contentContainer = new javax.swing.JPanel();

        chatMenuItem.setText("Chat");
        onlineFriendMenu.add(chatMenuItem);

        createGroupMenuItem.setText("Create Group");
        onlineFriendMenu.add(createGroupMenuItem);

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
        onlineTable.setComponentPopupMenu(onlineFriendMenu);
        onlineTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(onlineTable);
        if (onlineTable.getColumnModel().getColumnCount() > 0) {
            onlineTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            onlineTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

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
                {"User1",  new Boolean(true), "", ""},
                {"User2",  new Boolean(true), "", ""},
                {"User3", null, "", ""},
                {"User4", null, "", ""},
                {"User5", null, null, null},
                {"User6",  new Boolean(true), null, null},
                {"User7",  new Boolean(true), null, null},
                {"User8",  new Boolean(false), null, null},
                {"User9",  new Boolean(true), null, null},
                {"User10", null, null, null},
                {"User11", null, null, null},
                {"User12",  new Boolean(true), null, null},
                {"User13",  new Boolean(true), null, null},
                {"User14", null, null, null},
                {"User15", null, null, null},
                {"User16",  new Boolean(true), null, null},
                {"User17",  new Boolean(true), null, null},
                {"User18", null, null, null},
                {"User19", null, null, null},
                {"User20",  new Boolean(true), null, null}
            },
            new String [] {
                "Username", "Online", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        allTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(allTable);
        if (allTable.getColumnModel().getColumnCount() > 0) {
            allTable.getColumnModel().getColumn(0).setResizable(false);
            allTable.getColumnModel().getColumn(1).setResizable(false);
            allTable.getColumnModel().getColumn(2).setResizable(false);
            allTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            allTable.getColumnModel().getColumn(3).setResizable(false);
            allTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Filter:");

        javax.swing.GroupLayout allCardLayout = new javax.swing.GroupLayout(allCard);
        allCard.setLayout(allCardLayout);
        allCardLayout.setHorizontalGroup(
            allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            .addGroup(allCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        allCardLayout.setVerticalGroup(
            allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, allCardLayout.createSequentialGroup()
                .addGroup(allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel2))
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
        if (requestTable.getColumnModel().getColumnCount() > 0) {
            requestTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wave - Friend");

        container.setBackground(new java.awt.Color(246, 246, 246));
        container.setBorder(new javax.swing.border.MatteBorder(null));
        container.setLayout(new java.awt.GridBagLayout());

        navBarContainer.setPreferredSize(new java.awt.Dimension(188, 600));

        logoContainer.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        logoContainer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoSmall.png"))); // NOI18N
        logoContainer.setText("Wave");
        logoContainer.setPreferredSize(new java.awt.Dimension(165, 40));

        profileContainer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jButton6.setBackground(new java.awt.Color(26, 41, 128));
        jButton6.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile_white.png"))); // NOI18N
        jButton6.setText("Profile");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.setIconTextGap(8);
        jButton6.setPreferredSize(new java.awt.Dimension(125, 40));
        profileContainer.add(jButton6);

        jButton5.setBackground(new java.awt.Color(26, 41, 128));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        jButton5.setPreferredSize(new java.awt.Dimension(40, 40));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        profileContainer.add(jButton5);

        chatButton.setBackground(new java.awt.Color(26, 41, 128));
        chatButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        chatButton.setForeground(new java.awt.Color(255, 255, 255));
        chatButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat_white.png"))); // NOI18N
        chatButton.setText("Chat");
        chatButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chatButton.setIconTextGap(12);
        chatButton.setPreferredSize(new java.awt.Dimension(165, 40));

        chatButton1.setBackground(new java.awt.Color(153, 255, 255));
        chatButton1.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        chatButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/friend_black.png"))); // NOI18N
        chatButton1.setText("Friend");
        chatButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chatButton1.setIconTextGap(12);
        chatButton1.setPreferredSize(new java.awt.Dimension(165, 40));
        chatButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatButton1ActionPerformed(evt);
            }
        });

        chatButton2.setBackground(new java.awt.Color(26, 41, 128));
        chatButton2.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        chatButton2.setForeground(new java.awt.Color(255, 255, 255));
        chatButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_white.png"))); // NOI18N
        chatButton2.setText("Find Friend");
        chatButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chatButton2.setIconTextGap(12);
        chatButton2.setPreferredSize(new java.awt.Dimension(165, 40));

        javax.swing.GroupLayout navBarContainerLayout = new javax.swing.GroupLayout(navBarContainer);
        navBarContainer.setLayout(navBarContainerLayout);
        navBarContainerLayout.setHorizontalGroup(
            navBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navBarContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chatButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chatButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        navBarContainerLayout.setVerticalGroup(
            navBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navBarContainerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(logoContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chatButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chatButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chatButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(profileContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        container.add(navBarContainer, gridBagConstraints);

        navChatContainer.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        navChatContainer.setPreferredSize(new java.awt.Dimension(741, 600));

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 28)); // NOI18N
        jLabel1.setText("Friend");
        jLabel1.setPreferredSize(new java.awt.Dimension(214, 32));

        allButton.setBackground(new java.awt.Color(204, 204, 204));
        allButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        allButton.setText("All");
        allButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allButtonActionPerformed(evt);
            }
        });

        requestButton.setBackground(new java.awt.Color(204, 204, 204));
        requestButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        requestButton.setText("Request");
        requestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestButtonActionPerformed(evt);
            }
        });

        blockButton.setBackground(new java.awt.Color(204, 204, 204));
        blockButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        blockButton.setText("Block");
        blockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navFriendContainerLayout = new javax.swing.GroupLayout(navFriendContainer);
        navFriendContainer.setLayout(navFriendContainerLayout);
        navFriendContainerLayout.setHorizontalGroup(
            navFriendContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navFriendContainerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(allButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(requestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(blockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        navFriendContainerLayout.setVerticalGroup(
            navFriendContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navFriendContainerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(navFriendContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(allButton, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(requestButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(blockButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        contentContainer.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout navChatContainerLayout = new javax.swing.GroupLayout(navChatContainer);
        navChatContainer.setLayout(navChatContainerLayout);
        navChatContainerLayout.setHorizontalGroup(
            navChatContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navChatContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navChatContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, navChatContainerLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(navFriendContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        navChatContainerLayout.setVerticalGroup(
            navChatContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navChatContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navFriendContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        container.add(navChatContainer, gridBagConstraints);

        getContentPane().add(container, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void chatButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatButton1ActionPerformed

    private void allButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allButtonActionPerformed
        contentContainer.removeAll();
        contentContainer.add(allCard);
        contentContainer.revalidate();
        contentContainer.repaint();
    }//GEN-LAST:event_allButtonActionPerformed

    private void requestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestButtonActionPerformed
        contentContainer.removeAll();
        contentContainer.add(requestCard);
        contentContainer.revalidate();
        contentContainer.repaint();
    }//GEN-LAST:event_requestButtonActionPerformed

    private void blockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockButtonActionPerformed
        contentContainer.removeAll();
        contentContainer.add(onlineCard);
        contentContainer.revalidate();
        contentContainer.repaint();
    }//GEN-LAST:event_blockButtonActionPerformed

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
            java.util.logging.Logger.getLogger(UserFriend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFriend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFriend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFriend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserFriend().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allButton;
    private javax.swing.JPanel allCard;
    private javax.swing.JTable allTable;
    private javax.swing.JButton blockButton;
    private javax.swing.JButton chatButton;
    private javax.swing.JButton chatButton1;
    private javax.swing.JButton chatButton2;
    private javax.swing.JMenuItem chatMenuItem;
    private javax.swing.JPanel container;
    private javax.swing.JPanel contentContainer;
    private javax.swing.JMenuItem createGroupMenuItem;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel logoContainer;
    private javax.swing.JPanel navBarContainer;
    private javax.swing.JPanel navChatContainer;
    private javax.swing.JPanel navFriendContainer;
    private javax.swing.JPanel onlineCard;
    private javax.swing.JPopupMenu onlineFriendMenu;
    private javax.swing.JTable onlineTable;
    private javax.swing.JPanel profileContainer;
    private javax.swing.JButton requestButton;
    private javax.swing.JPanel requestCard;
    private javax.swing.JTable requestTable;
    // End of variables declaration//GEN-END:variables
}
