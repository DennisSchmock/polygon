/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;

/**
 * Contains the connection to the connections to the database
 * Uses all of the mappers to retrieve info from the database or uses the mappers
 * to save information to the database. Does not handle SQL statements directly
 * but controls which mapper to use. 
 * @author dennisschmock
 */
public class DBFacade {
    private Connection con;
    private static DBFacade instance;
    public static void main(String[] args) {
        DBFacade facade = getInstance();
        System.out.println(facade);
    }
    
    private DBFacade() {
      
        con = DBconnector.getInstance().getConnection();
    }

    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }
}
