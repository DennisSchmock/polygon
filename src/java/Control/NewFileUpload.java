/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.Building;
import Domain.BuildingFile;
import Domain.BuildingFiles;
import Domain.BuildingFloor;
import Domain.DomainFacade;
import Domain.Exceptions.PolygonException;
import Domain.Floorplan;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * The purpose of this class is to upload files and images to the folder on the server
 * (Note from Dennis: Most of the methods in this class should be moved to the Domain Layer)
 * @author Daniel Grønbjerg
 */
public class NewFileUpload {

    /**
     * Meant for the singular image of the building.
     *
     * Saves a single file to the db, returns null if no files are in the
     * Part-collection Otherwise returns the filename If more than one file is
     * submitted it takes the first one found in the collection.
     *
     * @param parentFolder the general project folder
     * @param parts Collection of parts that holds file(s)
     * @return
     */
    public String savePictureBuilding(String parentFolder, Collection<Part> parts) {

        System.out.println("Inside nfu savePicBuild");
        Part filePart = getSinglePart(parts);
        if (filePart != null && !filePart.getSubmittedFileName().isEmpty()) {
            System.out.println("filePart Not empty uploading");
            String[] fileDotSplit = filePart.getSubmittedFileName().split("\\."); //Split by dot
            String extension = fileDotSplit[fileDotSplit.length - 1];               //Take last part of filename(the extension)

            String filename = nextFileId(); //Make random filename and upload to folder.
            filename = filename + "." + extension;
            uploadFile(filePart, parentFolder, "buildingPic", filename);
            return filename; //Upload the file in buildingPicFolder
        }
        return null;

    }

    /**
     * Takes the potential multifile upload of building related documents
     * uploads it and saves the file information in a list of BuildingFile
     * objects
     *
     * @param parentFolder
     * @param parts
     * @return
     */
    public ArrayList<BuildingFile> saveBuildingDocs(String parentFolder, Collection<Part> parts) {
        ArrayList<BuildingFile> buildingfile = null;
        System.out.println("saveBuildingDocs");
        if (parts != null) {
            System.out.println("Parts not null");
            List<Part> fileParts = getAllParts(parts);
            buildingfile = new ArrayList();
            if (fileParts != null) {
                for (Part filePart : fileParts) {
                    BuildingFile bf = saveBuildingDoc(parentFolder, filePart);
                    buildingfile.add(bf);
                    System.out.println(bf.getDocumentname());
                    System.out.println(bf.getSize());
                }
            }
        }
        return buildingfile;
    }

    /**
     * Saves potential multifile upload to disk and returns an ArrrayList of the
     * Floorplan objects with info it generates
     *
     * @param parentFolder
     * @param parts
     * @return
     */
    public ArrayList<Floorplan> saveFloorplans(String parentFolder, Collection<Part> parts) {
        ArrayList<Floorplan> floorplans = null;
        System.out.println("saveFloorplans");
        if (parts != null) {
            System.out.println("Parts not null");
            List<Part> fileParts = getAllParts(parts);
            floorplans = new ArrayList();
            if (fileParts != null) {
                for (Part filePart : fileParts) {
                    Floorplan f = saveFloorplan(parentFolder, filePart);
                    floorplans.add(f);
                    
                }
            }
        }
        return floorplans;
    }

    /**
     * The purpose of this method, is to upload files to the exterior, and return the
     * filename.
     * @param parentFolder 
     * @param parts The filepart that should get uploaded.
     * @return
     */
    public String saveExtPicture(String parentFolder, Collection<Part> parts) {

        Part filePart = getSinglePart(parts);
        if (filePart != null) {
            String filename = getNewFileName(filePart);
            uploadFile(filePart, parentFolder, "ReportExtPic", filename);
            return filename; //Upload the file in buildingPicFolder
        }
        return null;
    }

