/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import Model.DBFacade;

/**
 *
 The servlet knows this controller class.
 This controller knows the other classes in the control layer.
 Connects to the DB Facade to send on information.
 * @author dennisschmock
 */
public class DomainFacade {
    
    private DBFacade dbFacade;
    
    private DomainFacade()
    {
      dbFacade = DBFacade.getInstance();
    }

    public static DomainFacade getInstance()
    {
         return new DomainFacade();
    }
    
    
   
}
