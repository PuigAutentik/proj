

import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.milaifontanals.info.projecte1.BDClient;
import org.milaifontanals.info.projecte1.model.Client;
import org.milaifontanals.info.projecte1.*;
import org.milaifontanals.info.projecte1.model.Canso;
import org.milaifontanals.info.projecte1.model.Estil;
import org.milaifontanals.info.projecte1.model.Producte;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Toni Puig
 */
public class main {

    public static void main(String[] args) throws GestorBDSportifyException {

        BDClient cli = new BDClient();
        BDCansons c = new BDCansons();
        BDProductes prods = new BDProductes();
        //List lli= new List<String>("C","A");

        try {
            List<Canso> cansons = c.getLlistaCansons("");
            List<Client> clients = cli.getLlistaClients();
            
           List<String> tip =new ArrayList<>();
                 tip.add(0,"C");
                 tip.add(1,"Z");
                 tip.add(2,"LL");
                 

           //System.out.println("ABANS: "+pro.getTitol());
            System.out.println("OK");
            prods.eliminarProducte(122);
            System.out.println("OK2");
            //pro.setTitol("Pont AeriMODIFICATTT");

            
            
           Producte pro2=prods.getProducte(2);
            System.out.println("DESPRESS: "+pro2.getTitol());
            
             Estil est = new Estil(2, "");
 
             Canso nouc = new Canso(0,"proba","C",est);
             
             List<Estil> estt= new ArrayList<>();
              estt=prods.getEstils();
              System.out.println(estt.get(1).getNom());
           
           
           int i=prods.getUltimProducte();
            System.out.println("ULTIM PRODUCTE+ " +i);
                 
                 //System.out.println(tip.get(4));
           // List<Client> prodsss = prods.getProductes("oj", "1", );

            Cansons jf = new Cansons();
            jf.setVisible(true);
            jf.setDefaultCloseOperation(EXIT_ON_CLOSE);

            long id=jf.getId_can();
            //System.out.println(id);
            
           // System.out.println("");
            //Value result = dlg.showDialog();


        } catch (Exception e) {
            System.out.printf(e.getMessage());
            e.printStackTrace();
        }
    }
}
