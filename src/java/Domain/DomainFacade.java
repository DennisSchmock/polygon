/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 The servlet knows this controller class.
 This controller knows the other classes in the control layer.
 Connects to the DB Facade to send on information.
 * @author dennisschmock
 */
public class DomainFacade {
    
     private DomainFacade()
    {
      
    }

    public static DomainFacade getInstance()
    {
         return new DomainFacade();
    }

    /**
     * @see All the fields needed to create an building object
     * Creates the building object and sends it to the DBFacade
     */
    public void createnewBuilding(String buildingName, String StreetAddress, String StreetNumber, int zipcode, double buildingsize, int buildingYear, String useOfBuilding) {
        Building b = new Building(buildingName, StreetAddress, StreetNumber, zipcode, buildingYear, buildingsize, useOfBuilding);
        
    } 
}
