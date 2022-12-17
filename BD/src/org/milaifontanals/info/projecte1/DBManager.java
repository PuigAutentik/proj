package org.milaifontanals.info.projecte1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.milaifontanals.info.projecte1.BDProductes.con;

public class DBManager {

    private static Connection con = null;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File("configuracions.properties")));
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");
            System.out.println(url + user + pass);
            con = DriverManager.getConnection(url, user, pass);
            con.setAutoCommit(false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConnection() {
        //SINGELTON
        return con;
    }

    public void validarCanvis() throws GestorBDSportifyException {
        try {
            con.commit();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en validar els canvis: " + ex.getMessage());
        }
    }

    public void desferCanvis() throws GestorBDSportifyException {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en desfer els canvis: " + ex.getMessage());
        }
    }
}
