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
public class generar_conexionpv {

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (con == null) {
                Runtime.getRuntime().addShutdownHook(new MiShDwnHook1());
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://localhost/puntoventa?autoReconnect=true";
                String pwd = "root";
                String usr = "root";
                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                Runtime.getRuntime().addShutdownHook(new MiShDwnHook1());
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://localhost/puntoventa?autoReconnect=true";
                String pwd = JOptionPane.showInputDialog(null, "Contraseña de la base de datos: ");
                String usr = "root";
                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);
            } catch (Exception e) {
                System.exit(0);
            }
        }
        return con;
    }
}

class MiShDwnHook1 extends Thread {// justo antes de ﬁ nalizar el programa la JVM invocara     
    // a este metodo donde podemos cerrar la conexion       

    @Override
    public void run() {
        try {
            Connection con = generar_conexionpv.getConnection();
            con.close();
        } catch (Exception ex) {
        }
    }
}
