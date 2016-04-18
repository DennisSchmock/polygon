
package Control;

import Domain.*;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This Class is responsable for one thing only. Printing an
 * Report in  PDF file.
 * @author Daniel
 */
public class PrinterPDF {
    
    /**
     * Creates an Test report object, that can be used to test
     * that the pdf works as it should. 
     * @param args
     */
    public static void main(String[] args) {
        //Test class:
        Report report = setUpPDFTest();
        Building reportBuilding = getreportBuilding(report.getBuildingId());
        PrinterPDF instance = new PrinterPDF();
        try {
            instance.sendReportToPrint(report, reportBuilding);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Method for getting a building based on the right id!
     * @param buildingId
     * @return
     */
    public static Building getreportBuilding(int buildingId) {
        DomainFacade df = DomainFacade.getInstance();
        return df.getBuilding(buildingId);
    }

    
    /**
     * This class is resposable for creating a pdf document based on the
     * 
     * @param report
     * @param reportBuilding An object of the building that the report was created for.
     * @throws java.lang.Exception This method throws all Exceptions
     */
    public void sendReportToPrint(Report report, Building reportBuilding ) throws Exception{
        
                
        Document doc = new Document();
        PdfWriter writer =  PdfWriter.getInstance(doc, new FileOutputStream(
          "testPDF.pdf"));
        doc.open();
        Font title = new Font(Font.FontFamily.HELVETICA  , 25, Font.BOLD);
        Font underline = new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.UNDERLINE);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 17);
        Font links = new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.UNDERLINE);
        links.setColor(BaseColor.BLUE);
        Font smallHeadline = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        
        
        addHeader(doc, report, reportBuilding);
        
        Paragraph headline = new Paragraph("Report Overview", title);
        headline.setAlignment(Element.ALIGN_CENTER);
        doc.add(headline);
        
        addpolygonInfo(writer);
        addBuildingInfo(doc, reportBuilding, underline, normal, report);
        addListOfRooms(smallHeadline, doc, report, links);
        
