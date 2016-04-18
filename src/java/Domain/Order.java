
package Domain;

/**
 * This class will be handling the orders
 * @author CJS  
 */
public class Order {
    private int orderNumber;
    private String serviceDescription;
    private String problemStatement;
    private String orderStatus;
    private int customerId;
    private int buildingId;

    /**
     *
     * @param orderNumber auto-incremented number when creating an order
     * @param serviceDescription services that Polygon offers
     * @param problemStatement describes the problem
     * @param orderStatus status of the order
     * @param customerId customer ID that will reference to the customer
     * @param buildingId building ID that will reference to which building
     */
    public Order(int orderNumber, String serviceDescription, String problemStatement, String orderStatus, int customerId, int buildingId) {
        this.orderNumber = orderNumber;
        this.serviceDescription = serviceDescription;
        this.problemStatement = problemStatement;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.buildingId = buildingId;
    }

    public Order(String serviceDescription, String problemStatement, String orderStatus, int customerId, int buildingId) {
        this.serviceDescription = serviceDescription;
        this.problemStatement = problemStatement;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.buildingId = buildingId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
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
