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
import Domain.Exceptions.PolygonException;

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
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author dennisschmock
 */

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
       
    }

   
}
