/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Building;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void saveNewBuildingDB(Building b, Connection con) {
            String sqlString = "insert into building(building_name, building_m2, "
                    + "building_adress, building_housenumber, building_zip, "
                    + "building_pic, building_use, building_buildyear, customer_id ) values(?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setString(1, b.getBuildingName());
            statement.setDouble(2, b.getBuildingSize());
            statement.setString(3, b.getStreetAddress());
            statement.setString(4, b.getStreetNumber());
            statement.setInt(5, b.getZipCode());
            statement.setInt(6, b.getBuilding_pic());
            statement.setString(7, b.getUseOfBuilding());
            statement.setInt(8, b.getBuildingYear());
            statement.setInt(9, b.getCustId());
            statement.execute();
        } catch (SQLException ex) {
            System.out.println("SQL ERROR IN SaveNewBuildingDB " +ex.getMessage());
        }
        
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
                + " building_pic=?, "
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
            statement.setInt(7, updatedBuildObj.getBuilding_pic());
            statement.setString(8, updatedBuildObj.getUseOfBuilding());
            statement.setInt(9, updatedBuildObj.getCustId());
            statement.setInt(10, updatedBuildObj.getBdgId());
            System.out.println(statement.toString());
            statement.execute();
        } catch (SQLException ex) {
            System.out.println("SQL ERROR IN UPDATEBUILDINGBM " + ex);
        }
    }
    
}
