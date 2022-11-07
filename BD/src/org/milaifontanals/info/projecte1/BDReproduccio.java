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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.milaifontanals.info.projecte1.model.Producte;
import org.milaifontanals.info.projecte1.model.*;

/**
 *
 * @author Toni Puig
 */
public class BDReproduccio {

    static Connection con = DBManager.getConnection();

    private PreparedStatement eliminarReproduccio;
    private PreparedStatement modificarReproduccio;
    private PreparedStatement afegirReproduccio;
    private PreparedStatement qDelListProducte;

    public List<Reproduccio> getListProducte(int idClient, String fragNomProd, Date inici, Date fi) throws GestorBDSportifyException {
        List<Reproduccio> llRep = new ArrayList<>();
        Statement q = null;
        Client cli = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT CLI_EMAIL,CLI_NOM,CLI_COGNOMS,CLI_DATANEIX FROM client where cli_id=?");
            ps.setInt(1, idClient);
            ResultSet resultclient = ps.executeQuery();
            while (resultclient.next()) {
                cli = new Client(idClient,resultclient.getString("CLI_EMAIL"), resultclient.getString("CLI_NOM"), resultclient.getString("CLI_COGNOMS"), resultclient.getDate("CLI_DATANEIX"));
            }
            resultclient.close();
            
            PreparedStatement psR = con.prepareStatement("SELECT p.pro_id,r.rep_idclient,p.pro_estil,r.rep_momenttemp,p.pro_actiu,p.pro_tipus,p.pro_titol FROM reproduccio r inner join producte p on r.rep_idproducte=p.pro_id where r.rep_idclient=? AND p.pro_titol LIKE ? AND r.rep_momenttemp BETWEEN ? AND ?");
            psR.setInt(1, idClient);
            String text="%"+fragNomProd+"%";
            //System.out.println(text);
            psR.setString(2, text);
            
            psR.setDate(3, inici);
            psR.setDate(4, fi);
            ResultSet resultRep = psR.executeQuery();
            while (resultRep.next()) {
                Producte pro = null;
                Reproduccio r=null;
                String tipo=resultRep.getString(6);
                switch (tipo) {
                    case "A":
                        pro=new Album(resultRep.getInt(1),resultRep.getString(7));
                        r = new Reproduccio(resultRep.getTimestamp(4), cli, pro);
                        llRep.add(r);
                        break;
                    case "L":
                        pro = new LlistaReproduccio(resultRep.getInt(1),resultRep.getString(7));
                        r = new Reproduccio(resultRep.getTimestamp(4), cli, pro);
                        llRep.add(r);
                        break;
                    case "C":
                        pro = new Canso(resultRep.getInt(1),resultRep.getString(7));
                        r = new Reproduccio(resultRep.getTimestamp(4), cli, pro);
                        llRep.add(r);
                        break;
                }
            }
            resultRep.close();
            /*
            ResultSet rs = q("SELECT * FROM client where id=?");

            ResultSet rs = q.executeQuery("SELECT * FROM reproduccio");
            while (rs.next()) {
                llRep.add(new Reproduccio(rs.getInt("prod_num"), rs.getString("descripcio")));
            }*/
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
        return llRep;
    }
    public void afegirReproduccio(Reproduccio rep) throws GestorBDSportifyException{
        try {
            afegirReproduccio=con.prepareStatement( "INSERT INTO reproduccio values (?,?,?)");
            Long idrep= rep.getClient().getId();
            afegirReproduccio.setLong(1, idrep);
            afegirReproduccio.setDate(2,new java.sql.Date(rep.getTimestamp().getTime()) );
            afegirReproduccio.setLong(3, rep.getProducte().getId());
            afegirReproduccio.execute();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar afegir reporudccio"+ex.getMessage());
        }
    }
    
    public void modificarReproduccio(Reproduccio orig,int id_prod) throws GestorBDSportifyException{
        try {
            modificarReproduccio=con.prepareStatement( "update reproduccio set rep_idproducte=? where rep_idclient=? and rep_momenttemp=?");
            modificarReproduccio.setInt(1, id_prod);
            //Long idrep= dest.getClient().getId();
            //modificarReproduccio.setLong(1, idrep);
//            modificarReproduccio.setLong(2, dest.getProducte().getId());
//            modificarReproduccio.setDate(3,new java.sql.Date(dest.getTimestamp().getTime()) );
            modificarReproduccio.setLong(2, orig.getClient().getId());
            modificarReproduccio.setTimestamp(3,orig.getTimestamp());
            modificarReproduccio.executeUpdate();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en actualitzar repro: "+ex.getMessage());
        }
    }
    

    public void eliminarReproduccio(Reproduccio rep) throws GestorBDSportifyException{
        try {
            eliminarReproduccio=con.prepareStatement( "delete from reproduccio where rep_idclient=? and REP_MOMENTTEMP=?");
            Long idrep= rep.getClient().getId();
            System.out.println("d");
            eliminarReproduccio.setLong(1, idrep);
            eliminarReproduccio.setTimestamp(2, rep.getTimestamp());
            //eliminarReproduccio.setLong(2, rep.getProducte().getId());
            eliminarReproduccio.execute();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar eliminar repro");
        }
    }

    /* public void afegirReproduccio(Reproduccio p) throws GestorBDSportifyException {
        try {
            qAddProducte.setInt(1, p.getProdNum());
            qAddProducte.setString(2, p.getDescripcio());
            qAddProducte.executeUpdate();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar inserir el producte " + p.getProdNum() + "\n" + ex.getMessage());
        }
    }*/
    public static void main(String[] args) {

    }

}
