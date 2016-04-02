/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.DomainFacade;
import java.io.IOException;
import java.io.PrintWriter;
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
    public HttpServletRequest process(HttpServletRequest request, HttpServletResponse response,DomainFacade df){
        this.df=df;
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
        int reportBuildingId = 1; //some bookkeeping to be done
        int reportCategory = Integer.parseInt(request.getParameter("category"));
        df.saveNewReport(reportDate,reportBuildingId,reportCategory);
        String buildingName = request.getParameter("buildingName");
        //String buildingName = request.getParameter("buildingName");
    }
}
