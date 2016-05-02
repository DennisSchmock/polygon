/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.Building;
import Domain.BuildingFloor;
import Domain.BuildingRoom;
import Domain.DomainFacade;
import Domain.Exceptions.PolygonException;
import Domain.Report;
import Domain.ReportExterior;
import Domain.ReportPic;
import Domain.ReportRoom;
import Domain.ReportRoomDamage;
import Domain.ReportRoomInterior;
import Domain.ReportRoomMoist;
import Domain.ReportRoomRecommendation;
import Domain.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Dennis, Daniel, Daniel, Cherry
 */
public class ReportHelper {
    private final PrinterPDF printer = new PrinterPDF();
    NewFileUpload nfu;
    
    public ReportHelper(NewFileUpload nfu){
        this.nfu = nfu;
    }

    /**
     * This method is for create a Report Room Recomendations Element.
     * Then it will be stored in the ReportROOM Attribute
     * @param roomRecomendations
     * @param sessionObj
     */
    private void createRoomRecomendation(String roomRecomendations, HttpSession sessionObj) {
        ReportRoomRecommendation roomRecommendation = new ReportRoomRecommendation(roomRecomendations);
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");
        if (reportRoom.getListOfRec() == null) {
            //Means that there are no Recomendation Elements in the Report Room
            ArrayList<ReportRoomRecommendation> temp = new ArrayList<>();
            temp.add(roomRecommendation);
            reportRoom.setListOfRec(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        } else {
            // Means that there is already some Recomendation Elements in the Report Room.
            ArrayList<ReportRoomRecommendation> temp = reportRoom.getListOfRec();
            temp.add(roomRecommendation);
            reportRoom.setListOfRec(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        }
    }

    /**
     * This method creates an Report_Room_Interior obejct an stores it in the
     * Report_Room Attribute
     * @param examinedpart A String that specifics what part of the room the remark belongs to
     * @param remark The remark the user has filled in
     * @param sessionObj Session object that holds the Report_Room Attriubte
     */
    private void createRoomInteriorElement(String examinedpart, String remark, HttpSession sessionObj) {
        ReportRoomInterior interiorElement = new ReportRoomInterior(examinedpart, remark);
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");
        if (reportRoom.getListOfInt() == null) {
            //Means that there are no Interior Elements in the Report Room
            ArrayList<ReportRoomInterior> temp = new ArrayList<>();
            temp.add(interiorElement);
            reportRoom.setListOfInt(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        } else {
            // Means that there is already some Interor Elements in the Report Room.
            ArrayList<ReportRoomInterior> temp = reportRoom.getListOfInt();
            temp.add(interiorElement);
            reportRoom.setListOfInt(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        }
    }

    /**
     * This method is for create a Report Room Damage Element.
     * Then it will be stored in the ReportROOM Attribute
     * @param damageTime
     * @param damagePlace
     * @param damageWhatHasHappend
     * @param damageRepaired
     * @param damageType
     * @param sessionObj
     */
    private void createRoomDamageElement(String damageTime, String damagePlace, String damageWhatHasHappend, String damageRepaired, String damageType, HttpSession sessionObj) {
        ReportRoomDamage roomDamage = new ReportRoomDamage(damageTime, damagePlace, damageWhatHasHappend, damageRepaired, damageType);
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");
        if (reportRoom.getListOfDamages() == null) {
            //Means that there are no Damage Elements in the Report Room
            ArrayList<ReportRoomDamage> temp = new ArrayList<>();
            temp.add(roomDamage);
            reportRoom.setListOfDamages(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        } else {
            // Means that there is already some Damage Elements in the Report Room.
            ArrayList<ReportRoomDamage> temp = reportRoom.getListOfDamages();
            temp.add(roomDamage);
            reportRoom.setListOfDamages(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        }
    }

    /**
     * Saves the inserted Condition Grade and date to the report object.
     * Also saves the CompanyMan responsable for the building.
     * @param request Hold the field with the condition grade.
     * @param sessionObj Hold the report obejct
     */
    void finishReportObject(HttpServletRequest request, HttpSession sessionObj) {
        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");
        int conditionGrade = Integer.parseInt(request.getParameter("Condition Grade"));
        report.setCategoryConclusion(conditionGrade);
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        report.setDate(date);
        String customerAccountable = request.getParameter("customerAccountable");
        report.setCustomerAccountable(customerAccountable);
        sessionObj.setAttribute("reportToBeCreated", report);
    }

    /**
     * This method is for create a Report Room MOIST Element.
     * Then it will be stored in the ReportROOM Attribute
     * @param moistScanResult
     * @param moistScanArea
     * @param sessionObj
     */
    private void createRoomMoistElement(String moistScanResult, String moistScanArea, HttpSession sessionObj) {
        ReportRoomMoist roomMoist = new ReportRoomMoist(moistScanResult, moistScanArea);
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");
        reportRoom.setMoist(roomMoist);
        sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
    }

    /**
     * Takes the ellements form the request and saves it, all that is needed for
     * creating the exterior decription in the report. Also saves the new
     * infomation in the report object. OBS: DOES NOT HANDLE
     * PICTURES!!!!!!!!!!!!!!!!!!
     *
     * @param request Holds the fields, the user have inserted.
     * @param sessionObj Holds obejcts like report, and building for report
     */
    public void saveReportExterior(HttpServletRequest request, HttpSession sessionObj, Collection<Part> parts, FrontControl frontControl) {
        String remarksOnRoof = request.getParameter("remarksOnRoof");
        String remarksOnWalls = request.getParameter("remarksOnWall");
        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");
        ReportExterior roofEx = new ReportExterior("Roof", remarksOnRoof);
        ReportExterior wallEx = new ReportExterior("Wall", remarksOnWalls);
        ArrayList<ReportPic> extPic = new ArrayList();
        String filepath = nfu.saveExtPicture(frontControl.getServletContext().getRealPath(""), parts);
        String description = request.getParameter("decriptionOfPicture");
        extPic.add(new ReportPic(filepath, description));
        report.setListOfExtPics(extPic);
        if (report.getListOfRepExt() == null) {
            ArrayList<ReportExterior> listOfExt = new ArrayList<>();
            listOfExt.add(wallEx);
            listOfExt.add(roofEx);
            report.setListOfRepExt(listOfExt);
        } else {
            ArrayList<ReportExterior> listOfExt = report.getListOfRepExt();
            listOfExt.add(wallEx);
            listOfExt.add(roofEx);
            report.setListOfRepExt(listOfExt);
        }
        sessionObj.setAttribute("reportToBeCreated", report);
    }

    /**
     * Creates the Report based on only the building object. Method should be
     * called right when the user has chosen which building to create a report
     * for. At this point, the report object does not contain any details, but
     * only infomation regarding to building, and the Employee that creates it.
     * Also loads the building object of the building to be created an stores it
     * in the session.
     *
     * @param request
     * @param sessionObj
     * @param df
     */
    void createReport(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        int buildingID = Integer.parseInt(request.getParameter("buildings"));
        User polygonUser = (User) sessionObj.getAttribute("user");
        String polygonUserID = polygonUser.getUserName();
        Report report = new Report(buildingID, polygonUserID);
        sessionObj.setAttribute("reportToBeCreated", report);
        Building b = df.getBuilding(buildingID);
        sessionObj.setAttribute("reportBuilding", b);
    }

    /**
     * This method can be accesses in two ways.
     * Either the user has selected an already existing room to inspected
     * or the user has just created an new BUILDING room, that the user now wants
     * to inspect.
     * @param request Holds the Fields to Create the Report_ROOM
     * @param sessionObj
     */
    void setUpForRoomInspection(HttpServletRequest request, HttpSession sessionObj, DomainFacade df, Collection<Part> parts) throws PolygonException {
        int buildingRoomid;
        if (request.getParameter("RoomSelected") != null) {
            /*
            This means that the user has selected an already existing room
            to inspected. Therefore it is the parameter that is to be used!
             */
            buildingRoomid = Integer.parseInt(request.getParameter("RoomSelected"));
        } else {
            /*
            This means that the user has just created an room
            to inspected. Therefore it is the attribute is used
             */
            buildingRoomid = (int) (request.getAttribute("RoomSelected"));
        }
        Building temp = (Building) sessionObj.getAttribute("reportBuilding"); // finds the building object from session
        BuildingRoom buildingRoom = null;
        // Loops through all the rooms for the building
        // To find the one the user has selected.
        for (BuildingFloor floor : temp.getListOfFloors()) {
            for (BuildingRoom Room : floor.getListOfRooms()) {
                if (Room.getRoomId() == buildingRoomid) {
                    buildingRoom = Room;
                }
            }
        }
        ReportRoom reportRoom = new ReportRoom(buildingRoom.getRoomName(), buildingRoom.getRoomId());
        BuildingFloor buildingFloor = df.getBuildingFloor(buildingRoom.getFloorid());
        reportRoom.setRoomFloor(buildingFloor.getFloorNumber() + "");
        sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
    }

    /**
     * This method creates the reportRoom elements that is then added to the
     * report_room, that is added to Report. Checks the fields to see if they have
     * been filled or not, and the passes them to other methods that creates the objects
     * @param request Holds all the parameters that the user has put in to
     * the fields in the jsp site.
     * @param sessionObj Session object holds the Report object that needs to
     * be updated.
     */
    void createReportRoomElements(HttpServletRequest request, HttpSession sessionObj, Collection<Part> parts, FrontControl frontControl) {
        //For the interior / Examination:
        if (request.getParameter("Examination").equalsIgnoreCase("Remarks")) {
            // This means that the user has check the radio button to yes.
            if (request.getParameter("Floor") != null || !(request.getParameter("Floor").equals(""))) {
                //This means that Field has been filled by the user:
                createRoomInteriorElement("Floor", request.getParameter("Floor"), sessionObj);
            }
            if (request.getParameter("Window") != null || !(request.getParameter("Window").equals(""))) {
                //This means that Field has been filled by the user:
                createRoomInteriorElement("Window", request.getParameter("Window"), sessionObj);
            }
            if (request.getParameter("Celling") != null || !(request.getParameter("Celling").equals(""))) {
                //This means that Field has been filled by the user:
                createRoomInteriorElement("Celling", request.getParameter("Celling"), sessionObj);
            }
            if (request.getParameter("Other") != null || !(request.getParameter("Other").equals(""))) {
                //This means that Field has been filled by the user:
                createRoomInteriorElement("Other", request.getParameter("Other"), sessionObj);
            }
        }
        // For the Damage:
        if (request.getParameter("damage").equalsIgnoreCase("Damage")) {
            // The user has Check the damages Field, We can move the try-catch if this works!
            String damageTime = request.getParameter("damageTime");
            String damagePlace = request.getParameter("damagePlace");
            String damageWhatHasHappend = request.getParameter("damageHappend");
            String damageRepaired = request.getParameter("damageReparied");
            String damageType = request.getParameter("damageType");
            createRoomDamageElement(damageTime, damagePlace, damageWhatHasHappend, damageRepaired, damageType, sessionObj);
        }
        //For Moist:
        if (request.getParameter("Moist").equalsIgnoreCase("Moist")) {
            // The user has Check the moist Field, We can move the try-catch if this works!
            String moistScanResult = request.getParameter("moistScanResult");
            String moistScanArea = request.getParameter("moistScanArea");
            createRoomMoistElement(moistScanResult, moistScanArea, sessionObj);
        }
        //For Recomendations:
        if (request.getParameter("Recommendation").equalsIgnoreCase("Recommendation")) {
            // The user has Check the Recomendation Field, We can move the try-catch if this works!
            String roomRecomendations = request.getParameter("recomendation");
            createRoomRecomendation(roomRecomendations, sessionObj);
        }
        // Stuff for adding reportRoomPics
        ArrayList<ReportPic> rrPic = new ArrayList();
        String description = request.getParameter("roompicdescrip");
        if (description == null) {
            description = "";
        }
        
        rrPic = nfu.addReportRoomPics(frontControl.getServletContext().getRealPath(""), description, parts);
        // After all of the elemets has been added to the report_Room
        // The report_Room, should be saved in the Report att.
        // And then be removed, since there now needs to be an new object inserted
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");
        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");
        reportRoom.setRrPic(rrPic);
        if (report.getListOfRepRoom() == null) {
            // means that the report does not contain any rooms yet
            ArrayList<ReportRoom> roomList = new ArrayList();
            roomList.add(reportRoom);
            report.setListOfRepRoom(roomList);
            sessionObj.setAttribute("reportToBeCreated", report);
        } else {
            // means that the report allready has Rooms
            ArrayList<ReportRoom> roomList = report.getListOfRepRoom();
            roomList.add(reportRoom);
            report.setListOfRepRoom(roomList);
            sessionObj.setAttribute("reportToBeCreated", report);
        }
    }

    /**
     * Method for creating an Report in the PDF Format.
     * Also sends via the response.setHeader() back to the View
     * So that the user can download the just created report
     * @param sessionObj Hold the Report object that is used to retrive whitch report
     * to create
     * @param df Connection to the domain.
     * @param response Responce object to place the file in.
     * @throws Domain.Exceptions.PolygonException Throws an Polygon if there is any problem
     * with creating an PDF File
     */
    public void printReport(HttpSession sessionObj, DomainFacade df, HttpServletResponse response, FrontControl frontControl) throws PolygonException {
        Report report = (Report) sessionObj.getAttribute("report");
        Building building = df.getBuilding(report.getBuildingId());
        String realPath = frontControl.getServletContext().getRealPath("");
        String fileName = "ReportFile" + report.getReportId();
        printer.sendReportToPrint(report, building, realPath, fileName);
        
        //This tells the browser that we will send a PDF file to the browser
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
        File filepath = new File(realPath + File.separator);
        filepath = new File(filepath.getParentFile().getParent() + File.separator + "web" + File.separator + "pdfReports");
        String absFilePath = filepath.getPath();
        //This is the absoulte File Path, for the File that has just been created!
        absFilePath += File.separator + fileName + ".pdf";
        File my_file = new File(absFilePath);
        //output Stream and Input Stream are to read from the PDF and Write to the PDF
        // that will be send to the Browser
        OutputStream out;
        try {
            out = response.getOutputStream();
            FileInputStream in = new FileInputStream(my_file);
            byte[] buffer = new byte[4096];
            int length;
            // This makes sure that the writer is finished with creating
            // the PDF Document
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            // this closes the Stream.
            // No need for Response redirct because we have already specified that
            // the response should make a download.
            in.close();
            out.flush();
        } catch (IOException ex) {
            System.out.println("Error in Creating an PDf" + ex);
            throw new PolygonException("Error in Printing the pdf");
        }
    }

    /**
     * This method should be called when the report is ready to be saved and finished
     * This method saves the report object, that is in the session object, to the database.
     * @param sessionObj Holds the report object
     * @param df Holds the connection to the domain layer
     */
    int saveFinishedReport(HttpSession sessionObj, DomainFacade df) throws PolygonException {
        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");
        return df.saveReport(report);
    }
    
}
