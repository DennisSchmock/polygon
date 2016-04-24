/*
 * 
 * 
 * 
 */
package Domain;

/**
 *
 * @author danie
 */
public class BuildingFile {
    
    private int fileID;
    private int size;
    private String filename;
    private String documentname;

    public BuildingFile(int size, String filename, String documentname) {
        this.size = size;
        this.filename = filename;
        this.documentname = documentname;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
    
    
}
