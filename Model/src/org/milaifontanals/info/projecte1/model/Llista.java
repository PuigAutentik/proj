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
public class Llista extends Producte{
    private long lli_id;
    private int lli_durada;
    
    public Llista(long id,String titol,boolean estat,String tipus,Estil estil) {
        super(id,titol,estat, tipus,estil); 
    }
        public Llista(long id,String titol, String tipus,Estil estil) {
        super(id,titol, tipus,estil); 
    }
        
                public Llista(long id,String titol, String tipus,Estil estil,int lli_durada) {
        super(id,titol, tipus,estil);
        this.lli_durada=lli_durada;
    }

    public long getLli_id() {
        return lli_id;
    }

    public void setLli_id(long lli_id) {
        this.lli_id = lli_id;
    }

    public int getLli_durada() {
        return lli_durada;
    }

    public void setLli_durada(int lli_durada) {
        this.lli_durada = lli_durada;
    }
     
        
        
    

    @Override
    public int getDurada() {
        return lli_durada;
    }
}
