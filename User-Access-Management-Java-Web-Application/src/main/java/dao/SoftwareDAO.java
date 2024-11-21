package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoftwareDAO {

    final String URL = "jdbc:mysql://localhost:3306/user_access_mgmt";  // MySQL connection URL
    final String USERNAME = "root";  // MySQL Username
    final String PASSWORD = "1234";  // MySQL Password

    private Connection connection;

    public SoftwareDAO() {
        try {
            // Initialize your database connection with MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL JDBC Driver
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all softwares from the database
    public List<Software> getAllSoftwares() {
        List<Software> softwares = new ArrayList<>();
        String query = "SELECT * FROM software";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Software software = new Software();
                software.setId(resultSet.getInt("id"));
                software.setName(resultSet.getString("name"));
                software.setDescription(resultSet.getString("description"));
                softwares.add(software);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return softwares;
    }

    // Add a new software to the database
    public void addSoftware(Software software) {
        String query = "INSERT INTO software (name, description) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, software.getName());
            statement.setString(2, software.getDescription());
            statement.executeUpdate();
            System.out.println("Software Added...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a software based on its ID
    public void deleteSoftware(int id) {
        String query = "DELETE FROM software WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Software Deleted...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close the database connection
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
