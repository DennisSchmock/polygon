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
public class ReportRoomInterior {
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

    public int getRepRoomIntId() {
        return repRoomIntId;
    }

    public String getRepRoomIntName() {
        return repRoomIntName;
    }

    public String getRemark() {
        return remark;
    }

    public int getRepRoomId() {
        return repRoomId;
    }
    
}
