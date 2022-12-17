/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static org.milaifontanals.info.projecte1.BDClient.con;
import org.milaifontanals.info.projecte1.model.Canso;
import org.milaifontanals.info.projecte1.model.Client;

/**
 *
 * @author Toni Puig
 */
public class BDCansons {
    static Connection con = DBManager.getConnection();//AIXO ES TIPO SINGELTON: MIRAR LA CLASSE BDManager
    private static PreparedStatement ps_llistacansons;

    public BDCansons() throws GestorBDSportifyException {
        String sql=null;
           try {
            sql = "select p.pro_id,p.pro_titol,c.can_anyCreacio,c.can_durada from producte p inner join canso c on p.pro_id=c.can_id where p.pro_titol like ?";
            ps_llistacansons = con.prepareStatement(sql);
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("No es pot crear el PreparedStatement:" +sql+"  - ERROR: "+ ex.getMessage());
        }
    }

    public List<Canso> getLlistaCansons(String frag) throws GestorBDSportifyException {
        List<Canso> llCanso = new ArrayList<>();
        Canso c = null;
        try{
          // PreparedStatement ps = con.prepareStatement("select p.pro_id,p.pro_titol,c.can_anyCreacio,c.can_durada from producte p inner join canso c on p.pro_id=c.can_id");
           String text="%"+frag+"%";
           ps_llistacansons.setString(1, text);
           ResultSet rs = ps_llistacansons.executeQuery();
            while (rs.next()) {
                c =  new Canso(rs.getDate("can_anyCreacio"), rs.getInt("can_durada"),rs.getInt("pro_id"), rs.getString("pro_titol"));
                llCanso.add(c);
                c=null;
            }
            rs.close();
       } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar llista cansons\n" + ex.getMessage());
        } 
        return llCanso;
    }
}
