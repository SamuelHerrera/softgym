package Utilidades;

//import Completados.Mensajes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Samuel herrera
 */
public class generar_conexion {

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (con == null) {
                Runtime.getRuntime().addShutdownHook(new MiShDwnHook());
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://localhost/softgym?autoReconnect=true";
                String pwd = "root";
                String usr = "root";
                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);
                System.out.println("Conection Succesfull");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                Runtime.getRuntime().addShutdownHook(new MiShDwnHook1());
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://localhost/softgym?autoReconnect=true";
                String pwd = JOptionPane.showInputDialog(null, "Contraseña de la base de datos: ");
                String usr = "root";
                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);
                System.out.println("Conection Succesfull");
            } catch (Exception e) {
                System.exit(0);
            }
            //ex.printStackTrace();
//             Mensajes m=new Mensajes("Error de conexion","No se puede conectar a la base de datos, contacte a soporte!.");

        }
        return con;
    }
}

class MiShDwnHook extends Thread {// justo antes de ﬁ nalizar el programa la JVM invocara     
    // a este metodo donde podemos cerrar la conexion       

    @Override
    public void run() {
        try {
            Connection con = generar_conexion.getConnection();
            con.close();
            System.out.println("Connection closed succesfully");
        } catch (Exception ex) {
            //throw new RuntimeException(ex);       
        }
    }
}
