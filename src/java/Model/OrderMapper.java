package Model;

import Domain.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author CJS
 */
public class OrderMapper {
    
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
            
            o = new Order(
                    rs.getInt("order_number"),
                    rs.getDate("order_date"),
                    rs.getString("service_description"),
                    rs.getString("problem_statement"),
                    rs.getInt("order_status"),
                    rs.getInt("customer_id"),
                    rs.getInt("idbuilding"));
            return o;
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper-getOrder");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
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
}
