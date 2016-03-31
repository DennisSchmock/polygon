/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.List;

/**
 *
 * @author CJS
 */
public class Customer {
    private String companyName;
    private String contactPerson;
    private String cusMail;
    private String street;
    private int streetNumber;
    private int cusCVR;
    private int zip;
    private String city;
    private String phoneNumber;

    public Customer(String companyName, String contactPerson, String cusMail, String street, int streetNumber, int cusCVR, int zip, String city, String phoneNumber) {
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.cusMail = cusMail;
        this.street = street;
        this.streetNumber = streetNumber;
        this.cusCVR = cusCVR;
        this.zip = zip;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String companyName, String contactPerson) {
        this.companyName = companyName;
        this.contactPerson = contactPerson;
    }
    
    
    
    
    
    private List<Building> buildings;
    private List<Report> reports;

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @return the contactPerson
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * @return the cusMail
     */
    public String getCusMail() {
        return cusMail;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return the streetNumber
     */
    public int getStreetNumber() {
        return streetNumber;
    }

    /**
     * @return the cusCVR
     */
    public int getCusCVR() {
        return cusCVR;
    }

    /**
     * @return the zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return the buildings
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * @return the reports
     */
    public List<Report> getReports() {
        return reports;
    }
    
    
    
   
}
