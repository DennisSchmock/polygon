/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.ArrayList;
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
    private String streetNumber;
    private String cusCVR;
    private int zip;
    private String city;
    private String phoneNumber;
    private ArrayList<User> listOfUsers;

    /**
     *
     * @param companyName
     * @param contactPerson
     * @param cusMail
     * @param street
     * @param streetNumber
     * @param cusCVR
     * @param zip
     * @param city
     * @param phoneNumber
     */
    public Customer(String companyName, String contactPerson, String cusMail, String street, String streetNumber, String cusCVR, int zip, String city, String phoneNumber) {
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
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * @return the cusCVR
     */
    public String getCusCVR() {
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

    /**
     *
     * @return the list of contacts
     */
    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(ArrayList<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    @Override
    public String toString() {
        return "Customer{" + "companyName=" + companyName + ", contactPerson=" + contactPerson + ", cusMail=" + cusMail + ", street=" + street + ", streetNumber=" + streetNumber + ", cusCVR=" + cusCVR + ", zip=" + zip + ", city=" + city + ", phoneNumber=" + phoneNumber + ", listOfUsers=" + listOfUsers + ", buildings=" + buildings + ", reports=" + reports + '}';
    }
    
    
    
}
