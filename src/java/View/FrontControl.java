/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.DomainFacade;
import Domain.Building;
import Domain.Customer;
import Domain.Report;
import Domain.ReportRoom;
import Domain.ReportRoomDamage;
import Domain.ReportRoomExterior;
import Domain.ReportRoomInterior;
import Domain.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
@WebServlet(name = "FrontControl", urlPatterns = {"/frontpage", "/Style/frontpage", "/login"})
@MultipartConfig
public class FrontControl extends HttpServlet {

    private final CreateUserHelper CUH = new CreateUserHelper();
    private boolean testing = true;

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
        Part filePart = null;
        if (ServletFileUpload.isMultipartContent(request)){
        filePart = request.getPart("buildingImg");
        }
    
        request.setCharacterEncoding("UTF-8");
        HttpSession sessionObj = request.getSession(); //Get the session
        ReportHelper rh = new ReportHelper();
        NewReportHelper nrh = new NewReportHelper();
        
        

        DomainFacade df = (DomainFacade) sessionObj.getAttribute("Controller"); //Get the DomainFacede
        //If it is a new session, create a new DomainFacade Object and put it in the session.
        sessionObj.setAttribute("testing", testing);
        if (df == null) {
            df = DomainFacade.getInstance();
            sessionObj.setAttribute("Controller", df);
        }

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        //Set base url
        String url = "/index.jsp";
        String page = request.getParameter("page");
        System.out.println(page);
        
        if (page=="viewReportTest"){
            
        }

        if (page == null) {
            page = "/index.jsp";
        }
        if (page.equalsIgnoreCase("report")) {
            url = "/report.jsp";
            request = rh.process(request, response, df);
            sessionObj.setAttribute("reports", df.getReport(4));
        }
        if (page.equalsIgnoreCase("newreport")) {
            url = "/reportJSPs/choosebuilding.jsp";
            sessionObj.setAttribute("customerSelcted", false);
            chooseCustomer(sessionObj, df );
        }
        
        if (page.equalsIgnoreCase("report_cus_choosen")) {
            url = "/reportJSPs/choosebuilding.jsp";
            loadCustomersBuildings(request,sessionObj, df );
        }
        
        if (page.equalsIgnoreCase("report_start")) {
            url = "/reportJSPs/report_start.jsp";
            createReport(request, sessionObj, df);
        }

        if (page.equalsIgnoreCase("newReportSubmit")) {
            nrh.submitReport(request, response, df);
            sessionObj.setAttribute("reports", df.getListOfReports(1));
            response.sendRedirect("viewreport.jsp");
            return;
        }
        if (page.equalsIgnoreCase("listreports")) {
            sessionObj.setAttribute("reports", df.getListOfReports(1));
            response.sendRedirect("viewreport.jsp");
            return;
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
            findBuildingToBeEdit(request, sessionObj);
            response.sendRedirect("editBuilding.jsp");
            return;
        }

        /**
         * sending a rediret is better, because a forward will add to the
         * database twice
         */
        if (page.equalsIgnoreCase("newbuilding")) {
            Building b=createBuilding(request, df, sessionObj);
            
            if (filePart!=null){
                String[] fileDotSplit = filePart.getSubmittedFileName().split("\\.");
                String extension = fileDotSplit[fileDotSplit.length-1];
                System.out.println(filePart.getSubmittedFileName());
                System.out.println(extension);
                String filename = df.saveBuildingPic(b.getBdgId(), extension);
                b.setBuilding_pic(filename);
                uploadFile(filePart,"buildingPic",filename);
            } 
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        }
        if (page.equalsIgnoreCase("vieweditedbuilding")) {
            Building b =updateBuilding(request, df, sessionObj);
            if (filePart!=null){
                String[] fileDotSplit = filePart.getSubmittedFileName().split("\\.");
                String extension = fileDotSplit[fileDotSplit.length-1];
                System.out.println(filePart.getSubmittedFileName());
                System.out.println(extension);
                String filename = df.saveBuildingPic(b.getBdgId(), extension);
                b.setBuilding_pic(filename);
                uploadFile(filePart,"buildingPic",filename);
            }
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        }
        if (page.equalsIgnoreCase("submitcustomer")) {
            createNewCustomer(request, df, sessionObj);
            response.sendRedirect("customersubmitted.jsp");
            return;
        }

        if (page.equalsIgnoreCase("createuser")) {
            createUser(request, df, sessionObj);
            response.sendRedirect("login");
            return;
        }