    /**
     * The purpose of this method is to add pictures to the Report Room. It will return
     * an ArrayList with ReportPic objects
     * @param parentFolder folder to upload
     * @param description a short description of the picture
     * @param parts the picture to upload.
     * @return
     */
    public ArrayList<ReportPic> addReportRoomPics(String parentFolder, String description, Collection<Part> parts) {
        System.out.println("AddRepRoomPics");
        ArrayList<ReportPic> rrPic = null;
        if (parts != null) {
            System.out.println("Parts not null");
            List<Part> fileParts = getAllParts(parts);
            String filename;
            rrPic = new ArrayList();
            if (fileParts != null) {
                for (Part filePart : fileParts) {
                    filename = saveRoomPicture(parentFolder, filePart);
                    if (filename != null) {
                        rrPic.add(new ReportPic(filename, description));
                    }
                    System.out.println(filename);
                }
            }
        }
        return rrPic;
    }

    /**
     * The purpose of this method is, to add a picture to a ReportRoom. Returns a 
     * string with the filename.
     * @param parentFolder The folder to upload in.
     * @param part the file that should get uploaded
     * @return a filename
     */
    public String saveRoomPicture(String parentFolder, Part part) {

        System.out.println("Inside nfu saveRoomPic");

        if (part != null) {
            String filename = getNewFileName(part);
            uploadFile(part, parentFolder, "ReportRoomPic", filename);
            System.out.println(filename);
            return filename; //Upload the file in buildingPicFolder
        }
        return null;
    }