        doc.add(Chunk.NEXTPAGE);
        addSecondPageEXTERIOR(doc, report, reportBuilding, title, smallHeadline);
        
        
        
        
        
        
        doc.close();
        
    }

    /**
     * Creates the second page in the PDF, with Exterior and Stuff.
     * @param doc
     * @param report
     * @param reportBuilding
     * @param title
     * @param smallHeadline
     * @throws DocumentException
     * @throws IOException
     */
    private void addSecondPageEXTERIOR(Document doc, Report report, Building reportBuilding, Font title, Font smallHeadline) throws DocumentException, IOException {
        addHeader(doc, report, reportBuilding);
        Paragraph headlineEXTERIOR = new Paragraph("Exterior review of Building", title);
        headlineEXTERIOR.setAlignment(Element.ALIGN_CENTER);
        doc.add(headlineEXTERIOR);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        
        if(report.getListOfRepExt() == null || report.getListOfRepExt().isEmpty()){
            Paragraph noExteriors = new Paragraph("No exterior added for this Report", smallHeadline);
            noExteriors.setAlignment(Element.ALIGN_CENTER);
            doc.add(noExteriors);
        }
        else{
            for (ReportExterior ExteriorElement : report.getListOfRepExt()) {
                PdfPTable exteriorTable = new PdfPTable(2);
                
                PdfPCell row1cell1 = new PdfPCell(new Phrase(ExteriorElement.getRepExtInspectedArea()));
                row1cell1.setBackgroundColor(BaseColor.GRAY);
                exteriorTable.addCell(row1cell1);
                PdfPCell row1cell2 = new PdfPCell( new Phrase(ExteriorElement.getRepExtDescription()));
                exteriorTable.addCell(row1cell2);
                
                doc.add(exteriorTable);
                doc.add(Chunk.NEWLINE);
                
            }
            //takes the first, since now it's only possible to add one picture
            ReportExterior firstEx = report.getListOfRepExt().get(0);
            
            PdfPTable exteriorTable = new PdfPTable(2);
            PdfPCell row1cell1 = new PdfPCell(new Phrase("Picture:"));
            row1cell1.setBackgroundColor(BaseColor.GRAY);
            exteriorTable.addCell(row1cell1);
            
            if(firstEx.getRepExtPic() != null){
                Image exteriorimg = Image.getInstance("web/ReportExtPic/" +firstEx.getRepExtPic());
                exteriorTable.addCell(exteriorimg);
            }
            else{ // if no picture, add an empty field.
                PdfPCell row1cell2 = new PdfPCell(new Phrase(""));
                exteriorTable.addCell(row1cell2);
            }
            doc.add(exteriorTable);
            
        }
    }

    /**
     * Method for creating the header to an new page in the pdf.
     * @param doc
     * @throws DocumentException
     * @throws IOException
     * @throws BadElementException
     */
    private void addHeader(Document doc, Report report, Building reportBuilding) throws DocumentException, IOException, BadElementException {
        Image header = Image.getInstance("web/images/Healthybuildings.png");
        Image logo = Image.getInstance("web/images/PolygonLogo.png");
        header.setAbsolutePosition(430, 750);
        doc.add(header);
        logo.setAbsolutePosition(10, 750);
        doc.add(logo);
        doc.add(new Phrase("\n"));
        doc.add(new Phrase("\n"));
        doc.add(new Phrase("\n"));
        doc.add(new Phrase("\n"));
        
        Phrase Reportnumber = new Phrase("Report ID:" + report.getReportId() + "\n" + "Name Of Building: " + reportBuilding.getBuildingName());
        doc.add(Reportnumber);
        doc.add( Chunk.NEWLINE );
    }

    /**
     * Adds an list of Report Rooms to the first page.
     * @param smallHeadline
     * @param doc
     * @param report
     * @param links
     * @throws DocumentException
     */
    private void addListOfRooms(Font smallHeadline, Document doc, Report report, Font links) throws DocumentException {
        Paragraph listofRooms =new Paragraph("List of Rooms in the Report: ",smallHeadline );
        listofRooms.setAlignment(Element.ALIGN_CENTER);
        doc.add(listofRooms);
        for (ReportRoom room : report.getListOfRepRoom()) {
            Paragraph roomPhrase =new Paragraph( room.getRoomFloor()+ ": " + room.getRoomName(), links);
            roomPhrase.setAlignment(Element.ALIGN_CENTER);
            doc.add(roomPhrase);
            
        }
    }

    /**
     * Adds the building Info Fields
     * @param doc
     * @param reportBuilding
     * @param underline
     * @param report
     * @throws DocumentException
     */
    private void addBuildingInfo(Document doc, Building reportBuilding, Font underline, Font normal,  Report report) throws DocumentException {
        doc.add( Chunk.NEWLINE );
        doc.add( Chunk.NEWLINE );
        
        Phrase buildingname = new Phrase(reportBuilding.getBuildingName(), underline);
        doc.add(new Chunk("Building Name : ", normal));
        doc.add(buildingname);
        
        doc.add(Chunk.SPACETABBING);
        doc.add(Chunk.SPACETABBING);
        doc.add(Chunk.SPACETABBING);
        
        Phrase date = new Phrase(report.getDate().toString(), underline);
        doc.add(new Chunk("Date: ", normal));
        doc.add(date);
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        
        Phrase buildingAddress = new Phrase(reportBuilding.getStreetAddress() + " " + reportBuilding.getStreetNumber(), underline);
        doc.add(new Chunk("Address: ", normal));
        doc.add(buildingAddress);
        
        doc.add(Chunk.SPACETABBING);
        doc.add(Chunk.SPACETABBING);
        doc.add(Chunk.SPACETABBING);
        
        Phrase buildingZipAndArea = new Phrase(reportBuilding.getZipCode()+ "", underline); // not connected with Area yet
        doc.add(new Chunk("Zip and Area: ", normal));
        doc.add(buildingZipAndArea);
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        
        doc.add(new Phrase("General Infomation:", underline));
        doc.add(Chunk.NEWLINE);
        doc.add(new Phrase("Building Year:", normal));
        doc.add(new Phrase(reportBuilding.getBuildingYear()+"", underline));
        doc.add(Chunk.NEWLINE);
        doc.add(new Phrase("Building m2: ", normal));
        doc.add(new Phrase(reportBuilding.getBuildingSize()+"" , underline));
        doc.add(Chunk.NEWLINE);
        doc.add(new Phrase("Use of building :", normal));
        doc.add(new Phrase(reportBuilding.getUseOfBuilding() , underline));
        
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
    }

    /**
     * Adds the table in the first page with polygon info.
     * @param writer
     */
    private void addpolygonInfo(PdfWriter writer) {
        PdfPTable polygonInfo = new PdfPTable(1);
        
        PdfPCell companyName = new PdfPCell(new Phrase("Polygon"));
        companyName.setBorder(0);
        companyName.setColspan(3);
        PdfPCell companyAddress = new PdfPCell(new Phrase("Rypevang 5"));
        companyAddress.setColspan(3);
        companyAddress.setBorder(0);
        PdfPCell companyZip = new PdfPCell(new Phrase("3450 Allerød"));
        companyZip.setColspan(3);
        companyZip.setBorder(0);
        PdfPCell companyPhone = new PdfPCell(new Phrase("Phone. 4814 0555"));
        companyPhone.setColspan(3);
        companyPhone.setBorder(0);
        PdfPCell companyEmail = new PdfPCell(new Phrase("sundebygninger@polygon.dk"));
        companyEmail.setColspan(3);
        companyEmail.setBorder(0);
        
        polygonInfo.addCell(companyName);
        polygonInfo.addCell(companyAddress);
        polygonInfo.addCell(companyZip);
        polygonInfo.addCell(companyPhone);
        polygonInfo.addCell(companyEmail);
        
        polygonInfo.setTotalWidth(300f);
        polygonInfo.writeSelectedRows(0, -1, 375, 650,writer.getDirectContent());
    }
    
    /**
     * This method is just for the setup in the PrinterPDF
     * 
     * @return A Report object that is loaded from the database for testing.
     */
    private static Report setUpPDFTest() {
        DomainFacade df = DomainFacade.getInstance();
        Report report = df.getReport(71); // This can be changed if you want to test another report.
        return report;
    }
}   
