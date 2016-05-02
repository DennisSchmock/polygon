/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;

/**
 *
 * @author danie
 */
public class ReportRoomMoist implements Serializable {
    
    private int moistMeasureId;
    private String moistMeasured;
    private String measurePoint;
    private int reportRoom;

    /**
     *
     * @param moistMeasureId
     * @param moistMeasured
     * @param measurePoint
     * @param reportRoom
     */
    public ReportRoomMoist(int moistMeasureId, String moistMeasured, String measurePoint, int reportRoom) {
        this.moistMeasureId = moistMeasureId;
        this.moistMeasured = moistMeasured;
        this.measurePoint = measurePoint;
        this.reportRoom = reportRoom;
    }
    
    /**
     * This is the one to be used in the frontcontroller
     * to create an object based on the fields in the jsp!
     * @param moistMeasured
     * @param measurePoint
     */
    public ReportRoomMoist(String moistMeasured, String measurePoint) {
        this.moistMeasured = moistMeasured;
        this.measurePoint = measurePoint;
    }

    /**
     *
     * @return
     */
    public int getMoistMeasureId() {
        return moistMeasureId;
    }

    /**
     *
     * @param moistMeasureId
     */
    public void setMoistMeasureId(int moistMeasureId) {
        this.moistMeasureId = moistMeasureId;
    }

    /**
     *
     * @return
     */
    public String getMoistMeasured() {
        return moistMeasured;
    }

    /**
     *
     * @param moistMeasured
     */
    public void setMoistMeasured(String moistMeasured) {
        this.moistMeasured = moistMeasured;
    }

    /**
     *
     * @return
     */
    public String getMeasurePoint() {
        return measurePoint;
    }

    /**
     *
     * @param measurePoint
     */
    public void setMeasurePoint(String measurePoint) {
        this.measurePoint = measurePoint;
    }

    /**
     *
     * @return
     */
    public int getReportRoom() {
        return reportRoom;
    }

    /**
     *
     * @param reportRoom
     */
    public void setReportRoom(int reportRoom) {
        this.reportRoom = reportRoom;
    }

    @Override
    public String toString() {
        return "ReportRoomMoist{" + "moistMeasureId=" + moistMeasureId + ", moistMeasured=" + moistMeasured + ", measurePoint=" + measurePoint + ", reportRoom=" + reportRoom + '}';
    }
    
    
    
}
