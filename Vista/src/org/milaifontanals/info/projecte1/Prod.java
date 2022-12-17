/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.info.projecte1;

import java.awt.Color;
import java.awt.Dialog;
import java.sql.Array;
import org.milaifontanals.info.projecte1.*;
import java.util.Date;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.milaifontanals.info.projecte1.BDClient.con;
import org.milaifontanals.info.projecte1.model.Client;
import org.milaifontanals.info.projecte1.model.Reproduccio;
import org.milaifontanals.info.projecte1.model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.lang.Object;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.milaifontanals.info.projecte1.model.*;

/**
 *
 * @author Toni Puig
 */
public class Prod extends javax.swing.JFrame {

    public BDCansons bdcan;
    public BDProductes bdprod;

    DefaultTableModel dtmCan = new DefaultTableModel();
    DefaultTableModel dtmProd = new DefaultTableModel();
    DefaultTableModel dtmProdLlista = new DefaultTableModel();
    List<Producte> llista_productes = new ArrayList<>();
    List<Producte> llista_productes_selecionats = new ArrayList<>();
    List<Canso> cansons_afegir = new ArrayList<>();
    List<Artista> llista_artistes = new ArrayList<>();
    List<Estil> llista_estils = new ArrayList<>();
    List<Estil> llista_proba_estils = new ArrayList<>();
    Producte producte_editan = null;
    Object[] datos_llista = null;

    /**
     * Creates new form Prod
     */
    public Prod() throws GestorBDSportifyException {
        this.bdprod = new BDProductes();

        initComponents();

        ButtonGroup btn_gr = new ButtonGroup();
        jRB_eActiu.setSelected(true);
        btn_gr.add(jRB_eActiu);
        btn_gr.add(jRB_eInactiu);
        btn_gr.add(jRB_eTots);

        jCB_canso.setSelected(true);
        jCB_Album.setSelected(true);
        jCB_llista.setSelected(true);

        ButtonGroup btn_grnew = new ButtonGroup();
        jCB_canso1.setSelected(true);
        btn_grnew.add(jCB_canso1);
        btn_grnew.add(jCB_Album1);
        btn_grnew.add(jCB_llista1);

        crear_DataCanso.setMaxSelectableDate(new Date());
        crear_DataCanso.setMinSelectableDate(new Date(1, 1, 1910));

        carregar_artistas();
        carregar_estils();

        activar_generals(false);
        ACTUALITAR.setEnabled(false);
        acceptar_crear.setEnabled(false);

    }

    ///************************************************///    
    ///***********   TAULA SECUNDARIA CANSONS  ********///
    ///************************************************///
    private void setTaula_afegirCansons() {
        String[] cabecera = {"NOM", "DATA", "DURADA"};
        dtmCan.setColumnIdentifiers(cabecera);
        jTable_cansons.setModel(dtmCan);
        setDatos_afegirCansons();
    }

    private void setDatos_afegirCansons() {
        dtmCan.setNumRows(0);
        jTable_cansons.setModel(dtmCan);
        Object[] datos = new Object[dtmCan.getColumnCount()];
        try {
            bdcan = new BDCansons();
            cansons_afegir = bdcan.getLlistaCansons(jTextField_FragCanso.getText());
            for (int i = 0; i < cansons_afegir.size(); i++) {
                datos[0] = cansons_afegir.get(i).getTitol();
                datos[1] = cansons_afegir.get(i).getAnyCreacio();
                datos[2] = cansons_afegir.get(i).getDurada();
                dtmCan.addRow(datos);
            }
        } catch (GestorBDSportifyException e) {
            missatge_avis(e.getMessage());
            e.printStackTrace();
        }
        jTable_cansons.setModel(dtmCan);
    }

    ///************************************************///    
    ///***********   TAULA    PETITA   CANSONS  ********///
    ///************************************************///
    private void setTaula_llista_selecionats() {
        String[] cabecera = {"NOM"};
        dtmProdLlista.setColumnIdentifiers(cabecera);
        jPanel_llista_selecionats.setModel(dtmProdLlista);
        setDatos_llista_selecionats();
    }

    private void setDatos_llista_selecionats() {
        dtmProdLlista.setNumRows(0);
        jPanel_llista_selecionats.setModel(dtmProdLlista);
        Object[] datos = new Object[dtmProdLlista.getColumnCount()];
        for (int i = 0; i < llista_productes_selecionats.size(); i++) {
            datos[0] = llista_productes_selecionats.get(i).getTitol();
            dtmProdLlista.addRow(datos);
        }
        jPanel_llista_selecionats.setModel(dtmProdLlista);
    }

