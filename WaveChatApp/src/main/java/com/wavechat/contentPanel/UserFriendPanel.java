package com.wavechat.contentPanel;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.*;
import com.wavechat.dao.*;
import com.wavechat.dto.*;
import com.wavechat.component.ButtonEditor;
import com.wavechat.component.ButtonRenderer;
import java.awt.Component;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserFriendPanel extends javax.swing.JPanel {
    
    public UserFriendPanel() {
        initComponents();
        
        // Add vào card layout
        contentContainer.add(allCard);          
        contentContainer.add(requestCard);  
        contentContainer.add(onlineCard);           
        
        // Lấy userID
        String userID = GlobalVariable.getUserID();
        
        // Add data vào bảng danh sách bạn bè
        FriendBUS friendBUS = new FriendBUS();
        List<FriendDTO> friendsList = friendBUS.getFriends(userID); // Lọc danh sách bạn bè
        addFriendData(friendsList, userID); // Gọi hàm addFriendData với danh sách đã lọc

        // Add data vào bảng danh sách yêu cầu kết bạn
        FriendRequestBUS requestBUS = new FriendRequestBUS();
        List<FriendRequestDTO> requestList = requestBUS.getPendingRequests(userID); // Lọc danh sách bạn bè
        addFriendRequestData(requestList, userID);
        
        // Add data vào bảng danh sách bạn bè online
        addOnlineFriendData(friendsList, userID); // Gọi hàm addOnlineFriendData với danh sách đã lọc
        
        // Bật mode all
        changeModeButton("All");
    }
    
    // Đổi mode
    private void changeModeButton(String mode) {
        if (mode == "All") {
            allButton.setBackground(new java.awt.Color(153, 255, 255));
            allButton.setForeground(new java.awt.Color(0, 0, 0));
            requestButton.setBackground(new java.awt.Color(26, 41, 128));
            requestButton.setForeground(new java.awt.Color(255, 255, 255));
            onlineButton.setBackground(new java.awt.Color(26, 41, 128));
            onlineButton.setForeground(new java.awt.Color(255, 255, 255));
            
            allCard.setVisible(true);        
            requestCard.setVisible(false);
            onlineCard.setVisible(false);
            
            removePopupMenuItemListeners();
            addPopupMenuListeners(allTable);
        }
        else if (mode == "Request") {
            allButton.setBackground(new java.awt.Color(26, 41, 128));
            allButton.setForeground(new java.awt.Color(255, 255, 255));
            requestButton.setBackground(new java.awt.Color(153, 255, 255));
            requestButton.setForeground(new java.awt.Color(0, 0, 0));
            onlineButton.setBackground(new java.awt.Color(26, 41, 128));
            onlineButton.setForeground(new java.awt.Color(255, 255, 255));
            
            allCard.setVisible(false);        
            requestCard.setVisible(true);
            onlineCard.setVisible(false);
        }
        else if (mode == "Online") {
            allButton.setBackground(new java.awt.Color(26, 41, 128));
            allButton.setForeground(new java.awt.Color(255, 255, 255));
            requestButton.setBackground(new java.awt.Color(26, 41, 128));
            requestButton.setForeground(new java.awt.Color(255, 255, 255));
            onlineButton.setBackground(new java.awt.Color(153, 255, 255));
            onlineButton.setForeground(new java.awt.Color(0, 0, 0));
            
            allCard.setVisible(false);        
            requestCard.setVisible(false);
            onlineCard.setVisible(true);
            
            removePopupMenuItemListeners();
            addPopupMenuListeners(onlineTable);
        }
    }
    
    // Hàm lấy userID theo username
    private String getUserIDByUsername(String username) {
        UserDAO userDAO = new UserDAO(); 
        return userDAO.getUserIDByUsername(username); 
    }

    // Hàm add data vào bảng friend
    private void addFriendData(List<FriendDTO> friendsList, String userID) {
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
        allTable.setModel(model);

        // Đặt chiều cao cho tất cả các hàng
        allTable.setRowHeight(30);

        // Không cho phép chọn nhiều cột và hàng
        allTable.setRowSelectionAllowed(false);
        allTable.setColumnSelectionAllowed(false);

        // Không cho phép edit trên bảng
        allTable.setDefaultEditor(Object.class, null);

        // Tạo ButtonRenderer và ButtonEditor cho các cột Unfriend và Block
        ButtonRenderer.Unfriend unfriendRenderer = new ButtonRenderer.Unfriend();
        ButtonRenderer.Block blockRenderer = new ButtonRenderer.Block();

        ButtonEditor unfriendEditor = new ButtonEditor(new JCheckBox());
        ButtonEditor blockEditor = new ButtonEditor(new JCheckBox());

        // Đặt hành động cho Unfriend button
        unfriendEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = allTable.getSelectedRow(); // Lấy row được chọn
                String userName = (String) allTable.getValueAt(row, 1); // Lấy UserName của hàng hiện tại

                String friendUserID = getUserIDByUsername(userName); // Hàm này sẽ lấy userID dựa vào username

                // Gọi hàm xử lý Unfriend từ FriendBUS
                FriendBUS friendBUS = new FriendBUS();
                boolean success = friendBUS.unfriend(userID, friendUserID);
                System.out.println("S" + userID + " friend " + friendUserID);
                if (success) {
                    System.out.println("Successfully unfriended " + userName);
                    List<FriendDTO> tempFriend = friendBUS.getFriends(userID);
                    addFriendData(tempFriend, userID); // Reload lại bảng all
                    
                    List<FriendDTO> tempOnline = friendBUS.getFriends(userID);
                    addOnlineFriendData(tempOnline, userID); // Reload lại bảng online
                } else {
                    System.out.println("Failed to unfriend " + userName);
                }
            }
        });

        // Đặt hành động cho Block button
        blockEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = allTable.getSelectedRow(); // Lấy row được chọn
                String userName = (String) allTable.getValueAt(row, 1); // Lấy UserName của hàng hiện tại

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
                        List<FriendDTO> tempFriend = friendBUS.getFriends(userID);
                        addFriendData(tempFriend, userID); // Reload lại bảng all

                        List<FriendDTO> tempOnline = friendBUS.getFriends(userID);
                        addOnlineFriendData(tempOnline, userID); // Reload lại bảng online
                    } else {
                        System.out.println("Failed to unfriend " + userName);
                    }
                } else {
                    System.out.println("Failed to block " + userName);
                }
            }
        });

        // Gán renderer và editor cho các cột Unfriend và Block
        allTable.getColumnModel().getColumn(3).setCellRenderer(unfriendRenderer);
        allTable.getColumnModel().getColumn(4).setCellRenderer(blockRenderer);

        allTable.getColumnModel().getColumn(3).setCellEditor(unfriendEditor);
        allTable.getColumnModel().getColumn(4).setCellEditor(blockEditor);
    }

    // Hàm lọc bạn bè theo từ khóa
    private void filterFriends(String searchQuery, String userID) {
        FriendBUS friendBUS = new FriendBUS();
        List<FriendDTO> allFriends = friendBUS.getFriends(userID); 

        List<FriendDTO> filteredFriends = new ArrayList<>();

        for (FriendDTO friend : allFriends) {
            // Kiểm tra xem tên đầy đủ của bạn bè có chứa từ khóa tìm kiếm không
            if (friend.getFullName().toLowerCase().contains(searchQuery)) {
                filteredFriends.add(friend); // Nếu có, thêm vào danh sách kết quả lọc
            }
        }

        // Cập nhật bảng với danh sách bạn bè đã lọc
        addFriendData(filteredFriends, userID);
    }
    
    // Hàm add data vào bảng request
    private void addFriendRequestData(List<FriendRequestDTO> requestList, String userID) {
        // Thông tin các cột
        String[] columnNames = {"FullName", "Username", "Accept", "Delete"};

        // Dữ liệu cho bảng (mỗi hàng là một đối tượng FriendRequestDTO)
        Object[][] data = new Object[requestList.size()][4];  

        // Điền dữ liệu vào bảng
        for (int i = 0; i < requestList.size(); i++) {
            FriendRequestDTO request = requestList.get(i);
            data[i][0] = request.getFullName();      // Full Name
            data[i][1] = request.getUserName();      // User Name
            data[i][2] = "Accept";                   // Cột Accept chứa nút
            data[i][3] = "Delete";                   // Cột Delete chứa nút
        }

        // Tạo DefaultTableModel và gán cho bảng
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        requestTable.setModel(model);

        // Đặt chiều cao cho tất cả các hàng
        requestTable.setRowHeight(30);

        // Không cho phép chọn nhiều cột và hàng
        requestTable.setRowSelectionAllowed(false);
        requestTable.setColumnSelectionAllowed(false);

        // Không cho phép chỉnh sửa bảng
        requestTable.setDefaultEditor(Object.class, null);

        // Tạo ButtonRenderer và ButtonEditor cho các cột Accept và Delete
        ButtonRenderer.Accept acceptRenderer = new ButtonRenderer.Accept();
        ButtonRenderer.Delete deleteRenderer = new ButtonRenderer.Delete();

        ButtonEditor acceptEditor = new ButtonEditor(new JCheckBox());
        ButtonEditor deleteEditor = new ButtonEditor(new JCheckBox());

        // Đặt hành động cho nút Accept
        acceptEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = requestTable.getSelectedRow(); // Lấy row được chọn
                String userName = (String) requestTable.getValueAt(row, 1); // Lấy UserName của người gửi yêu cầu

                // Lấy userID của người gửi yêu cầu kết bạn từ username
                String requesterUserID = getUserIDByUsername(userName); // Hàm lấy userID từ username

                // Gọi hàm xử lý Accept từ FriendRequestBUS (giả sử có lớp này xử lý yêu cầu kết bạn)
                FriendRequestBUS friendRequestBUS = new FriendRequestBUS();
                boolean success = friendRequestBUS.acceptRequest(userID, requesterUserID);
                if (success) {
                    System.out.println("Successfully accepted request from " + userName);
                    List<FriendRequestDTO> tempRequest = friendRequestBUS.getPendingRequests(userID);
                    addFriendRequestData(tempRequest, userID); // Reload lại bảng request
                    
                    FriendBUS friendBUS = new FriendBUS();
                    List<FriendDTO> tempFriend = friendBUS.getFriends(userID);
                    addFriendData(tempFriend, userID); // Reload lại bảng all
                    
                    List<FriendDTO> tempOnline = friendBUS.getFriends(userID);
                    addOnlineFriendData(tempOnline, userID); // Reload lại bảng online
                } else {
                    System.out.println("Failed to accept request from " + userName);
                }
            }
        });

        // Đặt hành động cho nút Delete
        deleteEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = requestTable.getSelectedRow(); // Lấy row được chọn
                String userName = (String) requestTable.getValueAt(row, 1); // Lấy UserName của người gửi yêu cầu

                // Lấy userID của người gửi yêu cầu kết bạn từ username
                String requesterUserID = getUserIDByUsername(userName); // Hàm lấy userID từ username

                // Gọi hàm xử lý Delete từ FriendRequestBUS
                FriendRequestBUS friendRequestBUS = new FriendRequestBUS();
                boolean success = friendRequestBUS.deleteRequest(userID, requesterUserID);
                if (success) {
                    System.out.println("Successfully deleted request from " + userName);
                    List<FriendRequestDTO> tempRequest = friendRequestBUS.getPendingRequests(userID);
                    addFriendRequestData(tempRequest, userID); // Reload lại bảng request
                    
                    FriendBUS friendBUS = new FriendBUS();
                    List<FriendDTO> tempFriend = friendBUS.getFriends(userID);
                    addFriendData(tempFriend, userID); // Reload lại bảng all
                    
                    List<FriendDTO> tempOnline = friendBUS.getFriends(userID);
                    addOnlineFriendData(tempOnline, userID); // Reload lại bảng online
                } else {
                    System.out.println("Failed to delete request from " + userName);
                }
            }
        });

        // Gán renderer và editor cho các cột Accept và Delete
        requestTable.getColumnModel().getColumn(2).setCellRenderer(acceptRenderer);
        requestTable.getColumnModel().getColumn(3).setCellRenderer(deleteRenderer);

        requestTable.getColumnModel().getColumn(2).setCellEditor(acceptEditor);
        requestTable.getColumnModel().getColumn(3).setCellEditor(deleteEditor);
    }

    // Hàm lọc yêu cầu kết bạn theo từ khóa
    private void filterFriendRequests(String searchQuery, String userID) {
        FriendRequestBUS friendRequestBUS = new FriendRequestBUS();
        List<FriendRequestDTO> allRequests = friendRequestBUS.getPendingRequests(userID);
        
        List<FriendRequestDTO> filteredRequests = new ArrayList<>();

        // Duyệt qua tất cả các yêu cầu kết bạn và kiểm tra xem có chứa từ khóa tìm kiếm không
        for (FriendRequestDTO request : allRequests) {
            // Kiểm tra xem tên đầy đủ của người yêu cầu hoặc người nhận có chứa từ khóa tìm kiếm không
            if (request.getFullName().toLowerCase().contains(searchQuery)) {
                System.out.println(request.getFullName().toLowerCase());
                filteredRequests.add(request); // Nếu có, thêm vào danh sách kết quả lọc
            }
        }

        // Cập nhật bảng với danh sách yêu cầu kết bạn đã lọc
        addFriendRequestData(filteredRequests, userID);
    }

    // Hàm add data vào bảng online friend
    private void addOnlineFriendData(List<FriendDTO> friendsList, String userID) {
        // Lọc ra các bạn bè có onlineStatus là true (chỉ lấy những người đang online)
        List<FriendDTO> onlineFriends = new ArrayList<>();
        for (FriendDTO friend : friendsList) {
            if (friend.isOnlineStatus()) {
                onlineFriends.add(friend);
            }
        }
        
        // Thông tin các cột
        String[] columnNames = {"FullName", "Username", "Status", "Unfriend", "Block"};

        // Dữ liệu cho bảng (mỗi hàng là một đối tượng FriendDTO)
        Object[][] data = new Object[onlineFriends.size()][5];  

        // Điền dữ liệu vào bảng
        for (int i = 0; i < onlineFriends.size(); i++) {
            FriendDTO friend = onlineFriends.get(i);
            data[i][0] = friend.getFullName();      
            data[i][1] = friend.getUserName();      
            data[i][2] = friend.isOnlineStatus() ? "Online" : "Offline"; 
        }

        // Tạo DefaultTableModel và gán cho bảng
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        onlineTable.setModel(model);

        // Đặt chiều cao cho tất cả các hàng
        onlineTable.setRowHeight(30);

        // Không cho phép chọn nhiều cột và hàng
        onlineTable.setRowSelectionAllowed(false);
        onlineTable.setColumnSelectionAllowed(false);

        // Không cho phép edit trên bảng
        onlineTable.setDefaultEditor(Object.class, null);

        // Tạo ButtonRenderer và ButtonEditor cho các cột Unfriend và Block
        ButtonRenderer.Unfriend unfriendRenderer = new ButtonRenderer.Unfriend();
        ButtonRenderer.Block blockRenderer = new ButtonRenderer.Block();

        ButtonEditor unfriendEditor = new ButtonEditor(new JCheckBox());
        ButtonEditor blockEditor = new ButtonEditor(new JCheckBox());

        // Đặt hành động cho Unfriend button
        unfriendEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = onlineTable.getSelectedRow(); // Lấy row được chọn
                String userName = (String) onlineTable.getValueAt(row, 1); // Lấy UserName của hàng hiện tại

                String friendUserID = getUserIDByUsername(userName); // Hàm này sẽ lấy userID dựa vào username

                // Gọi hàm xử lý Unfriend từ FriendBUS
                FriendBUS friendBUS = new FriendBUS();
                boolean success = friendBUS.unfriend(userID, friendUserID);
                System.out.println("S" + userID + " friend " + friendUserID);
                if (success) {
                    System.out.println("Successfully unfriended " + userName);
                    List<FriendDTO> tempFriend = friendBUS.getFriends(userID);
                    addFriendData(tempFriend, userID); // Reload lại bảng all

                    List<FriendDTO> tempOnline = friendBUS.getFriends(userID);
                    addOnlineFriendData(tempOnline, userID); // Reload lại bảng online
                } else {
                    System.out.println("Failed to unfriend " + userName);
                }
            }
        });

        // Đặt hành động cho Block button
        blockEditor.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = onlineTable.getSelectedRow(); // Lấy row được chọn
                String userName = (String) onlineTable.getValueAt(row, 1); // Lấy UserName của hàng hiện tại

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
                        List<FriendDTO> tempFriend = friendBUS.getFriends(userID);
                        addFriendData(tempFriend, userID); // Reload lại bảng all

                        List<FriendDTO> tempOnline = friendBUS.getFriends(userID);
                        addOnlineFriendData(tempOnline, userID); // Reload lại bảng online
                    } else {
                        System.out.println("Failed to unfriend " + userName);
                    }
                } else {
                    System.out.println("Failed to block " + userName);
                }
            }
        });

        // Gán renderer và editor cho các cột Unfriend và Block
        onlineTable.getColumnModel().getColumn(3).setCellRenderer(unfriendRenderer);
        onlineTable.getColumnModel().getColumn(4).setCellRenderer(blockRenderer);

        onlineTable.getColumnModel().getColumn(3).setCellEditor(unfriendEditor);
        onlineTable.getColumnModel().getColumn(4).setCellEditor(blockEditor);
    }

    // Hàm lọc bạn bè online theo từ khóa
    private void filterOnlineFriends(String searchQuery, String userID) {
        FriendBUS friendBUS = new FriendBUS();
        List<FriendDTO> allFriends = friendBUS.getFriends(userID); 
        
        List<FriendDTO> filteredFriends = new ArrayList<>();

        for (FriendDTO friend : allFriends) {
            // Kiểm tra xem tên đầy đủ của bạn bè có chứa từ khóa tìm kiếm không
            if (friend.getFullName().toLowerCase().contains(searchQuery)) {
                System.out.println("friend name: " + friend.getFullName() + " query: " + searchQuery);
                filteredFriends.add(friend); // Nếu có, thêm vào danh sách kết quả lọc
            }
        }

        // Cập nhật bảng với danh sách bạn bè đã lọc
        addOnlineFriendData(filteredFriends, userID);
    }
    
    // Tạo phương thức riêng để gắn sự kiện cho các mục trong PopupMenu
    private void addPopupMenuListeners(JTable curTable) {
        // Sự kiện cho "Chat"
        chatMenuItem.addActionListener(e -> {
            if (curTable != null) {
                int row = curTable.getSelectedRow();
                if (row >= 0) {
                    String userName = (String) curTable.getValueAt(row, 1); // Lấy username của dòng được chọn
                    String friendUserID = getUserIDByUsername(userName); // Hàm lấy userID từ username

                    JOptionPane.showMessageDialog(this, "Choosing: " + friendUserID + " " + userName, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Sự kiện cho "Create group"
        createGroupMenuItem.addActionListener(e -> {
            if (curTable != null) {
                int row = curTable.getSelectedRow();
                if (row >= 0) {
                    String userName = (String) curTable.getValueAt(row, 1); // Lấy username của dòng được chọn
                    String friendUserID = getUserIDByUsername(userName); // Hàm lấy userID từ username

                    JOptionPane.showMessageDialog(this, "Choosing: " + friendUserID + " " + userName, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Hàm mở popupmenu
    private void showPopup(MouseEvent e, JTable curTable) {
        int row = curTable.rowAtPoint(e.getPoint()); // Lấy dòng của chuột
        if (row >= 0 && row < curTable.getRowCount()) {
            curTable.setRowSelectionInterval(row, row); // Chọn dòng khi nhấn chuột phải
        } else {
            curTable.clearSelection(); // Nếu không có dòng nào, xóa lựa chọn
        }


        friendPopupMenu.show(e.getComponent(), e.getX(), e.getY()); // Hiển thị menu pop-up
    }
    
    private void removePopupMenuItemListeners() {
        for (Component comp : friendPopupMenu.getComponents()) {
            if (comp instanceof JMenuItem) {
                JMenuItem menuItem = (JMenuItem) comp;

//                // Loại bỏ tất cả MouseListener
//                MouseListener[] mouseListeners = menuItem.getMouseListeners();
//                for (MouseListener listener : mouseListeners) {
//                    menuItem.removeMouseListener(listener);
//                }

                // Loại bỏ tất cả ActionListener (nếu có)
                ActionListener[] actionListeners = menuItem.getActionListeners();
                for (ActionListener listener : actionListeners) {
                    menuItem.removeActionListener(listener);
                }
            }
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

        onlineCard = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        onlineTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        filterAllLabel1 = new javax.swing.JLabel();
        onlineSearch = new javax.swing.JTextField();
        searchOnlineButton = new javax.swing.JButton();
        allCard = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        allTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        filterAllLabel = new javax.swing.JLabel();
        allSearch = new javax.swing.JTextField();
        searchAllButton = new javax.swing.JButton();
        requestCard = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        requestTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        filterRequestLabel = new javax.swing.JLabel();
        requestSearch = new javax.swing.JTextField();
        searchRequestButton = new javax.swing.JButton();
        friendPopupMenu = new javax.swing.JPopupMenu();
        chatMenuItem = new javax.swing.JMenuItem();
        createGroupMenuItem = new javax.swing.JMenuItem();
        contentContainer = new javax.swing.JPanel();
        navFriendContainer = new javax.swing.JPanel();
        allButton = new javax.swing.JButton();
        requestButton = new javax.swing.JButton();
        onlineButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        onlineTable.setModel(new javax.swing.table.DefaultTableModel(
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
        onlineTable.getTableHeader().setReorderingAllowed(false);
        onlineTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onlineTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(onlineTable);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        filterAllLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        filterAllLabel1.setText("Filter:   ");
        jPanel3.add(filterAllLabel1);

        onlineSearch.setPreferredSize(new java.awt.Dimension(200, 32));
        onlineSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                onlineSearchKeyPressed(evt);
            }
        });
        jPanel3.add(onlineSearch);

        searchOnlineButton.setBackground(new java.awt.Color(26, 41, 128));
        searchOnlineButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        searchOnlineButton.setForeground(new java.awt.Color(255, 255, 255));
        searchOnlineButton.setText("Search");
        searchOnlineButton.setToolTipText("");
        searchOnlineButton.setPreferredSize(new java.awt.Dimension(80, 32));
        searchOnlineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchOnlineButtonActionPerformed(evt);
            }
        });
        jPanel3.add(searchOnlineButton);

        javax.swing.GroupLayout onlineCardLayout = new javax.swing.GroupLayout(onlineCard);
        onlineCard.setLayout(onlineCardLayout);
        onlineCardLayout.setHorizontalGroup(
            onlineCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(onlineCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(onlineCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(onlineCardLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE))
                .addContainerGap())
        );
        onlineCardLayout.setVerticalGroup(
            onlineCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(onlineCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addContainerGap())
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
        allTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(allTable);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        filterAllLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        filterAllLabel.setText("Filter:   ");
        jPanel1.add(filterAllLabel);

        allSearch.setPreferredSize(new java.awt.Dimension(200, 32));
        allSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                allSearchKeyPressed(evt);
            }
        });
        jPanel1.add(allSearch);

        searchAllButton.setBackground(new java.awt.Color(26, 41, 128));
        searchAllButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        searchAllButton.setForeground(new java.awt.Color(255, 255, 255));
        searchAllButton.setText("Search");
        searchAllButton.setToolTipText("");
        searchAllButton.setPreferredSize(new java.awt.Dimension(80, 32));
        searchAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAllButtonActionPerformed(evt);
            }
        });
        jPanel1.add(searchAllButton);

        javax.swing.GroupLayout allCardLayout = new javax.swing.GroupLayout(allCard);
        allCard.setLayout(allCardLayout);
        allCardLayout.setHorizontalGroup(
            allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(allCardLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE))
                .addContainerGap())
        );
        allCardLayout.setVerticalGroup(
            allCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap())
        );

        requestTable.setModel(new javax.swing.table.DefaultTableModel(
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
        requestTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(requestTable);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        filterRequestLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        filterRequestLabel.setText("Filter:   ");
        jPanel2.add(filterRequestLabel);

        requestSearch.setPreferredSize(new java.awt.Dimension(200, 32));
        requestSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                requestSearchKeyPressed(evt);
            }
        });
        jPanel2.add(requestSearch);

        searchRequestButton.setBackground(new java.awt.Color(26, 41, 128));
        searchRequestButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        searchRequestButton.setForeground(new java.awt.Color(255, 255, 255));
        searchRequestButton.setText("Search");
        searchRequestButton.setPreferredSize(new java.awt.Dimension(80, 32));
        searchRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchRequestButtonActionPerformed(evt);
            }
        });
        jPanel2.add(searchRequestButton);

        javax.swing.GroupLayout requestCardLayout = new javax.swing.GroupLayout(requestCard);
        requestCard.setLayout(requestCardLayout);
        requestCardLayout.setHorizontalGroup(
            requestCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(requestCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(requestCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                    .addGroup(requestCardLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        requestCardLayout.setVerticalGroup(
            requestCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(requestCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );

        chatMenuItem.setText("Chat");
        friendPopupMenu.add(chatMenuItem);

        createGroupMenuItem.setText("Create group");
        friendPopupMenu.add(createGroupMenuItem);

        setPreferredSize(new java.awt.Dimension(741, 600));

        contentContainer.setBackground(new java.awt.Color(255, 255, 255));
        contentContainer.setLayout(new java.awt.CardLayout());

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(onlineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(navFriendContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navFriendContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void allButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allButtonActionPerformed
        changeModeButton("All");
    }//GEN-LAST:event_allButtonActionPerformed

    private void requestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestButtonActionPerformed
        changeModeButton("Request");
    }//GEN-LAST:event_requestButtonActionPerformed

    private void onlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineButtonActionPerformed
        changeModeButton("Online");
    }//GEN-LAST:event_onlineButtonActionPerformed

    private void allSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_allSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchQuery = allSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
            String userID = GlobalVariable.getUserID();
            filterFriends(searchQuery, userID); // Gọi hàm lọc bạn bè
        }
    }//GEN-LAST:event_allSearchKeyPressed

    private void searchAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAllButtonActionPerformed
        String searchQuery = allSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
        String userID = GlobalVariable.getUserID();
        filterFriends(searchQuery, userID); // Gọi hàm lọc bạn bè
    }//GEN-LAST:event_searchAllButtonActionPerformed

    private void searchRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchRequestButtonActionPerformed
        String searchQuery = requestSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
        String userID = GlobalVariable.getUserID();
        filterFriendRequests(searchQuery, userID); // Gọi hàm lọc yêu cầu kết bạn
    }//GEN-LAST:event_searchRequestButtonActionPerformed

    private void requestSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_requestSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchQuery = requestSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
            String userID = GlobalVariable.getUserID();
            filterFriendRequests(searchQuery, userID); // Gọi hàm lọc yêu cầu kết bạn
        }
    }//GEN-LAST:event_requestSearchKeyPressed

    private void onlineSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onlineSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchQuery = onlineSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
            String userID = GlobalVariable.getUserID();
            filterOnlineFriends(searchQuery, userID); // Gọi hàm lọc bạn bè online
        }
    }//GEN-LAST:event_onlineSearchKeyPressed

    private void searchOnlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchOnlineButtonActionPerformed
        String searchQuery = onlineSearch.getText().toLowerCase(); // Lấy nội dung từ JTextField và chuyển thành chữ thường
        String userID = GlobalVariable.getUserID();
        filterOnlineFriends(searchQuery, userID); // Gọi hàm lọc bạn bè online
    }//GEN-LAST:event_searchOnlineButtonActionPerformed

    private void onlineTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onlineTableMouseClicked
        showPopup(evt, onlineTable);
    }//GEN-LAST:event_onlineTableMouseClicked

    private void allTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allTableMouseClicked
        showPopup(evt, allTable);
    }//GEN-LAST:event_allTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allButton;
    private javax.swing.JPanel allCard;
    private javax.swing.JTextField allSearch;
    private javax.swing.JTable allTable;
    private javax.swing.JMenuItem chatMenuItem;
    private javax.swing.JPanel contentContainer;
    private javax.swing.JMenuItem createGroupMenuItem;
    private javax.swing.JLabel filterAllLabel;
    private javax.swing.JLabel filterAllLabel1;
    private javax.swing.JLabel filterRequestLabel;
    private javax.swing.JPopupMenu friendPopupMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel navFriendContainer;
    private javax.swing.JButton onlineButton;
    private javax.swing.JPanel onlineCard;
    private javax.swing.JTextField onlineSearch;
    private javax.swing.JTable onlineTable;
    private javax.swing.JButton requestButton;
    private javax.swing.JPanel requestCard;
    private javax.swing.JTextField requestSearch;
    private javax.swing.JTable requestTable;
    private javax.swing.JButton searchAllButton;
    private javax.swing.JButton searchOnlineButton;
    private javax.swing.JButton searchRequestButton;
    // End of variables declaration//GEN-END:variables
}