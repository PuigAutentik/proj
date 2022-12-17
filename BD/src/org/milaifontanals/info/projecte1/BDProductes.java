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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static org.milaifontanals.info.projecte1.BDReproduccio.con;
import org.milaifontanals.info.projecte1.model.Album;
import org.milaifontanals.info.projecte1.model.AlbumContingut;
import org.milaifontanals.info.projecte1.model.Artista;
import org.milaifontanals.info.projecte1.model.ArtistaIndividual;
import org.milaifontanals.info.projecte1.model.Canso;
import org.milaifontanals.info.projecte1.model.Client;
import org.milaifontanals.info.projecte1.model.Estil;
import org.milaifontanals.info.projecte1.model.Grup;
import org.milaifontanals.info.projecte1.model.Llista;
import org.milaifontanals.info.projecte1.model.LlistaContingut;
import org.milaifontanals.info.projecte1.model.LlistaReproduccio;
import org.milaifontanals.info.projecte1.model.Producte;
import org.milaifontanals.info.projecte1.model.Reproduccio;

/**
 *
 * @author Toni Puig
 */
public class BDProductes {

    //AIXO ES SINGELTON, MIRAR CLASE BDManager
    static Connection con = DBManager.getConnection();
    private static PreparedStatement ps_llistaproductes;
    private static PreparedStatement ps_agafarproducte;
    private static PreparedStatement ps_borrarproducte;
    private static PreparedStatement ps_artistes;
    private static PreparedStatement ps_inserirproducte;
    private static PreparedStatement ps_updateproducte;
    private static PreparedStatement ps_estils;
    private static PreparedStatement ps_ultimproduct;
    private static PreparedStatement ps_novacanso;
    private static PreparedStatement ps_novallista;
    private static PreparedStatement ps_afegirproductellista;
    private static PreparedStatement ps_afegirproductealbum;
    private static PreparedStatement ps_novalbum;
    private static PreparedStatement ps_cansonsalbum;
    private static PreparedStatement ps_cansonsllista;
    private static PreparedStatement ps_modificaproducte;
    private static PreparedStatement ps_eliminarContingutLlista;
    private static PreparedStatement ps_eliminarContingutAlbum;

    public BDProductes() throws GestorBDSportifyException {
        String sql = null;
        try {
            sql = "select pro_id, pro_titol,pro_actiu,pro_estil,est_nom,pro_tipus from producte p \n"
                    + "join estil on p.pro_estil=estil.est_id\n"
                    + "where (?=3 or p.pro_actiu=?) and pro_tipus in (?,?,?) and (?='' or UPPER(p.pro_titol) like ?) order by pro_titol asc";
            ps_llistaproductes = con.prepareStatement(sql);

            sql = "SELECT p.pro_id,p.pro_estil,p.pro_actiu,p.pro_tipus,p.pro_titol,c.can_anycreacio,c.can_durada,c.can_interpret,a.alb_anycreacio,a.alb_dura,ll.lli_durada FROM producte p\n" +
"full outer join canso c on p.pro_id=c.can_id\n" +
"full OUTER join album a on p.pro_id=a.alb_id\n" +
"FULL OUTER JOIN llista ll on p.pro_id=ll.lli_id\n" +
"where p.pro_id=?";
            ps_agafarproducte = con.prepareStatement(sql);
            
            sql = "delete from producte where pro_id=?";
            ps_borrarproducte = con.prepareStatement(sql);
            
            sql = "select art_id,art_nom,art_tipus from artista";
            ps_artistes = con.prepareStatement(sql);
            
            sql = "INSERT INTO producte(pro_titol,pro_estil,pro_tipus,pro_actiu) VALUES (?,?,?,1)";
            ps_inserirproducte = con.prepareStatement(sql);
            
            sql = "select pro_id from producte order by pro_id desc";
            ps_ultimproduct = con.prepareStatement(sql);
            
            sql = "UPDATE producte\n" +
"SET pro_titol = ?,pro_estil = ?,pro_tipus = ? where pro_id=?";
            ps_updateproducte = con.prepareStatement(sql);
            
            sql = "select est_id,est_nom from estil order by est_id";
            ps_estils = con.prepareStatement(sql);
            
            sql = "insert into canso values (?,?,?,?)";
            ps_novacanso = con.prepareStatement(sql);
            
            sql = "insert into llista values (?,?)";
            ps_novallista = con.prepareStatement(sql);
            
            sql = "insert into llista_contingut values (?,?,?)";
            ps_afegirproductellista = con.prepareStatement(sql);
            
            sql = "insert into album(alb_id,alb_anycreacio,alb_dura) values (?,?,?)";
            ps_novalbum = con.prepareStatement(sql);
            
            sql = "insert into album_contingut values (?,?,?)";
            ps_afegirproductealbum = con.prepareStatement(sql);
            
            sql = "select a.alc_idc,p.pro_titol from album_contingut a inner join producte p on p.pro_id=a.alc_idc where a.alc_ida=?";
            ps_cansonsalbum = con.prepareStatement(sql);
            
            sql="select l.llc_id_producte ,p.pro_titol from llista_contingut l inner join producte p on p.pro_id=l.llc_id_producte where l.llc_id_llista=?";
            ps_cansonsllista = con.prepareStatement(sql);
            
            sql="UPDATE producte SET pro_titol = ?,pro_estil = ? WHERE pro_id=?";
            ps_modificaproducte = con.prepareStatement(sql);
            
            sql="delete from album_contingut where alc_ida=?";
            ps_eliminarContingutAlbum = con.prepareStatement(sql);
            
            sql="delete from llista_contingut where llc_id_llista=?";
            ps_eliminarContingutLlista = con.prepareStatement(sql);
            
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("No es pot crear el PreparedStatement:" + sql + "  - ERROR: " + ex.getMessage());
        }
    }
    