    ///************************************************///    
    ///***********TAULA PRINCIPAL DE PRODUCTES ********///
    ///************************************************///
    private void setTaula_afegirProductesPrincipals() {
        String[] cabecera = {"TITUL", "TIPO", "ESTIL", "ACTIU"};
        dtmProd.setColumnIdentifiers(cabecera);
        jTable_productes.setModel(dtmProd);
        setDatos_afegirproductesPrincipals();
    }

    private void setDatos_productesPrincipals() {
        dtmProd.setNumRows(0);
        for (int i = 0; i < llista_productes.size(); i++) {
            datos_llista[0] = llista_productes.get(i).getTitol();
            datos_llista[1] = tipo(llista_productes.get(i).getTipus());
            datos_llista[2] = llista_productes.get(i).getEstil().getNom();
            datos_llista[3] = estat(llista_productes.get(i).isActiu());
            dtmProd.addRow(datos_llista);
        }
        jTable_productes.setModel(dtmProd);
    }

    private void setDatos_afegirproductesPrincipals() {
        dtmProd.setNumRows(0);
        jTable_productes.setModel(dtmProd);
        datos_llista = new Object[dtmProd.getColumnCount()];
        try {
            //if(jRB_eTots.isSelected())
            int estat = 0;
            if (jRB_eActiu.isSelected()) {
                estat = 1;
            }
            if (jRB_eInactiu.isSelected()) {
                estat = 0;
            }
            if (jRB_eTots.isSelected()) {
                estat = 3;
            }
            List<String> llista = new ArrayList<>();
            if (jCB_canso.isSelected()) {
                llista.add("C");
            } else {
                llista.add("Z");
            };
            if (jCB_Album.isSelected()) {
                llista.add("A");
            } else {
                llista.add("Z");
            };
            if (jCB_llista.isSelected()) {
                llista.add("L");
            } else {
                llista.add("Z");
            };
            llista_productes = bdprod.getProductes(jTextField_fragmentArtista.getText(), estat, llista);
            setDatos_productesPrincipals();
        } catch (GestorBDSportifyException e) {
            missatge_avis(e.getMessage());
            e.printStackTrace();
        }
        //jTable_productes.setModel(dtmProd);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog_selCancons = new javax.swing.JDialog();
        jPanel_general = new javax.swing.JPanel();
        jTextField_FragCanso = new javax.swing.JTextField();
        jLabel_canso = new javax.swing.JLabel();
        jScrollPane_cansons = new javax.swing.JScrollPane();
        jTable_cansons = new javax.swing.JTable();
        jBtn_selecionar = new javax.swing.JButton();
        jBtn_cancelar = new javax.swing.JButton();
        jBtn_buscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_productes1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_productes2 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_productes = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton_Buscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField_fragmentArtista = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jRB_eActiu = new javax.swing.JRadioButton();
        jRB_eInactiu = new javax.swing.JRadioButton();
        jRB_eTots = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jCB_canso = new javax.swing.JCheckBox();
        jCB_Album = new javax.swing.JCheckBox();
        jCB_llista = new javax.swing.JCheckBox();
        btn_invertir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jB_imprimir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jB_ieliminar = new javax.swing.JButton();
        jB_editar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCB_canso1 = new javax.swing.JCheckBox();
        jCB_Album1 = new javax.swing.JCheckBox();
        jCB_llista1 = new javax.swing.JCheckBox();
        crear_nomCanso = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        crear_DataCanso = new com.toedter.calendar.JDateChooser();
        crear_duradaCanso = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        acceptar_crear = new javax.swing.JButton();
        cancelar_crearCanso = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jComboBox_LlistaInterpret = new javax.swing.JComboBox<>();
        btn_crearnou = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jComboBox_estil = new javax.swing.JComboBox<>();
        jButton_llista_petita_afegir = new javax.swing.JButton();
        jButton_llista_petita_eliminar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel_llista_selecionats = new javax.swing.JTable();
        ACTUALITAR = new javax.swing.JButton();

        jDialog_selCancons.setTitle("CANSONS");
        jDialog_selCancons.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDialog_selCancons.setMinimumSize(new java.awt.Dimension(394, 391));

        jPanel_general.setBackground(new java.awt.Color(29, 185, 84));

        jLabel_canso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_canso.setForeground(java.awt.Color.white);
        jLabel_canso.setText("Cancion:");

        jTable_cansons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable_cansons.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_cansons.getTableHeader().setReorderingAllowed(false);
        jScrollPane_cansons.setViewportView(jTable_cansons);

        jBtn_selecionar.setText("SELECIONAR");
        jBtn_selecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_selecionarActionPerformed(evt);
            }
        });

        jBtn_cancelar.setBackground(java.awt.Color.red);
        jBtn_cancelar.setText("CANCELAR");
        jBtn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_cancelarActionPerformed(evt);
            }
        });

        jBtn_buscar.setText("BUSCAR");
        jBtn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_buscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_generalLayout = new javax.swing.GroupLayout(jPanel_general);
        jPanel_general.setLayout(jPanel_generalLayout);
        jPanel_generalLayout.setHorizontalGroup(
            jPanel_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_generalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_generalLayout.createSequentialGroup()
                        .addComponent(jLabel_canso, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_FragCanso, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_generalLayout.createSequentialGroup()
                        .addComponent(jBtn_selecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane_cansons, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel_generalLayout.setVerticalGroup(
            jPanel_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_generalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_FragCanso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_canso)
                    .addComponent(jBtn_buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane_cansons, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_generalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtn_selecionar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jBtn_cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jDialog_selCanconsLayout = new javax.swing.GroupLayout(jDialog_selCancons.getContentPane());
        jDialog_selCancons.getContentPane().setLayout(jDialog_selCanconsLayout);
        jDialog_selCanconsLayout.setHorizontalGroup(
            jDialog_selCanconsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_general, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialog_selCanconsLayout.setVerticalGroup(
            jDialog_selCanconsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_general, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jDialog_selCancons.getAccessibleContext().setAccessibleParent(this);

        jTable_productes1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable_productes1);

        jTable_productes2.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable_productes2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(25, 20, 20));
        setMinimumSize(new java.awt.Dimension(1141, 670));
        getContentPane().setLayout(null);

        jTable_productes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable_productes);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(350, 10, 770, 510);

        jPanel1.setBackground(new java.awt.Color(25, 20, 20));
        jPanel1.setForeground(new java.awt.Color(25, 20, 20));
        jPanel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jButton_Buscar.setText("BUSCAR");
        jButton_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BuscarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Artista:");

        jTextField_fragmentArtista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_fragmentArtistaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Estat:");

        jRB_eActiu.setForeground(java.awt.Color.white);
        jRB_eActiu.setText("Actiu");
        jRB_eActiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRB_eActiuActionPerformed(evt);
            }
        });

        jRB_eInactiu.setForeground(java.awt.Color.white);
        jRB_eInactiu.setText("Inactiu");

        jRB_eTots.setForeground(java.awt.Color.white);
        jRB_eTots.setText("Tots");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Tipus:");

        jCB_canso.setForeground(java.awt.Color.white);
        jCB_canso.setText("Cançó");
        jCB_canso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_cansoActionPerformed(evt);
            }
        });

        jCB_Album.setForeground(java.awt.Color.white);
        jCB_Album.setText("Àlbum");

        jCB_llista.setForeground(java.awt.Color.white);
        jCB_llista.setText("Llista");

        btn_invertir.setText("INV.");
        btn_invertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invertirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_invertir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRB_eActiu, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jRB_eInactiu, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jRB_eTots, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextField_fragmentArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCB_canso, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCB_Album, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jCB_llista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(797, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_fragmentArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRB_eActiu)
                    .addComponent(jRB_eInactiu)
                    .addComponent(jRB_eTots))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jCB_canso)
                    .addComponent(jCB_Album)
                    .addComponent(jCB_llista))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(btn_invertir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(605, 605, 605))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1130, 160);

        jPanel3.setBackground(new java.awt.Color(25, 21, 20));

        jB_imprimir.setText("IMPRIMIR");
        jB_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_imprimirActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setText("segons (s)");

        jB_ieliminar.setBackground(java.awt.Color.red);
        jB_ieliminar.setText("ELIMINAR");
        jB_ieliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ieliminarActionPerformed(evt);
            }
        });

        jB_editar.setText("EDITAR");
        jB_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_editarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jB_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addComponent(jB_ieliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jB_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(351, 351, 351)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(352, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(399, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jB_imprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jB_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jB_ieliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(196, 196, 196)
                    .addComponent(jLabel11)
                    .addContainerGap(267, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(340, 150, 790, 480);

        jPanel2.setBackground(new java.awt.Color(29, 185, 84));
        jPanel2.setForeground(new java.awt.Color(25, 20, 20));
        jPanel2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel2AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel2.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("CREAR NOU:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(123, 0, 91, 17);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Tipus:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(26, 27, 39, 17);

        jCB_canso1.setForeground(java.awt.Color.white);
        jCB_canso1.setText("Cançó");
        jPanel2.add(jCB_canso1);
        jCB_canso1.setBounds(71, 27, 79, 21);

        jCB_Album1.setForeground(java.awt.Color.white);
        jCB_Album1.setText("Àlbum");
        jPanel2.add(jCB_Album1);
        jCB_Album1.setBounds(168, 27, 79, 21);

        jCB_llista1.setForeground(java.awt.Color.white);
        jCB_llista1.setText("Llista");
        jPanel2.add(jCB_llista1);
        jCB_llista1.setBounds(265, 27, 84, 21);

        crear_nomCanso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_nomCansoActionPerformed(evt);
            }
        });
        jPanel2.add(crear_nomCanso);
        crear_nomCanso.setBounds(70, 80, 245, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("Nom:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(30, 90, 34, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Any:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(30, 120, 29, 17);
        jPanel2.add(crear_DataCanso);
        crear_DataCanso.setBounds(70, 120, 244, 30);

        crear_duradaCanso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crear_duradaCansoActionPerformed(evt);
            }
        });
        jPanel2.add(crear_duradaCanso);
        crear_duradaCanso.setBounds(80, 150, 155, 30);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(java.awt.Color.white);
        jLabel10.setText("segons (s)");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(260, 150, 87, 17);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.white);
        jLabel8.setText("Durada:");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(10, 150, 50, 17);

        acceptar_crear.setText("CREAR");
        acceptar_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptar_crearActionPerformed(evt);
            }
        });
        jPanel2.add(acceptar_crear);
        acceptar_crear.setBounds(120, 419, 209, 30);

        cancelar_crearCanso.setText("CANCELAR");
        cancelar_crearCanso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelar_crearCansoActionPerformed(evt);
            }
        });
        jPanel2.add(cancelar_crearCanso);
        cancelar_crearCanso.setBounds(10, 417, 104, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.white);
        jLabel9.setText("Interpret:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(10, 190, 70, 17);

        jComboBox_LlistaInterpret.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jComboBox_LlistaInterpretAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jComboBox_LlistaInterpret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_LlistaInterpretActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox_LlistaInterpret);
        jComboBox_LlistaInterpret.setBounds(80, 190, 244, 21);

        btn_crearnou.setText("CREAR");
        btn_crearnou.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearnouActionPerformed(evt);
            }
        });
        jPanel2.add(btn_crearnou);
        btn_crearnou.setBounds(130, 50, 90, 21);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setText("Estil:");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(10, 220, 60, 17);

        jComboBox_estil.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jComboBox_estilAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jComboBox_estil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_estilActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox_estil);
        jComboBox_estil.setBounds(80, 220, 244, 21);

        jButton_llista_petita_afegir.setText("AFEGIR");
        jButton_llista_petita_afegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_llista_petita_afegirActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_llista_petita_afegir);
        jButton_llista_petita_afegir.setBounds(170, 360, 163, 21);

        jButton_llista_petita_eliminar.setBackground(new java.awt.Color(255, 0, 0));
        jButton_llista_petita_eliminar.setText("ELIMINAR");
        jButton_llista_petita_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_llista_petita_eliminarActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_llista_petita_eliminar);
        jButton_llista_petita_eliminar.setBounds(10, 360, 160, 21);

        jPanel_llista_selecionats.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jPanel_llista_selecionats);

        jPanel2.add(jScrollPane4);
        jScrollPane4.setBounds(10, 250, 323, 120);

        ACTUALITAR.setText("ACTUALITZAR");
        ACTUALITAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACTUALITARActionPerformed(evt);
            }
        });
        jPanel2.add(ACTUALITAR);
        ACTUALITAR.setBounds(10, 390, 320, 21);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 160, 350, 470);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel1AncestorAdded

    }//GEN-LAST:event_jPanel1AncestorAdded

    private void jTextField_fragmentArtistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_fragmentArtistaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_fragmentArtistaActionPerformed

    private void jButton_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BuscarActionPerformed

        setTaula_afegirProductesPrincipals();

    }//GEN-LAST:event_jButton_BuscarActionPerformed

    private void jRB_eActiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRB_eActiuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRB_eActiuActionPerformed

    private void jPanel2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel2AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2AncestorAdded

    private void crear_nomCansoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_nomCansoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_crear_nomCansoActionPerformed

    private void crear_duradaCansoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear_duradaCansoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_crear_duradaCansoActionPerformed

    private void jComboBox_LlistaInterpretAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jComboBox_LlistaInterpretAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_LlistaInterpretAncestorAdded

    private void jComboBox_LlistaInterpretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_LlistaInterpretActionPerformed

    }//GEN-LAST:event_jComboBox_LlistaInterpretActionPerformed

    private void cancelar_crearCansoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelar_crearCansoActionPerformed
        natejar_generals();
        try {
            bdprod.desferCanvis();
        } catch (GestorBDSportifyException ex) {
            missatge_avis("Error al cancelar canvis: "+ex.getMessage());
        }
    }//GEN-LAST:event_cancelar_crearCansoActionPerformed

    private void acceptar_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptar_crearActionPerformed
        String tipo = "";
        // int id=(int)llista_productes.get(jTable_productes.getSelectedRow()).getEstil().getId();
        String[] split = jComboBox_estil.getSelectedItem().toString().split(":", 2);
        int interpet = 0;
        if (jCB_canso1.isSelected()) {
            String[] split2 = jComboBox_LlistaInterpret.getSelectedItem().toString().split(":", 2);
            interpet = Integer.parseInt(split2[0]);
        }
        Estil est = new Estil(Integer.parseInt(split[0]), "");
        Producte nou = null;
        try {
            if (jCB_Album1.isSelected()) {
                tipo = "A";
                Album nouc = new Album(0, crear_nomCanso.getText(), tipo, est);
                bdprod.inserirProducte(nouc);
                bdprod.validarCanvis();
                int id = bdprod.getUltimProducte();
                nouc.setId(id);
                nouc.setAnyCreacio(crear_DataCanso.getDate());
                nouc.setDurada(Integer.parseInt(crear_duradaCanso.getText()));
                bdprod.inserirAlbum(nouc);
                bdprod.validarCanvis();
                for (int i = 0; i < llista_productes_selecionats.size(); i++) {
                    AlbumContingut llc = new AlbumContingut(id, (int) llista_productes_selecionats.get(i).getId(), i + 1);
                    bdprod.afegirProducteAlbum(llc);
                }
                bdprod.validarCanvis();
                missatge_avis("ALBUM afegit correctament!");
                natejar_generals();
            } else if (jCB_llista1.isSelected()) {
                tipo = "L";
                Llista nouc = new Llista(0, crear_nomCanso.getText(), tipo, est);
                bdprod.inserirProducte(nouc);
                bdprod.validarCanvis();
                int id = bdprod.getUltimProducte();
                nouc.setLli_id(id);
                nouc.setLli_durada(200);
                bdprod.inserirLlista(nouc);
                bdprod.validarCanvis();
                for (int i = 0; i < llista_productes_selecionats.size(); i++) {
                    LlistaContingut llc = new LlistaContingut((int) id, (int) llista_productes_selecionats.get(i).getId(), i + 1);
                    bdprod.afegirProducteLlista(llc);
                }
                bdprod.validarCanvis();
                missatge_avis("Producte afegit correctament!");
                natejar_generals();
            } else {
                tipo = "C";
                Canso nouc = new Canso(0, crear_nomCanso.getText(), tipo, est);
                bdprod.inserirProducte(nouc);
                bdprod.validarCanvis();
                int id = bdprod.getUltimProducte();
                nouc.setDurada(Integer.parseInt(crear_duradaCanso.getText()));
                nouc.setAnyCreacio(crear_DataCanso.getDate());
                nouc.setInterpret(interpet);
                nouc.setId(id);
                bdprod.inserirCanso(nouc);
                bdprod.validarCanvis();
                missatge_avis("Producte afegit correctament!");
                natejar_generals();
            }
        } catch (GestorBDSportifyException ex) {

        } catch (SQLException ex) {
            missatge_avis("ERROR AL INSERIR PRODUCTE: " + ex.getMessage());
        }


    }//GEN-LAST:event_acceptar_crearActionPerformed

    private void jB_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_imprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jB_imprimirActionPerformed

    private void jB_ieliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ieliminarActionPerformed
        //JDialog jFrame = new JDialog();
        //int result = JOptionPane.showConfirmDialogj(Frame, "Estas segur que vols eliminar la Reproduccio");
        int input = JOptionPane.showConfirmDialog(this, "Estas segur que vols eliminar la Reproduccio", "Seleciona una opció", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        //System.out.println(jTable_productes.getSelectedRow());
        switch (input) {
            case 0:
                try {
                    System.out.println(llista_productes.get(jTable_productes.getSelectedRow()).getId());
                    bdprod.eliminarProducte(llista_productes.get(jTable_productes.getSelectedRow()).getId());
                    missatge_avis("S'ha esborrat el Producte Correctament");
                    llista_productes.remove(jTable_productes.getSelectedRow());
                    setDatos_productesPrincipals();
                    bdprod.validarCanvis();
                } catch (Exception e) {
                    missatge_avis("fallo al eliminar reproduccio: " + e.getCause().getMessage());
                }
                break;
            case 1:
                System.out.println("You pressed NO");
                break;
            default:
                System.out.println("You pressed Cancel");
                break;
        }
    }//GEN-LAST:event_jB_ieliminarActionPerformed

    private void jB_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_editarActionPerformed
        activar_generals(true);
        acceptar_crear.setEnabled(false);
        ACTUALITAR.setEnabled(true);
        try {
            producte_editan = bdprod.getProducte((int) llista_productes.get(jTable_productes.getSelectedRow()).getId());
            crear_nomCanso.setText(producte_editan.getTitol());
            crear_duradaCanso.setText(Integer.toString(producte_editan.getDurada()));
            Album a = null;
            Canso c = null;
            Llista l = null;
            int idx = 0;
            switch (producte_editan.getTipus()) {
                case "A":
                    a = (Album)producte_editan;
                    crear_DataCanso.setDate(a.getAnyCreacio());
                    jComboBox_LlistaInterpret.setSelectedIndex(-1);
                    jComboBox_LlistaInterpret.setEnabled(false);
                    crear_duradaCanso.setEnabled(false);
                    crear_DataCanso.setEnabled(false);
                    jComboBox_estil.setSelectedIndex(((int) a.getEstil().getId()) - 1);
                    llista_productes_selecionats = bdprod.getCansonsAlbum(a.getId());
                    jCB_Album1.setSelected(true);
                    setTaula_llista_selecionats();
                    break;
                case "C":
                    c = (Canso) producte_editan;
                    crear_DataCanso.setDate(c.getAnyCreacio());
                    jComboBox_LlistaInterpret.setSelectedIndex(c.getInterpret() - 1);
                    idx = ((int) c.getEstil().getId()) - 1;
                    jComboBox_estil.setSelectedIndex(((int) c.getEstil().getId()) - 1);
                    llista_productes_selecionats.clear();
                    jCB_canso1.setSelected(true);
                    setTaula_llista_selecionats();
                    break;
                case "L":
                    l = (Llista) producte_editan;
                    crear_DataCanso.setCalendar(null);
                    jComboBox_LlistaInterpret.setSelectedIndex(-1);
                    jComboBox_LlistaInterpret.setEnabled(false);
                    idx = ((int) l.getEstil().getId()) - 1;
                    crear_duradaCanso.setEnabled(false);
                    crear_DataCanso.setEnabled(false);
                    jComboBox_estil.setSelectedIndex(((int) l.getEstil().getId()) - 1);
                    llista_productes_selecionats = bdprod.getCansonsLlista(l.getId());
                    jCB_llista1.setSelected(true);
                    setTaula_llista_selecionats();
                    break;
            }
        } catch (GestorBDSportifyException ex) {
            missatge_avis("fallo al editar producte!");
            Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
        }
        //activar_generals(true);
