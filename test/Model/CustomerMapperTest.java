/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Customer;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CJS
 */
public class CustomerMapperTest {
    
    CustomerMapper cm;
    DBFixture fixture;
    Connection con;
    TestHelper th;
    
    public CustomerMapperTest() {
        
    }
    
    @Before
    public void setUp() throws SQLException {
        fixture = new DBFixture();
        fixture.setUp();
        con = fixture.getConnection();
        cm = new CustomerMapper();
    }

    @Test
    public void testAddCustomerToDB() {
       Customer c=new Customer("CPHBusiness","Miriam Sørensen","ms@cphbusiness.dk","Nørgaardsvej",30,231,3245,"Lyngby","+4553354494");
       cm.addCustomerToDB(c, con);
       Customer newCust = th.getCustomer(1, con);
       assertTrue("Get add customer-if null", newCust != null);
//       assertTrue("Get Part failed2",p.getPno()== 10506); 
       
    }  

//    @Test
//    public void testSaveContact() {
//    }

//    @Test
//    public void testGetListOfContacts() {
//    }

//    @Test
//    public void testEditCustomer() {
//    }
//
//    @Test
//    public void testGetCustomer() {
//    }
    
}
