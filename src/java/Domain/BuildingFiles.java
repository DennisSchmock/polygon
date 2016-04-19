/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class BuildingFiles implements Serializable{
    //private int buildingFilesID;
    private ArrayList<BuildingFile> listOfFileInfo;
    private String description;

    public BuildingFiles(ArrayList<BuildingFile> listOfFileInfo, String description) {
        this.listOfFileInfo = listOfFileInfo;
        this.description = description;
        
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<BuildingFile> getListOfFileInfo() {
        return listOfFileInfo;
    }

    public void setListOfFileInfo(ArrayList<String> listOfFilenames) {
        this.listOfFileInfo = listOfFileInfo;
    }
    
    
    
    
    
}
