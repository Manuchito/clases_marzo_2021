package Swing.Admin;

import DAO.Cursada.CursadaDAOH2Impl;
import Entidades.Alumno;
import Entidades.Cursada;
import Entidades.Curso;
import Entidades.Nota;
import Exceptions.*;
import Main.PanelManager;
import Services.AlumnoServicio;
import Services.CursadaServicio;
import Services.CursoServicio;
import Services.NotaServicio;
import Swing.Tablas.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.*;

public class Admin_Swing extends JPanel{
    AlumnoServicio servAlumno = new AlumnoServicio();
    CursoServicio servCurso = new CursoServicio();
    NotaServicio servNota = new NotaServicio();
    CursadaServicio servCursada = new CursadaServicio();

    private JScrollPane scrollpaneAlumnos;
    private JScrollPane scrollpaneCursos;
    private JScrollPane scrollpaneNotas;
    private JScrollPane scrollPaneCursadas;

    private JTable tablaAlumnos;
    private JTable tablaCursos;
    private JTable tablaNotas;
    private JTable tablaCursada;

    private JButton buttonCrearAlumno;
    private JButton buttonEliminarAlumno;
    private JButton buttonEliminarCurso;
    private JButton buttonCrearCurso;
    private JButton buttonCrearNota;
    private JButton buttonEliminarNota;
    private JButton buttonCrearCursada;
    private JButton buttonEliminarCursada;

    private JLabel textAlumnos;
    private JLabel textCursos;
    private JLabel textNotas;
    private JLabel textCursadas;

    private JButton buttonVolver;

    private JButton buttonActualizarTablas;



    private PanelManager panelManager;

    public Admin_Swing(PanelManager m){
        super();
        this.panelManager = m;
    }

