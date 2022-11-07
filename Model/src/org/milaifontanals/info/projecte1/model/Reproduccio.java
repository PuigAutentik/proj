/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Toni Puig
 */
public class Reproduccio {
    private Timestamp timestamp;
    private Client client;
    private Producte producte;

    public Reproduccio(Timestamp timestamp, Client client, Producte producte) {
        this.timestamp = timestamp;
        this.client = client;
        this.producte = producte;
    }

    

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Producte getProducte() {
        return producte;
    }

    public void setProducte(Producte producte) {
        this.producte = producte;
    }
    
    
    
}
