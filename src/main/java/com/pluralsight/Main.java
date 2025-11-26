package com.pluralsight;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //======Database Login INFO=========

        String username = "root";
        String password = "Omarxai1$";
        String database = "northwind";

          // JDBC URL tells java how to find the database on this computer
        String databaseUrl = "jdbc:mysql://localhost:3306/" + database;

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");// Loads SQL JDBC driver so java knows how to talk to MYsql

            // Open a connection
            Connection connection = DriverManager.getConnection(databaseUrl, username, password);//attems to login MYsql using the username,password and databaseURL

            while (true) { // main menu loop keep showing unit you exit
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                int option = ConsoleHelper.promptForInt("Select an option");

                switch (option) { // menu selection
                    case 1:
                        displayProducts(connection);
                        break;
                    case 2:
                        displayCustomers(connection);
                        break;
                    case 3:
                        displayCategoriesAndProducts(connection);
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        connection.close();//close the database connection
                        return; //stops the program
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
    }
    public static void displayCustomers(Connection connection) throws SQLException {

        //query selects exact fields from customers
        String query = """
                SELECT ContactName, CompanyName, City, Country, Phone
                FROM customers
                ORDER BY Country
                """;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nCUSTOMERS:");

            while (rs.next()) { //loop through all rows 
                System.out.println(
                        rs.getString("ContactName") + " | " +
                                rs.getString("CompanyName") + " | " +
                                rs.getString("City") + ", " +
                                rs.getString("Country") + " | Phone: " +
                                rs.getString("Phone")
                );
            }
        }
    }
    public static void displayProducts(Connection connection) throws SQLException {
        String query = """
                SELECT productID, productName, unitPrice, unitsInStock
                FROM products
                """;
        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {

            // Display table header
            System.out.printf("| %-6s | %-25s  %-10s  %-10s %n",
                    "ID", "PRODUCT NAME", "UnitPrice", "UnitsInStock");

            // Display table rows
            while (results.next()) {
                int id = results.getInt("productID");
                String name = results.getString("productName");
                if (name.length() > 25) name = name.substring(0, 25);
                double unitPrice = results.getDouble("unitPrice");
                int stock = results.getInt("unitsInStock");
                System.out.printf(" %-6d %-25s  %-10.2f  %-10d %n",
                        id, name, unitPrice, stock);
            }
        }
    }

    public static void displayCategoriesAndProducts(Connection connection) throws SQLException {
        System.out.println("\nCATEGORIES:");

        // 1. show all categories
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID")) {

            while (rs.next()) {
                System.out.println(rs.getInt("CategoryID") + " | " + rs.getString("CategoryName"));
            }
        }
        int categoryId = ConsoleHelper.promptForInt("Enter category ID");

        // 2. show products in that category
        String productQuery = """
                SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                FROM products
                WHERE CategoryID = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(productQuery)) {
            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\nPRODUCTS IN CATEGORY " + categoryId + ":");

                System.out.printf("| %-6s | %-25s | %-10s | %-10s |%n",
                        "ID", "PRODUCT NAME", "UnitPrice", "UnitsInStock");

                while (rs.next()) {
                    int id = rs.getInt("ProductID");
                    String name = rs.getString("ProductName");
                    if (name.length() > 25) name = name.substring(0, 25);
                    double price = rs.getDouble("UnitPrice");
                    int stock = rs.getInt("UnitsInStock");

                    System.out.printf("| %-6d | %-25s | %-10.2f | %-10d |%n",
                            id, name, price, stock);
                }
            }
        }
    }
}






