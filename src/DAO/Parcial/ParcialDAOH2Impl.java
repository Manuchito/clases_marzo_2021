package DAO.Parcial;

import DAO.Curso.CursoDAO;
import DAO.Curso.CursoDAOH2Impl;
import DAO.DBManager;
import Entidades.Alumno;
import Entidades.Curso;
import Entidades.Nota;
import Exceptions.DAOClaveDuplicadaException;
import Exceptions.DAOCursoNoExisteException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParcialDAOH2Impl {
    public void crearNota(Nota unaNota) throws DAOClaveDuplicadaException {
        int legajo = unaNota.getAlumno().getLegajo();
        int id_curso = unaNota.getCurso().getId();
        String tipoNota = unaNota.getTipoNota();
        int notaParcial = unaNota.getNotaParcial();

        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            String sql = "INSERT INTO parcial (id_alumno, id_curso, tipo_nota, nota) VALUES ('" + legajo + "', '" + id_curso + "', '" + tipoNota + "', '" + notaParcial + "')";
            s.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            if(e.getErrorCode() == 23505) {
                throw new DAOClaveDuplicadaException("La nota ya existe");
            }
            try {
                e.printStackTrace();
                c.rollback();
            } catch (SQLException e1) {
                e.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public List<Nota> listaNotasAlumno(Alumno unAlumno){
        List<Nota> notas = new ArrayList<>();
        CursoDAO cursoDAO = new CursoDAOH2Impl();

        String sql = "SELECT * FROM PARCIAL WHERE ID_ALUMNO = " + unAlumno.getLegajo();
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                int legajo = rs.getInt("ID_ALUMNO");
                Curso curso = cursoDAO.muestraCurso(rs.getInt("ID_CURSO"));
                String tipoNota = rs.getString("TIPO_NOTA");
                int nota = rs.getInt("NOTA");

                Nota n = new Nota(unAlumno, curso, tipoNota, nota);
                notas.add(n);

            }
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (DAOCursoNoExisteException cursoNoExisteException) {
            cursoNoExisteException.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return notas;

    }

    public List<Nota> listarNotasCursoALumno(Alumno unAlumno, Curso unCurso){
        List<Nota> notas = new ArrayList<>();
        CursoDAO cursoDAO = new CursoDAOH2Impl();

        String sql = "SELECT * FROM PARCIAL WHERE ID_ALUMNO = " + unAlumno.getLegajo() + " AND ID_CURSO = " + unCurso.getId();
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                String tipoNota = rs.getString("TIPO_NOTA");
                int nota = rs.getInt("NOTA");
                Nota n = new Nota(unAlumno, unCurso, tipoNota, nota);
                notas.add(n);

            }
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return notas;

    }

}
