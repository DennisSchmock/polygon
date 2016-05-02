/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;

/**
 *
 * @author danie
 */
public class Floorplan extends BuildingFile implements Serializable {
    
    /**
     *
     * @param size
     * @param filename
     * @param documentname
     */
    public Floorplan(int size, String filename, String documentname) {
        super(size, filename, documentname);
    }
    
}
