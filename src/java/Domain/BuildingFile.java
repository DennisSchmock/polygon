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

    /**
     *
     * @param size
     * @param filename
     * @param documentname
     */
    public BuildingFile(int size, String filename, String documentname) {
        this.size = size;
        this.filename = filename;
        this.documentname = documentname;
    }

    /**
     *
     * @return
     */
    public String getDocumentname() {
        return documentname;
    }

    /**
     *
     * @param documentname
     */
    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return
     */
    public int getFileID() {
        return fileID;
    }

    /**
     *
     * @param fileID
     */
    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
    
    
}
