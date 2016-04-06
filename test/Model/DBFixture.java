/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author dennisschmock
 */
public class DBFixture {

    public static final String driver = "org.mariadb.jdbc.Driver";
    public static final String url = "jdbc:mysql://it-vejlederen.dk:3306/Polytest";
    public static final String dbuser = "polygonuser";
    public static final String pwd = "Ospekos_22";

    private Connection con;

    private static DBFixture instance;

    private DBFixture() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, dbuser, pwd);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static DBFixture getInstance() {
        if (instance == null) {
            instance = new DBFixture();
        }
        return instance;
    }

    public Connection getConnection() {
        return con;
    }
}
