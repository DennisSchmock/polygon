/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import Model.DBFacade;
import java.util.List;

/**
 *
 The servlet knows this controller class.
 This controller knows the other classes in the control layer.
 Connects to the DB Facade to send on information.
 * @author dennisschmock
 */
public class DomainFacade {
    
    private DBFacade dbFacade;
    
    private DomainFacade()
    {
      dbFacade = DBFacade.getInstance();
    }

    public static DomainFacade getInstance()
    {
         return new DomainFacade();
    }

    /**
     * @return Returns the building object, so that it can be displayed in JSP.
     * @see All the fields needed to create an building object
     * Creates the building object and sends it to the DBFacade
     */
    public Building createnewBuilding(String buildingName, String StreetAddress, String StreetNumber, int zipcode, double buildingsize, int buildingYear, String useOfBuilding) {
        Building b = new Building(buildingName, StreetAddress, StreetNumber, zipcode, buildingYear, buildingsize, useOfBuilding);
        dbFacade.saveNewBuilding(b);
        return b;
    } 
    
    public Report saveNewReport(String date, int buildingId, int category){
        Report r = new Report(0,date,buildingId,category); // Fix
        r=dbFacade.saveNewReport(r);
        return r;
    }
    
    public ReportRoom saveReportRoom(int repRoomId, String roomName, int reportId, int buildingId) {
        ReportRoom rr = new ReportRoom(0,roomName,reportId);
        rr.setBuildingRoomId(buildingId);
        rr=dbFacade.saveReportRoom(rr);
        return rr;
    }
    
    public void saveReportMoist(int moistMeasured,String measurePoint, int reportRoom){
        ReportRoomMoist rrm = new ReportRoomMoist(0,moistMeasured,measurePoint, reportRoom);
        
        dbFacade.saveReportMoist(rrm);
    }
    
    public void saveReportRoomDamage(String damageTime, String place, String whatHappened, String whatIsRepaired, String damageType, int repRoomId) {
        ReportRoomDamage rrd = new ReportRoomDamage(damageTime, place, whatHappened, whatIsRepaired, damageType, repRoomId);
        dbFacade.saveReportRoomDamage(rrd);
    }
    
    public void saveNewReportExt(int repExtId, String repExtDescription, int repExtPic, int reportId){
        ReportRoomExterior r = new ReportRoomExterior(0,repExtDescription,repExtPic,reportId); // Fix
        dbFacade.saveReportExt(r);
    }
    
    public void saveReportRoomRec(String recommendation, int repRoomId) {
        ReportRoomRecommendation rrr = new ReportRoomRecommendation(recommendation, repRoomId);
        dbFacade.saveReportRoomRec(rrr);
    }
    
    public void saveReportInterior(String repRoomIntName, String remark, int repRoomId) {
        ReportRoomInterior ri = new ReportRoomInterior(repRoomIntName, remark, repRoomId);
        dbFacade.saveReportInterior(ri);
    }
    
//    public void saveNewReport(String date, int buildingId, int category){
//        Report r = new Report(0,date,buildingId,category); // Fix
//        dbFacade.saveNewReport(r);
//    }
    public void createNewCustomer(String companyName, String contactPerson, String email, String street, int streetnumber, int CVR, int zip, String city, String phonenumber){
        Customer customer = new Customer(companyName, contactPerson, email, street, streetnumber, CVR, zip, city, phonenumber);
        dbFacade.addCustomer(customer);
    }

    /**
     * Returns an list of buildings, for an specific customer
     * @param customerID ID of the customer that is to be loaded 
     * @return An list of buildings related to the customerID
     */
    public List<Building> getListOfBuildings(int customerID) {
        // When we have implemeted an hashmap, where should be some logic, to
        // Find out if the hashmap is empty or not. Otherwise it loads it form 
        // The database.
        return dbFacade.getListOfbuildingsDB(customerID);
    }

    /**
     * Take the building object that needs to be saved in the DB, and sends it
     * to the DBFacade
     * @param buildingToBeEdited Is the updated building object that needs to be
     * saved in the database
     */
    public void Updatebuilding(Building buildingToBeEdited) {
        dbFacade.updateBuildingDBFacade(buildingToBeEdited);
    }

   
    
}
