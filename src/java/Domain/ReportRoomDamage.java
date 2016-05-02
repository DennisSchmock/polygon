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
public class ReportRoomDamage implements Serializable {

    private int repRoomDmgId;
    private String damageTime;
    private String place;
    private String whatHappened;
    private String whatIsRepaired;
    private String damageType;
    private int repRoomId;

    /**
     *
     * @param repRoomDmgId report room damage ID
     * @param damageTime date when the damage was discovered
     * @param place location of the damage
     * @param whatHappened cause of the damage
     * @param whatIsRepaired part that has been repaired
     * @param damageType type of damage
     * @param repRoomId report room ID
     */
    public ReportRoomDamage(int repRoomDmgId, String damageTime, String place, String whatHappened, String whatIsRepaired, String damageType, int repRoomId) {
        this.repRoomDmgId = repRoomDmgId;
        this.damageTime = damageTime;
        this.place = place;
        this.whatHappened = whatHappened;
        this.damageType = damageType;
        this.repRoomId = repRoomId;
        this.whatIsRepaired = whatIsRepaired;
    }

    /**
     *
     * @param damageTime
     * @param place
     * @param whatHappened
     * @param whatIsRepaired
     * @param damageType
     * @param repRoomId
     */
    public ReportRoomDamage(String damageTime, String place, String whatHappened, String whatIsRepaired, String damageType, int repRoomId) {
        this.damageTime = damageTime;
        this.place = place;
        this.whatHappened = whatHappened;
        this.damageType = damageType;
        this.repRoomId = repRoomId;
        this.whatIsRepaired = whatIsRepaired;
    }

    /**
     * This is the constructor to be used in the frontcontrol to create the object
     * based on the field in the jsp.
     * @param damageTime
     * @param place
     * @param whatHappened
     * @param whatIsRepaired
     * @param damageType
     */
    public ReportRoomDamage(String damageTime, String place, String whatHappened, String whatIsRepaired, String damageType) {
        this.damageTime = damageTime;
        this.place = place;
        this.whatHappened = whatHappened;
        this.damageType = damageType;
        this.whatIsRepaired = whatIsRepaired;
    }

    /**
     *
     * @param repRoomDmgId
     */
    public void setRepRoomDmgId(int repRoomDmgId) {
        this.repRoomDmgId = repRoomDmgId;
    }

    /**
     *
     * @return
     */
    public int getRepRoomDmgId() {
        return repRoomDmgId;
    }

    /**
     *
     * @return
     */
    public String getDamageTime() {
        return damageTime;
    }

    /**
     *
     * @return
     */
    public String getPlace() {
        return place;
    }

    /**
     *
     * @return
     */
    public String getWhatHappened() {
        return whatHappened;
    }

    /**
     *
     * @return
     */
    public String getWhatIsRepaired() {
        return whatIsRepaired;
    }

    /**
     *
     * @return
     */
    public String getDamageType() {
        return damageType;
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
        return "ReportRoomDamage{" + "repRoomDmgId=" + repRoomDmgId + ", damageTime=" + damageTime + ", place=" + place + ", whatHappened=" + whatHappened + ", whatIsRepaired=" + whatIsRepaired + ", damageType=" + damageType + ", repRoomId=" + repRoomId + '}';
    }

    
}
