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
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class ReportTester {
    
        DBFixture fixture;
        DBFacade dbf;
    
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
    }
    
    @After
    public void tearDown() {
         fixture.closeConnection();
    }
    
    @Test
    public void testthing(){
        
    }
    
}
