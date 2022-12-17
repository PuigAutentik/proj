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
public class Canso extends Producte{
    private Date anyCreacio;
    private int durada;
    private int interpret;

    public Canso(Date anyCreacio, int durada, long id, String titol) {
        super(id, titol);
        this.anyCreacio = anyCreacio;
        this.durada = durada;
    }
    
    public Canso(long id,String titol, String tipus,Estil estil) {
        super(id,titol, tipus,estil); 
    }
        public Canso(long id,String titol,boolean estat,String tipus,Estil estil) {
        super(id,titol,estat, tipus,estil); 
    }
    
    

    public Canso(long id,String titol, boolean actiu, Estil estil, String tipus,Date anyCreacio, int durada,int interpret){
        super(id,titol, actiu, estil, tipus);
        this.anyCreacio = anyCreacio;
        this.durada = durada;
        this.interpret=interpret;
    }

    public Canso(long id, String titol) {
        super(id, titol);
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
    
 

    public void setDurada(int durada) {
        this.durada = durada;
    }

    public int getInterpret() {
        return interpret;
    }

    public void setInterpret(int interpret) {
        this.interpret = interpret;
    }
    
    
    
}
