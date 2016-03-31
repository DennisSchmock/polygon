/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.*;
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
    private ReportMapper rm;
    private static DBFacade instance;
    private CustomerMapper cm;
    private BuildingMapper bm;
    
    public static void main(String[] args) {
        DBFacade facade = getInstance();
        ReportRoomRecommendation r = new ReportRoomRecommendation(1,"dkjfhdskj",1);
        System.out.println(facade.saveReportRoomRec(r));
    }
    
    private DBFacade() {
        rm = new ReportMapper();
        con = DBconnector.getInstance().getConnection();
        cm = new CustomerMapper();
        bm = new BuildingMapper();
        
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

    public boolean saveReportRoom(ReportRoom rr){
        return rm.saveReportRoom(rr, con);
    }
    
    public boolean saveReportExt(ReportRoomExterior re) {
        return rm.saveReportExt(re, con);
    }
    
    public boolean saveReportRoomDamage(ReportRoomDamage rrd){
        return rm.saveReportRoomDamage(rrd,con);
    }
    
    public boolean saveReportInterior(ReportRoomInterior ri){
        return rm.saveReportInterior(ri,con);
    }
    
    public boolean saveReportRoomRec(ReportRoomRecommendation rrr){
        return rm.saveReportRoomRec(rrr,con);
    }
    
     public void addCustomer(Customer cus){
          cm.addCustomerToDB(cus,con);
     }

    /**
     * Sends the building object to be saved to the mapper
     * @param b A Building object that is to be saved in the database
     */
    public void saveNewBuilding(Building b) {
        bm.saveNewBuildingDB(b, con);
    }
}
