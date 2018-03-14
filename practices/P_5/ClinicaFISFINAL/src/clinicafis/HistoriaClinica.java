/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Jose Miguel Hernandez Garcia
 */
public class HistoriaClinica {

    public static int NumeroDeHistorias = 0;
    private Date fechaApertura;
    private int numeroHistoria;
    private ArrayList<Diagnostico> diagnosticos;

    // constructor
    HistoriaClinica(Date fechaApertura, int numeroHistoria){
        this.fechaApertura = fechaApertura;
        this.numeroHistoria = numeroHistoria;
    }
    
    HistoriaClinica(){}

    // redefinicion de toString

    @Override
    public String toString() {
        return "HistoriaClinica{" + "fechaApertura=" + fechaApertura + ", numeroHistoria=" + numeroHistoria + '}';
    }
    
    // aumenta en uno el numero de historias
    void aumentaNumHistoria(){
        NumeroDeHistorias++;
    }   
    
    // obtiene los datos de la historia clinica
    List <String> obtenerDatosHistoriaClinica(){
        List <String> salida = new ArrayList();

        String datos = "NumeroHistoria: " + Integer.toString(numeroHistoria) + 
                "FechaApertura: " + fechaApertura.getTime();
        
        salida.add(datos);
        
        return salida;
    }
    
    // crea un nuevo diagnostico
    List <String> nuevoDiagnostico (String codDiagnostico, String textoExplicativo, Medico medico){
        List <String> salida = new ArrayList();
        
        String datos = codDiagnostico + textoExplicativo + medico.obtenerNombre();
        
        salida.add(datos);
        
        return salida;
    }
}
