/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.Building;
import Domain.BuildingRoom;
import Domain.DomainFacade;
import Domain.Report;
import Domain.ReportRoom;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.RequestDispatcher;

/**
 *
 * @author danie
 */

public class ReportHelper extends HttpServlet{
    DomainFacade df;
    Building b; // This is only a placeholder until we can get a building out of db
    BuildingRoom r1;
    BuildingRoom r2;
    BuildingRoom r3;
    ArrayList listOfRooms;
    
    public ReportHelper(){
        b = new Building("Vaskeriet", "Nørregårdsvej", "28", 2800, 1978, 145.6, "Study facility");
        r1 = new BuildingRoom(0,"Entrance");
        r2 = new BuildingRoom(1,"Toilets");
        r3 = new BuildingRoom(2,"Main Room");
        listOfRooms= new ArrayList<BuildingRoom>();
        listOfRooms.add(r1);
        listOfRooms.add(r2);
        listOfRooms.add(r3);
        b.setListOfRooms(listOfRooms);
    }
    
    
    public HttpServletRequest process(HttpServletRequest request, HttpServletResponse response,DomainFacade df){
        this.df=df;
        HttpSession session = request.getSession();
        session.setAttribute("testBuilding", b); //Using a mock-instance of building until we can create from DB
        session.setAttribute("buildingRooms", b.getListOfRooms());
        String command = (String)request.getParameter("command");
        if (command==null)command="";
        if (command.equals("reportAddRoom")) request=AddRoom(request,response);
        if (command.equals("reportSubmit")) submitReport(request,response);
        return request;
        
    }
  
    public void AddDamage(){
        
    }
    
    // Add a new form for adding a rooms report

    /**
     * Increments the counter that the report.jsp uses to determine the 
     * amount of input fields for room damages
     * @param request
     * @param response
     * @return
     */
    public HttpServletRequest AddRoom(HttpServletRequest request, HttpServletResponse response){
        int numOfRooms;
        if (request.getParameter("numOfRooms")!=null && !(request.getParameter("numOfRooms")).equals("")){
        numOfRooms = Integer.parseInt(request.getParameter("numOfRooms"))+1;
        }
        else numOfRooms = 1;
        request.setAttribute("numOfRooms", numOfRooms);
        return request;
    }
    
    /**
     * Uses information from the request to submit a report including subparts in the database
     * @param request
     * @param response
     */
    public void submitReport(HttpServletRequest request, HttpServletResponse response){
        //Add report
        //Add reportRoom
        //Add reportRoomDamage
        String reportDate = request.getParameter("date");
        int reportBuildingId = 1; //some bookkeeping to be done with ID
        int reportCategory = Integer.parseInt(request.getParameter("category"));
        Report r =df.saveNewReport(reportDate,reportBuildingId,reportCategory);
        System.out.println("Report ID");
        System.out.println(r.getReportId());
        int reportId = r.getReportId();
        String roof  = request.getParameter("roof");
        String walls = request.getParameter("walls");
        df.saveNewReportExt(1,roof,reportBuildingId,reportId);
        df.saveNewReportExt(1,walls,reportBuildingId,reportId);
        
        
        System.out.println("Ready for forLoop in submitReport");
        int numOfRooms = 0;
        if (request.getAttribute("numOfRooms")!=null) numOfRooms = (int)request.getAttribute("numOfRooms");
         
        for (int roomCount = 0; roomCount <= numOfRooms; roomCount++) {
            System.out.println("when1");
            System.out.println(request.getParameter("when1"));
            System.out.println("when2");
            System.out.println(request.getParameter("when2"));
            
        ReportRoom rr=df.saveReportRoom(0, "ShouldGetFromRoom", r.getReportId());
        
        int rrId=rr.getRepRoomId();
        String when = request.getParameter("when"+String.valueOf(roomCount+1));
        String where = request.getParameter("where"+String.valueOf(roomCount+1));
        String how = request.getParameter("how"+String.valueOf(roomCount+1));
        String whatIsDone = request.getParameter("whatIsDone"+String.valueOf(roomCount+1));
        df.saveReportRoomDamage(when, where, how, whatIsDone, "Add dmgType", rrId);
        
        }
    }
}
