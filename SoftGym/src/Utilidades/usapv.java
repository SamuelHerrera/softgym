/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
/**
 *
 * @author ErickFrancisco
 */
public class usapv {
    
    public Object[][] buscar_producto(String descripcion){
       Object[][] resp=null;
        try{
            Connection c=Utilidades.generar_conexionpv.getConnection();
            Statement s = c.createStatement(); 
            ResultSet rs=null;
            rs= s.executeQuery ("select * from Inventario where descripcion like '%"+descripcion+"%'");
            int num_result=0;
            while(rs.next()){
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if(num_result!=0){
                resp=new Object[num_result][6];
            }
            for(int i=0;i<num_result;i++){
                rs.next();
                resp[i][0]=rs.getInt("idInventario");
                resp[i][1]=rs.getString("codigobarra");
                resp[i][2]=rs.getString("descripcion");
                resp[i][3]=rs.getDouble("cantidad");
                resp[i][4]=rs.getDouble("preciocompra");
                resp[i][5]=rs.getDouble("precioventa");                
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            resp=null;
        } 
        
        return resp;
    }
    
    public boolean in_inventario( String codigo, 
            String descripcion, double cantidad, 
            double preciocompra, double precioventa)//Pendiente ingresar la huella XD
    {    
      boolean sucessfull=false;
        try {
            Connection c=generar_conexionpv.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("INSERT INTO Inventario VALUES (?,?,?,?,?,?)"); 
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setString(2,codigo);
            st.setString(3,descripcion);
            st.setDouble(4, cantidad);
            st.setDouble(5,preciocompra);
            st.setDouble(6, precioventa);
                        
            if(st.executeUpdate()==1){
                System.out.println("Producto insertado correctamente");     
                sucessfull=true;				         
            }
         }
    catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        
        return sucessfull;   
    }
    
   

    public boolean in_corte( Date inicioperiodo, 
            Date finperiodo,double totalcaja ,double cantTot, String usuario)//Pendiente ingresar la huella XD
    {    
      boolean sucessfull=false;
        try {
            Connection c=generar_conexionpv.getConnection();
            PreparedStatement st;
            
            st = c.prepareStatement("INSERT INTO Corte VALUES (?,?,?,?,?,?)"); 
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setTimestamp(2,new java.sql.Timestamp(inicioperiodo.getTime()));
            st.setTimestamp(3,new java.sql.Timestamp(finperiodo.getTime()));
            st.setDouble(4,totalcaja);
            st.setDouble(5,cantTot);
            st.setString(6,usuario);
            if(st.executeUpdate()==1){
                System.out.println("Corte insertado correctamente");     
                sucessfull=true;				         
            }
         }
    catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        
        return sucessfull;   
    }

    public boolean in_movimiento( String codigoprod, 
            String tipomov, double cantidadven, 
            double preciotot,String usuario, Date fecha )//Pendiente ingresar la huella XD
    {    
      boolean sucessfull=false;
        try {
            Connection c=generar_conexionpv.getConnection();
            PreparedStatement st;
            System.out.println(fecha);
            //FileInputStream fila= new FileInputStream(imagen);
            st = c.prepareStatement("INSERT INTO Movimiento VALUES (?,?,?,?,?,?,?)"); 
            st.setObject(1, null);//idmovimiento,----Autoincrementado          
            st.setString(2, codigoprod);
            st.setString(3, tipomov);
            st.setDouble(4, cantidadven);
            st.setDouble(5, preciotot);
            st.setString(6, usuario);
            st.setTimestamp(7, new java.sql.Timestamp(fecha.getTime()));
            
            if(st.executeUpdate()==1){
                System.out.println("Movimiento insertado correctamente");     
                sucessfull=true;				         
            }
         }
    catch (Exception ex) {
            ex.printStackTrace();  //capturas alguna excepcion de la base de datos
        }
        
        return sucessfull;   
    }

     public Object[][] get_inventario(Object dato, String criterio){
        Object[][] resp=null;
        try{
            Connection c=generar_conexionpv.getConnection();
            Statement s = c.createStatement(); 
            ResultSet rs=null;
            switch(criterio){
                case "idInventario":
                    rs= s.executeQuery ("select * from Inventario where "+criterio+" = "+dato.toString());
                    break;
                case "codigobarra":
                    rs= s.executeQuery ("select * from Inventario where "+criterio+" = '"+dato.toString()+"'");
                    break;
                case "descripcion":
                    rs= s.executeQuery ("select * from Inventario where "+criterio+" = '"+dato.toString()+"'");
                    break;
               // case "email":
                //    rs= s.executeQuery ("select * from Cliente where "+criterio+" = '"+dato.toString()+"'");
                 //   break;
            }  

            
            int num_result=0;
            while(rs!=null&&rs.next()){
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            
            if(num_result!=0){
                rs.beforeFirst();
                resp=new Object[num_result][6];
            }
            for(int i=0;i<num_result;i++){
                rs.next();
                resp[i][0]=rs.getInt("idInventario");
                resp[i][1]=rs.getString("codigobarra");
                resp[i][2]=rs.getString("descripcion");
                resp[i][3]=rs.getDouble("cantidad");
                resp[i][4]=rs.getDouble("preciocompra");
                resp[i][5]=rs.getDouble("precioventa");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            resp=null;
        } 
        
        return resp;
    }

     /*public int ObtenerUltimoCorte(){
        int resp=-1;
        try{
        Connection c = generar_conexionpv.getConnection();
        Statement s = c.createStatement();
        ResultSet rs= s.executeQuery("select idCorte from Corte");
        rs.last();
        
        resp=rs.getInt("idCorte");
        }
        catch(SQLException ex){
        ex.printStackTrace();
            resp=-1;
        }
        return resp;
    }*/
    
    public Object[][] get_corte(Object dato, String criterio){
        Object[][] resp=null;
        try{
            Connection c=generar_conexionpv.getConnection();
            Statement s = c.createStatement(); 
            ResultSet rs=null;
            switch(criterio){
                /*case "fecha":
                    rs= s.executeQuery ("select * from Corte where "+ new java.sql.Date(((java.util.Date) dato).getTime())+"> inicioperiodo and "+new java.sql.Date(((java.util.Date)  dato).getTime())+" < finperiodo");
                    break;*/
                case "fecha":
                    Object[] aux=(Object[]) dato;
                    PreparedStatement pstmt = (PreparedStatement) c.prepareStatement("SELECT * FROM Corte WHERE fecha >= ? AND fecha <= ?");
                    pstmt.setDate(1,new java.sql.Date( ((Date)aux[0]).getTime()  ));
                    pstmt.setDate(2,new java.sql.Date( ((Date)aux[1]).getTime()  ));
                    rs= pstmt.executeQuery();
                break;
                
            }  

            
            int num_result=0;
            while(rs!=null&&rs.next()){
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if(num_result!=0){
                resp=new Object[num_result][6];
            }
            for(int i=0;i<num_result;i++){
                rs.next();
                resp[i][0]=rs.getInt("idCorte");
                resp[i][1]=rs.getDate("inicioperiodo");
                resp[i][2]=rs.getDate("finperiodo");
                resp[i][3]=rs.getDouble("totalcaja");
                resp[i][4]=rs.getDouble("cantidadTot");
                resp[i][5]=rs.getString("usuario");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            resp=null;
        } 
        return resp;
    }
    
    public Object[][] get_corte2(String nombre, String criterio){
        Object[][] resp=null;
        try{
            Connection c=generar_conexionpv.getConnection();
            Statement s = c.createStatement(); 
            ResultSet rs=null;
            switch(criterio){
                case "usuario":
                    rs=s.executeQuery ("select * from Corte where "+criterio+" ='"+nombre+"'");
                    
                    //rs.last();
                    break;
            }  

            
            int num_result=0;
            while(rs!=null&&rs.next()){
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if(num_result!=0){
                resp=new Object[num_result][6];
            }
            for(int i=0;i<num_result;i++){
                rs.next();
                resp[i][0]=rs.getInt("idCorte");
                resp[i][1]=rs.getTimestamp("inicioperiodo");
                resp[i][2]=rs.getTimestamp("finperiodo");
                resp[i][3]=rs.getDouble("totalcaja");
                resp[i][4]=rs.getDouble("cantidadTot");
                resp[i][5]=rs.getString("usuario");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            resp=null;
        } 
        return resp;
    }
    

    public Object[][] getAll_Inventario(){
        Object[][] resp=null;
        try{
            Connection c=generar_conexionpv.getConnection();
            Statement s = c.createStatement(); 
            ResultSet rs = s.executeQuery ("select * from Inventario");
            int num_result=0;
            while(rs.next()){
                num_result++;
            }//cuenta cuantos resultados fueron obtenidos
            rs.beforeFirst();
            if(num_result!=0){
                resp=new Object[num_result][6];
            }
            for(int i=0;i<num_result;i++){
                rs.next();
                resp[i][0]=rs.getInt("idInventario");
                resp[i][1]=rs.getString("codigobarra");
                resp[i][2]=rs.getString("descripcion");
                resp[i][3]=rs.getDouble("cantidad");
                resp[i][4]=rs.getDouble("preciocompra");
                resp[i][5]=rs.getDouble("precioventa");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            resp=null;
        } 
        
        return resp;
    }

    public Object[][] get_movimientos(Object dato, String criterio){
        Object[][] resp=null;
        try{
            Connection c=generar_conexionpv.getConnection();
            Statement s = c.createStatement(); 
            ResultSet rs=null;
            switch(criterio){
                case "fecha":
                    Object[] aux=(Object[]) dato;
                    PreparedStatement pstmt = (PreparedStatement) c.prepareStatement("SELECT * FROM Movimiento WHERE fecha >= ? AND fecha <= ?");
                    pstmt.setDate(1,new java.sql.Date( ((Date)aux[0]).getTime()  ));
                    pstmt.setDate(2,new java.sql.Date( ((Date)aux[1]).getTime()  ));
                    rs= pstmt.executeQuery();
                    break;
                case "usuario":
                    rs=s.executeQuery("select * from Movimiento where "+criterio+" = '"+dato.toString()+"'");
                    
                    break;
            }  

            
            int num_result=0;
            while(rs!=null&&rs.next()){
                num_result++;
            }
          
            rs.beforeFirst();
            if(num_result!=0){
                resp=new Object[num_result][7];
            }
            for(int i=0;i<num_result;i++){
                rs.next();
                resp[i][0]=rs.getInt("idMovimiento");
                resp[i][1]=rs.getString("codigoproducto");
                resp[i][2]=rs.getString("tipomov");
                resp[i][3]=rs.getDouble("cantidadvendida");
                resp[i][4]=rs.getDouble("preciototal");
                resp[i][5]=rs.getString("usuario");
                resp[i][6]=rs.getTimestamp("fecha");                
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            resp=null;
        } 
        return resp;
    }    
    
    public boolean actualizar(String tabla,Object[] datos){
        String inventario[]={"idInventario","codigobarra","descripcion","cantidad","preciocompra","precioventa"};
        
        boolean sucessfull=false;
        try{
            //System.out.println(""+datos[][]);
            Connection c=Utilidades.generar_conexionpv.getConnection();
            String  upd = "update "+tabla+" set ";
            switch(tabla){
                
                    case "Inventario":
                      for(int i=0;i<inventario.length;i++){
                        upd=upd+" "+inventario[i]+" = ?,"; }        
                      upd=upd.substring(0, upd.length()-1);
                        upd=upd+" where "+inventario[0]+" = "+ com(datos[0]);
                       
                        break;
                    
                }
            
            PreparedStatement preparedStmt = c.prepareStatement(upd);
            System.out.println(upd);
            for(int i=0;i<datos.length;i++){
                
                if(datos[i].getClass().equals(String.class)){
                    preparedStmt.setString   (i+1,(String) datos[i]);
                }
                if(datos[i].getClass().equals(Double.class)){
                    preparedStmt.setDouble   (i+1,(Double) datos[i]);
                }
                if(datos[i].getClass().equals(Integer.class)){
                     preparedStmt.setInt   (i+1,(Integer) datos[i]);
                }
                if(datos[i].getClass().equals(java.util.Date.class)){
                    java.util.Date aux=(java.util.Date)datos[i];
                    System.out.println("entro a la fehca"+aux.toLocaleString());
                    preparedStmt.setDate   (i+1,new java.sql.Date(aux.getTime()));
                }
                
                
                if(datos[i].getClass().equals(ImageSerializable.class)){
                    preparedStmt.setObject(2,(ImageSerializable)datos[1]);
                
                }
                
            }
            //No le entendi XD
            
            // execute the java preparedstatement
            if(preparedStmt.executeUpdate()==1){
                System.out.println("Actualizado correctamente: "+tabla+">>"+datos[0]);
               sucessfull=true;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            sucessfull=false;
        }
        return sucessfull;
    }
  
    public String com(Object dato){
        if(dato.getClass().equals(String.class)){
            return "'"+dato.toString()+"'";
        }
        else{
            return dato.toString();
        }
    }
}

