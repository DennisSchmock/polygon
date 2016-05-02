/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;

/**
 * Class that can elements to create exterior decription of the building.
 * Does contain few decriptions of the building.
 * @author  CJS
 */
public class ReportExterior implements Serializable{
    private int repExtId;
    private String repExtInspectedArea;
    private String repExtDescription;
    private String repExtPic;
    private String repExtPicDecription;
    private int reportId;

    /**
     * Used in the Frontcontroller to create, based on fields in JSP.
     * @param repExtInspectedArea The area that is inspected: Walls, Roof exc.
     * @param repExtDescription The concreate decription
     */
    public ReportExterior(String repExtInspectedArea, String repExtDescription) {
        this.repExtInspectedArea = repExtInspectedArea;
        this.repExtDescription = repExtDescription;
    }

    /**
     * Used in the database
     * @param repExtId
     * @param repExtInspectedArea
     * @param repExtDescription
     * @param repExtPic
     * @param reportId
     */
    public ReportExterior(int repExtId, String repExtInspectedArea, String repExtDescription, String repExtPic, int reportId) {
        this.repExtId = repExtId;
        this.repExtInspectedArea = repExtInspectedArea;
        this.repExtDescription = repExtDescription;
        this.repExtPic = repExtPic;
        this.reportId = reportId;
    }
    
    /**
     *
     * @param repExtDescription
     * @param repExtPic
     * @param reportId
     */
    public ReportExterior( String repExtDescription, String repExtPic, int reportId) {
        this.repExtDescription = repExtDescription;
        this.repExtPic = repExtPic;
        this.reportId = reportId;
    }
    
    /**
     *
     * @param repExtId
     */
    public void setRepExtId(int repExtId) {
        this.repExtId = repExtId;
    }

    /**
     *
     * @return
     */
    public int getRepExtId() {
        return repExtId;
    }

    /**
     *
     * @return
     */
    public String getRepExtDescription() {
        return repExtDescription;
    }

    /**
     *
     * @return
     */
    public String getRepExtPic() {
        return repExtPic;
    }

    /**
     *
     * @return
     */
    public int getReportId() {
        return reportId;
    }

    /**
     *
     * @return
     */
    public String getRepExtInspectedArea() {
        return repExtInspectedArea;
    }

    /**
     *
     * @param repExtInspectedArea
     */
    public void setRepExtInspectedArea(String repExtInspectedArea) {
        this.repExtInspectedArea = repExtInspectedArea;
    }

    /**
     *
     * @param repExtDescription
     */
    public void setRepExtDescription(String repExtDescription) {
        this.repExtDescription = repExtDescription;
    }

    /**
     *
     * @param repExtPic
     */
    public void setRepExtPic(String repExtPic) {
        this.repExtPic = repExtPic;
    }

    /**
     *
     * @param reportId
     */
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    /**
     *
     * @return
     */
    public String getRepExtPicDescriptoin() {
        return repExtPicDecription;
    }

    /**
     *
     * @param repExtPicDescriptoin
     */
    public void setRepExtPicDescriptoin(String repExtPicDescriptoin) {
        this.repExtPicDecription = repExtPicDescriptoin;
    }
    
    @Override
    public String toString() {
        return "ReportExterior{" + "repExtId=" + repExtId + ", repExtInspectedArea=" + repExtInspectedArea + ", repExtDescription=" + repExtDescription + ", repExtPic=" + repExtPic + ", reportId=" + reportId + '}';
    }
    
    
    
    
    
}
