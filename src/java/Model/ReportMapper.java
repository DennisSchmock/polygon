/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.*;
import Domain.Exceptions.PolygonException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Dennis Schmock, Daniel Grønberg, Cherry Rose, Daniel Holmann
 */
public class ReportMapper {

    /**
     * Take the finished Report object and saves it in the database
     * with all of the Lists going to the right places.
     * @param r Report object
     * @param con Connection to the database
     * @return 
     * @throws Domain.Exceptions.PolygonException throws exception in case of sql-exceptions
     */
    public int reportToDataBase(Report r, Connection con) throws PolygonException {
        String SQLString = "insert into report(report_date,building_id, polygonuser, customer_user, category_conclusion, report_finished) values (?,?,?,?,?,?)";
        try {
           con.setAutoCommit(false);
            PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
            //insert a tuple and set the values
            statement.setDate(1, r.getDate());
            statement.setInt(2, r.getBuildingId());
            statement.setString(3, r.getPolygonUserName());
            statement.setString(4, r.getCustomerAccountable());
            statement.setInt(5, r.getCategoryConclusion());
            statement.setInt(6,1);                               // This sets the report state to be not finished.
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            int reportId = 0;
            if (rs.next()) {   // Changed from !rs.next() as this didn't return the key
                reportId = rs.getInt(1);
                System.out.println("Report id = " + reportId);
                r.setReportId(reportId);
            }
            if(r.getListOfRepRoom() != null){
            saveRoomsToDatabase(r, con);
            }
            if(r.getListOfRepExt() != null){
            saveExteriorToDB(r, con);
            }
            
            
            con.commit();
            System.out.println("Report Saved in database Succes - Yeah 8)");
            return reportId;

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.out.println("Failed at rollingback" + ex );
                throw new PolygonException("Database error");
            }
            System.out.println("Fail in saving new report - saveNewReport. Actions has been Rolledback");
            System.out.println(e);
            throw new PolygonException("Database error");
        }
        

    }
    


    /**
     * The purpose of this method, is to get a Single ReportObject based on the report Id.
     * @param reportId
     * @param con
     * @return
     * @throws Domain.Exceptions.PolygonException throws exception in case of sql-exceptions
     */

    public Report getSingleReport(int reportId, Connection con) throws PolygonException {
        Report r;
        String SQLString = "select * from report where report_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, reportId);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            
            Date reportDate = rs.getDate("report_date");
            int buildingId = rs.getInt("building_id");
            int catagoryConclusion = rs.getInt("category_conclusion");
            String polygonUser = rs.getString("polygonuser");
            String customerUser = rs.getString("customer_user");
           

            r = new Report(reportId, reportDate, buildingId, catagoryConclusion,polygonUser,customerUser);
            
            r.setReportFloors(getReportFloors(buildingId,reportId,con));
            r.setListOfRepRoom(getReportRooms(reportId, con));
            System.out.println("AddedRoom!!!");
            r.setListOfRepExt(getReportExterior(reportId, con));

            return r;
        } catch (Exception e) {
            System.out.println("Fail in NewReportMapper-getReport");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
    }

    /**
     * The purpose of this method is to get an List of all reports connected to a specific building.
     * @param buildingId
     * @param con
     * @return
     * @throws Domain.Exceptions.PolygonException throws exception in case of sql-exceptions
     */
    public ArrayList<Report> getAllReportsBuilding(int buildingId, Connection con) throws PolygonException {
        ArrayList<Report> reports = new ArrayList<>();
        String SQLString = "select * from report where building_id = ?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, buildingId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int reportId = rs.getInt("report_id");
                reports.add(getSingleReport(reportId, con));
            }
        }
        catch (Exception e) {
            System.out.println("Failed to get reports for building");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
        return reports;

    }

    

    //method that will take all the Report_Exteriour details in a certain Report
    private ArrayList<ReportExterior> getReportExterior(int id, Connection con) throws PolygonException {
        String SQLString = "select * from report_exterior where report=?";
        ArrayList<ReportExterior> listOfExt = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ReportExterior re = new ReportExterior(
                        rs.getInt("report_ext_id"),
                        rs.getString("rep_ext_inspected_area"),
                        rs.getString("report_ext_description"),
                        rs.getString("report_ext_pic"),
                        rs.getInt("report"));
                re.setRepExtPicDescriptoin(rs.getString("rep_ext_pic_description"));
                listOfExt.add(re);
            }
            return listOfExt;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfExt");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
    }

    //take all damages of a certain report room
    private ArrayList<ReportRoomDamage> getListOfDamages(int id, Connection con) throws PolygonException {
        String SQLString = "select * from report_room_damage where report_room=?";
        ArrayList<ReportRoomDamage> listOfDmg = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ReportRoomDamage rd = new ReportRoomDamage(
                        rs.getInt("report_room_damage_id"),
                        rs.getString("damage_time"),
                        rs.getString("place"),
                        rs.getString("what_happened"),
                        rs.getString("what_is_repaired"),
                        rs.getString("damage_type"),
                        rs.getInt("report_room"));
                listOfDmg.add(rd);
            }
            return listOfDmg;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfDamages");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
    }

    //take all interiors data of a certain room 
    private ArrayList<ReportRoomInterior> getListOfInt(int id, Connection con) throws PolygonException {
        String SQLString = "select * from report_room_interior where report_room=?";
        ArrayList<ReportRoomInterior> listOfInt = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ReportRoomInterior ri = new ReportRoomInterior(
                        rs.getInt("report_room_interior_id"),
                        rs.getString("report_room_interior_name"),
                        rs.getString("remark"),
                        rs.getInt("report_room"));
                listOfInt.add(ri);
            }
            return listOfInt;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfInt");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
    }

    //take all recommendations of a ceratin reported room
    private ArrayList<ReportRoomRecommendation> getListOfRec(int id, Connection con) throws PolygonException {
        String SQLString = "select * from report_room_recommendation where report_room=?";
        ArrayList<ReportRoomRecommendation> listOfRec = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ReportRoomRecommendation rrr = new ReportRoomRecommendation(
                        rs.getInt("report_room_recommendation_id"),
                        rs.getString("recommendation"),
                        rs.getInt("report_room"));
                listOfRec.add(rrr);
            }
            return listOfRec;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfRec");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
    }

    private void saveRoomsToDatabase(Report r, Connection con) throws Exception {
        String SQLString = "insert into report_room(room_name,report,building_room) values (?,?,?)";
        for (ReportRoom reportRoom : r.getListOfRepRoom()) {
            PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS); 
                statement.setString(1, reportRoom.getRoomName());
                statement.setInt(2, r.getReportId());
                statement.setInt(3, reportRoom.getBuildingRoomId());
                System.out.println( "ReportRoom Building FOREIGN KEY: " + reportRoom.getBuildingRoomId());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                int roomId = 0;
                if (rs.next()) {
                    roomId = rs.getInt(1);
                }
                if(reportRoom.getListOfDamages() != null){
                saveRoomDamages(reportRoom, con);
                }
                if(reportRoom.getListOfInt() != null){
                saveRoomInterior(reportRoom, con);
                }
                if(reportRoom.getListOfRec() != null){
                saveRoomRecommendations(reportRoom, con);
                }
                if(reportRoom.getMoist() != null){
                saveRoomMoist(reportRoom, con);
                saveRoomPics(reportRoom,con);
        }
        }

    }

    private void saveRoomDamages(ReportRoom r, Connection con) throws Exception {
        String SQLString = "insert into report_room_damage (damage_time,place,what_happened,what_is_repaired,damage_type,report_room)"
                + " values (?,?,?,?,?,?)";
        for (ReportRoomDamage damage : r.getListOfDamages()) {
             PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS); 

                statement.setString(1, damage.getDamageTime());
                statement.setString(2, damage.getPlace());
                statement.setString(3, damage.getWhatHappened());
                statement.setString(4, damage.getWhatIsRepaired());
                statement.setString(5, damage.getDamageType());
                statement.setInt(6, r.getRepRoomId());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

          
        }

    }

    private void saveRoomInterior(ReportRoom reportRoom, Connection con) throws Exception {
        String SQLString = "insert into report_room_interior(report_room_interior_name,remark,report_room)"
                + " values (?,?,?)";
        for (ReportRoomInterior rri : reportRoom.getListOfInt()) {
            PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, rri.getRepRoomIntName());
                statement.setString(2, rri.getRemark());
                statement.setInt(3, reportRoom.getRepRoomId());
                statement.executeUpdate();

           

        }

    }

    private void saveRoomRecommendations(ReportRoom reportRoom, Connection con) throws Exception {
        String SQLString = "insert into report_room_recommendation(recommendation,report_room) values (?,?)";
        for (ReportRoomRecommendation rrr : reportRoom.getListOfRec()) {

            PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, rrr.getRecommendation());
                statement.setInt(2, reportRoom.getRepRoomId());
                statement.executeUpdate();

           
        }

    }

    private void saveRoomMoist(ReportRoom reportRoom, Connection con) throws Exception {
        String SQLString = "insert into report_room_moist(report_room_moist_measured, report_room_moist_place,report_room_id) values (?,?,?)";
        PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reportRoom.getMoist().getMoistMeasured());
            statement.setString(2, reportRoom.getMoist().getMeasurePoint());
            statement.setInt(3, reportRoom.getRepRoomId());
            statement.executeUpdate();

        
    }

    private void saveExteriorToDB(Report r, Connection con) throws Exception {

        String SQLString = "insert into report_exterior(report_ext_description, report_ext_pic,report,rep_ext_inspected_area, rep_ext_pic_description) values (?,?,?,?,?)";
        for (ReportExterior re : r.getListOfRepExt()) {
                       
           PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, re.getRepExtDescription());
                statement.setString(2, r.getListOfExtPics().get(0).getFilename());
                statement.setString(4, re.getRepExtInspectedArea());
                statement.setString(5, r.getListOfExtPics().get(0).getDescription());
                statement.setInt(3, r.getReportId());
                statement.executeUpdate();
        }
    }

    private ArrayList<ReportRoom> getReportRooms(int reportId, Connection con) throws PolygonException {
        ArrayList<ReportRoom> reportRooms = new ArrayList<>();

        String SQLString = "select * from report_room where report=?";

        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, reportId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int reportRoomId = rs.getInt("report_room_id");
                String roomName = rs.getString("room_name");
                ReportRoom reportRoom = new ReportRoom(reportRoomId, roomName, reportId);
                reportRoom.setListOfInt(getListOfInt(reportRoomId, con));
                reportRoom.setListOfDamages(getListOfDamages(reportRoomId, con));
                reportRoom.setListOfRec(getListOfRec(reportRoomId, con));
                reportRoom.setMoist(getMoist(reportRoomId, con));
                reportRoom.setRrPic(getListOfRoomPics(reportRoomId, con));
                reportRooms.add(reportRoom);
                
            }
            return reportRooms;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoom");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
    }

    

    private ReportRoomMoist getMoist(int reportRoomId, Connection con) throws PolygonException {
        String SQLString = "select * from report_room_moist where report_room_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, reportRoomId);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ReportRoomMoist moist; 
                    String place = rs.getString("report_room_moist_place");
                    String measured = rs.getString("report_room_moist_measured");
                  
            moist = new ReportRoomMoist(place,measured);
            return moist;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfDamages");
            System.out.println(e.getMessage());
            throw new PolygonException("Database error");
        }
    }

    private ArrayList<ReportFloor> getReportFloors(int buildingId,int reportId,Connection con) throws PolygonException {
    ArrayList<ReportFloor> reportFloors = new ArrayList<>();
    String SQLString = "SELECT * FROM Polygon.building_floor where idbuilding = ?;";

         try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, buildingId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int floorId = rs.getInt("floor_id");
                int floorNumber = rs.getInt("floor_number");
                int m2 = rs.getInt("floor_size");
                ReportFloor reportFloor = new ReportFloor( floorId, floorNumber, m2,  reportId, buildingId);
                reportFloor.setReportRooms(getReportRoomsWithFloorId(floorId,reportId,con));
                reportFloors.add(reportFloor);

            }
            return reportFloors;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoom");
            System.out.println(e.getMessage());
            throw new PolygonException("Error retrieving floors from database");
        }
    
    }

    private ArrayList<ReportRoom> getReportRoomsWithFloorId(int floorId, int reportId, Connection con) throws PolygonException {
        ArrayList<ReportRoom> reportRooms = new ArrayList<>();

        String SQLString = "SELECT * FROM Polygon.building_room, Polygon.report_room where report_room.building_room = building_room.room_id AND building_room.floor_id = ? AND report = ?;";

        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, floorId);
            statement.setInt(2, reportId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int reportRoomId = rs.getInt("report_room_id");
                String roomName = rs.getString("room_name");
                int buildingRoomId = rs.getInt("building_room");
                
                ReportRoom reportRoom = new ReportRoom(reportRoomId, roomName, buildingRoomId);
                reportRoom.setListOfInt(getListOfInt(reportRoomId, con));
                reportRoom.setListOfDamages(getListOfDamages(reportRoomId, con));
                reportRoom.setListOfRec(getListOfRec(reportRoomId, con));
                reportRoom.setMoist(getMoist(reportRoomId, con));
                reportRoom.setRrPic(getListOfRoomPics(reportRoomId,con));
                reportRooms.add(reportRoom);

            }
            return reportRooms;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoomBasedOnFloorId");
            System.out.println(e.getMessage());
            throw new PolygonException("Error retrieving report for room from database");
            
        }
    }

    ArrayList<Report> getSimpleListOfReports(Connection con) throws PolygonException {
         ArrayList<Report> reports = new ArrayList<>();
        String SQLString = "select * from report";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Report r = new Report();
                r.setReportId(rs.getInt("report_id"));
                r.setDate(rs.getDate("report_date"));
                reports.add(r);
            }
            return reports;
        }
        catch (Exception e) {
            System.out.println("Failed to get reports for building");
            System.out.println(e.getMessage());
            throw new PolygonException("Error retrieving reports from database");
        }
    }
    private void saveRoomPics(ReportRoom reportRoom, Connection con) throws Exception{
        String SQLString = "insert into report_room_pic(description, filename,reportroom) values (?,?,?)";
        for (ReportPic  rrPic : reportRoom.getRrPic()) {
            
        
        PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, rrPic.getDescription());
            statement.setString(2, rrPic.getFilename());
            statement.setInt(3, reportRoom.getRepRoomId());
            statement.executeUpdate();
        }
    }

    private ArrayList<ReportPic> getListOfRoomPics(int reportRoomId, Connection con) throws PolygonException {
        String SQLString = "select * from report_room_pic where reportroom=?";
        ArrayList<ReportPic> listOfRooms = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, reportRoomId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ReportPic rPic = new ReportPic(
                        
                        rs.getString("filename"),
                        rs.getString("description"));
                listOfRooms.add(rPic);
            }
            return listOfRooms;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfRoomPics");
            
            System.out.println(e.getMessage());
            throw new PolygonException("Error retrieving images from database");
            
        }}

   

    

}
