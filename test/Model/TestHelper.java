/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Contact;
import Domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Bruger
 */
public class TestHelper {
    public Customer getCustomer(int id, Connection con){
        String SQLString = "select * from customer where customer_id=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, id);  
            ResultSet rs = statement.executeQuery();
        
                Customer c = new Customer(
                rs.getString("companyname"),
                rs.getString("contactperson"),
                rs.getString("email"),
                rs.getString("street"),
                rs.getInt("streetnumber"),
                rs.getInt("cvr"),
                rs.getInt("zipcode"),
                "city",
                rs.getString("phone"));
                return c;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
