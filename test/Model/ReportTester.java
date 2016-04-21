/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Domain.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class ReportTester {
    
        DBFixture fixture;
        DBFacade dbf;
        Report r;
    
    public ReportTester() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() throws Exception {
        fixture = new DBFixture();
        fixture.setUp();
        dbf = DBFacade.getInstance();
        Connection con =fixture.getConnection();
        con.setAutoCommit(true);
        dbf.setCon(con);
        reportSetup();
    }
    
    @After
    public void tearDown() {
         fixture.closeConnection();
    }
    
    /** Method for Setting up an Report object, that can be altered and used
     * for testing purpurse in other testmethods
     * There is not need for creating id's for the different report elements
     * because the database should take care of that
     *
     */
    public void reportSetup(){
        Report report = new Report(1, "emp1");
        
        // Exterior
        ArrayList<ReportExterior> listReportEx = new  ArrayList<>();
        ReportExterior reportExteior1 = new ReportExterior("Testing area","Testing Decription");
        listReportEx.add(reportExteior1);
        report.setListOfRepExt(listReportEx);
        
        // Room1
        ReportRoom room1 = new ReportRoom("Testing Room Name 1", 1); // 1 is the foreign key to the test building Room already in the database
        
        
        //Interior
        ArrayList<ReportRoomInterior> listReportint = new  ArrayList<>();
        ReportRoomInterior roomInterior1 = new ReportRoomInterior("Test Report Romm Area", "Test Remark");
        ReportRoomInterior roomInterior2 = new ReportRoomInterior("Test Report Room 2", "Test Remark 2");
        listReportint.add(roomInterior1);
        listReportint.add(roomInterior2);
        room1.setListOfInt(listReportint);
        
        
        //Room Damage:
        ArrayList<ReportRoomDamage> listofRoomDmg = new  ArrayList<>();
        ReportRoomDamage roomdmg1 = new ReportRoomDamage("Test Time", "Test Place", "Test what happend", "Test Type", "Test Repaired");
        listofRoomDmg.add(roomdmg1);
        room1.setListOfDamages(listofRoomDmg);
        
        //Moist
        ReportRoomMoist moist1 = new ReportRoomMoist("Test measured", "Test Area");
        room1.setMoist(moist1);
        
        // Recomendations
        ArrayList<ReportRoomRecommendation> listofRecomendations = new ArrayList<>();
        ReportRoomRecommendation rec1 = new ReportRoomRecommendation("Test Recomendation");
        listofRecomendations.add(rec1);
        room1.setListOfRec(listofRecomendations);
        
        ArrayList<ReportRoom> roomlist = new ArrayList<>();
        roomlist.add(room1);
        roomlist.add(room1); // Places to identically rooms to the object
        report.setListOfRepRoom(roomlist);
        System.out.println(report.toString());
        
    }
    
    @Test
    public void testReport(){
        
    }
    
}
