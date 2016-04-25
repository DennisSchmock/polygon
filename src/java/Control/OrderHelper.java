/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.Building;
import Domain.Customer;
import Domain.DomainFacade;
import Domain.Exceptions.PolygonException;
import Domain.Order;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author danie
 */
public class OrderHelper {
    FrontControl fc;
    BuildingHelper bh;
    CreateUserHelper cuh;
    
    

    @EJB
    private MailSenderBean mailSender;
    public OrderHelper(FrontControl fc, BuildingHelper bh, CreateUserHelper cuh){
        this.bh=bh;
        this.cuh = cuh;
        this.fc=fc;
    }
    /**
     * This method will take the values of the attributes for saving a new order
     * @param request
     * @param sessionObj
     * @param df
     */
    public void saveOrder(HttpServletRequest request, HttpSession sessionObj, DomainFacade df, FrontControl frontControl) {
        String serviceDesc = (String) request.getParameter("services");
        String otherDesc = (String) request.getParameter("otherservice");
        if (serviceDesc.equals("other")) {
            serviceDesc = otherDesc;
        }
        String problemStmt = (String) request.getParameter("problemstatement");
        int orderStat = 1;
        sessionObj.setAttribute("orderStatus", orderStat);
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Building b = (Building) request.getSession().getAttribute("building");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Order o = new Order(date, serviceDesc, problemStmt, orderStat, customer.getCustomerId(), b.getBdgId());
        sessionObj.setAttribute("selectedOrder", o);
        df.addNewOrder(o);
        sendOrderEmail(request);
    }

    /**
     * This will get all the orders of all the customers of polygon and will be sorted by order status
     * @param sessionObj
     * @param df
     */
    void loadAllOrders(HttpSession sessionObj, DomainFacade df) throws PolygonException {
        ArrayList<Order> listOfAllOrders = df.getListOfAllOrders();
        Collections.sort(listOfAllOrders, Order.orderStat);
        sessionObj.setAttribute("listOfOrders", listOfAllOrders);
    }

    /**
     * This will get all the customer's orders
     * @param sessionObj
     * @param df
     */
    void loadCustomerOrders(HttpSession sessionObj, DomainFacade df, FrontControl frontControl) throws PolygonException {
        Customer customer = (Customer) sessionObj.getAttribute("customer");
        ArrayList<Order> listOfOrders = df.getListOfOrders(customer.getCustomerId());
        customer.setListOfOrders(listOfOrders);
        sessionObj.setAttribute("listOfOrders", listOfOrders);
        sessionObj.setAttribute("customer", customer);
    }

    /**
     * This method will call the mail sender bean that would be responsible in sending the email
     * to noreply.polygon and notify them about the order request
     */
    private void sendOrderEmail(HttpServletRequest request) {
        Order o = (Order)request.getSession().getAttribute("selectedOrder");
        Building b = (Building) request.getSession().getAttribute("building");
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        mailSender = new MailSenderBean();
        String toEmail = "noreply.polygonproject@gmail.com";
        String subject = "ORDER: " + o.getServiceDescription();
        String message = "REQUEST FOR " + o.getServiceDescription() + "\n\nOrder Number: " + o.getOrderNumber() + "\nOrder Date:" + o.getOrderDate() + "\nCustomer: " + customer.getCompanyName() + "\nBuilding: " + b.getBuildingName() + "\nProblem Description: " + o.getProblemStatement();
        String fromEmail = "noreply.polygonproject@gmail.com";
        String username = "noreply.polygonproject";
        String password = "poly123go";
        //Call to  mail sender bean
        mailSender.sendEmail(fromEmail, username, password, toEmail, subject, message);
        request.getSession().setAttribute("customer", customer);
    }
    
}
