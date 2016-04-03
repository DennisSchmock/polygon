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
    public HttpServletRequest AddRoom(HttpServletRequest request, HttpServletResponse response){
        int numOfRooms;
        if (request.getParameter("numOfRooms")!=null && !(request.getParameter("numOfRooms")).equals("")){
        numOfRooms = Integer.parseInt(request.getParameter("numOfRooms"))+1;
        }
        else numOfRooms = 1;
        request.setAttribute("numOfRooms", numOfRooms);
        return request;
    }
    
    public void submitReport(HttpServletRequest request, HttpServletResponse response){
        
        String reportDate = request.getParameter("date");
        int reportBuildingId = 1; //some bookkeeping to be done with ID
        int reportCategory = Integer.parseInt(request.getParameter("category"));
        Report r =df.saveNewReport(reportDate,reportBuildingId,reportCategory);
        int reportId = r.getReportId();
        String roof  = request.getParameter("roof");
        String walls = request.getParameter("walls");
        df.saveNewReportExt(1,roof,reportBuildingId,reportId);
        df.saveNewReportExt(1,walls,reportBuildingId,reportId);
        //String buildingName = request.getParameter("buildingName");
    }
}
