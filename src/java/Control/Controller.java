/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 The servlet knows this controller class.
 This controller knows the other classes in the control layer.
 Connects to the DB Facade to send on information.
 * @author dennisschmock
 */
public class Controller {
    
    /**
     * THis method takes two numbers and adds them up...
     * @param a an integer of any size
     * @param b an integer of any size
     * @return the two numbers added
     */
    public int addNumbers(int a, int b){
        return a+b; //here we do something....
    }
}
