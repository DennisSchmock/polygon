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
        String SQLString = "insert into Report(ReportDate,BuildingId,Category_conclusion) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            Date date = java.sql.Date.valueOf(r.getDate());  //gets a String value of date and converts it to sql date
            //insert a tuple and set the values
            statement.setDate(1, date);
            statement.setInt(2, r.getBdgId());
            statement.setInt(3, r.getCategoryConclusion());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                r.setReportId(rs.getInt(1));//get the value of the autoincremented value in DB and sets it to the ID
            } 
        } catch (Exception e) {
            System.out.println("Fail in saving new report - saveNewReport");
            System.out.println(e.getMessage());
        }
        return r;
    }
    
    //saving a new room exterior report in DB-Report_Exterior table
    public void saveReportExt(ReportRoomExterior re, Connection con) {
        String SQLString = "insert into Report_Exteriour(Report_Ext_Description, Report_Ext_Pic,Report) values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, re.getRepExtDescription());
            statement.setInt(2, re.getRepExtPic());
            statement.setInt(3, re.getReportId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                re.setRepExtId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report exterior - saveReportExt");
            System.out.println(e.getMessage());
        }
    }
    
    //saving a new report of a room in DB-Report_Room table
    public void saveReportRoom(ReportRoom rr, Connection con) {
        String SQLString = "insert into Report_Room(Room_Name,Report) values (?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, rr.getRoomName());
            statement.setInt(2, rr.getReportId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                rr.setRepRoomId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room - saveReportRoom");
            System.out.println(e.getMessage());
        }
    }

    //Saving a new report for damage in a room in DB-Report_Room_Damage table
    public void saveReportRoomDamage(ReportRoomDamage rrd, Connection con) {
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
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                rrd.setRepRoomDmgId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room damage - saveReportRoomDamage");
            System.out.println(e.getMessage());
        }
    }

    //saving a new report for room's interior in DB-Report_Room_Interiour table
    public void saveReportInterior(ReportRoomInterior ri, Connection con) {
        String SQLString = "insert into Report_Room_Interiour(Report_Room_Interiour_Name,Remark,Report_Room)"
                + " values (?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,ri.getRepRoomIntName());
            statement.setString(2,ri.getRemark());
            statement.setInt(3,ri.getRepRoomId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                ri.setRepRoomIntId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room interior - saveReportInterior");
            System.out.println(e.getMessage());
        }
    }

    //saving a new room report recommendations in DB-Report_Room_Recommendations table
    public void saveReportRoomRec(ReportRoomRecommendation rrr, Connection con) {
        String SQLString = "insert into Report_Room_Recommendation(Recommendation,Report_Room) values (?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,rrr.getRecommendation());
            statement.setInt(2,rrr.getRepRoomId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()){
                rrr.setRepRoomRecId(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving report room recommendation - saveReportRecommendation");
            System.out.println(e.getMessage());
        }
    }
    
    //Getting data from the DB
    public Report getReport(int id, Connection con) {
        
        String SQLString = "select * from Report where Report_Id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                return null;
            Report r = new Report(
                rs.getInt("Report_Id"),
                rs.getDate("ReportDate").toString(),
                rs.getInt("BuildingId"),
                rs.getInt("Category_conclusion"));
            return r;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReport");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ReportRoomExterior getReportExt(int id, Connection con) {
        
        String SQLString = "select * from Report_Exteriour where Report_Ext_Id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                return null;
            ReportRoomExterior re = new ReportRoomExterior(
                rs.getInt("Report_Ext_Id"),
                rs.getString("Report_Ext_Description"),
                rs.getInt("Report_Ext_Pic"),
                rs.getInt("Report"));
            return re;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportExt");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ReportRoom getReportRoom(int id, Connection con) {
        
        String SQLString = "select * from Report_Room where Report_Room_Id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                return null;
            ReportRoom rr = new ReportRoom(
                rs.getInt("Report_Room_Id"),
                rs.getString("Room_Name"),
                rs.getInt("Report"));
            return rr;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRoom");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ReportRoomDamage getReportDamage(int id, Connection con) {
        
        String SQLString = "select * from Report_Room_Damage where Report_Room_Damage_Id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                return null;
            ReportRoomDamage rd = new ReportRoomDamage(
                rs.getInt("Report_Room_Damage_Id"),
                rs.getDate("Damage_Time").toString(),
                rs.getString("Place"),
                rs.getString("WhatHappened"),
                rs.getString("WhatIsRepaired"),
                rs.getString("DamageType"),
                rs.getInt("Report_Room"));
            return rd;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportDamage");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ReportRoomInterior getReportInt(int id, Connection con) {
        
        String SQLString = "select * from Report_Room_Interiour where Report_Room_Interiour_Id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                return null;
            ReportRoomInterior ri = new ReportRoomInterior(
                rs.getInt("Report_Room_Interiour_Id"),
                rs.getString("Report_Room_Interiour_Name"),
                rs.getString("Remark"),
                rs.getInt("Report_Room"));
            return ri;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportInt");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ReportRoomRecommendation getReportRec(int id, Connection con) {
        
        String SQLString = "select * from Report_Room_Recommendation where Report_Room_Recommendation_Id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                return null;
            ReportRoomRecommendation rrr = new ReportRoomRecommendation(
                rs.getInt("Report_Room_Recommendation_Id"),
                rs.getString("Recommendation"),
                rs.getInt("Report_Room"));
            return rrr;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getReportRec");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //method that will take all the Report_Exteriour details in a certain Report
    public ArrayList<ReportRoomExterior> getListOfExt(int id, Connection con){
        String SQLString = "select * from Report_Exteriour where Report=?";
        ArrayList<ReportRoomExterior> listOfExt = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ReportRoomExterior re = new ReportRoomExterior(
                rs.getInt("Report_Ext_Id"),
                rs.getString("Report_Ext_Description"),
                rs.getInt("Report_Ext_Pic"),
                rs.getInt("Report"));
                listOfExt.add(re);
            }
            return listOfExt;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfExt");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<ReportRoom> getListOfReportRoom(int id, Connection con){
        String SQLString = "select * from Report_Room where Report=?";
        ArrayList<ReportRoom> listOfRepRm = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ReportRoom rr = new ReportRoom(
                rs.getInt("Report_Room_Id"),
                rs.getString("Room_Name"),
                rs.getInt("Report"));
                listOfRepRm.add(rr);
            }
            return listOfRepRm;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfReportRoom");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<ReportRoomDamage> getListOfDamages(int id, Connection con){
        String SQLString = "select * from Report_Room_Damage where Report_Room=?";
        ArrayList<ReportRoomDamage> listOfDmg = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ReportRoomDamage rd = new ReportRoomDamage(
                rs.getInt("Report_Room_Damage_Id"),
                rs.getDate("Damage_Time").toString(),
                rs.getString("Place"),
                rs.getString("WhatHappened"),
                rs.getString("WhatIsRepaired"),
                rs.getString("DamageType"),
                rs.getInt("Report_Room"));
                listOfDmg.add(rd);
            }
            return listOfDmg;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfDamages");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<ReportRoomInterior> getListOfInt(int id, Connection con){
        String SQLString = "select * from Report_Room_Interiour where Report_Room=?";
        ArrayList<ReportRoomInterior> listOfInt = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ReportRoomInterior ri = new ReportRoomInterior(
                rs.getInt("Report_Room_Interiour_Id"),
                rs.getString("Report_Room_Interiour_Name"),
                rs.getString("Remark"),
                rs.getInt("Report_Room"));
                listOfInt.add(ri);
            }
            return listOfInt;
        } catch (Exception e) {
            System.out.println("Fail in ReportMapper-getListOfInt");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<ReportRoomRecommendation> getListOfRec(int id, Connection con){
        String SQLString = "select * from Report_Room_Recommendation where Report_Room=?";
        ArrayList<ReportRoomRecommendation> listOfRec = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                ReportRoomRecommendation rrr = new ReportRoomRecommendation(
                rs.getInt("Report_Room_Recommendation_Id"),
                rs.getString("Recommendation"),
                rs.getInt("Report_Room"));
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
