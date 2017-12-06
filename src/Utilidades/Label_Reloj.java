/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;

/**
 *
 * @author Erick
 */
public class Label_Reloj extends JLabel implements Runnable {

    private String meses[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiempre", "Octubre", "Noviembre", "Diciembre"};
    private String dia, mes, año, hora, minuto, segundo;
    Calendar calendario = new GregorianCalendar();

    Thread hilo;

    public Label_Reloj() {
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == hilo) {
            try {
                actualizar();
                int mes1;
                mes1 = Integer.valueOf(mes);
                setText("Fecha: " + dia + "/" + meses[mes1] + "/" + año + " Hora: " + hora + ":" + minuto + ":" + segundo);

                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }

        }
    }

    public void actualizar() {
        Date fechahoraactual = new Date();
        calendario.setTime(fechahoraactual);

        hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
        minuto = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
        dia = calendario.get(Calendar.DATE) > 9 ? "" + calendario.get(Calendar.DATE) : "0" + calendario.get(Calendar.DATE);
        mes = calendario.get(Calendar.MONTH) > 9 ? "" + calendario.get(Calendar.MONTH) : "0" + calendario.get(Calendar.MONTH);
        año = calendario.get(Calendar.YEAR) > 9 ? "" + calendario.get(Calendar.YEAR) : "0" + calendario.get(Calendar.YEAR);
    }
}
