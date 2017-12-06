/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

/**
 *
 * @author Erick
 */
import Utilidades.usadb;
import Utilidades.SoftGym;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;

public class PanelEgresos extends javax.swing.JPanel {

    double sueldos, pagosServicios, otros1;
    public double totalEgreso;
    usadb db = new usadb();

    public PanelEgresos() {
        initComponents();
        mostrargrafica();
        mostrar_tablas();
    }

    public void mostrargrafica() {
        JFreeChart Grafica;
        DefaultPieDataset datos = new DefaultPieDataset();

        Object[][] dats = db.getAll_Egresos();
        for (int i = 0; dats != null && i < dats.length; i++) {
            if (dats[i][2].equals("Sueldos") && dats[i][5].equals(SoftGym.nombre)) {
                //dtm.addRow(new Object[]{datos[i][1],datos[i][2], datos[i][3], datos[i][4], datos[i][5]});
                sueldos = sueldos + (double) dats[i][4];

            }
            if (dats[i][2].equals("Pagos de servicios") && dats[i][5].equals(SoftGym.nombre)) {
                //dtm.addRow(new Object[]{datos[i][1],datos[i][2], datos[i][3], datos[i][4], datos[i][5]});
                pagosServicios = pagosServicios + (double) dats[i][4];

            }
            if (dats[i][2].equals("Otros") && dats[i][5].equals(SoftGym.nombre)) {

                otros1 = otros1 + (double) dats[i][4];

            }

        }
        totalEgreso = sueldos + pagosServicios + otros1;
        datos.setValue("Sueldos", sueldos);
        datos.setValue("Pago de servicios", pagosServicios);
        datos.setValue("otros", otros1);
        Grafica = ChartFactory.createPieChart("Grafica Gastos", datos, true, true, true);
        ChartPanel p = new ChartPanel(Grafica);
        jPanel1.add(p, "grafica");
        CardLayout card = (CardLayout) jPanel1.getLayout();
        card.show(jPanel1, "grafica");
        jTextField1.setText("$" + totalEgreso);
    }

    public void mostrar_tablas() {
        usadb db = new usadb();
        Object[][] datos = db.getAll_Egresos();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        while (jTable1.getRowCount() > 0) {
            dtm.removeRow(0);
        }

        for (int i = 0; datos != null && i < datos.length; i++) {
            if (datos[i][5].equals(SoftGym.nombre)) {
                dtm.addRow(new Object[]{datos[i][1], datos[i][2], datos[i][3], datos[i][4], datos[i][5]});

            }
        }
    }

    public void mostrar_tablasFecha() {
        usadb db = new usadb();
        if (jDateChooser1.getDate() != null || jDateChooser2.getDate() != null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar las fechas primero.");
            return;
        }
        Object[][] fechas = db.get_egresos(new Object[]{jDateChooser1.getDate(), jDateChooser2.getDate()}, "fecha");
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        while (jTable1.getRowCount() > 0) {
            dtm.removeRow(0);
        }
        if (jComboBox1.getSelectedItem().toString().equals("---Todo---") && fechas != null) {

            for (int i = 0; i < fechas.length; i++) {
                if (fechas[i][5].equals(SoftGym.nombre)) {
                    dtm.addRow(new Object[]{fechas[i][1], fechas[i][2], fechas[i][3], fechas[i][4], fechas[i][5]});

                }
            }
        }

        if (jComboBox1.getSelectedItem().toString().equals("Sueldos") && fechas != null) {
            Object[][] sueld = db.get_egresos("Sueldos", "motivo");
            for (int i = 0; i < fechas.length; i++) {
                if (fechas[i][5].equals(SoftGym.nombre)) {
                    dtm.addRow(new Object[]{sueld[i][1], sueld[i][2], sueld[i][3], sueld[i][4], sueld[i][5]});
                }

            }
        }

        if (jComboBox1.getSelectedItem().toString().equals("Pagos de servicios") && fechas != null) {
            Object[][] pds = db.get_egresos("Pagos de servicios", "motivo");
            for (int i = 0; i < fechas.length; i++) {
                if (pds[i][5].equals(SoftGym.nombre)) {
                    dtm.addRow(new Object[]{pds[i][1], pds[i][2], pds[i][3], pds[i][4], pds[i][5]});

                }
            }
        }

        if (jComboBox1.getSelectedItem().toString().equals("Otros") && fechas != null) {
            Object[][] otr = db.get_egresos("otros", "motivo");
            for (int i = 0; i < fechas.length; i++) {
                if (otr[i][5].equals(SoftGym.nombre)) {
                    dtm.addRow(new Object[]{otr[i][1], otr[i][2], otr[i][3], otr[i][4], otr[i][5]});

                }
            }
        }

        if (jComboBox1.getSelectedItem().toString().equals("Usuario") && fechas != null) {
            Object[][] otr = db.get_egresos(SoftGym.nombre, "usuario");
            for (int i = 0; i < fechas.length; i++) {
                if (otr[i][5].equals(SoftGym.nombre)) {
                    dtm.addRow(new Object[]{otr[i][1], otr[i][2], otr[i][3], otr[i][4], otr[i][5]});

                }
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setBackground(SoftGym.fondo);
        setName("EGRESOS"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Motivo", "Descripción", "Cantidad", "usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setText("De:");

        jLabel3.setText("Hasta:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---Todo---", "Sueldos", "Pagos de servicios", "Otros", "Usuario" }));

        jButton1.setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(">>>");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.setEnabled(false);

        jLabel1.setText("Total:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Grafica"));
        jPanel1.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        mostrar_tablasFecha();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jDateChooser2.setDate(jDateChooser1.getDate());
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
