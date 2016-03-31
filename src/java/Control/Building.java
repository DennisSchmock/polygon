/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dennisschmock
 */
public class Building {
    private int bdgId;
    private String bdgName;
    private String bdgAddress;
    private int postNum;
    private int bdgAge;
    private double bdgSize;
    private String useOfBdg;
    private List<Report> listOfReports;
    private int custId;

    /**
     *
     * @param bdgId   building's ID
     * @param bdgName building name
     * @param bdgAddress building address
     * @param postNum post number
     * @param bdgAge  building age
     * @param bdgSize building size
     * @param useOfBdg the use of the building
     * @param custId the customer's ID
     */
    public Building(int bdgId,String bdgName,
            String bdgAddress, int postNum, int bdgAge, double bdgSize, 
            String useOfBdg, int custId) {
        this.bdgId = bdgId;
        this.bdgName = bdgName;
        this.bdgAddress = bdgAddress;
        this.postNum = postNum;
        this.bdgAge = bdgAge;
        this.bdgSize = bdgSize;
        this.useOfBdg = useOfBdg;
        this.custId = custId;
        
    }

    public int getBdgId() {
        return bdgId;
    }

    public String getBdgName() {
        return bdgName;
    }

    public String getBdgAddress() {
        return bdgAddress;
    }

    public int getPostNum() {
        return postNum;
    }

    public int getBdgAge() {
        return bdgAge;
    }

    public double getBdgSize() {
        return bdgSize;
    }

    public String getUseOfBdg() {
        return useOfBdg;
    }

    public List<Report> getListOfReports() {
        return listOfReports;
    }

    public int getCustId() {
        return custId;
    }

    public void setListOfReports(ArrayList<Report> listOfReports) {
        this.listOfReports = listOfReports;
    }
}
