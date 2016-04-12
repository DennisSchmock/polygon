/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author dennisschmock
 */
public class Building implements Serializable {
    private int bdgId;
    private String buildingName;
    private String streetAddress;
    private String streetNumber; // its a string because it can take a number like 12b
    private int zipCode;
    private int buildingYear;
    private double buildingSize;
    private String useOfBuilding;
    private int building_pic;
    private ArrayList<Report> listOfReports;
    private ArrayList<BuildingFloor> listOfFloors;
    private int custId;

    public Building(String buildingName, String streetAddress, String streetNumber, int zipCode, int buildingYear, double buildingSize, String useOfBuilding) {
        this.buildingName = buildingName;
        this.streetAddress = streetAddress;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.buildingYear = buildingYear;
        this.buildingSize = buildingSize;
        this.useOfBuilding = useOfBuilding;
    }

    public Building(int bdgId, String buildingName,double buildingSize, String streetAddress, String streetNumber, int buildingYear, int zipCode,   int building_pic,String useOfBuilding, int custId) {
        this.bdgId = bdgId;
        this.buildingName = buildingName;
        this.streetAddress = streetAddress;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.buildingYear = buildingYear;
        this.buildingSize = buildingSize;
        this.useOfBuilding = useOfBuilding;
        this.building_pic = building_pic;
        this.custId = custId;
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

    public int getBuilding_pic() {
        return building_pic;
    }

    public void setBuilding_pic(int building_pic) {
        this.building_pic = building_pic;
    }

    @Override
    public String toString() {
        return "Building{" + "bdgId=" + bdgId + ", buildingName=" + buildingName + ", streetAddress=" + streetAddress + ", streetNumber=" + streetNumber + ", zipCode=" + zipCode + ", buildingYear=" + buildingYear + ", buildingSize=" + buildingSize + ", useOfBuilding=" + useOfBuilding + ", listOfReports=" + listOfReports + ", custId=" + custId + '}';
    }

    public ArrayList<BuildingFloor> getListOfFloors() {
        return listOfFloors;
    }

    public void setListOfFloors(ArrayList<BuildingFloor> listOfFloors) {
        this.listOfFloors = listOfFloors;
    }

    
    
    
}
