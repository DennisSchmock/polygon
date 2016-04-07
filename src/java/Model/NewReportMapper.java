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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dennis Schmock, Daniel Grønberg, Cherry Rose
 */
public class NewReportMapper {

    //Saving a new report in DB-Report Table
    public void reportToDataBase(Report r, Connection con) {
        String SQLString = "insert into report(report_date,building_id,category_conclusion) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(r.getDate());  //gets a String value of date and converts it to sql date. May break!
            //insert a tuple and set the values
            statement.setDate(1, date);
            statement.setInt(2, r.getBuildingId());
            statement.setInt(3, r.getCategoryConclusion());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            int reportId = 0;
            if (rs.next()) {   // Changed from !rs.next() as this didn't return the key
                reportId = rs.getInt(1);
                System.out.println("Report id = " + reportId);
            }

            saveRoomsToDatabase(r, reportId, con);
            saveExteriorToDB(r, reportId, con);

        } catch (Exception e) {
            System.out.println("Fail in saving new report - saveNewReport");
            System.out.println(e.getMessage());
        }

    }

    public Report getSingleReport(int reportId, Connection con) {
        Report r;
        String SQLString = "select * from report where report_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, reportId);
            System.out.println("Get here?");
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String reportDate = rs.getDate("report_date").toString();
            int buildingId = rs.getInt("building_id");
            int catagoryConclusion = rs.getInt("category_conclusion");

            r = new Report(reportId, reportDate, buildingId, catagoryConclusion);
            r.setListOfRepRoom(getReportRooms(reportId, con));
            r.setListOfRepRoomExt(getReportExterior(reportId, con));

            return r;
        } catch (Exception e) {
            System.out.println("Fail in NewReportMapper-getReport");
            System.out.println(e.getMessage());
            return null;
        }
    }

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

    public ArrayList<Report> getAllReports(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    //method that will take all the Report_Exteriour details in a certain Report
    private ArrayList<ReportRoomExterior> getReportExterior(int id, Connection con) {
        String SQLString = "select * from report_exterior where report=?";
        ArrayList<ReportRoomExterior> listOfExt = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ReportRoomExterior re = new ReportRoomExterior(
                        rs.getInt("report_ext_id"),
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
                        rs.getDate("damage_time").toString(),
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

    private void saveRoomsToDatabase(Report r, int reportId, Connection con) {
        String SQLString = "insert into report_room(room_name,report,building_room) values (?,?,?)";
        for (ReportRoom reportRoom : r.getListOfRepRoom()) {
            try (PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, reportRoom.getRoomName());
                statement.setInt(2, reportId);
                statement.setInt(3, reportRoom.getBuildingRoomId());
                System.out.println(reportRoom.getBuildingRoomId());
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

            } catch (Exception e) {
                System.out.println("Fail in saving report room - saveReportRoom");
                System.out.println(e.getMessage());
            }
        }

    }

    private void saveRoomDamages(ReportRoom r, int roomId, Connection con) {
        String SQLString = "insert into report_room_damage (damage_time,place,what_happened,what_is_repaired,damage_type,report_room)"
                + " values (?,?,?,?,?,?)";
        for (ReportRoomDamage damage : r.getListOfDamages()) {
            try (PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {

                Date date = java.sql.Date.valueOf(damage.getDamageTime());
                statement.setDate(1, date);
                statement.setString(2, damage.getPlace());
                statement.setString(3, damage.getWhatHappened());
                statement.setString(4, damage.getWhatIsRepaired());
                statement.setString(5, damage.getDamageType());
                statement.setInt(6, roomId);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

            } catch (Exception e) {
                System.out.println("Fail in saving report room damage - saveReportRoomDamage");
                System.out.println(e.getMessage());
            }
        }

    }

    private void saveRoomInterior(ReportRoom reportRoom, int roomId, Connection con) {
        String SQLString = "insert into report_room_interior(report_room_interior_name,remark,report_room)"
                + " values (?,?,?)";
        for (ReportRoomInterior rri : reportRoom.getListOfInt()) {
            try (PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, rri.getRepRoomIntName());
                statement.setString(2, rri.getRemark());
                statement.setInt(3, roomId);
                statement.executeUpdate();

            } catch (Exception e) {
                System.out.println("Fail in saving report room interior - saveReportInterior");
                System.out.println(e.getMessage());
            }

        }

    }

    private void saveRoomRecommendations(ReportRoom reportRoom, int roomId, Connection con) {
        String SQLString = "insert into report_room_recommendation(recommendation,report_room) values (?,?)";
        for (ReportRoomRecommendation rrr : reportRoom.getListOfRec()) {

            try (PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, rrr.getRecommendation());
                statement.setInt(2, roomId);
                statement.executeUpdate();

            } catch (Exception e) {
                System.out.println("Fail in saving report room recommendation - saveReportRecommendation");
                System.out.println(e.getMessage());
            }
        }

    }

    private void saveRoomMoist(ReportRoom reportRoom, int roomId, Connection con) {
        String SQLString = "insert into report_room_moist(report_room_moist_measured, report_room_moist_place,report_room_id) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, reportRoom.getMoist().getMoistMeasured());
            statement.setString(2, reportRoom.getMoist().getMeasurePoint());
            statement.setInt(3, roomId);
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Fail in saving report moist - report_room_moist");
            System.out.println(e.getMessage());
        }
    }

    private void saveExteriorToDB(Report r, int reportId, Connection con) {

        String SQLString = "insert into report_exterior(report_ext_description, report_ext_pic,report) values (?,?,?)";
        for (ReportRoomExterior re : r.getListOfRepRoomExt()) {

            try (PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, re.getRepExtDescription());
                statement.setInt(2, re.getRepExtPic());
                statement.setInt(3, reportId);
                statement.executeUpdate();

            } catch (Exception e) {
                System.out.println("Fail in saving report exterior - saveReportExt");
                System.out.println(e.getMessage());
            }
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
                    int measured = rs.getInt("report_room_moist_measured");
                  
            moist = new ReportRoomMoist(place,measured);
            return moist;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfDamages");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
