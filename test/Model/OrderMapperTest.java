/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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
    Order orderWrongDate;
    Order orderWrongCustomer;
    String stringDate;
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
        date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        stringDate = "2016-04-20";
        rightOrder=new Order(date,"Check-up Building", "for annual inspection",1,1,1);
//        orderWrongDate = new Order(stringDate,"Check-up Building", "for annual inspection",1,1,1);
        orderWrongCustomer = new Order(date,"Check-up Building", "for annual inspection",1,1,20);
    }
    
    @After
    public void tearDown() {
        fixture.closeConnection();
    }

    @Test
    public void testAddNewOrderValidDataEntry() {
        
    }
    
    //invalid data type of Date, inserting String instead of sql date
    @Test
    public void testAddNewOrderInvalidDate() {
        
    }
    
    //trying to insert a new order that has a customer ID that doesn't exist
    @Test
    public void testAddNewOrderForInvalidCustomer() {
        
    }

    @Test
    public void testGetOrder() {
    }

    @Test
    public void testGetOrderStatus() {
    }

    @Test
    public void testUpdateOrder() {
    }
    
}
