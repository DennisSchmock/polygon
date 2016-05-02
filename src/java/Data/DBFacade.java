/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

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
 * controls which mappers to use.
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
    private boolean unitTest = false;

    public static void main(String[] args) {
        DBFacade facade = getInstance();

    }

    private DBFacade() {
        cm = new CustomerMapper();
        bm = new BuildingMapper();
        um = new UserMapper();
        nrm = new ReportMapper();
        om = new OrderMapper();

    }

    /**
     * Implements a singleton pattern. 
     * @return
     */
    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }

    /**
     * Uses customer mapper to retrive and return a customer object
     * @param customerid
     * @return
     */
    public Customer getCustomer(int customerid) {
        return cm.getCustomer(customerid, getCon());
    }

    /**
     * Uses UserMapper to validate a user. Returns false if user does not exist or 
     * incorrect password.
     * @param username
     * @param pwd
     * @return true or false
     */
    public boolean validateUser(String username, String pwd) {
        return um.validateUser(username, pwd, getCon());
    }

    /**
     * Uses CustomerMapper to add a customer object to the database
     * @param cus
     */
    public void addCustomer(Customer cus) {
        cm.addCustomerToDB(cus, getCon());
    }

    /**
     * Uses customer mapper to save a contact to the database.
     * @param c
     */
    public void saveContact(Contact c) {
        cm.saveContact(c, getCon());
    }

    /**
     * uses customer mapper to retrive a contact object from database
     * @param custID
     * @return
     */
    public Contact getContact(int custID) {
        return cm.getContact(custID, getCon());
    }

    /**
     * uses the CustomerMapper to rerive a list of contacts based on customer id.
     * @param id
     * @return
     */
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
        b = bm.saveNewBuildingDB(b, getCon());
        return b;
    }

    /**
     * Saves a Building picture reference to database. 
     * @param buildId
     * @param filename
     * @return
     * @throws PolygonException
     */
    public String saveBuildingPic(int buildId, String filename) throws PolygonException {
        return bm.saveBuildingPic(buildId, filename, getCon());

    }

    /**
     * Uses the Building mapper to find the list of buildings in the database
     *
     * @param customerID ID of the customer that is to be loaded
     * @return An list of buildings related to the customerID
     */
    public List<Building> getListOfbuildingsDB(int customerID) throws PolygonException {
        return bm.getListOfBuildingsBM(customerID, getCon());
    }

    /**
     * Sends the to be updated building object to the right mapper
     *
     * @param updatedBuildObj The object that should be updated in the database
     *
     */
    public void updateBuildingDBFacade(Building updatedBuildObj) throws PolygonException {
        bm.updateBuildingBm(updatedBuildObj, getCon());
    }

    //Sending the report as a whole to DB - new method
    public void newReportToDB(Report R) throws PolygonException {
        nrm.reportToDataBase(R, getCon());
    }

    /**
     * Loads a user object based on username.
     * @param username
     * @return
     */
    public User loadUser(String username) {
        return um.getUser(username, getCon());
    }

    /**
     * Sends the user object to the right mapper, that creates the user in the
     * Database
     *
     * @param user to be inserted to
     */
    public void createUserDBFacade(User user) {
        um.addUserToDB(user, getCon());
    }

    /**
     * This method returns a connection. It is prepared for using another
     * connection in a testing environment.
     *
     * @return the con
     */
    public Connection getCon() {
        if (unitTest) {
            return this.con;
        }
        return DBconnector.getInstance().getConnection();
    }


    /**
     * Creates the tuble in the database for a Report.
     *
     * @param report Report to be saved in the database
     */
    public int reportToDataBase(Report report) throws PolygonException {
        return nrm.reportToDataBase(report, getCon());
    }

    /**
     * The purpse of this method is to retrieve a list of reports for a specific building
     * @param buildingId 
     * @return An arraylist of Reports
     * @throws PolygonException
     */
    public ArrayList<Report> getListOfReports(int buildingId) throws PolygonException {
        return nrm.getAllReportsBuilding(buildingId, getCon());
    }

    /**
     * Returns a single report based on the reportId
     *
     * @param reportId
     * @return a report object.
     * @throws Domain.Exceptions.PolygonException
     */
    public Report getSingleReport(int reportId) throws PolygonException {
        return nrm.getSingleReport(reportId, getCon());
    }

    /**
     * Sends the request to the right mapper.
     *
     * @return Returs an list of All customers in the database
     */
    public List<Customer> getAllCustomers() {
        return cm.getAllCustomersCM(getCon());

    }

    /**
     * The purpose of this method is to retrive a Polygon Employee as a user Object.
     * @param userName
     * @return User object
     */
    public User getPolygonUser(String userName) {

        return um.getPolygonUser(userName, getCon());
    }

    /**
     * The purpose of this method, is to check if a user can login in with
     * username and password TODO: add security
     *
     * @param userName
     * @param pwd
     * @return a boolean. False if not validated and true if validated
     */
    public boolean validatePolygonUser(String userName, String pwd) {
        return um.validatePolygonUser(userName, pwd, getCon());
    }

    /**
     * Returns a Building object based on building ID
     *
     * @param bdgId
     * @return
     */
    public Building getBuilding(int bdgId) throws PolygonException {
        Building b = bm.getBuilding(bdgId, getCon());
        String imgPath = bm.getLatestBuildingImage(bdgId, getCon());
        b.setBuilding_pic(imgPath);
        return b;
    }

    /**
     * Method for retrieving the latest building image.
     * @param buildingId
     * @return filename and path.
     * @throws PolygonException
     */
    public String getLatestBuildingImage(int buildingId) throws PolygonException {
        return bm.getLatestBuildingImage(buildingId, getCon());

    }

    /**
     * redirects to the BuildingMapper
     *
     * @param bf the BuildingFloor object will be added to the database
     */
    public void addFloor(BuildingFloor bf) throws PolygonException {
        bm.addFloor(bf, getCon());
    }

    /**
     * redirects to the BuildingMapper
     *
     * @param bdgId building Id
     * @return a list of floors from the database based on the building ID
     */
    public ArrayList<BuildingFloor> getListOfFloors(int bdgId) throws PolygonException {
        return bm.getFloorsList(bdgId, getCon());
    }

    /**
     * Sends the request to the right mapper
     *
     * @param newRoom Room the be created in the Database
     * @return The new buildingRoom with an uniqe ID.
     */
    public BuildingRoom saveBuildingRoom(BuildingRoom newRoom) throws PolygonException {
        return bm.saveBuildingRoom(newRoom, getCon());
    }

    /**
     * redirects to BuildingMapper
     *
     * @param id floor ID
     * @return BuildingFloor object based on the floor ID
     */
    public BuildingFloor getFloor(int id) throws PolygonException {
        return bm.getFloor(id, getCon());
    }

    /**
     * redirects to BuildingMapper
     *
     * @param id floorID
     * @param totalRooms new number of rooms to be updated in the database
     */
    public void updateFloor(int id, int totalRooms) throws PolygonException {
        bm.updateFloor(id, getCon(), totalRooms);
    }

    /**
     * redirects to BuildingMapper
     *
     * @param flrId floor ID
     * @return a list of Building Rooms based on the floor ID
     */
    public ArrayList<BuildingRoom> getRoomList(int flrId) throws PolygonException {
        return bm.getRoomList(flrId, getCon());
    }

    /**
     * redirects to the buildingMapper
     *
     * @param bdgId buildingId of the building that has to be deleted
     */
    public void deleteBuilding(int bdgId) throws PolygonException {
        bm.deleteBuilding(bdgId, getCon());
    }

    /**
     * redirects to the buildingMapper
     *
     * @param br new BuildingRoom that holds the changes
     */
    public void updateRoom(BuildingRoom br) throws PolygonException {
        bm.updateRoom(br, getCon());
    }

    /**
     * redirects to the BuildingMapper
     *
     * @param bf new BuildingFloor that holds the changes
     */
    public void updateFloor(BuildingFloor bf) throws PolygonException {
        bm.updateFloor(bf, getCon());
    }

    /**
     * redirects to the BuildingMapper
     *
     * @param b new Building that holds the changes
     */
    public void updateBuilding(Building b) throws PolygonException {
        bm.updateBuilding(b, getCon());
    }

    public ArrayList<Report> getSimpleListOfReports() throws PolygonException {
        return nrm.getSimpleListOfReports(getCon());

    }

    public void saveBuildingFiles(Building b) throws PolygonException {
        bm.saveBuildingDocs(b, getCon());
    }

    public void saveFloorplan(int floor, Floorplan f) throws PolygonException {
        bm.saveFloorplan(floor, f, getCon());
    }

    /**
     * redirects to the OrderMapper
     *
     * @param o new Order
     * @return just for testing
     */
    public boolean addNewOrder(Order o) {
        return om.addNewOrder(o, getCon());
    }

    /**
     * redirects to the OrderMapper
     *
     * @param orderNum an ID that will be a reference on which order should be
     * taken from the database
     * @return an Order
     */
    public Order getOrder(int orderNum) {
        return om.getOrder(orderNum, getCon());
    }

    /**
     * redirects to the CustomerMapper
     *
     * @param username username used by the user to login
     * @return
     */
    public Customer getCustomerAfterLogIn(String username) {
        return cm.getCustomerAfterLogIn(username, getCon());
    }

    /**
     * redirects to OrderMapper
     *
     * @param stat order status ID
     * @return status description
     */
    public String getOrderStatus(int stat) {
        return om.getOrderStatus(stat, getCon());
    }

    /**
     * redirects to OrderMapper
     *
     * @param custId customer ID
     * @return list of Orders
     */
    public ArrayList<Order> getlistOfOrders(int custId) throws PolygonException {
        return om.getListOfOrders(custId, getCon());
    }

    /**
     * redirects to OrderMapper
     *
     * @return list of all Orders
     */
    public ArrayList<Order> getListOfAllOrders() throws PolygonException {
        return om.getListOfAllOrders(getCon());

    }

    /**
     * This method makes a call to the buildingmapper, where it calls a method
     * that returns all floorplans for a specific floor, which is then returned.
     *
     * @param floorId the ID of a BuildingFloor object
     * @return an ArrayList of Floorplan objects
     */
    public ArrayList<Floorplan> getFloorplans(int floorId) throws PolygonException {
        return bm.getFloorplans(floorId, getCon());
    }

    /**
     * redirects to the OrderMapper
     *
     * @param orderNumber order number
     * @param newStat holds the change
     */
    public void updateOrder(int orderNumber, int newStat) {
        om.updateOrder(orderNumber, newStat, getCon());
    }

    /**
     * The purpose of this method, is to set up a connection for running
     * UNIT-tests Since we do not want the tests to affect out "production
     * server", it gives us the possibility of feeding it a connection to the
     * test database.
     *
     * @param con
     */
    public void setTestConnection(Connection con) {
        this.con = con;
        this.unitTest = true;
    }
}
