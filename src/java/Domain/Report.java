/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author CJS
 */
public class Report implements Serializable{
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
    private ArrayList<ReportPic> listOfExtPics;
    private String buildingName;
    /**
     *This constructer is for loading an object from the mapper.
     * @param reportId  report number
     * @param date   date
     * @param buildingId    building's ID
     * @param catCon  category conclusion
     * @param polygonUser
     * @param customerName
     */
    public Report(int reportId, Date date, int buildingId, int catCon, String polygonUser, String customerName) {
        this.reportId = reportId;
        this.date = date;
        this.buildingId = buildingId;
        this.categoryConclusion = catCon;
        this.polygonUserName = polygonUser;
        this.customerAccountable = customerName;
    }
    
    /**
     *
     * @param Date
     * @param buildingId
     * @param catCon
     */
    public Report(String Date, int buildingId, int catCon) {
        this.date = date;
        this.buildingId = buildingId;
        this.categoryConclusion = catCon;
        this.listOfRepRoom = new ArrayList<>();
        this.listOfRepExt = new ArrayList<>();
    }

    /**
     *
     * @param buildingId
     * @param polygonUserID
     */
    public Report(int buildingId, String polygonUserID) {
        this.buildingId = buildingId;
        this.polygonUserName = polygonUserID;
    }

    /**
     *
     */
    public Report() {
    }
    
    /**
     * The purpose of this method, is to get a specific report room based on id.
     * @param reportRoomId
     * @return
     */
    public ReportRoom getReportRoomFromReportFloor(int reportRoomId){
        for (ReportFloor reportFloor : getReportFloors()) {
            for (ReportRoom reportRoom : reportFloor.getReportRooms()) {
                if(reportRoom.getRepRoomId()==reportRoomId){
                    return reportRoom;
                }
                
            }
            
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public ReportRoom getReportRoom(int id){
        return this.getListOfRepRoom().get(id);
    }
    
    /**
     *
     * @param reportId
     */
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
    
    /**
     *
     * @return
     */
    public int getReportId() {
        return reportId;
    }
    
    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @return
     */
    public ArrayList<ReportRoom> getListOfRepRoom() {
        return listOfRepRoom;
    }

    /**
     *
     * @return
     */
    public ArrayList<ReportExterior> getListOfRepExt() {
        return listOfRepExt;
    }

    /**
     *
     * @param listOfRepRoomExt
     */
    public void setListOfRepExt(ArrayList<ReportExterior> listOfRepRoomExt) {
        this.listOfRepExt = listOfRepRoomExt;
    }

    /**
     *
     * @param listOfRepRoom
     */
    public void setListOfRepRoom(ArrayList<ReportRoom> listOfRepRoom) {
        this.listOfRepRoom = listOfRepRoom;
    }

    /**
     *
     * @return
     */
    public int getCategoryConclusion() {
        return categoryConclusion;
    }

    /**
     *
     * @return
     */
    public String getPolygonUserName() {
        return polygonUserName;
    }

    /**
     *
     * @param polygonUserName
     */
    public void setPolygonUserName(String polygonUserName) {
        this.polygonUserName = polygonUserName;
    }

    /**
     *
     * @return
     */
    public boolean isFinshed() {
        return finshed;
    }

    /**
     *
     * @param finshed
     */
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

    /**
     *
     * @param categoryConclusion
     */
    public void setCategoryConclusion(int categoryConclusion) {
        this.categoryConclusion = categoryConclusion;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getCustomerAccountable() {
        return customerAccountable;
    }

    /**
     *
     * @param customerAccountable
     */
    public void setCustomerAccountable(String customerAccountable) {
        this.customerAccountable = customerAccountable;
    }
    
    
    
    @Override
    public String toString() {
        return "Report{" + "reportId=" + getReportId() + ", date=" + getDate() + ", buildingId=" + getBuildingId() + ", categoryConclusion=" + getCategoryConclusion() + ", polygonUserName=" + getPolygonUserName() + ", finshed=" + isFinshed() + 
                reportRoomToString()+ reportRoomExtToString() +'}';
    }

    private String reportRoomToString() {
        String reportRoomsString="\n";
        if(getListOfRepRoom() != null){
        for (ReportRoom Room : getListOfRepRoom()) {
            reportRoomsString +="NEWROOM: " + Room.toString() +"\n";
        }
        }
        return reportRoomsString;
    }

    private String reportRoomExtToString() {
        String reportExtString ="\n";
        if(getListOfRepRoom() != null){
        for (ReportExterior exterior : getListOfRepExt() ) {
            reportExtString += "New EXTERIOR: " + exterior.toString();
        }
        }
        return reportExtString;
    }

    /**
     *
     * @return
     */
    public ArrayList<ReportPic> getListOfExtPics() {
        return listOfExtPics;
    }

    /**
     *
     * @param listOfExtPics
     */
    public void setListOfExtPics(ArrayList<ReportPic> listOfExtPics) {
        this.listOfExtPics = listOfExtPics;
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

    /**
     * @return the buildingName
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * @param buildingName the buildingName to set
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    
    
    

}
