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
public class Client {
    private Long id;
    private String email;
    private String nom;
    private String cognom;
    private Date dataNeix;
    private String cp;
    private String dom1;
    private String dom2;
    private String poblacio;
    private Pais pais;

    public Client(String email, String nom, String cognom, Date dataNeix, String cp, String dom1, String dom2, String poblacio, Pais pais) {
        this.email = email;
        this.nom = nom;
        this.cognom = cognom;
        this.dataNeix = dataNeix;
        this.cp = cp;
        this.dom1 = dom1;
        this.dom2 = dom2;
        this.poblacio = poblacio;
        this.pais = pais;
    }
    
    public Client(long id,String email, String nom, String cognom, Date dataNeix) {
        this.id=id;
        this.email = email;
        this.nom = nom;
        this.cognom = cognom;
        this.dataNeix = dataNeix;
        this.cp = null;
        this.dom1 = null;
        this.dom2 = null;
        this.poblacio = null;
        this.pais = null;
    }
    
        public Client(long id, String nom, String cognom) {
        this.id=id;
        this.nom = nom;
        this.cognom = cognom;
    }
        
   public Client(long id, String nom) {
        this.id=id;
        this.nom = nom;
    }
    
        public Client(String email, String nom, String cognom, Date dataNeix) {
        this.email = email;
        this.nom = nom;
        this.cognom = cognom;
        this.dataNeix = dataNeix;
        this.cp = null;
        this.dom1 = null;
        this.dom2 = null;
        this.poblacio = null;
        this.pais = null;
    }
    
    public Client getClient(String id) {
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public Date getDataNeix() {
        return dataNeix;
    }

    public void setDataNeix(Date dataNeix) {
        this.dataNeix = dataNeix;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDom1() {
        return dom1;
    }

    public void setDom1(String dom1) {
        this.dom1 = dom1;
    }

    public String getDom2() {
        return dom2;
    }

    public void setDom2(String dom2) {
        this.dom2 = dom2;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    
    
    
}
