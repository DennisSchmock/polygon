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

/**
 *
 * @author CJS
 */
public class ReportMapper {
    
    //Saving a new report in DB-Report Table
    public boolean saveNewReport(Report r, Connection con) {
        String SQLString = "insert into Report values (?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(r.getDate());
            statement.setInt(1, r.getReportNum());
            statement.setDate(2, date);
            statement.setInt(3, r.getBdgId());
            statement.setInt(4, r.getCategoryConclusion());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving new report - saveNewReport");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    //saving a new room exterior report in DB-Report_Exterior table
    public boolean saveReportExt(ReportRoomExterior re, Connection con) {
        String SQLString = "insert into Report_Exteriour values (?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, re.getRepExtId());
            statement.setString(2, re.getRepExtDescription());
            statement.setInt(3, re.getRepExtPic());
            statement.setInt(4, re.getReportId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report exterior - saveReportExt");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    //saving a new report of a room in DB-Report_Room table
    public boolean saveReportRoom(ReportRoom rr, Connection con) {
        String SQLString = "insert into Report_Room values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, rr.getRepRoomId());
            statement.setString(2, rr.getRoomName());
            statement.setInt(3, rr.getReportId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room - saveReportRoom");
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Saving a new report for damage in a room in DB-Report_Room_Damage table
    boolean saveReportRoomDamage(ReportRoomDamage rrd, Connection con) {
        String SQLString = "insert into Report_Room_Damage values (?,?,?,?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(rrd.getDamageTime());
            statement.setInt(1, rrd.getRepRoomDmgId());
            statement.setDate(2,date);
            statement.setString(3, rrd.getPlace());
            statement.setString(4, rrd.getWhatHappened());
            statement.setString(5, rrd.getWhatIsRepaired());
            statement.setString(6, rrd.getDamageType());
            statement.setInt(7, rrd.getRepRoomId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room damage - saveReportRoomDamage");
            System.out.println(e.getMessage());
            return false;
        }
    }

    //saving a new report for room's interior in DB-Report_Room_Interiour table
    boolean saveReportInterior(ReportRoomInterior ri, Connection con) {
        String SQLString = "insert into Report_Room_Interiour values (?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ri.getRepRoomIntId());
            statement.setString(2,ri.getRepRoomIntName());
            statement.setString(3,ri.getRemark());
            statement.setInt(4,ri.getRepRoomId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room interior - saveReportInterior");
            System.out.println(e.getMessage());
            return false;
        }
    }

    //saving a new room report recommendations in DB-Report_Room_Recommendations table
    boolean saveReportRoomRec(ReportRoomRecommendation rrr, Connection con) {
        String SQLString = "insert into Report_Room_Recommendation values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, rrr.getRepRoomRecId());
            statement.setString(2,rrr.getRecommendation());
            statement.setInt(3,rrr.getRepRoomId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room recommendation - saveReportRecommendation");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    
}
