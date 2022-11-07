/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1.model;
/**
 *
 * @author Toni Puig
 */
public class Canso extends Producte{
    private int anyCreacio;
    private int durada;

    public Canso(String titol, boolean actiu, Estil estil, String tipus,int anyCreacio, int durada){
        super(titol, actiu, estil, tipus);
        this.anyCreacio = anyCreacio;
        this.durada = durada;
    }

    public Canso(long id, String titol) {
        super(id, titol);
    }
    
    

    public int getAnyCreacio() {
        return anyCreacio;
    }

    public void setAnyCreacio(int anyCreacio) {
        this.anyCreacio = anyCreacio;
    }

    @Override
    public int getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }
    
}
