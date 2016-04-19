/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author dennisschmock
 */
public class ReportFloor implements Serializable{
    
    private int reportId;
    private int buildingId;
    private int floorId;
    private int floorNumber;
    private int squareMeters;
    private ArrayList<ReportRoom> reportRooms = new ArrayList<>();
    
    public ReportFloor (int floorId,int floorNumber,int m2, int reportId,int buildingId){
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.buildingId = buildingId;
        this.reportId = reportId;
    }

    /**
     * @return the reportId
     */
    public int getReportId() {
        return reportId;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(int reportId) {
        this.reportId = reportId;
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
     * @return the floorId
     */
    public int getFloorId() {
        return floorId;
    }

    /**
     * @param floorId the floorId to set
     */
    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    /**
     * @return the floorNumber
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * @param floorNumber the floorNumber to set
     */
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    /**
     * @return the squareMeters
     */
    public int getSquareMeters() {
        return squareMeters;
    }

    /**
     * @param squareMeters the squareMeters to set
     */
    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    /**
     * @return the reportRooms
     */
    public ArrayList<ReportRoom> getReportRooms() {
        return reportRooms;
    }

    /**
     * @param reportRooms the reportRooms to set
     */
    public void setReportRooms(ArrayList<ReportRoom> reportRooms) {
        this.reportRooms = reportRooms;
    }
    
}
