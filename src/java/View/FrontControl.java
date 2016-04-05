/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.DomainFacade;
import Domain.Building;
import Domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "FrontControl", urlPatterns = {"/frontpage", "/Style/frontpage","/login"})
public class FrontControl extends HttpServlet {

    private final CreateUserHelper CUH = new CreateUserHelper();

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
        System.out.println(page);

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
        if (page.equalsIgnoreCase("addcustomer")) {
            url = "/addcustomer.jsp";
        }
         if (page.equalsIgnoreCase("viewlistofbuildings")) {
            findListOfBuilding(request, df, sessionObj); 
            url = "/viewlistofbuildings.jsp";
        }
         if (page.equalsIgnoreCase("editBuilding")) {
            findBuildingToBeEdit(request,sessionObj);
            response.sendRedirect("editBuilding.jsp");
            return;
         }
         
         /**
         * sending a rediret is better, because a forward will add to the
         * database twice
         */
        if (page.equalsIgnoreCase("newbuilding")) {
            createBuilding(request, df, sessionObj);
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        } 
        if (page.equalsIgnoreCase("vieweditedbuilding")) {
            updateBuilding(request, df, sessionObj);
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        }
        if (page.equalsIgnoreCase("submitcustomer")) {
            createNewCustomer(request, df, sessionObj);
            response.sendRedirect("customersubmitted.jsp");
            return;
        }
        
        if (page.equalsIgnoreCase("createuser")){
            createUser(request,df,sessionObj);
            response.sendRedirect("login");
            return;
        }

        if (page.equalsIgnoreCase("login")) {
            url = "/login.jsp";

        }
        if (page.equalsIgnoreCase("loguserin")) {
            login(df, request, response);
            url = "/login.jsp";
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

    /**
     * Takes all the fields from the HTML form, and sends that on to the domain
     * Facade. Then it stores the created building object in the session to be
     * displayed.
     */
    private void createBuilding(HttpServletRequest request, DomainFacade df, HttpSession session) {
        String buildingName = request.getParameter("buildingName");
        String StreetAddress = request.getParameter("streetAddress");
        String StreetNumber = request.getParameter("streetNumber");
        int zipcode = Integer.parseInt(request.getParameter("zipCode"));
        double buildingsize = Double.parseDouble(request.getParameter("buildingSize"));
        int buildingYear = Integer.parseInt(request.getParameter("BuildingYear"));
        String useOfBuilding = request.getParameter("useOfBuilding");

        Building b = df.createnewBuilding(buildingName, StreetAddress, StreetNumber, zipcode,
                buildingsize, buildingYear, useOfBuilding);
        session.setAttribute("newbuilding", b);
    }

    private void createNewCustomer(HttpServletRequest request, DomainFacade df, HttpSession session) {
        CUH.createNewCustomer(df, session, request);

    }

    private void submitReport(HttpServletRequest request, HttpServletResponse response, DomainFacade df) {

    }

    /**
     * Loads a list of buildings from database for the customer_id Attribute
     * @param request
     * @param df
     * @param sessionObj this method assumes that the Attribute customer_id
     * is not null. Either this Attribute should be filled when an customer user
     * logs on, or before an admin gets to the site.
     * because it loads that
     */
    private void findListOfBuilding(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) {
//        int customerID = (Integer) sessionObj.getAttribute("customer_id");
        
        /**
         * This is just for testing. I have set the customerID by hardcode to 1
         */
        int customerID = 1;
        List<Building> buildingList =   df.getListOfBuildings(customerID);
        sessionObj.setAttribute("listOfBuildings", buildingList);
    }

    /**
     * Finds the building to be displayed in the edit JSP Site
     * @param request In this object it is looking for a parameter called buildingidEdit
     * that should contain the id of the building to be display
     * @param sessionObj The session object holds the list of the buildings for that
     * customer. 
     */
    private void findBuildingToBeEdit(HttpServletRequest request, HttpSession sessionObj) {
        List<Building> listofbuildings = (List<Building>) sessionObj.getAttribute("listOfBuildings");
        int buildingID = Integer.parseInt(request.getParameter("buildingidEdit"));
        
        for (Building building : listofbuildings) {
            if(building.getBdgId() == buildingID){
                sessionObj.setAttribute("buildingToBeEdited", building);
            }
        }
    }

    /**
     * Updates the existing object of the building with the fields from 
     * the form from the jsp site
     * @param request holds the parameters (input fields)
     * @param df Db facade connection
     * @param sessionObj Session object holds the buildingToBeEdited object, that
     * that we have to change based on the input fields
     */
    private void updateBuilding(HttpServletRequest request, DomainFacade df, HttpSession session) {
        
        Building buildingToBeEdited = (Building) session.getAttribute("buildingToBeEdited");
        buildingToBeEdited.setBuildingName(request.getParameter("buildingName"));
        buildingToBeEdited.setStreetAddress(request.getParameter("streetAddress"));
        buildingToBeEdited.setStreetNumber(request.getParameter("streetNumber"));
        buildingToBeEdited.setZipCode(Integer.parseInt(request.getParameter("zipCode")));
        buildingToBeEdited.setBuildingSize(Double.parseDouble(request.getParameter("buildingSize")));
        buildingToBeEdited.setBuildingYear(Integer.parseInt(request.getParameter("BuildingYear")));
        buildingToBeEdited.setUseOfBuilding(request.getParameter("useOfBuilding"));
                
        df.Updatebuilding(buildingToBeEdited);
        session.setAttribute("newbuilding", buildingToBeEdited);
    }
    
 
    public void login(DomainFacade df, HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getParameter("username");
        String pwd = (String) request.getParameter("pwd");

        if (df.logUserIn(username, pwd)) {
            request.getSession().setAttribute("loggedin", true);
            User user = df.loadUser(username);
            request.getSession().setAttribute("user", user);
        } else {
            request.getSession().setAttribute("loggedin", false);
        }
    }

    private void createUser(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) {
    }
    
    
    
}
