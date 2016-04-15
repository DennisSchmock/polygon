/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.Building;
import Domain.DomainFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
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
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Dennis
 */
@WebServlet(name = "upload", urlPatterns = {"/upload"})
@MultipartConfig
public class upload extends HttpServlet {

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
        String url = "";

        String page = request.getParameter("page");

        String command = request.getParameter("command");
        if (command == null) {
            command = "";
        }
        if (page == null) {
            page = "";
        }
        System.out.println(command);

        if (page.equalsIgnoreCase(page)) {
            url = "/addbuildingalternateupload.jsp";
        }

        if (command.equalsIgnoreCase("addpicture")) {
            List<Part> fileParts = prepareForUpload(request);

            String filename = savePictureBuilding(request, fileParts);
            Building b = (Building) request.getSession().getAttribute("building");
            b.setBuilding_pic(filename);
        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    private List<Part> prepareForUpload(HttpServletRequest request) {
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
                    System.out.println("part.getName()");
                    System.out.println(part.getName());
                }
            } catch (IOException | ServletException ex) {
                System.out.println("Failed in prepareForUpload " + ex);
                Logger.getLogger(upload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fileParts;
    }

    public String savePictureBuilding(HttpServletRequest request, List<Part> fileParts) {
        Part filePart = null;
        if (!fileParts.isEmpty()) {
            System.out.println("FileParts Size");
            System.out.println(fileParts.size());
            filePart = fileParts.get(0);
            String[] fileDotSplit = filePart.getSubmittedFileName().split("\\."); //Split by dot
            String extension = fileDotSplit[fileDotSplit.length - 1];               //Take last part of filename(the extension)
            System.out.println(filePart.getSubmittedFileName());

            String filename = nextSessionId(); //Make random filename and upload to folder.
            filename = filename + "." + extension;  //+ extension
            uploadFile(filePart, "buildingPic", filename);   
            return filename; //Upload the file in buildingPicFolder
        }
        return "";
        
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

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
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
