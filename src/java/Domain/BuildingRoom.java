/*
 * Class for information of the room of a building
 * Most importantly gives the option to iterate over the rooms in a particular building
 */
package Domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author daniel
 */
public class BuildingRoom implements Serializable{
    private int roomId;
    private String roomName;
    private int floorid;
    
    
    // Do we keep info about damages in the object here as well?

    
    public BuildingRoom(int roomId, String roomName){
        this.roomId = roomId;
        this.roomName = roomName;
    }
    
    /**
     *
     * @param roomId room ID
     * @param roomName name of the room
     * @param floorid floor ID reference to which floor
     */
    public BuildingRoom(int roomId, String roomName, int floorid){
        this.roomId = roomId;
        this.roomName = roomName;
        this.floorid = floorid;
    }

    public BuildingRoom(String roomName, int floorid) {
        this.roomName = roomName;
        this.floorid = floorid;
    }
    
    

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBdgId() {
        return floorid;
    }

    public void setfloorid(int floorid) {
        this.floorid = floorid;
    }

    public int getFloorid() {
        return floorid;
    }

}
