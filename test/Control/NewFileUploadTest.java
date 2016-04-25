/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.Part;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.easymock.EasyMock.*;
import org.easymock.EasyMockSupport;


/**
 *
 * @author Dennis
 */
public class NewFileUploadTest extends EasyMockSupport{
    private File file;
    private NewFileUpload fileUpload;
    private Part part;
    
    public NewFileUploadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FileNotFoundException {
        file = mock(File.class);
        part = mock(Part.class);
        part.getHeaderNames();
        fileUpload = new NewFileUpload();
        
    }
    
    @After
    public void tearDown() {
    }

    

    /**
     * Test of uploadFile method, of class NewFileUpload.
     */
    @Test
    public void testUploadFile() {
      fileUpload.uploadFile(part, "/a", "/b", "test");
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

  

//    /**
//     * Test of getSinglePart method, of class NewFileUpload.
//     */
//    @Test
//    public void testGetSinglePart() {
//        System.out.println("getSinglePart");
//        Collection<Part> parts = null;
//        NewFileUpload instance = new NewFileUpload();
//        Part expResult = null;
//        Part result = instance.getSinglePart(parts);
//        
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllParts method, of class NewFileUpload.
//     */
//    @Test
//    public void testGetAllParts() {
//        System.out.println("getAllParts");
//        Collection<Part> parts = null;
//        NewFileUpload instance = new NewFileUpload();
//        List<Part> expResult = null;
//        List<Part> result = instance.getAllParts(parts);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
