/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Domain.Report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author CJS
 */
public class ReportMapper {
    public boolean saveNewReport(Report r, Connection con) {
        String SQLString = "insert into Report values (?,?,?,?)";
        try (PreparedStatement statement
                = con.prepareStatement(SQLString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, r.getReportNum());
            statement.setDate(2, r.getDate());
            statement.setInt(3, r.getBdgId());
            statement.setInt(4, r.getCategoryConclusion());
            int rowsInserted = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            return rowsInserted == 1;
        } catch (Exception e) {
            System.out.println("Fail in saving new report - saveNewReport");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
