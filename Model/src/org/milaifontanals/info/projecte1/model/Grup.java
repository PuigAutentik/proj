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
public class Grup extends Artista {
    private int id;
    private Date grp_dataCreacio;
    private List<Membregrup> membres;

    public Grup(Date grp_dataCreacio, String nom, String tipusArtista) {
        super(nom, tipusArtista);
        this.grp_dataCreacio = grp_dataCreacio;
    }

    public Date getGrp_dataCreacio() {
        return grp_dataCreacio;
    }

    public void setGrp_dataCreacio(Date grp_dataCreacio) {
        this.grp_dataCreacio = grp_dataCreacio;
    }

    public List<Membregrup> getMembres() {
        return membres;
    }
    
    public void addMembre(Membregrup membre) {
        this.membres.add(membre);
    }
    
    public void eliminarMembre(Membregrup membre) {
        this.membres.remove(membre);
    }

    public void setMembres(List<Membregrup> membres) {
        this.membres = membres;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
}