    /**
     * Uploads a file to the server. Helper method for any fileUpload
     *
     * @param filePart the Part that holds the file
     * @param parentFolder
     * @param folder the subfolder it should go into (has to exist beforehand,
     * uses relative path!)
     * @param filename the full name of the file.
     */
    public void uploadFile(Part filePart, String parentFolder, String folder, String filename) {
        // The Wrong way of doing things according to several sources (relative path)
        // Deliberate in this case for the purpose of being able to implement across multiple systems
        String uploadFolder = parentFolder//getServletContext().getRealPath("")
                + File.separator;
        File uploads = new File(uploadFolder);
        uploads = new File(uploads.getParentFile().getParent() + File.separator + "web" + File.separator + folder);
        if (filename != null) {
            File file = new File(uploads, filename);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath());
            } catch (IOException ex) {
                Logger.getLogger(FrontControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private SecureRandom random = new SecureRandom();

    /**
     * Creates a random String to use for filenaming to prevent duplicate
     *
     * @return 
     */
    public String nextFileId() {
        return new BigInteger(130, random).toString(32);
    }

    /**
     * the purpose of this method is, to extract a single filepart from the Collection<Part>
     * @param parts
     * @return
     */
    public Part getSinglePart(Collection<Part> parts) {
        for (Part part : parts) {
            if (part.getName().equals("uploadFile")) {
                return part;
            }
        }
        return null;
    }

    /**
     * The purpose of this method is to extract multiple filesparts from a Collection<Part>
     * and return them as a List<Part>.
     * @param parts
     * @return List<Part>
     */
    public List<Part> getAllParts(Collection<Part> parts) {
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

    /**
     * The purpose of this method, is to create a random filename for a file, and return it as a string.
     * @param filePart The file to be named
     * @return the filename
     */
    public String getNewFileName(Part filePart) {
        String[] fileDotSplit = filePart.getSubmittedFileName().split("\\."); //Split by dot
        String extension = "";
        String filename = null;
        if (fileDotSplit.length > 1) {
            extension = fileDotSplit[fileDotSplit.length - 1];               //Take last part of filename(the extension)
            filename = nextFileId(); //Make random filename and upload to folder.
            filename = filename + "." + extension;  //+ extension
        }
        return filename;
    }

    /**
     * The purpose of this method is to add documents to a building. 
     * @param parentFolder The folder to upload to
     * @param filePart The file to upload
     * @return a BuildingFile object
     */
    private BuildingFile saveBuildingDoc(String parentFolder, Part filePart) {
        System.out.println("Inside nfu saveBuildingDoc");
        if (filePart != null) {

            String filename = getNewFileName(filePart);
            String documentname = filePart.getSubmittedFileName();
            long bytesize = filePart.getSize();
            int size = (int) (bytesize / 1048576); //convert from byte to MB
            uploadFile(filePart, parentFolder, "buildingDocs", filename);
            System.out.println(filename);
            BuildingFile bf = new BuildingFile(size, filename, documentname);
            return bf;
        }
        return null;
    }

    /**
     * The purpose of this method is, to upload floorplans to a building.
     * @param parentFolder the folder to upload to
     * @param filePart the file to upload.
     * @return a Floorplan object.
     */
    private Floorplan saveFloorplan(String parentFolder, Part filePart) {
        System.out.println("Inside nfu saveFloorplan");
        if (filePart != null) {
            String filename = getNewFileName(filePart);
            String documentname = filePart.getSubmittedFileName();
            long bytesize = filePart.getSize();
            int size = (int) (bytesize);//1048576); //convert from byte to MB
            uploadFile(filePart, parentFolder, "floorplans", filename);
            System.out.println(filename);
            Floorplan f = new Floorplan(size, filename, documentname);
            return f;
        }
        return null;
    }

    /**
     * Strips request of fileparts and uploads them to the documents folder Then
     * puts the information it gets back into the Buildings BuildingFiles list
     * Lastly updates the information in the db
     *
     * @param request
     * @param parts
     * @param df
     * @param frontControl
     * @return
     * @throws Domain.Exceptions.PolygonException
     */
    public HttpServletRequest addFiles(HttpServletRequest request, Collection<Part> parts, DomainFacade df, FrontControl frontControl) throws PolygonException {
        ArrayList<BuildingFiles> files;
        Building b = (Building) request.getSession().getAttribute("building");
        if (b != null) {
            files = b.getListOfFiles();
            System.out.println("b not null");
            //Add to folder
            ArrayList<BuildingFile> file;
            String filesDescription;
            file = saveBuildingDocs(frontControl.getServletContext().getRealPath(""), parts);
            filesDescription = request.getParameter("fileRemarks");
            if (file == null) {
                System.out.println("file is null");
            }
            if (filesDescription == null) {
                System.out.println("fileDescrip is null");
            }
            if (files == null) {
                System.out.println("files is null");
            }
            files.add(new BuildingFiles(file, filesDescription));
            b.setListOfFiles(files);
            //Add to db
            df.saveBuildingFiles(b);
            request.setAttribute("filessubmitted", true);
        }
        request.setAttribute("roomfiles", true);
        return request;
    }

    /**
     * Strips request of fileparts and uploads them to the floorplan folder Then
     * puts the information it gets back into the Buildings Floor objects Lastly
     * updates the information in the db
     *
     * @param request
     * @param parts
     * @param df
     * @param frontControl
     * @return
     * @throws Domain.Exceptions.PolygonException
     */
    public HttpServletRequest addFloorplans(HttpServletRequest request, Collection<Part> parts, DomainFacade df, FrontControl frontControl) throws PolygonException {
        ArrayList<BuildingFloor> floors;
        Building b = (Building) request.getSession().getAttribute("building");
        if (b != null) {
            //Add to folder
            ArrayList<Floorplan> floorplans;
            floorplans = saveFloorplans(frontControl.getServletContext().getRealPath(""), parts);
            //Add to buildings floorobject
            floors = b.getListOfFloors();
            int chosenFloor = Integer.parseInt(request.getParameter("floors"));
            for (BuildingFloor floor : floors) {
                if (floor.getFloorId() == chosenFloor) {
                    ArrayList<Floorplan> fp = floor.getFloorplans();
                    fp.addAll(floorplans);
                    floor.setFloorplans(fp);
                }
            }
            //Add to db
            df.saveFloorplans(chosenFloor, floorplans);
            //Set succesattribute
            request.setAttribute("filessubmitted", true);
        }
        request.setAttribute("roomfiles", true);
        return request;
    }
}
