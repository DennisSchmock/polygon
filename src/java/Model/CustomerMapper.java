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
import java.util.ArrayList;

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
            statement.setInt(3, cus.getStreetNumber());
            statement.setInt(4, cus.getZip());
            statement.setString(5, cus.getPhoneNumber());
            statement.setString(6, cus.getCusMail());
            statement.setString(7, cus.getContactPerson());
            statement.setInt(8, cus.getCusCVR());

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
            if (!rs.next()){
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

    Customer getCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
