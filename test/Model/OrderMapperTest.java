/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Order;
import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bruger
 */
public class OrderMapperTest {
    DBFixture fixture;
    DBFacade dbf;
    Order rightOrder;
    Order orderWrongCustomer;
    Order orderWrongBuilding;
//    Order orderWrongStat;
//    String stringStat;
    Date date;
    
    public OrderMapperTest() {
        
    }
    
    @Before
    public void setUp() throws Exception {
        fixture = new DBFixture();
        fixture.setUp();
        dbf = DBFacade.getInstance();
        Connection con =fixture.getConnection();
        con.setAutoCommit(true);
        dbf.setCon(con);
//        stringStat = "Ongoing";
        date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        rightOrder=new Order(date,"Check-up Building", "for annual inspection",1,1,1);
        orderWrongCustomer = new Order(date,"Check-up Building", "for annual inspection",1,1,20);
        orderWrongBuilding = new Order(date,"Check-up Building", "for annual inspection",1,2,1);
//        orderWrongStat = new Order(date,"Check-up Building", "for annual inspection",stringStat,1,1);
    }
    
    @After
    public void tearDown() {
        fixture.closeConnection();
    }

    
    //rightOrder is the expected order to be saved in the database
    @Test
    public void testAddNewOrderValidDataEntry() {
       boolean isSaved = dbf.addNewOrder(rightOrder);
       assertTrue("Order is saved!",isSaved);
    }
    
    //trying to insert a new order that has a customer that doesn't exist
    @Test
    public void testAddNewOrderForInvalidCustomer() {
        boolean isSaved = dbf.addNewOrder(orderWrongCustomer);
        assertFalse("Fail to Save!",isSaved);
    }

    //trying to insert a new order that has a building that doesn't exist
    @Test
    public void testAddNewOrderForInvalidBuilding() {
        boolean isSaved = dbf.addNewOrder(orderWrongBuilding);
        assertFalse("Fail to Save!",isSaved);
    }
//    
//    //trying to insert a new order that has a wrong data type of order status
//    @Test
//    public void testAddNewOrderInvalidStatus() {
//        boolean isSaved = dbf.addNewOrder(orderWrongStat);
//        assertFalse("Fail to Save!",isSaved);
//    }
}
