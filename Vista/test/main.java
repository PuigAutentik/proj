
import java.util.List;
import org.milaifontanals.info.projecte1.BDClient;
import org.milaifontanals.info.projecte1.model.Client;

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
    
    public static void main(String[] args) {

        BDClient cli=new BDClient();

        
        try {

            List<Client> clients=cli.getLlistaClients();
            System.out.println("FUNCIONA cognom:" + clients.get(0).getCognom());  
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            e.printStackTrace();
        }
    }
}
