/*
 * Object that holds a list of grouped files for buildings that share
 * a description
 * 
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
