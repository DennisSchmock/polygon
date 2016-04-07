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

//    @Test
//    public void testMain() {
//    }

//    @Test
//    public void testGetInstance() {
//    }
//
//    @Test
//    public void testValidateUser() {
//    }
    
    /**
     * Test to see if a user is created the right way.
     * Also needs to run the load method for loading to check if its right 
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
        String companyNameactual = actualUser.getCompanyName();
        
        assertTrue("Fail, Expeted: " +usernameexpected + " Found: " + usernameactual,usernameexpected.equals(usernameactual));
        assertTrue("Fail, Expeted: " +Passwordexpected + " Found: " + Passwordactual,Passwordexpected.equals(Passwordactual));
        assertTrue("Fail, Expeted: " +customerIDexpected + " Found: " + customerIDactual,customerIDexpected == customerIDactual);
        assertTrue("Fail, Expeted: " +fNameexpected + " Found: " + fNameactual,fNameexpected.equals(fNameactual));
        assertTrue("Fail, Expeted: " +lNameexpected + " Found: " + lNameactual,lNameexpected.equals(lNameactual));
        assertTrue("Fail, Expeted: " +emailexpected + " Found: " + emailactual,emailexpected.equals(emailactual));
        assertTrue("Fail, Expeted: " +phoneexpected + " Found: " + phoneexpected,phoneexpected.equals(phoneactual));
        assertTrue("Fail, Expeted: " +companyNameexpected + " Found: " + companyNameactual,companyNameexpected.equals(companyNameactual));
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
    @Test
    public void testAddCustomer() {
       Customer c=new Customer("CPHBusiness","Miriam Sørensen","ms@cphbusiness.dk","Nørgaardsvej",30,21,3245,"Lyngby","+4553354494");
       dbf.addCustomer(c);
       Customer newCust = dbf.getCustomer(2);
       assertTrue("Get add customer-if null", newCust != null);
       assertTrue("Get add customer-check name",newCust.getCompanyName().equals("CPHBusiness")); 
       assertTrue("Get add customer-check contact person",newCust.getContactPerson().equals("Miriam Sørensen")); 
       assertTrue("Get add customer-check email",newCust.getCusMail().equals("ms@cphbusiness.dk")); 
       assertTrue("Get add customer-check street",newCust.getStreet().equals("Nørgaardsvej")); 
       assertTrue("Get add customer-check streetNumber",newCust.getStreetNumber()==30); 
       assertTrue("Get add customer-check CVR",newCust.getCusCVR()==21); 
       assertTrue("Get add customer-check zipcode",newCust.getZip()==3245); 
       assertTrue("Get add customer-check city",newCust.getCity().equals("Lyngby")); 
       assertTrue("Get add customer-check phone number",newCust.getPhoneNumber().equals("+4553354494")); 
       
    }

//    @Test
//    public void testSaveContact() {
////        Contact contact =new Contact();
//        
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
    
}
