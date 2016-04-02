/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author CJS
 */
public class Report {
    private int reportId;
    private String date;
    private ArrayList<ReportRoom> listOfRepRoom;
    private int bdgId;
    private int categoryConclusion;

    /**
     *
     * @param reportId  report number
     * @param date   date
     * @param bdgId    building's ID
     * @param catCon  category conclusion
     */
    public Report(int reportId, String date, int bdgId, int catCon) {
        this.reportId = reportId;
        this.date = date;
        this.bdgId = bdgId;
        this.categoryConclusion = catCon;
    }
    
    public Report( String date, int bdgId, int catCon) {
        this.date = date;
        this.bdgId = bdgId;
        this.categoryConclusion = catCon;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
    
    public int getReportId() {
        return reportId;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<ReportRoom> getListOfRepRoom() {
        return listOfRepRoom;
    }

    public int getBdgId() {
        return bdgId;
    }
  
    public void setListOfRepRoom(ArrayList<ReportRoom> listOfRepRoom) {
        this.listOfRepRoom = listOfRepRoom;
    }

    public int getCategoryConclusion() {
        return categoryConclusion;
    }

}
