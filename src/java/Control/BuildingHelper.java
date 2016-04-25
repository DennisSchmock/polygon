/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.Building;
import Domain.BuildingFloor;
import Domain.BuildingRoom;
import Domain.Customer;
import Domain.DomainFacade;
import Domain.Exceptions.PolygonException;
import Domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author danie
 */
public class BuildingHelper {
    private NewFileUpload nfu;
    //private Building building;
    //private BuildingRoom buildingroom;
    //private BuildingFloor buildingfloor;
    private CreateUserHelper cuh;
    
    public BuildingHelper(NewFileUpload nfu, CreateUserHelper cuh){
        this.nfu=nfu;
        this.cuh=cuh;
    }

    /**
     * Takes all the fields from the HTML form, and sends that on to the domain
     * Facade. Then it stores the created building object in the session to be
     * displayed.
     */
    Building createBuilding(HttpServletRequest request, DomainFacade df, HttpSession session, Collection<Part> parts, FrontControl frontControl) throws PolygonException {
        String buildingName = request.getParameter("buildingName");
        String StreetAddress = request.getParameter("streetAddress");
        String StreetNumber = request.getParameter("streetNumber");
        int zipcode = Integer.parseInt(request.getParameter("zipCode"));
        double buildingsize = Double.parseDouble(request.getParameter("buildingSize"));
        int buildingYear = Integer.parseInt(request.getParameter("BuildingYear"));
        String useOfBuilding = request.getParameter("useOfBuilding");
        User userLoggedIn = (User) session.getAttribute("user");
        String buildingPic = nfu.savePictureBuilding(frontControl.getServletContext().getRealPath(""), parts);
        int custId = userLoggedIn.getCustomerid();
        System.out.println("CustId");
        System.out.println(custId);
        if (custId == 0 && request.getParameter("customerId") != null) {
            custId = Integer.parseInt(request.getParameter("customerId"));
        }
        System.out.println(custId);
        Building b = df.createnewBuilding(buildingName, StreetAddress, StreetNumber, zipcode, buildingsize, buildingYear, useOfBuilding, custId);
        b.setCustId(custId);
        b.setBuilding_pic(buildingPic);
        df.saveBuildingPic(b.getBdgId(), buildingPic);
        session.setAttribute("newbuilding", b);
        return b;
    }

