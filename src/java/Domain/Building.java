/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author dennisschmock
 */
public class Building implements Serializable{
    private int bdgId;
    private String buildingName;
    private String streetAddress;
    private String streetNumber; // its a string because it can take a number like 12b
    private int zipCode;
    private int buildingYear;
    private double buildingSize;
    private String useOfBuilding;
    private String buildingPic;   // Change to string to combine id with extension i.e. 23 + "." + "png"
    private ArrayList<Report> listOfReports;
    private ArrayList<BuildingFloor> listOfFloors;
    private ArrayList<BuildingFiles> listOfFiles;
    private int custId;
    private int buildingState;

    /**
     *
     */
    public Building() {
    }
    
    /**
     *
     * @param buildingName
     * @param streetAddress
     * @param streetNumber
     * @param zipCode
     * @param buildingYear
     * @param buildingSize
     * @param useOfBuilding
     */
    public Building(String buildingName, String streetAddress, String streetNumber, int zipCode, int buildingYear, double buildingSize, String useOfBuilding) {
        this.buildingName = buildingName;
        this.streetAddress = streetAddress;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.buildingYear = buildingYear;
        this.buildingSize = buildingSize;
        this.useOfBuilding = useOfBuilding;
        buildingPic="0.png";
    }

    /**
     *
     * @param bdgId
     * @param buildingName
     * @param buildingSize
     * @param streetAddress
     * @param streetNumber
     * @param buildingYear
     * @param zipCode
     * @param useOfBuilding
     * @param custId
     */
    public Building(int bdgId, String buildingName,double buildingSize, String streetAddress, String streetNumber, int buildingYear, int zipCode, String useOfBuilding, int custId) {
        this.bdgId = bdgId;
        this.buildingName = buildingName;
        this.streetAddress = streetAddress;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.buildingYear = buildingYear;
        this.buildingSize = buildingSize;
        this.useOfBuilding = useOfBuilding;
        //this.buildingPic = buildingPic;
        this.custId = custId;
        buildingPic="0.png";
    }

    /**
     *
     * @param bdgId
     * @param buildingName
     * @param streetAddress
     * @param streetNumber
     * @param zipCode
     * @param buildingYear
     * @param buildingSize
     * @param useOfBuilding
     * @param custId
     * @param buildingState
     */
    public Building(int bdgId, String buildingName, String streetAddress, String streetNumber, int zipCode, int buildingYear, double buildingSize, String useOfBuilding, int custId, int buildingState) {
        this.bdgId = bdgId;
        this.buildingName = buildingName;
        this.streetAddress = streetAddress;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.buildingYear = buildingYear;
        this.buildingSize = buildingSize;
        this.useOfBuilding = useOfBuilding;
//        this.buildingPic = buildingPic;
        this.custId = custId;
        this.buildingState = buildingState;
    }

    /**
     * This will sort the status of the orders
     */
    
    public static Comparator<Building> bdgState = new Comparator<Building>() {

	public int compare(Building b1, Building b2) {

	   int state01 = b1.getBuildingState();
	   int state02 = b2.getBuildingState();

	   /*For ascending order*/
	   return state01-state02;

   }};
    
    /**
     * The purpose of this method, is to return a specific room based on room id.
     * @param RoomId
     * @return
     */
    public BuildingRoom returnARoom(int RoomId){
       for (BuildingFloor floor : listOfFloors) {
           for (BuildingRoom room : floor.getListOfRooms()) {
               if (RoomId==room.getRoomId()){
                   return room;
               }
           }
           
       }
       return null;
   }
    @Override
    public String toString() {
        return "Building{" + "bdgId=" + bdgId + ", buildingName=" + buildingName + ", streetAddress=" + streetAddress + ", streetNumber=" + streetNumber + ", zipCode=" + zipCode + ", buildingYear=" + buildingYear + ", buildingSize=" + buildingSize + ", useOfBuilding=" + useOfBuilding + ", listOfReports=" + listOfReports + ", custId=" + custId + '}';
    }
    
    //Getters and setters below this line.

    /**
     *
     * @return
     */
    public int getBuildingState() {
        return buildingState;
    }

    /**
     *
     * @param buildingState
     */
    public void setBuildingState(int buildingState) {
        this.buildingState = buildingState;
    }
    
    /**
     *
     * @return
     */
    public int getBdgId() {
        return bdgId;
    }

    /**
     *
     * @param bdgId
     */
    public void setBdgId(int bdgId) {
        this.bdgId = bdgId;
    }

    /**
     *
     * @return
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     *
     * @param buildingName
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     *
     * @return
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     *
     * @param streetAddress
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     *
     * @return
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     *
     * @param streetNumber
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     *
     * @return
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     *
     * @param zipCode
     */
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    /**
     *
     * @return
     */
    public int getBuildingYear() {
        return buildingYear;
    }

    /**
     *
     * @param buildingYear
     */
    public void setBuildingYear(int buildingYear) {
        this.buildingYear = buildingYear;
    }

    /**
     *
     * @return
     */
    public double getBuildingSize() {
        return buildingSize;
    }

    /**
     *
     * @param buildingSize
     */
    public void setBuildingSize(double buildingSize) {
        this.buildingSize = buildingSize;
    }

    /**
     *
     * @return
     */
    public String getUseOfBuilding() {
        return useOfBuilding;
    }

    /**
     *
     * @param useOfBuilding
     */
    public void setUseOfBuilding(String useOfBuilding) {
        this.useOfBuilding = useOfBuilding;
    }

    /**
     *
     * @return
     */
    public ArrayList<Report> getListOfReports() {
        return listOfReports;
    }

    /**
     *
     * @param listOfReports
     */
    public void setListOfReports(ArrayList<Report> listOfReports) {
        this.listOfReports = listOfReports;
    }
    
    /**
     *
     * @return
     */
    public int getCustId() {
        return custId;
    }

    /**
     *
     * @param custId
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /**
     *
     * @return
     */
    public String getBuildingPic() {
        return buildingPic;
    }

    /**
     *
     * @param building_pic
     */
    public void setBuilding_pic(String building_pic) {
        this.buildingPic = building_pic;
    }

    /**
     *
     * @return
     */
    public ArrayList<BuildingFloor> getListOfFloors() {
        return listOfFloors;
    }

    /**
     *
     * @param listOfFloors
     */
    public void setListOfFloors(ArrayList<BuildingFloor> listOfFloors) {
        this.listOfFloors = listOfFloors;
    }
  
    /**
     *
     * @return
     */
    public ArrayList<BuildingFiles> getListOfFiles() {
        if (listOfFiles==null) listOfFiles=new ArrayList();
        return listOfFiles;
    }

    /**
     *
     * @param listOfFiles
     */
    public void setListOfFiles(ArrayList<BuildingFiles> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }
    
     
    
}