    private void activarBottonEliminar(JTable t, JButton b){
        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = t.getSelectedRow();
                if(filaSeleccionada!= -1){
                    b.setEnabled(true);
                }
            }
        });

    }

    public void armarAdminSwing() {
        //construct components

        AlumnoTableModel alumnoTableModel = new AlumnoTableModel();
        CursoTableModel cursoTableModel = new CursoTableModel();
        NotaTableModel notaTableModel = new NotaTableModel();
        CursadaTableModel cursadaTableModel = new CursadaTableModel();

        tablaAlumnos = new JTable(alumnoTableModel);
        tablaCursos = new JTable(cursoTableModel);
        tablaNotas = new JTable(notaTableModel);
        tablaCursada = new JTable(cursadaTableModel);

        scrollpaneAlumnos = new JScrollPane (tablaAlumnos);
        scrollpaneCursos = new JScrollPane (tablaCursos);
        scrollpaneNotas = new JScrollPane (tablaNotas);
        scrollPaneCursadas = new JScrollPane(tablaCursada);

        alumnoTableModel.setContenido(servAlumno.listarAlumnos());
        cursoTableModel.setContenido(servCurso.listarCursos());
        notaTableModel.setContenido(servNota.listarTodasLasNotas());
        cursadaTableModel.setContenido(servCursada.mostrarCursadas());

        buttonCrearAlumno = new JButton ("Crear");
        buttonEliminarAlumno = new JButton ("Eliminar");
        buttonEliminarCurso = new JButton ("Eliminar");
        buttonCrearCurso = new JButton ("Crear");
        buttonCrearNota = new JButton ("Crear");
        buttonEliminarNota = new JButton ("Eliminar");
        buttonCrearCursada = new JButton ("Crear");
        buttonEliminarCursada = new JButton ("Eliminar");

        textAlumnos = new JLabel ("ALUMNOS");
        textCursos = new JLabel ("CURSOS");
        textNotas = new JLabel ("NOTAS");
        textCursadas = new JLabel ("CURSADAS");

        buttonVolver = new JButton ("Volver");
        buttonActualizarTablas = new JButton ("Actualizar Tablas");

        buttonEliminarAlumno.setEnabled(false);
        buttonEliminarCurso.setEnabled(false);
        buttonEliminarNota.setEnabled(false);
        buttonEliminarCursada.setEnabled(false);

        //adjust size and set layout
        setLayout (null);

        //listeners
        TableCellListener tclAlumno = new TableCellListener(tablaAlumnos, new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                TableCellListener tclAlumno = (TableCellListener)e.getSource();
                Alumno a = alumnoTableModel.getContenido().get(tablaAlumnos.getSelectedRow());
                int exit = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere editar?" , null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (exit == JOptionPane.YES_OPTION) {
                    try {
                        servAlumno.editar(a.getLegajo(),a.getNombre(),a.getApellido(),a.getLimiteCursos());
                    } catch (ServiceLegajoNoExsiteException serviceLegajoNoExsiteException) {
                        serviceLegajoNoExsiteException.printStackTrace();
                    }
                }
                else {
                    tablaAlumnos.setValueAt(tclAlumno.getOldValue(), tclAlumno.getRow(), tclAlumno.getColumn());
                }
            }

        });

        TableCellListener tclCurso = new TableCellListener(tablaCursos, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                TableCellListener tclCurso = (TableCellListener)e.getSource();
                Curso c = cursoTableModel.getContenido().get(tablaCursos.getSelectedRow());
                int exit = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere editar el curso?" , null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (exit == JOptionPane.YES_OPTION) {
                    try {
                        servCurso.editarCurso(c.getId(),c.getNombre(),c.getCupo(),c.getPrecio(),c.getCantidad_parciales());
                    } catch (ServiceCursoNoExisteException serviceCursoNoExisteException) {
                        serviceCursoNoExisteException.printStackTrace();
                    }
                }
                else {
                    tablaCursos.setValueAt(tclCurso.getOldValue(), tclCurso.getRow(), tclCurso.getColumn());
                }
            }
        });

        TableCellListener tclNota = new TableCellListener(tablaNotas, new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                TableCellListener tclNota = (TableCellListener)e.getSource();
                Nota n = notaTableModel.getContenido().get(tablaNotas.getSelectedRow());
                int exit = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere editar?" , null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (exit == JOptionPane.YES_OPTION) {
                    try {
                        servNota.editarNota(n.getAlumno().getLegajo(), n.getCurso().getId(), n.getTipoNota(),n.getNotaParcial());
                    } catch (ServiceNotaParcialesDependenDeFinalException serviceNotaParcialesDependenDeFinalException) {
                        serviceNotaParcialesDependenDeFinalException.printStackTrace();
                    } catch (ServiceCursoNoExisteException serviceCursoNoExisteException) {
                        serviceCursoNoExisteException.printStackTrace();
                    } catch (ServiceLegajoNoExsiteException serviceLegajoNoExsiteException) {
                        serviceLegajoNoExsiteException.printStackTrace();
                    }
                }
                else {
                    tablaNotas.setValueAt(tclNota.getOldValue(), tclNota.getRow(), tclNota.getColumn());
                }
            }

        });

        activarBottonEliminar(tablaAlumnos,buttonEliminarAlumno);

        activarBottonEliminar(tablaCursos, buttonEliminarCurso);

        activarBottonEliminar(tablaNotas, buttonEliminarNota);

        activarBottonEliminar(tablaCursada, buttonEliminarCursada);


        buttonCrearAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPanelCreacionAlumno();
            }
        });



        buttonEliminarAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaAlumnos.getSelectedRow();
                Alumno a = alumnoTableModel.getContenido().get(filaSeleccionada);
                AlumnoServicio servAlumno = new AlumnoServicio();
                servAlumno.eliminar(a.getLegajo());
                alumnoTableModel.getContenido().remove(filaSeleccionada);
                alumnoTableModel.fireTableDataChanged();
                buttonEliminarAlumno.setEnabled(false);

            }
        });

        buttonCrearNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPanelCalificarAlumno(new Admin_Swing(panelManager));
            }
        });


        buttonEliminarNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionadaNotas = tablaNotas.getSelectedRow();
                Nota c = notaTableModel.getContenido().get(filaSeleccionadaNotas);
                NotaServicio servNota = new NotaServicio();
                try {
                    servNota.eliminarNota(c.getAlumno().getLegajo(), c.getCurso().getId(), c.getTipoNota());
                    buttonEliminarNota.setEnabled(false);
                    notaTableModel.getContenido().remove(filaSeleccionadaNotas);
                    notaTableModel.fireTableDataChanged();
                } catch (ServiceNotaParcialesDependenDeFinalException serviceNotaParcialesDependenDeFinalException) {
                    JOptionPane.showMessageDialog(null, "El final de la materia depende de la nota a eliminar",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        buttonCrearCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPanelCrearCurso();
            }
        });

        buttonEliminarCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionadaCursos = tablaCursos.getSelectedRow();
                Curso c = cursoTableModel.getContenido().get(filaSeleccionadaCursos);

                CursoServicio servCurso = new CursoServicio();
                buttonEliminarCurso.setEnabled(false);
                servCurso.borrarCurso(c.getId());
                cursoTableModel.getContenido().remove(filaSeleccionadaCursos);
                cursoTableModel.fireTableDataChanged();
            }
        });



        buttonCrearCursada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelManager.mostrarPanelCrearCursada();
            }
        });

        buttonEliminarCursada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionadaCursada = tablaCursada.getSelectedRow();
                Cursada c = cursadaTableModel.getContenido().get(filaSeleccionadaCursada);
                try {
                    servCursada.borrarCursada(c.getCursada());
                    buttonEliminarCursada.setEnabled(false);
                    cursadaTableModel.getContenido().remove(filaSeleccionadaCursada);
                    cursadaTableModel.fireTableDataChanged();
                } catch (ServiceLegajoNoExsiteException | ServiceCursoNoExisteException | ServiceCursadaNoExisteException serviceLegajoNoExsiteException) {
                    serviceLegajoNoExsiteException.printStackTrace();
                }

            }
        });

        buttonActualizarTablas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alumnoTableModel.setContenido(new ArrayList<>());
                alumnoTableModel.setContenido(servAlumno.listarAlumnos());
                alumnoTableModel.fireTableDataChanged();

                cursoTableModel.setContenido(new ArrayList<>());
                cursoTableModel.setContenido(servCurso.listarCursos());
                cursoTableModel.fireTableDataChanged();

                notaTableModel.setContenido(new ArrayList<>());
                notaTableModel.setContenido(servNota.listarTodasLasNotas());
                notaTableModel.fireTableDataChanged();

                cursadaTableModel.setContenido(new ArrayList<>());
                cursadaTableModel.setContenido(servCursada.mostrarCursadas());
                cursoTableModel.fireTableDataChanged();
            }
        });

        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int exit = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere volver al menu principal?" , null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (exit == JOptionPane.YES_OPTION) {
                    panelManager.mostrarPanelInicioSesion();
                }
            }
        });

        //add components
        add (scrollpaneAlumnos);
        add (scrollpaneCursos);
        add (scrollpaneNotas);
        add (scrollPaneCursadas);

        add (buttonCrearAlumno);
        add (buttonEliminarAlumno);
        add (buttonEliminarCurso);
        add (buttonCrearCurso);
        add (buttonCrearNota);
        add (buttonEliminarNota);
        add (buttonCrearCursada);
        add (buttonEliminarCursada);

        add (textAlumnos);
        add (textCursos);
        add (textNotas);
        add (textCursadas);

        add (buttonVolver);
        add (buttonActualizarTablas);


        scrollpaneAlumnos.setBounds (45, 220, 315, 515);
        scrollpaneCursos.setBounds (395, 220, 315, 515);
        scrollpaneNotas.setBounds (745, 220, 315, 515);
        scrollPaneCursadas.setBounds (1095, 220, 315, 515);

        buttonCrearAlumno.setBounds (155, 70, 100, 25);
        buttonEliminarAlumno.setBounds (155, 130, 100, 25);
        buttonEliminarCurso.setBounds (500, 130, 100, 25);
        buttonCrearCurso.setBounds (500, 70, 100, 25);
        buttonCrearNota.setBounds (850, 70, 100, 25);
        buttonEliminarNota.setBounds (850, 130, 100, 25);
        buttonCrearCursada.setBounds (1200, 70, 100, 25);
        buttonEliminarCursada.setBounds (1200, 130, 100, 25);

        textAlumnos.setBounds (175, 25, 60, 25);
        textCursos.setBounds (525, 25, 50, 25);
        textNotas.setBounds (880, 20, 40, 25);
        textCursadas.setBounds (1215, 20, 65, 25);
        textCursadas.setBounds (1215, 20, 65, 25);

        buttonVolver.setBounds (1300, 795, 100, 25);
        buttonActualizarTablas.setBounds (635, 770, 185, 35);


    }
}
