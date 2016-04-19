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
import Domain.BuildingRoom;
import Domain.DomainFacade;
import Domain.Floorplan;
import Domain.Report;
import Domain.ReportRoom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author dennisschmock
 */
@WebServlet(name = "ReportController", urlPatterns = {"/viewreports", "/getreport", "/viewreport1", "/room"})
@MultipartConfig
public class ReportController extends HttpServlet {
NewFileUpload nfu = new NewFileUpload();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Collection<Part> parts=null;
        if (ServletFileUpload.isMultipartContent(request)){     //Checks if the form might(!?) contain a file for upload
                      //Extracts the part of the form that is the file
        parts = request.getParts();
        }
        response.setContentType("text/html;charset=UTF-8");
        String url = "/viewbuildingadmin.jsp";

        String page = request.getParameter("page");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        if (page == null) {
            page = "";
        }

        if (request.getServletPath().equalsIgnoreCase("/viewreports")) {
            url = "/viewreports.jsp";
        }
        if (request.getServletPath().equalsIgnoreCase("/getreport")) {
            url = "/viewreport.jsp";
        }
        System.out.println(request.getServletPath());
        System.out.println(request.getMethod());

        DomainFacade df = (DomainFacade) request.getSession().getAttribute("Controller");

        //get the building and send it to the sessionobj
        System.out.println("test of action: " + action);
        if (action.equalsIgnoreCase("viewbuildingadmin")) {
            System.out.println("test!");
            int buildId = Integer.parseInt(request.getParameter("buildingid"));
            Building b = df.getBuilding(buildId);
            request.getSession().setAttribute("building", b);
            request.setAttribute("showBuilding", true);
        }

        //retrieve a room from the buildingobject and put it in response.
        if (action.equalsIgnoreCase("viewroom")) {
            Building b = (Building) request.getSession().getAttribute("building");
            int roomNumber;
            String viewReportRoomString = request.getParameter("viewroom");
            if (viewReportRoomString != null && b != null) {
                roomNumber = Integer.parseInt(viewReportRoomString);
                BuildingRoom r = b.returnARoom(roomNumber);
                request.getSession().setAttribute("room", r);
                request.getSession().setAttribute("building", b);
                request.setAttribute("showRoom", true);

            }
        }

        if (action.equalsIgnoreCase("addfloor")) {
            request.setAttribute("addFloor", true);

        }
        if (action.equalsIgnoreCase("addroom")) {
            int floorId = Integer.parseInt(request.getParameter("floor"));
            request.setAttribute("addRoom", true);
            request.setAttribute("floorId", floorId);

        }
        if (action.equalsIgnoreCase("listreports")) {
            request.getSession().setAttribute("reports", df.getSimpleListOfReports());
        }

        if (action.equalsIgnoreCase("addfloorsubmit")) {
            addFloors(request, df);

        }
        if (action.equalsIgnoreCase("addroomsubmit")) {
            int floorId = Integer.parseInt(request.getParameter("floorID"));
            addRoom(request, df, floorId);
            request.setAttribute("showBuilding", true);

        }
        if (action.equalsIgnoreCase("editbuilding")) {
            request.setAttribute("editBuilding", true);

        }
        if (action.equalsIgnoreCase("showBuilding")) {
            request.setAttribute("showBuilding", true);

        }
        if (action.equalsIgnoreCase("showBuilding")) {
            request.setAttribute("showBuilding", true);

        }
        if (action.equalsIgnoreCase("showreport")) {

            int reportId = Integer.parseInt(request.getParameter("reportid"));
            Report report = df.getReport(reportId);
            Building b = df.getBuilding(report.getBuildingId());
            report.setBuildingName(b.getBuildingName());
//
            request.getSession().setAttribute("report", report);

        }
        if (action.equalsIgnoreCase("reportroom")) {

            int reportRoomId = Integer.parseInt(request.getParameter("viewroom"));
            Report report = (Report) request.getSession().getAttribute("report");
            ReportRoom rr = report.getReportRoomFromReportFloor(reportRoomId);
//
            request.setAttribute("reportroom", rr);
            request.setAttribute("showroom", true);

        }
        
        //Trying to see if I can work this out
        if(action.equalsIgnoreCase("roomfiles")){
            //int buildId = Integer.parseInt(request.getParameter("buildingid"));
            request.setAttribute("roomfiles", true);
            //request.setAttribute("reportroom", report.getReportRoomFromReportFloor(roomId) );
        }
        if(action.equalsIgnoreCase("addfloorplans")){
            Building b = (Building)request.getSession().getAttribute("building");
            ArrayList<BuildingFloor> bfList = df.listOfFloors(b.getBdgId());
            ArrayList<Floorplan> plans= df.getFloorplans(bfList);
            
            request.getSession().setAttribute("floorplans", plans);
            request.getSession().setAttribute("floorsList", bfList);
            request.setAttribute("addfloorplans", true);
        }
        
