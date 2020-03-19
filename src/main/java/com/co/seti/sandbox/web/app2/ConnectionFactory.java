/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.seti.sandbox.web.app2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author andreslavado
 */
public class ConnectionFactory {


    private ConnectionFactory() {

    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Map<String, String> environment = System.getenv();
        String db_host = environment.getOrDefault("DATABASE_HOST", "localhost");
        String db_port = environment.getOrDefault("DATABASE_PORT", "3306");
        String db_name = environment.getOrDefault("DATABASE_NAME", "cardb");
        String db_username = environment.getOrDefault("DATABASE_USERNAME", "root");
        String db_password = environment.getOrDefault("DATABASE_PASSWORD", "");

        String url = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, db_username, db_password);
        System.out.println("Connection established");
        return connection;
    }
}
