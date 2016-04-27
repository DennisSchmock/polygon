/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Data.DBFacade;
import Domain.Building;
import Domain.BuildingRoom;
import Domain.Exceptions.PolygonException;
import Domain.Report;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dennisschmock
 */
public class DBFacadeTestBuilding {
    DBFixture fixture;
    DBFacade dbf;
        
        
    public DBFacadeTestBuilding() {

    }

    @Before
    public void setUp() throws Exception {
        fixture = new DBFixture();
        fixture.setUp();
        dbf = DBFacade.getInstance();
        Connection con =fixture.getConnection();
        con.setAutoCommit(true);
        dbf.setTestConnection(con);
    }

//
//    @Test
//    public void testGetListOfContacts() {
//    }
//
    @Test
    public void testSaveNewBuilding() throws PolygonException {
        
        Building b = new Building("vor Fredfdasflser Kirke", "Christianshavn", "12A", 2300, 1734, 237.9, "Praiseing the Lord");
        b.setCustId(1);
        BuildingRoom br1 = new BuildingRoom(1,"Et");
        BuildingRoom br2 = new BuildingRoom(1,"Et");
        BuildingRoom br3 = new BuildingRoom(1,"Et");
        BuildingRoom br4 = new BuildingRoom(1,"Et");
        ArrayList<BuildingRoom> listOfRooms = new ArrayList();
        listOfRooms.add(br1);
        listOfRooms.add(br2);
        listOfRooms.add(br3);
        listOfRooms.add(br4);
//        b.setListOfRooms(listOfRooms);
        dbf.saveNewBuilding(b);
        Building b2=null;
        List<Building> builds=dbf.getListOfbuildingsDB(1);
        if (!builds.isEmpty())b2=builds.get(0);
        assertTrue("Building size wrong",builds.size()==1);
        assertTrue("Getting building out failed", b2 != null);
        assertTrue("Getting same building back failed", compareBuildings(b,b2));
    }
    
    public boolean compareBuildings(Building b1, Building b2){
        boolean equals;
        equals = b1.getBuildingName().equals(b2.getBuildingName());
        if (    b1.getBuildingSize()!=b2.getBuildingSize() || 
                b1.getBuildingYear()!= b2.getBuildingYear()//||
                //b1.getListOfRooms()!=b2.getListOfRooms()
                ){
            equals=false;
        }
        return equals;
    }

    @Test
    public void testGetListOfbuildingsDB() throws PolygonException {
       Building b = new Building("Vor Frelser Kirke", "Christianshavn", "12A", 2300, 1734, 237.9, "Praiseing the Lord");
        b.setCustId(1);
        dbf.saveNewBuilding(b); 
        dbf.saveNewBuilding(b);
        dbf.saveNewBuilding(b); 
        List<Building> builds=dbf.getListOfbuildingsDB(1);
        assertTrue("Building size wrong",builds.size()==3);
    }

    @Test
    public void testUpdateBuildingDBFacade() throws PolygonException {
         Building b = new Building("vor Frelser Kirke", "Christianshavn", "12A", 2300, 1734, 237.9, "Praiseing the Lord");
        b.setCustId(1);
        dbf.saveNewBuilding(b);
        b.setBdgId(1);
        b.setBuildingName("New Name");
        b.setBuildingSize(100);
        dbf.updateBuildingDBFacade(b);
        Building b2 = dbf.getListOfbuildingsDB(1).get(0);
        assertTrue("Building name wrong",b2.getBuildingName().equals("New Name"));
        assertTrue("Building size wrong",b2.getBuildingSize()==100);
    }

//    @Test
//    public void testLoadUser() {
//    }
    public void tearDown() throws SQLException {
        fixture.closeConnection();
    }
}
