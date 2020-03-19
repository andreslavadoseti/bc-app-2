/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.seti.sandbox.web.app2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author andreslavado
 */
@Singleton
@Startup
public class CarDBInit {
    @PostConstruct
    public void initialize() {
        System.out.println("com.co.seti.sandbox.web.app2.CarDBInit.initialize()");
        Connection connection;
        try {
            System.out.println("Searching for Car table");
            connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("SHOW TABLES LIKE 'Car'");
            ResultSet result = statement.executeQuery();
            if (!result.next()){
                System.out.println("Car table don't found.");
                result.close();
                statement.close();
                connection.close();
                connection = ConnectionFactory.getConnection();
                connection.setAutoCommit(false);
                System.out.println("Create Car table");
                String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Car(\n" +
                "	car_id INTEGER NOT NULL AUTO_INCREMENT,\n" +
                "	cname VARCHAR(60) NOT NULL,\n" +
                "	color VARCHAR(60),\n" +
                "	speed INTEGER,\n" +
                "	Manufactured_Country VARCHAR(100),\n" +
                "	PRIMARY KEY(car_id))";
                Statement statementCreate = connection.createStatement();
                statementCreate.executeUpdate(sqlCreateTable);
                connection.commit();
                statementCreate.close();
                System.out.println("Insert Car data");
                Statement insertStatement = connection.createStatement();
                insertStatement.addBatch("INSERT INTO Car VALUES(1,'Zen','Grey',45.34,'India')");
                insertStatement.addBatch("INSERT INTO Car VALUES(2,'Volkswagen','Black',49.64,'Germany')");
                insertStatement.addBatch("INSERT INTO Car VALUES(3,'Polo','White',52.33,'Japan')");
                insertStatement.addBatch("INSERT INTO Car VALUES(4,'Audi','Blue',55.98,'Germany')");
                insertStatement.addBatch("INSERT INTO Car VALUES(5,'Innova','Maroon',39.97,'France')");
                insertStatement.addBatch("INSERT INTO Car VALUES(6,'FiatPalio','Silver',35.45,'Italy')");
                insertStatement.addBatch("INSERT INTO Car VALUES(7,'Qualis','Red',23.35,'Paris')");
                int [] updateCounts = insertStatement.executeBatch();
                connection.commit();
                insertStatement.close();
                System.out.println("Create and insert end");
            } else {
                result.close();
                statement.close();
            }
            connection.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CarDBInit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CarDBInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
