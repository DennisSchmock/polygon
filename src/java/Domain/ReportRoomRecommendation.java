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
public class ReportRoomRecommendation {
    private int repRoomRecId;
    private String recommendation;
    private int repRoomId;

    /**
     *
     * @param repRoomRecId    report room recommendation ID
     * @param recommendation  room recommendation
     * @param repRoomId       report room ID references to the report room
     */
    public ReportRoomRecommendation(int repRoomRecId, String recommendation, int repRoomId) {
        this.repRoomRecId = repRoomRecId;
        this.recommendation = recommendation;
        this.repRoomId = repRoomId;
    }

    public int getRepRoomRecId() {
        return repRoomRecId;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public int getRepRoomId() {
        return repRoomId;
    }
    
}
