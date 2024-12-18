package com.wavechat.dao;

import java.sql.*;

public class SpamReportDAO {
    
    // Hàm tạo 1 ID mới
    public int generateSpamReportID() {
        String query = "SELECT MAX(reportID) FROM SpamReport";
        int newReportID = 1; 

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return -1;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                newReportID = resultSet.getInt(1) + 1; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return newReportID;
    }

    // Tạo 1 spam report mới
    public boolean addSpamReport(String reporterID, String reportedID) {
        String query = "INSERT INTO SpamReport (reportID, reporterID, reportedUserId, timeStamp) VALUES (?, ?, ?, NOW())";

        DBconnector dbConnector = new DBconnector();
        Connection connection = dbConnector.getConnection();

        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int newReportID = generateSpamReportID();
            preparedStatement.setInt(1, newReportID);
            preparedStatement.setString(2, reporterID);
            preparedStatement.setString(3, reportedID);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
