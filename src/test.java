import DAO.Alumno.AlumnoDAOH2Impl;
import DAO.Curso.CursoDAOH2Impl;
import DAO.Parcial.ParcialDAOH2Impl;
import Entidades.Curso;
import Entidades.Parcial;
import Exceptions.*;
import DAO.TableManager;
import Services.AlumnoServicio;
import Services.CursoServicio;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test {

	public static void main(String [] args) throws SQLException, DAOClaveDuplicadaException, ClaveNoExisteException, IntegerVaciaException, DAOCursoNoExisteException, DAOLegajoNoExisteException, ServiceLegajoNoExsiteException {

		TableManager t = new TableManager();
		AlumnoDAOH2Impl x = new AlumnoDAOH2Impl();
		CursoDAOH2Impl ca = new CursoDAOH2Impl();
		ParcialDAOH2Impl pa = new ParcialDAOH2Impl();
		AlumnoServicio alumnoServicio = new AlumnoServicio();
		CursoServicio cursoServicio = new CursoServicio();

		List<Parcial> parciales = pa.listaParcialesAlumno(x.muestraAlumno(1));
		System.out.println(parciales);
	}

}
