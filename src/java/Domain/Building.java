/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.ArrayList;

/**
 *
 * @author dennisschmock
 */
public class Building {
    private int bdgId;
    private String buildingName;
    private String streetAddress;
    private String streetNumber; // its a string because it can take a number like 12b
    private int zipCode;
    private int buildingYear;
    private double buildingSize;
    private String useOfBuilding;
    private ArrayList<Report> listOfReports;
    private int custId;

    public Building(String buildingName, String streetAddress, String streetNumber, int buildingYear, String useOfBuilding) {
        this.buildingName = buildingName;
        this.streetAddress = streetAddress;
        this.streetNumber = streetNumber;
        this.buildingYear = buildingYear;
        this.useOfBuilding = useOfBuilding;
    }
    
    

    public int getBdgId() {
        return bdgId;
    }

    public void setBdgId(int bdgId) {
        this.bdgId = bdgId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getBuildingYear() {
        return buildingYear;
    }

    public void setBuildingYear(int buildingYear) {
        this.buildingYear = buildingYear;
    }

    public double getBuildingSize() {
        return buildingSize;
    }

    public void setBuildingSize(double buildingSize) {
        this.buildingSize = buildingSize;
    }

    public String getUseOfBuilding() {
        return useOfBuilding;
    }

    public void setUseOfBuilding(String useOfBuilding) {
        this.useOfBuilding = useOfBuilding;
    }

    public ArrayList<Report> getListOfReports() {
        return listOfReports;
    }

    public void setListOfReports(ArrayList<Report> listOfReports) {
        this.listOfReports = listOfReports;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
  

}
