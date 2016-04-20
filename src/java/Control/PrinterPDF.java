package Control;

import Domain.*;
import Domain.Exceptions.PolygonException;
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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This Class is responsable for one thing only. Printing an Report in PDF file.
 *
 * @author Daniel
 */
public class PrinterPDF {

    /**
     * Creates an Test report object, that can be used to test that the pdf
     * works as it should.
     *
     * @param args
     */
    public static void main(String[] args) {
        //Test class:
        Report report=null;
        try {
            report = setUpPDFTest();
        } catch (PolygonException ex) {
            Logger.getLogger(PrinterPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        Building reportBuilding = getreportBuilding(report.getBuildingId());
        PrinterPDF instance = new PrinterPDF();
        try {
            instance.sendReportToPrint(report, reportBuilding,"C:\\Users\\Daniel\\Dropbox\\Computer Science\\2.semester\\NetBeans Projects\\polygon\\build\\web\\","test");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Method for getting a building based on the right id!
     *
     * @param buildingId
     * @return
     */
    public static Building getreportBuilding(int buildingId) {
        DomainFacade df = DomainFacade.getInstance();
        return df.getBuilding(buildingId);
    }

    /**
     * This class is resposable for creating a pdf document based on the report
     * obejct in the parameter. READ CAREFULLY. USES ALOT OF METHOD CALLS.
     *
     * @param report An object of the report to be created.
     * @param reportBuilding An object of the building that the report was
     * created for.
     * @param path The Path to the Project folder that you get by using: getServletContext().getRealPath("");
     * in a servlet.
     * @param fileName The Filename that you want the saved pdf file to be named! 
     * But NOT with the .pdf - that will be added later
     * @throws Domain.Exceptions.PolygonException If an error has been chaught while 
     * creating an pdf document, we throw an PolygonException.
     */
    public void sendReportToPrint(Report report, Building reportBuilding, String path, String fileName) throws PolygonException {
        try{
        File filepath = new File (path+File.separator);
        filepath = new File(filepath.getParentFile().getParent()+File.separator+"web"+File.separator+"pdfReports");
        String webPath = filepath.getParentFile().getPath(); // This is to get the right folder for loading images!
        File file = new File(filepath,fileName+".pdf");
        FileOutputStream pdfFileout = new FileOutputStream(file);

        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, pdfFileout);
        doc.addAuthor("Polygon");
        doc.addTitle("TestingPDF");
        doc.open();
        Font title = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);
        Font underline = new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.UNDERLINE);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 17);
        Font links = new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.UNDERLINE);
        links.setColor(BaseColor.BLUE);
        Font smallHeadline = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font conditionFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        conditionFont.setColor(BaseColor.RED);
        Font smallText =  new Font(Font.FontFamily.TIMES_ROMAN, 12);
        Font bold = new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.BOLD);

        addHeader(doc, report, reportBuilding, webPath);

        Paragraph headline = new Paragraph("Report Overview", title);
        headline.setAlignment(Element.ALIGN_CENTER);
        doc.add(headline);

        addpolygonInfo(writer);
        addBuildingInfo(doc, reportBuilding, underline, normal, report);
        addListOfRooms(smallHeadline, doc, report, links);

        doc.add(Chunk.NEXTPAGE);
        addHeader(doc, report, reportBuilding, webPath);
        addSecondPageEXTERIOR(doc, report, reportBuilding, title, smallHeadline, webPath);
        doc.add(Chunk.NEXTPAGE);

