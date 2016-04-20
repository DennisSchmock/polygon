/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.DomainFacade;
import Domain.Building;
import Domain.BuildingFloor;
import Domain.BuildingRoom;
import Domain.Customer;
import Domain.Exceptions.PolygonException;
import Domain.Order;
import Domain.Report;
import Domain.ReportRoom;
import Domain.ReportRoomDamage;
import Domain.ReportExterior;
import Domain.ReportPic;
import Domain.ReportRoomInterior;
import Domain.ReportRoomMoist;
import Domain.ReportRoomRecommendation;
import Domain.User;
import Domain.Exceptions.PolygonException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
 * The front controller for all the JSP Sites. All the JSP sites returns through
 * actions to this servlet and then the sevlet passes on information to the
 * controller class, and later redirects the user. An instance of the controller
 * class is kept in the session object so that it does not have to create an new
 * instance every time the JSP returns here.
 *
 * @author dennisschmock
 */
@WebServlet(name = "FrontControl", urlPatterns = {"/frontpage", "/Style/frontpage", "/login", "/viewreport"})
@MultipartConfig
public class FrontControl extends HttpServlet {

    private final CreateUserHelper CUH = new CreateUserHelper();
    private final NewFileUpload nfu = new NewFileUpload();
    private final PrinterPDF printer = new PrinterPDF();
    private boolean testing = false;
    //store objects since get parameter values resets
    Customer c;
    Building bdg;
    BuildingFloor bf;
    BuildingRoom br;
    Order o;

    @EJB
    private MailSenderBean mailSender;

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
        request.setCharacterEncoding("UTF-8");                  //Characterencoding for special characters

        //This part of the code, checks if there might be files for upload, and seperates them, if that is the case
        Collection<Part> parts=null;
        if (ServletFileUpload.isMultipartContent(request)){                                           
            parts = request.getParts();            //Extracts the part of the form that is the file
        }

        HttpSession sessionObj = request.getSession(); //Get the session

        DomainFacade df = (DomainFacade) sessionObj.getAttribute("Controller");     //Get the DomainFacede
        //If it is a new session, create a new DomainFacade Object and put it in the session.
        sessionObj.setAttribute("testing", testing);
        if (df == null) {
            df = DomainFacade.getInstance();
            sessionObj.setAttribute("Controller", df);
        }

        //Set base url
        String url = "/index.jsp";

