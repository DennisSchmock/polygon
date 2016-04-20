
package Domain;

import java.sql.Date;

/**
 * This class will be handling the orders
 * @author CJS  
 */
public class Order {
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

  
    public Order(Date orderDate,String serviceDescription, String problemStatement,int orderStatus, int customerId, int buildingId) {
        this.orderDate = orderDate;
        this.serviceDescription = serviceDescription;
        this.problemStatement = problemStatement;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.buildingId = buildingId;
    }
     public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    
    public String getStatDesc() {
        return statDesc;
    }

    public void setStatDesc(String statDesc) {
        this.statDesc = statDesc;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
    
    
}
