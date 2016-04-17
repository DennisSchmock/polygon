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
    String SQLString = "insert into orders (service_description,problem_statement,order_status,customer_id,idbuilding) values (?,?,?,?,?)";
        try {
            con.setAutoCommit(false);
            PreparedStatement statement
                    = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS);
            //insert a tuple and set the values
            statement.setString(1, o.getServiceDescription());
            statement.setString(2, o.getProblemStatement());
            statement.setString(3, o.getOrderStatus());
            statement.setInt(4, o.getCustomerId());
            statement.setInt(5,o.getBuildingId());                  
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {   
                o.setOrderNumber(rs.getInt(1));
                System.out.println("Order Number = " + rs.getInt(1));
            }
           
            con.commit();

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.out.println("Failed at rollingback" + ex);
            }
            System.out.println("Fail in saving new order - addNewOrder. Actions has been Rolledback");
            System.out.println(e);
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
                    rs.getString("service_description"),
                    rs.getString("problem_statement"),
                    rs.getString("order_status"),
                    rs.getInt("customer_id"),
                    rs.getInt("idbuilding"));
            return o;
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper-getOrder");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
