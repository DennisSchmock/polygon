
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
    
     public BuildingFloor(int floorNumber, double floorSize, int buildingId) {
        this.floorNumber = floorNumber;
        this.floorSize = floorSize;
        this.buildingId = buildingId;
    }

    public BuildingFloor(int floorId, int floorNumber, double floorSize, int totalRooms, int buildingId) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.floorSize = floorSize;
        this.totalRooms = totalRooms;
        this.buildingId = buildingId;
    }

  

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public double getFloorSize() {
        return floorSize;
    }

    public void setFloorSize(double floorSize) {
        this.floorSize = floorSize;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public ArrayList<BuildingRoom> getListOfRooms() {
        return listOfRooms;
    }

    public void setListOfRooms(ArrayList<BuildingRoom> listOfRooms) {
        this.listOfRooms = listOfRooms;
    }
    
    public BuildingRoom getARoom(int roomId){
        for (BuildingRoom room : listOfRooms) {
            if(room.getRoomId()==roomId){
                return room; 
            }
        }
            return null;
    }

    public ArrayList<Floorplan> getFloorplans() {
        if (floorplans==null) floorplans= new ArrayList();
        return floorplans;
    }
    
    public void addFloorplan(Floorplan f){
        if (floorplans==null) floorplans= new ArrayList();
        floorplans.add(f);
    }

    public void setFloorplans(ArrayList<Floorplan> floorplans) {
        this.floorplans = floorplans;
    }
    
}
