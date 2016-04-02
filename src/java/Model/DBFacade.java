/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.*;
import java.sql.Connection;

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
        Report r = facade.getReport(1);
        System.out.println(r.getReportId() + r.getDate() + r.getBdgId() + r.getCategoryConclusion());
        
        ReportRoomExterior re = facade.getReportExt(1);
        System.out.println(re.getRepExtId() + re.getRepExtDescription() + re.getRepExtPic() + re.getReportId());
        
        ReportRoom rr = facade.getReportRoom(1);
        System.out.println(rr.getRepRoomId() + rr.getRoomName() + rr.getReportId());
        
        ReportRoomDamage rd = facade.getReportDamage(1);
        System.out.println(rd.getRepRoomDmgId() + rd.getDamageTime() + rd.getPlace() + rd.getWhatHappened() + rd.getWhatIsRepaired() + rd.getDamageType() + rd.getRepRoomId());
        
        ReportRoomInterior ri = facade.getReportInt(1);
        System.out.println(ri.getRepRoomIntId()+ri.getRepRoomIntName()+ri.getRemark()+ri.getRepRoomId());
        
        ReportRoomRecommendation rc = facade.getReportRec(1);
        System.out.println(rc.getRepRoomRecId()+rc.getRecommendation()+rc.getRepRoomId());
        
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

    public void saveNewReport(Report r) {
        rm.saveNewReport(r, con);
    }

    public void saveReportRoom(ReportRoom rr) {
        rm.saveReportRoom(rr, con);
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
