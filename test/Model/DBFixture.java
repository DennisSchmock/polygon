package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBFixture {

    private Connection connection;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost:3306/Polytest";
    private static String id = "root";			
    private static String pw = "kodeord";

    public void setUp() throws SQLException {
        try {
            Class.forName(driver);
            setConnection(DriverManager.getConnection(URL, id, pw));
            Statement st = getConnection().createStatement();
            // start transaction
            getConnection().setAutoCommit(false);

// Test setup start
            st.addBatch("drop table if exists floorplan, report_room_damage, report_room_recommendation, "
                    + "report_room_moist,report_room_interior_pic,\n"
                    + "report_room_interior,report_exterior,report_room,report_pic,report,"
                    + "building_room,order_request,building_documents,building,customer_user,"
                    + "contact,customer,zip_codes,polygon_user;");
      

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`customer` ("
                    + "`customer_id` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`companyname` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`street` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`streetnumber` VARCHAR(5) NULL DEFAULT NULL,"
                    + "`zipcode` INT(11) NULL DEFAULT NULL,"
                    + "`phone` VARCHAR(20) NULL DEFAULT NULL,"
                    + "`email` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`cvr` VARCHAR(15) NULL DEFAULT NULL,"
                    + "`contactperson` VARCHAR(45) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`customer_id`))");

            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`building` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`building` ("
                    + "`idbuilding` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`building_name` VARCHAR(40) NULL DEFAULT NULL,"
                    + "`building_m2` DOUBLE NOT NULL,"
                    + "`building_adress` VARCHAR(100) NOT NULL,"
                    + "`building_housenumber` VARCHAR(10) NOT NULL,"
                    + "`building_buildyear` INT(4) NOT NULL,"
                    + "`building_zip` INT(4) NOT NULL,"
                    + "`building_pic` INT(10) NULL DEFAULT NULL,"
                    + "`building_use` VARCHAR(100) NULL DEFAULT NULL,"
                    + "`customer_id` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`idbuilding`),"
                    + "INDEX `customer_id` (`customer_id` ASC),"
                    + "CONSTRAINT `building_ibfk_1`"
                    + "FOREIGN KEY (`customer_id`)"
                    + "REFERENCES `Polytest`.`customer` (`customer_id`))");

            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`building_documents` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`building_documents` ("
                    + "`document_id` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`document_type` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`document_size` BIGINT(20) NULL DEFAULT NULL,"
                    + "`documentname` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`building_id` INT(11) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`document_id`),"
                    + "INDEX `building_id` (`building_id` ASC),"
                    + "CONSTRAINT `building_documents_ibfk_1`"
                    + "  FOREIGN KEY (`building_id`)"
                    + "  REFERENCES `Polytest`.`building` (`idbuilding`))");

            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`building_room` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`building_room` ("
                    + "`room_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`room_name` VARCHAR(40) NULL DEFAULT NULL,"
                    + "`building_id` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`room_id`),"
                    + "INDEX `building_id` (`building_id` ASC),"
                    + "CONSTRAINT `building_room_ibfk_1`"
                    + "  FOREIGN KEY (`building_id`)"
                    + "  REFERENCES `Polytest`.`building` (`idbuilding`))");

            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`contact` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`contact` ("
                    + "`contactID` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`name` VARCHAR(30) NOT NULL,"
                    + "`email` VARCHAR(20) NOT NULL,"
                    + "`tel` VARCHAR(15) NOT NULL,"
                    + "`customerID` INT(11) NOT NULL,"
                    + "PRIMARY KEY (`contactID`),"
                    + "INDEX `customerID` (`customerID` ASC),"
                    + "CONSTRAINT `contact_ibfk_1`"
                    + "  FOREIGN KEY (`customerID`)"
                    + "  REFERENCES `Polytest`.`customer` (`customer_id`))");

            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`customer_user`");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`customer_user` ("
                    + "`username` VARCHAR(45) NOT NULL,"
                    + "`pwd` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`customer_id` INT(11) NULL DEFAULT NULL,"
                    + "`fname` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`lname` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`email` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`phone` VARCHAR(20) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`username`),"
                    + "INDEX `customer_id` (`customer_id` ASC),"
                    + "CONSTRAINT `customer_user_ibfk_1`"
                    + "  FOREIGN KEY (`customer_id`)"
                    + "  REFERENCES `Polytest`.`customer` (`customer_id`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`floorplan`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`floorplan` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`floorplan` ("
                    + "`floorplan_id` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`floorplanpath` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`building_id` INT(11) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`floorplan_id`),"
                    + "INDEX `building_id` (`building_id` ASC),"
                    + "CONSTRAINT `floorplan_ibfk_1`"
                    + "  FOREIGN KEY (`building_id`)"
                    + "  REFERENCES `Polytest`.`building` (`idbuilding`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`order_request`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`order_request` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`order_request` ("
                    + "`order_id` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`customer_username` VARCHAR(45) NOT NULL,"
                    + "`building_id` INT(11) NULL DEFAULT NULL,"
                    + "`order_type` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`order_description` TEXT NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`order_id`),"
                    + "INDEX `customer_username` (`customer_username` ASC),"
                    + "INDEX `building_id` (`building_id` ASC),"
                    + "CONSTRAINT `order_request_ibfk_1`"
                    + "  FOREIGN KEY (`customer_username`)"
                    + "  REFERENCES `Polytest`.`customer_user` (`username`),"
                    + "CONSTRAINT `order_request_ibfk_2`"
                    + "  FOREIGN KEY (`building_id`)"
                    + "  REFERENCES `Polytest`.`building` (`idbuilding`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`polygon_user`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`polygon_user` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`polygon_user` ("
                    + "`username` VARCHAR(45) NOT NULL,"
                    + "`pwd` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`fname` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`lname` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`email` VARCHAR(45) NULL DEFAULT NULL,"
                    + "`phone` VARCHAR(20) NULL DEFAULT NULL,"
                    + "`role` ENUM('employee', 'admin') NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`username`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`report`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report` ("
                    + "`report_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`report_date` DATE NULL DEFAULT NULL,"
                    + "`building_id` INT(10) NULL DEFAULT NULL,"
                    + "`category_conclusion` INT(1) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_id`),"
                    + "INDEX `building_id` (`building_id` ASC),"
                    + "CONSTRAINT `report_ibfk_1`"
                    + "  FOREIGN KEY (`building_id`)"
                    + "  REFERENCES `Polytest`.`building` (`idbuilding`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`report_exterior`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_exterior` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_exterior` ("
                    + "`report_ext_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`report_ext_description` VARCHAR(100) NULL DEFAULT NULL,"
                    + "`report_ext_pic` INT(10) NULL DEFAULT NULL,"
                    + "`report` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_ext_id`),"
                    + "INDEX `report` (`report` ASC),"
                    + "CONSTRAINT `report_exterior_ibfk_1`"
                    + "  FOREIGN KEY (`report`)"
                    + "  REFERENCES `Polytest`.`report` (`report_id`))");

//
//-- -----------------------------------------------------
//-- Table `Polytest`.`report_pic`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_pic` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_pic` ("
                    + "`report_pic_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`report` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_pic_id`),"
                    + "INDEX `report` (`report` ASC),"
                    + "CONSTRAINT `report_pic_ibfk_1`"
                    + "  FOREIGN KEY (`report`)"
                    + "  REFERENCES `Polytest`.`report` (`report_id`))");
//
//-- -----------------------------------------------------
//-- Table `Polytest`.`report_room`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room` ");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_room` ("
                    + "`report_room_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`room_name` VARCHAR(30) NULL DEFAULT NULL,"
                    + "`building_room` INT(10) NULL DEFAULT NULL,"
                    + "`report` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_room_id`),"
                    + "INDEX `report` (`report` ASC),"
                    + "INDEX `building_room` (`building_room` ASC),"
                    + "CONSTRAINT `report_room_ibfk_1`"
                    + "  FOREIGN KEY (`report`)"
                    + "  REFERENCES `Polytest`.`report` (`report_id`),"
                    + "CONSTRAINT `report_room_ibfk_2`"
                    + " FOREIGN KEY (`building_room`)"
                    + "  REFERENCES `Polytest`.`building_room` (`room_id`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`report_room_damage`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room_damage`");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_room_damage` ("
                    + "`report_room_damage_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`damage_time` DATE NULL DEFAULT NULL,"
                    + "`place` VARCHAR(30) NULL DEFAULT NULL,"
                    + "`what_happened` VARCHAR(30) NULL DEFAULT NULL,"
                    + "`what_is_repaired` VARCHAR(30) NULL DEFAULT NULL,"
                    + "`damage_type` VARCHAR(30) NULL DEFAULT NULL,"
                    + "`report_room` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_room_damage_id`),"
                    + "INDEX `report_room` (`report_room` ASC),"
                    + "CONSTRAINT `report_room_damage_ibfk_1`"
                    + "  FOREIGN KEY (`report_room`)"
                    + "  REFERENCES `Polytest`.`report_room` (`report_room_id`))");

