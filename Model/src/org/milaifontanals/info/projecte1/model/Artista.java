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
public abstract class Artista {
    private long id;
    private String nom;
    private String tipusArtista;

    public Artista(String nom, String tipusArtista) {
        this.nom = nom;
        this.tipusArtista = tipusArtista;
    }
    
    public Artista(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTipusArtista() {
        return tipusArtista;
    }

    public void setTipusArtista(String tipusArtista) {
        this.tipusArtista = tipusArtista;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
    
}
