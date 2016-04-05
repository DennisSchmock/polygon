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

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public User(String username, String password, int customerid) {
        this.userName = username;
        this.password = password;
        this.customerid = customerid;
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
}
