/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author CJS
 */
public class Report {
    private int reportId;
    private Date date;
    private ArrayList<ReportFloor> reportFloors;
    private ArrayList<ReportRoom> listOfRepRoom;
    private ArrayList<ReportExterior> listOfRepExt;
    private int buildingId;
    private int categoryConclusion;
    private String polygonUserName;
    private String customerAccountable;
    private boolean finshed;
    
    /**
     *This constructer is for loading an object from the mapper.
     * @param reportId  report number
     * @param date   date
     * @param buildingId    building's ID
     * @param catCon  category conclusion
     */
    public Report(int reportId, Date date, int buildingId, int catCon, String polygonUser, String customerName) {
        this.reportId = reportId;
        this.date = date;
        this.buildingId = buildingId;
        this.categoryConclusion = catCon;
        this.polygonUserName = polygonUser;
        this.customerAccountable = customerName;
    }
    
    public Report(String Date, int buildingId, int catCon) {
        this.date = date;
        this.buildingId = buildingId;
        this.categoryConclusion = catCon;
        this.listOfRepRoom = new ArrayList<>();
        this.listOfRepExt = new ArrayList<>();
    }

    public Report(int buildingId, String polygonUserID) {
        this.buildingId = buildingId;
        this.polygonUserName = polygonUserID;
    }

    public Report() {
    }

    
    

    public ReportRoom getReportRoom(int id){
        return this.listOfRepRoom.get(id);
    }
    
    public ReportRoom getReportRoomFromReportFloor(int reportRoomId){
        for (ReportFloor reportFloor : reportFloors) {
            for (ReportRoom reportRoom : reportFloor.getReportRooms()) {
                if(reportRoom.getRepRoomId()==reportRoomId){
                    return reportRoom;
                }
                
            }
            
        }
        return null;
    }
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
    
    public int getReportId() {
        return reportId;
    }
    
    

    public Date getDate() {
        return date;
    }

    public ArrayList<ReportRoom> getListOfRepRoom() {
        return listOfRepRoom;
    }

    public ArrayList<ReportExterior> getListOfRepExt() {
        return listOfRepExt;
    }

    public void setListOfRepExt(ArrayList<ReportExterior> listOfRepRoomExt) {
        this.listOfRepExt = listOfRepRoomExt;
    }

  
    public void setListOfRepRoom(ArrayList<ReportRoom> listOfRepRoom) {
        this.listOfRepRoom = listOfRepRoom;
    }

    public int getCategoryConclusion() {
        return categoryConclusion;
    }

    public String getPolygonUserName() {
        return polygonUserName;
    }

    public void setPolygonUserName(String polygonUserName) {
        this.polygonUserName = polygonUserName;
    }

    public boolean isFinshed() {
        return finshed;
    }

    public void setFinshed(boolean finshed) {
        this.finshed = finshed;
    }
    
    

    /**
     * @return the buildingId
     */
    public int getBuildingId() {
        return buildingId;
    }

    /**
     * @param buildingId the buildingId to set
     */
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public void setCategoryConclusion(int categoryConclusion) {
        this.categoryConclusion = categoryConclusion;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerAccountable() {
        return customerAccountable;
    }

    public void setCustomerAccountable(String customerAccountable) {
        this.customerAccountable = customerAccountable;
    }
    
    
    
    @Override
    public String toString() {
        return "Report{" + "reportId=" + reportId + ", date=" + date + ", buildingId=" + buildingId + ", categoryConclusion=" + categoryConclusion + ", polygonUserName=" + polygonUserName + ", finshed=" + finshed + 
                reportRoomToString()+ reportRoomExtToString() +'}';
    }

    private String reportRoomToString() {
        String reportRoomsString="\n";
        if(listOfRepRoom != null){
        for (ReportRoom Room : listOfRepRoom) {
            reportRoomsString +="NEWROOM: " + Room.toString() +"\n";
        }
        }
        return reportRoomsString;
    }

    private String reportRoomExtToString() {
        String reportExtString ="\n";
        if(listOfRepRoom != null){
        for (ReportExterior exterior : listOfRepExt ) {
            reportExtString += "New EXTERIOR: " + exterior.toString();
        }
        }
        return reportExtString;
    }

    /**
     * @return the reportFloors
     */
    public ArrayList<ReportFloor> getReportFloors() {
        return reportFloors;
    }

    /**
     * @param reportFloors the reportFloors to set
     */
    public void setReportFloors(ArrayList<ReportFloor> reportFloors) {
        this.reportFloors = reportFloors;
    }
    
    
    

}
