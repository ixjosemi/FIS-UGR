/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Jose Miguel Hernandez Garcia
 */

public class Paciente {
    private String dni;
    private String nombre;
    private String numeroTarjeta;
    private String telefono;
    private boolean activo;
    private HistoriaClinica hc;

    // Constructor
    Paciente(String dni, String nombre, String numeroTarjeta, String telefono){
        this.dni = dni;
        this.nombre = nombre;
        this.numeroTarjeta = numeroTarjeta;
        this.telefono = telefono;
    }

    // Map en el que se guardan las citas, la key es la fecha
    private Map<String, Cita> misCitas = new HashMap();

    // Redefinicion de toString
    @Override
    public String toString() {
        return "Paciente{" + "dni=" + dni + ", nombre=" + nombre + ", numeroTarjeta="
                + numeroTarjeta + ", telefono=" + telefono + ", activo=" + activo + '}';
    }

    // devuelve la historia clinica
    HistoriaClinica getHC(){
        return this.hc;
    }

    // devuelve el nombre del paciente
    String getNombre(){
        return this.nombre;
    }

    // devuelve el numero de tarjeta del paciente
    String getNumeroTarjeta(){
        return this.numeroTarjeta;
    }

    // Metodo para buscar la cita de un paciente
    private Cita buscarCita(String idCita){
        return misCitas.get(idCita);
    }

    // Metodo que elimina una cita del mapa
    private void eliminaCita(Cita cita){
        misCitas.remove(cita.obtenerFecha());
    }

    // anula una cita del paciente
    List<String> anularCita(Calendar fecha){

        Cita cita = buscarCita(fecha.toString());

        String datosAnulacion = cita.anularCita();
        if(cita.obtenerEstado() == TipoEstado.ATENDIDA)System.out.println("LA CITA NO PUEDE SER CANCELADA"); 
        cita.liberarCita();
        String nombreMedico = cita.getMedico().obtenerNombre();
        datosAnulacion = datosAnulacion + fecha + nombreMedico;

        List<String> datosConfirmacion = new ArrayList();

        datosConfirmacion.add(nombre);
        datosConfirmacion.add(datosAnulacion);

        return datosConfirmacion;
    }

    // incluir cita
    void incluirCita(Cita cita){
        Date fecha = cita.obtenerFecha();

        misCitas.put(fecha.toString(), cita);
    }

    // incluir historia clinica
    void incluirHistoria(HistoriaClinica h){
        this.hc = h;
    }

    // buscar una cita
    Cita buscarCita(Date fecha){
        return misCitas.get(fecha.toString());
    }

    // obtiene los datos de un paciente
    String obtenerDatos(){
        String datos = dni + nombre + numeroTarjeta + telefono;

        return datos;
    }

    // obtiene los datos clinicos de un paciente
    List<String> obtenerDatosClinicos(){
        List<String> infoPaciente= new ArrayList();
        List <String> datosHistoria = new ArrayList();
        List <String> datosAnotacion = new ArrayList();

        ArrayList <AnotacionHC> anotaciones = new ArrayList();
        AnotacionHC anotacion;
        Medico medico = misCitas.get(0).getMedico();
        HistoriaClinica hc = this.hc;
        String nombreMedico;
        Date fecha = new Date();

        infoPaciente.add(nombre);

        datosHistoria = hc.obtenerDatosHistoriaClinica();
        datosHistoria.add(hc.toString());

        for(int i = 0; i < anotaciones.size(); i++){
            datosAnotacion = anotaciones.get(i).obtenerDatos();
            nombreMedico = medico.obtenerNombre();
            datosAnotacion.add(fecha.toString());
            datosAnotacion.add(hc.toString());
            datosAnotacion.add(nombreMedico);

            datosHistoria.addAll(datosAnotacion);
            infoPaciente.addAll(datosHistoria);
        }
        return infoPaciente;
    }

    // modifica el activo
    void modificarActivo(boolean activo){
        this.activo = activo;
    }

    // baja clinica
    List<String> bajaClinica(){
       List<String> salida = new ArrayList();

       for(int i = 0; i < misCitas.size(); i++){
           misCitas.get(i).liberarCita();
           misCitas.get(i).modificarEstado(TipoEstado.LIBRE);
       }

       modificarActivo(false);

       salida.add(dni);
       salida.add(nombre);

       return salida;
    }

    // consulta todas las citas de un paciente
    List <String> consultarCitas(){
        List <String> salida = new ArrayList();
        String datosCita;
        Cita cita;
        Medico medico;
        String nombreMedico;
        String especialidad;

        for(int i = 0; i < misCitas.size(); i++)

            if(misCitas.get(i).obtenerEstado() == TipoEstado.PENDIENTE){
                cita = misCitas.get(i);
                medico = misCitas.get(i).getMedico();
                datosCita = cita.obtenerDatos();
                nombreMedico = medico.obtenerNombre();
                especialidad = medico.obtenerEspecialidad();
                datosCita = datosCita + nombreMedico + especialidad;
                salida.add(datosCita);
            }
        return salida;
    }

    // seleccionar citas posteriores
    List <Cita> seleccionarCitasPosteriores(Date fecha){
        List <Cita> citas = new ArrayList();
        Cita cita;

        for(int i = 0; i < citas.size(); i++){
            if(misCitas.get(i).obtenerFecha().compareTo(fecha) > 0){
                cita = citas.get(i);
                citas.add(cita);
            }
        }
        return citas;
    }

    // comprobar si el medico es el que debe ser
    boolean esTuMedico(Medico medico){
        boolean es_tu_medico = false;

        for(int i = 0; i < misCitas.size() && !es_tu_medico; i++){
            if(misCitas.get(i).getMedico() == medico)
                es_tu_medico = true;
        }

        return es_tu_medico;
    }

    // diagnostico del paciente
    List <String> diagnostico(String codDiagnostico, String textoExplicativo, String dniM){
        List <String> infoDiagnostico = new ArrayList();
        Medico medico = misCitas.get(0).getMedico();
        HistoriaClinica hc = new HistoriaClinica();
        Date fecha = new Date();

        infoDiagnostico.add(getNombre());

        List <String> datosDiagnostico;
        Diagnostico d = new Diagnostico(codDiagnostico, medico, textoExplicativo);

        datosDiagnostico = d.obtenerDatos();

        datosDiagnostico.add(medico.obtenerNombre());
        datosDiagnostico.add(fecha.toString());
        datosDiagnostico.add(textoExplicativo);

        infoDiagnostico.addAll(datosDiagnostico);

        return infoDiagnostico;
    }

    // comprobar si tienes citas
    boolean tienesCitas(Medico medico){
        boolean tienes_citas = false;
        Date fecha = new Date();

        List <Cita> citas = seleccionarCitasPosteriores(fecha);

        for(int i = 0; i < citas.size(); i++){

            if(!tienes_citas)
                tienes_citas = citas.get(i).getPaciente().esTuMedico(medico);
        }

        return tienes_citas;
    }
}
