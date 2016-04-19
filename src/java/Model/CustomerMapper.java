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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennisschmock
 */
public class CustomerMapper {

    /**
     * This method takes the 
     * @param cus the customer object
     * @param con the connection to the database
     */
    public void addCustomerToDB(Customer cus, Connection con) {
        String SQLString = "insert into customer (companyname,street,streetnumber,zipcode,phone,email,contactperson,cvr) values (?,?,?,?,?,?,?,?)";
        try (
                PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cus.getCompanyName());
            statement.setString(2, cus.getStreet());
            statement.setString(3, cus.getStreetNumber());
            statement.setInt(4, cus.getZip());
            statement.setString(5, cus.getPhoneNumber());
            statement.setString(6, cus.getCusMail());
            statement.setString(7, cus.getContactPerson());
            statement.setString(8, cus.getCusCVR());

            //int rowsInserted = statement.executeUpdate();
            //ResultSet rs = statement.getGeneratedKeys();
            statement.execute();
        } catch (Exception e) {
            System.out.println("Failed at creating customer");
            System.out.println(e.getMessage());
        }

    }
    
    
    
    

    /**
     *
     * @param c new Contact
     * @param con
     * save a new contact to DB
     */
    public void saveContact(Contact c, Connection con){
        String SQLString = "insert into contact(name,email,tel,customerID) values (?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, c.getName());
            statement.setString(2, c.getEmail());
            statement.setString(3,c.getTelNum());
            statement.setInt(4,c.getCustID());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                c.setContactID(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Fail in saving Contact - saveContact");
            System.out.println(e.getMessage());
        }
    }
    
    /**
     *
     * @param id   the customer ID
     * @param con  connection to the DB
     * @return  a list of contacts of a certain customer
     */
    public ArrayList<Contact> getListOfContacts(int id, Connection con){
        String SQLString = "select * from contact where customerID=?";
        ArrayList<Contact> listOfContacts = new ArrayList();
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Contact c = new Contact(
                rs.getInt("contactID"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("tel"),
                rs.getInt("customerID"));
                listOfContacts.add(c);
            }
            return listOfContacts;
        } catch (Exception e) {
            System.out.println("Fail in getListOfContacts");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void editCustomer() {

    }

    public Customer getCustomer(int id, Connection con){
        Customer c=null;
        String SQLString = "select * from customer where customer_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                c = new Customer(
                rs.getInt("customer_id"),
                rs.getString("companyname"),
                rs.getString("contactperson"),
                rs.getString("email"),
                rs.getString("street"),
                rs.getString("streetnumber"),
                rs.getString("cvr"),
                rs.getInt("zipcode"),
                rs.getString("phone"));
                return c;
            }
            
        } catch (Exception e) {
            System.out.println("caught an exception");
            System.out.println(e.getMessage());
            return null;
        }
        return c;
    }

    /**
     * This method will get the customer data based on the username after log in
     * @param username username used by the user when logging in
     * @param con connection
     * @return
     */
    public Customer getCustomerAfterLogIn(String username, Connection con){
        Customer c=null;
        String SQLString = "select customer_id from customer_user where username=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setString(1, username);  
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                c = getCustomer(rs.getInt("customer_id"), con);
                return c;
            }
            
        } catch (Exception e) {
            System.out.println("caught an exception");
            System.out.println(e.getMessage());
            return null;
        }
        return c;
    }
    
    public Contact getContact(int custID, Connection con) {
        Contact c=null;
        String SQLString = "select * from contact where customerID=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, custID);  
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                c = new Contact(
                rs.getInt("contactID"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("tel"),
                rs.getInt("customerID"));
                return c;
            }
            
        } catch (Exception e) {
            System.out.println("getContact-caught exception");
            System.out.println(e.getMessage());
            return null;
        }
        return c;}

    /**
     * Retrives the list of all the Customers in the database Polygon
     * @param con Connection to database
     * @return Returs an list of All customers in the database
     */
    public List<Customer> getAllCustomersCM(Connection con) {
        String sql = "select * from customer";
        List<Customer> allCus = new ArrayList<>();
        try {
            PreparedStatement statment = con.prepareStatement(sql);
            ResultSet rs = statment.executeQuery();
            
            while(rs.next()){
                Customer temp = new Customer
                (rs.getString("companyname"),
                rs.getString("contactperson"),
                rs.getString("email"),
                rs.getString("street"),
                rs.getString("streetnumber"),
                rs.getString("cvr"),
                rs.getInt("zipcode"), 
                "NOT IMPLEMETED", //Getting a city based on zip 
                rs.getString("phone"));
                temp.setCustomerId(rs.getInt("customer_id"));
                allCus.add(temp);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error in SQL CustomerMapper " + ex);
        }
       
        return allCus;
    }


}
