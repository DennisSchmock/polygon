/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Customer;
import Domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public boolean validateUser(String userName, String pwd, Connection con) {
        try {

            String sqlString = "select pwd from user where username = ?;";
            PreparedStatement stmt = con.prepareStatement(sqlString);

            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("pwd"));
                boolean validated = rs.getString("pwd").equals(pwd);
                return validated;
            }

            return false;
        } catch (SQLException ex) {
            Logger.getLogger(UserMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    User getUser(String userName, Connection con) {
        User user = null;
        String name = "";
        String pwd = "";
        int customerId;
        try {

            String sqlString = "select * from user where username = ?;";
            PreparedStatement stmt = con.prepareStatement(sqlString);

            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("username");
                pwd = rs.getString("pwd");
                customerId = rs.getInt("customerid");
                user = new User(name, pwd, customerId);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(UserMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
