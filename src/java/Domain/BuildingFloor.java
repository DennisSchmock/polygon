
package Domain;

import java.util.List;

/**
 *
 * @author CJS
 */
public class BuildingFloor {
    private int floorId;
    private int floorNumber;
    private double floorSize;
    private int totalRooms;
    private int buildingId;
    private List<BuildingRoom> listOfRooms;

    public BuildingFloor(int floorNumber, double floorSize, int totalRooms, int buildingId) {
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

    public List<BuildingRoom> getListOfRooms() {
        return listOfRooms;
    }

    public void setListOfRooms(List<BuildingRoom> listOfRooms) {
        this.listOfRooms = listOfRooms;
    }
    
    
}
