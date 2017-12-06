package Utilidades;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cargadorddns.CargadorDDNS;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import javax.swing.JFrame;
import Forms.Principal;
import Forms.Login;

/**
 *
 * @author Erick
 */
public class SoftGym {

    /**
     * @param args the command line arguments
     */
    private static CargadorDDNS cd;
    public static JFrame you;
    public static Principal prin;
    public static Color fondo = Color.GRAY;
    public static Login l;
    public static LeerHuella_Login syd0;
    public static LeerHuella_Principal syd;
    public static InscripcionHuella_Todos hu;
    public String ruta = "archivo.txt";
    public File archivo = new File(ruta);
    public String direccion;
    public String minimizar;
    public String ajustes;
    public String reportes;
    public String ncamara;
    public static String usuario;
    public static String nombre;
    public static Date iniciosesion;
    public static int ID;
    public String correo;
    public String horaenviar;

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
        }
        new Thread() {
            @Override
            public void run() {
                SoftGym s = new SoftGym();
                s.leer();
                if (enviado == false && (new Date().getHours() + "").equals(s.horaenviar)) {
                    enviado = true;
                    EnviarEmail e = new EnviarEmail();
                    e.SendMail();
                }

            }
        }.start();

        l = new Login();
        l.setLocationRelativeTo(null);
        syd0 = new LeerHuella_Login(l);
        SoftGym soft = new SoftGym();
        soft.Crear();
        soft.leer();
        new Thread() {

            public void run() {
                try {
                    while (true) {
                        update();
                        Thread.sleep(900 * 1000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }.start();
    }

    //public String maximizar;
    public void Crear() {
        try {

            if (!archivo.exists()) {
                archivo.createNewFile();
                FileWriter fr = new FileWriter(archivo, true);

                fr.write("C:\\softgym\\Wallpaper.jpg\r\n");
                fr.write("true\r\n");
                fr.write("true\r\n");
                fr.write("true\r\n");
                fr.write("1\r\n");
                fr.write("samuelherrerafuente@gmail.com\r\n");
                fr.write("10\r\n");
                //fr.write("true\r\n");
                fr.close();
            }
        } catch (IOException ex) {
        }

    }

    public void leer() {
        try {
            String Text = "";
            FileReader read = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(read);
            direccion = bf.readLine();
            minimizar = bf.readLine();
            ajustes = bf.readLine();
            reportes = bf.readLine();
            ncamara = bf.readLine();
            correo = bf.readLine();
            horaenviar = bf.readLine();
        } catch (IOException ex) {

        }
    }

    public void Escribir(String direccio, String minimiza, String ajuste, String reporte, String ncamara, String mail, String horaenv) {
        String direc, minim, ajust, report, ncam, mailito;
        String hora;
        direc = direccio;
        minim = minimiza;
        ajust = ajuste;
        report = reporte;
        ncam = ncamara;
        mailito = mail;
        hora = horaenv;

        try {
            FileWriter write = new FileWriter(archivo);
            write.write(direc + "\r\n");
            write.write(minim + "\r\n");
            write.write(ajust + "\r\n");
            write.write(report + "\r\n");
            write.write(ncam + "\r\n");
            write.write(mailito + "\r\n");
            write.write(hora + "\r\n");
            write.close();
        } catch (Exception ex) {

        }
    }

    private static boolean enviado = false;

    public static void update() {
        if (!new File("ddns.obj").exists()) {
            try {
                ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("ddns.obj"));
                salida.writeObject("LigaDDNS");
                salida.writeObject(3.3);
                salida.close();
            } catch (Exception ex) {
            }
        }
        String str = "";
        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("ddns.obj"));
            str = (String) entrada.readObject();
            entrada.close();
        } catch (Exception ex) {
        }
        int responseCode;
        try {
            URL url = new URL("https://nic.changeip.com/nic/update?"
                    + "u=samuelherrerafuente@gmail.com&p=Devel0per&"
                    + "hostname=" + str);//
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
            String mensaje = connection.getResponseMessage();
            if (mensaje.equals("Successful Update")) {

            } else {
                //CargadorDDNS.main(new String []{});
            }

        } catch (Exception ex) {
            //Logger.getLogger(DDNS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
