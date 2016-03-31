/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Building;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                    + "building_pic, building_use, building_buildyear) values(?,?,?,?,?,?,?,?) ";
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
            statement.execute();
        } catch (SQLException ex) {
            System.out.println("SQL ERROR IN SaveNewBuildingDB " +ex.getMessage());
        }
        
    }
    
}