    /**
     * Based on the fields in the request object, this method creates an new
     * building_Room in the database. Also sets the newly created id for the
     * BUILDING ROOM, as a Attribute in the request object.
     * @param request Holds the requied fields to create an new room
     * @param sessionObj Holds the buildingID
     * @param df Connection to the domain level
     */
    void createNewRoom(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
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
    void addFloors(HttpServletRequest request, DomainFacade df, HttpSession sessionObj, FrontControl frontControl) throws PolygonException {
        String floorNum = (String) request.getParameter("floornumber");
        String floorSize = (String) request.getParameter("floorsize");
        String totalRooms = (String) request.getParameter("totalrooms");
        if (floorNum != null) {
            int n = (int) Integer.parseInt(floorNum);
            double s = (double) Double.parseDouble(floorSize);
            int r = (int) Integer.parseInt(totalRooms);
            Building b = (Building) request.getSession().getAttribute("building");
            BuildingFloor buildingfloor = new BuildingFloor(n, s, r, b.getBdgId());
            df.addFloors(buildingfloor); //new building floor will be added
            //for updating the view of floors list added
            ArrayList<BuildingFloor> bfList = df.listOfFloors(buildingfloor.getBuildingId());
            b.setListOfFloors(bfList);
            request.getSession().setAttribute("building", b);
            request.getSession().setAttribute("buildingfloor", buildingfloor);
            sessionObj.setAttribute("floorsList", bfList);
        }
    }

    /**
     * Finds the building to be displayed in the edit JSP Site
     *
     * @param request In this object it is looking for a parameter called
     * buildingidEdit that should contain the id of the building to be display
     * @param sessionObj The session object holds the list of the buildings for
     * that customer.
     */
    void findBuildingToBeEdit(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        if (sessionObj.getAttribute("listOfBuildings") == null) {
            findListOfBuilding(request, df, sessionObj); // Added for the sake of Admin editing building
        }
        List<Building> listofbuildings = (List<Building>) sessionObj.getAttribute("listOfBuildings");
        int buildingID = Integer.parseInt(request.getParameter("buildingidEdit"));
        System.out.println("Building Id in findBuilding");
        System.out.println(buildingID);
        for (Building building : listofbuildings) {
            if (building.getBdgId() == buildingID) {
                building.setBuilding_pic(df.getLatestBuildingImage(building.getBdgId())); //Call db to see if there is an Img for the building and add the latest to the object
                sessionObj.setAttribute("buildingToBeEdited", building);
            }
        }
    }

    /**
     * This method load the floors in a certain building and sets to a session
     * @param request
     * @param sessionObj
     * @param df
     */
    void loadFloors(HttpServletRequest request, HttpSession sessionObj, DomainFacade df, FrontControl frontControl) throws PolygonException {
        Building b = (Building)request.getSession().getAttribute("building");
        ArrayList<BuildingFloor> bfList = df.listOfFloors(b.getBdgId());
        b.setListOfFloors(bfList);
        sessionObj.setAttribute("floorsList", bfList);
    }

    /**
     * This method loads the list of rooms and sets it to a session
     * @param request
     * @param sessionObj
     * @param df
     */
    void loadRooms(HttpServletRequest request, HttpSession sessionObj, DomainFacade df, FrontControl frontControl) throws PolygonException {
        BuildingFloor buildingfloor = (BuildingFloor) request.getSession().getAttribute("buildingfloor");
        ArrayList<BuildingRoom> roomsList = df.getListOfRooms(buildingfloor.getFloorId());
        buildingfloor.setListOfRooms(roomsList);
        sessionObj.setAttribute("roomsList", roomsList);
        request.getSession().setAttribute("buildingfloor", buildingfloor);
    }

    public void loadBuildingsAfterLogIn(HttpSession sessionObj, DomainFacade df, FrontControl frontControl) throws PolygonException {
        Customer customer = (Customer) sessionObj.getAttribute("customer");
        List<Building> listOfBuildings = df.getListOfBuildings(customer.getCustomerId());
        Collections.sort(listOfBuildings, Building.bdgState);
        sessionObj.setAttribute("customersBuildings", listOfBuildings);
    }

    /**
     * This method gets the building data and sets to a session
     * @param request
     * @param df
     * @param sessionObj
     */
    void selectBuilding(HttpServletRequest request, DomainFacade df, HttpSession sessionObj, FrontControl frontControl) throws PolygonException {
        int id = Integer.parseInt(request.getParameter("buildings"));
        Building building = df.getBuilding(id);
        sessionObj.setAttribute("selectedBuilding", building);
        sessionObj.setAttribute("building", building);
    }

    /**
     * This method adds a new room and updates the session of rooms list
     * @param request
     * @param sessionObj
     * @param df
     */
    void addRoom(HttpServletRequest request, HttpSession sessionObj, DomainFacade df, FrontControl frontControl) throws PolygonException {
        String roomName = (String) request.getParameter("roomname");
        if (roomName != null) {
            BuildingFloor buildingfloor = (BuildingFloor) request.getSession().getAttribute("buildingfloor");
            BuildingRoom buildingroom = new BuildingRoom(roomName, buildingfloor.getFloorId());
            df.addRoom(buildingroom);
            ArrayList<BuildingRoom> brList = df.getListOfRooms(buildingfloor.getFloorId());
            buildingfloor.setListOfRooms(brList);
            sessionObj.setAttribute("roomsList", brList);
            int tr = brList.size();
            //update the number of rooms once added a new one
            df.updateFloor(buildingfloor.getFloorId(), tr);
            buildingfloor.setTotalRooms(tr);
            sessionObj.setAttribute("selectedFloor", buildingfloor);
            sessionObj.setAttribute("buildingfloor", buildingfloor);
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
    Building updateBuilding(HttpServletRequest request, DomainFacade df, HttpSession session, Collection<Part> parts, FrontControl frontControl) throws PolygonException {
        System.out.println(request.getCharacterEncoding());
        Building buildingToBeEdited = (Building) session.getAttribute("buildingToBeEdited"); // Had been edited to "building"?! gave crash
        if (buildingToBeEdited == null) {
            buildingToBeEdited = (Building) session.getAttribute("building");
        }
        buildingToBeEdited.setBuildingName(request.getParameter("buildingName"));
        buildingToBeEdited.setStreetAddress(request.getParameter("streetAddress"));
        buildingToBeEdited.setStreetNumber(request.getParameter("streetNumber"));
        buildingToBeEdited.setZipCode(Integer.parseInt(request.getParameter("zipCode")));
        buildingToBeEdited.setBuildingSize(Double.parseDouble(request.getParameter("buildingSize")));
        buildingToBeEdited.setBuildingYear(Integer.parseInt(request.getParameter("BuildingYear")));
        buildingToBeEdited.setUseOfBuilding(request.getParameter("useOfBuilding"));
        //Calls method to upload file and get a string with filename back
        buildingToBeEdited.setBuilding_pic(nfu.savePictureBuilding(frontControl.getServletContext().getRealPath(""), parts));
        //This call should perhaps be moved to a deeper layer
        df.saveBuildingPic(buildingToBeEdited.getBdgId(), buildingToBeEdited.getBuildingPic());
        System.out.println("BuildingPic");
        System.out.println(buildingToBeEdited.getBuildingPic());
        df.Updatebuilding(buildingToBeEdited);
        session.setAttribute("newbuilding", buildingToBeEdited);
        return buildingToBeEdited;
    }

    /**
     * This method gets the selected floor and sets to a session
     * @param request
     * @param sessionObj
     * @param df
     */
    void selectFloor(HttpServletRequest request, HttpSession sessionObj, DomainFacade df, FrontControl frontControl) throws PolygonException {
        int id = Integer.parseInt(request.getParameter("floors"));
         BuildingFloor buildingfloor = df.getBuildingFloor(id);
        sessionObj.setAttribute("selectedFloor", buildingfloor);
        request.getSession().setAttribute("buildingfloor", buildingfloor);
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
    void findListOfBuilding(HttpServletRequest request, DomainFacade df, HttpSession sessionObj) throws PolygonException {
        //        int customerID = (Integer) sessionObj.getAttribute("customer_id");
        /**
         * This is just for testing. I have set the customerID by hardcode to 1
         */
        int customerID = 1;
        if (sessionObj.getAttribute("customer_id") != null) {
            customerID = (Integer) (sessionObj.getAttribute("customer_id"));
        }
        List<Building> buildingList = df.getListOfBuildings(customerID);
        sessionObj.setAttribute("listOfBuildings", buildingList);
    }

    /**
     * Loads all the customers buildings, based on whitch user the empoleyee
     * choose, and sets that in the session obj.
     *
     * @param sessionObj
     * @param df
     */
    void loadCustomersBuildings(HttpServletRequest request, HttpSession sessionObj, DomainFacade df) throws PolygonException {
        sessionObj.setAttribute("customerSelcted", true);
        int cusid = Integer.parseInt(request.getParameter("owners"));
        List<Building> listOfBuildings = df.getListOfBuildings(cusid);
        sessionObj.setAttribute("customersBuildings", listOfBuildings);
        Customer customer = df.getCustomer(cusid);
        customer.setBuildings(listOfBuildings);
        sessionObj.setAttribute("selectedCustomer", customer);
        request.getSession().setAttribute("customer", customer);
    }
    
}
