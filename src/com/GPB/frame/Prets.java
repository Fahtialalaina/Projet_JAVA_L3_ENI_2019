/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GPB.frame;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author anouer
 */
public class Prets extends javax.swing.JInternalFrame {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    Connection conn1 = null;
    ResultSet rs1 = null;
    PreparedStatement ps1 = null;
    public static String v4;
    public static String type;
    public static String parc;
    public static String typepermis;
    public static String test;
    public static String test2;
    public static String test3;
    public static String tes;

    /**
     * Creates new form Formation
     */
    public Prets() {
        initComponents();
        remove_title_bar();
        conn = ConexionBD.Conexion();
        conn1 = ConexionBD.Conexion();
        AffichagePret();
        affichageBanque();
        affichageClient();
        suppPret.setEnabled(false);
        modifierPret.setEnabled(false);
        ImageIcon img = new ImageIcon(getClass().getResource("txt2.png"));
        txtbachground.setIcon(img);
        txtreca.setText("Taper le Nom ou Prénoms du Client");
        txtrechercherBanque.setText("Taper le Nom de la Banque");
        txtrechercherPret.setText("Rechercher ici!");
    }

    public void deplacePret() {
        try {
            int row = Table4.getSelectedRow();
            Prets.test3 = (Table4.getModel().getValueAt(row, 0).toString());
            String requet = " select * from  pret where idPret= '" + test3 + "' ";
            ps = conn.prepareStatement(requet);
            rs = ps.executeQuery();
            System.out.println(test3);
            if (rs.next()) {
                String t1 = rs.getString("Montant");
                txtMontant.setText(t1);
                String t2 = rs.getString("DatePret");

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(t2);

                DatePret.setDate(date);

                String t3 = rs.getString("idPret");
                idPret.setText(t3);
            }

        } catch (SQLException | ParseException e) {
            System.out.println(e);
        }
    }

