/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Utilidades.usadb;
import Utilidades.Label_Reloj;
import Utilidades.LeerHuella_Login;
import Utilidades.SoftGym;
import Utilidades.LeerHuella_ConfimacionAdmin;
import PuntoVenta.PuntoVenta;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static Utilidades.SoftGym.l;
import static Utilidades.SoftGym.syd0;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Erick
 */
public class Principal extends javax.swing.JFrame {

    Label_Reloj reloj = new Label_Reloj();
    private int x, y;
    public int IDCliente = -1;
    public int IDUsuario = -1;
    private DPFPTemplate template;
    public LeerHuella_ConfimacionAdmin s;
    SoftGym soft = new SoftGym();

    /**
     * Creates new form Principal
     */
    //
    public Principal() {
        Panel_Botones_Principal b = new Panel_Botones_Principal();
        soft.leer();
        initComponents();
        //JOptionPane.showMessageDialog(null,"Abierto");
        jLabel11.setText(SoftGym.nombre);
        jPanel6.add(b, "menu1");
        CardLayout paletas = (CardLayout) jPanel6.getLayout();
        paletas.show(jPanel6, "menu1");

        this.setExtendedState(MAXIMIZED_BOTH);
        repaint();

        final DeferredDocumentListener listener = new DeferredDocumentListener(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usadb db = new usadb();
                db.actualizarNotasCliente(IDCliente, jTextArea2.getText());
            }
        }, true);
        jTextArea2.getDocument().addDocumentListener(listener);
        jTextArea2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                listener.start();
            }

            @Override
            public void focusLost(FocusEvent e) {
                listener.stop();
            }
        });
    }

    private void desplegarDatosCliente() {
        usadb db = new usadb();
        Object client[][] = db.get_cliente(this.IDCliente, "idcliente");
        jTextField1.setText(client[0][1].toString());
        jTextField2.setText(client[0][2].toString());
        jTextField3.setText(client[0][3].toString());
        jTextArea2.setText(client[0][5].toString());

        Object[][] fc = db.get_fechaCorteClientes(this.IDCliente, "idcliente");
        //manejador para los colores de la fecha de corte
        Date hoy = new Date();
        hoy.setSeconds(0);
        hoy.setMinutes(0);
        hoy.setHours(0);
        Date fec = new Date(((Date) fc[0][1]).getTime());
        fec.setSeconds(0);
        fec.setMinutes(0);
        fec.setHours(0);
        Image imagen = db.get_fotocliente(this.IDCliente, "idcliente");
        if (imagen != null) {
            jLabel2.setText("");
            jLabel2.setIcon(new ImageIcon(imagen.getScaledInstance(193, 193, Image.SCALE_DEFAULT)));
        }

        if (hoy.toLocaleString().equals(fec.toLocaleString()) || hoy.compareTo(fec) == 1) {
            jPanel3.setBackground(Color.red);
            jLabel4.setText(fec.toLocaleString().substring(0, 11));
            repaint();
            JOptionPane.showMessageDialog(this, "El usuario ha pasado la fecha de corte", "Aviso de ingreso", JOptionPane.WARNING_MESSAGE);
        } else {
            Date comfec = new Date(fec.getTime() - 259200000);
            if (hoy.compareTo(comfec) == 1) {

                jPanel3.setBackground(Color.YELLOW);
                jLabel4.setText(fec.toLocaleString().substring(0, 11));
                repaint();
            } else {
                jPanel3.setBackground(new Color(255, 255, 255, 100));
                jLabel4.setText(fec.toLocaleString().substring(0, 11));
                repaint();
            }
        }

    }

    public void BusquedaCompleta(int ID) {//metodo para desplgar en ventana el resultado
        if (ID == (-1)) {
            //JOptionPane.showMessageDialog(null, "No se ha encontrado la huella D");
            this.IDCliente = -1;
            jTextField1.setText("");
            jLabel1.setIcon(null);
            jTextField2.setText("");
            jTextField3.setText("");
            jTextArea2.setText("");
            jLabel2.setIcon(null);
            jLabel4.setText("");
            jPanel3.setBackground(new Color(255, 255, 255, 100));
            repaint();

        } else {//se encontro la huella
            this.IDCliente = ID;
            usadb db = new usadb();
            db.in_flujoDiarioCliente(new Date(), IDCliente);
            desplegarDatosCliente();

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SoftGym");
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        try{
            File f=new File(soft.direccion);
            if(!f.exists()||soft.direccion==null||soft.direccion.equals("\\imagenes\\Nike.jpg")){
                Utilidades.Borde_ImagenFondo borde = new Utilidades.Borde_ImagenFondo(ImageIO.read(this.getClass().
                    getResource("/imagenes/Nike.jpg")));
            jPanel1.setBorder(borde);
        }
        else{

            Utilidades.Borde_ImagenFondo borde = new Utilidades.Borde_ImagenFondo(ImageIO.read(f));
            jPanel1.setBorder(borde);
        }
    }
    catch(IOException ex){}
    jPanel1.updateUI();
    jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));
    jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jDesktopPane1.setOpaque(false);
    jDesktopPane1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            jDesktopPane1MouseEntered(evt);
        }
    });
    jDesktopPane1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jLabel1.setBackground(new Color(0,0,0,250));
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabel10.setText("Sesion de: ");

    jLabel11.setText("jLabel11");

    jButton11.setText("C.U.");
    jButton11.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton11ActionPerformed(evt);
        }
    });

    jButton3.setText("_");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });
    jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jButton2.setText("[ ]");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });
    jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jButton1.setText("X");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jPanel3.setBackground(new Color(255,255,255,100));
    jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    jLabel4.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    jLabel3.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel3.setText("Fecha de corte");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel2.setBackground(new Color(255,255,255,100));
    jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
            .addContainerGap())
    );

    jPanel6.setBackground(new Color(255,255,255,100));
    jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jPanel6.add(new Forms.Panel_Botones_Principal());
    jPanel6.setLayout(new java.awt.CardLayout());

    jTextField1.setEditable(false);
    jTextField1.setBackground(new java.awt.Color(255, 255, 255));
    jTextField1.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
    jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jTextField1.setText("Nombre");
    jTextField1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
    jTextField1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField1ActionPerformed(evt);
        }
    });
    jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jTextField2.setEditable(false);
    jTextField2.setBackground(new java.awt.Color(255, 255, 255));
    jTextField2.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
    jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jTextField2.setText("Telefono");
    jTextField2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
    jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jTextField3.setEditable(false);
    jTextField3.setBackground(new java.awt.Color(255, 255, 255));
    jTextField3.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
    jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jTextField3.setText("Mail");
    jTextField3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
    jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            formKeyPressed(evt);
        }
    });

    jTextArea2.setColumns(20);
    jTextArea2.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
    jTextArea2.setLineWrap(true);
    jTextArea2.setRows(5);
    jTextArea2.setBorder(javax.swing.BorderFactory.createTitledBorder("Notas:"));
    jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTextArea2MouseClicked(evt);
        }
    });
    jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            jTextArea2KeyPressed(evt);
        }
    });
    jScrollPane2.setViewportView(jTextArea2);

    jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jButton11, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jTextField2, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jTextField3, javax.swing.JLayeredPane.DEFAULT_LAYER);
    jDesktopPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

    javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
    jDesktopPane1.setLayout(jDesktopPane1Layout);
    jDesktopPane1Layout.setHorizontalGroup(
        jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jDesktopPane1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                            .addComponent(jButton11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField1)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                        .addComponent(jTextField3))))
            .addContainerGap())
    );
    jDesktopPane1Layout.setVerticalGroup(
        jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jDesktopPane1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addGap(0, 308, Short.MAX_VALUE)
                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton11))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)))
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jDesktopPane1)
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jDesktopPane1)
    );

    jMenu1.setText("Generales");

    jMenuItem1.setText("Buscar cliente");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem1);

    jMenuItem2.setText("Pagos Atrasados");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem2ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem2);
    jMenu1.add(jSeparator1);

    jMenuItem3.setText("Punto de venta");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem3ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem3);
    jMenu1.add(jSeparator2);

    jMenuItem4.setText("Reportes");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem4ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem4);
    jMenu1.add(jSeparator4);

    jMenuItem6.setText("Abrir Youtube");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem6ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem6);
    jMenu1.add(jSeparator3);

    jMenuItem5.setText("Configuracion");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem5ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem5);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("Ventana");
    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextArea2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea2MouseClicked
        // TODO add your handling code here:
        jTextArea2.setEditable(true);
        jTextArea2.requestFocus();
    }//GEN-LAST:event_jTextArea2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        soft.leer();
        if (soft.minimizar.equals("true")) {
            SoftGym.syd.stop();
            s = new LeerHuella_ConfimacionAdmin(new Dialogo_Minimizar("cerrar"));
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        soft.leer();
        if (soft.minimizar.equals("true")) {
            SoftGym.syd.stop();
            s = new LeerHuella_ConfimacionAdmin(new Dialogo_Minimizar("minimizar"));
        } else {
            if (mb != null) {
                mb.setVisible(false);
                mb.dispose();
                mb = null;
                NativeInterface.close();
                jMenuItem6.setText("Abrir Musica");
            }

            SoftGym.prin.removeNotify();
            SoftGym.prin.setUndecorated(false);
            SoftGym.prin.setLocationRelativeTo(null);
            SoftGym.prin.setExtendedState(NORMAL);
            SoftGym.prin.setSize(600, 400);
            SoftGym.prin.addNotify();
            SoftGym.prin.setExtendedState(ICONIFIED);

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (mb != null) {
            mb.setVisible(false);
            mb.dispose();
            mb = null;

            NativeInterface.close();
            jMenuItem6.setText("Abrir Musica");
        }

        if (getExtendedState() == NORMAL) {
            removeNotify();
            setUndecorated(true);
            setExtendedState(MAXIMIZED_BOTH);
            addNotify();
        } else {
            if (this.isUndecorated()) {
                soft.leer();
                if (soft.minimizar.equals("true")) {
                    SoftGym.syd.stop();
                    s = new LeerHuella_ConfimacionAdmin(new Dialogo_Minimizar("ventana"));
                } else {
                    SoftGym.prin.removeNotify();
                    SoftGym.prin.setUndecorated(false);
                    SoftGym.prin.setLocationRelativeTo(null);
                    SoftGym.prin.setExtendedState(NORMAL);
                    SoftGym.prin.setSize(600, 400);
                    SoftGym.prin.addNotify();
                }
            } else {

                removeNotify();
                setUndecorated(true);
                setExtendedState(MAXIMIZED_BOTH);
                addNotify();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jDesktopPane1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDesktopPane1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jDesktopPane1MouseEntered

    private void jTextArea2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            usadb db = new usadb();
            db.actualizarNotasCliente(IDCliente, jTextArea2.getText());
        }
    }//GEN-LAST:event_jTextArea2KeyPressed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    private Configuracion_Global c = null;
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ALT) {
            try {
                Robot robot = new Robot();
                robot.keyRelease(KeyEvent.VK_ALT);
                robot.keyRelease(KeyEvent.VK_TAB);
                this.requestFocus();
            } catch (AWTException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ALT) {
            this.requestFocus();
        }
    }//GEN-LAST:event_formKeyPressed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        SoftGym.syd.stop();
        SoftGym.syd0.stop();
        dispose();
        SoftGym.l = new Login();
        l.setLocationRelativeTo(null);
        syd0 = new LeerHuella_Login(l);

    }//GEN-LAST:event_jButton11ActionPerformed
    private MusicBrowser mb;
    public PuntoVenta pv;
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        Busqueda_Cliente b = new Busqueda_Cliente(new javax.swing.JFrame(), true);
        b.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Registro_Pagos_Atrasados m = new Registro_Pagos_Atrasados();
        jDesktopPane1.add(m);
        m.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        pv = new PuntoVenta();
        pv.setVisible(true);
        Principal.jDesktopPane1.add(pv);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        soft.leer();
        if (SoftGym.usuario.equals("normal") && soft.reportes.equals("false")) {
            JOptionPane.showMessageDialog(null, "No cuentas con los permisos necesarios para ejecutar esta opción");
        } else {
            Registro_Global_Operaciones c = new Registro_Global_Operaciones();
            c.setVisible(true);
            c.setSize(700, 450);
            Principal.jDesktopPane1.add(c);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        soft.leer();
        if (SoftGym.usuario.equals("normal") && soft.ajustes.equals("false")) {
            JOptionPane.showMessageDialog(null, "No tienes los perimsos para abrir esta ventana");
        } else {
            if (c == null) {
                c = new Configuracion_Global();
                c.setLocationRelativeTo(null);
                c.setVisible(true);
            } else {
                c.setExtendedState(NORMAL);
                c.setVisible(true);
                c.toFront();
                c.setLocationRelativeTo(null);

            }
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        if (mb == null) {
            mb = new MusicBrowser();
            Principal.jDesktopPane1.add(mb);
            mb.setVisible(true);
            jMenuItem6.setText("Cerrar Musica");
        } else {
            mb.setVisible(false);
            mb.dispose();
            mb = null;

            NativeInterface.close();
            jMenuItem6.setText("Abrir Musica");
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    public static javax.swing.JDesktopPane jDesktopPane1;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JTextArea jTextArea2;
    public javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

class DeferredDocumentListener implements DocumentListener {

    private final Timer timer;

    public DeferredDocumentListener(int timeOut, ActionListener listener, boolean repeats) {
        timer = new Timer(timeOut, listener);
        timer.setRepeats(repeats);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        timer.restart();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        timer.restart();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        timer.restart();
    }

}
