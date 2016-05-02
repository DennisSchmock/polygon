/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The purpose of this class, is to create a connection to the database This
 * class is written with inspiration from the DatamapperAllTheWay example Notice
 * that we never closes the connection to the DB.
 *
 * @author Dennis Schmock
 */
public class DBconnector {

    private static final String driver = "org.mariadb.jdbc.Driver"; //Use this driver, when connection to our remote database
    private static final String url = "jdbc:mysql://it-vejlederen.dk:3306/Polygon"; //Use this url to connect to remote host
    //private static final String driver = "com.mysql.jdbc.Driver"; //Use this driver if using a Localhost MYSQL database
    //private static final String url = "jdbc:mysql://localhost:3306/Polygon";

    private static final String dbuser = "polygonuser";
    private static final String pwd = "Ospekos_22";

    private Connection con;

    private static DBconnector instance;

    private DBconnector() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, dbuser, pwd);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * The purpose of this method is to return a singleton DB-connector.
     *
     * @return
     */
    public static DBconnector getInstance() {
        if (instance == null) {
            instance = new DBconnector();
        }
        return instance;
    }

    /**
     * The purpose of this method is to return a Database connection. Since the
     * Connection is assigned to a singleton, it checks wether or not the
     * connection is still alive (the DB might reclaim the connection), and
     * returns a new connection if that is the case.
     *
     * @return
     */
    public Connection getConnection() {
        try {
            if (!con.isValid(5)) {
                this.con = DriverManager.getConnection(url, dbuser, pwd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBconnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    /**
     * The purpose of this method, is to close the resultset and the prepared
     * statement After execution. To be run inside mapper methods and such.
     *
     * @param stmt
     * @param rs
     */
    public static void cleanUp(PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error in DBconnector.cleanUp() closing resultSet" + e);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println("Error in DBconnector.cleanUp() closing preparedStatement" + e);
        }

    }
}
