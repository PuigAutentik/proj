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
public class BDProductes {
    
    static Connection con = DBManager.getConnection();
     
    
      public Producte getProducte(int id) throws GestorBDSportifyException {
        Producte pro =null;
        Statement q = null;
        try {
            PreparedStatement psR = con.prepareStatement("SELECT p.pro_id,p.pro_estil,p.pro_actiu,p.pro_tipus,p.pro_titol FROM producte p where p.pro_id=?");
            psR.setInt(1, id);
            ResultSet resultProd = psR.executeQuery();
            while (resultProd.next()) {
                String tipo=resultProd.getString(4);
                switch (tipo) {
                    case "A":
                        pro=new Album(resultProd.getInt(1),resultProd.getString(5));
                        break;
                    case "L":
                        pro=new LlistaReproduccio(resultProd.getInt(1),resultProd.getString(5));
                        break;
                    case "C":
                        pro=new Canso(resultProd.getInt(1),resultProd.getString(5));
                        break;
                }
            }
            resultProd.close();
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar Reroducions\n" + ex.getMessage());
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    throw new GestorBDSportifyException("Error en intentar tancar la sentència que ha recuperat la llista de repros.\n" + ex.getMessage());
                }
            }
        }
        return pro;
      }
        
        
     
      public List<Producte> getLlistaProductes() throws GestorBDSportifyException {
        List<Producte> llProd = new ArrayList<>();
        Statement q = null;
        try {
            PreparedStatement psR = con.prepareStatement("SELECT p.pro_id,p.pro_estil,p.pro_actiu,p.pro_tipus,p.pro_titol FROM producte p");
            ResultSet resultProd = psR.executeQuery();
            while (resultProd.next()) {
                Producte pro = null;
                Reproduccio r=null;
                String tipo=resultProd.getString(4);
                switch (tipo) {
                    case "A":
                        pro=new Album(resultProd.getInt(1),resultProd.getString(5));
                        llProd.add(pro);
                        break;
                    case "L":
                        pro=new LlistaReproduccio(resultProd.getInt(1),resultProd.getString(5));
                        llProd.add(pro);
                        break;
                    case "C":
                        pro=new Canso(resultProd.getInt(1),resultProd.getString(5));
                        llProd.add(pro);
                        break;
                }
            }
            resultProd.close();
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar Reroducions\n" + ex.getMessage());
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    throw new GestorBDSportifyException("Error en intentar tancar la sentència que ha recuperat la llista de productes.\n" + ex.getMessage());
                }
            }
        }
        return llProd;
    }
}
