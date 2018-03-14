/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ana anaya
 */
class Cita {
    private static int TiempoDeCita = 10; // variable de ámbito de clase que indica el tiempo que tiene asignado cada cita
    private Calendar fecha;
    private TipoEstado estado = TipoEstado.LIBRE;
    private Medico miMedico;
    private Paciente paciente;

    // A partir del número de cita de esa fecha se calcula la hora de la cita
    // a cada cita se le reservan 10 minutos
    Cita(Calendar fecha, int numeroCita, Medico medico) {
        this.fecha =(Calendar) fecha.clone();
        this.fecha.set(Calendar.HOUR,9);
        this.fecha.set(Calendar.MINUTE,0);
        this.fecha.add(fecha.MINUTE,numeroCita*TiempoDeCita);
        this.miMedico = medico;
    }

    // constructor vacio
    public Cita(){}

    // Map en el que se guardan las citas, la key es la fecha
    Map<String, Cita> misCitas = new HashMap();

    // Redefinición de toString, cuando la cita tenga asignado el Paciente
    // tendrás que quitar los comentarios que hay en este código
    @Override
    public String toString()
    {
        SimpleDateFormat hmsf = new SimpleDateFormat("hh:mm");
        String salida =  "hora: " + hmsf.format(fecha.getTime()) +" (" + estado.toString()+ ")";
//          if (miPaciente != null)
//              salida += "Paciente: " + miPaciente.getNombre();
        salida +=  "\n";
        return salida;
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

    // metodo que devuelve el medico
    Medico getMedico(){
        return this.miMedico;
    }

    // metodo que devuelve el paciente
    Paciente getPaciente(){
        return this.paciente;
    }

    // metodo que devuelve la fecha de la cita
    Date obtenerFecha(){
        return fecha.getTime();
    }

    // metodo que devuelve los datos de un medico
    String obtenerDatos(){
        return miMedico.toString();
    }

    // metodo para establecer la fecha
    void setFecha(Calendar fecha){
        this.fecha = fecha;
    }

    // obtener el estado de la cita
    TipoEstado obtenerEstado(){
        return this.estado;
    }

    // asigna un paciente a una cita
    void asignarPaciente(Paciente paciente){
        Cita cita = new Cita();
        estado = TipoEstado.PENDIENTE;
        paciente.incluirCita(cita);
    }

    // comprueba si el paciente es el paciente del medico
    boolean esTuPaciente(Paciente paciente){
        boolean es_paciente = false;

        for(int i = 0; i < misCitas.size() && !es_paciente; i++){
            if(misCitas.get(i).getPaciente() == paciente)
                es_paciente = true;
        }

        return es_paciente;
    }

    // metodo para liberar una cita
    void liberarCita(){
        this.estado = TipoEstado.LIBRE;
    }

    // metodo para anular una cita
    String anularCita(){
        this.estado = TipoEstado.LIBRE;
        String anulada ="anulada";
        return anulada;
    }

    // modifica el estado de una cita
    void modificarEstado(TipoEstado estado){
        this.estado = estado;
    }
}
