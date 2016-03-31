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
        String SQLString = "insert into Report(ReportDate,BuildingId,Category_conclusion) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(r.getDate());  //gets a String value of date and converts it to sql date
            //insert a tuple and set the values
            statement.setDate(1, date);
            statement.setInt(2, r.getBdgId());
            statement.setInt(3, r.getCategoryConclusion());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                r.setReportId(rs.getInt(1));
            } //get the value of the autoincremented value in DB and sets it to the ID
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving new report - saveNewReport");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    //saving a new room exterior report in DB-Report_Exterior table
    public boolean saveReportExt(ReportRoomExterior re, Connection con) {
        String SQLString = "insert into Report_Exteriour(Report_Ext_Description, Report_Ext_Pic,Report) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, re.getRepExtDescription());
            statement.setInt(2, re.getRepExtPic());
            statement.setInt(3, re.getReportId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                re.setRepExtId(rs.getInt(1));
            }
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report exterior - saveReportExt");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    //saving a new report of a room in DB-Report_Room table
    public boolean saveReportRoom(ReportRoom rr, Connection con) {
        String SQLString = "insert into Report_Room(Room_Name,Report) values (?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, rr.getRoomName());
            statement.setInt(2, rr.getReportId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                rr.setRepRoomId(rs.getInt(1));
            }
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room - saveReportRoom");
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Saving a new report for damage in a room in DB-Report_Room_Damage table
    boolean saveReportRoomDamage(ReportRoomDamage rrd, Connection con) {
        String SQLString = "insert into Report_Room_Damage (Damage_Time,Place,WhatHappened,WhatIsRepaired,DamageType,Report_Room)" 
                + " values (?,?,?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(rrd.getDamageTime());
            statement.setDate(1,date);
            statement.setString(2, rrd.getPlace());
            statement.setString(3, rrd.getWhatHappened());
            statement.setString(4, rrd.getWhatIsRepaired());
            statement.setString(5, rrd.getDamageType());
            statement.setInt(6, rrd.getRepRoomId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                rrd.setRepRoomDmgId(rs.getInt(1));
            }
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room damage - saveReportRoomDamage");
            System.out.println(e.getMessage());
            return false;
        }
    }

    //saving a new report for room's interior in DB-Report_Room_Interiour table
    boolean saveReportInterior(ReportRoomInterior ri, Connection con) {
        String SQLString = "insert into Report_Room_Interiour(Report_Room_Interiour_Name,Remark,Report_Room)"
                + " values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,ri.getRepRoomIntName());
            statement.setString(2,ri.getRemark());
            statement.setInt(3,ri.getRepRoomId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                ri.setRepRoomIntId(rs.getInt(1));
            }
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room interior - saveReportInterior");
            System.out.println(e.getMessage());
            return false;
        }
    }

    //saving a new room report recommendations in DB-Report_Room_Recommendations table
    boolean saveReportRoomRec(ReportRoomRecommendation rrr, Connection con) {
        String SQLString = "insert into Report_Room_Recommendation(Recommendation,Report_Room) values (?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,rrr.getRecommendation());
            statement.setInt(2,rrr.getRepRoomId());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                rrr.setRepRoomRecId(rs.getInt(1));
            }
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving report room recommendation - saveReportRecommendation");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    
}
