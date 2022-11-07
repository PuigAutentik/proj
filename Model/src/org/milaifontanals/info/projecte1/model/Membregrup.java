/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Toni Puig
 */
public class Membregrup {
    public Date dataInici;
    public Date dataFinal;
    public ArtistaIndividual membre;

    public Membregrup(Date dataInici, Date dataFinal, ArtistaIndividual membre) {
        this.dataInici = dataInici;
        this.dataFinal = dataFinal;
        this.membre = membre;
    }

    public Artista getMembre() {
        return membre;
    }

    public void setMembre(ArtistaIndividual membres) {
        this.membre = membres;
    }
    
    public Date getDataInici() {
        return dataInici;
    }

    public void setDataInici(Date dataInici) {
        this.dataInici = dataInici;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }  
}
