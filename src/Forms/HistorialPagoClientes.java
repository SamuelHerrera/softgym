/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Utilidades.usadb;
import Utilidades.VerticalLabelUI;
import Utilidades.SoftGym;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ErickFrancisco
 */
public class HistorialPagoClientes extends javax.swing.JInternalFrame {

    /**
     * Creates new form HistorialPagoClientes
     */
    public HistorialPagoClientes() {
        initComponents();
        actualizartabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setTitle("Historial de pago de cliente");

        jPanel1.setBackground(SoftGym.fondo);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Motivo", "Descripcion", "Cantidad", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void actualizartabla() {
        usadb db = new usadb();
        Object[][] pagoclientes = db.get_historialPagoClientes(SoftGym.prin.IDCliente, "idcliente");
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

        while (jTable1.getRowCount() > 0) {
            dtm.removeRow(0);
        }

        for (int jj = 0; pagoclientes != null && jj < pagoclientes.length; jj++) {
            Object[][] ingresos = db.get_ingresos(pagoclientes[jj][1], "idIngresos");

            System.out.println("entro a for en actualizartable()" + pagoclientes[jj][1]);

            System.out.println("entro a for en actualizartable()");
            dtm.addRow(new Object[]{
                ingresos[0][2],
                ingresos[0][3],
                ingresos[0][4],
                ((Date) ingresos[0][1]).toLocaleString().substring(0, 11)}
            );

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