//
//-- -----------------------------------------------------
//-- Table `Polytest`.`report_room_interior`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room_interior`");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_room_interior` ("
                    + "`report_room_interior_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`report_room_interior_name` VARCHAR(30) NULL DEFAULT NULL,"
                    + "`remark` VARCHAR(100) NULL DEFAULT NULL,"
                    + "`report_room` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_room_interior_id`),"
                    + "INDEX `report_room` (`report_room` ASC),"
                    + "CONSTRAINT `report_room_interior_ibfk_1`"
                    + "  FOREIGN KEY (`report_room`)"
                    + "  REFERENCES `Polytest`.`report_room` (`report_room_id`))");
//
//-- -----------------------------------------------------
//-- Table `Polytest`.`report_room_interior_pic`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room_interior_pic`");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_room_interior_pic` ("
                    + "`report_room_interior_pic_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`report_room_interior` INT(10) NULL DEFAULT NULL,"
                    + "`remark` VARCHAR(100) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_room_interior_pic_id`),"
                    + "INDEX `report_room_interior` (`report_room_interior` ASC),"
                    + "CONSTRAINT `report_room_interior_pic_ibfk_1`"
                    + "  FOREIGN KEY (`report_room_interior`)"
                    + "  REFERENCES `Polytest`.`report_room_interior` (`report_room_interior_id`))");

