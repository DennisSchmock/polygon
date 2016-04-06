/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Building;
import java.sql.Connection;
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
    DBFixture fixture;
    DBFacade dbf;
        
        
    public DBFacadeTestBuilding() {

    }

    @Before
    public void setUp() throws Exception {
        fixture = new DBFixture();
        fixture.setUp();
        dbf = DBFacade.getInstance();
        System.out.println(fixture.getConnection()!=null);
        System.out.println(fixture.getConnection().isClosed());
        Connection con =fixture.getConnection();
        con.setAutoCommit(true);
        dbf.setCon(con);
    }

//
//    @Test
//    public void testGetListOfContacts() {
//    }
//
    @Test
    public void testSaveNewBuilding() {
        
        Building b = new Building("vor Fredfdasflser Kirke", "Christianshavn", "12A", 2300, 1734, 237.9, "Praiseing the Lord");
        b.setCustId(1);
        
        dbf.saveNewBuilding(b);
        Building b2=null;
        List<Building> builds=dbf.getListOfbuildingsDB(1);
        if (!builds.isEmpty())b2=builds.get(0);
        assertTrue("Building size wrong",builds.size()==1);
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
    public void tearDown() throws SQLException {
        fixture.closeConnection();
    }
}
