/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author danie
 */
public class ReportRoomMoist {
    
    private int moistMeasureId;
    private int moistMeasured;
    private String measurePoint;
    private int reportRoom;

    public ReportRoomMoist(int moistMeasureId, int moistMeasured, String measurePoint, int reportRoom) {
        this.moistMeasureId = moistMeasureId;
        this.moistMeasured = moistMeasured;
        this.measurePoint = measurePoint;
        this.reportRoom = reportRoom;
    }
    
    public ReportRoomMoist(int moistMeasured, String measurePoint) {
        this.moistMeasured = moistMeasured;
        this.measurePoint = measurePoint;
    }

    
    
    
    public int getMoistMeasureId() {
        return moistMeasureId;
    }

    public void setMoistMeasureId(int moistMeasureId) {
        this.moistMeasureId = moistMeasureId;
    }

    public int getMoistMeasured() {
        return moistMeasured;
    }

    public void setMoistMeasured(int moistMeasured) {
        this.moistMeasured = moistMeasured;
    }

    public String getMeasurePoint() {
        return measurePoint;
    }

    public void setMeasurePoint(String measurePoint) {
        this.measurePoint = measurePoint;
    }

    public int getReportRoom() {
        return reportRoom;
    }

    public void setReportRoom(int reportRoom) {
        this.reportRoom = reportRoom;
    }
    
    
    
}
