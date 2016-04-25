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
import Domain.Exceptions.PolygonException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class ReportTester {

    DBFixture fixture;
    Report r;
    Connection con;
    ReportMapper rm;

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
        con = fixture.getConnection();
        con.setAutoCommit(true);
        reportSetup();
        rm = new ReportMapper();
    }

    @After
    public void tearDown() {
        fixture.closeConnection();
    }

    /**
     * Method for Setting up an Report object, that can be altered and used for
     * testing purpurse in other testmethods There is not need for creating id's
     * for the different report elements because the database should take care
     * of that
     *
     */
    public void reportSetup() {
        Report report = new Report(1, "emp1");

        // Exterior
        ArrayList<ReportExterior> listReportEx = new ArrayList<>();
        ReportExterior reportExteior1 = new ReportExterior("Testing area", "Testing Decription");
        listReportEx.add(reportExteior1);
        report.setListOfRepExt(listReportEx);

        // Room1
        ReportRoom room1 = new ReportRoom("Testing Room Name 1", 1); // 1 is the foreign key to the test building Room already in the database

        //Interior
        ArrayList<ReportRoomInterior> listReportint = new ArrayList<>();
        ReportRoomInterior roomInterior1 = new ReportRoomInterior("Test Report Romm Area", "Test Remark");
        ReportRoomInterior roomInterior2 = new ReportRoomInterior("Test Report Room 2", "Test Remark 2");
        listReportint.add(roomInterior1);
        listReportint.add(roomInterior2);
        room1.setListOfInt(listReportint);

        //Room Damage:
        ArrayList<ReportRoomDamage> listofRoomDmg = new ArrayList<>();
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

        r = report;
    }

    /**
     * Test method that makes sure that the hard coded object does not have
     * errors, so that we can validate our other tests
     */
    @Test
    public void TestWholeObject() {
        try {
            int reportID = rm.reportToDataBase(r, con);
            Report reportfromDatabase = rm.getSingleReport(reportID, con);

            int buildingIDfromdatabase = reportfromDatabase.getBuildingId();
            int buildingIDfromR = r.getBuildingId();

            ReportRoom roomfromDB = reportfromDatabase.getListOfRepRoom().get(0);
            ReportRoom roomfromR = r.getListOfRepRoom().get(0);

            ReportRoomMoist moistfromDB = roomfromDB.getMoist();
            ReportRoomMoist moistfromR = roomfromR.getMoist();

            ReportRoomDamage damagefromDB = roomfromDB.getListOfDamages().get(0);
            ReportRoomDamage damagefromR = roomfromR.getListOfDamages().get(0);

            ReportRoomInterior interiorfromDB = roomfromDB.getListOfInt().get(0);
            ReportRoomInterior interiorfromR = roomfromR.getListOfInt().get(0);

            ReportRoomRecommendation recomendationfromDatabase = reportfromDatabase.getListOfRepRoom().get(0).getListOfRec().get(0);
            ReportRoomRecommendation recomendationfromR = r.getListOfRepRoom().get(0).getListOfRec().get(0);
            
            assertTrue("Building ID expeted: " + buildingIDfromR + " Found: " + buildingIDfromdatabase,
                    buildingIDfromR == buildingIDfromdatabase );
            assertTrue("Room Name expeted: " + roomfromR.getRoomName() + " Found: " + roomfromDB.getRoomName(),
                    roomfromR.getRoomName().equals(roomfromDB.getRoomName()));
            assertTrue("MoistMesaured expeted: " + moistfromR.getMeasurePoint() + " Found:  " + moistfromDB.getMeasurePoint(),
                    moistfromR.getMeasurePoint().equals(moistfromDB.getMeasurePoint()));
            assertTrue("Damage expeted: " + damagefromR.getWhatHappened() + " Found: " + damagefromDB.getWhatHappened(),
                    damagefromR.getWhatHappened().equals(damagefromDB.getWhatHappened()));
            assertTrue("Interior Name expeted: " + interiorfromR.getRepRoomIntName() + " Found: " + interiorfromDB.getRepRoomIntName()
                    ,interiorfromR.getRepRoomIntName().equals(interiorfromDB.getRepRoomIntName()));
            assertTrue("Interior Remark expeted: " + interiorfromR.getRemark() + " Found: " + interiorfromDB.getRemark(),
                    interiorfromR.getRemark().equals(interiorfromDB.getRemark()));
            assertTrue("Recomendation Remark expeted: " + recomendationfromR.getRecommendation() + " Found: " + recomendationfromDatabase.getRecommendation(),
                    recomendationfromR.getRecommendation().equals(recomendationfromDatabase.getRecommendation()));
            

        } catch (PolygonException ex) {
            System.out.println("Error Polygon Exception " + ex);
            ex.printStackTrace();
        }

    }

    /**
     *This method tests the method getmoist and setmoist in the reportmapper, 
     * to make sure that the messure point is saved and loaded in the correct place.
     */
    @Test
    public void TestMoistPoint() {
        
        try {
            String expected = "Upper left Corner";
            
            ReportRoomMoist tempMoist = r.getListOfRepRoom().get(0).getMoist();
            tempMoist.setMeasurePoint(expected);
            r.getListOfRepRoom().get(0).setMoist(tempMoist);
            int reportID = rm.reportToDataBase(r, con);
            Report reportfromDatabase = rm.getSingleReport(reportID, con);
            
            String actual = reportfromDatabase.getListOfRepRoom().get(0).getMoist().getMeasurePoint();

            assertTrue("Expected: " + expected + " Found: " + actual, expected.equals(actual));
        } catch (PolygonException ex) {
             System.out.println("Error Polygon Exception " + ex);
            ex.printStackTrace();
        }
        
    }
    
    /**
     * Method that test if an exception is thrown if an String that is too long is inserted
     * @throws PolygonException
     */
    @Test (expected = PolygonException.class)  
    public void TestMoistPointTooLong() throws PolygonException {
        String longString ="";
        
        for (int i = 0; i < 1000; i++) {
            longString +="e";
        }
        ReportRoomMoist tempMoist = r.getListOfRepRoom().get(0).getMoist();
            tempMoist.setMeasurePoint(longString);
            r.getListOfRepRoom().get(0).setMoist(tempMoist);
            int reportID = rm.reportToDataBase(r, con);
        
            //This should throw an exception before getting here.
            assertTrue(false);
    }
    
    /**
     * 
     * Method for test what happens when the point string is null
     */
    @Test 
    public void TestMoistPointNull() {
        try{
        String expected = null;
        
         ReportRoomMoist tempMoist = r.getListOfRepRoom().get(0).getMoist();
            tempMoist.setMeasurePoint(expected);
            r.getListOfRepRoom().get(0).setMoist(tempMoist);
            int reportID = rm.reportToDataBase(r, con);
            Report reportfromDatabase = rm.getSingleReport(reportID, con);
            
            String actual = reportfromDatabase.getListOfRepRoom().get(0).getMoist().getMeasurePoint();

            assertTrue("Expected: " + expected + " Found: " + actual, actual == null);
        } catch (PolygonException ex) {
             System.out.println("Error Polygon Exception " + ex);
            ex.printStackTrace();
        }
    }
    
    /**
     * Method for test what happens when the point string is empty
     */
    @Test 
    public void TestMoistPointEmpty()   {
        
         try{
        String expected = "";
        
         ReportRoomMoist tempMoist = r.getListOfRepRoom().get(0).getMoist();
            tempMoist.setMeasurePoint(expected);
            r.getListOfRepRoom().get(0).setMoist(tempMoist);
            int reportID = rm.reportToDataBase(r, con);
            Report reportfromDatabase = rm.getSingleReport(reportID, con);
            
            String actual = reportfromDatabase.getListOfRepRoom().get(0).getMoist().getMeasurePoint();

            assertTrue("Expected: " + expected + " Found: " + actual, expected.equals(actual));
        } catch (PolygonException ex) {
             System.out.println("Error Polygon Exception " + ex);
            ex.printStackTrace();
        }
        
       
    }
    
    

}
