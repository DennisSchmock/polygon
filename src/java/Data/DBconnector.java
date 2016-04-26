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
 *
 * @author Dennis Schmock
 */
public class DBconnector {

    public static final String driver = "org.mariadb.jdbc.Driver";
    public static final String url = "jdbc:mysql://it-vejlederen.dk:3306/Polygon";
    public static final String dbuser = "polygonuser";
    public static final String pwd = "Ospekos_22";

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

    public static DBconnector getInstance() {
        if (instance == null) {
            instance = new DBconnector();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (!con.isValid(5)){              
                this.con = DriverManager.getConnection(url, dbuser, pwd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBconnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public static void cleanUp(PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error in DB.cleanUp() closing resultSet" + e);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println("Error in DB.cleanUp() closing preparedStatement" + e);
        }
       
    }
}