        if (page.equalsIgnoreCase("login")) {
            url = "/login.jsp";

        }
        if (page.equalsIgnoreCase("loguserin")) {
            if (request.getParameter("empOrCus").equals("emp")) {
                emplogin(df, request, response);
            } else {
                login(df, request, response);
            }
            url = "/login.jsp";
        }
        if (page.equalsIgnoreCase("logout")) {
            request.setAttribute("user", null);
            request.setAttribute("loggedin", false);
            request.getSession().invalidate();
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
    private Building createBuilding(HttpServletRequest request, DomainFacade df, HttpSession session) {
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
        return b;
    }

    private void createNewCustomer(HttpServletRequest request, DomainFacade df, HttpSession session) {
        CUH.createNewCustomer(df, session, request);

    }

    private void submitReport(HttpServletRequest request, HttpServletResponse response, DomainFacade df) {

    }

    /**
     * Loads a list of buildings from database for the customer_id Attribute
     *
     * @param request
     * @param df
     * @param sessionObj this method assumes that the Attribute customer_id is
     * not null. Either this Attribute should be filled when an customer user
     * logs on, or before an admin gets to the site. because it loads that
     */
    private void findListOfBuilding(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) {
//        int customerID = (Integer) sessionObj.getAttribute("customer_id");

        /**
         * This is just for testing. I have set the customerID by hardcode to 1
         */
        int customerID = 1;
        List<Building> buildingList = df.getListOfBuildings(customerID);
        sessionObj.setAttribute("listOfBuildings", buildingList);
    }

    /**
     * Finds the building to be displayed in the edit JSP Site
     *
     * @param request In this object it is looking for a parameter called
     * buildingidEdit that should contain the id of the building to be display
     * @param sessionObj The session object holds the list of the buildings for
     * that customer.
     */
    private void findBuildingToBeEdit(HttpServletRequest request, HttpSession sessionObj) {
        List<Building> listofbuildings = (List<Building>) sessionObj.getAttribute("listOfBuildings");
        int buildingID = Integer.parseInt(request.getParameter("buildingidEdit"));

        for (Building building : listofbuildings) {
            if (building.getBdgId() == buildingID) {
                sessionObj.setAttribute("buildingToBeEdited", building);
            }
        }
    }

    /**
     * Updates the existing object of the building with the fields from the form
     * from the jsp site
     *
     * @param request holds the parameters (input fields)
     * @param df Db facade connection
     * @param sessionObj Session object holds the buildingToBeEdited object,
     * that that we have to change based on the input fields
     */
    private Building updateBuilding(HttpServletRequest request, DomainFacade df, HttpSession session) {

        System.out.println(request.getCharacterEncoding());

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
        return buildingToBeEdited;
    }

    /**
     * Method for logging in.
     *
     * @param df
     * @param request
     * @param response
     */
    public void login(DomainFacade df, HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getParameter("username");
        String pwd = (String) request.getParameter("pwd");

        if (df.logUserIn(username, pwd)) {
            request.getSession().setAttribute("loggedin", true);
            request.getSession().setAttribute("userrole", "user");
            User user = df.loadUser(username);
            request.getSession().setAttribute("user", user);
        } else {
            request.getSession().setAttribute("loggedin", false);
        }
    }

    private void createUser(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) {
    }


    /**
     * Method for logging an user in. Question: The cus login set a session
     * parameter that is called "user" This method could just use that aswell,
     * to store the object Or have a different parameter. Also we need to find
     * out if the loggedin should be an int. 0 = not logged in, 1= cus_loggedin,
     * 2= emp_loggedin
     *
     * @param df
     * @param request
     * @param response
     */
    private void emplogin(DomainFacade df, HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getParameter("username");
        String pwd = (String) request.getParameter("pwd");
        
        if(df.logEmpUserIn(username, pwd)) { // not implemented!
            request.getSession().setAttribute("loggedin", true);
            
            User user = df.loadEmpUser(username); // not implemented!
            request.getSession().setAttribute("user", user);
            
        } else {
            request.getSession().setAttribute("loggedin", false);
        }
            
    }

    /**
     * Method that sets up, for the emp whitch building he has to create
     * an report for. Needs to load all the customers.
     * @param request
     * @param sessionObj
     * @param df
     */
    private void chooseCustomer( HttpSession sessionObj, DomainFacade df) {
        List<Customer> allCustomers = df.loadAllCustomers();
        sessionObj.setAttribute("allCustomers", allCustomers);
    }

    /**
     * Loads all the customers buildings, based on whitch user 
     * the empoleyee choose, and sets that in the session obj.
     * @param sessionObj
     * @param df
     */
    public void loadCustomersBuildings(HttpServletRequest request,HttpSession sessionObj, DomainFacade df) {
        sessionObj.setAttribute("customerSelcted", true);
        int cusid = Integer.parseInt(request.getParameter("owners"));
        List<Building> listOfBuildings = df.getListOfBuildings(cusid);
        sessionObj.setAttribute("customersBuildings", listOfBuildings);
        
    }

    /**
     * Creates the Report based on only the building object.
     * Method should be called right when the user has chosen which building
     * to create a report for. At this point, the report object does not
     * contain any details, but only infomation regarding to building, and the
     * Employee that creates it.
     * 
     * @param request
     * @param sessionObj
     * @param df
     */
    public void createReport(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) {
        int buildingID = Integer.parseInt(request.getParameter("buildings"));
        User polygonUser = (User) sessionObj.getAttribute("user");
        String polygonUserID = polygonUser.getUserName();
        
        Report report = new Report(buildingID, polygonUserID);
        report = df.saveReport(report);
        sessionObj.setAttribute("reportToBeCreated", report);
        
    }
    private void uploadFile(Part filePart, String folder, String filename) {
        //The Wrong way of doing things (relative path)
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + folder;
        String[] fileDotSplit = filePart.getName().split("\\.");
        String extension = fileDotSplit[fileDotSplit.length-1];
        
        
        System.out.println("UploadFile");
        File uploads = new File(uploadFolder);
        System.out.println(uploads.getAbsolutePath());
        File file = new File(uploads, filename);

try (InputStream input = filePart.getInputStream()) {
    Files.copy(input, file.toPath());
}       catch (IOException ex) {
            Logger.getLogger(FrontControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    }


