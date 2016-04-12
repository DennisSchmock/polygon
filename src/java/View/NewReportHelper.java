/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Domain.Building;
import Domain.BuildingRoom;
import Domain.DomainFacade;
import Domain.Report;
import Domain.ReportRoom;
import Domain.ReportRoomDamage;
import Domain.ReportRoomExterior;
import Domain.ReportRoomInterior;
import Domain.ReportRoomMoist;
import Domain.ReportRoomRecommendation;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Dennis Schmock, Daniel Grønberg, Cherry Rose
 */
public class NewReportHelper extends HttpServlet {

    DomainFacade df;
    Building b; // This is only a placeholder until we can get a building out of db
    BuildingRoom r1;
    BuildingRoom r2;
    BuildingRoom r3;
    ArrayList listOfRooms;

    public NewReportHelper() {
        b = new Building("Vaskeriet", "Nørregårdsvej", "28", 2800, 1978, 145.6, "Study facility");
        b.setBdgId(1);
        r1 = new BuildingRoom(1, "Entrance");
        r2 = new BuildingRoom(2, "Toilets");
        r3 = new BuildingRoom(3, "Main Room");
        listOfRooms = new ArrayList<BuildingRoom>();
        listOfRooms.add(r1);
        listOfRooms.add(r2);
        listOfRooms.add(r3);
//        b.setListOfRooms(listOfRooms);
    }

    public HttpServletRequest process(HttpServletRequest request, HttpServletResponse response, DomainFacade df) {
        this.df = df;
        HttpSession session = request.getSession();
        int numOfRooms = 0;
        if (request.getAttribute("numOfRooms") != null) {
            numOfRooms = (int) request.getAttribute("numOfRooms");
        }
        request.setAttribute("numOfRooms", numOfRooms);
        session.setAttribute("testBuilding", b); //Using a mock-instance of building until we can create from DB
//        session.setAttribute("buildingRooms", b.getListOfRooms());
        String command = (String) request.getParameter("command");
        if (command == null) {
            command = "";
        }
        if (command.equals("reportAddRoom")) {
            request = AddRoom(request, response);
        }

        if (command.equals("reportAddRecommendation")) {
            request = AddRecommendation(request, response);
        }
       
        return request;

    }

    /**
     * Increments the counter that the report.jsp uses to determine the amount
     * of input fields for room damages
     *
     * @param request
     * @param response
     * @return
     */
    public HttpServletRequest AddRoom(HttpServletRequest request, HttpServletResponse response) {
        int numOfRooms;

        if (request.getParameter("numOfRooms") != null && !(request.getParameter("numOfRooms")).equals("")) {
            numOfRooms = Integer.parseInt(request.getParameter("numOfRooms")) + 1;
        } else {
            numOfRooms = 1;
        }
        request.setAttribute("numOfRooms", numOfRooms);
        return request;
    }

    public HttpServletRequest AddRecommendation(HttpServletRequest request, HttpServletResponse response) {
        int numOfRecs;

        if (request.getParameter("numOfRecs") != null && !(request.getParameter("numOfRecs")).equals("")) {
            numOfRecs = Integer.parseInt(request.getParameter("numOfRecs")) + 1;
        } else {
            numOfRecs = 1;
        }
        request.setAttribute("numOfRecs", numOfRecs);
        return request;
    }

