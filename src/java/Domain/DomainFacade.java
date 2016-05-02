/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import Domain.Exceptions.PolygonException;
import Data.DBFacade;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The servlet knows this controller class. This controller knows the other
 * classes in the control layer. Connects to the DB Facade to send on
 * information.
 *
 * @author dennisschmock
 */
public class DomainFacade {

    private DBFacade dbFacade;

    private DomainFacade() {
        dbFacade = DBFacade.getInstance();
    }

    /**
     *
     * @return
     */
    public static DomainFacade getInstance() {
        return new DomainFacade();
    }

    /**
     * @param buildingName
     * @param StreetAddress
     * @param zipcode
     * @param StreetNumber
     * @param useOfBuilding
     * @param buildingsize
     * @param buildingYear
     * @param custId
     * @return Returns the building object, so that it can be displayed in JSP.
     * @throws Domain.Exceptions.PolygonException
     * @see All the fields needed to create an building object Creates the
     * building object and sends it to the DBFacade
     */
    public Building createnewBuilding(String buildingName, String StreetAddress, String StreetNumber, int zipcode, double buildingsize, int buildingYear, String useOfBuilding, int custId) throws PolygonException {
        Building b = new Building(buildingName, StreetAddress, StreetNumber, zipcode, buildingYear, buildingsize, useOfBuilding);
        b.setCustId(custId); // this is hardcoded! Should load the userloged in!
        b = dbFacade.saveNewBuilding(b);
        return b;
    }

    /**
     *
     * @param buildId
     * @param filename
     * @return
     * @throws PolygonException
     */
    public String saveBuildingPic(int buildId, String filename) throws PolygonException{
        return dbFacade.saveBuildingPic(buildId, filename);
        
    }

//    public Report saveNewReport(Date date, int buildingId, int category) {
//        Report r = new Report(0, date, buildingId, category); // Fix
//        r = dbFacade.saveNewReport(r);
//        return r;
//    }

    /**
     *
     * @param repExtId
     * @param repExtDescription
     * @param repExtPic
     * @param reportId
     */
    public void saveNewReportExt(int repExtId, String repExtDescription, int repExtPic, int reportId) {
//        ReportExterior r = new ReportExterior(0, repExtDescription, repExtPic, reportId); // Fix
//        dbFacade.saveReportExt(r);
    }

//    public void saveNewReport(String date, int buildingId, int category){
//        Report r = new Report(0,date,buildingId,category); // Fix
//        dbFacade.saveNewReport(r);
//    }

    /**
     *
     * @param companyName
     * @param contactPerson
     * @param email
     * @param street
     * @param streetnumber
     * @param CVR
     * @param zip
     * @param city
     * @param phonenumber
     */
    public void createNewCustomer(String companyName, String contactPerson, String email, String street, String streetnumber, String CVR, int zip, String city, String phonenumber) {
        Customer customer = new Customer(companyName, contactPerson, email, street, streetnumber, CVR, zip, city, phonenumber);
        dbFacade.addCustomer(customer);
    }

    /**
     * Returns an list of buildings, for an specific customer
     *
     * @param customerID ID of the customer that is to be loaded
     * @return An list of buildings related to the customerID
     * @throws Domain.Exceptions.PolygonException
     */
    public List<Building> getListOfBuildings(int customerID) throws PolygonException {
        // When we have implemeted an hashmap, where should be some logic, to
        // Find out if the hashmap is empty or not. Otherwise it loads it form 
        // The database.
        return dbFacade.getListOfbuildingsDB(customerID);
    }

    /**
     *
     * @param buildingId
     * @return
     * @throws PolygonException
     */
    public ArrayList<Report> getListOfReports(int buildingId) throws PolygonException {
        return dbFacade.getListOfReports(buildingId);
    }

