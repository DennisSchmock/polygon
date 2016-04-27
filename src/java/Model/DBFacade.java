/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.*;
import Domain.Exceptions.PolygonException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
    private static DBFacade instance;
    private CustomerMapper cm;
    private BuildingMapper bm;
    private UserMapper um;
    private ReportMapper nrm;
    private OrderMapper om;

    public static void main(String[] args) {
        DBFacade facade = getInstance();
//        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//        String stringStat = "test";
//        Order orderWrongStat = new Order(date,"Check-up Building", "for annual inspection",stringStat,1,1);
//        System.out.println(facade.addNewOrder(orderWrongStat));
//        String username = "daeniz";
//        Customer c = facade.getCustomerAfterLogIn(username);
//        System.out.println("c" + c.getCustomerId());
//        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//        Order o = new Order(date,"check-up","inspection",1,1,2);
//        facade.addNewOrder(o);
        
//        facade.deleteBuilding(22);
//        BuildingRoom newRoom = new BuildingRoom("Kitchen",1);
//        facade.saveBuildingRoom(newRoom);
//        ArrayList <BuildingRoom> rl = facade.getRoomList(1);
//        for (BuildingRoom br : rl) {
//            System.out.println("floor:" + br.getRoomName());
//        }
//        Report report = facade.getSingleReport(1);
//        for (ReportFloor reportFloor : report.getReportFloors()) {
//            System.out.println("****Floor number: " + reportFloor.getFloorId() + " " + reportFloor.getFloorNumber());
//            for (ReportRoom reportRoom : reportFloor.getReportRooms()) {
//                System.out.println("***Roomname: " + reportRoom.getRoomName());
//                for (ReportRoomInterior reportRoomInterior : reportRoom.getListOfInt()) {
//                    System.out.println("  InteriorName: " + reportRoomInterior.getRepRoomIntName());
//                    System.out.println("    InteriorRemark: " + reportRoomInterior.getRemark());
//                    
//                }
//                
//            }
//        }
//       System.out.println( "Numbers of floors in building" + report.getReportFloors().size());
        

    }

    private DBFacade() {
        con = DBconnector.getInstance().getConnection();
        //this.con = con;
        cm = new CustomerMapper();
        bm = new BuildingMapper();
        um = new UserMapper();
        nrm = new ReportMapper();
        om = new OrderMapper();

    }

    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }

    public Customer getCustomer(int customerid) {
        return cm.getCustomer(customerid, getCon());
    }

    public boolean validateUser(String username, String pwd) {
        return um.validateUser(username, pwd, getCon());
    }

   

   
    public void addCustomer(Customer cus) {
        cm.addCustomerToDB(cus, getCon());
    }

    public void saveContact(Contact c) {
        cm.saveContact(c, getCon());
    }

    public Contact getContact(int custID) {
        return cm.getContact(custID, con);
    }

    

    public ArrayList<Contact> getListOfContacts(int id) {
        return cm.getListOfContacts(id, getCon());
    }

    /**
     * Sends the building object to be saved to the mapper
     *
     * @param b A Building object that is to be saved in the database
     * @return The created building with it's ID set
     */
    public Building saveNewBuilding(Building b) throws PolygonException {
        b=bm.saveNewBuildingDB(b, con);
        System.out.println("Saved building");
        return b;
    }
    
    public String saveBuildingPic(int buildId, String filename) throws PolygonException {
        System.out.println("Saving buildingPic db-facade");
        return bm.saveBuildingPic(buildId, filename, con);
        
    }

    /**
     * Uses the Building mapper to find the list of buildings in the database
     *
     * @param customerID ID of the customer that is to be loaded
     * @return An list of buildings related to the customerID
     */
    public List<Building> getListOfbuildingsDB(int customerID) throws PolygonException {
        return bm.getListOfBuildingsBM(customerID, con);
    }

    /**
     * Sends the to be updated building object to the right mapper
     *
     * @param updatedBuildObj The object that should be updated in the database
     *
     */
    public void updateBuildingDBFacade(Building updatedBuildObj) throws PolygonException {
        bm.updateBuildingBm(updatedBuildObj, con);
    }

    //Sending the report as a whole to DB - new method
    public void newReportToDB(Report R) throws PolygonException {
            nrm.reportToDataBase(R, con);
    }

    public User loadUser(String username) {
        return um.getUser(username, con);
    }

    /**
     * Sends the user object to the right mapper, that creates the user in the
     * Database
     *
     * @param user to be inserted to
     */
    public void createUserDBFacade(User user) {
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

    /**
     * Creates the tuble in the database for a Report.
     * @param report Report to be saved in the database
     */
    public int reportToDataBase(Report report) throws PolygonException {
           return nrm.reportToDataBase(report, con);
    }

    public ArrayList<Report> getListOfReports(int buildingId) throws PolygonException {
        return nrm.getAllReportsBuilding(buildingId, con);
    }
    
    /**
     * Returns a single report based on the reportId
     * @param reportId
     * @return a report object.
     */
    public Report getSingleReport(int reportId) throws PolygonException {
        return nrm.getSingleReport(reportId, getCon());
    }

    /**
     * Sends the request to the right mapper.
     * @return Returs an list of All customers in the database
     */
    public List<Customer> getAllCustomers() {
        return cm.getAllCustomersCM(con);

}
    public User getPolygonUser(String userName) {
        return um.getPolygonUser(userName, con);
    }
    
    /** 
     * The purpose of this method, is to check if a user can login in with username and password
     * TODO: add security
     * @param userName
     * @param pwd
     * @return a boolean. False if not validated and true if validated
     */
    public boolean validatePolygonUser(String userName, String pwd) {
        return um.validatePolygonUser(userName,pwd,con);
    }
    
    /**
     * Returns a Building object based on building ID
     * @param bdgId
     * @return
     */
    public Building getBuilding(int bdgId) throws PolygonException{
        Building b=bm.getBuilding(bdgId, con);
        String imgPath = bm.getLatestBuildingImage(bdgId, con);
        b.setBuilding_pic(imgPath);
        return b;
    }
    
    public String getLatestBuildingImage(int buildingId) throws PolygonException{
        return bm.getLatestBuildingImage(buildingId, con);
        
    }

    /**
     * redirects to the BuildingMapper
     * @param bf the BuildingFloor object will be added to the database
     */
    public void addFloor(BuildingFloor bf) throws PolygonException {
        bm.addFloor(bf,con);
    }
    
    /**
     * redirects to the BuildingMapper
     * @param bdgId building Id
     * @return a list of floors from the database based on the building ID
     */
    public ArrayList<BuildingFloor> getListOfFloors(int bdgId) throws PolygonException{
        return bm.getFloorsList(bdgId, con);
    }

    /**
     * Sends the request to the right mapper
     * @param newRoom Room the be created in the Database 
     * @return The new buildingRoom with an uniqe ID.
     */
    public BuildingRoom saveBuildingRoom(BuildingRoom newRoom) throws PolygonException {
        return bm.saveBuildingRoom(newRoom, con);
    }
    
    /**
     * redirects to BuildingMapper
     * @param id floor ID
     * @return BuildingFloor object based on the floor ID
     */
    public BuildingFloor getFloor(int id) throws PolygonException{
        return bm.getFloor(id, con);
    }
    
    /**
     * redirects to BuildingMapper
     * @param id floorID
     * @param totalRooms new number of rooms to be updated in the database
     */
    public void updateFloor(int id, int totalRooms) throws PolygonException{
        bm.updateFloor(id, con, totalRooms);
    }
    
    /**
     * redirects to BuildingMapper
     * @param flrId floor ID
     * @return a list of Building Rooms based on the floor ID
     */
    public ArrayList<BuildingRoom> getRoomList(int flrId) throws PolygonException{
        return bm.getRoomList(flrId, con);
    }
    
    /**
     *redirects to the buildingMapper
     * @param bdgId buildingId of the building that has to be deleted
     */
    public void deleteBuilding(int bdgId) throws PolygonException{
        bm.deleteBuilding(bdgId, con);
    }
    
    /**
     *redirects to the buildingMapper
     * @param br new BuildingRoom that holds the changes
     */
    public void updateRoom(BuildingRoom br) throws PolygonException{
        bm.updateRoom(br, con);
    }
    
    /**
     * redirects to the BuildingMapper
     * @param bf new BuildingFloor that holds the changes
     */
    public void updateFloor(BuildingFloor bf) throws PolygonException{
        bm.updateFloor(bf, con);
    }
    
    /**
     * redirects to the BuildingMapper
     * @param b new Building that holds the changes
     */
    public void updateBuilding(Building b) throws PolygonException{
        bm.updateBuilding(b, con);
        
    }

    public ArrayList<Report> getSimpleListOfReports() throws PolygonException {
       return nrm.getSimpleListOfReports(con);

    }

    public void saveBuildingFiles(Building b) throws PolygonException {
        bm.saveBuildingDocs(b, con);
    }

    public void saveFloorplan(int floor, Floorplan f) throws PolygonException {
        bm.saveFloorplan(floor, f,con);
                }
    
    
    
    /**
     * redirects to the OrderMapper
     * @param o new Order
     * @return just for testing
     */
    public boolean addNewOrder(Order o){
            return om.addNewOrder(o, con);
    }
    
    /**
     * redirects to the OrderMapper
     * @param orderNum an ID that will be a reference on which order should be taken from the database 
     * @return an Order
     */
    public Order getOrder(int orderNum){
        return om.getOrder(orderNum, con);
    }
    
    /**
     * redirects to the CustomerMapper
     * @param username username used by the user to login
     * @return
     */
    public Customer getCustomerAfterLogIn(String username){
        return cm.getCustomerAfterLogIn(username, con);
    }
    
    /**
     * redirects to OrderMapper
     * @param stat order status ID
     * @return status description
     */
    public String getOrderStatus(int stat){
        return om.getOrderStatus(stat, con);
    }
    
    /**
     * redirects to OrderMapper
     * @param custId customer ID
     * @return list of Orders
     */
    public ArrayList<Order> getlistOfOrders(int custId) throws PolygonException{
        return om.getListOfOrders(custId, con);
    }
    
    /**
     * redirects to OrderMapper
     * @return list of all Orders
     */
    public ArrayList<Order> getListOfAllOrders() throws PolygonException{
        return om.getListOfAllOrders(con);
    
    }
    
    /**
     * This method makes a call to the buildingmapper, where it calls
     * a method that returns all floorplans for a specific floor, which is then
     * returned.
     *
     * @param floorId the ID of a BuildingFloor object
     * @return an ArrayList of Floorplan objects
     */
    public ArrayList<Floorplan> getFloorplans(int floorId) throws PolygonException{
        return bm.getFloorplans(floorId, con);
    }

    /**
     * redirects to the OrderMapper
     * @param orderNumber order number
     * @param newStat holds the change
     */
    public void updateOrder(int orderNumber, int newStat) {
        om.updateOrder(orderNumber,newStat,con);
    }
}
