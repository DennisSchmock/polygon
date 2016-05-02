
package Domain;

import java.sql.Date;
import java.util.Comparator;

/**
 * This class will be handling the orders
 * @author CJS  
 */
public class Order{
    private int orderNumber;
    private Date orderDate;
    private String serviceDescription;
    private String problemStatement;
    private int orderStatus;
    private int customerId;
    private int buildingId;
    private String buildingName;
    private String statDesc;

    /**
     *
     * @param orderNumber auto-incremented number when creating an order
     * @param orderDate date of Order
     * @param serviceDescription services that Polygon offers
     * @param problemStatement describes the problem
     * @param orderStatus status of the order
     * @param customerId customer ID that will reference to the customer
     * @param buildingId building ID that will reference to which building
     * @param statDesc status description
     * @param buildingName building name
     */
    public Order(int orderNumber, Date orderDate, String serviceDescription, String problemStatement, int orderStatus, int customerId, int buildingId, String statDesc, String buildingName) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.serviceDescription = serviceDescription;
        this.problemStatement = problemStatement;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.buildingId = buildingId;
        this.statDesc = statDesc;
        this.buildingName = buildingName;
    }

    /**
     *
     * @param orderDate
     * @param serviceDescription
     * @param problemStatement
     * @param orderStatus
     * @param customerId
     * @param buildingId
     */
    public Order(Date orderDate,String serviceDescription, String problemStatement,int orderStatus, int customerId, int buildingId) {
        this.orderDate = orderDate;
        this.serviceDescription = serviceDescription;
        this.problemStatement = problemStatement;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.buildingId = buildingId;
    }
    
     /**
     * This will sort the status of the orders
     */
    
    public static Comparator<Order> orderStat = new Comparator<Order>() {

	public int compare(Order o1, Order o2) {

	   int stat01 = o1.getOrderStatus();
	   int stat02 = o2.getOrderStatus();

	   /*For ascending order*/
	   return stat01-stat02;

   }};
    
    //Setters and getters below this point

    /**
     *
     * @return
     */
     public String getBuildingName() {
        return buildingName;
    }

    /**
     *
     * @param buildingName
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     *
     * @return
     */
    public String getStatDesc() {
        return statDesc;
    }

    /**
     *
     * @param statDesc
     */
    public void setStatDesc(String statDesc) {
        this.statDesc = statDesc;
    }
    
    /**
     *
     * @return
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     *
     * @param orderDate
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     *
     * @return
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     *
     * @param orderNumber
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     *
     * @return
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    /**
     *
     * @param serviceDescription
     */
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    /**
     *
     * @return
     */
    public String getProblemStatement() {
        return problemStatement;
    }

    /**
     *
     * @param problemStatement
     */
    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    /**
     *
     * @return
     */
    public int getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus
     */
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public int getBuildingId() {
        return buildingId;
    }

    /**
     *
     * @param buildingId
     */
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

//    @Override
//    public int compareTo(Object o) {
//        int compareStat = ((Order)o).getOrderStatus();
//        return compareStat-this.orderStatus;
//    }

   

}
