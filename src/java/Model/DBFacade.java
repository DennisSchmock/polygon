/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the connection to the connections to the database Uses all of the
 * mappers to retrieve info from the database or uses the mappers to save
 * information to the database. Does not handle SQL statements directly but
 * controls which mapper to use.
 *
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
        ArrayList<Contact> listOfContacts = facade.getListOfContacts(1);
        for (Contact c : listOfContacts) {
            System.out.println("\t" + c.getContactID()+ "\t" + c.getName() + "\t" +c.getEmail() + "\t" + c.getTelNum() + "\t" + c.getCustID());
        }
        
        
//        Contact c = new Contact("name","..@....dk","(+45)98564730",2);
//        facade.saveContact(c);

//        Customer customer = new Customer("Polygon", "Dennis Schmock","dennis@schmock.eu", "MyStreet12", 213, 2312, 1111, "Albertslund", "21321311");
//        facade.addCustomer(customer);

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

    public Report saveNewReport(Report r) {
        r=rm.saveNewReport(r, con);
        return r;
    }

    public ReportRoom saveReportRoom(ReportRoom rr) {
        rr=rm.saveReportRoom(rr, con);
        return rr;
    }

    public void saveReportExt(ReportRoomExterior re) {
        rm.saveReportExt(re, con);
    }

    public void saveReportRoomDamage(ReportRoomDamage rrd) {
        rm.saveReportRoomDamage(rrd, con);
    }

    public void saveReportInterior(ReportRoomInterior ri) {
        rm.saveReportInterior(ri, con);
    }

    public void saveReportRoomRec(ReportRoomRecommendation rrr) {
        rm.saveReportRoomRec(rrr, con);
    }
    
     public Report getReport(int id){
        return rm.getReport(id, con);
     }
     
     public ReportRoomExterior getReportExt(int id){
        return rm.getReportExt(id, con);
     }
     
     public ReportRoom getReportRoom(int id){
        return rm.getReportRoom(id, con);
     }
     
     public ReportRoomDamage getReportDamage(int id){
        return rm.getReportDamage(id, con);
     }
     
     public ReportRoomInterior getReportInt(int id){
        return rm.getReportInt(id, con);
     }
     
     public ReportRoomRecommendation getReportRec(int id){
        return rm.getReportRec(id, con);
     }
    
     public ArrayList<ReportRoomExterior> getListOfExt(int id){
         return rm.getListOfExt(id, con);
     }
     
     public ArrayList<ReportRoom> getListOfReportRoom(int id){
         return rm.getListOfReportRoom(id, con);
     }
     
     public ArrayList<ReportRoomDamage> getListOfDamages(int id){
         return rm.getListOfDamages(id, con);
     }
     
     public ArrayList<ReportRoomInterior> getListOfInt(int id){
         return rm.getListOfInt(id, con);
     }
     
     public ArrayList<ReportRoomRecommendation> getListOfRec(int id){
         return rm.getListOfRec(id, con);
     }
     
    public void addCustomer(Customer cus){
          cm.addCustomerToDB(cus,con);
    }
    
    public void saveContact(Contact c){
        cm.saveContact(c, con);
    }
    
    public void saveReportMoist (ReportRoomMoist rrm){
        rm.saveReportMoist(rrm, con);
    
    }

    public ArrayList<Contact> getListOfContacts(int id){
        return cm.getListOfContacts(id, con);
    }

    
    /**
     * Sends the building object to be saved to the mapper
     * @param b A Building object that is to be saved in the database
     */
    public void saveNewBuilding(Building b) {
        bm.saveNewBuildingDB(b, con);
    }

    /**
     * Uses the Building mapper to find the list of buildings in the database
     * @param customerID ID of the customer that is to be loaded 
     * @return An list of buildings related to the customerID
     */
    public List<Building> getListOfbuildingsDB(int customerID) {
       return bm.getListOfBuildingsBM(customerID, con);
    }

    /**
     * Sends the to be updated building object to the right mapper
     * @param updatedBuildObj The object that should be updated in the database
     *
     */
    public void updateBuildingDBFacade(Building updatedBuildObj) {
        bm.updateBuildingBm(updatedBuildObj, con);
    }
}
