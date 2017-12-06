/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import Utilidades.ImageSerializable;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ErickFrancisco
 */
public class usadb {

    public Object[][] buscar_cliente(String nombre) {
        Object[][] resp = null;
        try {
            Connection c = Utilidades.generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            rs = s.executeQuery("select * from cliente where nombre like '%" + nombre + "%'");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][5];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getString("nombre");
                resp[i][2] = rs.getString("telefono");
                resp[i][3] = rs.getString("email");
                resp[i][4] = rs.getDate("fecharegistro");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public boolean in_cliente(String nombre,
            String telefono, String email,
            Date fecharegistro)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("INSERT INTO Cliente VALUES (?,?,?,?,?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setString(2, nombre);
            st.setString(3, telefono);
            st.setString(4, email);
            st.setDate(5, new java.sql.Date(fecharegistro.getTime()));
            st.setString(6, "");

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_FotoCliente(Image foto) {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("INSERT INTO FotoCliente VALUES (?,?)");
            st.setObject(1, this.ObtenerUltimoCliente());//idmovimiento,----Autoincrementado          
            st.setObject(2, new ImageSerializable(foto));

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_HuellaCliente(DPFPTemplate template) {
        boolean sucessfull = false;

        ByteArrayInputStream huella = new ByteArrayInputStream(template.serialize());
        Integer largo = template.serialize().length;
        try {
            /*
                 byte templateBuffer[] = (byte []) huellas[index][1];
            DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer); 
            
             */

            Connection c = generar_conexion.getConnection();
            PreparedStatement st;

            st = c.prepareStatement("INSERT INTO HuellaCliente VALUES (?,?)");
            st.setObject(1, this.ObtenerUltimoCliente());//idmovimiento,----Autoincrementado          
            st.setBinaryStream(2, huella, largo);

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_HuellaCliente(int idcliente, DPFPTemplate template) {
        boolean sucessfull = false;

        ByteArrayInputStream huella = new ByteArrayInputStream(template.serialize());
        Integer largo = template.serialize().length;
        try {
            /*
                 byte templateBuffer[] = (byte []) huellas[index][1];
            DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer); 
            
             */

            Connection c = generar_conexion.getConnection();
            PreparedStatement st;

            st = c.prepareStatement("INSERT INTO HuellaCliente VALUES (?,?)");
            st.setObject(1, idcliente);//idmovimiento,----Autoincrementado          
            st.setBinaryStream(2, huella, largo);

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public void eliminarCliente(int idcliente) {
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("DELETE FROM FechaCorte_Clientes where idcliente= ?");
            st.setInt(1, idcliente);//idmovimiento,----Autoincrementado          
            if (st.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("DELETE FROM FlujoDiarioClientes WHERE idcliente = ?");
            st.setInt(1, idcliente);//idmovimiento,----Autoincrementado          
            if (st.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("DELETE FROM HistorialPagoClientes WHERE idcliente = ?");
            st.setInt(1, idcliente);//idmovimiento,----Autoincrementado          
            if (st.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("DELETE FROM HuellaCliente WHERE idcliente = ?");
            st.setInt(1, idcliente);//idmovimiento,----Autoincrementado          
            if (st.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("DELETE FROM FotoCliente WHERE idcliente = ?");
            st.setInt(1, idcliente);//idmovimiento,----Autoincrementado          
            if (st.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("DELETE FROM cliente WHERE idcliente = ?");
            st.setInt(1, idcliente);//idmovimiento,----Autoincrementado          
            if (st.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

    }

    public boolean in_usuario(String nombre,
            String contraseña, String tipo)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;

            st = c.prepareStatement("INSERT INTO Usuario VALUES (?,?,?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setString(2, nombre);
            st.setString(3, contraseña);
            st.setString(4, tipo);
            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_FotoUsuario(File foto) {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            FileInputStream fila = new FileInputStream(foto);
            st = c.prepareStatement("INSERT INTO FotoUsuario VALUES (?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setBinaryStream(2, fila, (int) foto.length());

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_HuellaUsuario(DPFPTemplate template) {
        boolean sucessfull = false;
        ByteArrayInputStream huella = new ByteArrayInputStream(template.serialize());
        Integer largo = template.serialize().length;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;

            st = c.prepareStatement("INSERT INTO HuellaUsuario VALUES (?,?)");
            st.setObject(1, this.ObtenerUltimoUsuario());//idmovimiento,----Autoincrementado          
            st.setBinaryStream(2, huella, largo);

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_ingreso(Date fecha,
            String motivo, String descripcion,
            Double cantidad, String usuario)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            //FileInputStream fila= new FileInputStream(imagen);
            st = c.prepareStatement("INSERT INTO Ingresos VALUES (?,?,?,?,?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setDate(2, new java.sql.Date(fecha.getTime()));
            st.setString(3, motivo);
            st.setString(4, descripcion);
            st.setDouble(5, cantidad);
            st.setString(6, usuario);

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_egreso(Date fecha,
            String motivo, String descripcion,
            Double cantidad, String usuario)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            //FileInputStream fila= new FileInputStream(imagen);
            st = c.prepareStatement("INSERT INTO Egresos VALUES (?,?,?,?,?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setDate(2, new java.sql.Date(fecha.getTime()));
            st.setString(3, motivo);
            st.setString(4, descripcion);
            st.setDouble(5, cantidad);
            st.setString(6, usuario);

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_corteEfectivoPeriodo(Double totalingresos,
            Double totalegresos, Double efectivocaja,
            String usuario, Date finicioperiodo, Date fterminoperiodo)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            //FileInputStream fila= new FileInputStream(imagen);
            st = c.prepareStatement("INSERT INTO CorteEfectivo_periodo VALUES (?,?,?,?,?,?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setDouble(2, totalingresos);
            st.setDouble(3, totalegresos);
            st.setDouble(4, efectivocaja);
            st.setString(5, usuario);
            st.setDate(6, new java.sql.Date(finicioperiodo.getTime()));
            st.setDate(7, new java.sql.Date(fterminoperiodo.getTime()));

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_historialClientes(int idingreso,
            int idcliente)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            //FileInputStream fila= new FileInputStream(imagen);
            st = c.prepareStatement("INSERT INTO HistorialPagoClientes VALUES (?,?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setInt(2, idingreso);
            st.setInt(3, idcliente);

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_fechaCorteCliente(int idcliente, Date fechacorte)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            //FileInputStream fila= new FileInputStream(imagen);
            st = c.prepareStatement("INSERT INTO FechaCorte_Clientes VALUES (?,?)");
            st.setInt(1, idcliente);//idmovimiento,----Autoincrementado          
            st.setDate(2, new java.sql.Date(fechacorte.getTime()));

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public boolean in_flujoDiarioCliente(Date fechahora, int idcliente)//Pendiente ingresar la huella XD
    {
        boolean sucessfull = false;
        try {
            Connection c = generar_conexion.getConnection();
            PreparedStatement st;
            //FileInputStream fila= new FileInputStream(imagen);
            st = c.prepareStatement("INSERT INTO FlujoDiarioClientes VALUES (?,?,?)");
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setTimestamp(2, new java.sql.Timestamp(fechahora.getTime()));
            st.setInt(3, idcliente);

            if (st.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }

        return sucessfull;
    }

    public Object[][] get_cliente(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idcliente":
                    rs = s.executeQuery("select * from Cliente where " + criterio + " = " + dato.toString());
                    break;
                case "nombre":
                    rs = s.executeQuery("select * from Cliente where " + criterio + " = '" + dato.toString() + "'");
                    break;
                case "telefono":
                    rs = s.executeQuery("select * from Cliente where " + criterio + " = '" + dato.toString() + "'");
                    break;
                case "email":
                    rs = s.executeQuery("select * from Cliente where " + criterio + " = '" + dato.toString() + "'");
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos

            if (num_result != 0) {
                rs.beforeFirst();
                resp = new Object[num_result][6];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getString("nombre");
                resp[i][2] = rs.getString("telefono");
                resp[i][3] = rs.getString("email");
                resp[i][4] = rs.getDate("fecharegistro");
                resp[i][5] = rs.getString("notas");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Image get_fotocliente(Object dato, String criterio) {
        Object[][] resp = null;
        Image imagen = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idcliente":
                    rs = s.executeQuery("select * from FotoCliente where idcliente = " + dato.toString());
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getObject("foto");
            }
            ImageSerializable x = null;
            try {
                if (resp != null && resp[0] != null && resp[0][1] != null) {
                    x = (ImageSerializable) new ObjectInputStream(new ByteArrayInputStream((byte[]) resp[0][1])).readObject();
                    imagen = x.getImage();
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(usadb.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return imagen;
    }

    public Object[][] get_huellacliente(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idcliente":
                    rs = s.executeQuery("select * from HuellaCliente where idcliente = " + dato.toString());
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getBinaryStream("huelladigital");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] get_usuario(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idusuario":
                    rs = s.executeQuery("select * from Usuario where " + criterio + " = " + dato.toString());
                    break;
                case "nombre":
                    rs = s.executeQuery("select * from Usuario where " + criterio + " = '" + dato.toString() + "'");
                    break;
                case "contrasena":
                    rs = s.executeQuery("select * from Usuario where " + criterio + " ='" + dato.toString() + "'");
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][4];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idusuario");
                resp[i][1] = rs.getString("nombre");
                resp[i][2] = rs.getString("contrasena");
                resp[i][3] = rs.getString("tipo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] get_huellaUsuario(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idusuario":
                    rs = s.executeQuery("select * from HuellaUsuario where idusuario = " + dato.toString());
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idusuario");
                resp[i][1] = rs.getBytes("huellausuario");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] get_fotoUsuario(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idusuario":
                    rs = s.executeQuery("select * from FotoUsuario where idusuario = " + dato.toString());
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idusuario");
                resp[i][1] = rs.getBinaryStream("fotousuario");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    //*******Pendiente hacer el get e in de codigo barra cliente y usuario**********//
    public Object[][] get_ingresos(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idIngresos":
                    rs = s.executeQuery("select * from Ingresos where " + criterio + " = " + dato.toString());
                    break;
                case "fecha":
                    Object[] aux = (Object[]) dato;
                    //rs= s.executeQuery ("select * from Ingresos where "+criterio+" between "+aux[0]+" and "+aux[1]);
                    PreparedStatement pstmt = (PreparedStatement) c.prepareStatement("SELECT * FROM Ingresos WHERE fecha >= ? AND fecha <= ?");
                    pstmt.setDate(1, new java.sql.Date(((Date) aux[0]).getTime()));
                    pstmt.setDate(2, new java.sql.Date(((Date) aux[1]).getTime()));
                    rs = pstmt.executeQuery();
                    break;
                case "motivo":
                    rs = s.executeQuery("select * from Ingresos where " + criterio + " ='" + dato.toString() + "'");
                    break;
                case "cantidad":
                    rs = s.executeQuery("select * from Ingresos where " + criterio + " =" + dato.toString());
                    break;
                case "usuario":
                    rs = s.executeQuery("select * from Ingresos where " + criterio + " ='" + dato.toString() + "'");
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos

            if (num_result != 0) {
                rs.beforeFirst();
                resp = new Object[num_result][6];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idIngresos");
                resp[i][1] = rs.getDate("fecha");
                resp[i][2] = rs.getString("motivo");
                resp[i][3] = rs.getString("descripcion");
                resp[i][4] = rs.getDouble("cantidad");
                resp[i][5] = rs.getString("usuario");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] get_egresos(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "fecha":
                    Object[] aux = (Object[]) dato;
                    PreparedStatement pstmt = (PreparedStatement) c.prepareStatement("SELECT * FROM Egresos WHERE fecha >= ? AND fecha <= ?");
                    pstmt.setDate(1, new java.sql.Date(((Date) aux[0]).getTime()));
                    pstmt.setDate(2, new java.sql.Date(((Date) aux[1]).getTime()));
                    rs = pstmt.executeQuery();
                    break;
                case "motivo":
                    rs = s.executeQuery("select * from Egresos where " + criterio + " ='" + dato.toString() + "'");
                    break;
                case "cantidad":
                    rs = s.executeQuery("select * from Egresos where " + criterio + " =" + dato.toString());
                    break;
                case "usuario":
                    rs = s.executeQuery("select * from Egresos where " + criterio + " ='" + dato.toString() + "'");
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }

//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][6];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idEgresos");
                resp[i][1] = rs.getDate("fecha");
                resp[i][2] = rs.getString("motivo");
                resp[i][3] = rs.getString("descripcion");
                resp[i][4] = rs.getDouble("cantidad");
                resp[i][5] = rs.getString("usuario");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] get_cortePeriodo(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "fecha":
                    rs = s.executeQuery("select * from CorteEfectivo_periodo where " + dato + "> FInicioPeriodo and" + dato + " < FTerminoPeriodo");
                    break;
                case "Usuario":
                    rs = s.executeQuery("select * from CorteEfectivo_periodo where " + criterio + " ='" + dato.toString() + "'");
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][7];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idCorteEfectivo_periodo");
                resp[i][1] = rs.getDouble("TotalIngresos");
                resp[i][2] = rs.getDouble("TotalEgresos");
                resp[i][3] = rs.getDouble("EfectivoEnCaja");
                resp[i][4] = rs.getString("Usuario");
                resp[i][5] = rs.getDate("FInicioPeriodo");
                resp[i][6] = rs.getDate("FTerminoPeriodo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] get_historialPagoClientes(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "idIngresos":
                    rs = s.executeQuery("select * from HistorialPagoClientes where " + criterio + " = " + dato.toString());
                    break;
                case "idcliente":
                    rs = s.executeQuery("select * from HistorialPagoClientes where " + criterio + " = " + dato.toString());
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][3];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idHistorialPagoClientes");
                resp[i][1] = rs.getInt("idIngresos");
                resp[i][2] = rs.getInt("idcliente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] get_flujoDiarioClientes(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "fechahora":
                    Object[] aux = (Object[]) dato;
                    //rs= s.executeQuery ("select * from Ingresos where "+criterio+" between "+aux[0]+" and "+aux[1]);
                    PreparedStatement pstmt = (PreparedStatement) c.prepareStatement("SELECT * FROM FlujoDiarioClientes WHERE fechahora >= ? AND fechahora <= ?");
                    pstmt.setDate(1, new java.sql.Date(((Date) aux[0]).getTime()));
                    pstmt.setDate(2, new java.sql.Date(((Date) aux[1]).getTime()));
                    rs = pstmt.executeQuery();
                    break;
                case "idcliente":
                    rs = s.executeQuery("select * from FlujoDiarioClientes where " + criterio + " = " + dato.toString());
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][3];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idFlujoClientes");
                resp[i][1] = new Date(rs.getTimestamp("fechahora").getTime());
                resp[i][2] = rs.getInt("idcliente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] get_fechaCorteClientes(Object dato, String criterio) {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = null;
            switch (criterio) {
                case "fechacorte":
                    Object[] aux = (Object[]) dato;
                    PreparedStatement pstmt = (PreparedStatement) c.prepareStatement("SELECT * FROM FechaCorte_Clientes WHERE fechacorte >= ? AND fechacorte <= ?");
                    pstmt.setDate(1, new java.sql.Date(((Date) aux[0]).getTime()));
                    pstmt.setDate(2, new java.sql.Date(((Date) aux[1]).getTime()));
                    rs = pstmt.executeQuery();
                    break;
                case "idcliente":
                    rs = s.executeQuery("select * from FechaCorte_Clientes where " + criterio + " = " + dato.toString());
                    break;
            }

            int num_result = 0;
            while (rs != null && rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getDate("fechacorte");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }
        return resp;
    }

    public Object[][] getAll_Cliente() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from Cliente");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][6];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getString("nombre");
                resp[i][2] = rs.getString("telefono");
                resp[i][3] = rs.getString("email");
                resp[i][4] = rs.getDate("fecharegistro");
                resp[i][5] = rs.getString("notas");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_FotoCliente() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from FotoCliente");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getBinaryStream("foto");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_HuellaCliente() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from HuellaCliente");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getBytes("huelladigital");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_Usuario() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from Usuario");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][4];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idusuario");
                resp[i][1] = rs.getString("nombre");
                resp[i][2] = rs.getString("contrasena");
                resp[i][3] = rs.getString("tipo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_FotoUsuario() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from FotoUsusario");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idusuario");
                resp[i][1] = rs.getBinaryStream("foto");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_HuellaUsuario() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from HuellaUsuario");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idusuario");
                resp[i][1] = rs.getBytes("huellausuario");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public int ObtenerUltimoIngreso() {
        int resp = -1;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select idIngresos from Ingresos");
            rs.last();

            resp = rs.getInt("idIngresos");
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = -1;
        }
        return resp;
    }

    public int ObtenerUltimoCliente() {
        int resp = -1;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select idcliente from Cliente");
            rs.last();

            resp = rs.getInt("idcliente");
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = -1;
        }
        return resp;
    }

    public int ObtenerUltimoUsuario() {
        int resp = -1;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select idusuario from Usuario");
            rs.last();

            resp = rs.getInt("idusuario");
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = -1;
        }
        return resp;
    }

    public Object[][] getAll_Ingresos() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from Ingresos");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][6];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idIngresos");
                resp[i][1] = rs.getDate("fecha");
                resp[i][2] = rs.getString("motivo");
                resp[i][3] = rs.getString("descripcion");
                resp[i][4] = rs.getDouble("cantidad");
                resp[i][5] = rs.getString("usuario");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_Egresos() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from Egresos");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][6];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idEgresos");
                resp[i][1] = rs.getDate("fecha");
                resp[i][2] = rs.getString("motivo");
                resp[i][3] = rs.getString("descripcion");
                resp[i][4] = rs.getDouble("cantidad");
                resp[i][5] = rs.getString("usuario");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_CorteEfectivoPeriodo() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from CorteEfectivo_periodo");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][7];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idCorteEfectivo_periodo");
                resp[i][1] = rs.getDouble("TotalIngresos");
                resp[i][2] = rs.getDouble("TotalEgresos");
                resp[i][3] = rs.getDouble("EfectivoEnCaja");
                resp[i][4] = rs.getString("Usuario");
                resp[i][5] = rs.getDate("FInicioPeriodo");
                resp[i][6] = rs.getDate("FTerminoPeriodo");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_HistorialPagoClientes() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from HistorialPagoClientes");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][3];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idHistorialPagoClientes");
                resp[i][1] = rs.getInt("idIngresos");
                resp[i][2] = rs.getInt("idcliente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_FlujoDiarioClientes() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from FlujoDiarioClientes");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][3];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idFlujoClientes");
                resp[i][1] = rs.getDate("fechahora");
                resp[i][2] = rs.getInt("idcliente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public Object[][] getAll_FechaCorteClientes() {
        Object[][] resp = null;
        try {
            Connection c = generar_conexion.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from FechaCorte_Clientes");
            int num_result = 0;
            while (rs.next()) {
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if (num_result != 0) {
                resp = new Object[num_result][2];
            }
            for (int i = 0; i < num_result; i++) {
                rs.next();
                resp[i][0] = rs.getInt("idcliente");
                resp[i][1] = rs.getDate("fechacorte");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = null;
        }

        return resp;
    }

    public boolean actualizar(String tabla, Object[] datos) {
        String clientes[] = {"idcliente", "nombre", "telefono", "email"};
        String fotoclientes[] = {"idcliente", "foto"};
        String huellaclientes[] = {"idcliente", "huelladigital"};
        String usuario[] = {"idusuario", "nombre", "contrasena", "tipo"};
        String fotousuarios[] = {"idusuario", "foto"};
        String huellausuario[] = {"idusuario", "huellausuario"};
        String ingresos[] = {"idIngresos", "fecha", "motivo", "descripcion", "cantidad", "usuario"};
        String egresos[] = {"idEgresos", "fecha", "motivo", "descripcion", "cantidad", "usuario"};
        String corteefectivo_periodo[] = {"idCorteEfectivo_periodo", "TotalIngresos", "TotalEgresos", "EfectivoEnCaja", "Usuario", "FInicioPeriodo", "FTerminoPeriodo"};
        String historialpagoclientes[] = {"idHistorialPagoClientes", "idIngresos", "idcliente"};
        String flujodiarioclientes[] = {"idFlujoClientes", "fechahora", "idcliente"};
        String fechacorte_clientes[] = {"idcliente", "fechacorte"};
        boolean sucessfull = false;
        try {
            Connection c = Utilidades.generar_conexion.getConnection();
            String upd = "update " + tabla + " set ";
            switch (tabla) {

                case "Cliente":
                    for (int i = 0; i < clientes.length; i++) {
                        upd = upd + " " + clientes[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + clientes[0] + " = " + com(datos[0]);

                    break;
                case "FotoCliente":
                    for (int i = 0; i < fotoclientes.length; i++) {
                        upd = upd + " " + fotoclientes[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + fotoclientes[0] + " = " + com(datos[0]);
                    break;
                case "HuellaCliente":
                    for (int i = 0; i < huellaclientes.length; i++) {
                        upd = upd + " " + huellaclientes[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + huellaclientes[0] + " = " + com(datos[0]);
                    break;
                case "Usuario":
                    for (int i = 0; i < usuario.length; i++) {
                        upd = upd + " " + usuario[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + usuario[0] + " = " + com(datos[0]);
                    break;
                case "FotoUsuario":
                    for (int i = 0; i < fotousuarios.length; i++) {
                        upd = upd + " " + fotousuarios[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + fotousuarios[0] + " = " + com(datos[0]);
                    break;
                case "HuellaUsuario":
                    for (int i = 0; i < huellausuario.length; i++) {
                        upd = upd + " " + huellausuario[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + huellausuario[0] + " = " + com(datos[0]);
                    break;

                case "Ingresos":
                    for (int i = 0; i < ingresos.length; i++) {
                        upd = upd + " " + ingresos[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + ingresos[0] + " = " + com(datos[0]);

                    break;
                case "CorteEfectivo_periodo":
                    for (int i = 0; i < corteefectivo_periodo.length; i++) {
                        upd = upd + " " + corteefectivo_periodo[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + corteefectivo_periodo[0] + " = " + com(datos[0]);

                    break;

                case "HistorialPagoClientes":
                    for (int i = 0; i < historialpagoclientes.length; i++) {
                        upd = upd + " " + historialpagoclientes[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + historialpagoclientes[0] + " = " + com(datos[0]);

                    break;

                case "FechaCorte_Clientes":
                    for (int i = 0; i < fechacorte_clientes.length; i++) {
                        upd = upd + " " + fechacorte_clientes[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + fechacorte_clientes[0] + " = " + com(datos[0]);

                    break;
                case "FlujodiarioClientes":
                    for (int i = 0; i < flujodiarioclientes.length; i++) {
                        upd = upd + " " + flujodiarioclientes[i] + " = ?,";
                    }
                    upd = upd.substring(0, upd.length() - 1);
                    upd = upd + " where " + flujodiarioclientes[0] + " = " + com(datos[0]);

                    break;
            }

            PreparedStatement preparedStmt = c.prepareStatement(upd);
            for (int i = 0; i < datos.length; i++) {

                if (datos[i].getClass().equals(String.class)) {
                    preparedStmt.setString(i + 1, (String) datos[i]);
                }
                if (datos[i].getClass().equals(Double.class)) {
                    preparedStmt.setDouble(i + 1, (Double) datos[i]);
                }
                if (datos[i].getClass().equals(Integer.class)) {
                    preparedStmt.setInt(i + 1, (Integer) datos[i]);
                }
                if (datos[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date aux = (java.util.Date) datos[i];
                    preparedStmt.setDate(i + 1, new java.sql.Date(aux.getTime()));
                }

                if (datos[i].getClass().equals(ImageSerializable.class)) {
                    preparedStmt.setObject(2, (ImageSerializable) datos[1]);

                }

            }
            //No le entendi XD

            if (tabla.equals("HuellaCliente")) {
                DPFPTemplate aux = (DPFPTemplate) datos[1];
                ByteArrayInputStream huella = new ByteArrayInputStream(aux.serialize());
                Integer largo = aux.serialize().length;
                preparedStmt.setBinaryStream(2, huella, largo);
            }

            // execute the java preparedstatement
            if (preparedStmt.executeUpdate() == 1) {
                sucessfull = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            sucessfull = false;
        }
        return sucessfull;
    }

    public void actualizarNotasCliente(int idcliente, String notas) {
        try {
            Connection c = Utilidades.generar_conexion.getConnection();
            String upd = "update cliente set notas=? where idcliente = " + idcliente;
            PreparedStatement preparedStmt = c.prepareStatement(upd);
            preparedStmt.setString(1, notas);
            if (preparedStmt.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String com(Object dato) {
        if (dato.getClass().equals(String.class)) {
            return "'" + dato.toString() + "'";
        } else {
            return dato.toString();
        }
    }

    public boolean eliminar(String tabla, String criterio, Object dato) {
        boolean resp = false;
        try {
            Connection c = Utilidades.generar_conexion.getConnection();
            PreparedStatement st;

            if (dato.getClass().equals(String.class)) {
                st = c.prepareStatement("DELETE FROM " + tabla + " WHERE " + criterio + " = '" + dato.toString() + "'");
            } else {
                st = c.prepareStatement("DELETE FROM " + tabla + " WHERE " + criterio + " = " + dato.toString());
            }
            if (st.executeUpdate() == 1) {
                resp = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resp = false;
        }
        return resp;
    }
}
