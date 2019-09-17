/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GPB.frame;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFrame;

/**
 *
 * @author anouer
 */
public class AcceuilGui extends javax.swing.JFrame {

    public static String t3;
    private static ChartFrame frame;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    /**
     * Creates new form AcceuilGui
     */
    public AcceuilGui() {
        initComponents();
        datecourante();
        //this.setIconImage(new ImageIcon(getClass().getResource("logocar.png")).getImage());
        conn = ConexionBD.Conexion();
        recuperutlisateur();

    }

    private void datecourante() {
        Thread clock = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    Calendar Cal = new GregorianCalendar();

                    int sconde = Cal.get(Calendar.SECOND);
                    int minute = Cal.get(Calendar.MINUTE);
                    int heure = Cal.get(Calendar.HOUR);
                    int AM_PM = Cal.get(Calendar.AM_PM);
                    String pa;
                    if (AM_PM == 1) {
                        pa = "PM";
                    } else {
                        pa = "AM";
                    }

                    labDate.setText(+heure + ":" + (minute) + ":" + sconde + " " + pa);

                    int mois = Cal.get(Calendar.MONTH);
                    int annee = Cal.get(Calendar.YEAR);
                    int jour = Cal.get(Calendar.DAY_OF_MONTH);

                    labdate2.setText(+jour + "/" + (mois + 1) + "/" + annee);

                    try {
                        sleep(1000);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        };

        clock.start();
    }

    private void recuperutlisateur() {
        LoginGUI lo = new LoginGUI();

        try {
            String recp = lo.utilisateur();
            String requet = "select * from login_table where login = '" + recp + "'";
            ps = conn.prepareStatement(requet);
            rs = ps.executeQuery();
            if (rs.next()) {
                String t2 = rs.getString("prenom");
//                lprenom.setText(t2);
                String t1 = rs.getString("nom");
                lnom.setText(t1 + " " + t2);
                ps.close();
                rs.close();
            }
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

    public String utilisateurs() {
        return t3;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labDate = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labdate2 = new javax.swing.JLabel();
        logomax = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        menublanc = new javax.swing.JLabel();
        desk = new javax.swing.JDesktopPane();
        type = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lnom = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        pretbtn = new javax.swing.JButton();
        banquebtn = new javax.swing.JButton();
        utlisateurbtn = new javax.swing.JButton();
        Clientbtn = new javax.swing.JButton();
        Effectifbtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 380));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        labDate.setFont(new java.awt.Font("Alpha Echo", 1, 24)); // NOI18N
        labDate.setForeground(new java.awt.Color(255, 255, 255));
        labDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labDate.setText("10:15:30 PM");
        getContentPane().add(labDate);
        labDate.setBounds(570, 30, 180, 30);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/info.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(1170, 30, 34, 30);

        labdate2.setFont(new java.awt.Font("Aaargh", 0, 14)); // NOI18N
        labdate2.setForeground(new java.awt.Color(255, 255, 255));
        labdate2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labdate2.setText("12/02/2016");
        getContentPane().add(labdate2);
        labdate2.setBounds(730, 30, 140, 30);

        logomax.setFont(new java.awt.Font("Armalite Rifle", 1, 24)); // NOI18N
        logomax.setForeground(new java.awt.Color(0, 153, 153));
        logomax.setText("Gestion des Prets Bancaires");
        getContentPane().add(logomax);
        logomax.setBounds(10, 0, 610, 30);

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Projet L3 IG 2018-2019");
        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 70, 140, 10);

        menublanc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/menu.png"))); // NOI18N
        getContentPane().add(menublanc);
        menublanc.setBounds(0, 0, 1370, 30);

