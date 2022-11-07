/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1.model;

import java.util.Date;

/**
 *
 * @author Toni Puig
 */
public class ArtistaIndividual extends Artista {
        private Date dataNeixament;
        private Pais pais;

    public ArtistaIndividual(Date dataNeixament, Pais pais, String nom, String tipusArtista) {
        super(nom, tipusArtista);
        this.dataNeixament = dataNeixament;
        this.pais = pais;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
  
        
    public Date getDataNeixament() {
        return dataNeixament;
    }

    public void setDataNeixament(Date dataNeixament) {
        this.dataNeixament = dataNeixament;
    }
        
    
}
