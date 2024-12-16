package com.wavechat.dao;

import com.wavechat.GlobalVariable;
import com.wavechat.dto.GroupChatDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupChatDAO {

    // Hàm lấy list group chat
    public List<GroupChatDTO> getGroupChats(String userID) {
        List<GroupChatDTO> groupChats = new ArrayList<>();

        String query = "SELECT gc.groupID, gc.groupName, gc.createdBy, gc.onlineStatus " +
                       "FROM GroupChat gc " +
                       "JOIN GroupMembers gm ON gm.groupID = gc.groupID " +
                       "WHERE gm.userID = ?";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            return groupChats;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int groupID = resultSet.getInt("groupID");
                String groupName = resultSet.getString("groupName");
                String createdBy = resultSet.getString("createdBy");
                boolean onlineStatus = resultSet.getBoolean("onlineStatus");  

                groupChats.add(new GroupChatDTO(groupID, groupName, createdBy, onlineStatus));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching group chats: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return groupChats;
    }
    
    // Hàm lấy 1 group chat bằng ID
    public GroupChatDTO getGroupChatByID(int groupID) {
        GroupChatDTO groupChat = null;
        String query = "SELECT groupID, groupName, createdBy, onlineStatus " +
                     "FROM GroupChat " +
                     "WHERE groupID = ?";

        DBconnector dbConnector = new DBconnector();  
        Connection connection = dbConnector.getConnection();  
        if (connection == null) {
            return groupChat;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupID);  

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int groupIDFromDB = resultSet.getInt("groupID");
                String groupName = resultSet.getString("groupName");
                String createdBy = resultSet.getString("createdBy");
                boolean onlineStatus = resultSet.getBoolean("onlineStatus");

                groupChat = new GroupChatDTO(groupIDFromDB, groupName, createdBy, onlineStatus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groupChat;  
    }
    
    // Phương thức để lấy ID Group Chat mới (tăng theo ID lớn nhất hiện tại)
    public int generateNewGroupChatID() {
        String query = "SELECT MAX(groupID) FROM GroupChat";
        int newGroupID = 1;  // Nếu bảng GroupChat rỗng thì groupID sẽ bắt đầu từ 1

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return -1;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                newGroupID = resultSet.getInt(1) + 1; // Tăng ID lên 1 so với ID cao nhất
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return newGroupID;
    }

    // Hàm tạo Group Chat mới
    public GroupChatDTO createGroupChat(String groupName) {
        int newGroupID = generateNewGroupChatID();
        String query = "INSERT INTO GroupChat (groupID, groupName, createdBy, createdAt, onlineStatus) " +
                       "VALUES (?, ?, ?, NOW(), TRUE)";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return null; 
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newGroupID);
            preparedStatement.setString(2, groupName); 
            preparedStatement.setString(3, GlobalVariable.getUserID());  // Người tạo là người dùng hiện tại
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return new GroupChatDTO(newGroupID, groupName, GlobalVariable.getUserID(), true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Hàm thêm thành viên vào Group Chat
    public void addMember(String memberID, int groupID, boolean isAdmin) {
        String query = "INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate) VALUES (?, ?, ?, NOW())";
        
        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupID);
            preparedStatement.setString(2, memberID);  
            preparedStatement.setBoolean(3, isAdmin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();  // Đảm bảo đóng kết nối sau khi sử dụng
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}