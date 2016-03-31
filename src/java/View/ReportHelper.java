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
    
    public HttpServletRequest process(HttpServletRequest request, HttpServletResponse response,DomainFacade df){
        
        String command = (String)request.getAttribute("command");
        if (command==null)command="";
        if (command.equals("reportAddRoom")) request=AddRoom(request,response);
        return request;
    }
  
    public void AddDamage(){
        
    }
    public HttpServletRequest AddRoom(HttpServletRequest request, HttpServletResponse response){
        int numOfRooms = (int)request.getAttribute("numOfRooms");
        System.out.println(numOfRooms);
        return request;
    }
    
    public void submitReport(){
        
    }
}
