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
    private UserMapper um;
    private NewReportMapper nrm;

    public static void main(String[] args) {
        DBFacade facade = getInstance();
        ArrayList<Contact> listOfContacts = facade.getListOfContacts(1);
        for (Contact c : listOfContacts) {
            System.out.println("\t" + c.getContactID() + "\t" + c.getName() + "\t" + c.getEmail() + "\t" + c.getTelNum() + "\t" + c.getCustID());
        }

        System.out.println(facade.validateUser("daeniz", "123"));

//        Contact c = new Contact("name","..@....dk","(+45)98564730",2);
//        facade.saveContact(c);
//        Customer customer = new Customer("Polygon", "Dennis Schmock","dennis@schmock.eu", "MyStreet12", 213, 2312, 1111, "Albertslund", "21321311");
//        facade.addCustomer(customer);
    }

    private DBFacade() {
        rm = new ReportMapper();
        con = DBconnector.getInstance().getConnection();
        //this.con = con;
        cm = new CustomerMapper();
        bm = new BuildingMapper();
        um = new UserMapper();

    }

    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }
    
    public Customer getCustomer(int customerid){
        return cm.getCustomer(customerid,getCon());
    }

    public boolean validateUser(String username, String pwd) {
        return um.validateUser(username, pwd, getCon());
    }

    public Report saveNewReport(Report r) {
        r = rm.saveNewReport(r, getCon());
        return r;
    }

    public ReportRoom saveReportRoom(ReportRoom rr) {
        rr = rm.saveReportRoom(rr, getCon());
        return rr;
    }

    public void saveReportExt(ReportRoomExterior re) {
        rm.saveReportExt(re, getCon());
    }

    public void saveReportRoomDamage(ReportRoomDamage rrd) {
        rm.saveReportRoomDamage(rrd, getCon());
    }

    public void saveReportInterior(ReportRoomInterior ri) {
        rm.saveReportInterior(ri, getCon());
    }

    public void saveReportRoomRec(ReportRoomRecommendation rrr) {
        rm.saveReportRoomRec(rrr, getCon());
    }

    public Report getReport(int id) {
        return rm.getReport(id, getCon());
    }

    public ReportRoomExterior getReportExt(int id) {
        return rm.getReportExt(id, getCon());
    }

    public ReportRoom getReportRoom(int id) {
        return rm.getReportRoom(id, getCon());
    }

    public ReportRoomDamage getReportDamage(int id) {
        return rm.getReportDamage(id, getCon());
    }

    public ReportRoomInterior getReportInt(int id) {
        return rm.getReportInt(id, getCon());
    }

    public ReportRoomRecommendation getReportRec(int id) {
        return rm.getReportRec(id, getCon());
    }

    public ArrayList<ReportRoomExterior> getListOfExt(int id) {
        return rm.getListOfExt(id, getCon());
    }

    public ArrayList<ReportRoom> getListOfReportRoom(int id) {
        return rm.getListOfReportRoom(id, getCon());
    }

    public ArrayList<ReportRoomDamage> getListOfDamages(int id) {
        return rm.getListOfDamages(id, getCon());
    }

    public ArrayList<ReportRoomInterior> getListOfInt(int id) {
        return rm.getListOfInt(id, getCon());
    }

    public ArrayList<ReportRoomRecommendation> getListOfRec(int id) {
        return rm.getListOfRec(id, getCon());
    }

    public void addCustomer(Customer cus) {
        cm.addCustomerToDB(cus, getCon());
    }

    public void saveContact(Contact c) {
        cm.saveContact(c, getCon());
    }
    
    public void saveReportMoist (ReportRoomMoist rrm){
        rm.saveReportMoist(rrm, getCon());
    
    }

    public ArrayList<Contact> getListOfContacts(int id) {
        return cm.getListOfContacts(id, getCon());
    }

    
    /**
     * Sends the building object to be saved to the mapper
     *
     * @param b A Building object that is to be saved in the database
     */
    public void saveNewBuilding(Building b) {
        bm.saveNewBuildingDB(b, con);
        System.out.println("Saved building");
    }

    /**
     * Uses the Building mapper to find the list of buildings in the database
     *
     * @param customerID ID of the customer that is to be loaded
     * @return An list of buildings related to the customerID
     */
    public List<Building> getListOfbuildingsDB(int customerID) {
        return bm.getListOfBuildingsBM(customerID, con);
    }

    /**
     * Sends the to be updated building object to the right mapper
     *
     * @param updatedBuildObj The object that should be updated in the database
     *
     */
    public void updateBuildingDBFacade(Building updatedBuildObj) {
        bm.updateBuildingBm(updatedBuildObj, con);
    }
    
    
    //Sending the report as a whole to DB - new method
    public void newReportToDB(Report R){
        nrm.reportToDataBase(R, con);
    }

    public User loadUser(String username) {
        return um.getUser(username, con);
    }
    
    /**
     * Sends the user object to the right mapper, that creates the user
     * in the Database
     * @param user to be inserted to 
     */
    public void createUserDBFacade(User user){
        um.addUserToDB(user, con);
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
        System.out.println(con);
    }
}
