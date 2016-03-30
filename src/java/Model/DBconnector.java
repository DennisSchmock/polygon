/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author dennisschmock
 */
public class DBconnector {

    public static final String driver = "org.mariadb.jdbc.Driver";
    public static final String url = "it-vejlederen.dk:3306";
    public static final String dbuser = "polygonuser";
    public static final String pwd = "Ospekos_22";

    private Connection con;

    private static DBconnector instance;

    private DBconnector() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:mysql://it-vejlederen.dk:3306", "polygonuser", "Ospekos_22");

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
        return con;
    }
}
