/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ErickFrancisco
 */
public class Propiedades {
     String ruta = "imagenes\\archivo.txt";
     File archivo = new File(ruta);
     public String direccion;
     public boolean minimizar;
     public boolean maximizar;
     
    public void Crear(){
        try{
               
        if(!archivo.exists()){
            archivo.createNewFile();
        FileWriter fr = new FileWriter(archivo,true);
        
        fr.write("/Imagenes/Nike.jpg\r\n");
        
        fr.write("true\r\n");
        fr.write("true\r\n");
        fr.close();
        }
        }
        catch(IOException ex){
            System.out.println(ex);
        }
           
    
    }
    
    public void leer(){
        try{
        String Text="";
        FileReader read = new FileReader(archivo);
        BufferedReader bf = new BufferedReader(read);
         direccion = bf.readLine();
         minimizar = Boolean.parseBoolean(bf.readLine());
         maximizar = Boolean.parseBoolean(bf.readLine());
        }
        catch(IOException ex){
            
        }
    }
    
    public void Escribir(String direccion, String minimizar, String maximizar){
        String direc, minim, maxi;
        direc= direccion;
        minim=minimizar;
        maxi=maximizar;
        try{
        FileWriter write = new FileWriter(archivo);
        write.write(direc+"\r\n");
        write.write(minim+"\r\n");
        write.write(maxi+"\r\n");
        write.close();
        }
        catch(Exception ex){
            
        }
    }
}
