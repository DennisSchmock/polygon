/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;

/**
 *
 * @author CJS
 */
public class ReportRoomRecommendation implements Serializable{

    private int repRoomRecId;
    private String recommendation;
    private int repRoomId;

    /**
     *
     * @param repRoomRecId report room recommendation ID
     * @param recommendation room recommendation
     * @param repRoomId report room ID references to the report room
     */
    public ReportRoomRecommendation(int repRoomRecId, String recommendation, int repRoomId) {
        this.repRoomRecId = repRoomRecId;
        this.recommendation = recommendation;
        this.repRoomId = repRoomId;
    }

    /**
     *
     * @param recommendation
     * @param repRoomId
     */
    public ReportRoomRecommendation(String recommendation, int repRoomId) {
        this.recommendation = recommendation;
        this.repRoomId = repRoomId;
    }

    /**
     *
     * @param recommendation
     */
    public ReportRoomRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    /**
     *
     * @param repRoomRecId
     */
    public void setRepRoomRecId(int repRoomRecId) {
        this.repRoomRecId = repRoomRecId;
    }

    /**
     *
     * @return
     */
    public int getRepRoomRecId() {
        return repRoomRecId;
    }

    /**
     *
     * @return
     */
    public String getRecommendation() {
        return recommendation;
    }

    /**
     *
     * @return
     */
    public int getRepRoomId() {
        return repRoomId;
    }

    @Override
    public String toString() {
        return "ReportRoomRecommendation{" + "repRoomRecId=" + repRoomRecId + ", recommendation=" + recommendation + ", repRoomId=" + repRoomId + '}';
    }
    
    

}
