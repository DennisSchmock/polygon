/*
 * Contact data handler
 */
package Domain;

/**
 *
 * @author CJS
 */
public class Contact {
    private int contactID;
    private String name;
    private String email;
    private String telNum;
    private int custID;

    /**
     *
     * @param contactID the contact ID
     * @param name  the name of a contact person
     * @param email email of a contact person
     * @param telNum  contact number
     * @param custID  customer ID that will reference to the customer
     */
    public Contact(int contactID, String name, String email, String telNum, int custID) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
        this.telNum = telNum;
        this.custID = custID;
    }

    /**
     *
     * @param name
     * @param email
     * @param telNum
     * @param custID
     */
    public Contact(String name, String email, String telNum, int custID) {
        this.name = name;
        this.email = email;
        this.telNum = telNum;
        this.custID = custID;
    }
    
    /**
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return
     */
    public String getTelNum() {
        return telNum;
    }

    /**
     *
     * @return
     */
    public int getCustID() {
        return custID;
    }
    
}
