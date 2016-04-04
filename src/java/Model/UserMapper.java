/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Customer;
import Domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author dennisschmock
 */
public class UserMapper {

    public void addUserToDB(User user, Connection con) {
        String SQLString = "insert into user (username,pwd) values (?,?)";
        try (
                PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
           

            //int rowsInserted = statement.executeUpdate();
            //ResultSet rs = statement.getGeneratedKeys();
            statement.execute();
        } catch (Exception e) {
            System.out.println("Failed at creating user");
            System.out.println(e.getMessage());
        }

    }

    public void editCustomer() {

    }

}