    public void suppPret() {
        try {
            if (JOptionPane.showConfirmDialog(null, "Attention vous devez suprimer un Pret, est-ce que tu es sûr?",
                    "Supprimer Pret", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {

                String requete = "delete from pret where idPret = '" + test3 + "'";
                ps = conn.prepareStatement(requete);

                ps.execute();

            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Rrreur de suppression\n" + e.getMessage());
        }
        refreshPret();

    }

    public void deplaceBanque() {
        try {
            int row = Table3.getSelectedRow();
            Prets.test2 = (Table3.getModel().getValueAt(row, 0).toString());
            String requet = " select * from  banque where NumBanque = '" + test2 + "' ";
            ps = conn.prepareStatement(requet);
            rs = ps.executeQuery();

            if (rs.next()) {
                String t1 = rs.getString("NumBanque");
                txtidBanque.setText(t1);
                String t2 = rs.getString("NomBanque");
                txtNomBanque.setText(t2);
                String t3 = rs.getString("PretBanque");
                Tauxlbl.setText(t3);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void deplacePretBanque() {
        try {
            int row = Table4.getSelectedRow();
            Prets.test2 = (Table4.getModel().getValueAt(row, 2).toString());
            String requet = " select * from  banque where NumBanque = '" + test2 + "' ";
            ps = conn.prepareStatement(requet);
            rs = ps.executeQuery();

            if (rs.next()) {
                String t1 = rs.getString("NumBanque");
                txtidBanque.setText(t1);
//                String t2 = rs.getString("Categoriepermis");
//                txtper.setText(t2);
                String t2 = rs.getString("NomBanque");
                txtNomBanque.setText(t2);
                String t3 = rs.getString("PretBanque");
                Tauxlbl.setText(t3);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void rechercheclient() {

        try {

            String requete = "select cin as 'CIN' ,nomc as 'Nom' ,prenomc as 'Prenom' ,date_naissance as 'Date de Naissance',sexe As 'Sexe',gsm as 'GSM',adresse as 'Adresse',date_inscription as 'Date dinscription' from  client_table where nomc LIKE ? OR prenomc LIKE ?";
            ps = conn.prepareStatement(requete);
            ps.setString(1, "%" + txtreca.getText() + "%");
            ps.setString(2, "%" + txtreca.getText() + "%");
            rs = ps.executeQuery();
            Table2.setModel(DbUtils.resultSetToTableModel(rs));

            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {

            try {
                ps.close();
                rs.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "erreur BD");
            }
        }

    }

    public void refreshPret() {
        try {

            String requete6 = "select idPret as 'IdPret' ,cincand as 'Cin_Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where cincand='" + test + "'";
            ps = conn.prepareStatement(requete6);
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deplaceClient() {
        try {
            int row = Table2.getSelectedRow();
            Prets.test = (Table2.getModel().getValueAt(row, 0).toString());
            String requet = " select * from  client_table where cin = '" + test + "' ";
            ps = conn.prepareStatement(requet);
            rs = ps.executeQuery();

            if (rs.next()) {
                String t1 = rs.getString("cin");
                txtcin.setText(t1);
                String t2 = rs.getString("nomc");
                String t3 = rs.getString("prenomc");
                txtnomprenom.setText(t2 + "  " + " " + " " + t3);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void deplacePretClient() {
        try {
            int row = Table4.getSelectedRow();
            Prets.test = (Table4.getModel().getValueAt(row, 1).toString());
            String requet = " select * from  client_table where cin = '" + test + "' ";
            ps = conn.prepareStatement(requet);
            rs = ps.executeQuery();

            if (rs.next()) {
                String t1 = rs.getString("cin");
                txtcin.setText(t1);
                String t2 = rs.getString("nomc");
                String t3 = rs.getString("prenomc");
                txtnomprenom.setText(t2 + "  " + " " + " " + t3);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void remove_title_bar() {
        putClientProperty("Formation.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.setBorder(null);
    }

    private void AffichagePret() {
        try {

            String requete6 = "select idPret as 'IdPret' ,cincand as 'CIN Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret";
            ps = conn.prepareStatement(requete6);
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void affichageBanque() {
        try {
            String requete2 = "select NumBanque as 'NumBanque' ,NomBanque as 'NomBanque' ,ContactBanque as 'ContactBanque' ,PretBanque as 'PretBanque' from banque ";
            ps = conn.prepareStatement(requete2);
            rs = ps.executeQuery();
            Table3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void affichageClient() {
        try {
            String requete = "select cin as 'CIN' ,nomc as 'Nom',prenomc as 'Prenom',date_naissance as 'Date de Naissance',adresse as 'Adresse',sexe As 'Sexe',gsm as 'GSM',date_inscription as 'Date dinscription' from client_table";
            ps = conn.prepareStatement(requete);
            rs = ps.executeQuery();
            Table2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtreca = new javax.swing.JTextField();
        txtbachground = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table4 = new javax.swing.JTable();
        AjoutPret = new javax.swing.JButton();
        Panelinfo = new javax.swing.JPanel();
        txtidBanque = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtnomprenom = new javax.swing.JTextField();
        txtcin = new javax.swing.JTextField();
        txtNomBanque = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txnom = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Tauxlbl = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMontant = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        DatePret = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        idPret = new javax.swing.JLabel();
        btnrefresh = new javax.swing.JButton();
        suppPret = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        printbtn = new javax.swing.JButton();
        modifierPret = new javax.swing.JButton();
        formationbtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        date1 = new com.toedter.calendar.JDateChooser();
        date2 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        Rech2date = new javax.swing.JButton();
        txtrechercherBanque = new javax.swing.JTextField();
        txtbackground1 = new javax.swing.JLabel();
        txtrechercherPret = new javax.swing.JTextField();
        txtbackground2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        TotalAPayer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Effectif1 = new javax.swing.JLabel();

        setBorder(null);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(null);

        txtreca.setBackground(new java.awt.Color(240, 240, 240));
        txtreca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtreca.setForeground(new java.awt.Color(0, 153, 153));
        txtreca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtreca.setToolTipText("");
        txtreca.setBorder(null);
        txtreca.setDoubleBuffered(true);
        txtreca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtrecaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtrecaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtrecaMouseExited(evt);
            }
        });
        txtreca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrecaActionPerformed(evt);
            }
        });
        txtreca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrecaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrecaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrecaKeyTyped(evt);
            }
        });
        getContentPane().add(txtreca);
        txtreca.setBounds(52, 58, 213, 14);

        txtbachground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/frame/txt2.png"))); // NOI18N
        getContentPane().add(txtbachground);
        txtbachground.setBounds(50, 50, 220, 30);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        Table2.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        Table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Table2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Table2.setRowHeight(20);
        Table2.setSelectionBackground(new java.awt.Color(0, 153, 153));
        Table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table2MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table2MouseReleased(evt);
            }
        });
        Table2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table2KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Table2);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 79, 420, 240);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        Table3.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        Table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Table3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Table3.setRowHeight(20);
        Table3.setSelectionBackground(new java.awt.Color(0, 153, 153));
        Table3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table3MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Table3);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(740, 80, 300, 240);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)), "Listes des Prets :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 3, 10))); // NOI18N

        Table4.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        Table4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Table4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Table4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Table4.setSelectionBackground(new java.awt.Color(0, 153, 153));
        Table4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table4MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table4MouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(Table4);
        Table4.getAccessibleContext().setAccessibleName("\n");

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(0, 410, 1040, 250);

        AjoutPret.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        AjoutPret.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/save.png"))); // NOI18N
        AjoutPret.setText("Enregistrer");
        AjoutPret.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        AjoutPret.setContentAreaFilled(false);
        AjoutPret.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AjoutPretMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AjoutPretMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AjoutPretMousePressed(evt);
            }
        });
        AjoutPret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjoutPretActionPerformed(evt);
            }
        });
        getContentPane().add(AjoutPret);
        AjoutPret.setBounds(430, 330, 150, 30);

        Panelinfo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true), "Ajout Pret :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 12))); // NOI18N

