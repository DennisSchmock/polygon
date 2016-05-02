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
public class ReportRoomInterior implements Serializable{
    private int repRoomIntId;
    private String repRoomIntName;
    private String remark;
    private int repRoomId;

    /**
     *
     * @param repRoomIntId   report room interior ID
     * @param repRoomIntName report room interior name
     * @param remark         report room interior remark
     * @param repRoomId      report room ID references to the room report
     */
    public ReportRoomInterior(int repRoomIntId, String repRoomIntName, String remark, int repRoomId) {
        this.repRoomIntId = repRoomIntId;
        this.repRoomIntName = repRoomIntName;
        this.remark = remark;
        this.repRoomId = repRoomId;
    }
    
    /**
     *
     * @param repRoomIntName
     * @param remark
     * @param repRoomId
     */
    public ReportRoomInterior(String repRoomIntName, String remark, int repRoomId) {
        this.repRoomIntName = repRoomIntName;
        this.remark = remark;
        this.repRoomId = repRoomId;
    }
    /**
     * This is the one that is used to create an object based on the
     * user input in the jsp.
     * @param repRoomIntName Examined part of the Room
     * @param remark The remark the user put in.
     */
    public ReportRoomInterior(String repRoomIntName, String remark) {
        this.repRoomIntName = repRoomIntName;
        this.remark = remark;
    }

    /**
     *
     * @param repRoomIntId
     */
    public void setRepRoomIntId(int repRoomIntId) {
        this.repRoomIntId = repRoomIntId;
    }

    /**
     *
     * @return
     */
    public int getRepRoomIntId() {
        return repRoomIntId;
    }

    /**
     *
     * @return
     */
    public String getRepRoomIntName() {
        return repRoomIntName;
    }

    /**
     *
     * @return
     */
    public String getRemark() {
        return remark;
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
        return "ReportRoomInterior{" + "repRoomIntId=" + repRoomIntId + ", repRoomIntName=" + repRoomIntName + ", remark=" + remark + ", repRoomId=" + repRoomId + '}';
    }
    
    
}
