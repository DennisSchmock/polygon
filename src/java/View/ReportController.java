/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.Building;
import Domain.BuildingFloor;
import Domain.BuildingRoom;
import Domain.DomainFacade;
import Domain.Report;
import Domain.ReportRoom;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dennisschmock
 */
@WebServlet(name = "ReportController", urlPatterns = {"/viewreports", "/getreport", "/viewreport1", "/room"})
public class ReportController extends HttpServlet {

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
//        if(action.equalsIgnoreCase("reportroom")){
//            Report report = (Report) request.getSession().getAttribute("report");
//            int roomId = Integer.parseInt(request.getParameter("reportid"));
//            request.setAttribute("showReportRoom", true);
//            request.setAttribute("reportroom", report.getReportRoomFromReportFloor(roomId) );
//        }
//       
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

}
