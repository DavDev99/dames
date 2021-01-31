/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dames;

import damas.util.HibernateUtil;
import static dames.DamasMenu.partida;
import dames.entity.Moviments;
import dames.entity.Partides;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import org.hibernate.Session;

/**
 *
 * @author david
 */
public class DamasPartida extends javax.swing.JFrame {

    boolean jugaX = true;
    boolean jugaO = false;
    int filaOrigen = -1;
    int columnaOrigen = -1;

    /**
     * Creates new form DamasMenu
     */
    public DamasPartida() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton3.setText("Sortir");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"X", "", "X", "", "X", "", "X", ""},
                {"", "X", "", "X", "", "X", "", "X"},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"O", "", "O", "", "O", "", "O", ""},
                {"", "O", "", "O", "", "O", "", "O"}
            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(54, 54, 54)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        int fila = obtenirFilaClicada();
        int columna = obtenirColumnaClicada();

        if (noHiHaOrigen()) {
            if (jugaX && EsX(fila, columna)) {
                actualitzaNouOrigen(fila, columna);
            } else if (jugaO && EsO(fila, columna)) {
                actualitzaNouOrigen(fila, columna);
            } else {
                mostraError();
            }
        } else {
            if (movimentVàlid(fila, columna)) {
                if (esBuit(fila, columna) || ocupatContrari(fila, columna)) {
                    mou(fila, columna);

                    if (jugaX) {
                        jugaX = false;
                    } else {
                        jugaX = true;
                    }
                    jugaO = !jugaX;

                    filaOrigen = -1;
                    columnaOrigen = -1;
                }
                //si diagonal cap avall per X o cap a dalt per O
            } else if (ocupatPropi(fila, columna)) {
                actualitzaNouOrigen(fila, columna);
            } else {
                mostraErrorMoviment();
            }
        }

        if (comprobarGuanyador()) {
            if (jugaX) {
                JOptionPane.showMessageDialog(this,
                        "El jugador O ha guanyat!",
                        "Felicitats",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "El jugador X ha guanyat!",
                        "Felicitats",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        System.exit(WIDTH);
    }//GEN-LAST:event_jButton3MouseClicked

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
            java.util.logging.Logger.getLogger(DamasPartida.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DamasPartida.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DamasPartida.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DamasPartida.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DamasPartida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private int obtenirFilaClicada() {
        return jTable1.getSelectedRow();
    }

    private int obtenirColumnaClicada() {
        return jTable1.getSelectedColumn();
    }

    private boolean noHiHaOrigen() {

        return filaOrigen == -1 && columnaOrigen == -1;
    }

    private boolean EsX(int fila, int columna) {

        String value = (String) jTable1.getValueAt(fila, columna);

        return value.equals("X");
    }

    private void actualitzaNouOrigen(int fila, int columna) {
        filaOrigen = fila;
        columnaOrigen = columna;
    }

    private boolean EsO(int fila, int columna) {

        String value = (String) jTable1.getValueAt(fila, columna);

        return value.equals("O");
    }

    private void mostraError() {
        JOptionPane.showMessageDialog(this,
                "Has de seleccionar una fila diferent o valida.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
    }

    private boolean movimentVàlid(int fila, int columna) {
        if (jugaX && filaOrigen + 1 == fila && (columnaOrigen + 1 == columna || columnaOrigen - 1 == columna)) {
            return true;
        } else if (jugaO && filaOrigen - 1 == fila && (columnaOrigen + 1 == columna || columnaOrigen - 1 == columna)) {
            return true;
        }

        return false;
    }

    private boolean esBuit(int fila, int columna) {
        String value = (String) jTable1.getValueAt(fila, columna);

        return value.equals("");
    }

    private void mou(int fila, int columna) {
        jTable1.setValueAt("", filaOrigen, columnaOrigen);

        if (jugaO) {
            jTable1.setValueAt("O", fila, columna);
        } else {
            jTable1.setValueAt("X", fila, columna);
        }
        
        runQueryBasedOnCreateMoviment(fila, columna);
    }

    private boolean ocupatContrari(int fila, int columna) {
        String value = (String) jTable1.getValueAt(fila, columna);

        if (jugaO && value.equals("X")) {
            return true;
        } else if (jugaX && value.equals("O")) {
            return true;
        }
        return false;
    }

    private void mostraErrorMoviment() {
        JOptionPane.showMessageDialog(this,
                "Has de seleccionar una cela valida per a fer el moviment.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
    }

    private boolean ocupatPropi(int fila, int columna) {
        String value = (String) jTable1.getValueAt(fila, columna);

        if (jugaO && value.equals("O")) {
            return true;
        } else if (jugaX && value.equals("X")) {
            return true;
        }

        return false;
    }

    private boolean comprobarGuanyador() {
        TableModel tablero = jTable1.getModel();

        int countX = 0;
        int countO = 0;

        for (int i = 0; i < tablero.getRowCount(); i++) {
            for (int j = 0; j < tablero.getColumnCount(); j++) {

                if (EsO(i, j)) {
                    countO++;
                } else if (EsX(i, j)) {
                    countX++;
                }

                if (jugaO && i == tablero.getRowCount() - 1 && EsX(i, j)) {
                    runQueryBasedOnUpdatePartidaSetWinner("X");
                    return true;
                } else if (jugaX && i == 0 && EsO(i, j)) {
                    runQueryBasedOnUpdatePartidaSetWinner("O");
                    return true;
                }
            }
        }

        if (countO == 0) {
            runQueryBasedOnUpdatePartidaSetWinner("X");
            return true;
        } else if (countX == 0) {
            runQueryBasedOnUpdatePartidaSetWinner("O");
            return true;
        }
        return false;
    }

    private void runQueryBasedOnCreateMoviment(int fila, int columna) {


        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //Add new Employee object
        Moviments moviment = new Moviments();
        moviment.setPartides(DamasMenu.partida);
        moviment.setFila(fila);
        moviment.setColumna(columna);
        moviment.setFilaOrigen(filaOrigen);
        moviment.setColumnaOrigen(columnaOrigen);


        //Save the employee in database
        session.save(moviment);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }
    
        private void runQueryBasedOnUpdatePartidaSetWinner(String guanyador) {


        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //Add new Employee object

        DamasMenu.partida.setGuanyador(guanyador);



        //Save the employee in database
        session.save(DamasMenu.partida);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();
    }
}