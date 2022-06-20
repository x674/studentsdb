package com.x674;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Scanner;

public class App {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:StudentsDataBase";
    // Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    private static final String createTableQuery = "CREATE TABLE STUDENTS " +
            "(id INTEGER not NULL, " +
            " lastName VARCHAR(255), " +
            " firstName VARCHAR(255), " +
            " middleName VARCHAR(255), " +
            " birthday DATETIME, " +
            " PRIMARY KEY ( id ))";
     private static final String allStrudentsQuery = "select * from students";
    //private static final String addStrudentQuery = "INSERT INTO students " + "VALUES (100, 'Zara', 'Ali', 18)";
    private static final String removeStrudentQuery = "DELETE FROM Registration " + "WHERE id = 101";

    private static void addStudent(Statement statement, int id, String lastName, String firstName, String middleName, String birthday) {
        try {
            String sql =

            "INSERT INTO Registration " + "VALUES("+id+
                    ", "+lastName+"'"+
                    ", '"+firstName+"'"+
                    ", '"+middleName+"'"+
                    birthday+
                    ", '"+lastName+"'"
                    +")";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
            switch (menu()) {
                case 1:
                    addStudent(stmt, 100, "Pupkin", "Vasua", "Vasilewich", "01.01.2000");
                    break;
                case 2:
                    stmt.executeUpdate(removeStrudentQuery);
                    break;
                case 3:
                    ResultSet rs = stmt.executeQuery(allStrudentsQuery);
                    while (rs.next())
                    {
                        System.out.println(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"));
                    }
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    // STEP 4: Clean-up environment
                    stmt.close();
                    conn.close();
                    break;
                default:
                    // The user input an unexpected choice.
            }

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try

    }

    public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Выбирите действие");
        System.out.println("-------------------------\n");
        System.out.println("1 - Добавить студента");
        System.out.println("2 - Удалить студента");
        System.out.println("3 - Вывести список студентов");
        System.out.println("4 - Выход");

        selection = input.nextInt();
        return selection;
    }
}
