/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author  CJS
 */
public class ReportRoomExterior {
    private int repExtId;
    private String repExtDescription;
    private int repExtPic;
    private int reportId;

    /**
     *
     * @param repExtId   report room exterior ID
     * @param repExtDescription   report room exterior description
     * @param repExtPic   report room exterior picture ID
     * @param reportId   report ID references to the report
     */
    public ReportRoomExterior(int repExtId, String repExtDescription, int repExtPic, int reportId) {
        this.repExtId = repExtId;
        this.repExtDescription = repExtDescription;
        this.repExtPic = repExtPic;
        this.reportId = reportId;
    }

    public int getRepExtId() {
        return repExtId;
    }

    public String getRepExtDescription() {
        return repExtDescription;
    }

    public int getRepExtPic() {
        return repExtPic;
    }

    public int getReportId() {
        return reportId;
    }
    
    
}