        for (ReportFloor floor : report.getReportFloors()) {

            for (ReportRoom room : floor.getReportRooms()) {

                addHeader(doc, report, reportBuilding, webPath);
                Paragraph headlineRoom = new Paragraph("Inspection of Room: " + floor.getFloorNumber() + " - " + room.getRoomName(), title);
                headlineRoom.setAlignment(Element.ALIGN_CENTER);
                doc.add(headlineRoom);

                doc.add(Chunk.NEWLINE);

                Phrase examinationHeadline = new Phrase("Interior State Of Room:", smallHeadline);
                doc.add(examinationHeadline);
                addInteriorTable(room, bold, doc, smallHeadline);

                Phrase damageHeadline = new Phrase("Damages to the room", bold);
                doc.add(damageHeadline);
                addDamageTable(room, bold, doc, smallHeadline);

                Phrase moistHeadline = new Phrase("Moist Scan For Room", smallHeadline);
                doc.add(moistHeadline);
                addMoistTable(room, bold, doc, smallHeadline);

                Phrase recomendationHeadline = new Phrase("Recomendation For Room", smallHeadline);
                doc.add(recomendationHeadline);
                addRecomendationTable(room, bold, doc, smallHeadline);
                
                addPicturesForRooms(room, smallHeadline, doc, bold, webPath);

                doc.add(Chunk.NEXTPAGE); // New page for each report.
            }
        }

        // Last Page:
        addHeader(doc, report, reportBuilding, webPath);
        Paragraph headlineConclussion = new Paragraph("Conclusion", title);
        headlineConclussion.setAlignment(Element.ALIGN_CENTER);
        doc.add(headlineConclussion);

        addLastPage(doc, normal, report, underline, smallHeadline, conditionFont, bold, smallText);

        doc.add(Chunk.NEWLINE);



