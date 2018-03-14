/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ana anaya
 */

public class ClinicaFIS {

    // patrón singleton
    private static ClinicaFIS instancia = new ClinicaFIS();
    public static ClinicaFIS getInstance(){
        return instancia;
    }

    // Map en el que se guardan los médicos, la key es el dni del médico
    private Map<String, Medico> misMedicos = new HashMap();

    // Map en el que se guardan los pacientes, la key es el dni del paciente
    public Map<String, Paciente> misPacientes = new HashMap();

    // Map en el que se guardan las citas, la key es la fecha
    public Map<String, Cita> misCitas = new HashMap();

    public void nuevoMedico(String dni,String nombre,String especialidad) throws Exception{
        if(existeMedico(dni)) throw new Exception("YA EXISTE UN MÉDICO CON ESE DNI");
        misMedicos.put(dni, new Medico(dni,nombre,especialidad));
    }

    private boolean existeMedico(String dni){
        return misMedicos.containsKey(dni);
    }

    public void definirAgenda(String dniM,int aPartirDe) throws Exception {
        Medico unMedico = buscarMedico(dniM);
        unMedico.definirAgenda(aPartirDe);
    }

    private Medico buscarMedico(String dni) throws Exception {
       if(!existeMedico(dni)) throw new  Exception("NO EXISTE EL MÉDICO CON ESE DNI");
       return misMedicos.get(dni);
    }

    public List<String> consultarAgenda(String dniM, int numeroDias) throws Exception {
        Medico unMedico = buscarMedico(dniM);
        return unMedico.obtenerCitas(numeroDias);
    }

    public List<String> todosLosMedico(){
        List<String> salida = new ArrayList();
        for (Map.Entry<String, Medico> entry : misMedicos.entrySet()){
            Medico unMedico = entry.getValue();
            salida.add(unMedico.toString());
        }
        return salida;
    }

    // crear un nuevo paciente
    public void crearPaciente(String dni, String nombre, String numTarjeta, String telefono) throws Exception{
        boolean existe = existePaciente(dni);

        if(existe)
            throw new Exception("EL PACIENTE YA EXISTE");

        boolean existePacienteTarjeta = existePacienteConTarjeta(numTarjeta);

        if(existePacienteTarjeta)
            throw new Exception("YA EXISTE UN PACIENTE CON EL MISMO NUMERO DE TARJETA");

        Paciente paciente = new Paciente(dni, nombre, numTarjeta, telefono);

        Calendar fecha = Calendar.getInstance();
        HistoriaClinica hc = new HistoriaClinica(fecha.getTime(), HistoriaClinica.NumeroDeHistorias++);
        paciente.incluirHistoria(hc);

        misPacientes.put(dni, paciente);
    }

    // comprobar si existe el paciente identificado por dni
    boolean existePaciente(String dni){
        return misPacientes.containsKey(dni);
    }

    // comprobar si existe el paciente identificado por numeroTarjeta
    boolean existePacienteConTarjeta(String numeroTarjeta){
        boolean existe = false;

        return existe;
    }

    // busca un paciente
    private Paciente buscarPaciente(String dni) throws Exception{
        if(!existePaciente(dni)) throw new Exception("NO EXISTE EL PACIENTE");

        return misPacientes.get(dni);
    }

    // consultar informacion de un paciente
    public List <String> consultarPaciente(String dni) throws Exception{
        if(!existePaciente(dni)) throw new Exception("NO EXISTE EL PACIENTE");

        Paciente paciente = buscarPaciente(dni);
        String infoPaciente = paciente.obtenerDatos();

        List <String> salida = new ArrayList();
        salida.add(infoPaciente);

        return salida;
    }

    // consultar datos clinicos de un paciente
    public List <String> consultarDatosClinicos(String dni) throws Exception{
        if(!existePaciente(dni)) throw new Exception("NO EXISTE EL PACIENTE");

        Paciente paciente = buscarPaciente(dni);

        List <String> infoClinicaPaciente = new ArrayList();

        infoClinicaPaciente = paciente.obtenerDatosClinicos();

        return infoClinicaPaciente;
    }

