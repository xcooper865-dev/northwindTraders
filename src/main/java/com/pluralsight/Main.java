package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        // Connection details for the Northwind MySQL database
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password =  "Omarxai1$";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "SELECT ProductName, ProductId, unitPrice, unitsInStock FROM products";// SQL query to get all product names

            try (Statement stmt = conn.createStatement(); // Execute the query and loop through the results
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {   // Display each product name
                    System.out.println(rs.getString("ProductName"));
                    System.out.println(rs.getString("ProductID"));
                    System.out.println(rs.getString("unitPrice"));
                    System.out.println(rs.getString("UnitsInStock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // If something goes wrong, show the error
        }


    }
}