        doc.close();
        pdfFileout.close();
        }
        catch(DocumentException | IOException e){
            System.out.println("An Error was chaugh when creating an Report" + e);
            e.printStackTrace();
            throw new PolygonException("Error Printing the Report");
        }

    }

    /**
     * Adds an picture table if there is any pictures
     * associated with the room
     * @param room
     * @param smallHeadline
     * @param doc
     * @param bold
     * @throws IOException
     * @throws DocumentException
     */
    private void addPicturesForRooms(ReportRoom room, Font smallHeadline, Document doc, Font bold, String webPath) throws IOException, DocumentException {
        if(room.getRrPic() != null && !room.getRrPic().isEmpty()){
            Phrase pictureHeadline = new Phrase("Pictures for Room:", smallHeadline);
            doc.add(pictureHeadline);
            webPath += File.separator+"ReportRoomPic";
            for (ReportPic picture : room.getRrPic()) {
                
                PdfPTable pictureTable = new PdfPTable(2);
                PdfPCell row1cell1 = new PdfPCell(new Phrase("Picture", bold));
                row1cell1.setBackgroundColor(BaseColor.GRAY);
                
                Image roompic = Image.getInstance(webPath + File.separator + picture.getFilename());
                
                
                
                roompic.scaleToFit(new Rectangle(200, 200));
                PdfPCell row1cell2 = new PdfPCell(roompic);
                row1cell2.setBorder(0);
                PdfPCell row2cell1 = new PdfPCell(new Phrase("Description", bold));
                row2cell1.setBackgroundColor(BaseColor.GRAY);
                PdfPCell row2cell2 = new PdfPCell(new Phrase(picture.getDescription(), bold));
                
                
                pictureTable.addCell(row1cell1);
                pictureTable.addCell(row1cell2);
                pictureTable.addCell(row2cell1);
                pictureTable.addCell(row2cell2);
                doc.add(pictureTable);
                doc.add(Chunk.NEWLINE);
                
            }
        }
    }

    /**
     * Responsable for creating elements for the last page in the 
     * printable version of the report.
     * @param doc
     * @param normal
     * @param report
     * @param underline
     * @param smallHeadline
     * @param conditionFont
     * @param bold
     * @param smallText
     * @throws DocumentException
     */
    private void addLastPage(Document doc, Font normal, Report report, Font underline, Font smallHeadline, Font conditionFont, Font bold, Font smallText) throws DocumentException {
        doc.add(new Phrase("The Building Inspection Was Done by ", normal));
        doc.add(new Phrase(report.getPolygonUserName(), underline));
        doc.add(Chunk.NEWLINE);

        if (report.getCustomerAccountable() != null && !report.getCustomerAccountable().equals("")) {
            // An Customer Accountable has been added to the report
            doc.add(new Phrase("In Cooperation with Customer Accountable ", normal));
            doc.add(new Phrase(report.getCustomerAccountable(), underline));
        } else {
            // An Customer accountable has not been added to the report:
            doc.add(new Phrase("The Inspection was done without an Customer Contact Person", normal));

        }
        doc.add(Chunk.NEWLINE);

        Paragraph conditionGradeHeadline = new Paragraph("The Condition Grade for the Building is: ", smallHeadline);
        conditionGradeHeadline.setAlignment(Element.ALIGN_CENTER);
        doc.add(conditionGradeHeadline);
        Paragraph conditionGrade = new Paragraph(report.getCategoryConclusion() + "", conditionFont);
        conditionGrade.setAlignment(Element.ALIGN_CENTER);
        doc.add(conditionGrade);

        doc.add(new Phrase("Condition Grade Reference Table", smallHeadline));
        doc.add(Chunk.NEWLINE);

        PdfPTable referenceTable = new PdfPTable(3);
        PdfPCell row1cell1 = new PdfPCell(new Phrase("Condition Grade", bold));
        row1cell1.setBackgroundColor(BaseColor.GRAY);
        PdfPCell row1cell2 = new PdfPCell(new Phrase("Description of building", bold));
        PdfPCell row1cell3 = new PdfPCell(new Phrase("Functionalty of building", bold));
        PdfPCell row2cell1 = new PdfPCell(new Phrase("Condition Grade 0", smallText));
        row2cell1.setBackgroundColor(BaseColor.GRAY);
        PdfPCell row2cell2 = new PdfPCell(new Phrase("The building is as if it was new", smallText));
        PdfPCell row2cell3 = new PdfPCell(new Phrase("The Functionality of the building is as descriped", smallText));
        PdfPCell row3cell1 = new PdfPCell(new Phrase("Condition Grade 1", smallText));
        row3cell1.setBackgroundColor(BaseColor.GRAY);
        PdfPCell row3cell2 = new PdfPCell(new Phrase("The building is intact, but with some wear and some visible damages. (Not something that disrupts the functionality )", smallText));
        PdfPCell row3cell3 = new PdfPCell(new Phrase("The Functionality of the building is as descriped", smallText));
        PdfPCell row4cell1 = new PdfPCell(new Phrase("Condition Grade 2", smallText));
        row4cell1.setBackgroundColor(BaseColor.GRAY);
        PdfPCell row4cell2 = new PdfPCell(new Phrase("The building has started to decay with some defective elements", smallText));
        PdfPCell row4cell3 = new PdfPCell(new Phrase("The functionality has been reduced – risk for consequential damages", smallText));
        PdfPCell row5cell1 = new PdfPCell(new Phrase("Condition Grade 3", smallText));
        row5cell1.setBackgroundColor(BaseColor.GRAY);
        PdfPCell row5cell2 = new PdfPCell(new Phrase("The building has been degraded and needs to be replaced", smallText));
        PdfPCell row5cell3 = new PdfPCell(new Phrase("	The functionality has ceased - risk for consequential damages", smallText));

        referenceTable.addCell(row1cell1);
        referenceTable.addCell(row1cell3);
        referenceTable.addCell(row1cell2);
        referenceTable.addCell(row2cell1);
        referenceTable.addCell(row2cell3);
        referenceTable.addCell(row2cell2);
        referenceTable.addCell(row3cell1);
        referenceTable.addCell(row3cell3);
        referenceTable.addCell(row3cell2);
        referenceTable.addCell(row4cell1);
        referenceTable.addCell(row4cell3);
        referenceTable.addCell(row4cell2);
        referenceTable.addCell(row5cell1);
        referenceTable.addCell(row5cell3);
        referenceTable.addCell(row5cell2);
        
        // Google T'ed!
        Phrase endingText = new Phrase("This report and building analysis is made"
                + " to clarify the immediate visual problems. Our purpose is to "
                + "ensure that the use of the building can be maintained. We will "
                + "not repair damage as part of building the review / report. "
                + "The review of the building does not contain moisture measurements "
                + "of the entire building, but we can make moisture scans few places "
                + "in the building, if we deem it necessary. If we find critical areas"
                + " of the building we will present recommendations for additional measures"
                + " such as further examinations, repairs or construction updates."
                + "Note that we have access to the entire building to perform a full review "
                + "(this includes access to the roof, attic, basement, crawl space or other enclosed areas)."
                + " This building analysis is non-destructive. If there is to be made destructive interference,"
                + " this must first be approved by the building manager."
                + " Destructive interference is not part of this report or building analysis."
                + "The building principal must supply the floor plan of the building before "
                + "building the review can be made.", smallText);
        doc.add(endingText);

        doc.add(referenceTable);
    }

    /**
     * Responsable for creating the recomendation table for each Room.
     *
     * @param room
     * @param bold
     * @param doc
     * @param smallHeadline
     * @throws DocumentException
     */
    private void addRecomendationTable(ReportRoom room, Font bold, Document doc, Font smallHeadline) throws DocumentException {
        if (room.getListOfRec() != null && !room.getListOfRec().isEmpty()) {
            // There has been added Recomendation for the Room.

            for (ReportRoomRecommendation recomendation : room.getListOfRec()) {
                PdfPTable recomendationTable = new PdfPTable(2);
                PdfPCell row1cell1 = new PdfPCell(new Phrase("Recomendation", bold));
                row1cell1.setBackgroundColor(BaseColor.GRAY);
                PdfPCell row1cell2 = new PdfPCell(new Phrase(recomendation.getRecommendation(), bold));

                recomendationTable.addCell(row1cell1);
                recomendationTable.addCell(row1cell2);
                doc.add(recomendationTable);
                doc.add(Chunk.NEWLINE);
            }

        } else {
            // No Recomendation for the Room has been added.
            Paragraph noRecomendations = new Paragraph("No Recomendations added for this room", smallHeadline);
            noRecomendations.setAlignment(Element.ALIGN_CENTER);
            doc.add(noRecomendations);
            doc.add(Chunk.NEWLINE);
        }
    }

    /**
     * Responsable for added in moist Table for a given room in the report!
     *
     * @param room
     * @param bold
     * @param doc
     * @param smallHeadline
     * @throws DocumentException
     */
    public void addMoistTable(ReportRoom room, Font bold, Document doc, Font smallHeadline) throws DocumentException {
        if (room.getMoist() != null) {
            // There has been added moist scan for this room.
            PdfPTable moistTable = new PdfPTable(2);
            PdfPCell row1cell1 = new PdfPCell(new Phrase("Moist Scan Result", bold));
            row1cell1.setBackgroundColor(BaseColor.GRAY);
            PdfPCell row1cell2 = new PdfPCell(new Phrase(room.getMoist().getMoistMeasured(), bold));
            PdfPCell row2cell1 = new PdfPCell(new Phrase("Moist Scan Area", bold));
            row2cell1.setBackgroundColor(BaseColor.GRAY);
            PdfPCell row2cell2 = new PdfPCell(new Phrase(room.getMoist().getMeasurePoint(), bold));

            moistTable.addCell(row1cell1);
            moistTable.addCell(row1cell2);
            moistTable.addCell(row2cell1);
            moistTable.addCell(row2cell2);
            doc.add(moistTable);
            doc.add(Chunk.NEWLINE);
        } else {
            // There has not been added a moist scan for the room
            Paragraph noMoistScan = new Paragraph("No Moist scan for this Room", smallHeadline);
            noMoistScan.setAlignment(Element.ALIGN_CENTER);
            doc.add(noMoistScan);
            doc.add(Chunk.NEWLINE);
        }
    }

    /**
     * Responsable for creating the damage table for each report room.
     *
     * @param room
     * @param bold
     * @param doc
     * @param smallHeadline
     * @throws DocumentException
     */
    private void addDamageTable(ReportRoom room, Font bold, Document doc, Font smallHeadline) throws DocumentException {
        if (room.getListOfDamages() != null && !room.getListOfDamages().isEmpty()) {
            // There has been added some damages in the report
            for (ReportRoomDamage damage : room.getListOfDamages()) {
                PdfPTable damagetable = new PdfPTable(2);
                PdfPCell row1cell1 = new PdfPCell(new Phrase("Estimated Time of Damage", bold));
                row1cell1.setBackgroundColor(BaseColor.GRAY);
                PdfPCell row1cell2 = new PdfPCell(new Phrase(damage.getDamageTime(), bold));
                PdfPCell row2cell1 = new PdfPCell(new Phrase("Where Damage Occurred", bold));
                row2cell1.setBackgroundColor(BaseColor.GRAY);
                PdfPCell row2cell2 = new PdfPCell(new Phrase(damage.getPlace(), bold));
                PdfPCell row3cell1 = new PdfPCell(new Phrase("Decription of Damage", bold));
                row3cell1.setBackgroundColor(BaseColor.GRAY);
                PdfPCell row3cell2 = new PdfPCell(new Phrase(damage.getWhatHappened(), bold));
                PdfPCell row4cell1 = new PdfPCell(new Phrase("Done Repairs ", bold));
                row4cell1.setBackgroundColor(BaseColor.GRAY);
                PdfPCell row4cell2 = new PdfPCell(new Phrase(damage.getWhatIsRepaired(), bold));
                PdfPCell row5cell1 = new PdfPCell(new Phrase("Damage Type ", bold));
                row5cell1.setBackgroundColor(BaseColor.GRAY);
                PdfPCell row5cell2 = new PdfPCell(new Phrase(damage.getDamageType(), bold));
                
                damagetable.addCell(row1cell1);
                damagetable.addCell(row1cell2);
                damagetable.addCell(row2cell1);
                damagetable.addCell(row2cell2);
                damagetable.addCell(row3cell1);
                damagetable.addCell(row3cell2);
                damagetable.addCell(row4cell1);
                damagetable.addCell(row4cell2);
                damagetable.addCell(row5cell1);
                damagetable.addCell(row5cell2);
                doc.add(damagetable);
                doc.add(Chunk.NEWLINE);

            }
        } else {
            //There has not been added any damages in the report.
            Paragraph noDamages = new Paragraph("No damages added for this room", smallHeadline);
            noDamages.setAlignment(Element.ALIGN_CENTER);
            doc.add(noDamages);
            doc.add(Chunk.NEWLINE);

        }
    }

    /**
     * Responsable for creating, and adding the interior table for each of the
     * interior obejects in the stored in the rooms list of interiors.
     *
     * @param room
     * @param bold
     * @param doc
     * @param smallHeadline
     * @throws DocumentException
     */
    private void addInteriorTable(ReportRoom room, Font bold, Document doc, Font smallHeadline) throws DocumentException {
        if (room.getListOfInt() != null && !room.getListOfInt().isEmpty()) {
            // not empty or null there for read from and add
            PdfPTable interiorTable = new PdfPTable(2);
            PdfPCell row1cell1 = new PdfPCell(new Phrase("Inspected Area", bold));
            row1cell1.setBackgroundColor(BaseColor.GRAY);
            interiorTable.addCell(row1cell1);
            PdfPCell row1cell2 = new PdfPCell(new Phrase("Remark", bold));
            interiorTable.addCell(row1cell2);
            for (ReportRoomInterior interior : room.getListOfInt()) {

                PdfPCell row2cell1 = new PdfPCell(new Phrase(interior.getRepRoomIntName(), bold));
                row2cell1.setBackgroundColor(BaseColor.GRAY);
                interiorTable.addCell(row2cell1);
                PdfPCell row2cell2 = new PdfPCell(new Phrase(interior.getRemark(), bold));
                interiorTable.addCell(row2cell2);
            }
            doc.add(interiorTable);
            doc.add(Chunk.NEWLINE);
        } else {
            // The list of Room interior was empty or null.
            Paragraph noInterior = new Paragraph("There are no Interior Remarks of this Room", smallHeadline);
            noInterior.setAlignment(Element.ALIGN_CENTER);
            doc.add(noInterior);
            doc.add(Chunk.NEWLINE);
        }
    }

    /**
     * Inserts an number to the page. Needs to be called every time and new page
     * is created. Does not take into account if an report room takes up more
     * than one page.
     *
     * @param writer
     * @param pagenumber
     * @throws IOException
     * @throws DocumentException
     */
    private void insertPageNumber(PdfWriter writer, int pagenumber) throws IOException, DocumentException {
        PdfContentByte pagenumberPos = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont();
        pagenumberPos.saveState();
        pagenumberPos.beginText();
        pagenumberPos.moveText(550, 30);
        pagenumberPos.setFontAndSize(bf, 12);
        pagenumberPos.showText("" + pagenumber);
        pagenumberPos.endText();
        pagenumberPos.restoreState();
    }

    /**
     * Creates the second page in the PDF, with Exterior Discription and
     * Pictures.
     *
     * @param doc
     * @param report
     * @param reportBuilding
     * @param title
     * @param smallHeadline
     * @throws DocumentException
     * @throws IOException
     */
    private void addSecondPageEXTERIOR(Document doc, Report report, Building reportBuilding, Font title, Font smallHeadline, String webPath) throws DocumentException, IOException {
        Paragraph headlineEXTERIOR = new Paragraph("Exterior review of Building", title);
        headlineEXTERIOR.setAlignment(Element.ALIGN_CENTER);
        doc.add(headlineEXTERIOR);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);

        if (report.getListOfRepExt() == null || report.getListOfRepExt().isEmpty()) {
            Paragraph noExteriors = new Paragraph("No exterior added for this Report", smallHeadline);
            noExteriors.setAlignment(Element.ALIGN_CENTER);
            doc.add(noExteriors);
        } else {
            for (ReportExterior ExteriorElement : report.getListOfRepExt()) {
                PdfPTable exteriorTable = new PdfPTable(2);

                PdfPCell row1cell1 = new PdfPCell(new Phrase(ExteriorElement.getRepExtInspectedArea()));
                row1cell1.setBackgroundColor(BaseColor.GRAY);
                exteriorTable.addCell(row1cell1);
                PdfPCell row1cell2 = new PdfPCell(new Phrase(ExteriorElement.getRepExtDescription()));
                exteriorTable.addCell(row1cell2);

                doc.add(exteriorTable);

            }
            doc.add(Chunk.NEWLINE);
            //takes the first, since now it's only possible to add one picture
            ReportExterior firstEx = report.getListOfRepExt().get(0);
            if (firstEx != null && firstEx.getRepExtPic() != null) {

                report.getListOfExtPics();
                PdfPTable pictureExteriorTable = new PdfPTable(2);
                PdfPCell row1cell1 = new PdfPCell(new Phrase("Picture:"));
                row1cell1.setBackgroundColor(BaseColor.GRAY);
                pictureExteriorTable.addCell(row1cell1);

                webPath += File.separator + "ReportExtPic" ; 
                Image exteriorimg = Image.getInstance( webPath + File.separator +firstEx.getRepExtPic());
                exteriorimg.scaleToFit(new Rectangle(200, 200));
                PdfPCell row1cell2 = new PdfPCell(exteriorimg);
                row1cell2.setBorder(0);
                pictureExteriorTable.addCell(row1cell2);

                PdfPCell row2cell1 = new PdfPCell(new Phrase("Decription:"));
                row2cell1.setBackgroundColor(BaseColor.GRAY);
                pictureExteriorTable.addCell(row2cell1);
                PdfPCell row2cell2 = new PdfPCell(new Phrase(firstEx.getRepExtPicDescriptoin()));
                pictureExteriorTable.addCell(row2cell2);
                doc.add(pictureExteriorTable);
            }

        }
    }

    /**
     * Method for creating the header to an new page in the pdf.
     *
     * @param doc
     * @throws DocumentException
     * @throws IOException
     * @throws BadElementException
     */
    private void addHeader(Document doc, Report report, Building reportBuilding, String path) throws DocumentException, IOException, BadElementException {
        path += File.separator+"images";
        Image header = Image.getInstance(path + File.separator +"Healthybuildings.png");
        Image logo = Image.getInstance(path + File.separator +"PolygonLogo.png");
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
        doc.add(Chunk.NEWLINE);
    }

    /**
     * Adds an list of Report Rooms to the first page.
     *
     * @param smallHeadline
     * @param doc
     * @param report
     * @param links
     * @throws DocumentException
     */
    private void addListOfRooms(Font smallHeadline, Document doc, Report report, Font links) throws DocumentException {
        Paragraph listofRooms = new Paragraph("List of Rooms in the Report: ", smallHeadline);
        listofRooms.setAlignment(Element.ALIGN_CENTER);
        doc.add(listofRooms);
        for (ReportFloor Floor : report.getReportFloors()) {
            for (ReportRoom room : Floor.getReportRooms()) {

                Paragraph roomPhrase = new Paragraph(Floor.getFloorNumber() + ": " + room.getRoomName(), links);
                roomPhrase.setAlignment(Element.ALIGN_CENTER);
                doc.add(roomPhrase);
            }

        }
    }

    /**
     * Adds the building Info Fields
     *
     * @param doc
     * @param reportBuilding
     * @param underline
     * @param report
     * @throws DocumentException
     */
    private void addBuildingInfo(Document doc, Building reportBuilding, Font underline, Font normal, Report report) throws DocumentException {
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);

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

        Phrase buildingZipAndArea = new Phrase(reportBuilding.getZipCode() + "", underline); // not connected with Area yet
        doc.add(new Chunk("Zip and Area: ", normal));
        doc.add(buildingZipAndArea);

        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);

        doc.add(new Phrase("General Infomation:", underline));
        doc.add(Chunk.NEWLINE);
        doc.add(new Phrase("Building Year:", normal));
        doc.add(new Phrase(reportBuilding.getBuildingYear() + "", underline));
        doc.add(Chunk.NEWLINE);
        doc.add(new Phrase("Building m2: ", normal));
        doc.add(new Phrase(reportBuilding.getBuildingSize() + "", underline));
        doc.add(Chunk.NEWLINE);
        doc.add(new Phrase("Use of building :", normal));
        doc.add(new Phrase(reportBuilding.getUseOfBuilding(), underline));

        doc.add(Chunk.NEWLINE);
        doc.add(Chunk.NEWLINE);
    }

    /**
     * Adds the table in the first page with polygon info.
     *
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
        polygonInfo.writeSelectedRows(0, -1, 375, 650, writer.getDirectContent());
    }

    /**
     * This method is just for the setup in the PrinterPDF
     *
     * @return A Report object that is loaded from the database for testing.
     */
    private static Report setUpPDFTest() throws PolygonException {
        DomainFacade df = DomainFacade.getInstance();
        Report report = df.getReport(71); // This can be changed if you want to test another report.
        return report;
    }
}
