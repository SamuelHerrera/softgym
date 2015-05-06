/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softgym;

/**
 *
 * @author Erick
 */
import Utilidades.usadb;
import Utilidades.SoftGym;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;

public class PanelIngresos1 extends javax.swing.JPanel {
    
    double inscripcion, mensualidad, otros;
    public  double totalIngreso;
    usadb db= new usadb();
    
    public PanelIngresos1() {
        
        initComponents();
        mostrargrafica();
        mostrar_tablas();
        System.out.println(""+otros);
    }
    
    public void mostrargrafica(){
        JFreeChart Grafica;
        Object[][] dats = db.getAll_Ingresos();
        DefaultPieDataset datos= new DefaultPieDataset();
        for(int i=0;dats!=null&& i<dats.length;i++){
            if(dats[i][2].equals("Inscripcion")&&dats[i][5].equals(SoftGym.nombre)){
            //dtm.addRow(new Object[]{datos[i][1],datos[i][2], datos[i][3], datos[i][4], datos[i][5]});
                inscripcion=inscripcion+(double)dats[i][4];
                
                
            }
            if(dats[i][2].equals("Mensualidad")&&dats[i][5].equals(SoftGym.nombre)){
            //dtm.addRow(new Object[]{datos[i][1],datos[i][2], datos[i][3], datos[i][4], datos[i][5]});
            mensualidad=mensualidad+(double)dats[i][4];
            
            }
            if(dats[i][2].equals("Otros")&&dats[i][5].equals(SoftGym.nombre)){
            
                otros=otros+(double)dats[i][4];
                
            }
            
        }
        totalIngreso=inscripcion+mensualidad+otros;
        datos.setValue("Inscripcion", inscripcion);
        datos.setValue("Mensualidad",mensualidad);
        datos.setValue("Otros",otros);
        Grafica=ChartFactory.createPieChart("Grafica Ingresos", datos, true, true, true);
        ChartPanel p=new ChartPanel(Grafica);
        jPanel1.add(p,"grafica");
        CardLayout card=(CardLayout)jPanel1.getLayout();
        card.show(jPanel1, "grafica");
        jTextField1.setText("$"+totalIngreso);
    }  
    
   public void obtener_inscripcion(){
        //Object[][] datos = db.getAll_Ingresos();
        DefaultTableModel dtm=(DefaultTableModel) jTable1.getModel();
        while(jTable1.getRowCount()>0){
            dtm.removeRow(0);
        }
        
       }
    
            
    public void mostrar_datos(){
        Object[][] datos = db.getAll_Ingresos();
        DefaultTableModel dtm=(DefaultTableModel) jTable1.getModel();
        while(jTable1.getRowCount()>0){
            dtm.removeRow(0);
        }
        for(int i=0; i<datos.length;i++){
            if(datos[i][5].equals(SoftGym.nombre)){
                dtm.addRow(new Object[]{datos[i][1],datos[i][2], datos[i][3], datos[i][4], datos[i][5]});
            }
            
        }
    }
    
    
    public void mostrar_tablas(){
        usadb db=new usadb();
        Object[][] datos = db.getAll_Ingresos();
        DefaultTableModel dtm=(DefaultTableModel) jTable1.getModel();
        while(jTable1.getRowCount()>0){
            dtm.removeRow(0);
        }
        for(int i=0;datos!=null  &&  i<datos.length;i++){
            
            if(datos[i][5].equals(SoftGym.nombre)){
                dtm.addRow(new Object[]{datos[i][1],datos[i][2], datos[i][3], datos[i][4], datos[i][5]});
            }
        }
    }
    
    public void mostrar_tablasFecha(){
        usadb db=new usadb();
        Object[][] fechas = db.get_ingresos(new Object[]{jDateChooser1.getDate(),jDateChooser2.getDate()},"fecha");
        
        DefaultTableModel dtm=(DefaultTableModel) jTable1.getModel();
        
        while(jTable1.getRowCount()>0){
            dtm.removeRow(0);
        }
        if(jComboBox1.getSelectedItem().toString().equals("---Todo---")&&fechas!=null){
            fechas = db.get_ingresos(new Object[]{jDateChooser1.getDate(),jDateChooser2.getDate()},"fecha");
            for(int i=0; i<fechas.length;i++){
                if(fechas[i][5].equals(SoftGym.nombre)){
                dtm.addRow(new Object[]{fechas[i][1],fechas[i][2], fechas[i][3], fechas[i][4], fechas[i][5]});
            }
            }
        }
        
        if(jComboBox1.getSelectedItem().equals("Inscripcion")&&fechas!=null){
         Object[][] inscripcion=db.get_ingresos("Inscripcion", "motivo");
        for(int i=0; i<inscripcion.length;i++){
            //System.out.println("Fechas: "+fechas.length);
            if(inscripcion[i][5].equals(SoftGym.nombre)){
                dtm.addRow(new Object[]{inscripcion[i][1],inscripcion[i][2], inscripcion[i][3], inscripcion[i][4], inscripcion[i][5]});
                }
            }
        }
        
        if(jComboBox1.getSelectedItem().equals("Mensualidad")&&fechas!=null){
            Object[][] mens=db.get_ingresos("Mensualidad", "motivo");
        for(int i=0; i<mens.length;i++){
            if(mens[i][5].equals(SoftGym.nombre)){
            dtm.addRow(new Object[]{mens[i][1],mens[i][2], mens[i][3], mens[i][4], mens[i][5]});
            }
            
        }
        }
        
        if(jComboBox1.getSelectedItem().toString().equals("Otros")&&fechas!=null){
            Object[][] otros=db.get_ingresos("otros", "motivo");
        for(int i=0; i<fechas.length;i++){
            if(otros[i][5].equals(SoftGym.nombre)){
            dtm.addRow(new Object[]{otros[i][1],otros[i][2], otros[i][3], otros[i][4], otros[i][5]});
           
            }
        }
        }
        
        if(jComboBox1.getSelectedItem().toString().equals("Usuario")&&fechas!=null){
            Object[][] usuario=db.get_ingresos(SoftGym.nombre, "usuario");
        for(int i=0; i<usuario.length;i++){
            if(usuario[i][5].equals(SoftGym.nombre)){
            dtm.addRow(new Object[]{usuario[i][1],usuario[i][2], usuario[i][3], usuario[i][4], usuario[i][5]});
            
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

        setBackground(new Color(255,255,255,254));
        setName("INGRESOS"); // NOI18N

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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---Todo---", "Inscripcion", "Mensualidad", "Otros", "Usuario" }));

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
