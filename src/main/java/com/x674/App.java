package com.x674;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    public static Scanner input = new Scanner(System.in);
    
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

    private static void addStudent(Statement statement, int id, String lastName, String firstName, String middleName,
            String birthday) {
        try {
            String sql = "INSERT INTO STUDENTS " + "VALUES(" + id +
                    ", '" + lastName + "'" +
                    ", '" + firstName + "'" +
                    ", '" + middleName + "'" +
                    ", '" + birthday + "'"
                    + ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void removeStudent(Statement statement, int id) {
        try {
            String sql = "DELETE FROM STUDENTS " + "WHERE ID=" + id;
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
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
            while (true) {
                switch (menu()) {
                    case 1:
                        System.out.println("Введите id студента");
                        int id = input.nextInt();
                        System.out.println("Введите фамилию студента");
                        String lastName = input.next();
                        System.out.println("Введите имя студента");
                        String firstName = input.next();
                        System.out.println("Введите отчество студента");
                        String middleName = input.next();
                        System.out.println("Введите дату рождения студента (2000-12-25)");
                        String birthday = input.next();

                        addStudent(stmt, id, lastName, firstName, middleName, birthday);
                        break;
                    case 2:
                        System.out.println("Введите id студента");
                        int idStudent = input.nextInt();
                        removeStudent(stmt, idStudent);
                        break;
                    case 3:
                        ResultSet rs = stmt.executeQuery(allStrudentsQuery);
                        clearConsole();
                        while (rs.next()) {
                            System.out.println(rs.getString("id") + " " + rs.getString("firstName") + " " +
                                    rs.getString("middleName") + " " + rs.getString("lastName") + " " +
                                    rs.getString("birthday"));
                        }
                        System.out.println("Нажмите любую клавишу чтобы продолжить...");
                        System.in.read();
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        // STEP 4: Clean-up environment
                        stmt.close();
                        conn.close();
                        return;
                    default:

                }
                clearConsole();
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
        }

    }

    public static int menu() {
        int selection;

        System.out.println("Выбирите действие");
        System.out.println("-------------------------\n");
        System.out.println("1 - Добавить студента");
        System.out.println("2 - Удалить студента");
        System.out.println("3 - Вывести список студентов");
        System.out.println("4 - Выход");

        selection = input.nextInt();
        return selection;
    }

    public final static void clearConsole() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }
}