        txtidBanque.setEditable(false);

        jLabel11.setText("CIN Client :");

        jLabel13.setText("Nom & Prénom :");

        jLabel12.setText("Numero Banque :");

        jLabel14.setText("Nom de la Banque :");

        txtnomprenom.setEditable(false);
        txtnomprenom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtnomprenom.setForeground(new java.awt.Color(0, 0, 153));
        txtnomprenom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnomprenom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnomprenomActionPerformed(evt);
            }
        });

        txtcin.setEditable(false);
        txtcin.setForeground(new java.awt.Color(0, 0, 153));

        txtNomBanque.setEditable(false);

        jLabel25.setName(""); // NOI18N

        txnom.setName(""); // NOI18N

        jLabel1.setText("Taux du Pret :");

        Tauxlbl.setText("        ");

        jLabel4.setText("%");

        jLabel3.setText("Montant du Pret :");

        txtMontant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontantActionPerformed(evt);
            }
        });

        jLabel5.setText("Date du Pret :");

        DatePret.setDateFormatString("yyyy-MM-dd");

        jLabel6.setText("id :");

        idPret.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        idPret.setText("           ");

        btnrefresh.setBackground(new java.awt.Color(153, 0, 0));
        btnrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/interface.png"))); // NOI18N
        btnrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelinfoLayout = new javax.swing.GroupLayout(Panelinfo);
        Panelinfo.setLayout(PanelinfoLayout);
        PanelinfoLayout.setHorizontalGroup(
            PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelinfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelinfoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                                .addComponent(jLabel25))
                            .addComponent(txnom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(PanelinfoLayout.createSequentialGroup()
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tauxlbl)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(0, 116, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelinfoLayout.createSequentialGroup()
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelinfoLayout.createSequentialGroup()
                                .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelinfoLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtcin)
                                            .addComponent(txtidBanque)
                                            .addComponent(txtNomBanque)))
                                    .addGroup(PanelinfoLayout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(txtnomprenom))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelinfoLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(idPret)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelinfoLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DatePret, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMontant))))
                        .addGap(1, 1, 1))))
        );
        PanelinfoLayout.setVerticalGroup(
            PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelinfoLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7)
                        .addComponent(idPret))
                    .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelinfoLayout.createSequentialGroup()
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnomprenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidBanque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomBanque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(Tauxlbl))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMontant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(DatePret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelinfoLayout.createSequentialGroup()
                        .addComponent(txnom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)))
                .addContainerGap())
        );

        getContentPane().add(Panelinfo);
        Panelinfo.setBounds(430, 70, 300, 250);

        suppPret.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        suppPret.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/file (1).png"))); // NOI18N
        suppPret.setText("Supprimer");
        suppPret.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        suppPret.setContentAreaFilled(false);
        suppPret.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        suppPret.setOpaque(true);
        suppPret.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                suppPretMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                suppPretMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                suppPretMousePressed(evt);
            }
        });
        suppPret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suppPretActionPerformed(evt);
            }
        });
        getContentPane().add(suppPret);
        suppPret.setBounds(430, 370, 150, 30);
        getContentPane().add(jLabel21);
        jLabel21.setBounds(1040, 180, 40, 20);

        printbtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        printbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/printer.png"))); // NOI18N
        printbtn.setText("Imprimer");
        printbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        printbtn.setContentAreaFilled(false);
        printbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printbtn.setOpaque(true);
        printbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                printbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                printbtnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                printbtnMousePressed(evt);
            }
        });
        printbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtnActionPerformed(evt);
            }
        });
        getContentPane().add(printbtn);
        printbtn.setBounds(590, 370, 150, 30);

        modifierPret.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        modifierPret.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/file (2).png"))); // NOI18N
        modifierPret.setText("Modifier");
        modifierPret.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        modifierPret.setContentAreaFilled(false);
        modifierPret.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        modifierPret.setOpaque(true);
        modifierPret.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                modifierPretMouseMoved(evt);
            }
        });
        modifierPret.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                modifierPretMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                modifierPretMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                modifierPretMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                modifierPretMouseReleased(evt);
            }
        });
        modifierPret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifierPretActionPerformed(evt);
            }
        });
        getContentPane().add(modifierPret);
        modifierPret.setBounds(590, 330, 150, 30);

        formationbtn.setBackground(new java.awt.Color(255, 255, 255));
        formationbtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        formationbtn.setForeground(new java.awt.Color(0, 153, 153));
        formationbtn.setText("Afficher tout");
        formationbtn.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        formationbtn.setContentAreaFilled(false);
        formationbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formationbtn.setMinimumSize(new java.awt.Dimension(69, 23));
        formationbtn.setOpaque(true);
        formationbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formationbtnMouseReleased(evt);
            }
        });
        formationbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formationbtnActionPerformed(evt);
            }
        });
        getContentPane().add(formationbtn);
        formationbtn.setBounds(0, 390, 120, 21);

        jLabel8.setText("Recherche entre :");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(780, 330, 90, 14);

        date1.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(date1);
        date1.setBounds(780, 350, 120, 20);

        date2.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(date2);
        date2.setBounds(930, 350, 110, 20);

        jLabel9.setText("et");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(910, 350, 30, 20);

        Rech2date.setText("Recherche");
        Rech2date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rech2dateActionPerformed(evt);
            }
        });
        getContentPane().add(Rech2date);
        Rech2date.setBounds(780, 380, 260, 23);

        txtrechercherBanque.setBackground(new java.awt.Color(240, 240, 240));
        txtrechercherBanque.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtrechercherBanque.setForeground(new java.awt.Color(0, 153, 153));
        txtrechercherBanque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrechercherBanque.setBorder(null);
        txtrechercherBanque.setDoubleBuffered(true);
        txtrechercherBanque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtrechercherBanqueMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtrechercherBanqueMouseEntered(evt);
            }
        });
        txtrechercherBanque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrechercherBanqueActionPerformed(evt);
            }
        });
        txtrechercherBanque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrechercherBanqueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrechercherBanqueKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrechercherBanqueKeyTyped(evt);
            }
        });
        getContentPane().add(txtrechercherBanque);
        txtrechercherBanque.setBounds(780, 60, 200, 10);

        txtbackground1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/frame/txt2.png"))); // NOI18N
        getContentPane().add(txtbackground1);
        txtbackground1.setBounds(770, 50, 220, 30);

        txtrechercherPret.setBackground(new java.awt.Color(240, 240, 240));
        txtrechercherPret.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtrechercherPret.setForeground(new java.awt.Color(0, 153, 153));
        txtrechercherPret.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrechercherPret.setBorder(null);
        txtrechercherPret.setDoubleBuffered(true);
        txtrechercherPret.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtrechercherPretMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtrechercherPretMouseEntered(evt);
            }
        });
        txtrechercherPret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrechercherPretActionPerformed(evt);
            }
        });
        txtrechercherPret.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrechercherPretKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrechercherPretKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtrechercherPretKeyTyped(evt);
            }
        });
        getContentPane().add(txtrechercherPret);
        txtrechercherPret.setBounds(170, 390, 200, 10);

        txtbackground2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/frame/txt2.png"))); // NOI18N
        getContentPane().add(txtbackground2);
        txtbackground2.setBounds(160, 380, 220, 30);

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        jLabel10.setFont(new java.awt.Font("Advent Pro", 0, 40)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("GESTION DES PRETS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(648, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 0, 1160, 50);

        jLabel15.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel15.setText("TOTAL A PAYER : ");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(140, 340, 130, 14);

        TotalAPayer.setText("        ");
        getContentPane().add(TotalAPayer);
        TotalAPayer.setBounds(270, 340, 160, 14);

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel2.setText("EFFECTIF : ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 340, 70, 14);

        Effectif1.setText("           ");
        getContentPane().add(Effectif1);
        Effectif1.setBounds(80, 340, 34, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MouseClicked

    private void Table2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseReleased
        deplaceClient();
        refreshPret();

        AjoutPret.setEnabled(true);
        modifierPret.setEnabled(false);
        suppPret.setEnabled(false);
        txtidBanque.setText("");
        txtNomBanque.setText("");
        idPret.setText("");
        Tauxlbl.setText("");
        txtMontant.setText("");
        DatePret.setDate(new Date());
    }//GEN-LAST:event_Table2MouseReleased

    private void Table3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MouseReleased
        deplaceBanque();
        Date actuelle = new Date();
        DatePret.setDate(actuelle);
    }//GEN-LAST:event_Table3MouseReleased

    private void AjoutPretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjoutPretActionPerformed
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String a = txtcin.getText();
            String requete1 = "select cincand from pret where cincand='" + a + "'";
            System.out.println(requete1);
            System.out.println(a);
            ps1 = conn1.prepareStatement(requete1);
            rs1 = ps1.executeQuery();
            System.out.println(rs1);
            
            if(rs1.next()){
                JOptionPane.showMessageDialog(null, "Ce client a déja fait une pret dans une banque");
            }else{
                String requete = "insert into pret (cincand,NumBanque,Montant,MontantAPayer,datePret) values (?,?,?,?,?)";
                ps = conn.prepareStatement(requete);
                ps.setString(1, txtcin.getText());
                ps.setString(2, txtidBanque.getText());
                ps.setString(3, txtMontant.getText());
                
                long Montant = Long.parseLong(txtMontant.getText());
                int Taux = Integer.parseInt(Tauxlbl.getText());
                long MontantAPayer = ((Montant * Taux) / 100) + Montant;
                System.out.println("(" + Montant + " x " + Taux + " / " + 100 + ") + " + Montant + " = " + MontantAPayer + "");
                ps.setString(4, String.valueOf(MontantAPayer));
                
                String datePret = dateFormat.format(DatePret.getDate());
                ps.setString(5, datePret);
                
                ps.execute();
                
                JOptionPane.showMessageDialog(null, "Enregistrement avec succès");
                Table4.setBackground(Color.WHITE);
            }

        } catch (HeadlessException | NumberFormatException | SQLException e) {
            System.out.println("--> SQLException : " + e);
            JOptionPane.showMessageDialog(null, "Tout est Obligatoire \n -> Montant doit inférieur à 20 Millions");
        } finally {

            try {
                ps.close();
                rs.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "deja inseré");
            }
        }

        refreshPret();

    }//GEN-LAST:event_AjoutPretActionPerformed

    private void Table4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table4MouseReleased
        suppPret.setEnabled(true);
        AjoutPret.setEnabled(false);
        modifierPret.setEnabled(true);
        deplacePretClient();
        deplacePretBanque();
        deplacePret();

