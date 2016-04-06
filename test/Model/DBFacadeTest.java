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
import org.junit.After;
import org.junit.BeforeClass;

/**
 *
 * @author dennisschmock
 */
public class DBFacadeTest {
    
        DBFixture fixture;
        DBFacade dbf;

    
    public DBFacadeTest() {
        
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
        fixture = new DBFixture();
        fixture.setUp();
        dbf = DBFacade.getInstance();
        dbf.setCon(fixture.getConnection());
    }

    @After
    public void tearDown() throws Exception {
        fixture.tearDown();
    }

//    @Test
//    public void testMain() {
//    }

//    @Test
//    public void testGetInstance() {
//    }
//

    /**
     *Validation of a user.
     * First creates an user, and saves it to the database.
     * Then calls the validate method to check the password
     */
        @Test
    public void testValidateUser1() {
        String username ="HejmedDig";
        String password = "HELLo";
        
        User user = new User(username, password, 1, null, null, null, null, null);
        dbf.createUserDBFacade(user);
        
        
        boolean expected = true;
        boolean actual = dbf.validateUser(username, password);
        
            assertTrue("Fail, Expected: " + expected + " Found: " + actual, expected == actual);
        
    }
    
    /**
     *Test for difference in captions
     */
//    @Test
    public void testValidateUser2() {
        String username ="HejmedDig";
        String password = "HELLo";
        
        User user = new User(username, password, 1, null, null, null, null, null);
        dbf.createUserDBFacade(user);
        
        
        boolean expected = false;
        boolean actual = dbf.validateUser(username, "HeLLO");
        
            assertTrue("Fail, Expected: " + expected + " Found: " + actual, expected == actual);
        
    }
    
    /**
     * difference in username
     */
    @Test
    public void testValidateUser3() {
        String username ="HejmedDig";
        String password = "HELLo";
        
        User user = new User(username, password, 1, null, null, null, null, null);
        dbf.createUserDBFacade(user);
        
        
        boolean expected = false;
        boolean actual = dbf.validateUser("medDig", password);
        
            assertTrue("Fail, Expected: " + expected + " Found: " + actual, expected == actual);
        
    }
    
    /**
     * Test with password difference in just a space
     */
    @Test
    public void testValidateUser4() {
        String username ="HejmedDig";
        String password = "HELLo";
        
        User user = new User(username, password, 1, null, null, null, null, null);
        dbf.createUserDBFacade(user);
        
        
        boolean expected = false;
        boolean actual = dbf.validateUser("HELLo ", password);
        
            assertTrue("Fail, Expected: " + expected + " Found: " + actual, expected == actual);
        
    }
    
    /**
     * Test to see if a user is created the right way.
     * Also needs to run the load method for loading to check if its right
     * Checks all the fields but the company name in user table.
     * 
     */
    @Test
    public void testCreateUserDBFacadeAndLoad() {
        String usernameexpected ="Danielh";
        String Passwordexpected="123";
        int customerIDexpected = 1;
        String fNameexpected= "Daniel";
        String lNameexpected= "Hollmann";
        String emailexpected = "danielhollmann@hotmail.com";
        String phoneexpected = "22878265";
        String companyNameexpected = "Redskins";

        User user = new User(usernameexpected,Passwordexpected,customerIDexpected,fNameexpected,lNameexpected,emailexpected,phoneexpected,companyNameexpected);
        dbf.createUserDBFacade(user);
        
        User actualUser = dbf.loadUser(usernameexpected);
        String usernameactual = actualUser.getUserName();
        String Passwordactual= actualUser.getPassword();
        int customerIDactual = actualUser.getCustomerid();
        String fNameactual= actualUser.getfName();
        String lNameactual= actualUser.getlName();
        String emailactual = actualUser.getEmail();
        String phoneactual = actualUser.getPhone();
        
        assertTrue("Fail, Expeted: " +usernameexpected + " Found: " + usernameactual,usernameexpected.equals(usernameactual));
        assertTrue("Fail, Expeted: " +Passwordexpected + " Found: " + Passwordactual,Passwordexpected.equals(Passwordactual));
        assertTrue("Fail, Expeted: " +customerIDexpected + " Found: " + customerIDactual,customerIDexpected == customerIDactual);
        assertTrue("Fail, Expeted: " +fNameexpected + " Found: " + fNameactual,fNameexpected.equals(fNameactual));
        assertTrue("Fail, Expeted: " +lNameexpected + " Found: " + lNameactual,lNameexpected.equals(lNameactual));
        assertTrue("Fail, Expeted: " +emailexpected + " Found: " + emailactual,emailexpected.equals(emailactual));
        assertTrue("Fail, Expeted: " +phoneexpected + " Found: " + phoneexpected,phoneexpected.equals(phoneactual));
    }

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
    public void testMain() {
    }

    @Test
    public void testGetInstance() {
    }

    @Test
    public void testGetCustomer() {
    }

    @Test
    public void testValidateUser() {
    }

    @Test
    public void testSaveNewReport() {
    }

    @Test
    public void testSaveReportRoom() {
    }

    @Test
    public void testSaveReportExt() {
    }

    @Test
    public void testSaveReportRoomDamage() {
    }

    @Test
    public void testSaveReportInterior() {
    }

    @Test
    public void testSaveReportRoomRec() {
    }

    @Test
    public void testGetReport() {
    }

    @Test
    public void testGetReportExt() {
    }

    @Test
    public void testGetReportRoom() {
    }

    @Test
    public void testGetReportDamage() {
    }

    @Test
    public void testGetReportInt() {
    }

    @Test
    public void testGetReportRec() {
    }

    @Test
    public void testGetListOfExt() {
    }

    @Test
    public void testGetListOfReportRoom() {
    }

    @Test
    public void testGetListOfDamages() {
    }

    @Test
    public void testGetListOfInt() {
    }

    @Test
    public void testGetListOfRec() {
    }

    @Test
    public void testAddCustomer() {
    }

    @Test
    public void testSaveContact() {
    }

    @Test
    public void testSaveReportMoist() {
    }

    @Test
    public void testGetListOfContacts() {
    }

    @Test
    public void testSaveNewBuilding() {
    }

    @Test
    public void testGetListOfbuildingsDB() {
    }

    @Test
    public void testUpdateBuildingDBFacade() {
    }

    @Test
    public void testNewReportToDB() {
    }

    @Test
    public void testLoadUser() {
    }

    @Test
    public void testCreateUserDBFacade() {
    }

    @Test
    public void testGetCon() {
    }

    @Test
    public void testSetCon() {
    }
    
}
