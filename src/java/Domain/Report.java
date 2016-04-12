/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.ArrayList;

/**
 *
 * @author CJS
 */
public class Report {
    private int reportId;
    private String date;
    private ArrayList<ReportRoom> listOfRepRoom;
    private ArrayList<ReportExterior> listOfRepExt;
    private int buildingId;
    private int categoryConclusion;
    private String polygonUserName;
    private boolean finshed;
    /**
     *
     * @param reportId  report number
     * @param date   date
     * @param buildingId    building's ID
     * @param catCon  category conclusion
     */
    public Report(int reportId, String date, int buildingId, int catCon) {
        this.reportId = reportId;
        this.date = date;
        this.buildingId = buildingId;
        this.categoryConclusion = catCon;
    }
    
    public Report(String date, int buildingId, int catCon) {
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
    
    

    public ReportRoom getReportRoom(int id){
        return this.listOfRepRoom.get(id);
    }
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
    
    public int getReportId() {
        return reportId;
    }
    
    

    public String getDate() {
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

}
