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

/**
 *
 * @author dennisschmock
 */
public class DBFacadeTestBuilding {
    DBFacade dbf;
    public DBFacadeTestBuilding() {
        
    }
    
    @Before
    public void setUp() throws Exception {
        JdbcTest test = new JdbcTest();
        test.setUp();
        dbf = DBFacade.getInstance();
    }

    @Test
    public void testMain() {
    }


//
//    @Test
//    public void testGetListOfContacts() {
//    }
//
    @Test
    public void testSaveNewBuilding() {
        dbf.
    
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
    
}
