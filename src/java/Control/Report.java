/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author CJS
 */
public class Report {
    private int reportNum;
    private String date;
    private String bdgName;
    private String bdgAddress;
    private int postNum;
    private int bdgYear;
    private double bdgSize;
    private String useOfBdg;
    private ArrayList<ReportRoom> listOfRepRoom;
    private int bdgId;

    /**
     *
     * @param reportNum  report number
     * @param date   date
     * @param bdgName  building name
     * @param bdgAddress   building address
     * @param postNum    post number
     * @param bdgYear    building build year
     * @param bdgSize   building size
     * @param useOfBdg   use of the building
     * @param bdgId    building's ID
     */
    public Report(int reportNum, String date, String bdgName,
            String bdgAddress, int postNum, int bdgYear, double bdgSize, 
            String useOfBdg, int bdgId) {
        this.reportNum = reportNum;
        this.date = date;
        this.bdgName = bdgName;
        this.bdgAddress = bdgAddress;
        this.postNum = postNum;
        this.bdgYear = bdgYear;
        this.bdgSize = bdgSize;
        this.useOfBdg = useOfBdg;
        this.bdgId = bdgId;
    }

    public int getReportNum() {
        return reportNum;
    }

    public String getDate() {
        return date;
    }

    public String getBdgName() {
        return bdgName;
    }

    public String getBdgAddress() {
        return bdgAddress;
    }

    public int getPostNum() {
        return postNum;
    }

    public int getBdgAge() {
        return bdgYear;
    }

    public double getBdgSize() {
        return bdgSize;
    }

    public String getUseOfBdg() {
        return useOfBdg;
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

    
}
