/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Toni Puig
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.milaifontanals.info.projecte1.*;
import org.milaifontanals.info.projecte1.BDReproduccio;
import org.milaifontanals.info.projecte1.model.Client;
import org.milaifontanals.info.projecte1.model.Reproduccio;

public class test {

    // static Connection con = DBManager.getConnection();
    public static void main(String[] args) throws GestorBDSportifyException {

        BDProductes pro = new BDProductes();
        pro.eliminarProducte(122);
        pro.validarCanvis();
//        BDReproduccio bb= new BDReproduccio();
//        BDClient cli=new BDClient();
//        Date inici= new Date(116, 5,3);
//        Date ultima=new Date();
//        
//        try {
//            List<Reproduccio> rep=bb.getListProducte(0,"e",new java.sql.Date(inici.getTime()),new java.sql.Date(ultima.getTime()));
//            if(rep.isEmpty()==true){
//                System.out.println("Emptu");
//            }else{  
//            }
//            List<Client> clients=cli.getLlistaClients();
//            System.out.println("FUNCIONA cognom:" + clients.get(0).getCognom());
//            System.out.println(rep.get(0).getTimestamp());
//            //bb.modificarReproduccio(rep.get(0),rep.get(1));
//            System.out.println(rep.get(0).getTimestamp());   
//        } catch (Exception e) {
//            System.out.printf(e.getMessage());
//            e.printStackTrace();
//        }

    }
}

