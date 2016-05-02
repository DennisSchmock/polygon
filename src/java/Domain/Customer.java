/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dennis Schmock
 */
public class Customer implements Serializable{
    private int customerId;
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
    private List<Building> buildings;
    private ArrayList<Order> listOfOrders;

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

    /**
     *
     * @param customerId
     * @param companyName
     * @param contactPerson
     * @param cusMail
     * @param street
     * @param streetNumber
     * @param cusCVR
     * @param zip
     * @param phoneNumber
     */
    public Customer(int customerId, String companyName, String contactPerson, String cusMail, String street, String streetNumber, String cusCVR, int zip, String phoneNumber) {
        this.customerId = customerId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.cusMail = cusMail;
        this.street = street;
        this.streetNumber = streetNumber;
        this.cusCVR = cusCVR;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }
    
    

    /**
     *
     * @param companyName
     * @param contactPerson
     */
    public Customer(String companyName, String contactPerson) {
        this.companyName = companyName;
        this.contactPerson = contactPerson;
    }
    

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
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    

    /**
     *
     * @return the list of contacts
     */
    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    /**
     *
     * @param listOfUsers
     */
    public void setListOfUsers(ArrayList<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }


    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @param contactPerson the contactPerson to set
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * @param cusMail the cusMail to set
     */
    public void setCusMail(String cusMail) {
        this.cusMail = cusMail;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @param streetNumber the streetNumber to set
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * @param cusCVR the cusCVR to set
     */
    public void setCusCVR(String cusCVR) {
        this.cusCVR = cusCVR;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @param buildings the buildings to set
     */
    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    /**
     *
     * @return
     */
    public ArrayList<Order> getListOfOrders() {
        return listOfOrders;
    }

    /**
     *
     * @param listOfOrders
     */
    public void setListOfOrders(ArrayList<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

   
}
