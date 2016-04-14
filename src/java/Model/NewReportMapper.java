/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dennis Schmock, Daniel Grønberg, Cherry Rose
 */
public class NewReportMapper {

    /**
     * Take the finished Report object and saves it in the database
     * with all of the Lists going to the right places.
     * @param r Report object
     * @param con Connection to the database
     */
    public void reportToDataBase(Report r, Connection con) {
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
            }

            saveRoomsToDatabase(r, reportId, con);
            saveExteriorToDB(r, reportId, con);
            
            con.commit();
            System.out.println("Report Saved in database Succes - Yeah 8)");

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.out.println("Failed at rollingback" + ex );
            }
            System.out.println("Fail in saving new report - saveNewReport. Actions has been Rolledback");
            System.out.println(e);
        }

    }
    
//    /**
//     * Saves an NON finished Report object in the database. It
//     * Assumes that so far the report object only contains building_id 
//     * and Polygonuser. The rest is to be inserted.
//     * @param report Report only containing values of the building and polygonuser.
//     * @param con Connection to the database
//     * @return Returns the updated Report object that now has a Report_id
//     */
//    public Report createReportTuble(Report report, Connection con){
//        String sql = "insert into report(building_id, polygonuser, report_finished) values (?,?,?)";
//        
//        try {
//            PreparedStatement statment = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statment.setInt(1, report.getBuildingId());
//            statment.setString(2, report.getPolygonUserName());
//            statment.setInt(3, 0); // means that the report statues is set to not finished
//            statment.executeUpdate();
//            ResultSet rs = statment.getGeneratedKeys();
//            if(rs.next()){
//                report.setReportId(rs.getInt(1));
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error in inserting Report Tuble " + ex );
//        }
//        return report;
//    }

    /**
     * The purpose of this method, is to get a Single ReportObject based on the report Id.
     * @param reportId
     * @param con
     * @return
     */

    public Report getSingleReport(int reportId, Connection con) {
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
//            r.setListOfRepRoomExt(getReportExterior(reportId, con));

            return r;
        } catch (Exception e) {
            System.out.println("Fail in NewReportMapper-getReport");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * The purpose of this method is to get an List of all reports connected to a specific building.
     * @param buildingId
     * @param con
     * @return
     */
    public ArrayList<Report> getAllReportsBuilding(int buildingId, Connection con) {
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
            return null;
        }
        return reports;

    }

    /**
     * The purpose of this method is to get a list of all reports in the database.
     * @param con
     * @return
     */
    public ArrayList<Report> getAllReports(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    //method that will take all the Report_Exteriour details in a certain Report
    private ArrayList<ReportExterior> getReportExterior(int id, Connection con) {
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
                        rs.getInt("report_ext_pic"),
                        rs.getInt("report"));
                listOfExt.add(re);
            }
            return listOfExt;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfExt");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //take all damages of a certain report room
    private ArrayList<ReportRoomDamage> getListOfDamages(int id, Connection con) {
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
            return null;
        }
    }

    //take all interiors data of a certain room 
    private ArrayList<ReportRoomInterior> getListOfInt(int id, Connection con) {
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
            return null;
        }
    }

    //take all recommendations of a ceratin reported room
    private ArrayList<ReportRoomRecommendation> getListOfRec(int id, Connection con) {
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
            return null;
        }
    }

    private void saveRoomsToDatabase(Report r, int reportId, Connection con) throws Exception {
        String SQLString = "insert into report_room(room_name,report,building_room) values (?,?,?)";
        for (ReportRoom reportRoom : r.getListOfRepRoom()) {
            PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS); 
                statement.setString(1, reportRoom.getRoomName());
                statement.setInt(2, reportId);
                statement.setInt(3, reportRoom.getBuildingRoomId());
                System.out.println( "ReportRoom Building FOREIGN KEY: " + reportRoom.getBuildingRoomId());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                int roomId = 0;
                if (rs.next()) {
                    roomId = rs.getInt(1);
                }
                saveRoomDamages(reportRoom, roomId, con);
                saveRoomInterior(reportRoom, roomId, con);
                saveRoomRecommendations(reportRoom, roomId, con);
                saveRoomMoist(reportRoom, roomId, con);
        }

    }

    private void saveRoomDamages(ReportRoom r, int roomId, Connection con) throws Exception {
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
                statement.setInt(6, roomId);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

          
        }

    }

    private void saveRoomInterior(ReportRoom reportRoom, int roomId, Connection con) throws Exception {
        String SQLString = "insert into report_room_interior(report_room_interior_name,remark,report_room)"
                + " values (?,?,?)";
        for (ReportRoomInterior rri : reportRoom.getListOfInt()) {
            PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, rri.getRepRoomIntName());
                statement.setString(2, rri.getRemark());
                statement.setInt(3, roomId);
                statement.executeUpdate();

           

        }

    }

    private void saveRoomRecommendations(ReportRoom reportRoom, int roomId, Connection con) throws Exception {
        String SQLString = "insert into report_room_recommendation(recommendation,report_room) values (?,?)";
        for (ReportRoomRecommendation rrr : reportRoom.getListOfRec()) {

            PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, rrr.getRecommendation());
                statement.setInt(2, roomId);
                statement.executeUpdate();

           
        }

    }

    private void saveRoomMoist(ReportRoom reportRoom, int roomId, Connection con) throws Exception {
        String SQLString = "insert into report_room_moist(report_room_moist_measured, report_room_moist_place,report_room_id) values (?,?,?)";
        PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reportRoom.getMoist().getMoistMeasured());
            statement.setString(2, reportRoom.getMoist().getMeasurePoint());
            statement.setInt(3, roomId);
            statement.executeUpdate();

        
    }

    private void saveExteriorToDB(Report r, int reportId, Connection con) throws Exception {

        String SQLString = "insert into report_exterior(report_ext_description, report_ext_pic,report) values (?,?,?)";
        for (ReportExterior re : r.getListOfRepExt()) {

           PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, re.getRepExtDescription());
                statement.setInt(2, re.getRepExtPic());
                statement.setInt(3, reportId);
                statement.executeUpdate();
        }
    }

    private ArrayList<ReportRoom> getReportRooms(int reportId, Connection con) {
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
                reportRooms.add(reportRoom);

            }
            return reportRooms;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoom");
            System.out.println(e.getMessage());
            return null;
        }
    }

    

    private ReportRoomMoist getMoist(int reportRoomId, Connection con) {
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
            return null;
        }
    }

    private ArrayList<ReportFloor> getReportFloors(int buildingId,int reportId,Connection con) {
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
                reportFloor.setReportRooms(getReportRoomsWithFloorId(floorId,con));
                reportFloors.add(reportFloor);

            }
            return reportFloors;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoom");
            System.out.println(e.getMessage());
            return null;
        }
    
    }

    private ArrayList<ReportRoom> getReportRoomsWithFloorId(int floorId, Connection con) {
        ArrayList<ReportRoom> reportRooms = new ArrayList<>();

        String SQLString = "SELECT * FROM Polygon.building_room, Polygon.report_room where report_room.building_room = building_room.room_id AND building_room.floor_id = ?;";

        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, floorId);
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
                reportRooms.add(reportRoom);

            }
            return reportRooms;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoomBasedOnFloorId");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
