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
public class Album extends Producte{
    private Date anyCreacio;
    private int durada;

    public Album(long id,Date anyCreacio, String titol, boolean actiu, Estil estil, String tipus,int durada) {
        super(id,titol, actiu, estil, tipus);
        this.anyCreacio = anyCreacio;
                this.durada = durada;
    }

    public Album(Date anyCreacio, int durada, String titol, String tipus) {
        super(titol, tipus);
        this.anyCreacio = anyCreacio;
        this.durada = durada;
    }
    
    public Album(long id,String titol, String tipus,Estil estil) {
        super(id,titol, tipus,estil); 
    }
        public Album(long id,String titol,boolean estat,String tipus,Estil estil) {
        super(id,titol,estat, tipus,estil); 
    }
    
    

    public Album(long id, String titol) {
        super(id, titol);
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }

   

    public Date getAnyCreacio() {
        return anyCreacio;
    }

    public void setAnyCreacio(Date anyCreacio) {
        this.anyCreacio = anyCreacio;
    }
    
    @Override
    public int getDurada() {
        return durada;
    }
    
}
