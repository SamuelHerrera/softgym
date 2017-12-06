/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author Erick
 */

public class EnviarEmail {
    SoftGym prop = new SoftGym();
    public static String Username = "softwarepv0@gmail.com";
    public static String PassWord = "Devel0per";
    String Mensage = "";
    String To = "";
    String Subject = "Total del dia "+new Date().toLocaleString();
    usadb db = new usadb();
    
    
    public void SendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, PassWord);
                    }
                });

        try {
            usapv pv = new usapv();
            Object[][] cortes=pv.get_corte(new Object[]{new Date(),new Date()}, "fecha");
            Object[][]ingresos= db.get_ingresos(new Object[]{new Date(),new Date()}, "fecha");
            Object[][]egresos = db.get_egresos(new Object[]{new Date(), new Date()},"fecha");
            String datosIns="";
            String datosMens="";
            String datosOtros="";
            /////////////////////////////////////
            String datosSueldos="";
            String datosPagosS="";
            String datosOtrosP="";
            ////////////////////////////////////
            String datosCorte="";
            double totalingreso=0;
            double totalegreso=0;
            
            for(int i =0;ingresos!=null&&i<ingresos.length;i++){
                if(ingresos[i][2].equals("Inscripcion")){
                    datosIns=datosIns+ingresos[i][1]+"||"+ingresos[i][2]+"||"+ingresos[i][3]+"||"+ingresos[i][4]+"||"+ingresos[i][5]+"\n";
                }
                if(ingresos[i][2].equals("Mensualidad")){
                    datosMens=datosMens+ingresos[i][1]+"||"+ingresos[i][2]+"||"+ingresos[i][3]+"||"+ingresos[i][4]+"||"+ingresos[i][5]+"\n";
                }
                if(ingresos[i][2].equals("Otros")){
                    datosOtros=datosOtros+ingresos[i][1]+"||"+ingresos[i][2]+"||"+ingresos[i][3]+"||"+ingresos[i][4]+"||"+ingresos[i][5]+"\n";
                }
                totalingreso=totalingreso+(double)ingresos[i][4];
            }
            
            for(int i =0;egresos!=null&&i<egresos.length;i++){
                if(egresos[i][2].equals("Sueldos")){
                    datosSueldos=datosSueldos+egresos[i][1]+"||"+egresos[i][2]+"||"+egresos[i][3]+"||"+egresos[i][4]+"||"+egresos[i][5]+"\n";
                }
                if(egresos[i][2].equals("Pagos de servicios")){
                    datosPagosS=datosPagosS+egresos[i][1]+"||"+egresos[i][2]+"||"+egresos[i][3]+"||"+egresos[i][4]+"||"+egresos[i][5]+"\n";
                }
                if(egresos[i][2].equals("Otros")){
                    datosOtrosP=datosOtrosP+egresos[i][1]+"||"+egresos[i][2]+"||"+egresos[i][3]+"||"+egresos[i][4]+"||"+egresos[i][5]+"\n";
                }
                totalegreso=totalegreso+(double)egresos[i][4];
            }
            
            for(int j=0;cortes!=null&&j<cortes.length;j++){
                datosCorte=datosCorte+cortes[j][1]+"||"+cortes[j][2]+"||"+cortes[j][3]+"||"+cortes[j][4]+"||"+cortes[j][5]+"\n";
            }
            
            prop.leer();
            To=prop.correo;
            Mensage="----------------Ingresos del día----------------\n"+"Inscripciones del dia:\n"+datosIns+"\nPagos de Mensualidad:\n"+datosMens+
                    "\nOtros Ingresos:\n"+datosOtros+"\nTotal Ingresos: "+totalingreso+"\n\n"+
                    "----------------Egresos del día----------------\n"+"Sueldos:\n"+datosSueldos+"\nPagos de servicios:\n"+datosPagosS+
                    "\nOtros Pagos:\n"+datosPagosS+totalingreso+"\nTotal egreso: "+totalegreso+"\n\n"+
                    "----------------Cortes del día----------------\n"+datosCorte; //"Total ingreso: "+totalingreso+"\n"+"Total egreso: "+totalegreso+"\n";
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensage);

            Transport.send(message);
            //JOptionPane.showMessageDialog(null, "Su mensaje ha sido enviado");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
