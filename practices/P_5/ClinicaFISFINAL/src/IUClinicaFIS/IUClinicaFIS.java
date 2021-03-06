/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IUClinicaFIS;

import clinicafis.ClinicaFIS;
import clinicafis.Medico;
import clinicafis.Paciente;
import clinicafis.Diagnostico;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.Date;


/**
 *
 * @author ana anaya
 */
public class IUClinicaFIS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ClinicaFIS unaClinica = ClinicaFIS.getInstance();

        // Definir la variable que nos permite leer String desde teclado
        final Scanner in = new Scanner(System.in);
        int opcion = 0;
        do{
            try{ // tratamiento de las excepciones. Bloque try en el que se puede producir una excepción y la capturamos

                 //Terminar de diseñar el menú (usando System.out.println(...)) con las opciones que faltan
		 // Podéis hacer vuestros propios diseños de interfaz, esta es la interfaz mínima que tenéis que entregar
                 System.out.println("\n\n********************** OPCIONES ********************\n");

                 System.out.println("===== GESTIÓN DE MEDICO ===== \n" +
                                    "\t10. Nuevo Médico \n" +
                                    "\t11. Definir agenda \n" +
                                    "\t12. Consultar agenda \n" +
                                    "\t13. Ver todos los médicos \n");

                  System.out.println("===== GESTIÓN DE PACIENTES =====\n" +
                                     "\t20. Nuevo paciente \n" +
                                     "\t21. Consultar datos personales del paciente \n" +
                                     "\t22. Eliminar Paciente\n" +
                                     "\t23. Ver todos los pacientes\n");

                System.out.println("===== GESTIÓN DE CITAS =====  \n" +
                                    "\t30. Ver todas las posibles citas \n" +
                                    "\t31. Pedir una cita \n" +
                                    "\t32. Anular una cita \n" +
                                    "\t33. Consultar todas las citas pendientes\n");

               System.out.println("===== GESTIÓN DE CONSULTAS MEDICAS =====  \n" +
                                    "\t40. Terminar Consulta \n" +
                                    "\t41. Diagnosticar paciente \n" +
                                    "\t42. Consultar datos clinicos paciente\n");

                System.out.println("\n*******************************************************");

                System.out.println("\t0. TERMINAR");
		System.out.println("\n*******************************************************");

                // Lectura de un int, para darle valor a opcion.
                opcion =Integer.parseInt(in.nextLine());

                // Estructura switch con todas las opciones de menú. Algunos de ellos ya lo tenéis hecho
                // Tenéis que terminar las opciones que están incompletas y las que no están hechas
                switch(opcion){
                    case 10: //incluir un nuevo usuario en el sistema

                        System.out.print("Nombre del médico: ");
                        String nombre=in.nextLine();

                        System.out.print("dni del médico: ");
                        String dni= in.nextLine();

                        System.out.print("Especialidad: ");
                        String especialidad= in.nextLine();

                        unaClinica.nuevoMedico(dni, nombre, especialidad);
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 11:/*Definir agenda */
                        System.out.print("dni del médico: ");
                        dni= in.nextLine();
                        System.out.print("A partir de cuántos días? : ");
                        int numeroDias = Integer.parseInt(in.nextLine());
                        unaClinica.definirAgenda(dni,numeroDias);
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 12:/*Consultar agenda*/
                        System.out.print("dni del médico: ");
                        dni= in.nextLine();
                        System.out.print("para qué día \n \t1 = hoy \n \t2 = mañana \n \t .... \n \t-1 = todos los días \n ");
                        numeroDias = Integer.parseInt(in.nextLine());

                        System.out.println(unaClinica.consultarAgenda(dni,numeroDias));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();

                    break;
                    case 13:/*Ver todos los médicos */
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("\t\tdni\t\tnombre\t\tespecialidad");
                        System.out.println("-------------------------------------------------------------");
                        System.out.println(unaClinica.todosLosMedico());
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 20: /*Nuevo paciente  */
                        System.out.println("-------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        dni= in.nextLine();
                        System.out.print("nombre del paciente: ");
                        nombre = in.nextLine();
                        System.out.print("numero de tarjeta del paciente: ");
                        String numeroTarjeta = in.nextLine();
                        System.out.print("telefono del paciente: ");
                        String telefono = in.nextLine();

                        // System.out.println(unaClinica.crearPaciente(dni,nombre,numeroTarjeta,telefono));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();

                    break;

                    case 21: /* Consultar datos clinicos del paciente */
                        System.out.println("--------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        dni = in.nextLine();
                        System.out.println(unaClinica.consultarDatosClinicos(dni));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();

                    break;

                    case 22: /* Eliminar paciente  */
                        System.out.println("--------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        dni = in.nextLine();
                        System.out.println(unaClinica.eliminarPaciente(dni));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();

                    break;

                    case 23: /* Ver todos los pacientes */
                        System.out.println("--------------------------------------------------------------");
                        for(int i = 0; i < unaClinica.misPacientes.size(); i++)
                            System.out.println(unaClinica.misPacientes.get(i));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();

                    break;
                    case 30: /* Ver posibles citas */
                        System.out.println("--------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        String dniP = in.nextLine();
                        System.out.print("dni del medico: ");
                        String dniM = in.nextLine();
                        System.out.println(unaClinica.obtenerPosiblesCitas(dniM, dniP));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 31: /* Pedir una cita */
                        System.out.println("--------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        dniP = in.nextLine();
                        System.out.print("dni del medico: ");
                        dniM = in.nextLine();
                        Date fecha = new Date();
                        System.out.println(unaClinica.pedirCita(dniP, dniM, fecha));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 32: /* Anular cita */
                        System.out.print("dni del paciente: ");
                        dniP = in.nextLine();
                        System.out.println(unaClinica.consultarCitas(dniP));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 33: /*  Consultar todas las citas pendientes  */
                         System.out.println("--------------------------------------------------------------");
                        for(int i = 0; i < unaClinica.misCitas.size(); i++)
                            System.out.println(unaClinica.misCitas.get(i));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;
                    case 40: /* Terminar consulta */
                        System.out.println("--------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        dniP = in.nextLine();
                        System.out.print("dni del medico: ");
                        dniM = in.nextLine();
                        // System.out.println(unaClinica.terminarConsulta(dniM, dniP));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 41: /* Hacer un diagnóstico */
                        System.out.println("--------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        dniP = in.nextLine();
                        System.out.print("dni del medico: ");
                        dniM = in.nextLine();
                        System.out.print("codigo de diagnostico: ");
                        String codDiagnostico = in.nextLine();
                        System.out.print("telefono del paciente: ");
                        telefono = in.nextLine();
                        System.out.println(unaClinica.diagnosticar(dniP, codDiagnostico, telefono, dniM));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 42: /*  Consultar datos clinicos pendientes  */
                        System.out.println("--------------------------------------------------------------");
                        System.out.print("dni del paciente: ");
                        dniP = in.nextLine();
                        System.out.println(unaClinica.consultarDatosClinicos(dniP));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 0: /* terminar */
                    break;

                    default:
                        System.out.println("OPCION NO VÁLIDA");
                    break;
                }
//
            }catch(Exception ex){ // captura de la excepción
                System.err.println("se ha producido la siguiente excepcion: "+ ex.getMessage());
            }
        }while(opcion !=0);
        System.exit(0);
    }
    }

