/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.DomainFacade;
import Domain.Building;
import Domain.Customer;
import Domain.Order;
import Domain.Report;
import Domain.Exceptions.PolygonException;
import java.io.IOException;
import java.util.Collection;
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

    private final CreateUserHelper cuh = new CreateUserHelper();
    private final NewFileUpload nfu = new NewFileUpload();
    private final BuildingHelper bh = new BuildingHelper(nfu,cuh);
    private final ReportHelper rh = new ReportHelper(nfu);
    private final OrderHelper oh = new OrderHelper(this, bh, cuh);
    
    private boolean testing = false;
    //store objects since get parameter values resets
    //Order order;
   
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
                cuh.chooseCustomer(sessionObj, df);
        }
        //For choosing the customer //TODO split redirect and action
        if (page.equalsIgnoreCase("report_cus_choosen")) {
            url = "/reportJSPs/choosebuilding.jsp";
                bh.loadCustomersBuildings(request, sessionObj, df);
        }
        //When building has been chosen, it sets up the report object
        if (page.equalsIgnoreCase("report_start")) {
            url = "/reportJSPs/report_start.jsp";
            rh.createReport(request, sessionObj, df);
        }

        //For choosing room when setting up report, after exterior has been added
        if (page.equalsIgnoreCase("ChooseRoom")) {
            url = "/reportJSPs/chooseroom.jsp";
            rh.saveReportExterior(request, sessionObj,parts, this);
        }

        //For inspecting the chosen room.
        if (page.equalsIgnoreCase("inspectRoom")) {
            url = "/reportJSPs/reportaddaroom.jsp";
            rh.setUpForRoomInspection(request, sessionObj, df, parts);
        }

        //For submitting what is written about the room
        if (page.equalsIgnoreCase("submittedRoom")) {
            url = "/reportJSPs/chooseroom.jsp";
            rh.createReportRoomElements(request,sessionObj, parts, this);
        }

        //Saving finished report and redirection to report view. 
        if (page.equalsIgnoreCase("saveFinishedReport")) {
            url = "/viewreport.jsp";
           rh.finishReportObject(request,sessionObj);
           int reportId = rh.saveFinishedReport(sessionObj,df);
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
            bh.createNewRoom(request, sessionObj, df);
            rh.setUpForRoomInspection(request, sessionObj, df, parts );
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
            bh.findListOfBuilding(request, df, sessionObj);
            url = "/viewlistofbuildings.jsp";
        }

        //Edit a building
        if (page.equalsIgnoreCase("editBuilding")) {
            bh.findBuildingToBeEdit(request, sessionObj, df);
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

            Building b=bh.createBuilding(request, df, sessionObj,parts, this);
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        }

        //TODO: seperate action and redirect
        if (page.equalsIgnoreCase("vieweditedbuilding")) {
            Building b =bh.updateBuilding(request, df, sessionObj,parts, this);
            response.sendRedirect("viewnewbuilding.jsp");
            return;
        }

        //TODO: seperate action and redirect
        if (page.equalsIgnoreCase("submitcustomer")) {
                cuh.createNewCustomer(request, df, sessionObj, this);
            response.sendRedirect("customersubmitted.jsp");
            return;
        }
        
        if (page.equalsIgnoreCase("addfloorsubmit")) {
            bh.addFloors(request, df, sessionObj, this);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("selBdg")) {
            bh.selectBuilding(request, df, sessionObj, this);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("addfloor")) {
            sessionObj.setAttribute("customerSelcted", false);
                cuh.chooseCustomer(sessionObj, df);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("selCust")) {
                bh.loadCustomersBuildings(request, sessionObj, df);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("loadFloors")) {
            bh.loadFloors(request, sessionObj, df, this);
            response.sendRedirect("addfloor.jsp");
            return;
        }

        if (page.equalsIgnoreCase("selFlr")) {
            bh.selectFloor(request, sessionObj, df, this);
            response.sendRedirect("addroom.jsp");
            return;
        }

        if (page.equalsIgnoreCase("loadRooms")) {
            bh.loadRooms(request, sessionObj, df, this);
            response.sendRedirect("addroom.jsp");
            return;
        }

        if (page.equalsIgnoreCase("addroomsubmit")) {
            bh.addRoom(request, sessionObj, df, this);
            response.sendRedirect("addroom.jsp");
            return;
        }

        //loading order request page
        if(page.equalsIgnoreCase("orderRequest")){
            bh.loadBuildingsAfterLogIn(sessionObj, df, this);
            response.sendRedirect("orderRequest.jsp");
            return;
        }

        //selecting a building for order request
        if (page.equalsIgnoreCase("selBdgReq")) {
            bh.selectBuilding(request, df, sessionObj, this);
            response.sendRedirect("orderRequest.jsp");
            return;
        }

        //create an order request
        if(page.equalsIgnoreCase("orderRequestSubmit")){
            oh.saveOrder(request, sessionObj, df, this);
            response.sendRedirect("ordersuccess.jsp");
            return; 
        }

        //displays the order history and order progress
        if(page.equalsIgnoreCase("orderhistory")){
            oh.loadCustomerOrders(sessionObj, df, this);
            response.sendRedirect("orderhistory.jsp");
            return;
        }
        
        //displays the order list and order progress
        if(page.equalsIgnoreCase("orderslist")){
            oh.loadAllOrders(sessionObj, df);
            response.sendRedirect("orderslist.jsp");
            return;
        }
        
        //displays the order details
        if (page.equalsIgnoreCase("vieworder")) {
            int orderNumber = Integer.parseInt(request.getParameter("ordernumber"));
            sessionObj.setAttribute("orderNumber",orderNumber);
            sessionObj.setAttribute("selectedOrder", df.getOrder(orderNumber));
            response.sendRedirect("vieworder.jsp");
            return;
        }
        
        //updates the order progress
        if(page.equalsIgnoreCase("updateStat")){
            int newStat = Integer.parseInt(request.getParameter("orderstatus"));
            Order o = (Order) sessionObj.getAttribute("selectedOrder");
            df.updateStatus(o.getOrderNumber(),newStat);
            oh.loadAllOrders(sessionObj, df);
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
                    cuh.emplogin(df, request, response);
            } else {
                    cuh.login(df, request, response);
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
            rh.printReport(sessionObj, df, response, this);
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
