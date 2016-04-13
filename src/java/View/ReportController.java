/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.Building;
import Domain.BuildingFloor;
import Domain.BuildingRoom;
import Domain.DomainFacade;
import Domain.User;
import static Model.DBconnector.url;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author dennisschmock
 */
@WebServlet(name = "ReportController", urlPatterns = {"/viewreport1"})
public class ReportController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String url = "/viewbuildingadmin.jsp";
        String action = "";

        action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        System.out.println(request.getMethod());

        DomainFacade df = (DomainFacade) request.getSession().getAttribute("Controller");

        //get the building and send it to the sessionobj
        System.out.println("test of action: " + action);
        if (action.equalsIgnoreCase("viewbuildingadmin")) {
            System.out.println("test!");
            int buildId = Integer.parseInt(request.getParameter("buildingid"));
            Building b = df.getBuilding(buildId);
            request.getSession().setAttribute("building", b);
            request.setAttribute("showBuilding", true);
        }

        //retrieve a room from the buildingobject and put it in response.
        if (action.equalsIgnoreCase("viewroom")) {
            Building b = (Building) request.getSession().getAttribute("building");
            int roomNumber;
            String viewReportRoomString = request.getParameter("viewroom");
            if (viewReportRoomString != null && b != null) {
                roomNumber = Integer.parseInt(viewReportRoomString);
                BuildingRoom r = b.returnARoom(roomNumber);
                request.getSession().setAttribute("room", r);
                request.getSession().setAttribute("building", b);
                request.setAttribute("showRoom", true);

            }
        }

        if (action.equalsIgnoreCase("addfloor")) {
            request.setAttribute("addFloor", true);

        }

        if (action.equalsIgnoreCase("addfloorsubmit")) {
            addFloors(request, df);

        }
        if (action.equalsIgnoreCase("editbuilding")) {
            request.setAttribute("editBuilding", true);

        }
        if (action.equalsIgnoreCase("showBuilding")) {
            request.setAttribute("showBuilding", true);

        }
        if (action.equalsIgnoreCase("newbuilding")) {
            Building b = createBuilding(request, df);
            b = df.getBuilding(b.getBdgId());
            request.getSession().setAttribute("building", b);
            request.setAttribute("showBuilding", true);

        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    /**
     * The purpose of this method is, to add a new floor to a building, based on
     * the buildings id.
     *
     * @param request
     * @param df
     * @param sessionObj
     */
    private void addFloors(HttpServletRequest request, DomainFacade df) {
        String floorNum = (String) request.getParameter("floornumber");
        String floorSize = (String) request.getParameter("floorsize");
        String totalRooms = (String) request.getParameter("totalrooms");
        Building b = (Building) request.getSession().getAttribute("building");

        if (floorNum != null && b != null) {
            int n = (int) Integer.parseInt(floorNum);
            double s = (double) Double.parseDouble(floorSize);
            int r = (int) Integer.parseInt(totalRooms);
            BuildingFloor bf = new BuildingFloor(n, s, b.getBdgId());
            df.addFloors(bf);
            b = df.getBuilding(b.getBdgId());
            request.getSession().setAttribute("building", b);
        }

    }

    private Building createBuilding(HttpServletRequest request, DomainFacade df) {
        String buildingName = request.getParameter("buildingName");
        String StreetAddress = request.getParameter("streetAddress");
        String StreetNumber = request.getParameter("streetNumber");
        int zipcode = Integer.parseInt(request.getParameter("zipCode"));
        double buildingsize = Double.parseDouble(request.getParameter("buildingSize"));
        int buildingYear = Integer.parseInt(request.getParameter("BuildingYear"));
        String useOfBuilding = request.getParameter("useOfBuilding");
        User userLoggedIn = (User) request.getSession().getAttribute("user");

        int custId = userLoggedIn.getCustomerid();
        if (custId == 0 && request.getParameter("customerId") != null) {
            custId = Integer.parseInt(request.getParameter("customerId"));

        }

        Building b = df.createnewBuilding(buildingName, StreetAddress, StreetNumber, zipcode,
                buildingsize, buildingYear, useOfBuilding, custId);
        int buildingId = b.getBdgId();

        savePictureBuilding(request, df, buildingId);
        return b;

    }

    public void savePictureBuilding(HttpServletRequest request, DomainFacade df, int buildingId) {
        Part filePart = null;                                   //Used in case of fileuploads
        List<Part> fileParts = new ArrayList();
        //filePart = request.getPart("buildingImg");
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                //Checks if the form might(!?) contain a file for upload
                //Extracts the part of the form that is the file
                Collection<Part> parts = request.getParts();

                for (Part part : parts) {
                    //filePart = request.getPart("buildingImg");
                    if (part.getName().equals("buildingImg")) {
                        fileParts.add(part);
                    }
                }
            } catch (IOException | ServletException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!fileParts.isEmpty()) {
            System.out.println("FileParts Size");
            System.out.println(fileParts.size());
            filePart = fileParts.get(0);
            String[] fileDotSplit = filePart.getSubmittedFileName().split("\\."); //Split by dot
            String extension = fileDotSplit[fileDotSplit.length - 1];               //Take last part of filename(the extension)
            System.out.println(filePart.getSubmittedFileName());

            String filename = df.saveBuildingPic(buildingId, extension);        //Upload the image details in db, get a filename back
            uploadFile(filePart, "buildingPic", filename);                          //Upload the file in buildingPicFolder
        }
    }

    /**
     * Uploads a file to the server. Helper method for any fileUpload
     *
     * @param filePart the Part that holds the file
     * @param folder the subfolder it should go into (has to exist beforehand,
     * uses relative path!)
     * @param filename the full name of the file.
     */
    private void uploadFile(Part filePart, String folder, String filename) {
        // The Wrong way of doing things according to several sources (relative path)
        // Deliberate in this case for the purpose of being able to implement across multiple systems
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator;

        File uploads = new File(uploadFolder);
        uploads = new File(uploads.getParentFile().getParent() + File.separator + "web" + File.separator + folder);
        File file = new File(uploads, filename);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(FrontControl.class.getName()).log(Level.SEVERE, null, ex);
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

}