//        carregar_artistas();
//        carregar_estils();
    }//GEN-LAST:event_jB_editarActionPerformed

    private void jBtn_selecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_selecionarActionPerformed
        try {
            Canso produc = (Canso) bdprod.getProducte((int) cansons_afegir.get(jTable_cansons.getSelectedRow()).getId());
            llista_productes_selecionats.add(produc);
            setTaula_llista_selecionats();
            missatge_avis("Canso afegida a la llista/album (recorda guardar canvis)");
        } catch (GestorBDSportifyException ex) {
            missatge_avis("Error al selecionar canso");
            Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtn_selecionarActionPerformed

    private void jBtn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_cancelarActionPerformed
        jDialog_selCancons.setVisible(false);
        //dispose();
    }//GEN-LAST:event_jBtn_cancelarActionPerformed

    private void jBtn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_buscarActionPerformed
        setTaula_afegirCansons();
    }//GEN-LAST:event_jBtn_buscarActionPerformed

    private void jCB_cansoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_cansoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCB_cansoActionPerformed

    private void btn_invertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invertirActionPerformed
        Collections.reverse(llista_productes);
        setDatos_productesPrincipals();
    }//GEN-LAST:event_btn_invertirActionPerformed

    private void btn_crearnouActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearnouActionPerformed
        natejar_generals();
        activar_generals(true);
        ACTUALITAR.setEnabled(false);
        if (jCB_Album1.isSelected() || jCB_llista1.isSelected()) {
            jComboBox_LlistaInterpret.enable(false);
        } else {
            jComboBox_LlistaInterpret.enable();
        }
        acceptar_crear.setEnabled(true);
    }//GEN-LAST:event_btn_crearnouActionPerformed

    private void jComboBox_estilAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jComboBox_estilAncestorAdded

    }//GEN-LAST:event_jComboBox_estilAncestorAdded

    private void jComboBox_estilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_estilActionPerformed

    }//GEN-LAST:event_jComboBox_estilActionPerformed

    private void jButton_llista_petita_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_llista_petita_eliminarActionPerformed
        llista_productes_selecionats.remove(jPanel_llista_selecionats.getSelectedRow());
        setTaula_llista_selecionats();
        missatge_avis("Canso eliminada de la llista");
        //jPanel_llista_selecionats.getSelectedRow()
    }//GEN-LAST:event_jButton_llista_petita_eliminarActionPerformed

    private void jButton_llista_petita_afegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_llista_petita_afegirActionPerformed
        // jDialog_selCancons=new Dialog();
        if (jCB_canso1.isSelected()) {
            missatge_avis("NOMES DISPONIBLE PER ALBUM O LLISTA");
        } else {
            jDialog_selCancons.setTitle("CANSONS");
            jDialog_selCancons.setVisible(true);
            jDialog_selCancons.setAlwaysOnTop(true);
            jDialog_selCancons.setSize(410, 440);
            jDialog_selCancons.setLocation(300, 40);
        }
        setTaula_afegirCansons();
    }//GEN-LAST:event_jButton_llista_petita_afegirActionPerformed

    private void ACTUALITARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACTUALITARActionPerformed
        producte_editan.setTitol(crear_nomCanso.getText());
        String[] split = jComboBox_estil.getSelectedItem().toString().split(":", 2);
        Estil est = new Estil(Integer.parseInt(split[0]), "");
        producte_editan.setEstil(est);
        if (!producte_editan.getTipus().equals("C")) {
            try {
                if (producte_editan.getTipus().equals("A")){
                    bdprod.eliminarContingutLlista((int)producte_editan.getId());
                    bdprod.validarCanvis();
                    for (int i = 0; i < llista_productes_selecionats.size(); i++) {
                        AlbumContingut llc = new AlbumContingut((int) producte_editan.getId(), (int) llista_productes_selecionats.get(i).getId(), i + 1);
                        bdprod.afegirProducteAlbum(llc);
                    }
                } else {
                    bdprod.eliminarContingutLlista((int)producte_editan.getId());
                    bdprod.validarCanvis();
                    for (int i = 0; i < llista_productes_selecionats.size(); i++) {
                        LlistaContingut llc = new LlistaContingut((int) producte_editan.getId(), (int) llista_productes_selecionats.get(i).getId(), i + 1);
                        bdprod.afegirProducteLlista(llc);
                    }
                }
            } catch (SQLException ex) {
                missatge_avis(ex.getMessage());
            } catch (GestorBDSportifyException ex) {
                missatge_avis(ex.getMessage());
            }
        }
        try {
            int i = bdprod.modificarProducte(producte_editan);
            if (i == 0) {
                missatge_avis("ERROR AL MODIFICAR PRODUCTE");
                bdprod.desferCanvis();
            } else {
                missatge_avis("PRODUCTE MODIFICAT CORRECTAMENT");
                bdprod.validarCanvis();
                setDatos_afegirproductesPrincipals();
            }
        } catch (GestorBDSportifyException ex) {
            missatge_avis(ex.getMessage());
        }
    }//GEN-LAST:event_ACTUALITARActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prod.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Prod().setVisible(true);
                } catch (GestorBDSportifyException ex) {
                    Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public String tipo(String t) {
        switch (t) {
            case "C":
                return "Canço";
            case "A":
                return "Àlbum";
            case "L":
                return "Llista";
        }
        return null;
    }

    public String estat(boolean t) {
        if (t == true) {
            return "Actiu";
        }
        return "Inactiu";
    }

    public void missatge_avis(String str) {
        //JDialog jFrame = new JDialog();
        JOptionPane.showMessageDialog(this, str);
    }

    public void activar_generals(Boolean b) {
        jComboBox_estil.setEnabled(b);
        crear_nomCanso.setEnabled(b);
        crear_duradaCanso.setEnabled(b);
        crear_DataCanso.setEnabled(b);
        jComboBox_LlistaInterpret.setEnabled(b);
    }

    public void natejar_generals() {
        crear_nomCanso.setText("");
        crear_DataCanso.cleanup();
        crear_duradaCanso.setText("");
        llista_productes_selecionats.clear();
        setDatos_llista_selecionats();
        jComboBox_estil.setSelectedIndex(-1);
        jComboBox_LlistaInterpret.setSelectedIndex(-1);
        crear_DataCanso.setCalendar(null);
    }

    private void carregar_estils() {
        if (llista_estils.size() < 1) {
            try {
                llista_estils = bdprod.getEstils();
                for (int i = 0; i < llista_estils.size(); i++) {
                    String str = llista_estils.get(i).getId() + ": " + llista_estils.get(i).getNom();
                    jComboBox_estil.addItem(str);
                    //jComboBox_LlistaClients_afegir.addItem(str);
                    str = null;
                }
            } catch (GestorBDSportifyException ex) {
                missatge_avis("error en carregar estils: " + ex.getMessage());
                //Logger.getLogger(Reproduccions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jComboBox_estil.setSelectedIndex(-1);
    }

    private void carregar_artistas() {
        if (llista_artistes.size() < 1) {
            try {
                llista_artistes = bdprod.getArtistes();
                for (int i = 0; i < llista_artistes.size(); i++) {
                    String str = llista_artistes.get(i).getId() + ": " + llista_artistes.get(i).getNom();
                    jComboBox_LlistaInterpret.addItem(str);
                    //jComboBox_LlistaClients_afegir.addItem(str);
                    str = null;
                }
            } catch (GestorBDSportifyException ex) {
                //Logger.getLogger(Reproduccions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jComboBox_LlistaInterpret.setSelectedIndex(-1);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ACTUALITAR;
    private javax.swing.JButton acceptar_crear;
    private javax.swing.JButton btn_crearnou;
    private javax.swing.JButton btn_invertir;
    private javax.swing.JButton cancelar_crearCanso;
    private com.toedter.calendar.JDateChooser crear_DataCanso;
    private javax.swing.JTextField crear_duradaCanso;
    private javax.swing.JTextField crear_nomCanso;
    private javax.swing.JButton jB_editar;
    private javax.swing.JButton jB_ieliminar;
    private javax.swing.JButton jB_imprimir;
    private javax.swing.JButton jBtn_buscar;
    private javax.swing.JButton jBtn_cancelar;
    private javax.swing.JButton jBtn_selecionar;
    private javax.swing.JButton jButton_Buscar;
    private javax.swing.JButton jButton_llista_petita_afegir;
    private javax.swing.JButton jButton_llista_petita_eliminar;
    private javax.swing.JCheckBox jCB_Album;
    private javax.swing.JCheckBox jCB_Album1;
    private javax.swing.JCheckBox jCB_canso;
    private javax.swing.JCheckBox jCB_canso1;
    private javax.swing.JCheckBox jCB_llista;
    private javax.swing.JCheckBox jCB_llista1;
    private javax.swing.JComboBox<String> jComboBox_LlistaInterpret;
    private javax.swing.JComboBox<String> jComboBox_estil;
    private javax.swing.JDialog jDialog_selCancons;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_canso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_general;
    private javax.swing.JTable jPanel_llista_selecionats;
    private javax.swing.JRadioButton jRB_eActiu;
    private javax.swing.JRadioButton jRB_eInactiu;
    private javax.swing.JRadioButton jRB_eTots;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane_cansons;
    private javax.swing.JTable jTable_cansons;
    private javax.swing.JTable jTable_productes;
    private javax.swing.JTable jTable_productes1;
    private javax.swing.JTable jTable_productes2;
    private javax.swing.JTextField jTextField_FragCanso;
    private javax.swing.JTextField jTextField_fragmentArtista;
    // End of variables declaration//GEN-END:variables

}
