/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.IOException;

/**
 *
 * @author Dennis
 */
public class FileTest {

    //10000000 bytes = 10mb
    public static int setSize = 10000000;

    public static void main(String[] args) throws IOException {

     
        String newline = System.getProperty("line.separator");

        // Create file
        Writer output = null;
        File file = new File("file.txt");
        output = new BufferedWriter(new FileWriter(file, true));
        output.write("");
        output.write(newline);

        // Get file size in bytes
        long size = fp.getFileSize("file.txt");

        // Write file whilst the size is smaller than setSize
        while (size < setSize) {
            output.write("Java Programming Forums");
            output.write(newline);

            //flush for the file to get updated on size and content
            output.flush();

            size = fp.getFileSize("file.txt");
            System.out.println(size + " bytes");
        }

        output.close();
        System.out.println("Finished at - " + size);

    }

    // File size code
    public long getFileSize(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File does not exist");
            return -1;
        }
        return file.length();
    }
}