        if(action.equalsIgnoreCase("addfilessubmit")){
             
            request = addFiles(request,parts, df);
            
        }
        
        if(action.equalsIgnoreCase("addfloorplanssubmit")){
             
            request = addFloorplans(request,parts, df);
            
        }
       
        if (action.equalsIgnoreCase("addBuilding")) {
            Building b = new Building();
            b.setBuildingName("tempname");
            request.getSession().setAttribute("building", b);

        }
        if (action.equalsIgnoreCase("viewbuildingadmin")) {
            int buildId = Integer.parseInt(request.getParameter("buildingid"));
            Building b = df.getBuilding(buildId);
            request.getSession().setAttribute("building", b);

        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    /**
     * The purpose of this method is, to add a new floor to a building, based on
     * the buildings id.
     *
     * @param request
     * @param df
     * @param sessionObj
     */
    private void addFloors(HttpServletRequest request, DomainFacade df) {
        String floorNum = (String) request.getParameter("floornumber");
        String floorSize = (String) request.getParameter("floorsize");
        String totalRooms = (String) request.getParameter("totalrooms");
        Building b = (Building) request.getSession().getAttribute("building");

        if (floorNum != null && b != null) {
            int n = (int) Integer.parseInt(floorNum);
            double s = (double) Double.parseDouble(floorSize);
            int r = (int) Integer.parseInt(totalRooms);
            BuildingFloor bf = new BuildingFloor(n, s, b.getBdgId());
            df.addFloors(bf);
            b = df.getBuilding(b.getBdgId());
            request.getSession().setAttribute("building", b);

        }

    }

    private void addRoom(HttpServletRequest request, DomainFacade df, int floorId) {
        Building b = (Building) request.getSession().getAttribute("building");

        String roomName = (String) request.getParameter("roomname");
        if (roomName != null) {
            BuildingRoom br = new BuildingRoom(roomName, floorId);
            df.addRoom(br);
            b = df.getBuilding(b.getBdgId());
            request.getSession().setAttribute("building", b);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Strips request of fileparts and uploads them to the documents folder
     * Then puts the information it gets back into the Buildings BuildingFiles list
     * Lastly updates the information in the db
     *
     * @param request
     * @param parts
     * @param df
     * @return
     */
    public HttpServletRequest addFiles(HttpServletRequest request, Collection<Part> parts, DomainFacade df) {
        ArrayList<BuildingFiles> files;
        Building b = (Building) request.getSession().getAttribute("building");
        int buildId;
            if (b!=null) {
                files=b.getListOfFiles();
                
                System.out.println("b not null");
                //Add to folder
                ArrayList<BuildingFile> file;
                
                String filesDescription;
                file=nfu.saveBuildingDocs(getServletContext().getRealPath(""),  parts);
                filesDescription=request.getParameter("fileRemarks");
                if (file==null)System.out.println("file is null");
                if (filesDescription==null)System.out.println("fileDescrip is null");
                if (files==null)System.out.println("files is null");
                files.add(new BuildingFiles(file,filesDescription));
                b.setListOfFiles(files);
                //Add to db
                df.saveBuildingFiles(b);
                
                request.setAttribute("filessubmitted", true);
            }
            request.setAttribute("roomfiles", true);
            return request;
    }

    /**
     * Strips request of fileparts and uploads them to the floorplan folder
     * Then puts the information it gets back into the Buildings Floor objects
     * Lastly updates the information in the db
     *
     * @param request
     * @param parts
     * @param df
     * @return
     */
    public HttpServletRequest addFloorplans(HttpServletRequest request, Collection<Part> parts, DomainFacade df) {
        ArrayList<BuildingFloor> floors;
        Building b = (Building) request.getSession().getAttribute("building");
        
            if (b!=null) {
                //Add to folder
                ArrayList<Floorplan> floorplans;
                floorplans=nfu.saveFloorplans(getServletContext().getRealPath(""),  parts);
                
                //Add to buildings floorobject
                floors=b.getListOfFloors();
                int chosenFloor = Integer.parseInt(request.getParameter("floors"));
                for (BuildingFloor floor : floors) {
                    if (floor.getFloorId()==chosenFloor){
                        ArrayList<Floorplan> fp =floor.getFloorplans();
                        fp.addAll(floorplans);
                        floor.setFloorplans(fp);
                    }
                }
                
                //Add to db
                df.saveFloorplans(chosenFloor,floorplans);
                
                //Set succesattribute
                request.setAttribute("filessubmitted", true);
            }
            request.setAttribute("roomfiles", true);
            return request;}

}
