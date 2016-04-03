/*
 * Class for information of the room of a building
 * Most importantly gives the option to iterate over the rooms in a particular building
 */
package Domain;

import java.util.List;

/**
 *
 * @author daniel
 */
public class BuildingRoom {
    private int roomId;
    private String roomName;
    private List damages;
    private List remarks;
    private List moist;
    
    
    public BuildingRoom(int roomId, String roomName){
        this.roomId = roomId;
        this.roomName = roomName;
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

    public List getDamages() {
        return damages;
    }

    public void setDamages(List damages) {
        this.damages = damages;
    }

    public List getRemarks() {
        return remarks;
    }

    public void setRemarks(List remarks) {
        this.remarks = remarks;
    }

    public List getMoist() {
        return moist;
    }

    public void setMoist(List moist) {
        this.moist = moist;
    }
    
    
}
