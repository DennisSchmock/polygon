/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Building;
import Domain.BuildingFloor;
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
 * @author danie
 */
public class BuildingMapper {

    /**
     * saves the building object in the database
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
            System.out.println("SQL ERROR IN SaveNewBuildingDB " +ex.getMessage());
        }
        return b;
    }
    
    public String saveBuildingPic(int buildId, String ext, Connection con){
        int imgId=0;
        System.out.println("build id");
        System.out.println(buildId);
        
        try {
            String sqlString = "insert into building_pic(building_pic_extension,building_id) values(?,?)";
            PreparedStatement statement = con.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, ext);
            statement.setInt(2, buildId);
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {   // Changed from !rs.next() as this didn't return the key
                imgId=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BuildingMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        String filePath = imgId+"."+ext;
        System.out.println(filePath);
        return filePath;
    }

    /**
     * Loading the list of buildings for an specific customer in the database
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
            
            while(rs.next()){
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
     * @param updatedBuildObj Holds all the information that is needed to 
     * update the building tuble in the database
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
            //int pic = rs.getInt("building_pic");
            String use = rs.getString("building_use");
            int cusId = rs.getInt("customer_id");
            
            b = new Building(buildingId,name,size,address,houseNumber,yr,zip,use,cusId);
//            b.setListOfRooms(getRoomsList(buildingId, con));

            return b;
        } catch (Exception e) {
            System.out.println("Fail in NewBuildingMapper-getBuilding");
            System.out.println(e.getMessage());
            return null;
        }
    }

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
        }}
    
}
