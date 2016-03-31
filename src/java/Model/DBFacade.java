/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import Control.*;

/**
 * Contains the connection to the connections to the database
 * Uses all of the mappers to retrieve info from the database or uses the mappers
 * to save information to the database. Does not handle SQL statements directly
 * but controls which mapper to use. 
 * @author dennisschmock
 */
public class DBFacade {
    private Connection con;
    private ReportMapper rm;
    private static DBFacade instance;
    public static void main(String[] args) {
        DBFacade facade = getInstance();
        Report r=new Report(1,"03-30-2016","CPHBusiness","Norgaardsvej, Lyngby",2950,10,100.5,"School",241);
        System.out.println(facade.saveNewReport(r));
    }
    
    private DBFacade() {
        rm = new ReportMapper();
        con = DBconnector.getInstance().getConnection();
    }

    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }
    
     public boolean saveNewReport(Report r) {
        return rm.saveNewReport(r, con);
    }
}
