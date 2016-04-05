/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.User;
import java.sql.Connection;
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

    /**
     * For saving a user to the database
     * @param user User object to get put into the database
     * @param con
     */
    public void addUserToDB(User user, Connection con) {
        String SQLString = "insert into user (username,pwd) values (?,?)";
        try (
                PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {        
            statement.execute();
        } catch (Exception e) {
            System.out.println("Failed at creating user");
            System.out.println(e.getMessage());
        }

    }

    /**
     * Method for validation a user
     * @param userName 
     * @param pwd the users password
     * @param con the DB-connection
     * @return
     */
    public boolean validateUser(String userName, String pwd, Connection con) {
        try {

            String sqlString = "select pwd from customer_user where username = ?;";
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

    /**
     * Method for retrieving a user from the Database and returning it as a user Object.
     * @param userName
     * @param con DB-connection
     * @return a User Object
     */
    public User getUser(String userName, Connection con) {
        User user = null;

        try {

            String sqlString = "SELECT username,customer_user.customer_id,pwd,fname,lname,"
                    + "customer_user.email,customer_user.phone, companyname "
                    + "FROM customer_user,customer where username = ? and "
                    + "customer_user.customer_id = customer.customer_id;";
            PreparedStatement stmt = con.prepareStatement(sqlString);

            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String companyname = rs.getString("companyname");
                String name = rs.getString("username");
                String fName = rs.getString("fname");
                String pwd = rs.getString("pwd");
                String lName = rs.getString("lname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                int customerId = rs.getInt("customer_id");
                user = new User(userName, pwd, customerId, fName, lName, email, phone, companyname);
                System.out.println(name + pwd + customerId);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