//clearsuivi();

    }//GEN-LAST:event_Table4MouseReleased

    private void txtrecaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtrecaMouseClicked
        txtreca.setText("");
    }//GEN-LAST:event_txtrecaMouseClicked

    private void txtrecaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtrecaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrecaMouseEntered

    private void txtrecaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrecaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrecaActionPerformed

    private void txtrecaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrecaKeyPressed

    }//GEN-LAST:event_txtrecaKeyPressed

    private void txtrecaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrecaKeyReleased
        rechercheclient();
    }//GEN-LAST:event_txtrecaKeyReleased

    private void txtrecaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrecaKeyTyped


    }//GEN-LAST:event_txtrecaKeyTyped

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void Table4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Table4MouseClicked

    private void Table2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table2KeyReleased
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            deplaceClient();
            refreshPret();
            txtidBanque.setText("");
            txtNomBanque.setText("");
        }
    }//GEN-LAST:event_Table2KeyReleased

    private void printbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtnMouseEntered
        printbtn.setBackground(new java.awt.Color(0, 153, 153));
    }//GEN-LAST:event_printbtnMouseEntered

    private void printbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtnMouseExited
        printbtn.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_printbtnMouseExited

    private void printbtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtnMousePressed
        printbtn.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_printbtnMousePressed

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed

        MessageFormat header = new MessageFormat("Liste des Prets:");
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
        try {
            Table4.print(JTable.PrintMode.NORMAL, header, footer);

        } catch (java.awt.print.PrinterException e) {
            System.err.format("Erreur d'impression ", e.getMessage());
        }
    }//GEN-LAST:event_printbtnActionPerformed

    private void suppPretMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suppPretMouseEntered
        suppPret.setBackground(new java.awt.Color(0,153,153));
    }//GEN-LAST:event_suppPretMouseEntered

    private void suppPretMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suppPretMouseExited
        suppPret.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_suppPretMouseExited

    private void suppPretMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suppPretMousePressed
        suppPret.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_suppPretMousePressed

    private void suppPretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suppPretActionPerformed
        suppPret();
    }//GEN-LAST:event_suppPretActionPerformed

    private void AjoutPretMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AjoutPretMouseEntered
        AjoutPret.setBackground(new java.awt.Color(0,153,153));
    }//GEN-LAST:event_AjoutPretMouseEntered

    private void AjoutPretMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AjoutPretMouseExited
        AjoutPret.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_AjoutPretMouseExited

    private void AjoutPretMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AjoutPretMousePressed
        AjoutPret.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AjoutPretMousePressed

    private void modifierPretMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modifierPretMouseMoved

    }//GEN-LAST:event_modifierPretMouseMoved

    private void modifierPretMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modifierPretMouseEntered
        modifierPret.setBackground(new java.awt.Color(0, 153, 153));
    }//GEN-LAST:event_modifierPretMouseEntered

    private void modifierPretMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modifierPretMouseExited
        modifierPret.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_modifierPretMouseExited

    private void modifierPretMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modifierPretMousePressed
        modifierPret.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_modifierPretMousePressed

    private void modifierPretMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modifierPretMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_modifierPretMouseReleased

    private void modifierPretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifierPretActionPerformed
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String requete = "update pret set cincand =?,NumBanque =?,Montant=?,MontantAPayer =?,DatePret =? where  idPret ='" + idPret.getText() + "'";

        try {
            /*String a = txtcin.getText();
            String requete1 = "select cincand from pret where cincand='" + a + "'";
            System.out.println(requete1);
            System.out.println(a);
            ps1 = conn1.prepareStatement(requete1);
            rs1 = ps1.executeQuery();
            System.out.println(rs1);
            
            if(rs1.next()){
                JOptionPane.showMessageDialog(null, "Ce client a déja fait une pret dans une banque");
            }else{*/
            
            ps = conn.prepareStatement(requete);
            ps.setString(1, txtcin.getText());
            ps.setString(2, txtidBanque.getText());
            ps.setString(3, txtMontant.getText());
            long Montant = Long.parseLong(txtMontant.getText());
            int Taux = Integer.parseInt(Tauxlbl.getText());
            long MontantAPayer = ((Montant * Taux) / 100) + Montant;
            ps.setString(4, String.valueOf(MontantAPayer));
            String DatePret1 = dateFormat.format(DatePret.getDate());

            ps.setString(5, DatePret1);
            System.out.println(MontantAPayer);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Modification avec succès");
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {

            try {
                ps.close();
                rs.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "erreur BD");
            }
        }

        refreshPret();
    }//GEN-LAST:event_modifierPretActionPerformed

    private void formationbtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formationbtnMouseReleased
        formationbtn.setBackground(new java.awt.Color(0, 204, 04));
    }//GEN-LAST:event_formationbtnMouseReleased

    private void formationbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formationbtnActionPerformed
        AffichagePret();
        affichageBanque();
        affichageClient();
        AjoutPret.setEnabled(true);
        suppPret.setEnabled(false);
        modifierPret.setEnabled(false);
        
        Effectif1.setText("");
        TotalAPayer.setText("");
        idPret.setText("");
        txtnomprenom.setText("");
        txtcin.setText("");
        txtidBanque.setText("");
        txtNomBanque.setText("");
        idPret.setText("");
        Tauxlbl.setText("");
        txtMontant.setText("");
        DatePret.setDate(new Date());
    }//GEN-LAST:event_formationbtnActionPerformed

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        txtreca.setText("Taper le Nom ou Prénoms du Client");
        txtrechercherBanque.setText("Taper le Nom de la Banque");
        txtrechercherPret.setText("Rechercher ici!");
        AffichagePret();
        affichageBanque();
        affichageClient();
        AjoutPret.setEnabled(true);
        suppPret.setEnabled(false);
        modifierPret.setEnabled(false);
        idPret.setText("");
        txtnomprenom.setText("");
        txtcin.setText("");
        txtidBanque.setText("");
        txtNomBanque.setText("");
        idPret.setText("");
        Tauxlbl.setText("");
        txtMontant.setText("");
        Effectif1.setText("");
        TotalAPayer.setText("");
        DatePret.setDate(new Date());
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void txtMontantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontantActionPerformed

    private void txtnomprenomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnomprenomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnomprenomActionPerformed

    private void Rech2dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rech2dateActionPerformed
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateAvant = dateFormat.format(date1.getDate());
            String dateApret = dateFormat.format(date2.getDate());

            String requete6 = "select idPret as 'IdPret' ,cincand as 'CIN Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where datePret between '" + dateAvant + "' and '" + dateApret + "' order by datePret desc";
            System.out.println(requete6);
            ps = conn.prepareStatement(requete6);
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));
            
            String requete7 = "select idPret as 'IdPret' ,cincand as 'CIN Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where datePret between '" + dateAvant + "' and '" + dateApret + "' order by datePret desc";
            System.out.println(requete7);
            ps1 = conn1.prepareStatement(requete7);
            rs1 = ps1.executeQuery();
            long MontT = 0;
            long eff = 0;
            
            while(rs1.next()){
                String Mont = rs1.getString(5);
                long MontP = Long.parseLong(Mont);
                eff = eff + 1;
                MontT = MontT + MontP;
            }
            
            System.out.print("MontantTotal : " + MontT);
            System.out.print("Effectif : " + eff);
            
            Effectif1.setText(String.valueOf(eff));
            TotalAPayer.setText(String.valueOf(MontT));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_Rech2dateActionPerformed

    private void txtrechercherBanqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtrechercherBanqueMouseClicked
        txtrechercherBanque.setText("");
    }//GEN-LAST:event_txtrechercherBanqueMouseClicked

    private void txtrechercherBanqueMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtrechercherBanqueMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechercherBanqueMouseEntered

    private void txtrechercherBanqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrechercherBanqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechercherBanqueActionPerformed

    private void txtrechercherBanqueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrechercherBanqueKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechercherBanqueKeyPressed

    private void txtrechercherBanqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrechercherBanqueKeyReleased
        try {
            String requete = "select NumBanque as 'NumBanque' ,NomBanque as 'NomBanque' ,ContactBanque as 'ContactBanque' ,PretBanque as 'PretBanque' from banque where NomBanque LIKE ?";
            ps = conn.prepareStatement(requete);
            ps.setString(1, "%" + txtrechercherBanque.getText() + "%");
            rs = ps.executeQuery();
            Table3.setModel(DbUtils.resultSetToTableModel(rs));
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {

            try {
                ps.close();
                rs.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "erreur BD");
            }
        }

    }//GEN-LAST:event_txtrechercherBanqueKeyReleased

    private void txtrechercherBanqueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrechercherBanqueKeyTyped

    }//GEN-LAST:event_txtrechercherBanqueKeyTyped

    private void txtrecaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtrecaMouseExited

    }//GEN-LAST:event_txtrecaMouseExited

    private void txtrechercherPretMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtrechercherPretMouseClicked
        txtrechercherPret.setText("");
    }//GEN-LAST:event_txtrechercherPretMouseClicked

    private void txtrechercherPretMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtrechercherPretMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechercherPretMouseEntered

    private void txtrechercherPretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrechercherPretActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechercherPretActionPerformed

    private void txtrechercherPretKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrechercherPretKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechercherPretKeyPressed

    private void txtrechercherPretKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrechercherPretKeyReleased
        try {
            String requete = "select idPret as 'IdPret' ,cincand as 'CIN Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where NumBanque LIKE ? or Montant LIKE ? or cincand LIKE ? or MontantAPayer LIKE ? or datePret LIKE ?";
            ps = conn.prepareStatement(requete);
            ps.setString(1, "%" + txtrechercherPret.getText() + "%");
            ps.setString(2, "%" + txtrechercherPret.getText() + "%");
            ps.setString(3, "%" + txtrechercherPret.getText() + "%");
            ps.setString(4, "%" + txtrechercherPret.getText() + "%");
            ps.setString(5, "%" + txtrechercherPret.getText() + "%");
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {

            try {
                ps.close();
                rs.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "erreur BD");
            }
        }
    }//GEN-LAST:event_txtrechercherPretKeyReleased

    private void txtrechercherPretKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrechercherPretKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechercherPretKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AjoutPret;
    private com.toedter.calendar.JDateChooser DatePret;
    private javax.swing.JLabel Effectif1;
    private javax.swing.JPanel Panelinfo;
    private javax.swing.JButton Rech2date;
    private javax.swing.JTable Table2;
    private javax.swing.JTable Table3;
    private javax.swing.JTable Table4;
    private javax.swing.JLabel Tauxlbl;
    private javax.swing.JLabel TotalAPayer;
    private javax.swing.JButton btnrefresh;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JButton formationbtn;
    private javax.swing.JLabel idPret;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JButton modifierPret;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton suppPret;
    private javax.swing.JLabel txnom;
    private javax.swing.JTextField txtMontant;
    private javax.swing.JTextField txtNomBanque;
    private javax.swing.JLabel txtbachground;
    private javax.swing.JLabel txtbackground1;
    private javax.swing.JLabel txtbackground2;
    private javax.swing.JTextField txtcin;
    private javax.swing.JTextField txtidBanque;
    private javax.swing.JTextField txtnomprenom;
    private javax.swing.JTextField txtreca;
    private javax.swing.JTextField txtrechercherBanque;
    private javax.swing.JTextField txtrechercherPret;
    // End of variables declaration//GEN-END:variables
}
