/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageSerializable implements java.io.Serializable{
    private byte[] imageInByte;
        
    public ImageSerializable(Image imag){
        if(imag==null){imageInByte=null;}//Do nothing
        else{
            try{
                BufferedImage original=new BufferedImage(imag.getWidth(null),imag.getHeight(null),BufferedImage.TYPE_INT_RGB);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                Graphics2D g2 = original.createGraphics();
                g2.drawImage(imag, 0, 0, null);
                ImageIO.write(original,"jpg",baos);
                imageInByte=baos.toByteArray();
            }
            catch(IOException ex){
                Logger.getLogger(ImageSerializable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ImageSerializable(File fnew){
        try {
            BufferedImage originalImage=ImageIO.read(fnew);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ImageIO.write(originalImage, "bmp", baos );
            imageInByte=baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ImageSerializable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Image getImage(int w,int h){
       Image imagen=null;
        try {
            ByteArrayInputStream bais=new ByteArrayInputStream(imageInByte);
            // ois = new ObjectInputStream(bais);
            BufferedImage read = ImageIO.read(bais);
            imagen=read.getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT);
        } 
        catch (IOException ex ) {
            //Logger.getLogger(ImageSerializable.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        catch(NullPointerException ex){System.out.println("no picture");return null;}
        return imagen;
    }
    public Image getImage(){
       Image imagen=null;
        try {
            ByteArrayInputStream bais=new ByteArrayInputStream(imageInByte);
            // ois = new ObjectInputStream(bais);
            BufferedImage read = ImageIO.read(bais);
            imagen=read.getScaledInstance(read.getWidth(), read.getHeight(), BufferedImage.SCALE_DEFAULT);
        } 
        catch (IOException ex ) {
            //Logger.getLogger(ImageSerializable.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        catch(NullPointerException ex){System.out.println("no picture");return null;}
        return imagen;
    }
   
}
