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
import Domain.Floorplan;
import Domain.Exceptions.PolygonException;
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
 * @author Dennis Schmock and Daniel Grønberg
 */
public class BuildingMapper{

    /**
     * saves the building object in the database
     *
     * @param b building to be added to database
     * @param con connection to database
     */
    public Building saveNewBuildingDB(Building b, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error");
        }
        return b;
    }

    /**
     * Saves information of a picture associated with a building
     *
     * @param buildId building to associate picture with
     * @param filename full filename ie xxx.jpg
     * @param con
     * @return the same filename that was put in. for some odd reason
     */
    public String saveBuildingPic(int buildId, String filename, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error");
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
    public List<Building> getListOfBuildingsBM(int customerID, Connection con) throws PolygonException {
        List<Building> buildingList = new ArrayList<>();
        String sqlString = "SELECT * FROM building where customer_id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, customerID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int bdgId = rs.getInt("idbuilding");
                String n= rs.getString("building_name");
                String a= rs.getString("building_adress");
                String hn = rs.getString("building_housenumber");
                int z = rs.getInt("building_zip");
                int y =rs.getInt("building_buildyear");
                double s = rs.getDouble("building_m2");
                String u = rs.getString("building_use");
                int cId = rs.getInt("customer_id");
                int state = getBuildingState(bdgId, con);
                Building temp = new Building(bdgId,n,a,hn,z,y,s,u,cId,state);
                buildingList.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception in BUILDINGMAPPER: " + ex);
            throw new PolygonException("Database error");
        }
        return buildingList;
    }
    
    /**
     * This will get the building state corresponding to the building ID
     * @param buildingID building ID
     * @param con connection
     * @return integer of category conclusion or building state
     */
    public int getBuildingState(int buildingID, Connection con) {
        int state=0;
        String sqlString = "SELECT category_conclusion FROM report where building_id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, buildingID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                state = rs.getInt("category_conclusion");
            }
        } catch (SQLException ex) {
            System.out.println("SQL ERROR IN get state " + ex);
        }
        return state;
    }

    /**
     * Updates the right building in the database
     *
     * @param updatedBuildObj Holds all the information that is needed to update
     * the building tuble in the database
     * @param con Connection to the database
     */
    public void updateBuildingBm(Building updatedBuildObj, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error");
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
    public Building getBuildingBM(int buildingID, Connection con) throws PolygonException {

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
            throw new PolygonException("Database error");
        }
        return temp;
    }

    /**
     * The purpose of this method is to get a building from a specific building id
     * @param bdgId
     * @param con
     * @return
     */
    public Building getBuilding(int bdgId, Connection con) throws PolygonException {
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
        } catch (SQLException ex) {
            System.out.println("Fail in NewBuildingMapper-getBuilding");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error");
        }
    }

    /**
     * The purpofse of this method is to add a floor object to the database
     * @param bf floor object
     * @param con
     */
    public void addFloor(BuildingFloor bf, Connection con) throws PolygonException {
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
        } catch (SQLException ex) {
            System.out.println("Fail in saving new floor - addFloor");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error");
        }
    }

    /**
     * The purpose of this method is to get a list of Floors connect to a certain building
     * @param buildingId the id of the building
     * @param con 
     * @return a list of floors
     */
    public ArrayList<BuildingFloor> getFloorsList(int buildingId, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error");
        }
        return floorsList;
    }

    /**
     *The purpose of this method is to map a list of Rooms for a certain floor
     * @param floorId the id of the floor we need
     * @param con
     * @return a list of rooms
     */
    public ArrayList<BuildingRoom> getRoomList(int floorId, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error - Couldn't get rooms");
        }
        return roomList;
    }
    
    /**
     * Method for getting all floorplans associated with a floor
     *
     * @param floorId id to choose a specific floor in db
     * @param con
     * @return a list of Floorplan objects
     */
    public ArrayList<Floorplan> getFloorplans(int floorId, Connection con) throws PolygonException {
        ArrayList<Floorplan> floorplans = new ArrayList();
        String sqlString = "SELECT * FROM floorplan where floor_id=?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, floorId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Floorplan temp = new Floorplan(
                        rs.getInt("documentsize"),
                        rs.getString("floorplanpath"),
                        rs.getString("documentname")
                );
                temp.setFileID(rs.getInt("floorplan_id"));
                floorplans.add(temp);
                System.out.println(temp.getFilename());
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception in BUILDINGMAPPER/getfloorplan list: " + ex);
            throw new PolygonException("Database error - Couldn't get floorplans");
        }
        return floorplans;
    }
        
    /**
     * Method for getting one and just one image associated with a building
     *
     * @param buildingId id of the building to find an image for
     * @param con
     * @return the last image to come out of the db with the buildingId
     */
    public String getLatestBuildingImage(int buildingId, Connection con) throws PolygonException {
        String filename = null;
        System.out.println("getLatestBuildingImage");
        String SQLString = "select * from building_pic where building_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            System.out.println("statement prepared");
            statement.setInt(1, buildingId);
            ResultSet rs = statement.executeQuery();
            System.out.println("query executed");

            while (rs.next()){
            filename = rs.getString("filename");
            }
            
        } catch (SQLException ex) {
            System.out.println("Fail in NewBuildingMapper-getLatestBuildingPic");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error - Couldn't get Image");
        }
        
        
        return filename;
    }

    /**
     * Saves the buildingRoom object in the database and returns it
     * with an Unique ID.
     * @param newRoom The building object to be inserted.
     * @param con Connection to the database
     * @return The newly inserted building object
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public BuildingRoom saveBuildingRoom(BuildingRoom newRoom, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error - Couldn't save room");
        }
        
        return newRoom;
    }

    /**
     * Loads an BuildingFloor based on the ID in the parameter.
     * Creates an object of BuildingFloor and returns it.
     * @param floorid For the building to be loaded
     * @param con Connection to Database
     * @return  Loaded Buildingfloor object
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public BuildingFloor getBuildingFloorBM(int floorid, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error - Couldn't get floor");
        }
        return bf;
    }
    
    /**
     * Get the floor data from the database reference from the floor ID
     * @param flrId floor ID
     * @param con
     * @return BuildingFloor object of a certain floor in a building
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public BuildingFloor getFloor(int flrId, Connection con) throws PolygonException {
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
            throw new PolygonException("Database error- Couldn't get floor");
        }
        return bf;
    }
    
    /**
     * Update the floor's total number of rooms in the building_floor table based it's floor ID
     * @param flrId floor ID
     * @param con
     * @param newTotalRooms new number of Rooms to be changed in the database
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public void updateFloor(int flrId, Connection con, int newTotalRooms) throws PolygonException {
       
        String SQLString
                = "update building_floor set total_rooms = ? where floor_id = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, newTotalRooms);
            statement.setInt(2, flrId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fail in updateFloor");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error - Couldn't update floor");
        } 
    }
    
    /**
     * This method will update data of a building in the database based on the building ID
     * @param b new Building object that holds the changes
     * @param con
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public void updateBuilding(Building b, Connection con) throws PolygonException{
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
        } catch (SQLException ex) {
            System.out.println("Fail in updateBuilding");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error - Couldn't update building");
        } 
    }
    
    /**
     * This method will update the building_floor in the database
     * @param bf new BuildingFloor that holds the changes
     * @param con
     */
    public void updateFloor(BuildingFloor bf, Connection con) throws PolygonException{
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
        } catch (SQLException ex) {
            System.out.println("Fail in updateFloor");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error - Couldn't update floor");
        } 
    }
    
    
    /**
     * This method will update the building_room in the database
     * @param br new BuildingRoom that holds the changes
     * @param con
     */
    public void updateRoom(BuildingRoom br, Connection con) throws PolygonException{
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
        } catch (SQLException ex) {
            System.out.println("Fail in updateRoom");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error - Couldn't update room");
        } 
    }
    
    /**
     *This method will delete tuples in the building_rooms table based on the floor ID
     * @param floorId floor ID
     * @param con
     * REMEMBER: BuildingFloor and Building rooms should be deleted first before deleting the Building 
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public void deleteAllRooms(int floorId, Connection con) throws PolygonException{
        String SQLString
                = "delete from building_room where floor_id = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, floorId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fail in deleteBuildingRooms ");
            System.out.println(ex.getMessage());
            throw new PolygonException("Database error");
        } 
    }
    /**
     *This method will delete tuples in the building_floors table based on the building ID
     * @param bdgId building ID
     * @param con
     * REMEMBER: BuildingFloor and Building rooms should be deleted first before deleting the Building 
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public void deleteAllFloors(int bdgId, Connection con) throws PolygonException{
        String SQLString
                = "delete from building_floor where idbuilding = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, bdgId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Fail in deleteBuildingFloors ");
            
            System.out.println(ex.getMessage());
            throw new PolygonException("Error deleting building floors from database - "
                    + "The file was NOT uploaded");
        } 
    }
    
    /**
     *This method will delete a tuple in the building table based on the building ID
     * @param bdgId building ID
     * @param con
     * REMEMBER: BuildingFloor and Building rooms should be deleted first before deleting the Building 
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public void deleteBuilding(int bdgId, Connection con) throws PolygonException{
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
        } catch (SQLException ex) {
            System.out.println("Fail in deleteBuilding ");
            System.out.println(ex.getMessage());
            throw new PolygonException("There was an error deleting from database");
        } 
    }
    
    //Does not deal with the fact that docs may be there already

    /**
     * Method takes a cluster of documents and iterates over all of the
     * individual files sotring their info in the db
     * @param b the building that has docs to store
     * @param con
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public void saveBuildingDocs(Building b, Connection con) throws PolygonException {
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

    /**
     * method for putting information of single files in db
     *
     * @param buildId Id of the building fileinfo belongs to
     * @param description description of the document
     * @param bf information on the specific document
     * @param con
     * @throws Domain.Exceptions.PolygonException Thrown in case of sql-exception
     */
    public void saveBuildingDoc(int buildId,String description,BuildingFile bf, Connection con) throws PolygonException {
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
            throw new PolygonException("Error saving building documents to database - "
                    + "NO files were uploaded");
        }
        
    }

    /**
     * Method puts information of a Floorplan in the db (not the file itself)
     *
     * @param floorId the foreign key of the table
     * @param f the floorplan to be stored
     * @param con
     * @throws Domain.Exceptions.PolygonException if saving the  Floorplan fails due to sql error
     */
    public void saveFloorplan(int floorId, Floorplan f, Connection con) throws PolygonException {
        String filename = f.getFilename();
        String documentname= f.getDocumentname();
        int size = f.getSize();
        
            
            
        String sql = "insert into floorplan (floorplanpath,documentname,floor_id,documentsize) values  (?,?,?,?) ";
        try {
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, filename);
            statement.setString(2, documentname);
            statement.setInt(3, floorId);
            statement.setInt(4, size);
            statement.executeUpdate();
            
           
        } catch (SQLException ex) {
            System.out.println("Error in SQL SaveFloorplan " + ex );
            throw new PolygonException("Error saving floorplan to database - "
                    + "The file was NOT uploaded");
        }
    }
}


