/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1.model;

import java.util.List;

/**
 *
 * @author Toni Puig
 */
public class LlistaReproduccio extends Producte{
    private int durada;
    private List<Producte> items;

    public LlistaReproduccio(long id,int durada, List<Producte> items, String titol, boolean actiu, Estil estil, String tipus) {
        super(id,titol, actiu, estil, tipus);
        this.durada = durada;
        this.items = items;
    }

    public LlistaReproduccio(long id, String titol) {
        super(id, titol);
    }
    
            public LlistaReproduccio(long id,String titol,boolean estat,String tipus,Estil estil) {
        super(id,titol,estat, tipus,estil); 
    }

    
    
    
    @Override
    public int getDurada() {
     return durada;
    }

    public List<Producte> getItems() {
        return items;
    }
    
    public int getNumItems() {
        return items.size();
    }
    
    public Producte getItem(int i){
        return items.get(i);
    }
    
    public void addItem(Producte p){
        items.add(p);
    }
    
    public void removeItem(Producte p){
        items.remove(p);
    }  
}
