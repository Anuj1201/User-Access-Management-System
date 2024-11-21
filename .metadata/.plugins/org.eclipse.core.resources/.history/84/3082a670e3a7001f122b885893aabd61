package dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import dao.Software;

public class SoftwareDAO {
	final String URL = "jdbc:oracle:thin:@MSI:1521:orcl";
    final String USERNAME = "system";  //Databse Username
    final String PASSWORD = "Bhagyajyoti768"; //Databse Username

    private Connection connection;

    public SoftwareDAO() {
        try {
            // Initialize your database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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