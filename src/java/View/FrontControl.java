/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.DomainFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * The front controller for all the JSP Sites. All the JSP sites returns through
 * actions to this servlet and then the sevlet passes on information to the
 * controller class, and later redirects the user. An instance of the controller
 * class is kept in the session object so that it does not have to create an new
 * instance every time the JSP returns here.
 *
 * @author dennisschmock
 */
@WebServlet(name = "FrontControl", urlPatterns = {"/frontpage", "/Style/frontpage"})
public class FrontControl extends HttpServlet {

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
        System.out.println("frontControl to Major Tom");

        HttpSession sessionObj = request.getSession(); //Get the session
        ReportHelper rh = new ReportHelper();

        DomainFacade df = (DomainFacade) sessionObj.getAttribute("Controller"); //Get the DomainFacede
        //If it is a new session, create a new DomainFacade Object and put it in the session.
        if (df == null) {
            df = DomainFacade.getInstance();
            sessionObj.setAttribute("Controller", df);
        } 
        
        response.setContentType("text/html;charset=UTF-8");
        
        //Set base url
        String url = "/index.jsp";
        String page = request.getParameter("page");
        

        if (page == null) {
            page = "/report.jsp";
        }
        if (page.equalsIgnoreCase("report")) {
            url = "/report.jsp";
            request=rh.process( request, response,df);
        }

        if (page.equalsIgnoreCase("reportSubmit")) {
            //url = "/report.jsp";
        }
        if (page.equalsIgnoreCase("reportAddRoom")) {
            //url = "/report.jsp";
        }
        if (page.equalsIgnoreCase("addbuilding")) {
            url = "/addbuilding.jsp";
        }
        
        if (page.equalsIgnoreCase("newbuilding")){
            createBuilding(request,df);
            url = "/viewnewbuilding.jsp";
        }

        if (page.equalsIgnoreCase("test")) {
            url = "/index.jsp";
            request.getSession().setAttribute("test", "tester");

        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
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

    private void createBuilding(HttpServletRequest request, DomainFacade df) {
        String buildingName = request.getParameter("buildingName");
        String StreetAddress = request.getParameter("streetAddress");
        String StreetNumber = request.getParameter("streetNumber");
        int zipcode =  Integer.parseInt(request.getParameter("zipCode"));
        double buildingsize =  Double.parseDouble(request.getParameter("buildingSize"));
        int buildingYear =  Integer.parseInt(request.getParameter("BuildingYear"));
        String useOfBuilding = request.getParameter("useOfBuilding");
        
        
                
        df.createnewBuilding(buildingName,StreetAddress,StreetNumber,zipcode,
                             buildingsize, buildingYear, useOfBuilding);
}
    private void submitReport(HttpServletRequest request, HttpServletResponse response, DomainFacade df) {
        

    }
}