//
//-- -----------------------------------------------------
//-- Table `Polytest`.`report_room_moist`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room_moist`");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_room_moist` ("
                    + "`report_room_moist_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`report_room_moist_measured` INT(5) NULL DEFAULT NULL,"
                    + "`report_room_moist_place` VARCHAR(30) NULL DEFAULT NULL,"
                    + "`report_room_id` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_room_moist_id`),"
                    + "INDEX `report_room_id` (`report_room_id` ASC),"
                    + "CONSTRAINT `report_room_moist_ibfk_1`"
                    + "  FOREIGN KEY (`report_room_id`)"
                    + "  REFERENCES `Polytest`.`report_room` (`report_room_id`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`report_room_recommendation`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room_recommendation`");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_room_recommendation` ("
                    + "`report_room_recommendation_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`recommendation` VARCHAR(100) NULL DEFAULT NULL,"
                    + "`report_room` INT(10) NULL DEFAULT NULL,"
                    + "PRIMARY KEY (`report_room_recommendation_id`),"
                    + "INDEX `report_room` (`report_room` ASC),"
                    + "CONSTRAINT `report_room_recommendation_ibfk_1`"
                    + "  FOREIGN KEY (`report_room`)"
                    + "  REFERENCES `Polytest`.`report_room` (`report_room_id`))");

//
//-- -----------------------------------------------------
//-- Table `Polytest`.`zip_codes`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`zip_codes`");

            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`zip_codes` ("
                    + "`zipcode` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`city` VARCHAR(45) NOT NULL,"
                    + "PRIMARY KEY (`zipcode`))");

            // insert
            st.addBatch("insert into customer (companyname,street)"
            +"values ('CPH-Business','Nørregårsvej')");
            
            int[] updateCounts = st.executeBatch();

            // end transaction
            getConnection().commit();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Fail in JdbcTest - setup");
            System.out.println(e.getMessage());
        } finally {
        }
    }

    public void tearDown() throws SQLException {
        
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBFixture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
