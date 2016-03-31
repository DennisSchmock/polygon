/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author CJS
 */
public class ReportRoom {
    private int repRoomId;
    private String roomName;
    private int reportId;

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

    public int getRepRoomId() {
        return repRoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getReportId() {
        return reportId;
    }
    
}
