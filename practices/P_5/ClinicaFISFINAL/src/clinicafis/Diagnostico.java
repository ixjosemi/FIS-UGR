/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.util.ArrayList;

/**
 *
 * @author Jose Miguel Hernandez Garcia
 */
public class Diagnostico extends AnotacionHC {
    private String codDiagnostico;
    private Medico medico;
    private String textoExplicativo;

    @Override
    ArrayList<String> obtenerDatos() {
           ArrayList<String> salida = new ArrayList();
        String datos = "codDiagnostico: " + codDiagnostico + 
                "Texto: " + textoExplicativo;
        
        salida.add(datos);
        
        return salida;
    }

    // Constructor
    Diagnostico(String cod, Medico med, String texto){
        this.codDiagnostico = cod;
        this.medico = med;
        this.textoExplicativo = texto;
    }
}
