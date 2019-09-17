/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GPB.frame;

import com.mysql.jdbc.ResultSetMetaData;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author anouer
 */
public class Effectif extends javax.swing.JInternalFrame {

    Connection conn = null;
    Connection conn1 = null;
    Connection conn2 = null;
    Connection conn3 = null;
    Connection conn4 = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    ResultSet rs1 = null;
    PreparedStatement ps1 = null;
    ResultSet rs2 = null;
    PreparedStatement ps2 = null;
    ResultSet rs3 = null;
    PreparedStatement ps3 = null;
    ResultSet rs4 = null;
    PreparedStatement ps4 = null;
    public static String v4;
    public static String type;
    public static String parc;
    public static String typepermis;
    public static String test;
    public static String test2;
    public static String test3;
    public static String tes;
    ChartPanel chartPanel;
    ChartPanel chartPanel2;

    /**
     * Creates new form Formation
     */
    public Effectif() {
        initComponents();
        remove_title_bar();
        conn = ConexionBD.Conexion();
        conn1 = ConexionBD.Conexion();
        conn2 = ConexionBD.Conexion();
        conn3 = ConexionBD.Conexion();
        conn4 = ConexionBD.Conexion();
        //AffichagePret();
        affichageTableauEffectif();
        affichagePieBanque();
        System.out.println("Réussit");

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

    public void rechercheBanque() {

        try {

            String requete = "select cin as 'CIN' ,nomc as 'Nom' ,prenomc as 'Prenom' ,gsm as 'GSM',adresse as 'Adresse',date_inscription as 'Date dinscription' from  candidat_table where nomc LIKE ?";
            ps = conn.prepareStatement(requete);
//            ps.setString(1, "%"+txtreca.getText()+"%");
            rs = ps.executeQuery();
            Table3.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void refreshPret() {
        try {

            String requete6 = "select idPret as 'IdPret' ,cincand as 'Cin_Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where NumBanque='" + test + "'";
            ps = conn.prepareStatement(requete6);
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));

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

    public void AffichagePret() {
        try {

            String requete6 = "select idPret as 'IdPret' ,cincand as 'CIN Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret";
            ps = conn.prepareStatement(requete6);
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void affichagePieBanque() {

        this.jPanel1.removeAll();
        this.jPanel1.repaint();
        this.jPanel2.removeAll();
        this.jPanel2.repaint();
        try {
            String requete3 = "select NomBanque as 'Banque',Effectif as 'Effectif',TotalPret as 'Total Pret'  from  effectif";
            ps3 = conn3.prepareStatement(requete3);
            rs3 = ps3.executeQuery();
            //Table3.setModel(DbUtils.resultSetToTableModel(rs3));

            ResultSetMetaData a = (ResultSetMetaData) rs3.getMetaData();
            int nbColonnes = a.getColumnCount();

            DefaultPieDataset data = new DefaultPieDataset();
            DefaultPieDataset data2 = new DefaultPieDataset();

            while (rs3.next()) {

                for (int i = 1; i <= nbColonnes; i++) {
                    String laValeur = rs3.getString(i);
                    System.out.println(laValeur + " " + a.getColumnName(i));
                }

                String NomBanque = rs3.getString(1);
                System.out.println("Banque = " + NomBanque);
                String EffectifClient = rs3.getString(2);
                System.out.println("Effectif = " + EffectifClient);
                String TotalPret = rs3.getString(3);
                System.out.println("TotalPret = " + TotalPret);

                data.setValue(NomBanque, Long.parseLong(EffectifClient));
                data2.setValue(NomBanque, Long.parseLong(TotalPret));
            }

            boolean URLs = false;
            boolean legend = false;
            boolean tooltips = false;
            JFreeChart chart = ChartFactory.createPieChart("Effectif Client par Banque", data, true, true, false);
            JFreeChart chart2 = ChartFactory.createPieChart("Effectif Montant par Banque", data2, true, true, false);
            chartPanel = new ChartPanel(chart);
            chartPanel2 = new ChartPanel(chart2);
            jPanel1.setLayout(new BorderLayout());
            jPanel1.add(chartPanel, BorderLayout.CENTER);
            jPanel1.show();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.add(chartPanel2, BorderLayout.CENTER);
            jPanel2.show();

        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }

    private void affichageTableauEffectif() {
        try {

            String requete4 = "delete from effectif";
            ps4 = conn4.prepareStatement(requete4);
            ps4.execute();

            String requete = "select NumBanque, NomBanque, PretBanque from banque";
            System.out.println(requete);
            ps = conn.prepareStatement(requete);
            rs = ps.executeQuery();
            ResultSetMetaData a = (ResultSetMetaData) rs.getMetaData();
            int nbColonnes = a.getColumnCount();

            while (rs.next()) {

                for (int i = 1; i <= nbColonnes; i++) {
                    String laValeur = rs.getString(i);
                    System.out.println(laValeur + " " + a.getColumnName(i));
                }

                String Num = rs.getString(1);
                System.out.println("Valeur de num = " + Num);
                int NumInt = Integer.parseInt(Num);
                String Nom = rs.getString(2);
                System.out.println("Valeur de nom = " + Nom);
                String TauxPret = rs.getString(3);
                System.out.println("Valeur de taux = " + TauxPret);

                String requete1 = "select idPret as 'IdPret' ,cincand as 'Cin_Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' from  pret where NumBanque= '" + NumInt + "'";
                System.out.println(requete1);
                ps1 = conn1.prepareStatement(requete1);
                rs1 = ps1.executeQuery();

                ResultSetMetaData a1;
                a1 = (ResultSetMetaData) rs1.getMetaData();
                int nbColonnes1 = a1.getColumnCount();

                long eff = 0;
                long Mont = 0;
                long MontP = 0;

                while (rs1.next()) {
                    eff = eff + 1;

                    String MontantPret = rs1.getString(4);
                    String MontantP = rs1.getString(5);
                    Mont = Mont + Long.parseLong(MontantPret);
                    MontP = MontP + Long.parseLong(MontantP);

                }
                System.out.println("Effectif : " + eff);
                System.out.println("MontantTotal : " + Mont);
                System.out.println("MontantTotalAPay : " + MontP);

                String requete2 = "insert into effectif (NumBanque,NomBanque,Taux,Effectif,TotalPret,TotalAPayer) values (?,?,?,?,?,?)";
                ps2 = conn2.prepareStatement(requete2);

                ps2.setString(1, Num);
                ps2.setString(2, Nom);
                ps2.setString(3, TauxPret);
                ps2.setString(4, String.valueOf(eff));
                ps2.setString(5, String.valueOf(Mont));
                ps2.setString(6, String.valueOf(MontP));

                ps2.execute();

            }

            try {

                String requete3 = "select NumBanque as 'Numero Banque', NomBanque as 'Banque' , Taux as 'Taux' ,Effectif as 'Effectif',TotalPret as 'Total Pret' ,TotalAPayer as 'Total à Payer' from  effectif";
                ps3 = conn3.prepareStatement(requete3);
                rs3 = ps3.executeQuery();
                Table3.setModel(DbUtils.resultSetToTableModel(rs3));

            } catch (SQLException e) {
                System.out.println(e);
            }

        } catch (NumberFormatException | SQLException e) {
            System.out.println("--> SQLException : " + e);
        }
    }

    public void recupBanque() {
        int row = Table3.getSelectedRow();
        Prets.test = (Table3.getModel().getValueAt(row, 0).toString());

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
        jScrollPane2 = new javax.swing.JScrollPane();
        Table3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table4 = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Banque = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Taux = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Effectif = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Montant = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        MontantAPayer = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pieBanque = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        printbtn = new javax.swing.JButton();
        printbtn1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        date1 = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        date2 = new com.toedter.calendar.JDateChooser();
        Rech2date = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Effectif1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TotalAPayer = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Banque1 = new javax.swing.JLabel();

        setBorder(null);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(null);

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
        Table3.getTableHeader().setResizingAllowed(false);
        Table3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table3MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Table3);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(0, 100, 460, 240);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)), "Listes des Prets :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 3, 10))); // NOI18N

        Table4.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        Table4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Table4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(0, 420, 1150, 230);
        getContentPane().add(jLabel21);
        jLabel21.setBounds(1040, 180, 40, 20);

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel3.setText("BANQUE : ");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 70, 70, 14);

        Banque.setText("                       ");
        getContentPane().add(Banque);
        Banque.setBounds(80, 70, 70, 14);

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel5.setText("TAUX : ");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(180, 70, 50, 14);

        Taux.setText("         ");
        getContentPane().add(Taux);
        Taux.setBounds(240, 70, 40, 14);

        jLabel7.setText("%");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(290, 70, 30, 14);

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel1.setText("EFFECTIF : ");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(360, 70, 70, 14);

        Effectif.setText("           ");
        getContentPane().add(Effectif);
        Effectif.setBounds(430, 70, 34, 14);

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel9.setText("MONTANT : ");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(490, 70, 80, 14);

        Montant.setText("          ");
        getContentPane().add(Montant);
        Montant.setBounds(570, 70, 140, 14);

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel11.setText("MONTANT A PAYER : ");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(730, 70, 130, 14);

        MontantAPayer.setText("        ");
        getContentPane().add(MontantAPayer);
        MontantAPayer.setBounds(860, 70, 160, 14);

        pieBanque.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(pieBanque);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(470, 100, 340, 270);
        getContentPane().add(jPanel2);
        jPanel2.setBounds(820, 100, 330, 270);

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        jLabel10.setFont(new java.awt.Font("Advent Pro", 0, 40)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("SUIVI DES EFFECTIFS");

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

        printbtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        printbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/printer.png"))); // NOI18N
        printbtn.setText("Imprimer les Diagrammes");
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
        printbtn.setBounds(940, 390, 210, 30);

        printbtn1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        printbtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/printer.png"))); // NOI18N
        printbtn1.setText("Imprimer Effectif");
        printbtn1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        printbtn1.setContentAreaFilled(false);
        printbtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printbtn1.setOpaque(true);
        printbtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                printbtn1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                printbtn1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                printbtn1MousePressed(evt);
            }
        });
        printbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtn1ActionPerformed(evt);
            }
        });
        getContentPane().add(printbtn1);
        printbtn1.setBounds(770, 390, 160, 30);

        jLabel8.setText("Recherche entre :");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(0, 360, 90, 20);

        date1.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(date1);
        date1.setBounds(90, 360, 120, 20);

        jLabel12.setText("et");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(220, 360, 30, 20);

        date2.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(date2);
        date2.setBounds(240, 360, 120, 20);

        Rech2date.setText("Recherche");
        Rech2date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rech2dateActionPerformed(evt);
            }
        });
        getContentPane().add(Rech2date);
        Rech2date.setBounds(370, 360, 90, 23);

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel2.setText("EFFECTIF : ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(180, 390, 70, 14);

        Effectif1.setText("           ");
        getContentPane().add(Effectif1);
        Effectif1.setBounds(250, 390, 34, 14);

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel13.setText("TOTAL A PAYER : ");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(340, 390, 130, 14);

        TotalAPayer.setText("        ");
        getContentPane().add(TotalAPayer);
        TotalAPayer.setBounds(470, 390, 160, 14);

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 11)); // NOI18N
        jLabel4.setText("BANQUE : ");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 390, 70, 14);

        Banque1.setText("                       ");
        getContentPane().add(Banque1);
        Banque1.setBounds(90, 390, 70, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Table3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MouseReleased
        try {
            int row = Table3.getSelectedRow();
            this.test = (Table3.getModel().getValueAt(row, 0).toString());

            String requete6 = "select idPret as 'IdPret' ,cincand as 'Cin_Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where NumBanque='" + test + "'";
            ps = conn.prepareStatement(requete6);
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));

            String requete7 = "select NumBanque as 'Numero Banque', NomBanque as 'Banque' , Taux as 'Taux' ,Effectif as 'Effectif',TotalPret as 'Total Pret' ,TotalAPayer as 'Total à Payer' from  effectif where NumBanque='" + test + "'";
            ps1 = conn1.prepareStatement(requete7);
            rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String t1 = rs1.getString("Banque");
                Banque.setText(t1);
                Banque1.setText(t1);
                String t2 = rs1.getString("Taux");
                Taux.setText(t2);
                String t3 = rs1.getString("Effectif");
                Effectif.setText(t3);
                Effectif1.setText(t3);
                String t4 = rs1.getString("Total Pret");
                Montant.setText(t4);
                String t5 = rs1.getString("Total à Payer");
                MontantAPayer.setText(t5);
                TotalAPayer.setText(t5);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println(test);

        //refreshPret();
    }//GEN-LAST:event_Table3MouseReleased

    private void Table4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table4MouseReleased

    }//GEN-LAST:event_Table4MouseReleased

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void Table4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Table4MouseClicked

    private void printbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtnMouseEntered
        printbtn.setBackground(new java.awt.Color(0, 153, 153));
    }//GEN-LAST:event_printbtnMouseEntered

    private void printbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtnMouseExited
        printbtn.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_printbtnMouseExited

    private void printbtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtnMousePressed
        printbtn.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_printbtnMousePressed

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed
        chartPanel.createChartPrintJob();
        chartPanel2.createChartPrintJob();
    }//GEN-LAST:event_printbtnActionPerformed

    private void printbtn1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtn1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_printbtn1MouseEntered

    private void printbtn1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtn1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_printbtn1MouseExited

    private void printbtn1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printbtn1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_printbtn1MousePressed

    private void printbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtn1ActionPerformed

        MessageFormat header = new MessageFormat("Effectifs :");
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
        try {
            Table3.print(JTable.PrintMode.NORMAL, header, footer);
        } catch (java.awt.print.PrinterException e) {
            System.err.format("Erreur d'impression ", e.getMessage());
        }
    }//GEN-LAST:event_printbtn1ActionPerformed

    private void Rech2dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rech2dateActionPerformed
        try {
            String banque = Banque.getText();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateAvant = dateFormat.format(date1.getDate());
            String dateApret = dateFormat.format(date2.getDate());
            
            String requete6 = "select idPret as 'IdPret' ,cincand as 'Cin_Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where NumBanque='" + test + "' and datePret between '" + dateAvant + "' and '" + dateApret + "' order by datePret desc";
            System.out.println(requete6);
            ps = conn.prepareStatement(requete6);
            rs = ps.executeQuery();
            Table4.setModel(DbUtils.resultSetToTableModel(rs));
            
            String requete7 = "select idPret as 'IdPret' ,cincand as 'Cin_Client' ,NumBanque as 'Numero Banque',Montant as 'Montant' ,MontantAPayer as 'Montant à Payer' ,datePret as 'Date du Pret' from  pret where NumBanque='" + test + "' and datePret between '" + dateAvant + "' and '" + dateApret + "' order by datePret desc";
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
            Banque1.setText(banque);
            Banque.setText("");
            Taux.setText("");
            Effectif.setText("");
            Montant.setText("");
            MontantAPayer.setText("");
            

        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_Rech2dateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Banque;
    private javax.swing.JLabel Banque1;
    private javax.swing.JLabel Effectif;
    private javax.swing.JLabel Effectif1;
    private javax.swing.JLabel Montant;
    private javax.swing.JLabel MontantAPayer;
    private javax.swing.JButton Rech2date;
    private javax.swing.JTable Table3;
    private javax.swing.JTable Table4;
    private javax.swing.JLabel Taux;
    private javax.swing.JLabel TotalAPayer;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JDesktopPane pieBanque;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton printbtn1;
    // End of variables declaration//GEN-END:variables


}