    public void eliminarContingutAlbum(int id) throws SQLException{
        ps_eliminarContingutAlbum.setInt(1, id);
        ps_eliminarContingutAlbum.execute();
    }
    
    public void eliminarContingutLlista(int id) throws SQLException{
        ps_eliminarContingutLlista.setInt(1, id);
        ps_eliminarContingutLlista.execute();
    }
    
    public int modificarProducte(Producte p) throws GestorBDSportifyException{
        try {
            ps_modificaproducte.setString(1, p.getTitol());
            ps_modificaproducte.setInt(2, (int)p.getEstil().getId());
            ps_modificaproducte.setInt(3, (int)p.getId());
            return ps_modificaproducte.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BDProductes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public List<Producte> getCansonsAlbum(long id)throws GestorBDSportifyException, SQLException{
            ps_cansonsalbum.setLong(1,id);
            List<Producte> llista = new ArrayList<>();
            ResultSet resultProd = ps_cansonsalbum.executeQuery();
            while (resultProd.next()) {
                    Canso c = new Canso(resultProd.getInt(1),resultProd.getString(2));
                    llista.add(c);
                }
            resultProd.close();
            return llista;
    }
    
    
        public List<Producte> getCansonsLlista(long id)throws GestorBDSportifyException, SQLException{
            ps_cansonsllista.setLong(1,id);
            List<Producte> llista = new ArrayList<>();
            ResultSet resultProd = ps_cansonsllista.executeQuery();
            while (resultProd.next()) {
                    Canso c = new Canso(resultProd.getInt(1),resultProd.getString(2));
                    llista.add(c);
                }
            resultProd.close();
            return llista;
    }

    public Producte getProducte(int id) throws GestorBDSportifyException {
        Producte pro = null;
        Statement q = null;
        try {
            ps_agafarproducte.setInt(1, id);
            ResultSet resultProd = ps_agafarproducte.executeQuery();
            while (resultProd.next()) {
                String tipo = resultProd.getString(4);
                Estil est=null;
                switch (tipo) {
                    case "A":
                        est = new Estil(resultProd.getLong(2), "");
                        pro = new Album(resultProd.getLong(1), resultProd.getDate(9),resultProd.getString(5), resultProd.getBoolean(3), est, resultProd.getString(4),resultProd.getInt(10));
                        break;
                    case "L":
                        est = new Estil(resultProd.getLong(2), "");
                        pro = new Llista(resultProd.getInt(1), resultProd.getString(5),"L",est,resultProd.getInt(11));
                        break;
                    case "C":
                        est = new Estil(resultProd.getLong(2), "");
                        pro = new Canso(resultProd.getLong(1),resultProd.getString(5), resultProd.getBoolean(3), est, resultProd.getString(4), resultProd.getDate(6),resultProd.getInt(7),resultProd.getInt(8));
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

    public List<Producte> getProductes(String frag, int estat, List<String> tipus) throws GestorBDSportifyException {
        List<Producte> llProd = new ArrayList<>();
        try {
            ps_llistaproductes.setInt(1, estat);
            ps_llistaproductes.setInt(2, estat);
            int i=3;
            for(String str : tipus)
                    {
                        ps_llistaproductes.setString(i, str);
                        i++;
                    }
            ps_llistaproductes.setString(6,frag);
            ps_llistaproductes.setString(7,"%"+frag.toUpperCase()+"%");
            ResultSet resultProd = ps_llistaproductes.executeQuery();
            while (resultProd.next()) {
                Producte pro = null;
                Estil est=null;
                String tipo = resultProd.getString(6);
                switch (tipo) {
                    case "A":
                        est = new Estil(resultProd.getInt(4), resultProd.getString(5));
                        pro = new Album(resultProd.getLong(1), resultProd.getString(2), resultProd.getBoolean(3), resultProd.getString(6), est);
                        //pro=new Album(resultProd.getInt(1),resultProd.getString(5));
                        llProd.add(pro);
                        break;
                    case "L":
                        est = new Estil(resultProd.getInt(4), resultProd.getString(5));
                        pro = new Llista(resultProd.getLong(1), resultProd.getString(2), resultProd.getBoolean(3), resultProd.getString(6), est);
                        llProd.add(pro);
                        break;
                    case "C":
                        est = new Estil(resultProd.getInt(4), resultProd.getString(5));
                        pro = new Canso(resultProd.getLong(1), resultProd.getString(2), resultProd.getBoolean(3), resultProd.getString(6), est);
                        llProd.add(pro);
                        break;
                }
            }
            resultProd.close();
            return llProd;
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar Productes\n" + ex.getMessage());
        }
    }
    
    public int getUltimProducte() throws GestorBDSportifyException, SQLException{
         ResultSet rs = ps_ultimproduct.executeQuery();
         rs.next();
        return rs.getInt(1);

    }
    
     public void eliminarProducte(long id) throws GestorBDSportifyException{
        try {
            String sql = "delete from producte where pro_id=?";
            PreparedStatement ps_test = con.prepareStatement(sql);
            ps_test.setLong(1, id);
            ps_test.executeUpdate();
            System.out.println("ok");
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar eliminar producte");
        }
    }
     
      public void afegirProducteAlbum(AlbumContingut p) throws GestorBDSportifyException{
        try {
            ps_afegirproductealbum.setInt(1, p.getId());
            ps_afegirproductealbum.setInt(2, p.getId_producte());
            ps_afegirproductealbum.setInt(3, p.getPos());
            ps_afegirproductealbum.execute();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar afegir productes al ALBUM");
        }
    }
     
     
    public void inserirAlbum(Album p) throws GestorBDSportifyException{
        try {
            ps_novalbum.setLong(1, p.getId());
            ps_novalbum.setDate(2,new java.sql.Date(p.getAnyCreacio().getTime()));
            ps_novalbum.setInt(3, p.getDurada());
            ps_novalbum.execute();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar crear ALBUM");
        }
    }
     
    public void afegirProducteLlista(LlistaContingut p) throws GestorBDSportifyException{
        try {
            ps_afegirproductellista.setLong(1, p.getId());
            ps_afegirproductellista.setInt(2, p.getId_producte());
            ps_afegirproductellista.setInt(3, p.getPos());
            ps_afegirproductellista.execute();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar afegir productes a la llista");
        }
    }
     
     
    public void inserirLlista(Llista p) throws GestorBDSportifyException{
        try {
            ps_novallista.setLong(1, p.getLli_id());
            ps_novallista.setInt(2, p.getLli_durada());
            ps_novallista.execute();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar crear llista");
        }
    }
     
    public void inserirCanso(Canso p) throws GestorBDSportifyException{
        try {
            ps_novacanso.setLong(1, p.getId());
            ps_novacanso.setDate(2, new java.sql.Date(p.getAnyCreacio().getTime()));
            ps_novacanso.setInt(3, p.getInterpret());
            ps_novacanso.setInt(4, p.getDurada());
            ps_novacanso.execute();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar crear canso");
        }
    }
     
    public void inserirProducte(Producte p) throws GestorBDSportifyException{
        try {
            ps_inserirproducte.setString(1, p.getTitol());
            ps_inserirproducte.setLong(2, p.getEstil().getId());
            ps_inserirproducte.setString(3, p.getTipus());
            ps_inserirproducte.executeUpdate();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en intentar afegir producte");
        }
    }
    
    
//    public void modificarProducte(Producte p) throws GestorBDSportifyException{
//        try {
//            ps_updateproducte.setString(1, p.getTitol());
//            ps_updateproducte.setInt(2, (int)p.getEstil().getId());
//            ps_updateproducte.setString(3, p.getTipus());
//            ps_updateproducte.setLong(4, p.getId());
//            ps_updateproducte.execute();
//            System.out.println("ok");
//        } catch (SQLException ex) {
//            throw new GestorBDSportifyException("Error en intentar afegir producte");
//        }
//    }
     
       public List<Artista> getArtistes() throws GestorBDSportifyException {
        List<Artista> llart = new ArrayList<>();
        try{
            ResultSet resultProd = ps_artistes.executeQuery();
            while (resultProd.next()) {
                Artista art = null;
                String tipo = resultProd.getString(3);
                switch (tipo) {
                    case "I":
                        art = new ArtistaIndividual(resultProd.getInt(1), resultProd.getString(2));
                        llart.add(art);
                        break;
                    case "G":
                        art = new Grup(resultProd.getInt(1), resultProd.getString(2));
                        llart.add(art);
                        break; 
                    }
            }
            resultProd.close();
            return llart;
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar Productes\n" + ex.getMessage());
        }
    }
       
    public List<Estil> getEstils() throws GestorBDSportifyException {
        List<Estil> llestil = new ArrayList<>();
        try{
            ResultSet resultProd = ps_estils.executeQuery();
            while (resultProd.next()) {
                Estil e = new Estil(resultProd.getInt(1), resultProd.getString(2));
                llestil.add(e);
            }
            resultProd.close();
            return llestil;
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            throw new GestorBDSportifyException("Error en intentar recuperar Estils\n" + ex.getMessage());
        }
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
