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
    private static String URL = "jdbc:mysql://localhost:3306/polytest";
    private static String id = "root";
    private static String pw = "cjs110292";
   
    
    public void setUp() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, id, pw);
            Statement st = getConnection().createStatement();
            // start transaction
            getConnection().setAutoCommit(true);

            //Test setup start
            st.addBatch("drop table if exists building_pic, report_ext_pic, report_room_pic, orders, order_status, floorplan, "
                    + "report_room_damage,report_room_recommendation,"
                    + "report_room_moist,report_room_interior_pic,"
                    + "report_room_interior,report_exterior,report_room,report_pic,report,"
                    + "building_room, building_floor,order_request,building_documents,building,customer_user,"
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
            
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`building_floor` ");
            
            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`building_floor` ("
                    + "`floor_id` int(20) NOT NULL AUTO_INCREMENT,"
                    + "`floor_number` int(10) DEFAULT NULL,"
                    + "`floor_size` double NOT NULL,"
                    + " `total_rooms` int(100) DEFAULT NULL,"
                    + " `idbuilding` int(10) DEFAULT NULL,"
                    + "  PRIMARY KEY (`floor_id`),"
                    + " CONSTRAINT `building_floor_ibfk_1`"
                    + " FOREIGN KEY (`idbuilding`) "
                    + "REFERENCES `Polytest`.`building` (`idbuilding`))");
            
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`building_room` ");
            
            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`building_room` ("
                    + "`room_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`room_name` VARCHAR(40) NULL DEFAULT NULL,"
                    + "`floor_id` int(11) DEFAULT NULL,"
                    + "PRIMARY KEY (`room_id`),"
                    + "CONSTRAINT `building_room_ibfk_1`"
                    + "  FOREIGN KEY (`floor_id`)"
                    + "  REFERENCES `Polytest`.`building_floor` (`floor_id`))");
            
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
//-- Table `Polytest`.`order_status`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`orders_status` ");
            
            st.addBatch("CREATE TABLE IF NOT EXISTS  `order_status`( "
                    + "`order_status` int(11) NOT NULL,"
                    + "  `status_description` varchar(30) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  PRIMARY KEY (`order_status`))");

//-- -----------------------------------------------------
//-- Table `Polytest`.`orders`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`orders` ");
            
            st.addBatch("CREATE TABLE IF NOT EXISTS  `orders` ("
                    + "  `order_number` int(20) NOT NULL AUTO_INCREMENT,"
                    + "  `order_date` date DEFAULT NULL,"
                    + "  `service_description` varchar(50) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  `problem_statement` varchar(200) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  `order_status` int(5) DEFAULT NULL,"
                    + "  `customer_id` int(10) DEFAULT NULL,"
                    + "  `idbuilding` int(10) DEFAULT NULL,"
                    + "  PRIMARY KEY (`order_number`),"
                    + "CONSTRAINT `orders_ibfk_1` "
                    + "FOREIGN KEY (`customer_id`) "
                    + "REFERENCES `customer` (`customer_id`),"
                    + "CONSTRAINT `orders_ibfk_2` "
                    + "FOREIGN KEY (`idbuilding`) "
                    + "REFERENCES `building` (`idbuilding`))");

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
                    + "`polygonuser` varchar(30) DEFAULT NULL,"
                    + "`customer_user` varchar(30) DEFAULT NULL,"
                    + "`category_conclusion` INT(1) NULL DEFAULT NULL,"
                    + "`report_finished` tinyint(1) NOT NULL,"
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
                    + "`rep_ext_inspected_area` varchar(45) DEFAULT NULL,"
                    + "PRIMARY KEY (`report_ext_id`),"
                    + "INDEX `report` (`report` ASC),"
                    + "CONSTRAINT `report_exterior_ibfk_1`"
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
                    + "`damage_time` varchar(100) DEFAULT NULL,"
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
//-- Table `Polytest`.`report_room_moist`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room_moist`");
            
            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`report_room_moist` ("
                    + "`report_room_moist_id` INT(10) NOT NULL AUTO_INCREMENT,"
                    + "`report_room_moist_measured` varchar(100) NULL DEFAULT NULL,"
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
//-- Table `Polytest`.`building_pic`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`building_pic`");
            
            st.addBatch("CREATE TABLE `building_pic` ("
                    + "  `building_pic_id` int(10) NOT NULL AUTO_INCREMENT,"
                    + "  `building_id` int(10) DEFAULT NULL,"
                    + "  `filename` varchar(30) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  PRIMARY KEY (`building_pic_id`),"
                    + "CONSTRAINT `building_pic_ibfk_1` "
                    + "FOREIGN KEY (`building_id`) "
                    + "REFERENCES `building` (`idbuilding`))");

//
//-- -----------------------------------------------------
//-- Table `Polytest`.`report_ext_pic`
//-- -----------------------------------------------------            
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_ext_pic`");
            
            st.addBatch("CREATE TABLE `report_ext_pic` ("
                    + "  `report_pic_id` int(10) NOT NULL AUTO_INCREMENT,"
                    + "  `filename` varchar(30) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  `description` varchar(200) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  `report_id` int(10) DEFAULT NULL,"
                    + "  PRIMARY KEY (`report_pic_id`),CONSTRAINT `report_ext_pic_ibfk_1` "
                    + "  FOREIGN KEY (`report_id`) "
                    + "  REFERENCES `report` (`report_id`))");

//
//-- -----------------------------------------------------
//-- Table `Polytest`.`report_room_pic`
//-- -----------------------------------------------------            
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`report_room_pic`");
            
            st.addBatch("CREATE TABLE `report_room_pic` ("
                    + "  `report_pic_id` int(10) NOT NULL AUTO_INCREMENT,"
                    + "  `description` varchar(200) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  `filename` varchar(30) COLLATE utf8_danish_ci DEFAULT NULL,"
                    + "  `reportroom` int(10) DEFAULT NULL,"
                    + "  PRIMARY KEY (`report_pic_id`),CONSTRAINT `report_room_pic_ibfk_1`"
                    + "  FOREIGN KEY (`reportroom`) "
                    + "  REFERENCES `report_room` (`report_room_id`))");

//
//-- -----------------------------------------------------
//-- Table `Polytest`.`zip_codes`
//-- -----------------------------------------------------
            st.addBatch("DROP TABLE IF EXISTS `Polytest`.`zip_codes`");
            
            st.addBatch("CREATE TABLE IF NOT EXISTS `Polytest`.`zip_codes` ("
                    + "`zipcode` INT(11) NOT NULL AUTO_INCREMENT,"
                    + "`city` VARCHAR(45) NOT NULL,"
                    + "PRIMARY KEY (`zipcode`))");

            // insert in all: 1 customer, 1 building, 1 Polygon user, 1 building_floor, 2 building rooms
            st.addBatch("insert into customer (companyname,street)"
                    + "values ('CPH-Business','Nørregårsvej')");
            st.addBatch("INSERT INTO `polytest`.`building` (`idbuilding`, `building_name`, `building_m2`, `building_adress`, `building_housenumber`, `building_buildyear`, `building_zip`, `building_pic`, `building_use`, `customer_id`) "
                    + "VALUES ('1', 'TestBuilding', '201', 'Sverige', '281', '1921', '2812', '0', 'Nothing', '1')");
            st.addBatch("INSERT INTO `polytest`.`polygon_user` (`username`, `pwd`, `fname`, `lname`, `email`, `phone`, `role`) "
                    + "VALUES ('emp1', 'hej', 'daniel', 'test', 'ingen', '272', 'employee');");
            st.addBatch("INSERT INTO `polytest`.`building_floor` (`floor_id`, `floor_number`, `floor_size`, `total_rooms`, `idbuilding`) "
                    + "VALUES ('1', '2', '28', '21', '1');");
            st.addBatch("INSERT INTO `polytest`.`building_room` (`room_name`, `floor_id`) "
                    + "VALUES ('Toilet', '1');");
            st.addBatch("INSERT INTO `polytest`.`building_room` (`room_id`, `room_name`, `floor_id`) "
                    + "VALUES ('2', 'Hallway', '1');");
            
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
    
    public void closeConnection() {
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