    /**
     * Take the building object that needs to be saved in the DB, and sends it
     * to the DBFacade
     *
     * @param buildingToBeEdited Is the updated building object that needs to be
     * saved in the database
     * @throws Domain.Exceptions.PolygonException
     */
    public void Updatebuilding(Building buildingToBeEdited) throws PolygonException {
        dbFacade.updateBuildingDBFacade(buildingToBeEdited);
    }

    /**
     *
     * @param userName
     * @param pwd
     * @return
     */
    public boolean logUserIn(String userName, String pwd) {
        return dbFacade.validateUser(userName, pwd);
    }

    /**
     *
     * @param username
     * @return
     */
    public User loadUser(String username) {
        return dbFacade.loadUser(username);
    }

    /**
     * Sends the report obejct to the DB Facade
     *
     * @param report The report object without an uniqe ID jet.
     * @return 
     * @throws Domain.Exceptions.PolygonException
     */
    public int saveReport(Report report) throws PolygonException {
        return dbFacade.reportToDataBase(report);

    }

    /**
     *
     * @param userName
     * @param pwd
     * @return
     */
    public boolean logEmpUserIn(String userName, String pwd) {
        return dbFacade.validatePolygonUser(userName, pwd);

    }

    /**
     *
     * @param userName
     * @return
     */
    public User loadEmpUser(String userName) {
        return dbFacade.getPolygonUser(userName);

    }

    /**
     * Needs to load all the customers in the database
     *
     * @return Returs an list of All customers in the database
     */
    public List<Customer> loadAllCustomers() {
        return dbFacade.getAllCustomers();
    }

    /**
     * Loads an building in the database
     *
     * @param buildingID Id for the building to be loaded
     * @return An objet of the the Building that has been loaded
     * @throws Domain.Exceptions.PolygonException
     */
    public Building getBuilding(int buildingID) throws PolygonException {
        return dbFacade.getBuilding(buildingID);
    }

    /**
     *
     * @param buildingId
     * @return
     * @throws PolygonException
     */
    public String getLatestBuildingImage(int buildingId) throws PolygonException {
        return dbFacade.getLatestBuildingImage(buildingId);

    }

    /**
     *
     * @param bf
     * @throws PolygonException
     */
    public void addFloors(BuildingFloor bf) throws PolygonException {
        dbFacade.addFloor(bf);
    }

    /**
     * Method for creating a BUILDING ROOM. Something that belongs to the FLOOR,
     * that Belongs to the builing. Not something in the report
     *
     * @param newRoom A BuildingRoom object that is to be created.
     * @return The newly created building object in the database with the an ID!
     * @throws Domain.Exceptions.PolygonException
     */
    public BuildingRoom addBuildingRoom(BuildingRoom newRoom) throws PolygonException {
        return dbFacade.saveBuildingRoom(newRoom);
    }

    /**
     *
     * @param bdgId
     * @return
     * @throws PolygonException
     */
    public ArrayList<BuildingFloor> listOfFloors(int bdgId) throws PolygonException {
        return dbFacade.getListOfFloors(bdgId);
    }

    /**
     *
     * @param cusid
     * @return
     */
    public Customer getCustomer(int cusid) {
        return dbFacade.getCustomer(cusid);
    }

    /**
     *
     * @param flrId
     * @return
     * @throws PolygonException
     */
    public ArrayList<BuildingRoom> getListOfRooms(int flrId) throws PolygonException {
        return dbFacade.getRoomList(flrId);
    }

    /**
     *
     * @param br
     * @throws PolygonException
     */
    public void addRoom(BuildingRoom br) throws PolygonException {
        dbFacade.saveBuildingRoom(br);
    }

    /**
     *
     * @param id
     * @param newNumRooms
     * @throws PolygonException
     */
    public void updateFloor(int id, int newNumRooms) throws PolygonException {
        dbFacade.updateFloor(id, newNumRooms);
    }

    /**
     * Method that needs to load an Building Floor based on a floorod-
     *
     * @param floorid the ID for the floor to be loaded
     * @return An object of the infomation for the floor.
     * @throws Domain.Exceptions.PolygonException
     */
    public BuildingFloor getBuildingFloor(int floorid) throws PolygonException {
        return dbFacade.getFloor(floorid);
    }