    /**
     * Uses information from the request to submit a report including subparts
     * in the database
     *
     * @param request
     * @param response
     * @param df
     */
    public Report submitReport(HttpServletRequest request, HttpServletResponse response,DomainFacade df) {   
        Report report;

        int reportCategory = Integer.parseInt(request.getParameter("category"));
        int buildingId = b.getBdgId(); //This needs to get pulled from the form...
        String reportDate = request.getParameter("date");
        report = new Report(reportDate, buildingId, reportCategory);

        int numOfRooms = 0;
        if (request.getParameter("numOfRooms") != null) {
            numOfRooms = (int) Integer.parseInt(request.getParameter("numOfRooms"));
        }

        String roof = request.getParameter("roof"); //Getting description of Roof.
        String walls = request.getParameter("walls"); //Getting description of Walls.
        report.getListOfRepRoomExt().add(new ReportRoomExterior(roof, 1));//Putting it inside the report object
        report.getListOfRepRoomExt().add(new ReportRoomExterior(walls, 1));

        for (int roomCount = 0; roomCount <= numOfRooms; roomCount++) {
            ReportRoom rr;
            int roomNumber = Integer.parseInt(request.getParameter("roomSelect" + String.valueOf(roomCount + 1)));

            rr = new ReportRoom("roomNameNotHarvested?",roomNumber);//Create a ReportRoom;
            rr.getListOfDamages().add(createReportRoomDamage(request, roomCount));

            int moist;
            String place = "";
            //Get the moistlevels from the form.
            if (!request.getParameter("moistScan" + String.valueOf(roomCount + 1)).equals("")) {
                moist = Integer.parseInt(request.getParameter("moistScan" + String.valueOf(roomCount + 1)));
                if (request.getParameter("moistPoint" + String.valueOf(roomCount + 1)) != null) {
                    place = (String) request.getParameter("moistPoint" + String.valueOf(roomCount + 1));
                }
                rr.setMoist(new ReportRoomMoist(moist, place)); //Adding moist object to room
            }

            rr.setListOfInt(createInterior(request, roomCount));//Add the list of interior to the Arraylist in the Room

            if (request.getParameter("recommendation" + String.valueOf(roomCount + 1)) != null) {
                String recommendation = request.getParameter("recommendation" + String.valueOf(roomCount + 1));
                rr.getListOfRec().add(new ReportRoomRecommendation(recommendation));                        
            }
            report.getListOfRepRoom().add(rr);//Add the room to the report
        }
        df.saveReport(report);
        return report;
    }

    /**
     * This method creates an object to input.
     * @param request
     * @param roomCount
     * @return
     */
    public ReportRoomDamage createReportRoomDamage(HttpServletRequest request, int roomCount) {
        ReportRoomDamage repRoomDam;
        String when;
        String where = "";
        String how = "";
        String whatIsDone = "";

        when = request.getParameter("when" + String.valueOf(roomCount + 1));

        if (request.getParameter("where" + String.valueOf(roomCount + 1)) != null) {
            where = request.getParameter("where" + String.valueOf(roomCount + 1));
        }

        if (request.getParameter("how" + String.valueOf(roomCount + 1)) != null) {
            how = request.getParameter("how" + String.valueOf(roomCount + 1));
        }

        if (request.getParameter("whatIsDone" + String.valueOf(roomCount + 1)) != null) {
            whatIsDone = request.getParameter("whatIsDone" + String.valueOf(roomCount + 1));
        }
        repRoomDam = new ReportRoomDamage(when, where, how, whatIsDone, "add damageType");
        return repRoomDam;
    }

   

    private ArrayList<ReportRoomInterior> createInterior(HttpServletRequest request, int roomCount) {
        ArrayList<ReportRoomInterior> interior = new ArrayList<>();

        String rWalls = request.getParameter("rWalls" + String.valueOf(roomCount + 1));
        String rCeil = request.getParameter("rCeil" + String.valueOf(roomCount + 1));
        String rFloor = request.getParameter("rFloor" + String.valueOf(roomCount + 1));
        String rWinDoors = request.getParameter("rWinDoors" + String.valueOf(roomCount + 1));
        String r1other = request.getParameter("r1other" + String.valueOf(roomCount + 1));
        String r2other = request.getParameter("r2other" + String.valueOf(roomCount + 1));
        String r1otherLoc = request.getParameter("r1otherLoc" + String.valueOf(roomCount + 1));
        String r2otherLoc = request.getParameter("r2otherLoc" + String.valueOf(roomCount + 1));

        if (rWalls != (null)) {
            interior.add(new ReportRoomInterior("Walls", rWalls));
        }
        if (rCeil != (null)) {
            interior.add(new ReportRoomInterior("Ceiling", rCeil));
        }
        if (rFloor != (null)) {
            interior.add(new ReportRoomInterior("Floor", rFloor));
        }
        if (rWinDoors != (null)) {
            interior.add(new ReportRoomInterior("Windows and doors", rWinDoors));

        }
        if (r1other != null && r1otherLoc != (null)) {
            interior.add(new ReportRoomInterior("Other 1", r1other));

        }
        if (r2other != (null) && r2otherLoc != (null)) {
            interior.add(new ReportRoomInterior("Other 2", r2other));

        }
        return interior;
    }
}
