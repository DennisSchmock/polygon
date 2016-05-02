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

    /**
     *
     * @param roomId
     * @param roomName
     */

    
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

    /**
     *
     * @param roomName
     * @param floorid
     */
    public BuildingRoom(String roomName, int floorid) {
        this.roomName = roomName;
        this.floorid = floorid;
    }
    
    /**
     *
     * @return
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     *
     * @param roomName
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     *
     * @return
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     *
     * @param roomId
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     *
     * @return
     */
    public int getBdgId() {
        return floorid;
    }

    /**
     *
     * @param floorid
     */
    public void setfloorid(int floorid) {
        this.floorid = floorid;
    }

    /**
     *
     * @return
     */
    public int getFloorid() {
        return floorid;
    }

}
