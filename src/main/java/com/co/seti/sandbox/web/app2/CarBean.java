/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.seti.sandbox.web.app2;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "carBean")
@SessionScoped
/**
 * @author andreslavado
 */
public class CarBean implements Serializable {

    private static final long serialVersionUID = 5789957489L;

    public List<Car> getCars() throws ClassNotFoundException, SQLException {
        System.out.println("Getting cars");
        Connection connect = ConnectionFactory.getConnection();
        List<Car> cars = new ArrayList<Car>();
        PreparedStatement pstmt = connect
                .prepareStatement("SELECT car_id, cname, color, speed, Manufactured_Country FROM Car");
        ResultSet rs = pstmt.executeQuery();
         System.out.println("Execute Query");
        while (rs.next()) {
            System.out.println("Creating car");
            Car car = new Car();
            car.setCid(rs.getInt("car_id"));
            car.setCname(rs.getString("cname"));
            car.setColor(rs.getString("color"));
            car.setSpeed(rs.getInt("speed"));
            car.setMfdctry(rs.getString("Manufactured_Country"));
            cars.add(car);
        }
        System.out.println("Closing connection");
        // close resources
        rs.close();
        pstmt.close();
        connect.close();
        return cars;
    }

}
