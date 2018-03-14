/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Jose Miguel Hernandez Garcia
 */
public abstract class AnotacionHC {
    private Date fecha;
    private String comentario;

    abstract ArrayList<String> obtenerDatos();
}