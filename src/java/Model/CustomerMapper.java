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
        String SQLString = "insert into Report values (?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
           
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
        } catch (Exception e) {
            System.out.println("Fail in saving new report - saveNewReport");
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public void editCustomer(){
        
    }
    
}
