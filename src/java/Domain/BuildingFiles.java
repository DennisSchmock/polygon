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

    /**
     *
     * @param listOfFileInfo
     * @param description
     */
    public BuildingFiles(ArrayList<BuildingFile> listOfFileInfo, String description) {
        this.listOfFileInfo = listOfFileInfo;
        this.description = description;
        
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public ArrayList<BuildingFile> getListOfFileInfo() {
        return listOfFileInfo;
    }

    /**
     *
     * @param listOfFilenames
     */
    public void setListOfFileInfo(ArrayList<String> listOfFilenames) {
        this.listOfFileInfo = listOfFileInfo;
    }
    
    
    
    
    
}
