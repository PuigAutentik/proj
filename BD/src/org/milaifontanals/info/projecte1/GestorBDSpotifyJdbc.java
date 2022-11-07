package org.milaifontanals.info.projecte1;

//import org.milaifontanals.empresa.model.Producte;
import java.io.FileInputStream;
import org.milaifontanals.info.projecte1.model.*;
import org.milaifontanals.info.projecte1.GestorBDSportifyException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Toni Puig
 */
public class GestorBDSpotifyJdbc {
 /**
     * Aquest objecte és el que ha de mantenir la connexió amb el SGBD Es crea
 en el constructor d'aquesta classe i manté la connexió fins que el
 programador decideix tancar la connexió amb el mètode tancar.
     */
    private Connection conn;

    /**
     * Els següents PreparedStatement els declarem globals i es construeixen en
     * el constructor, després d'establir la connexió, de manera que els tinguem
     * creats cada vegada que es necessitin i no els 'preparem' cada vegada
     * que es necessiti
     *
     */
    private PreparedStatement qAddProducte;
    private PreparedStatement qUpdProducte;
    private PreparedStatement qDelListProducte;

    /**
     * Estableix la connexió amb la BD. Les dades que necessita (url, usuari i
     * contrasenya) es llegiran d'un fitxer de propietats
     * "empresaJDBC.properties" que ha de contenir les següents claus: url,
     * user, password.
     */
    public GestorBDSpotifyJdbc() throws GestorBDSportifyException {
         this("empresaJDBC.properties");            
    }

    /**
     * Estableix la connexió amb la BD. Les dades que necessita (url, usuari i
     * contrasenya) es llegiran d'un fitxer de propietats de nom passat per
     * paràmetre i que ha de contenir les següents claus: url, user, password.
     * En cas de no passar nom de fitxer de propietats, cercarà un fitxer de nom
     * "empresaJDBC.properties"
     *
     */
    public GestorBDSpotifyJdbc(String nomFitxerPropietats) throws GestorBDSportifyException {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(nomFitxerPropietats));
            String[] claus = {"url", "user", "password"};
            String[] valors = new String[3];
            for (int i = 0; i < claus.length; i++) {
                valors[i] = props.getProperty(claus[i]);
                if (valors[i] == null || valors[i].isEmpty()) {
                    throw new GestorBDSportifyException("L'arxiu " + nomFitxerPropietats + " no troba la clau " + claus[i]);
                }
            }
            conn = DriverManager.getConnection(valors[0], valors[1], valors[2]);
            conn.setAutoCommit(false);
        } catch (IOException ex) {
            throw new GestorBDSportifyException("Problemes en recuperar l'arxiu de configuració " + nomFitxerPropietats + "\n" + ex.getMessage());
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("No es pot establir la connexió.\n" + ex.getMessage());
        }
        // Una vegada ja hem establert connexió, intentem crear tots els PreparedStatement
        // que usarem a l'aplicació
        String inst = null;
        try {
            inst = "INSERT INTO Producte (prod_num, descripcio) VALUES (?,?)";
            qAddProducte = conn.prepareStatement(inst);
            inst = "UPDATE Producte SET descripcio=? WHERE prod_num=?";
            qUpdProducte = conn.prepareStatement(inst);
            inst = "DELETE FROM PRODUCTE WHERE prod_num IN ?";
            /* Alerta: El paràmetre ha de ser una llista de valors numèrics*/
            qDelListProducte = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
    }

    /**
     * Tanca la connexió
     */
    public void tancar() throws GestorBDSportifyException {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new GestorBDSportifyException("Error en fer rollback final.\n" + ex.getMessage());
            }
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new GestorBDSportifyException("Error en tancar la connexió.\n" + ex.getMessage());
            }
        }
    }

    /**
     * Obtenir tots els productes de la GestorBDEmpresaJdbc

    public List<Producte> getListProducte() throws GestorBDEmpresaJdbcException {
        List<Producte> llProd = new ArrayList<Producte>();
        Statement q = null;
        try {
            q = conn.createStatement();
            ResultSet rs = q.executeQuery("SELECT prod_num, descripcio FROM producte");
            while (rs.next()){
                llProd.add(new Producte(rs.getInt("prod_num"), rs.getString("descripcio")));
            }
            rs.close();
        } catch (SQLException ex) {
            throw new GestorBDEmpresaJdbcException("Error en intentar recuperar la llista de productes.\n" + ex.getMessage());
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    throw new GestorBDEmpresaJdbcException("Error en intentar tancar la sentència que ha recuperat la llista de productes.\n" + ex.getMessage());
                }
            }
        }
        return llProd;
    }
  
 
     Eliminar un conjunt de productes de la GestorBDEmpresaJdbc, a partir dels seus codis
   
    public void delListProducte(List<Integer> ll) throws GestorBDEmpresaJdbcException {
        Statement q = null;     // Només pel cas d'Oracle
        try {
            // Per passar una llista de valors a un paràmetre d'un PreparedStatement,
            // no existeix un setList... JDBC4 incorpora un mètode setArray, però
            // es pot usar si el SGBD facilita mètode Connection.createArrayOf...
            // Oracle NO ho permet... 
            if (conn.getClass().getName().startsWith("oracle.jdbc")) {
                // No podem usar una PreparedStatement senzilla en Oracle.
                // Ho fem sense PreparedStatement
                q = conn.createStatement();
                String aux = ll.toString();
                aux = aux.replace("[", "(");
                aux = aux.replace("]", ")");
                q.executeUpdate("DELETE FROM PRODUCTE WHERE prod_num IN " + aux);
            } else { // Per SGBDR que no siguin Oracle i que implementen Connection.createArrayOf
                qDelListProducte.setArray(1, conn.createArrayOf("int", ll.toArray()));
                qDelListProducte.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new GestorBDEmpresaJdbcException("Error en intentar eliminar un conjunt de productes.\n" + ex.getMessage());
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    throw new GestorBDEmpresaJdbcException("Error en intentar tancar la sentència que ha executat l'eliminació de productes\n" + ex.getMessage());
                }
            }
        }
    }

    public void addProducte(Producte p) throws GestorBDEmpresaJdbcException {
        try {
            qAddProducte.setInt(1, p.getProdNum());
            qAddProducte.setString(2, p.getDescripcio());
            qAddProducte.executeUpdate();
        } catch (SQLException ex) {
            throw new GestorBDEmpresaJdbcException("Error en intentar inserir el producte " + p.getProdNum() + "\n" + ex.getMessage());
        }
    }    

    public void updProducte(Producte p) throws GestorBDEmpresaJdbcException {
        try {
            String inst = "UPDATE Producte SET descripcio=? WHERE prod_num=?";
            qUpdProducte.setString(1, p.getDescripcio());
            qUpdProducte.setInt(2, p.getProdNum());
            qUpdProducte.executeUpdate();
        } catch (SQLException ex) {
            throw new GestorBDEmpresaJdbcException("Error en intentar modificar el producte " + p.getProdNum() + "\n" + ex.getMessage());
        }
    }*/

    /**
     * Valida (fa commit) els canvis de la transacció activa
     */
    public void validarCanvis() throws GestorBDSportifyException {
        try {
            conn.commit();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en validar els canvis: " + ex.getMessage());
        }
    }

    public void desferCanvis() throws GestorBDSportifyException {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new GestorBDSportifyException("Error en desfer els canvis: " + ex.getMessage());
        }
    }
    
}
