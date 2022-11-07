/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static org.milaifontanals.info.projecte1.BDReproduccio.con;
import org.milaifontanals.info.projecte1.model.Album;
import org.milaifontanals.info.projecte1.model.Canso;
import org.milaifontanals.info.projecte1.model.Client;
import org.milaifontanals.info.projecte1.model.LlistaReproduccio;
import org.milaifontanals.info.projecte1.model.Producte;
import org.milaifontanals.info.projecte1.model.Reproduccio;

/**
 *
 * @author Toni Puig
 */
public class BDClient {
     static Connection con = DBManager.getConnection();
     
      public Client getClient(int id) throws GestorBDSportifyException {
        Statement q = null;
        Client cli = null;
        try{
           PreparedStatement ps = con.prepareStatement("SELECT CLI_ID,CLI_NOM,CLI_COGNOMS FROM client where cli_id=?");
           ps.setInt(1, id);
           ResultSet resultclients = ps.executeQuery();
            while (resultclients.next()) {
                cli = new Client(resultclients.getInt("CLI_ID"),resultclients.getString("CLI_NOM"), resultclients.getString("CLI_COGNOMS"));
            }
            resultclients.close();
       } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar llista clients\n" + ex.getMessage());
        } 
        return cli;
    }
     
     
     
     
      public List<Client> getLlistaClients() throws GestorBDSportifyException {
        List<Client> llClients = new ArrayList<>();
        Statement q = null;
        Client cli = null;
        try{
           PreparedStatement ps = con.prepareStatement("SELECT CLI_ID,CLI_NOM,CLI_COGNOMS FROM client");
           ResultSet resultclients = ps.executeQuery();
            while (resultclients.next()) {
                cli = new Client(resultclients.getInt("CLI_ID"),resultclients.getString("CLI_NOM"), resultclients.getString("CLI_COGNOMS"));
                llClients.add(cli);
                cli=null;
            }
            resultclients.close();
       } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar llista clients\n" + ex.getMessage());
        } 
        return llClients;
    }
}
