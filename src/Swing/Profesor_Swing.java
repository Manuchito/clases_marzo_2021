package Swing;
import Exceptions.ServiceCursoNoExisteException;
import Exceptions.ServiceLegajoNoExsiteException;
import Main.PanelManager;
import Services.AlumnoServicio;
import Services.CursoServicio;
import Swing.Tablas.AlumnoTableModel;
import Swing.Tablas.CursoTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Profesor_Swing extends JPanel {
    private PanelManager panelManager;
    CursoServicio serv = new CursoServicio();
    AlumnoServicio servAlumno = new AlumnoServicio();
    CursoServicio servCurso = new CursoServicio();
    private CursoTableModel cursoTableModel;
    private JTable tablaCursosAlumno;
    private JScrollPane scrollPaneCursosAlumno;

    private AlumnoTableModel alumnoTableModel;
    private JTable tablaAlumnosCurso;
    private JScrollPane scrollPaneAlumnosCurso;


    public Profesor_Swing(PanelManager m){
        super();
        this.panelManager = m;
    }

    public void armarProfesorSwing() {
        //construct preComponents


        //construct components
        JLabel textAlumno = new JLabel("Legajo Alumno");
        JButton buttonBuscarAlumno = new JButton("Buscar");
        JLabel textCursosAlumno = new JLabel("Cursos del Alumno");
        JLabel textAlumnosCurso = new JLabel("Alumnos del Curso");
        JLabel textCurso = new JLabel("Codigo Curso");
        JTextField fieldAlumno = new JTextField(5);
        JTextField fieldCurso = new JTextField(5);
        JButton buttonBuscarCurso = new JButton("Buscar");
        JButton buttonCrear = new JButton("Crear Alumno");
        JButton buttonInscribir = new JButton("Inscribir Alumno");
        JButton buttonVolver = new JButton("Volver");
        JButton buttonCalificar = new JButton("Calificar Alumno");
        JButton buttonReporte = new JButton("Reporte Curso");

        alumnoTableModel = new AlumnoTableModel();
        tablaAlumnosCurso = new JTable(alumnoTableModel);
        scrollPaneAlumnosCurso = new JScrollPane(tablaAlumnosCurso);
        buttonBuscarCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    servCurso = new CursoServicio();
                    alumnoTableModel.setContenido(servCurso.listarAlumnosDelCurso(Integer.parseInt(fieldCurso.getText())));
                    alumnoTableModel.fireTableDataChanged();
                } catch (ServiceCursoNoExisteException serviceCursoNoExisteException) {
                    serviceCursoNoExisteException.printStackTrace();
                } catch (ServiceLegajoNoExsiteException serviceLegajoNoExsiteException) {
                    serviceLegajoNoExsiteException.printStackTrace();
                }
            }
        });

        cursoTableModel = new CursoTableModel();
        tablaCursosAlumno = new JTable(cursoTableModel);
        scrollPaneCursosAlumno = new JScrollPane(tablaCursosAlumno);

        buttonBuscarAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    servAlumno = new AlumnoServicio();
                    cursoTableModel.setContenido(servAlumno.listarCursosDelAlumno(Integer.parseInt(fieldAlumno.getText())));
                    cursoTableModel.fireTableDataChanged();
                } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                } catch (ServiceLegajoNoExsiteException serviceLegajoNoExsiteException) {
                    serviceLegajoNoExsiteException.printStackTrace();
                } catch (ServiceCursoNoExisteException serviceCursoNoExisteException) {
                    serviceCursoNoExisteException.printStackTrace();
                }
            }
        });


        buttonInscribir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPanelInscribirAlumno();
            }
        });

        buttonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPanelCreacionAlumno();
            }
        });

        buttonCalificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPanelCalificarAlumno();
            }
        });





        //adjust size and set layout
        setLayout (null);

        //add components
        add (scrollPaneCursosAlumno);
        add (scrollPaneAlumnosCurso);

        add (textAlumno);
        add (buttonBuscarAlumno);
        add (textCursosAlumno);
        add (textAlumnosCurso);
        add (textCurso);
        add (fieldAlumno);
        add (fieldCurso);
        add (buttonBuscarCurso);
        add (buttonCrear);
        add (buttonInscribir);
        add (buttonVolver);
        add (buttonCalificar);
        add (buttonReporte);

        //set component bounds (only needed by Absolute Positioning)

        scrollPaneCursosAlumno.setBounds (385, 90, 330, 115);
        scrollPaneAlumnosCurso.setBounds(385, 305, 330, 220);

        textAlumno.setBounds (385, 50, 100, 25);
        buttonBuscarAlumno.setBounds (625, 50, 90, 25);
        textCursosAlumno.setBounds (410, 10, 120, 25);
        textAlumnosCurso.setBounds (410, 225, 140, 25);
        textCurso.setBounds (385, 265, 100, 25);
        fieldAlumno.setBounds (475, 50, 135, 25);
        fieldCurso.setBounds (475, 265, 135, 25);
        buttonBuscarCurso.setBounds (625, 265, 90, 25);
        buttonCrear.setBounds (100, 80, 140, 45);
        buttonInscribir.setBounds (100, 180, 140, 45);
        buttonVolver.setBounds (110, 485, 120, 40);
        buttonCalificar.setBounds (100, 280, 140, 45);
        buttonReporte.setBounds (100, 380, 140, 45);
    }


}