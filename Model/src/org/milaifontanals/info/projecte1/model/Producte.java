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
public abstract class Producte {
    private long id;
    private String titol;
    private boolean actiu;
    private Estil estil;
    private String tipus;

    public Producte(long id,String titol, boolean actiu, Estil estil, String tipus) {
        this.id=id;
        this.titol = titol;
        this.actiu = actiu;
        this.estil = estil;
        this.tipus = tipus;
    }

    public Producte(String titol, String tipus) {
        this.titol = titol;
        this.tipus = tipus;
    }
    
    public Producte(long id,String titol, String tipus, Estil estil) {
        this.id=id;
        this.titol = titol;
        this.tipus = tipus;
        this.estil=estil;
    }
    
    public Producte(long id,String titol, boolean actiu,String tipus, Estil estil) {
        this.id=id;
        this.titol = titol;
        this.tipus = tipus;
        this.estil=estil;
        this.actiu=actiu;
    }

    public Producte(long id, String titol) {
        this.id = id;
        this.titol = titol;
    }

    public Producte(int id) {
        this.id = id;
    }

    abstract public int getDurada();

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id=id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public Estil getEstil() {
        return estil;
    }

    public void setEstil(Estil estil) {
        this.estil = estil;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
    
    

}
