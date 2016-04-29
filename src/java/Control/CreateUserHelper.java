/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.Customer;
import Domain.DomainFacade;
import Domain.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dennis Schmock
 */
class CreateUserHelper {

    //Customer customer;

    public void createNewCustomer(DomainFacade df, HttpSession session, HttpServletRequest request) {
        String companyName = request.getParameter("companyname");
        String contactPerson = request.getParameter("contactperson");
        String street = request.getParameter("street");
        String streetNumber = (request.getParameter("streetnumber"));
        String email = request.getParameter("email");
        String CVR = (request.getParameter("cvr"));
        String city = request.getParameter("city");
        String phoneNumber = request.getParameter("phone");
        int zip = Integer.parseInt(request.getParameter("zip"));
        df.createNewCustomer(companyName, contactPerson, email, street, streetNumber, CVR, zip, city, phoneNumber);
   
    }

    public void createNewCustomer(HttpServletRequest request, DomainFacade df, HttpSession session, FrontControl frontControl) {
        createNewCustomer(df, session, request);
    }

    /**
     * The purpose of this method, is to validate the user login.
     *
     * @param df
     * @param request
     * @param response
     */
    public void login(DomainFacade df, HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getParameter("username");
        String pwd = (String) request.getParameter("pwd");
        //this is for order request when a customer loggedin
        //c = df.getCustomerAfterLogIn(username);
        request.getSession().setAttribute("customer", df.getCustomerAfterLogIn(username));
        if (df.logUserIn(username, pwd)) {
            request.getSession().setAttribute("loggedin", true);
            request.getSession().setAttribute("userrole", "user");
            User user = df.loadUser(username);
            request.getSession().setAttribute("user", user);
        } else {
            request.getSession().setAttribute("loggedin", false);
        }
    }

    /**
     * Method that sets up, for the emp which building he has to create an
     * report for. Needs to load all the customers.
     *
     * @param request
     * @param sessionObj
     * @param df
     */
    public void chooseCustomer(HttpSession sessionObj, DomainFacade df) {
        List<Customer> allCustomers = df.loadAllCustomers();
        sessionObj.setAttribute("allCustomers", allCustomers);
    }

    /**
     * Method for logging an user in. 
     *
     * @param df
     * @param request
     * @param response
     */
    public void emplogin(DomainFacade df, HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getParameter("username");
        String pwd = (String) request.getParameter("pwd");
        if (df.logEmpUserIn(username, pwd)) {
          
            request.getSession().setAttribute("loggedin", true);
            User user = df.loadEmpUser(username); // not implemented!
            request.getSession().setAttribute("user", user);
        } else {
            request.getSession().setAttribute("loggedin", false);
        }
    }
    
}
