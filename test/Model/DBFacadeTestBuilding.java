/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Building;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dennisschmock
 */
public class DBFacadeTestBuilding {
    DBFacade dbf;
    public DBFacadeTestBuilding() {
        
    }
    
    @Before
    public void setUp() throws Exception {
        DBFixture fixture = new DBFixture();
        fixture.setUp();
        dbf = DBFacade.getInstance();
        dbf.setCon(fixture.getConnection());
    }

    @Test
    public void testMain() {
    }


//
//    @Test
//    public void testGetListOfContacts() {
//    }
//
    @Test
    public void testSaveNewBuilding() {
        Building b = new Building("vor Frelser Kirke", "Christianshavn", "12A", 2300, 1734, 237.9, "Praiseing the Lord");
        b.setCustId(1);
        dbf.saveNewBuilding(new Building("vor Frelser Kirke", "Christianshavn", "12A", 2300, 1734, 237.9, "Praiseing the Lord"));
        Building b2;
        List<Building> builds=dbf.getListOfbuildingsDB(1);
        b2=builds.get(0);
        assertTrue(dbf.getListOfbuildingsDB(1).size()==1);
        assertTrue("Getting building out failed", b2 != null);
        assertTrue("Getting same building back failed", b2.equals(b));
    }
//
//    @Test
//    public void testGetListOfbuildingsDB() {
//    }
//
//    @Test
//    public void testUpdateBuildingDBFacade() {
//    }
//
//    @Test
//    public void testLoadUser() {
//    }
    
}
