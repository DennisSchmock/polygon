/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import Domain.Exceptions.PolygonException;
import Model.DBFacade;
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

    public static DomainFacade getInstance() {
        return new DomainFacade();
    }

    /**
     * @return Returns the building object, so that it can be displayed in JSP.
     * @see All the fields needed to create an building object Creates the
     * building object and sends it to the DBFacade
     */
    public Building createnewBuilding(String buildingName, String StreetAddress, String StreetNumber, int zipcode, double buildingsize, int buildingYear, String useOfBuilding, int custId) {
        Building b = new Building(buildingName, StreetAddress, StreetNumber, zipcode, buildingYear, buildingsize, useOfBuilding);
        b.setCustId(custId); // this is hardcoded! Should load the userloged in!
        b = dbFacade.saveNewBuilding(b);
        return b;
    }


public String saveBuildingPic(int buildId, String filename){
        return dbFacade.saveBuildingPic(buildId, filename);
        
    }

//    public Report saveNewReport(Date date, int buildingId, int category) {
//        Report r = new Report(0, date, buildingId, category); // Fix
//        r = dbFacade.saveNewReport(r);
//        return r;
//    }
    public void saveNewReportExt(int repExtId, String repExtDescription, int repExtPic, int reportId) {
//        ReportExterior r = new ReportExterior(0, repExtDescription, repExtPic, reportId); // Fix
//        dbFacade.saveReportExt(r);
    }

//    public void saveNewReport(String date, int buildingId, int category){
//        Report r = new Report(0,date,buildingId,category); // Fix
//        dbFacade.saveNewReport(r);
//    }
    public void createNewCustomer(String companyName, String contactPerson, String email, String street, String streetnumber, String CVR, int zip, String city, String phonenumber) {
        Customer customer = new Customer(companyName, contactPerson, email, street, streetnumber, CVR, zip, city, phonenumber);
        dbFacade.addCustomer(customer);
    }

    /**
     * Returns an list of buildings, for an specific customer
     *
     * @param customerID ID of the customer that is to be loaded
     * @return An list of buildings related to the customerID
     */
    public List<Building> getListOfBuildings(int customerID) {
        // When we have implemeted an hashmap, where should be some logic, to
        // Find out if the hashmap is empty or not. Otherwise it loads it form 
        // The database.
        return dbFacade.getListOfbuildingsDB(customerID);
    }

    public ArrayList<Report> getListOfReports(int buildingId) {
        return dbFacade.getListOfReports(buildingId);
    }

    /**
     * Take the building object that needs to be saved in the DB, and sends it
     * to the DBFacade
     *
     * @param buildingToBeEdited Is the updated building object that needs to be
     * saved in the database
     */
    public void Updatebuilding(Building buildingToBeEdited) {
        dbFacade.updateBuildingDBFacade(buildingToBeEdited);
    }

    public boolean logUserIn(String userName, String pwd) {
        return dbFacade.validateUser(userName, pwd);
    }

    public User loadUser(String username) {
        return dbFacade.loadUser(username);
    }

    /**
     * Sends the report obejct to the DB Facade
     *
     * @param report The report object without an uniqe ID jet.
     */
    public int saveReport(Report report) {
        return dbFacade.reportToDataBase(report);

    }

    public boolean logEmpUserIn(String userName, String pwd) {
        return dbFacade.validatePolygonUser(userName, pwd);

    }

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
     */
    public Building getBuilding(int buildingID) {
        return dbFacade.getBuilding(buildingID);
    }

    public String getLatestBuildingImage(int buildingId) {
        return dbFacade.getLatestBuildingImage(buildingId);

    }

    public void addFloors(BuildingFloor bf) {
        dbFacade.addFloor(bf);
    }

    /**
     * Method for creating a BUILDING ROOM. Something that belongs to the FLOOR,
     * that Belongs to the builing. Not something in the report
     *
     * @param newRoom A BuildingRoom object that is to be created.
     * @return The newly created building object in the database with the an ID!
     */
    public BuildingRoom addBuildingRoom(BuildingRoom newRoom) {
        return dbFacade.saveBuildingRoom(newRoom);
    }

    public ArrayList<BuildingFloor> listOfFloors(int bdgId) {
        return dbFacade.getListOfFloors(bdgId);
    }

    public Customer getCustomer(int cusid) {
        return dbFacade.getCustomer(cusid);
    }

    public ArrayList<BuildingRoom> getListOfRooms(int flrId) {
        return dbFacade.getRoomList(flrId);
    }

    public void addRoom(BuildingRoom br) {
        dbFacade.saveBuildingRoom(br);
    }

    public void updateFloor(int id, int newNumRooms) {
        dbFacade.updateFloor(id, newNumRooms);
    }

    /**
     * Method that needs to load an Building Floor based on a floorod-
     *
     * @param floorid the ID for the floor to be loaded
     * @return An object of the infomation for the floor.
     */
    public BuildingFloor getBuildingFloor(int floorid) {
        return dbFacade.getFloor(floorid);
    }

    /**
     * The purpose of this method, is to return a single report based on reportID
     * @param reportId
     * @return a Report
     */
    public Report getReport(int reportId) {
        return dbFacade.getSingleReport(reportId);
    }

    /**
     * The purpose of this method, is to get a very simple list of all reports from DB. 
     * @return
     */
    public ArrayList<Report> getSimpleListOfReports() throws PolygonException {
        return dbFacade.getSimpleListOfReports();
    }

    /**
     * Saves Documents associated with a building that are NOT the
     * floorplans
     * @param b the buliding which has documents to be saved
     */
    public void saveBuildingFiles(Building b) throws PolygonException {
        dbFacade.saveBuildingFiles(b);
    }

    /**
     * Saves floorplans to a specific floor
     * @param floorId Id of the floor the floorplan(s) belongs to 
     * @param plans ArrayList of floorplans
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
     */
    public ArrayList<Floorplan> getFloorplans(ArrayList<BuildingFloor> listOfFLoors){
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
     */
    public ArrayList<Order> getListOfOrders(int custId){
        return dbFacade.getlistOfOrders(custId);
    }
    
    /**
     * This method is used to get list of all the orders
     * @return list of all Orders
     */
    public ArrayList<Order> getListOfAllOrders(){
        return dbFacade.getListOfAllOrders();
    }
}
