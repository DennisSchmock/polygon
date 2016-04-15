/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Domain.DomainFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dennis
 */
class CreateUserHelper {

    void createNewCustomer(DomainFacade df, HttpSession session, HttpServletRequest request) {
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
    
}
