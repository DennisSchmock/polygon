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
public class ReportRoom {
    private int repRoomId;
    private String roomName;
    private int reportId;
    private int buildingRoomId;
    private ArrayList<ReportRoomDamage> listOfDamages;
    private ArrayList<ReportRoomInterior> listOfInt;
    private ArrayList<ReportRoomRecommendation> listOfRec;

    /**
     *
     * @param repRoomId  report room ID
     * @param roomName   report room Name
     * @param reportId   report ID
     */
    public ReportRoom(int repRoomId, String roomName, int reportId) {
        this.repRoomId = repRoomId;
        this.roomName = roomName;
        this.reportId = reportId;
    }

    public ReportRoom( String roomName, int reportId) {
        this.roomName = roomName;
        this.reportId = reportId;
    }

    public ReportRoom(String roomName) {
        this.roomName = roomName;
    }
    
    public void setRepRoomId(int repRoomId) {
        this.repRoomId = repRoomId;
    }

    
    public int getRepRoomId() {
        return repRoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getReportId() {
        return reportId;
    }

    public ArrayList<ReportRoomDamage> getListOfDamages() {
        return listOfDamages;
    }

    public void setListOfDamages(ArrayList<ReportRoomDamage> listOfDamages) {
        this.listOfDamages = listOfDamages;
    }

    public ArrayList<ReportRoomInterior> getListOfInt() {
        return listOfInt;
    }
    
        public void setListOfInt(ArrayList<ReportRoomInterior> listOfInt) {
        this.listOfInt = listOfInt;
    }

    public ArrayList<ReportRoomRecommendation> getListOfRec() {
        return listOfRec;
    }

    public void setListOfRec(ArrayList<ReportRoomRecommendation> listOfRec) {
        this.listOfRec = listOfRec;
    }

    public int getBuildingRoomId() {
        return buildingRoomId;
    }

    public void setBuildingRoomId(int buildingRoomId) {
        this.buildingRoomId = buildingRoomId;
    }
    
}
