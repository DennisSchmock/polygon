/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author dennisschmock
 */
public class User {

   

    private String userName;
    private String password;
    private int customerid;
    private String fName;
    private String lName;
    private String email;
    private String phone;
    private String role;
    private String companyName;
   
    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

   
    
     public User(String userName, String password, int customerid, String fName, String lName, String email, String phone,String companyName) {
        this.userName = userName;
        this.password = password;
        this.customerid = customerid;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
    }

    PasswordHandler pwdHandler = new PasswordHandler();

    public boolean checkPassWord() {
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCustomerid() {
        return customerid;
    }

    /**
     * @return the fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the lName
     */
    public String getlName() {
        return lName;
    }

    /**
     * @param lName the lName to set
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