    // eliminar un paciente
    public List <String> eliminarPaciente(String dni) throws Exception{
        if(!existePaciente(dni)) throw new Exception("NO EXISTE EL PACIENTE");

        Paciente paciente = buscarPaciente(dni);
        List <String> infoBajaClinica = paciente.bajaClinica();
        Cita cita = misCitas.remove(dni);

        infoBajaClinica = paciente.bajaClinica();

        return infoBajaClinica;
    }

    // anular cita de un paciente
    public List <String> anularCita(String dni, Calendar fecha) throws Exception{
        if(!existePaciente(dni)) throw new Exception("NO EXISTE EL PACIENTE");

        Paciente paciente = buscarPaciente(dni);

        List <String> datosConfirmacion = paciente.anularCita(fecha);
        Cita cita = paciente.buscarCita(fecha.getTime());
        String datosAnulacion = anularCita(dni, fecha).get(0);

        if(cita.obtenerEstado() == TipoEstado.ATENDIDA){
           cita.liberarCita();
        }

        String nombreMedico = cita.getMedico().obtenerNombre();

        datosConfirmacion.add(datosAnulacion);

        return datosConfirmacion;
    }

    // consultar citas
    public List <String> consultarCitas(String dni) throws Exception{
        if(!existePaciente(dni)) throw new Exception("NO EXISTE EL PACIENTE");

        List <String> listadoCitas = new ArrayList();
        Paciente paciente = buscarPaciente(dni);

        listadoCitas = paciente.consultarCitas();

        return listadoCitas;
    }

    // realizar un diagnostico
    public List <String> diagnosticar(String dniP, String codDiagnostico, String textoExplicativo, String dniM) throws Exception{
        if(!existePaciente(dniP)) throw new Exception("NO EXISTE EL PACIENTE");
        if(!existeMedico(dniM)) throw new Exception("NO EXISTE EL MEDICO");

        Medico medico = buscarMedico(dniM);
        Paciente paciente = buscarPaciente(dniP);

        List <String> infoDiagnostico = new ArrayList();

        infoDiagnostico = paciente.diagnostico(codDiagnostico, textoExplicativo, dniM);

        return infoDiagnostico;
    }

    // metodo para pasar de date a calendar
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    // obtener las posibles citas
    public List <Date> obtenerPosiblesCitas(String dniM, String dniP) throws Exception{
        if(!existeMedico(dniM)) throw new Exception("NO EXISTE EL MEDICO");
        if(!existePaciente(dniP)) throw new Exception("NO EXISTE EL PACIENTE");

        List <Date> listaPosiblesFechas = new ArrayList();
        Date fecha = new Date();

        Medico medico = buscarMedico(dniM);
        Paciente paciente = buscarPaciente(dniP);

        boolean tienesCita = paciente.tienesCitas(medico);

        List <Cita> citas = paciente.seleccionarCitasPosteriores(fecha);
        Cita cita;

        for (Cita cita1 : citas)
            tienesCita = paciente.esTuMedico(medico);

        if(!tienesCita){
            listaPosiblesFechas = medico.obtenerPosiblesCitas();
            citas = medico.seleccionarCitasPosteriores(toCalendar(fecha));
        }

        for(int i = 0; i < citas.size(); i++){
            if(citas.get(i).obtenerEstado() == TipoEstado.LIBRE){
                cita = citas.get(i);
                fecha = cita.obtenerFecha();
            }

            listaPosiblesFechas.add(fecha);
        }

        return listaPosiblesFechas;
    }

    // pide una cita
    public List <String> pedirCita(String dniP, String dniM, Date fecha) throws Exception{
        if(!existeMedico(dniM)) throw new Exception("NO EXISTE EL MEDICO");
        if(!existePaciente(dniP)) throw new Exception("NO EXISTE EL PACIENTE");

        List <String> infoCita = new ArrayList();

        Medico medico = buscarMedico(dniM);
        Paciente paciente = buscarPaciente(dniP);

        infoCita = medico.asignarCita(paciente, fecha);

        return infoCita;
    }

    //Terminar la consulta entre un medico y un paciente
    public void terminarConsulta(String dniM, String dniP) throws Exception{
        if(!existeMedico(dniM)) throw new Exception("NO EXISTE EL MEDICO");
        if(!existePaciente(dniP))  throw new Exception("NO EXISTE EL PACIENTE");
        Medico medico = buscarMedico(dniM);
        Paciente paciente = buscarPaciente(dniP);

        medico.terminarConsulta(paciente);
    }
}
