/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The purpose of this object, is to create a ReportRoom object.
 * @author CJS
 */
public class ReportRoom implements Serializable{
    private int repRoomId;
    private String roomName;
    private int reportId;
    private String roomFloor;
    private int buildingRoomId;
    private ReportRoomMoist moist;
    private ArrayList<ReportRoomDamage> listOfDamages = new ArrayList<>();
    private ArrayList<ReportRoomInterior> listOfInt  = new ArrayList<>();;
    private ArrayList<ReportRoomRecommendation> listOfRec  = new ArrayList<>();
    private ArrayList<ReportPic> rrPic = new ArrayList();

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

    /**
     * @param roomName
     * @param reportId
     * @param buildingRoomId
     */
    public ReportRoom(String roomName, int reportId, int buildingRoomId) {
        this.roomName = roomName;
        this.reportId = reportId;
        this.buildingRoomId = buildingRoomId;
    }
    
    /**
     * Used in the frontController to create the object
     * based on field from JSP.
     * @param roomName
     * @param buildingRoomId
     */
    public ReportRoom( String roomName, int buildingRoomId) {
        this.roomName = roomName;
        this.buildingRoomId = buildingRoomId;
    }

        @Override
    public String toString() {
        return "ReportRoom{" + "repRoomId=" + repRoomId + ", roomName=" + roomName + ", reportId=" + reportId + ", roomFloor=" + roomFloor + ", buildingRoomId=" + buildingRoomId + ", moist=" + moist + '}'
                + roomDamages() + roomInterior() + roomRec();
    }

    private String roomDamages() {
        String damageString="\n";
        for (ReportRoomDamage damage : listOfDamages) {
            damageString += damage.toString() + "\n";
        }
        return damageString;
    }

    private String roomInterior() {
        String InteriorString="\n";
        for (ReportRoomInterior interior : listOfInt) {
            InteriorString += interior.toString() + "\n";
        }
        return InteriorString;
    }

    private String roomRec() {
        String RecomendationString="\n";
        for (ReportRoomRecommendation recommendation : listOfRec) {
            RecomendationString += recommendation.toString() + "\n";
        }
        return RecomendationString;
    }
    
    
    //Getters and setters below this point
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

    /**
     * @return the moist
     */
    public ReportRoomMoist getMoist() {
        return moist;
    }

    /**
     * @param moist the moist to set
     */ 
    public void setMoist(ReportRoomMoist moist) {
        this.moist = moist;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
    }



    public ArrayList<ReportPic> getRrPic() {
        return rrPic;
    }

    public void setRrPic(ArrayList<ReportPic> rrPic) {
        this.rrPic = rrPic;
    }

    
    
    
    
    
}
