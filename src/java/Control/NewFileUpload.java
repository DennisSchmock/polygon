/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.Building;
import Domain.ReportPic;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

/**
 *
 * @author Daniel Grønbjerg
 */
public class NewFileUpload {
    
    
    

    
    /**
     * Meant for the singular image of the building.
     * 
     * Saves a single file to the db, returns null if no files are in the Part-collection
     * Otherwise returns the filename
     * If more than one file is submitted it takes the first one found in the collection.
     * 
     * @param parentFolder the general project folder
     * @param parts Collection of parts that holds file(s)
     * @return
     */
    public String savePictureBuilding(String parentFolder, Collection<Part> parts) {
        
        System.out.println("Inside nfu savePicBuild");
        
        Part filePart = getSinglePart(parts);
        if (filePart!=null) {
            
            String[] fileDotSplit = filePart.getSubmittedFileName().split("\\."); //Split by dot
            String extension = fileDotSplit[fileDotSplit.length - 1];               //Take last part of filename(the extension)

            String filename = nextFileId(); //Make random filename and upload to folder.
            filename = filename + "." + extension;  //+ extension
            uploadFile(filePart, parentFolder,"buildingPic", filename);   
            return filename; //Upload the file in buildingPicFolder
        }
        return null;
        
    }
    
    public String saveExtPicture(String parentFolder, Collection<Part> parts) {
        
        System.out.println("Inside nfu saveExtPic");
        
        Part filePart = getSinglePart(parts);
        if (filePart!=null) {
            String filename=getNewFileName(filePart);
            uploadFile(filePart, parentFolder,"ReportExtPic", filename);
            System.out.println(filename);
            return filename; //Upload the file in buildingPicFolder
        }
        return null;
        
    }
    
    public ArrayList<ReportPic> addReportRoomPics(String parentFolder, String description, Collection<Part> parts){
        System.out.println("AddRepRoomPics");
        ArrayList<ReportPic> rrPic=null;
        if (parts!=null){
            System.out.println("Parts not null");
        List<Part> fileParts = getAllParts(parts);
        String filename;
        rrPic= new ArrayList();
        if (fileParts!=null){
            for (Part filePart : fileParts) {
                filename=saveRoomPicture(parentFolder,filePart);
                rrPic.add(new ReportPic(filename,description));
                System.out.println(filename);
            }
            
        }
        }
        
        return rrPic;
    }
    
    public String saveRoomPicture(String parentFolder, Part part) {
        
        System.out.println("Inside nfu saveRoomPic");
        
        
        if (part!=null) {
            String filename=getNewFileName(part);
            uploadFile(part, parentFolder,"ReportRoomPic", filename);
            System.out.println(filename);
            return filename; //Upload the file in buildingPicFolder
        }
        return null;
        
    }

    /**
     * Uploads a file to the server. Helper method for any fileUpload
     *
     * @param filePart the Part that holds the file
     * @param folder the subfolder it should go into (has to exist beforehand,
     * uses relative path!)
     * @param filename the full name of the file.
     */
    private void uploadFile(Part filePart,String parentFolder, String folder, String filename) {
        // The Wrong way of doing things according to several sources (relative path)
        // Deliberate in this case for the purpose of being able to implement across multiple systems
        String uploadFolder = parentFolder//getServletContext().getRealPath("")
                + File.separator;

        File uploads = new File(uploadFolder);
        uploads = new File(uploads.getParentFile().getParent() + File.separator + "web" + File.separator + folder);
        if (filename!=null){
        File file = new File(uploads, filename);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(FrontControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    private SecureRandom random = new SecureRandom();

    public String nextFileId() {
        return new BigInteger(130, random).toString(32);
    }
    
    public Part getSinglePart(Collection<Part> parts){
                for (Part part : parts) {
                    if (part.getName().equals("uploadFile")) {
                        return part;
                    }
                }
                return null;

    }
    public List<Part> getAllParts(Collection<Part> parts){
        List<Part> fileParts = new ArrayList();
        
                //Checks if the form might(!?) contain a file for upload
                //Extracts the part of the form that is the file
                for (Part part : parts) {
                    if (part.getName().equals("uploadFile")) {
                        fileParts.add(part);
                    }
                }
        return fileParts;
        
    }
    
    public String getNewFileName(Part filePart){
        String[] fileDotSplit = filePart.getSubmittedFileName().split("\\."); //Split by dot
        String extension="";
        String filename=null;
        if (fileDotSplit.length>1){
            extension = fileDotSplit[fileDotSplit.length - 1];               //Take last part of filename(the extension)
            filename = nextFileId(); //Make random filename and upload to folder.
            filename = filename + "." + extension;  //+ extension
    }
            return filename;
    }
    
    
}
