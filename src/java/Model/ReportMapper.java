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
import java.util.ArrayList;

/**
 *
 * @author CJS
 */
public class ReportMapper {

    //Saving a new report in DB-Report Table
    public Report saveNewReport(Report r, Connection con) {
        String SQLString = "insert into report(report_date,building_id,category_conclusion) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(r.getDate());  //gets a String value of date and converts it to sql date
            //insert a tuple and set the values
            statement.setDate(1, date);
            statement.setInt(2, r.getBuildingId());
            statement.setInt(3, r.getCategoryConclusion());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {   // Changed from !rs.next() as this didn't return the key
                r.setReportId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving new report - saveNewReport");
            System.out.println(e.getMessage());
        }
        return r;
    }

    //saving a new room exterior report in DB-Report_Exterior table
    public void saveReportExt(ReportRoomExterior re, Connection con) {
        String SQLString = "insert into report_exterior(report_ext_description, report_ext_pic,report) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, re.getRepExtDescription());
            statement.setInt(2, re.getRepExtPic());
            statement.setInt(3, re.getReportId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                re.setRepExtId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report exterior - saveReportExt");
            System.out.println(e.getMessage());
        }
    }

    //saving a new report of a room in DB-Report_Room table
    public ReportRoom saveReportRoom(ReportRoom rr, Connection con) {
        String SQLString = "insert into report_room(room_name,report,building_room) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, rr.getRoomName());
            statement.setInt(2, rr.getReportId());
            statement.setInt(3, rr.getBuildingRoomId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                rr.setRepRoomId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room - saveReportRoom");
            System.out.println(e.getMessage());
        }
        return rr;
    }

    //Saving a new report for damage in a room in DB-Report_Room_Damage table
    public void saveReportRoomDamage(ReportRoomDamage rrd, Connection con) {
        String SQLString = "insert into report_room_damage (damage_time,place,what_happened,what_is_repaired,damage_type,report_room)"
                + " values (?,?,?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(rrd.getDamageTime());
            statement.setDate(1, date);
            statement.setString(2, rrd.getPlace());
            statement.setString(3, rrd.getWhatHappened());
            statement.setString(4, rrd.getWhatIsRepaired());
            statement.setString(5, rrd.getDamageType());
            statement.setInt(6, rrd.getRepRoomId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                rrd.setRepRoomDmgId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room damage - saveReportRoomDamage");
            System.out.println(e.getMessage());
        }
    }

    //saving a new report for room's interior in DB-Report_Room_Interiour table
    public void saveReportInterior(ReportRoomInterior ri, Connection con) {
        String SQLString = "insert into report_room_interior(report_room_interior_name,remark,report_room)"
                + " values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ri.getRepRoomIntName());
            statement.setString(2, ri.getRemark());
            statement.setInt(3, ri.getRepRoomId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()) {
                ri.setRepRoomIntId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room interior - saveReportInterior");
            System.out.println(e.getMessage());
        }
    }
    
    //saving a new room moist report in DB-report_room_moist table
    public void saveReportMoist(ReportRoomMoist rm, Connection con) {
        String SQLString = "insert into report_room_moist(report_room_moist_measured, report_room_moist_place,report_room_id) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, rm.getMoistMeasured());
            statement.setString(2, rm.getMeasurePoint());
            statement.setInt(3, rm.getReportRoom());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                rm.setMoistMeasureId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report moist - report_room_moist");
            System.out.println(e.getMessage());
        }
    }

    //saving a new room report recommendations in DB-Report_Room_Recommendations table
    public void saveReportRoomRec(ReportRoomRecommendation rrr, Connection con) {
        String SQLString = "insert into report_room_recommendation(recommendation,report_room) values (?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, rrr.getRecommendation());
            statement.setInt(2, rrr.getRepRoomId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()) {
                rrr.setRepRoomRecId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room recommendation - saveReportRecommendation");
            System.out.println(e.getMessage());
        }
    }
    
    //Get a report from the DB
    public Report getReport(int id, Connection con) {

        String SQLString = "select * from report where report_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Report r = new Report(
                    rs.getInt("report_id"),
                    rs.getDate("report_date").toString(),
                    rs.getInt("building_id"),
                    rs.getInt("category_conclusion"));
            return r;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReport");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //Get data of a room exterior in db
    public ReportRoomExterior getReportExt(int id, Connection con) {

        String SQLString = "select * from report_exterior where report_ext_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ReportRoomExterior re = new ReportRoomExterior(
                    rs.getInt("report_ext_id"),
                    rs.getString("report_ext_description"),
                    rs.getInt("report_ext_pic"),
                    rs.getInt("report"));
            return re;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportExt");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //get data of reported room from db
    public ReportRoom getReportRoom(int id, Connection con) {

        String SQLString = "select * from report_room where report_room_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ReportRoom rr = new ReportRoom(
                    rs.getInt("report_room_id"),
                    rs.getString("room_name"),
                    rs.getInt("report"));
            return rr;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoom");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //get a reported damage from db
    public ReportRoomDamage getReportDamage(int id, Connection con) {

        String SQLString = "select * from report_room_damage where report_room_damage_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ReportRoomDamage rd = new ReportRoomDamage(
                    rs.getInt("report_room_damage_id"),
                    rs.getDate("damage_time").toString(),
                    rs.getString("place"),
                    rs.getString("what_happened"),
                    rs.getString("what_is_repaired"),
                    rs.getString("damage_type"),
                    rs.getInt("report_room"));
            return rd;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportDamage");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //get a data of a report interior
    public ReportRoomInterior getReportInt(int id, Connection con) {

        String SQLString = "select * from report_room_interior where report_room_interior_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ReportRoomInterior ri = new ReportRoomInterior(
                    rs.getInt("report_room_interior_id"),
                    rs.getString("report_room_interior_name"),
                    rs.getString("remark"),
                    rs.getInt("report_room"));
            return ri;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportInt");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //get a room recommndation from db
    public ReportRoomRecommendation getReportRec(int id, Connection con) {

        String SQLString = "select * from report_room_recommendation where report_room_recommendation_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ReportRoomRecommendation rrr = new ReportRoomRecommendation(
                    rs.getInt("report_room_recommendation_id"),
                    rs.getString("recommendation"),
                    rs.getInt("report_room"));
            return rrr;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRec");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //method that will take all the Report_Exteriour details in a certain Report
    public ArrayList<ReportRoomExterior> getListOfExt(int id, Connection con) {
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
    
    //take all the listed report rooms of a certain report
    public ArrayList<ReportRoom> getListOfReportRoom(int id, Connection con){
        String SQLString = "select * from report_room where report=?";
        ArrayList<ReportRoom> listOfRepRm = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ReportRoom rr = new ReportRoom(
                        rs.getInt("report_room_id"),
                        rs.getString("room_name"),
                        rs.getInt("report"));
                listOfRepRm.add(rr);
            }
            return listOfRepRm;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfReportRoom");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //take all damages of a certain report room
    public ArrayList<ReportRoomDamage> getListOfDamages(int id, Connection con){
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
    public ArrayList<ReportRoomInterior> getListOfInt(int id, Connection con){
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
    public ArrayList<ReportRoomRecommendation> getListOfRec(int id, Connection con){
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
}
