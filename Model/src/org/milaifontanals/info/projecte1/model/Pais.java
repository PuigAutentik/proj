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
public class Pais {
    private String iso;
    private String nom;

    public Pais(String iso, String nom) {
        this.iso = iso;
        this.nom = nom;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    } 
}