    /**
     * The purpose of this method, is to return a single report based on reportID
     * @param reportId
     * @return a Report
     * @throws Domain.Exceptions.PolygonException
     */
    public Report getReport(int reportId) throws PolygonException {
        return dbFacade.getSingleReport(reportId);
    }

    /**
     * The purpose of this method, is to get a very simple list of all reports from DB. 
     * @return
     * @throws Domain.Exceptions.PolygonException
     */
    public ArrayList<Report> getSimpleListOfReports() throws PolygonException {
        return dbFacade.getSimpleListOfReports();
    }

    /**
     * Saves Documents associated with a building that are NOT the
     * floorplans
     * @param b the buliding which has documents to be saved
     * @throws Domain.Exceptions.PolygonException
     */
    public void saveBuildingFiles(Building b) throws PolygonException {
        dbFacade.saveBuildingFiles(b);
    }

    /**
     * Saves floorplans to a specific floor
     * @param floorId Id of the floor the floorplan(s) belongs to 
     * @param plans ArrayList of floorplans
     * @throws Domain.Exceptions.PolygonException
     */
    public void saveFloorplans(int floorId, ArrayList<Floorplan> plans) throws PolygonException {
            for (Floorplan floorplan : plans) {
                System.out.println("Trying to save floorplan:");;
                dbFacade.saveFloorplan(floorId,floorplan);
            }
    }
    
    /**
     * Method finds all the floorplans belonging to an ArrayList of floors
     * 
     * @param listOfFLoors a list of BuildingFloor objects
     * @return Floorplans belonging to the buildingfloors put in
     * @throws Domain.Exceptions.PolygonException
     */
    public ArrayList<Floorplan> getFloorplans(ArrayList<BuildingFloor> listOfFLoors) throws PolygonException{
        ArrayList<Floorplan> plans=new ArrayList();
        
        for (BuildingFloor bf : listOfFLoors) {
            ArrayList<Floorplan> plansFetched = dbFacade.getFloorplans(bf.getFloorId());
            if (plansFetched != null) plans.addAll(plansFetched);
        }
        return plans;
    }

    /**
     * This method is used to save a new Order
     * @param o new Order
     */
    public void addNewOrder(Order o){
        dbFacade.addNewOrder(o);
    }
    
    /**
     * This method is used to get a single order from the database
     * @param orderNum an ID that will be a reference on which order should be taken from the database 
     * @return an Order
     */
    public Order getOrder(int orderNum){
        return dbFacade.getOrder(orderNum);
    }
    
    /**
     * This method is used to get a customer data from the database based on the username
     * @param username username used by the user to login
     * @return a customer
     */
    public Customer getCustomerAfterLogIn(String username){
        return dbFacade.getCustomerAfterLogIn(username);
    }
    
    /**
     * This method is used to get the status description of an order
     * @param stat order status ID
     * @return status description
     */
    public String getOrderStatus(int stat){
        return dbFacade.getOrderStatus(stat);
    }
    
    /**
     * This method is used to get the list of orders of a customer
     * @param custId customer ID
     * @return list of Orders
     * @throws Domain.Exceptions.PolygonException
     */
    public ArrayList<Order> getListOfOrders(int custId) throws PolygonException{
        return dbFacade.getlistOfOrders(custId);
    }
    
    /**
     * This method is used to get list of all the orders
     * @return list of all Orders
     * @throws Domain.Exceptions.PolygonException
     */
    public ArrayList<Order> getListOfAllOrders() throws PolygonException{
        return dbFacade.getListOfAllOrders();
    }

    /**
     * This method is used to update the order status
     * @param orderNumber order number
     * @param newStat to be changed
     */
    public void updateStatus(int orderNumber, int newStat) {
        dbFacade.updateOrder(orderNumber,newStat);
    }
}
