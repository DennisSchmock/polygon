package Model;

import Domain.Building;
import Domain.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author CJS
 */
public class OrderMapper {
    
        BuildingMapper bm = new BuildingMapper();
    /**
     * This method will save the new order in the database
     * @param o an object that handles the new order
     * @param con connection
     */
    public void addNewOrder(Order o, Connection con){
            String SQLString = "insert into orders(order_date,service_description,problem_statement,order_status,customer_id,idbuilding) values (?,?,?,?,?,?)";
            try (PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
                //insert a tuple and set the values
                statement.setDate(1, o.getOrderDate());
                statement.setString(2, o.getServiceDescription());
                statement.setString(3, o.getProblemStatement());
                statement.setInt(4, o.getOrderStatus());
                statement.setInt(5, o.getCustomerId());
                statement.setInt(6, o.getBuildingId());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    o.setOrderNumber(rs.getInt(1));
                }
            } catch (Exception e) {
                System.out.println("Fail in saving new order - addNewOrder");
                System.out.println(e.getMessage());
            }
    }
    
    /**
     * This method will get the order data from the database
     * @param orderNumber will be reference on which order should be taken 
     * @param con connection
     * @return an Order
     */
    public Order getOrder(int orderNumber, Connection con) {
        Order o;
        String SQLString = "select * from orders where order_number=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, orderNumber);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            
                int on = rs.getInt("order_number");
                Date d = rs.getDate("order_date");
                String sd = rs.getString("service_description");
                String ps = rs.getString("problem_statement");
                int os = rs.getInt("order_status");
                int c = rs.getInt("customer_id");
                int bdgId = rs.getInt("idbuilding");
                String statDesc = getOrderStatus(os, con);
                Building b = bm.getBuilding(bdgId, con);
                o = new Order(on,d,sd,ps,os,c,bdgId,statDesc,b.getBuildingName());
            
            return o;
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper-getOrder");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * This method will get the status description of an order
     * @param stat a status id
     * @param con connection
     * @return status description 
     */
    public String getOrderStatus(int stat, Connection con) {
        String statDesc = "";
        String SQLString = "select * from order_status where order_status=?";
        try (PreparedStatement statement = con.prepareStatement(SQLString)) {
            statement.setInt(1, stat);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                statDesc = rs.getString("status_description");
            }
            
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper-getOrderStatus");
            System.out.println(e.getMessage());
            return null;
        }
        return statDesc;
    }
    
    /**
     * This method will get all the orders of a customer from the database
     * @param custId customer Id
     * @param con connection
     * @return list of Orders
     */
    public ArrayList<Order> getListOfOrders(int custId, Connection con){
        String sql = "select * from orders where customer_id = ?";
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, custId);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                int on = rs.getInt("order_number");
                Date d = rs.getDate("order_date");
                String sd = rs.getString("service_description");
                String ps = rs.getString("problem_statement");
                int os = rs.getInt("order_status");
                int bdgId = rs.getInt("idbuilding");
                String statDesc = getOrderStatus(os, con);
                Building b = bm.getBuilding(bdgId, con);
                Order o = new Order(on,d,sd,ps,os,custId,bdgId,statDesc,b.getBuildingName());
                orderList.add(o);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error in SQL OrderMapper-getListOfOrders " + ex);
            
        }
       
        return orderList;
    }
    
    /**
     * This method will get all the orders from the database
     * @param con connection
     * @return list of all Orders
     */
    public ArrayList<Order> getListOfAllOrders(Connection con){
        String sql = "select * from orders";
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                int on = rs.getInt("order_number");
                Date d = rs.getDate("order_date");
                String sd = rs.getString("service_description");
                String ps = rs.getString("problem_statement");
                int os = rs.getInt("order_status");
                int c = rs.getInt("customer_id");
                int bdgId = rs.getInt("idbuilding");
                String statDesc = getOrderStatus(os, con);
                Building b = bm.getBuilding(bdgId, con);
                Order o = new Order(on,d,sd,ps,os,c,bdgId,statDesc,b.getBuildingName());
                orderList.add(o);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error in SQL OrderMapper-getAllOrders " + ex);
            
        }
        return orderList;
    }

    /**
     * This will update the status in the orders table in DB
     * @param orderNumber order number
     * @param newStat holds the value to update in the order status
     * @param con connection
     */
    public void updateOrder(int orderNumber, int newStat, Connection con) {
        String SQLString
                = "update orders set order_status = ? where order_number = ?";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, newStat);
            statement.setInt(2, orderNumber);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in updateStatus");
            System.out.println(e.getMessage());
        } 
    }
    
    
}
