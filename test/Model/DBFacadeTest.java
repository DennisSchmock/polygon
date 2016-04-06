/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Domain.*;

/**
 *
 * @author dennisschmock
 */
public class DBFacadeTest {
    
        DBFixture fixture;
        DBFacade dbf;

    
    public DBFacadeTest() {
        
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

//    @Test
//    public void testGetInstance() {
//    }
//
    @Test
    public void testValidateUser() {
        dbf.createUserDBFacade(null);
    }
    
    @Test
    public void createUserDBFacade() {
        
    }
//
//    @Test
//    public void testSaveNewReport() {
//    }
//
//    @Test
//    public void testSaveReportRoom() {
//    }
//
//    @Test
//    public void testSaveReportExt() {
//    }
//
//    @Test
//    public void testSaveReportRoomDamage() {
//    }
//
//    @Test
//    public void testSaveReportInterior() {
//    }
//
//    @Test
//    public void testSaveReportRoomRec() {
//    }
//
//    @Test
//    public void testGetReport() {
//    }
//
//    @Test
//    public void testGetReportExt() {
//    }
//
//    @Test
//    public void testGetReportRoom() {
//    }
//
//    @Test
//    public void testGetReportDamage() {
//    }
//
//    @Test
//    public void testGetReportInt() {
//    }
//
//    @Test
//    public void testGetReportRec() {
//    }
//
//    @Test
//    public void testGetListOfExt() {
//    }
//
//    @Test
//    public void testGetListOfReportRoom() {
//    }
//
//    @Test
//    public void testGetListOfDamages() {
//    }
//
//    @Test
//    public void testGetListOfInt() {
//    }
//
//    @Test
//    public void testGetListOfRec() {
//    }
//
//    @Test
//    public void testAddCustomer() {
//    }
//
//    @Test
//    public void testSaveContact() {
//    }
//
//    @Test
//    public void testSaveReportMoist() {
//    }
//
//    @Test
//    public void testGetListOfContacts() {
//    }
//
//    @Test
//    public void testSaveNewBuilding() {
//    }
//
//    @Test
//    public void testGetListOfbuildingsDB() {
//    }
//
//    @Test
//    public void testUpdateBuildingDBFacade() {
//    }
//
    @Test
    public void testLoadUser() {
    }
    
}
