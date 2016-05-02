
package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CJS
 */
public class BuildingFloor implements Serializable{
    private int floorId;
    private int floorNumber;
    private double floorSize;
    private int totalRooms;
    private int buildingId;
    private ArrayList<BuildingRoom> listOfRooms;
    private ArrayList<Floorplan> floorplans;

    /**
     *
     * @param floorNumber floor number 
     * @param floorSize size of the floor in m2
     * @param totalRooms total number of rooms in a certain floor
     * @param buildingId ID reference to the building
     */
    public BuildingFloor(int floorNumber, double floorSize, int totalRooms, int buildingId) {
        this.floorNumber = floorNumber;
        this.floorSize = floorSize;
        this.totalRooms = totalRooms;
        this.buildingId = buildingId;
    }
    
    /**
     *
     * @param floorNumber
     * @param floorSize
     * @param buildingId
     */
    public BuildingFloor(int floorNumber, double floorSize, int buildingId) {
        this.floorNumber = floorNumber;
        this.floorSize = floorSize;
        this.buildingId = buildingId;
    }

    /**
     *
     * @param floorId
     * @param floorNumber
     * @param floorSize
     * @param totalRooms
     * @param buildingId
     */
    public BuildingFloor(int floorId, int floorNumber, double floorSize, int totalRooms, int buildingId) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.floorSize = floorSize;
        this.totalRooms = totalRooms;
        this.buildingId = buildingId;
    }

    /**
     *
     * @return
     */
    public int getFloorId() {
        return floorId;
    }

    /**
     *
     * @param floorId
     */
    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    /**
     *
     * @return
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     *
     * @param floorNumber
     */
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    /**
     *
     * @return
     */
    public double getFloorSize() {
        return floorSize;
    }

    /**
     *
     * @param floorSize
     */
    public void setFloorSize(double floorSize) {
        this.floorSize = floorSize;
    }

    /**
     *
     * @return
     */
    public int getTotalRooms() {
        return totalRooms;
    }

    /**
     *
     * @param totalRooms
     */
    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    /**
     *
     * @return
     */
    public int getBuildingId() {
        return buildingId;
    }

    /**
     *
     * @param buildingId
     */
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /**
     *
     * @return
     */
    public ArrayList<BuildingRoom> getListOfRooms() {
        return listOfRooms;
    }

    /**
     *
     * @param listOfRooms
     */
    public void setListOfRooms(ArrayList<BuildingRoom> listOfRooms) {
        this.listOfRooms = listOfRooms;
    }
    
    /**
     *
     * @param roomId
     * @return
     */
    public BuildingRoom getARoom(int roomId){
        for (BuildingRoom room : listOfRooms) {
            if(room.getRoomId()==roomId){
                return room; 
            }
        }
            return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<Floorplan> getFloorplans() {
        if (floorplans==null) floorplans= new ArrayList();
        return floorplans;
    }
    
    /**
     *
     * @param f
     */
    public void addFloorplan(Floorplan f){
        if (floorplans==null) floorplans= new ArrayList();
        floorplans.add(f);
    }

    /**
     *
     * @param floorplans
     */
    public void setFloorplans(ArrayList<Floorplan> floorplans) {
        this.floorplans = floorplans;
    }
    
}
