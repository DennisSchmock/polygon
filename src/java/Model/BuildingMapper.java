/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Building;
import Domain.BuildingFile;
import Domain.BuildingFloor;
import Domain.BuildingRoom;
import Domain.BuildingFiles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for everything going in and out of the database with the table
 * building
 *
 * @author danie
 */
public class BuildingMapper {

    /**
     * saves the building object in the database
     *
     * @param b building to be added to database
     * @param con connection to database
     */
    public Building saveNewBuildingDB(Building b, Connection con) {
        String sqlString = "insert into building(building_name, building_m2, "
                + "building_adress, building_housenumber, building_zip, "
                + "building_use, building_buildyear,customer_id) values(?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, b.getBuildingName());
            statement.setDouble(2, b.getBuildingSize());
            statement.setString(3, b.getStreetAddress());
            statement.setString(4, b.getStreetNumber());
            statement.setInt(5, b.getZipCode());
            statement.setString(6, b.getUseOfBuilding());
            statement.setInt(7, b.getBuildingYear());
            statement.setInt(8, b.getCustId());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {   // Changed from !rs.next() as this didn't return the key
                b.setBdgId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR IN SaveNewBuildingDB " + ex.getMessage());
        }
        return b;
    }

    public String saveBuildingPic(int buildId, String filename, Connection con) {
        int imgId = 0;
        System.out.println("build id");
        System.out.println(buildId);

        try {
            String sqlString = "insert into building_pic(filename,building_id) values(?,?)";
            PreparedStatement statement = con.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, filename);
            statement.setInt(2, buildId);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {   // Changed from !rs.next() as this didn't return the key
                imgId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BuildingMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        String filePath = filename;
        System.out.println(filePath);
        return filePath;
    }

    /**
     * Loading the list of buildings for an specific customer in the database
     *
     * @param customerID ID of the customer that is to be loaded
     * @param con Connection to Database
     * @return An list of buildings related to the customerID
     */
    public List<Building> getListOfBuildingsBM(int customerID, Connection con) {
        List<Building> buildingList = new ArrayList<>();
        String sqlString = "SELECT * FROM building where customer_id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, customerID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Building temp = new Building(
                        rs.getString("building_name"),
                        rs.getString("building_adress"),
                        rs.getString("building_housenumber"),
                        rs.getInt("building_zip"),
                        rs.getInt("building_buildyear"),
                        rs.getDouble("building_m2"),
                        rs.getString("building_use"));
                temp.setCustId(rs.getInt("customer_id"));
                temp.setBdgId(rs.getInt("idbuilding"));
                buildingList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception in BUILDINGMAPPER: " + ex);
        }
        return buildingList;
    }

    /**
     * Updates the right building in the database
     *
     * @param updatedBuildObj Holds all the information that is needed to update
     * the building tuble in the database
     * @param con Connection to the database
     */
    public void updateBuildingBm(Building updatedBuildObj, Connection con) {
        String sqlString = "UPDATE building "
                + "set building_name=?, "
                + " building_m2=?, "
                + " building_adress=?, "
                + " building_housenumber=?, "
                + " building_buildyear=?, "
                + " building_zip=?, "
                + " building_use=?, "
                + " customer_id=? "
                + "where idbuilding=?;";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setString(1, updatedBuildObj.getBuildingName());
            statement.setDouble(2, updatedBuildObj.getBuildingSize());
            statement.setString(3, updatedBuildObj.getStreetAddress());
            statement.setString(4, updatedBuildObj.getStreetNumber());
            statement.setInt(5, updatedBuildObj.getBuildingYear());
            statement.setInt(6, updatedBuildObj.getZipCode());
            statement.setString(7, updatedBuildObj.getUseOfBuilding());
            statement.setInt(8, updatedBuildObj.getCustId());
            statement.setInt(9, updatedBuildObj.getBdgId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException ex) {
            System.out.println("SQL ERROR IN UPDATEBUILDINGBM " + ex);
        }
    }

    /**
     * Loads info from tuble with the buildingID in the table building. Creates
     * a building object based on that, an returns that.
     *
     * @param buildingID BuildingID for the requested tuble
     * @param con Connection to the Database
     * @return An object of the building
     */
    public Building getBuildingBM(int buildingID, Connection con) {

        String sqlString = "SELECT * FROM building where customer_id=?";
        Building temp = null;
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                temp = new Building(
                        rs.getString("building_name"),
                        rs.getString("building_adress"),
                        rs.getString("building_housenumber"),
                        rs.getInt("building_zip"),
                        rs.getInt("building_buildyear"),
                        rs.getDouble("building_m2"),
                        rs.getString("building_use"));
                temp.setCustId(rs.getInt("customer_id"));
                temp.setBdgId(rs.getInt("idbuilding"));
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR IN UPDATEBUILDINGBM " + ex);
        }
        return temp;
    }

    /**
     * The purpose of this method is to get a building from a specific building id
     * @param bdgId
     * @param con
     * @return
     */
    public Building getBuilding(int bdgId, Connection con) {
        Building b;
        String SQLString = "select * from building where idbuilding=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, bdgId);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            int buildingId = rs.getInt("idbuilding");
            String name = rs.getString("building_name");
            double size = rs.getDouble("building_m2");
            String address = rs.getString("building_adress");
            String houseNumber = rs.getString("building_housenumber");
            int yr = rs.getInt("building_buildyear");
            int zip = rs.getInt("building_zip");
            String use = rs.getString("building_use");
            int cusId = rs.getInt("customer_id");

            b = new Building(buildingId, name, size, address, houseNumber, yr, zip, use, cusId);
            b.setListOfFloors(getFloorsList(buildingId, con));

            return b;
        } catch (Exception e) {
            System.out.println("Fail in NewBuildingMapper-getBuilding");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * The purpofse of this method is to add a floor object to the database
     * @param bf floor object
     * @param con
     */
    public void addFloor(BuildingFloor bf, Connection con) {
        String SQLString = "insert into building_floor(floor_number,floor_size,total_rooms,idbuilding) values (?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            //insert a tuple and set the values
            statement.setInt(1, bf.getFloorNumber());
            statement.setDouble(2, bf.getFloorSize());
            statement.setInt(3, bf.getTotalRooms());
            statement.setInt(4, bf.getBuildingId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                bf.setFloorId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving new floor - addFloor");
            System.out.println(e.getMessage());
        }
    }

    /**
     * The purpose of this method is to get a list of Floors connect to a certain building
     * @param buildingId the id of the building
     * @param con 
     * @return a list of floors
     */
    public ArrayList<BuildingFloor> getFloorsList(int buildingId, Connection con) {
        ArrayList<BuildingFloor> floorsList = new ArrayList();
        String sqlString = "SELECT * FROM building_floor where idbuilding=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, buildingId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BuildingFloor temp = new BuildingFloor(
                        rs.getInt("floor_id"),
                        rs.getInt("floor_number"),
                        rs.getDouble("floor_size"),
                        rs.getInt("total_rooms"),
                        rs.getInt("idbuilding"));
                temp.setListOfRooms(getRoomList(temp.getFloorId(), con));
                floorsList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception in BUILDINGMAPPER/get floorslist: " + ex);
        }
        return floorsList;
    }

    /**
     *The purpose of this method is to map a list of Rooms for a certain floor
     * @param floorId the id of the floor we need
     * @param con
     * @return a list of rooms
     */
    public ArrayList<BuildingRoom> getRoomList(int floorId, Connection con) {
        ArrayList<BuildingRoom> roomList = new ArrayList();
        String sqlString = "SELECT * FROM building_room where floor_id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, floorId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BuildingRoom temp = new BuildingRoom(
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getInt("floor_id"));
                roomList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception in BUILDINGMAPPER/getroom list: " + ex);
        }
        return roomList;
    }
        

    public String getLatestBuildingImage(int buildingId, Connection con) {
        String filename = null;
        System.out.println("getLatestBuildingImage");
        System.out.println(buildingId);
        String SQLString = "select * from building_pic where building_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            System.out.println("statement prepared");
            statement.setInt(1, buildingId);
            ResultSet rs = statement.executeQuery();
            System.out.println("query executed");

            while (rs.next()){
            filename = rs.getString("filename");
            }
            
        } catch (Exception e) {
            System.out.println("Fail in NewBuildingMapper-getLatestBuildingPic");
            System.out.println(e.getMessage());
            return null;
        }
        
        
        return filename;
    }

    /**
     * Saves the buildingRoom object in the database and returns it
     * with an Unique ID.
     * @param newRoom The building object to be inserted.
     * @param con Connection to the database
     * @return The newly inserted building object
     */
    public BuildingRoom saveBuildingRoom(BuildingRoom newRoom, Connection con) {
        String sql = "insert into building_room (room_name,floor_id) values  (?,?) ";
        try {
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newRoom.getRoomName());
            statement.setInt(2, newRoom.getFloorid());
            statement.executeUpdate();
            
           ResultSet rs = statement.getGeneratedKeys();
           
           if(rs.next()){
               newRoom.setRoomId(rs.getInt(1));
           }
        } catch (SQLException ex) {
            System.out.println("Error in SQL SavebuildingRoom " + ex );
        }
        
        return newRoom;
    }

    /**
     * Loads an BuildingFloor based on the ID in the parameter.
     * Creates an object of BuildingFloor and returns it.
     * @param floorid For the building to be loaded
     * @param con Connection to Database
     * @return  Loaded Buildingfloor object
     */
    public BuildingFloor getBuildingFloorBM(int floorid, Connection con) {
        String sql = "Select * from building_floor where floor_id=? ";
        BuildingFloor bf = null;
        try {
            PreparedStatement statement =  con.prepareStatement(sql);
            statement.setInt(1, floorid);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                bf = new BuildingFloor
               (rs.getInt("floor_number"), 
                rs.getDouble("floor_size"), 
                rs.getInt("total_rooms"), 
                rs.getInt("idbuilding"));
                bf.setFloorId(floorid);
            }
        } catch (SQLException ex) {
            System.out.println("Error in Get Building Floor BM: " + ex);
        }
        return bf;
    }
    
    /**
     * Get the floor data from the database reference from the floor ID
     * @param flrId floor ID
     * @param con
     * @return BuildingFloor object of a certain floor in a building
     */
    public BuildingFloor getFloor(int flrId, Connection con) {
        BuildingFloor bf = null;
        String sqlString = "SELECT * FROM building_floor where floor_id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, flrId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                bf = new BuildingFloor(
                        rs.getInt("floor_id"),
                        rs.getInt("floor_number"),
                        rs.getDouble("floor_size"),
                        rs.getInt("total_rooms"),
                        rs.getInt("idbuilding"));
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception in BUILDINGMAPPER/get floor: " + ex);
            return null;
        }
        return bf;
    }
    
    /**
     * Update the floor's total number of rooms in the building_floor table based it's floor ID
     * @param flrId floor ID
     * @param con
     * @param newTotalRooms new number of Rooms to be changed in the database
     */
    public void updateFloor(int flrId, Connection con, int newTotalRooms) {
       
        String SQLString
                = "update building_floor set total_rooms = ? where floor_id = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, newTotalRooms);
            statement.setInt(2, flrId);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in updateFloor");
            System.out.println(e.getMessage());
        } 
    }
    
    /**
     * This method will update data of a building in the database based on the building ID
     * @param b new Building object that holds the changes
     * @param con
     */
    public void updateBuilding(Building b, Connection con){
        String SQLString
                = "update building "
                + "set building_name = ?,"
                + "set building_m2 = ?,"
                + "set building_adress = ?,"
                + "set building_housenumber = ?,"
                + "set building_buildyear = ?,"
                + "set building_zip = ?,"
//                + "set building_pic = ?,"
                + "set building_use = ?,"
                + " where idbuilding = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setString(1, b.getBuildingName());
            statement.setDouble(2, b.getBuildingSize());
            statement.setString(3, b.getStreetAddress());
            statement.setString(4, b.getStreetNumber());
            statement.setInt(5, b.getBuildingYear());
            statement.setInt(6, b.getZipCode());
//            statement.setString(7, b.getBuildingPic()); //has to be changed in the database
            statement.setString(8, b.getUseOfBuilding());
            statement.setInt(10, b.getBdgId());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in updateBuilding");
            System.out.println(e.getMessage());
        } 
    }
    
    /**
     * This method will update the building_floor in the database
     * @param bf new BuildingFloor that holds the changes
     * @param con
     */
    public void updateFloor(BuildingFloor bf, Connection con){
        String SQLString
                = "update building_floor "
                + "set floor_size = ? "
                + "set total_rooms = ? "
                + "where floor_id = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setDouble(1, bf.getFloorSize());
            statement.setInt(2, bf.getTotalRooms());
            statement.setInt(3, bf.getFloorId());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in updateFloor");
            System.out.println(e.getMessage());
        } 
    }
    
    
    /**
     * This method will update the building_room in the database
     * @param br new BuildingRoom that holds the changes
     * @param con
     */
    public void updateRoom(BuildingRoom br, Connection con){
        String SQLString
                = "update building_room "
                + "set room_name = ? "
                + "where room_id = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setString(1, br.getRoomName());
            statement.setInt(2, br.getRoomId());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in updateRoom");
            System.out.println(e.getMessage());
        } 
    }
    
    /**
     *This method will delete tuples in the building_rooms table based on the floor ID
     * @param floorId floor ID
     * @param con
     * REMEMBER: BuildingFloor and Building rooms should be deleted first before deleting the Building 
     */
    public void deleteAllRooms(int floorId, Connection con){
        String SQLString
                = "delete from building_room where floor_id = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, floorId);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in deleteBuildingRooms ");
            System.out.println(e.getMessage());
        } 
    }
    /**
     *This method will delete tuples in the building_floors table based on the building ID
     * @param bdgId building ID
     * @param con
     * REMEMBER: BuildingFloor and Building rooms should be deleted first before deleting the Building 
     */
    public void deleteAllFloors(int bdgId, Connection con){
        String SQLString
                = "delete from building_floor where idbuilding = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, bdgId);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in deleteBuildingFloors ");
            System.out.println(e.getMessage());
        } 
    }
    
    /**
     *This method will delete a tuple in the building table based on the building ID
     * @param bdgId building ID
     * @param con
     * REMEMBER: BuildingFloor and Building rooms should be deleted first before deleting the Building 
     */
    public void deleteBuilding(int bdgId, Connection con){
        ArrayList<BuildingFloor> floorsInTheBuilding = getFloorsList(bdgId, con);
        for (BuildingFloor buildingFloor : floorsInTheBuilding) {
           deleteAllRooms(buildingFloor.getFloorId(), con); //it has to delete all the rooms on each floor
        }
        deleteAllFloors(bdgId, con);
        
        String SQLString
                = "delete from building where idbuilding = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, bdgId);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in deleteBuilding ");
            System.out.println(e.getMessage());
        } 
    }
    
    //Does not deal with the fact that docs may be there already
    public void saveBuildingDocs(Building b, Connection con) {
        int buildingId = b.getBdgId();
        ArrayList<BuildingFiles> files=b.getListOfFiles();
        for (BuildingFiles file : files) {
            String description = file.getDescription();
            ArrayList<BuildingFile> individualFiles=file.getListOfFileInfo();
            for (BuildingFile individualFile : individualFiles) {
                
                System.out.println("Trying to save buildingfile:");
                System.out.println(individualFile.getDocumentname());
                saveBuildingDoc(buildingId, description,individualFile,con);
                
            }
        }
    }
        public void saveBuildingDoc(int buildId,String description,BuildingFile bf, Connection con) {
            String filename = bf.getFilename();
            String documentname= bf.getDocumentname();
            int size = bf.getSize();
            //Size of file 0 for now
            
            
        String sql = "insert into building_documents (filename,documentname,building_id,document_size) values  (?,?,?,?) ";
        try {
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, filename);
            statement.setString(2, documentname);
            statement.setInt(3, buildId);
            statement.setInt(4, size);
            statement.executeUpdate();
            
           
        } catch (SQLException ex) {
            System.out.println("Error in SQL SavebuildingDoc " + ex );
        }
        
    }
}