        desk.setBackground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout deskLayout = new javax.swing.GroupLayout(desk);
        desk.setLayout(deskLayout);
        deskLayout.setHorizontalGroup(
            deskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1160, Short.MAX_VALUE)
        );
        deskLayout.setVerticalGroup(
            deskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        getContentPane().add(desk);
        desk.setBounds(190, 70, 1160, 640);

        type.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        type.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(type);
        type.setBounds(520, 70, 140, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText(":");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(660, 70, 10, 17);

        lnom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lnom.setForeground(new java.awt.Color(0, 0, 204));
        getContentPane().add(lnom);
        lnom.setBounds(670, 70, 320, 20);

        jDesktopPane1.setBackground(new java.awt.Color(240, 240, 240));

        pretbtn.setBackground(new java.awt.Color(153, 153, 153));
        pretbtn.setFont(new java.awt.Font("Bebas Neue Bold", 1, 18)); // NOI18N
        pretbtn.setForeground(new java.awt.Color(255, 255, 255));
        pretbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/icon.png"))); // NOI18N
        pretbtn.setText("Prets");
        pretbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 1, new java.awt.Color(0, 153, 153)));
        pretbtn.setContentAreaFilled(false);
        pretbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pretbtn.setOpaque(true);
        pretbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pretbtnMouseReleased(evt);
            }
        });
        pretbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pretbtnActionPerformed(evt);
            }
        });

        banquebtn.setBackground(new java.awt.Color(153, 153, 153));
        banquebtn.setFont(new java.awt.Font("Bebas Neue Bold", 0, 18)); // NOI18N
        banquebtn.setForeground(new java.awt.Color(255, 255, 255));
        banquebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/black-user-shape.png"))); // NOI18N
        banquebtn.setText("Banques");
        banquebtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 1, new java.awt.Color(0, 153, 153)));
        banquebtn.setContentAreaFilled(false);
        banquebtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        banquebtn.setOpaque(true);
        banquebtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                banquebtnMouseReleased(evt);
            }
        });
        banquebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banquebtnActionPerformed(evt);
            }
        });

        utlisateurbtn.setBackground(new java.awt.Color(153, 153, 153));
        utlisateurbtn.setFont(new java.awt.Font("Bebas Neue Bold", 1, 18)); // NOI18N
        utlisateurbtn.setForeground(new java.awt.Color(255, 255, 255));
        utlisateurbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/user.png"))); // NOI18N
        utlisateurbtn.setText("Utilisateurs");
        utlisateurbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 1, new java.awt.Color(0, 153, 153)));
        utlisateurbtn.setContentAreaFilled(false);
        utlisateurbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        utlisateurbtn.setOpaque(true);
        utlisateurbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                utlisateurbtnMouseReleased(evt);
            }
        });
        utlisateurbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utlisateurbtnActionPerformed(evt);
            }
        });

        Clientbtn.setBackground(new java.awt.Color(153, 153, 153));
        Clientbtn.setFont(new java.awt.Font("Bebas Neue Bold", 0, 18)); // NOI18N
        Clientbtn.setForeground(new java.awt.Color(255, 255, 255));
        Clientbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/multiple-users-silhouette.png"))); // NOI18N
        Clientbtn.setText("ClientS");
        Clientbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 1, new java.awt.Color(0, 153, 153)));
        Clientbtn.setContentAreaFilled(false);
        Clientbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Clientbtn.setOpaque(true);
        Clientbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ClientbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ClientbtnMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ClientbtnMouseReleased(evt);
            }
        });
        Clientbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientbtnActionPerformed(evt);
            }
        });

        Effectifbtn.setBackground(new java.awt.Color(153, 153, 153));
        Effectifbtn.setFont(new java.awt.Font("Bebas Neue Bold", 1, 18)); // NOI18N
        Effectifbtn.setForeground(new java.awt.Color(255, 255, 255));
        Effectifbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/GPB/images/diagram.png"))); // NOI18N
        Effectifbtn.setText("Effectifs");
        Effectifbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 1, new java.awt.Color(0, 153, 153)));
        Effectifbtn.setContentAreaFilled(false);
        Effectifbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Effectifbtn.setMinimumSize(new java.awt.Dimension(69, 23));
        Effectifbtn.setOpaque(true);
        Effectifbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                EffectifbtnMouseReleased(evt);
            }
        });
        Effectifbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EffectifbtnActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(pretbtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(banquebtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(utlisateurbtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Clientbtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Effectifbtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(banquebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Effectifbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pretbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(utlisateurbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Clientbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Clientbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(banquebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pretbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Effectifbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(utlisateurbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jDesktopPane1);
        jDesktopPane1.setBounds(10, 130, 160, 270);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1370, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 30, 1370, 30);

        setSize(new java.awt.Dimension(1382, 759));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ClientbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientbtnActionPerformed
        this.desk.removeAll();
        this.desk.repaint();

        Liste_des_Clients lc = null;
        try {
            lc = new Liste_des_Clients();
        } catch (SQLException ex) {
            Logger.getLogger(AcceuilGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.desk.add(lc);
        try {
            lc.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(AcceuilGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        lc.show();
    }//GEN-LAST:event_ClientbtnActionPerformed

    private void EffectifbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EffectifbtnActionPerformed
        this.desk.removeAll();
        this.desk.repaint();

        Effectif fr = new Effectif();
        this.desk.add(fr);
        try {
            fr.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(AcceuilGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        fr.show();
    }//GEN-LAST:event_EffectifbtnActionPerformed

    private void pretbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pretbtnActionPerformed
        this.desk.removeAll();
        this.desk.repaint();

        Prets ex = new Prets();
        this.desk.add(ex);
        try {
            ex.setMaximum(true);
        } catch (PropertyVetoException ex1) {
            Logger.getLogger(AcceuilGui.class.getName()).log(Level.SEVERE, null, ex1);
        }
        ex.show();
    }//GEN-LAST:event_pretbtnActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void banquebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banquebtnActionPerformed
        this.desk.removeAll();
        this.desk.repaint();

        Banque ex = null;
        try {
            ex = new Banque();
        } catch (SQLException ex1) {
            Logger.getLogger(AcceuilGui.class.getName()).log(Level.SEVERE, null, ex1);
        }
        this.desk.add(ex);
        try {
            ex.setMaximum(true);
        } catch (PropertyVetoException ex1) {
            Logger.getLogger(AcceuilGui.class.getName()).log(Level.SEVERE, null, ex1);
        }
        ex.show();
    }//GEN-LAST:event_banquebtnActionPerformed

    private void utlisateurbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utlisateurbtnActionPerformed
        this.desk.removeAll();
        this.desk.repaint();

        Utilisateur ex = new Utilisateur();
        this.desk.add(ex);
        try {
            ex.setMaximum(true);
        } catch (PropertyVetoException ex1) {
            Logger.getLogger(AcceuilGui.class.getName()).log(Level.SEVERE, null, ex1);
        }
        ex.show();
    }//GEN-LAST:event_utlisateurbtnActionPerformed

    private void ClientbtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientbtnMouseReleased
        Clientbtn.setBackground(new java.awt.Color(0, 153, 153));
        Effectifbtn.setBackground(new java.awt.Color(153, 153, 153));
        banquebtn.setBackground(new java.awt.Color(153, 153, 153));
        pretbtn.setBackground(new java.awt.Color(153, 153, 153));
        utlisateurbtn.setBackground(new java.awt.Color(153, 153, 153));
    }//GEN-LAST:event_ClientbtnMouseReleased

    private void EffectifbtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EffectifbtnMouseReleased
        Clientbtn.setBackground(new java.awt.Color(153, 153, 153));
        Effectifbtn.setBackground(new java.awt.Color(0, 153, 153));
        banquebtn.setBackground(new java.awt.Color(153, 153, 153));
        pretbtn.setBackground(new java.awt.Color(153, 153, 153));
        utlisateurbtn.setBackground(new java.awt.Color(153, 153, 153));
    }//GEN-LAST:event_EffectifbtnMouseReleased

    private void ClientbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientbtnMouseEntered
//     candidatbtn.setBackground(new java.awt.Color(51,2,255));
    }//GEN-LAST:event_ClientbtnMouseEntered

    private void ClientbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientbtnMouseExited
//        candidatbtn.setBackground(new java.awt.Color(204,204,204));
    }//GEN-LAST:event_ClientbtnMouseExited

    private void banquebtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banquebtnMouseReleased
        banquebtn.setBackground(new java.awt.Color(0, 153, 153));
        Clientbtn.setBackground(new java.awt.Color(153, 153, 153));
        Effectifbtn.setBackground(new java.awt.Color(153, 153, 153));
        pretbtn.setBackground(new java.awt.Color(153, 153, 153));
        utlisateurbtn.setBackground(new java.awt.Color(153, 153, 153));
    }//GEN-LAST:event_banquebtnMouseReleased

    private void pretbtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pretbtnMouseReleased
        banquebtn.setBackground(new java.awt.Color(153, 153, 153));
        Clientbtn.setBackground(new java.awt.Color(153, 153, 153));
        Effectifbtn.setBackground(new java.awt.Color(153, 153, 153));
        pretbtn.setBackground(new java.awt.Color(0, 153, 153));
        utlisateurbtn.setBackground(new java.awt.Color(153, 153, 153));
    }//GEN-LAST:event_pretbtnMouseReleased

    private void utlisateurbtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_utlisateurbtnMouseReleased
        banquebtn.setBackground(new java.awt.Color(153, 153, 153));
        Clientbtn.setBackground(new java.awt.Color(153, 153, 153));
        Effectifbtn.setBackground(new java.awt.Color(153, 153, 153));
        pretbtn.setBackground(new java.awt.Color(153, 153, 153));
        utlisateurbtn.setBackground(new java.awt.Color(0, 153, 153));
    }//GEN-LAST:event_utlisateurbtnMouseReleased

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        String v = "<html><head></head><body><h3 color ='#2C4CCC'><center>"
                + "<br><p><b><i>GESTION DES PRETS BANCAIRES</i></b>"
                + "<br><p><b><i>Logiciel developpé par Team4</i></b><br></p><br>"
                + "<br><p ><b><i>Tous droit reservé</i></b></p><br>"
                + "<br><p >2019</i></b></p><br>"
                + "<br><p >---------<b><i>" + "</i></b></p><br></center></h3></p></body></html>";
        JOptionPane p = new JOptionPane();
        p.setMessage(v);
        p.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog d = p.createDialog(null, "Information sur le logiciel ");
        d.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AcceuilGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AcceuilGui().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Clientbtn;
    private javax.swing.JButton Effectifbtn;
    private javax.swing.JButton banquebtn;
    private javax.swing.JDesktopPane desk;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labDate;
    private javax.swing.JLabel labdate2;
    private javax.swing.JLabel lnom;
    private javax.swing.JLabel logomax;
    private javax.swing.JLabel menublanc;
    private javax.swing.JButton pretbtn;
    private javax.swing.JLabel type;
    private javax.swing.JButton utlisateurbtn;
    // End of variables declaration//GEN-END:variables
}
