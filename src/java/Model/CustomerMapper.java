/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author dennisschmock
 */
public class CustomerMapper {

    public void addCustomerToDB(Customer cus, Connection con) {
        String SQLString = "insert into customer (companyname,street,streetnumber,zipcode,phone,email,contactperson) values (?,?,?,?,?,?,?)";
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

            //int rowsInserted = statement.executeUpdate();
            //ResultSet rs = statement.getGeneratedKeys();
            statement.execute();
        } catch (Exception e) {
            System.out.println("Failed at creating customer");
            System.out.println(e.getMessage());
        }

    }

    public void editCustomer() {

    }

}