        String page = request.getParameter("page");
        if (testing) System.out.println("Redirect parameter (page) set to:");
        if (testing) System.out.println(page);
        try {

       

        if (page == null) {
            page = "/index.jsp";
        }
        //For creating a new report
        if (page.equalsIgnoreCase("newreport")) {
            url = "/reportJSPs/choosebuilding.jsp";
            sessionObj.setAttribute("customerSelcted", false);
            chooseCustomer(sessionObj, df);
        }
        //For choosing the customer //TODO split redirect and action
        if (page.equalsIgnoreCase("report_cus_choosen")) {
            url = "/reportJSPs/choosebuilding.jsp";
            loadCustomersBuildings(request, sessionObj, df);
        }
        //When building has been chosen, it sets up the report object
        if (page.equalsIgnoreCase("report_start")) {
            url = "/reportJSPs/report_start.jsp";
            createReport(request, sessionObj, df);
        }

        //For choosing room when setting up report, after exterior has been added
        if (page.equalsIgnoreCase("ChooseRoom")) {
            url = "/reportJSPs/chooseroom.jsp";
            saveReportExterior(request, sessionObj,parts);
        }

        //For inspecting the chosen room.
        if (page.equalsIgnoreCase("inspectRoom")) {
            url = "/reportJSPs/reportaddaroom.jsp";
            setUpForRoomInspection(request, sessionObj, df, parts);
        }

        //For submitting what is written about the room
        if (page.equalsIgnoreCase("submittedRoom")) {
            url = "/reportJSPs/chooseroom.jsp";
            createReportRoomElements(request,sessionObj, parts);
        }

        //Saving finished report and redirection to report view. 
        if (page.equalsIgnoreCase("saveFinishedReport")) {
            url = "/viewreport.jsp";
           finishReportObject(request,sessionObj);
           int reportId = saveFinishedReport(sessionObj,df);
            
                request.getSession().setAttribute("report", df.getReport(reportId));
            
        }

        
        if (page.equalsIgnoreCase("toFinishReport")) {
            url = "/reportJSPs/finishreport.jsp";
        }
        if (page.equalsIgnoreCase("backToChooseRoom")) {
            url = "/reportJSPs/chooseroom.jsp";
        }

        //For inspecting a room you just added to the building
        if (page.equalsIgnoreCase("inspectRoomjustCreated")) {
            url = "/reportJSPs/reportaddaroom.jsp";
            createNewRoom(request, sessionObj, df);
            setUpForRoomInspection(request, sessionObj, df, parts );
        }

        //List all reports for all customers
        if (page.equalsIgnoreCase("listreports")) {
            sessionObj.setAttribute("reports", df.getListOfReports(1));
            response.sendRedirect("viewreports.jsp");
            return;
        }
        if (page.equalsIgnoreCase("addbuilding")) {
            url = "/addbuilding.jsp";
        }
        if (page.equalsIgnoreCase("addcustomer")) {
            url = "/addcustomer.jsp";
        }

        //Viewing the list of all the 
        if (page.equalsIgnoreCase("viewlistofbuildings")) {
            findListOfBuilding(request, df, sessionObj);
            url = "/viewlistofbuildings.jsp";
        }

        //Edit a building
        if (page.equalsIgnoreCase("editBuilding")) {
            findBuildingToBeEdit(request, sessionObj, df);
            response.sendRedirect("editBuilding.jsp");
            return;
        }

        if (page.equalsIgnoreCase("viewreport")) {
            int reportId = Integer.parseInt(request.getParameter("reportid"));
            Report report = df.getReport(reportId);

            sessionObj.setAttribute("report", report);
            response.sendRedirect("viewreport.jsp");
            return;
        }

        if (page.equalsIgnoreCase("viewcustomers")) {
            List<Customer> customers = df.loadAllCustomers();
            sessionObj.setAttribute("customers", customers);
            response.sendRedirect("viewcustomers.jsp");
            return;

        }

        if (page.equalsIgnoreCase("viewcustomer")) {
            int custId = Integer.parseInt(request.getParameter("customerid"));
            sessionObj.setAttribute("customer_id",custId);
            List<Building> buildings = df.getListOfBuildings(custId);
            List<Customer> customers = df.loadAllCustomers();
            for (Customer customer : customers) {
                if (customer.getCustomerId()==custId) sessionObj.setAttribute("customer", customer);
                }
            sessionObj.setAttribute("buildings", buildings);
            response.sendRedirect("viewcustomer.jsp");
            return;

        }

        //This gets a Dash for a building
        if (page.equalsIgnoreCase("viewbuildingadmin")) {
            int buildId = Integer.parseInt(request.getParameter("buildingid"));
            Building b=df.getBuilding(buildId);
            sessionObj.setAttribute("building", b);
            response.sendRedirect("viewbuildingadmin.jsp");
            return;

        }

        /**
         * sending a rediret is better, because a forward will add to the
         * database twice
         */
        
        //TODO seperate redirect and action
        if (page.equalsIgnoreCase("newbuilding")) {

            Building b=createBuilding(request, df, sessionObj,parts);
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        }

        //TODO: seperate action and redirect
        if (page.equalsIgnoreCase("vieweditedbuilding")) {
            Building b =updateBuilding(request, df, sessionObj,parts);
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        }

        //TODO: seperate action and redirect
        if (page.equalsIgnoreCase("submitcustomer")) {
            createNewCustomer(request, df, sessionObj);
            response.sendRedirect("customersubmitted.jsp");
            return;
        }

       

        if (page.equalsIgnoreCase("addfloorsubmit")) {
            addFloors(request, df, sessionObj);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("selBdg")) {
            selectBuilding(request, df, sessionObj);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("addfloor")) {
            sessionObj.setAttribute("customerSelcted", false);
            chooseCustomer(sessionObj, df);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("selCust")) {
            loadCustomersBuildings(request, sessionObj, df);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("loadFloors")) {
            loadFloors(request, sessionObj, df);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("selFlr")) {
            selectFloor(request, sessionObj, df);
            response.sendRedirect("addroom.jsp");
            return;
        }

        if (page.equalsIgnoreCase("loadRooms")) {
            loadRooms(request, sessionObj, df);
            response.sendRedirect("addroom.jsp");
            return;
        }

        if (page.equalsIgnoreCase("addroomsubmit")) {
            addRoom(request, sessionObj, df);
            response.sendRedirect("addroom.jsp");
            return;
        }

        //loading order request page
        if(page.equalsIgnoreCase("orderRequest")){
            loadBuildingsAfterLogIn(sessionObj, df);
            response.sendRedirect("orderRequest.jsp");
            return;
        }

        //selecting a building for order request
        if (page.equalsIgnoreCase("selBdgReq")) {
            selectBuilding(request, df, sessionObj);
            response.sendRedirect("orderRequest.jsp");
            return;
        }

        //create an order request
        if(page.equalsIgnoreCase("orderRequestSubmit")){
            saveOrder(request, sessionObj, df);
            response.sendRedirect("ordersuccess.jsp");
            return; 
        }

        //displays the order history and order progress
        if(page.equalsIgnoreCase("orderhistory")){
            loadCustomerOrders(sessionObj, df);
            response.sendRedirect("orderhistory.jsp");
            return;
        }
        
        //displays the order list and order progress
        if(page.equalsIgnoreCase("orderslist")){
            loadAllOrders(sessionObj, df);
            response.sendRedirect("orderslist.jsp");
            return;
        }
        
        //displays the order details
        if (page.equalsIgnoreCase("vieworder")) {
            int orderNumber = Integer.parseInt(request.getParameter("ordernumber"));
            sessionObj.setAttribute("orderNumber",orderNumber);
            o = df.getOrder(orderNumber);
            sessionObj.setAttribute("selectedOrder", o);
            response.sendRedirect("vieworder.jsp");
            return;
        }
        
        //updates the order progress
        if(page.equalsIgnoreCase("updateStat")){
            int newStat = Integer.parseInt(request.getParameter("orderstatus"));
            df.updateStatus(o.getOrderNumber(),newStat);
            loadAllOrders(sessionObj, df);
            response.sendRedirect("orderslist.jsp");
            return;
        }
        
        if (page.equalsIgnoreCase("continue")) {
            url = "/addroom.jsp";

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
            url="/index.jsp";
        }

        if (page.equalsIgnoreCase("printReport")) {
            printReport(sessionObj, df, response);
            return;
        }
        
        
        } catch (PolygonException ex) {
                Logger.getLogger(FrontControl.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errormessage", ex.getMessage());
                url="/errorpage.jsp";
            }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Method for creating an Report in the PDF Format.
     * Also sends via the response.setHeader() back to the View
     * So that the user can download the just created report
     * @param sessionObj Hold the Report object that is used to retrive whitch report
     * to create
     * @param df Connection to the domain.
     * @param response Responce object to place the file in. 
     * @throws Domain.Exceptions.PolygonException Throws an Polygon if there is any problem
     * with creating an PDF File.
     */
    private void printReport(HttpSession sessionObj, DomainFacade df, HttpServletResponse response) throws PolygonException  {
        Report report = (Report) sessionObj.getAttribute("report");
        Building building = df.getBuilding(report.getBuildingId());
        String realPath = getServletContext().getRealPath("");
        String fileName = "ReportFile" + report.getReportId();
        printer.sendReportToPrint(report, building, realPath, fileName);
        //This tells the browser that we will send a PDF file to the browser
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
        
        File filepath = new File(realPath + File.separator);
        filepath = new File(filepath.getParentFile().getParent() + File.separator + "web" + File.separator + "pdfReports");
        String absFilePath = filepath.getPath();
        
        //This is the absoulte File Path, for the File that has just been created!
        absFilePath += File.separator + fileName + ".pdf";
        File my_file = new File(absFilePath);
        //output Stream and Input Stream are to read from the PDF and Write to the PDF
        // that will be send to the Browser
        OutputStream out;
        try {
            out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        // This makes sure that the writer is finished with creating
        // the PDF Document
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        // this closes the Stream.
        // No need for Response redirct because we have already specified that
        // the response should make a download.
        in.close();
        out.flush();
        } catch (IOException ex) {
            System.out.println("Error in Creating an PDf" + ex);
            throw new PolygonException("Error in Printing the pdf");
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
     * Takes all the fields from the HTML form, and sends that on to the domain
     * Facade. Then it stores the created building object in the session to be
     * displayed.
     */
    private Building createBuilding(HttpServletRequest request, DomainFacade df, HttpSession session, Collection<Part> parts) throws PolygonException {
        String buildingName = request.getParameter("buildingName");
        String StreetAddress = request.getParameter("streetAddress");
        String StreetNumber = request.getParameter("streetNumber");
        int zipcode = Integer.parseInt(request.getParameter("zipCode"));
        double buildingsize = Double.parseDouble(request.getParameter("buildingSize"));
        int buildingYear = Integer.parseInt(request.getParameter("BuildingYear"));
        String useOfBuilding = request.getParameter("useOfBuilding");
        User userLoggedIn = (User)session.getAttribute("user");
        String buildingPic = (nfu.savePictureBuilding(getServletContext().getRealPath(""), parts));

        
        int custId=userLoggedIn.getCustomerid();
        System.out.println("CustId");
        System.out.println(custId);
        if (custId==0 && request.getParameter("customerId")!=null){
            custId=Integer.parseInt(request.getParameter("customerId"));
        }
        System.out.println(custId);


        Building b = df.createnewBuilding(buildingName, StreetAddress, StreetNumber, zipcode,
                buildingsize, buildingYear, useOfBuilding,custId);


        b.setCustId(custId);
        b.setBuilding_pic(buildingPic);
        df.saveBuildingPic(b.getBdgId(), buildingPic);
        session.setAttribute("newbuilding", b);
        return b;
    }

    private void createNewCustomer(HttpServletRequest request, DomainFacade df, HttpSession session) {
        CUH.createNewCustomer(df, session, request);

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
    private void findListOfBuilding(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) throws PolygonException {
//        int customerID = (Integer) sessionObj.getAttribute("customer_id");

        /**
         * This is just for testing. I have set the customerID by hardcode to 1
         */
        int customerID = 1;
        if (sessionObj.getAttribute("customer_id")!=null) customerID=(Integer)(sessionObj.getAttribute("customer_id"));
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
    private void findBuildingToBeEdit(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        if (sessionObj.getAttribute("listOfBuildings")==null)findListOfBuilding(request, df, sessionObj);   // Added for the sake of Admin editing building
        List<Building> listofbuildings = (List<Building>) sessionObj.getAttribute("listOfBuildings");
        int buildingID = Integer.parseInt(request.getParameter("buildingidEdit"));
        System.out.println("Building Id in findBuilding");
        System.out.println(buildingID);

        for (Building building : listofbuildings) {
            if (building.getBdgId() == buildingID) {
                building.setBuilding_pic(df.getLatestBuildingImage(building.getBdgId()));  //Call db to see if there is an Img for the building and add the latest to the object
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
    private Building updateBuilding(HttpServletRequest request, DomainFacade df, HttpSession session, Collection<Part> parts) throws PolygonException {

        System.out.println(request.getCharacterEncoding());

        Building buildingToBeEdited = (Building) session.getAttribute("buildingToBeEdited");  // Had been edited to "building"?! gave crash
        if (buildingToBeEdited==null)buildingToBeEdited = (Building)session.getAttribute("building");
        buildingToBeEdited.setBuildingName(request.getParameter("buildingName"));
        buildingToBeEdited.setStreetAddress(request.getParameter("streetAddress"));
        buildingToBeEdited.setStreetNumber(request.getParameter("streetNumber"));
        buildingToBeEdited.setZipCode(Integer.parseInt(request.getParameter("zipCode")));
        buildingToBeEdited.setBuildingSize(Double.parseDouble(request.getParameter("buildingSize")));
        buildingToBeEdited.setBuildingYear(Integer.parseInt(request.getParameter("BuildingYear")));
        buildingToBeEdited.setUseOfBuilding(request.getParameter("useOfBuilding"));
        //Calls method to upload file and get a string with filename back
        buildingToBeEdited.setBuilding_pic(nfu.savePictureBuilding(getServletContext().getRealPath(""), parts));
        //This call should perhaps be moved to a deeper layer
        df.saveBuildingPic(buildingToBeEdited.getBdgId(), buildingToBeEdited.getBuildingPic());
        System.out.println("BuildingPic");
        System.out.println(buildingToBeEdited.getBuildingPic());
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

        //this is for order request when a customer loggedin
        System.out.println("..." + username+pwd);
        c = df.getCustomerAfterLogIn(username);
        System.out.println("Customer:" + c.getCustomerId());

        if (df.logUserIn(username, pwd)) {
            request.getSession().setAttribute("loggedin", true);
            request.getSession().setAttribute("userrole", "user");
            User user = df.loadUser(username);
            request.getSession().setAttribute("user", user);
        } else {
            request.getSession().setAttribute("loggedin", false);
        }
    }

    public void loadBuildingsAfterLogIn( HttpSession sessionObj,DomainFacade df) throws PolygonException{
        if(c!=null){
            List<Building> listOfBuildings = df.getListOfBuildings(c.getCustomerId());
            sessionObj.setAttribute("customersBuildings", listOfBuildings);
        }
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

        if (df.logEmpUserIn(username, pwd)) { // not implemented!
            request.getSession().setAttribute("loggedin", true);

            User user = df.loadEmpUser(username); // not implemented!
            request.getSession().setAttribute("user", user);

        } else {
            request.getSession().setAttribute("loggedin", false);
        }

    }

    /**
     * Method that sets up, for the emp whitch building he has to create an
     * report for. Needs to load all the customers.
     *
     * @param request
     * @param sessionObj
     * @param df
     */
    private void chooseCustomer(HttpSession sessionObj, DomainFacade df) {
        List<Customer> allCustomers = df.loadAllCustomers();
        sessionObj.setAttribute("allCustomers", allCustomers);
    }

    /**
     * Loads all the customers buildings, based on whitch user the empoleyee
     * choose, and sets that in the session obj.
     *
     * @param sessionObj
     * @param df
     */
    private void loadCustomersBuildings(HttpServletRequest request,HttpSession sessionObj, DomainFacade df) throws PolygonException {
        sessionObj.setAttribute("customerSelcted", true);
        int cusid = Integer.parseInt(request.getParameter("owners"));
        List<Building> listOfBuildings = df.getListOfBuildings(cusid);
        sessionObj.setAttribute("customersBuildings", listOfBuildings);
        c = df.getCustomer(cusid);
        c.setBuildings(listOfBuildings);
        sessionObj.setAttribute("selectedCustomer", c);

    }

    /**
     * Creates the Report based on only the building object. Method should be
     * called right when the user has chosen which building to create a report
     * for. At this point, the report object does not contain any details, but
     * only infomation regarding to building, and the Employee that creates it.
     * Also loads the building object of the building to be created an stores it
     * in the session.
     *
     * @param request
     * @param sessionObj
     * @param df
     */
    private void createReport(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        int buildingID = Integer.parseInt(request.getParameter("buildings"));
        User polygonUser = (User) sessionObj.getAttribute("user");
        String polygonUserID = polygonUser.getUserName();

        Report report = new Report(buildingID, polygonUserID);
        sessionObj.setAttribute("reportToBeCreated", report);
        Building b = df.getBuilding(buildingID);
        sessionObj.setAttribute("reportBuilding", b);

    }

    /**
     * Takes the ellements form the request and saves it, all that is needed for
     * creating the exterior decription in the report. Also saves the new
     * infomation in the report object. OBS: DOES NOT HANDLE
     * PICTURES!!!!!!!!!!!!!!!!!!
     *
     * @param request Holds the fields, the user have inserted.
     * @param sessionObj Holds obejcts like report, and building for report
     */
    public void saveReportExterior(HttpServletRequest request, HttpSession sessionObj, Collection<Part> parts) {
        String remarksOnRoof = request.getParameter("remarksOnRoof");
        String remarksOnWalls = request.getParameter("remarksOnWall");

        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");

        ReportExterior roofEx = new ReportExterior("Roof", remarksOnRoof);
        ReportExterior wallEx = new ReportExterior("Wall", remarksOnWalls);

        ArrayList<ReportPic> extPic = new ArrayList();
        String filepath = nfu.saveExtPicture(getServletContext().getRealPath(""),parts);
        String description = request.getParameter("decriptionOfPicture");
        extPic.add(new ReportPic(filepath,description));

        report.setListOfExtPics(extPic);

        if (report.getListOfRepExt() == null) {
            ArrayList<ReportExterior> listOfExt = new ArrayList<>();
            listOfExt.add(wallEx);
            listOfExt.add(roofEx);
            report.setListOfRepExt(listOfExt);
        } else {
            ArrayList<ReportExterior> listOfExt = report.getListOfRepExt();
            listOfExt.add(wallEx);
            listOfExt.add(roofEx);
            report.setListOfRepExt(listOfExt);
        }

        sessionObj.setAttribute("reportToBeCreated", report);

        
    }

 
    /**
     * Uploads a file to the server. Helper method for any fileUpload
     * @param filePart the Part that holds the file
     * @param folder the subfolder it should go into (has to exist beforehand, uses relative path!)
     * @param filename the full name of the file.
     */
    
    /**
     * This method adds a new floor, then set to BuildingFloor object and session with the list of floors 
     * @param request
     * @param df
     * @param sessionObj
     */
    private void addFloors(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) throws PolygonException {
        String floorNum = (String)request.getParameter("floornumber");
        String floorSize =(String)request.getParameter("floorsize");
        String totalRooms =(String)request.getParameter("totalrooms");
        if (floorNum != null) {
            int n = (int) Integer.parseInt(floorNum);
            double s = (double) Double.parseDouble(floorSize);
            int r = (int) Integer.parseInt(totalRooms);
            bf = new BuildingFloor(n,s,r,bdg.getBdgId());
            df.addFloors(bf);//new building floor will be added
            //for updating the view of floors list added
            ArrayList<BuildingFloor> bfList = df.listOfFloors(bf.getBuildingId());
            bdg.setListOfFloors(bfList);
            sessionObj.setAttribute("floorsList", bfList);

        }

    }

    /**
     * This method load the floors in a certain building and sets to a session
     * @param request
     * @param sessionObj
     * @param df
     */
    private void loadFloors(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        ArrayList<BuildingFloor> bfList = df.listOfFloors(bdg.getBdgId());
        bdg.setListOfFloors(bfList);
        sessionObj.setAttribute("floorsList", bfList);
    }

    /**
     * This method gets the building data and sets to a session
     * @param request
     * @param df
     * @param sessionObj
     */
    private void selectBuilding(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) throws PolygonException {

        int id = Integer.parseInt(request.getParameter("buildings"));
        bdg = df.getBuilding(id);
        sessionObj.setAttribute("selectedBuilding", bdg);
    }

    /**
     * Based on the fields in the request object, this method creates an new
     * building_Room in the database. Also sets the newly created id for the
     * BUILDING ROOM, as a Attribute in the request object.
     * @param request Holds the requied fields to create an new room
     * @param sessionObj Holds the buildingID
     * @param df Connection to the domain level
     */
    private void createNewRoom(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        String roomName = request.getParameter("RoomName");
        int floorid = Integer.parseInt(request.getParameter("Floorselect2"));

        BuildingRoom newRoom = new BuildingRoom(roomName, floorid);
        newRoom = df.addBuildingRoom(newRoom);

        // After we have added a room to the database we need to reload the session att
        // For the reportBuilding.
        Building b = (Building) sessionObj.getAttribute("reportBuilding");
        sessionObj.setAttribute("reportBuilding", df.getBuilding(b.getBdgId()));
        request.setAttribute("RoomSelected", newRoom.getRoomId());

    }

    /**
     * This method can be accesses in two ways.
     * Either the user has selected an already existing room to inspected
     * or the user has just created an new BUILDING room, that the user now wants
     * to inspect.
     * @param request Holds the Fields to Create the Report_ROOM
     * @param sessionObj
     */
    private void setUpForRoomInspection(HttpServletRequest request, HttpSession sessionObj, DomainFacade df, Collection<Part> parts) throws PolygonException {
        int buildingRoomid;
        if(request.getParameter("RoomSelected") != null){

            /*
             This means that the user has selected an already existing room
             to inspected. Therefore it is the parameter that is to be used!
             */
            buildingRoomid = Integer.parseInt(request.getParameter("RoomSelected"));

    }
        else{
            /*
             This means that the user has just created an room
             to inspected. Therefore it is the attribute is used
             */

            buildingRoomid = (int) (request.getAttribute("RoomSelected"));
        }

        Building temp = (Building) sessionObj.getAttribute("reportBuilding"); // finds the building object from session
        BuildingRoom buildingRoom= null;

        // Loops through all the rooms for the building 
        // To find the one the user has selected.
        for (BuildingFloor floor : temp.getListOfFloors()) {
            for (BuildingRoom Room : floor.getListOfRooms() ) {

             if(Room.getRoomId() == buildingRoomid){
                    buildingRoom = Room;
                }
            }

        }

        ReportRoom reportRoom = new ReportRoom(buildingRoom.getRoomName(),buildingRoom.getRoomId());
        BuildingFloor buildingFloor =df.getBuildingFloor(buildingRoom.getFloorid());
        reportRoom.setRoomFloor(buildingFloor.getFloorNumber()+"");

        sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);

    }

    /**
     * This method gets the selected floor and sets to a session
     * @param request
     * @param sessionObj
     * @param df
     */
    private void selectFloor(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        int id = Integer.parseInt(request.getParameter("floors"));
        bf = df.getBuildingFloor(id);
        sessionObj.setAttribute("selectedFloor", bf);
    }

    /**
     * This method loads the list of rooms and sets it to a session
     * @param request
     * @param sessionObj
     * @param df
     */
    private void loadRooms(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        ArrayList<BuildingRoom> roomsList = df.getListOfRooms(bf.getFloorId());
        bf.setListOfRooms(roomsList);
        sessionObj.setAttribute("roomsList", roomsList);
    }

    /**
     * This method adds a new room and updates the session of rooms list
     * @param request
     * @param sessionObj
     * @param df
     */
    private void addRoom(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        String roomName = (String)request.getParameter("roomname");
        if (roomName != null) {
            br = new BuildingRoom(roomName,bf.getFloorId());
            df.addRoom(br);
            ArrayList<BuildingRoom> brList = df.getListOfRooms(bf.getFloorId());
            bf.setListOfRooms(brList);
            sessionObj.setAttribute("roomsList", brList);
            int tr=brList.size();
            //update the number of rooms once added a new one
            df.updateFloor(bf.getFloorId(), tr);
            bf.setTotalRooms(tr);
            sessionObj.setAttribute("selectedFloor", bf);
        }
    }

    /**
     * This method creates the reportRoom elements that is then added to the
     * report_room, that is added to Report. Checks the fields to see if they have
     * been filled or not, and the passes them to other methods that creates the objects
     * @param request Holds all the parameters that the user has put in to
     * the fields in the jsp site.
     * @param sessionObj Session object holds the Report object that needs to
     * be updated.
     */
    private void createReportRoomElements(HttpServletRequest request, HttpSession sessionObj, Collection<Part> parts) {

        //For the interior / Examination:
        if(request.getParameter("Examination").equalsIgnoreCase("Remarks")){
            // This means that the user has check the radio button to yes.
            try{
            if(request.getParameter("Floor") != null || !(request.getParameter("Floor").equals(""))){
                    //This means that Field has been filled by the user:
                createRoomInteriorElement("Floor",request.getParameter("Floor"),sessionObj);
                }
            if(request.getParameter("Window") != null || !(request.getParameter("Window").equals(""))){
                    //This means that Field has been filled by the user:
                createRoomInteriorElement("Window",request.getParameter("Window"),sessionObj);
                }

            if(request.getParameter("Celling") != null || !(request.getParameter("Celling").equals(""))){
                    //This means that Field has been filled by the user:
                createRoomInteriorElement("Celling",request.getParameter("Celling"),sessionObj);
                }

            if(request.getParameter("Other") != null || !(request.getParameter("Other").equals(""))){
                    //This means that Field has been filled by the user:
                createRoomInteriorElement("Other",request.getParameter("Other"),sessionObj);
                }
            }
            catch(Exception e){
                System.out.println("Error in getting the Exsamination field" + e.getMessage());
            }
        }

        // For the Damage:
        if(request.getParameter("damage").equalsIgnoreCase("Damage")){
            // The user has Check the damages Field, We can move the try-catch if this works!
            try{
                String damageTime = request.getParameter("damageTime");
                String damagePlace = request.getParameter("damagePlace");
                String damageWhatHasHappend = request.getParameter("damageHappend");
                String damageRepaired = request.getParameter("damageReparied");
                String damageType = request.getParameter("damageType");
            createRoomDamageElement(damageTime,damagePlace,damageWhatHasHappend,damageRepaired,damageType, sessionObj);
            }
            catch(Exception e){
                System.out.println("Error in getting field from Damages: " + e.getMessage());

            }

            
        }

        //For Moist:
        if(request.getParameter("Moist").equalsIgnoreCase("Moist")){
            // The user has Check the moist Field, We can move the try-catch if this works!

            try{
                String moistScanResult = request.getParameter("moistScanResult");
                String moistScanArea = request.getParameter("moistScanArea");
                createRoomMoistElement(moistScanResult, moistScanArea, sessionObj);

            }
            catch(Exception e){
                System.out.println("Error in getting field from Moist: " + e.getMessage());
            }
        }

        //For Recomendations:
        if(request.getParameter("Recommendation").equalsIgnoreCase("Recommendation")){
            // The user has Check the Recomendation Field, We can move the try-catch if this works!
            try{
                String roomRecomendations = request.getParameter("recomendation");
                createRoomRecomendation(roomRecomendations, sessionObj);
            }
            catch(Exception e){
                System.out.println("Error in getting field from Recomendations");
            }

        }

        // Stuff for adding reportRoomPics
        ArrayList<ReportPic> rrPic = new ArrayList();
        String description = request.getParameter("roompicdescrip");
        if (description==null) description="";
        System.out.println("Size of parts in reportroom");
        if (parts!=null) System.out.println(parts.size());
        rrPic=nfu.addReportRoomPics(getServletContext().getRealPath(""), description, parts);

        // After all of the elemets has been added to the report_Room
        // The report_Room, should be saved in the Report att.
        // And then be removed, since there now needs to be an new object inserted
        
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");
        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");
        reportRoom.setRrPic(rrPic);
        System.out.println(rrPic.size());
        if(report.getListOfRepRoom() == null){
            // means that the report does not contain any rooms yet
            ArrayList<ReportRoom> roomList = new ArrayList();
            roomList.add(reportRoom);
            report.setListOfRepRoom(roomList);
            sessionObj.setAttribute("reportToBeCreated", report);
        }
        else{
            // means that the report allready has Rooms
            ArrayList<ReportRoom> roomList = report.getListOfRepRoom();
            roomList.add(reportRoom);
            report.setListOfRepRoom(roomList);
            sessionObj.setAttribute("reportToBeCreated", report);

        }
        System.out.println(report.toString()); // for testing

    }

    /**
     * This method creates an Report_Room_Interior obejct an stores it in the
     * Report_Room Attribute
     * @param examinedpart A String that specifics what part of the room the remark belongs to
     * @param remark The remark the user has filled in
     * @param sessionObj Session object that holds the Report_Room Attriubte
     */
    private void createRoomInteriorElement(String examinedpart, String remark, HttpSession sessionObj)throws Exception  {
        ReportRoomInterior interiorElement = new ReportRoomInterior(examinedpart, remark);
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");

        if(reportRoom.getListOfInt() == null){
            //Means that there are no Interior Elements in the Report Room
            ArrayList<ReportRoomInterior> temp = new ArrayList<>();
            temp.add(interiorElement);
            reportRoom.setListOfInt(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        }
        else{
            // Means that there is already some Interor Elements in the Report Room.
            ArrayList<ReportRoomInterior> temp =reportRoom.getListOfInt();
            temp.add(interiorElement);
            reportRoom.setListOfInt(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);

        }
    }

    /**
     * This method is for create a Report Room Damage Element.
     * Then it will be stored in the ReportROOM Attribute 
     * @param damageTime
     * @param damagePlace
     * @param damageWhatHasHappend
     * @param damageRepaired
     * @param damageType
     * @param sessionObj
     */
    private void createRoomDamageElement(String damageTime, String damagePlace, String damageWhatHasHappend, String damageRepaired, String damageType, HttpSession sessionObj) throws Exception {
        ReportRoomDamage roomDamage = new ReportRoomDamage(damageTime, damagePlace, damageWhatHasHappend, damageRepaired, damageType);

        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");

       if(reportRoom.getListOfDamages() == null){
            //Means that there are no Damage Elements in the Report Room
            ArrayList<ReportRoomDamage> temp = new ArrayList<>();
            temp.add(roomDamage);
            reportRoom.setListOfDamages(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        }
        else{
            // Means that there is already some Damage Elements in the Report Room.
            ArrayList<ReportRoomDamage> temp =reportRoom.getListOfDamages();
            temp.add(roomDamage);
            reportRoom.setListOfDamages(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);

        }
    }

    /**
     * This method is for create a Report Room MOIST Element.
     * Then it will be stored in the ReportROOM Attribute 
     * @param moistScanResult
     * @param moistScanArea
     * @param sessionObj
     */
    private void createRoomMoistElement(String moistScanResult, String moistScanArea, HttpSession sessionObj) throws Exception {
        ReportRoomMoist roomMoist = new ReportRoomMoist(moistScanResult, moistScanArea);
        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");
        reportRoom.setMoist(roomMoist);
        sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
    }

    /**
     * This method is for create a Report Room Recomendations Element.
     * Then it will be stored in the ReportROOM Attribute 
     * @param roomRecomendations
     * @param sessionObj
     */
    private void createRoomRecomendation(String roomRecomendations, HttpSession sessionObj) throws Exception {
        ReportRoomRecommendation roomRecommendation = new ReportRoomRecommendation(roomRecomendations);

        ReportRoom reportRoom = (ReportRoom) sessionObj.getAttribute("reportRoomToBeCreated");

       if(reportRoom.getListOfRec() == null){
            //Means that there are no Recomendation Elements in the Report Room
            ArrayList<ReportRoomRecommendation> temp = new ArrayList<>();
            temp.add(roomRecommendation);
            reportRoom.setListOfRec(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);
        }
        else{
            // Means that there is already some Recomendation Elements in the Report Room.
            ArrayList<ReportRoomRecommendation> temp =reportRoom.getListOfRec();
            temp.add(roomRecommendation);
            reportRoom.setListOfRec(temp);
            sessionObj.setAttribute("reportRoomToBeCreated", reportRoom);

        }
    }

    /**
     * Saves the inserted Condition Grade and date to the report object.
     * Also saves the CompanyMan responsable for the building.
     * @param request Hold the field with the condition grade.
     * @param sessionObj Hold the report obejct
     */
    private void finishReportObject(HttpServletRequest request, HttpSession sessionObj) {
        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");
        int conditionGrade = Integer.parseInt(request.getParameter("Condition Grade"));
        report.setCategoryConclusion(conditionGrade);
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        report.setDate(date);
        String customerAccountable =  request.getParameter("customerAccountable");
        report.setCustomerAccountable(customerAccountable);

        sessionObj.setAttribute("reportToBeCreated", report);
    }

    /**
     * This method should be called when the report is ready to be saved and finished
     * This method saves the report object, that is in the session object, to the database.
     * @param sessionObj Holds the report object
     * @param df Holds the connection to the domain layer
     */
    private int saveFinishedReport( HttpSession sessionObj, DomainFacade df) throws PolygonException {
        Report report = (Report) sessionObj.getAttribute("reportToBeCreated");
        return df.saveReport(report);
    }

    /**
     * This method will take the values of the attributes for saving a new order
     * @param request
     * @param sessionObj
     * @param df
     */
    public void saveOrder(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) {

        String serviceDesc = (String) request.getParameter("services");
        String otherDesc = (String) request.getParameter("otherservice");
        if(serviceDesc.equals("other")){
            serviceDesc = otherDesc;
        }
        String problemStmt = (String) request.getParameter("problemstatement");
        int orderStat = 1;
        sessionObj.setAttribute("orderStatus", orderStat);
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        o = new Order(date, serviceDesc, problemStmt, orderStat, c.getCustomerId(), bdg.getBdgId());

        df.addNewOrder(o);
        sendOrderEmail();
    }

    /**
     * This method will call the mail sender bean that would be responsible in sending the email 
     * to noreply.polygon and notify them about the order request
     */
    private void sendOrderEmail() {
        mailSender = new MailSenderBean();
        String toEmail = "noreply.polygonproject@gmail.com";
        String subject = "ORDER: " + o.getServiceDescription();
        String message = "REQUEST FOR "+ o.getServiceDescription() +
                "\n\nOrder Number: " + o.getOrderNumber() +
                "\nOrder Date:" + o.getOrderDate() +
                "\nCustomer: " + c.getCompanyName() +
                "\nBuilding: " + bdg.getBuildingName() +
                "\nProblem Description: " + o.getProblemStatement();
        
        String fromEmail = "noreply.polygonproject@gmail.com";
        String username = "noreply.polygonproject";
        String password = "poly123go";

        //Call to  mail sender bean
        mailSender.sendEmail(fromEmail, username, password, toEmail, subject, message);
    }

    /**
     * This will get all the customer's orders
     * @param sessionObj
     * @param df
     */
    private void loadCustomerOrders(HttpSession sessionObj, DomainFacade df) {
        ArrayList<Order> listOfOrders = df.getListOfOrders(c.getCustomerId());
        c.setListOfOrders(listOfOrders);
        sessionObj.setAttribute("listOfOrders", listOfOrders);
        
    }

    /**
     * This will get all the orders of all the customers of polygon and will be sorted by order status
     * @param sessionObj
     * @param df
     */
    private void loadAllOrders(HttpSession sessionObj, DomainFacade df) {
        ArrayList<Order> listOfAllOrders = df.getListOfAllOrders();
        Collections.sort(listOfAllOrders,Order.orderStat);
        sessionObj.setAttribute("listOfOrders", listOfAllOrders);
    }

    
}

