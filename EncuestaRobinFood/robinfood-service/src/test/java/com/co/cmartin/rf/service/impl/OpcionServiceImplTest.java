/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.co.cmartin.rf.entity.Opcion;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.repository.OpcionRepository;
import com.co.cmartin.rf.request.OpcionRequest;
import com.co.cmartin.rf.response.OpcionResponse;

/**
 * Pruebas unitarias de la clase OpcionServiceImpl
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@ExtendWith(MockitoExtension.class)
class OpcionServiceImplTest {

	@Mock
	private OpcionRepository opcionRepository;
	@InjectMocks
	private OpcionServiceImpl opcionServiceImpl;
	
	/**
	 * Dado: Consulta de opciones
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaOpcionesCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(opcionRepository.findAll()).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			opcionServiceImpl.listarOpciones();
		});
	}
	
	/**
	 * Dado: Consulta de opciones
	 * Cuando: no hay informacion registrada
	 * Regresa: DatoNoEncontradoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaOpcionesCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(opcionRepository.findAll()).thenReturn(new ArrayList<>());
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			opcionServiceImpl.listarOpciones();
		});
	}
	
	/**
	 * Dado: Consulta de opciones
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de OpcionResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaOpcionesCuandoHayInformacionRegistradaRegresaListaOpcionResponse() throws SQLException, DatoNoEncontradoException
	{
		Opcion opcionSi = new Opcion();
		opcionSi.setId(1L);
		opcionSi.setDescripcion("SI");
		Opcion opcionNo = new Opcion();
		opcionNo.setId(2L);
		opcionNo.setDescripcion("NO");
		
		List<Opcion> opciones = new ArrayList<>();
		opciones.add(opcionSi);
		opciones.add(opcionNo);
		
		when(opcionRepository.findAll()).thenReturn(opciones);
				
		List<OpcionResponse> respuesta = opcionServiceImpl.listarOpciones();
		assertEquals(2, respuesta.size());
		assertEquals(1L, respuesta.get(0).getId());
		assertEquals(2L, respuesta.get(1).getId());
		assertEquals("SI", respuesta.get(0).getDescripcion());
		assertEquals("NO", respuesta.get(1).getDescripcion());
	}
	
	/**
	 * Dado: Consulta una opcion por id
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaOpcionPorIdCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(opcionRepository.getById(1L)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			opcionServiceImpl.consultarOpcion(1L);
		});
	}
	
	/**
	 * Dado: Consulta una opcion por id
	 * Cuando: no hay informacion registrada
	 * Regresa: DatoNoEncontradoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaOpcionPorIdCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(opcionRepository.getById(1L)).thenThrow(EntityNotFoundException.class);
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			opcionServiceImpl.consultarOpcion(1L);
		});
	}
	
	/**
	 * Dado: Consulta de opciones
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de OpcionResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaOpcionPorIdCuandoHayInformacionRegistradaRegresaOpcionResponse() throws SQLException, DatoNoEncontradoException
	{
		Opcion opcionSi = new Opcion();
		opcionSi.setId(1L);
		opcionSi.setDescripcion("SI");
		
		when(opcionRepository.getById(1L)).thenReturn(opcionSi);
				
		OpcionResponse respuesta = opcionServiceImpl.consultarOpcion(1L);
		assertEquals(1, respuesta.getId());
		assertEquals("SI", respuesta.getDescripcion());
	}
	
	/**
	 * Dado: Guardado de opcion
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadoGuardadoOpcionCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		OpcionRequest opcionRequest = new OpcionRequest();
		opcionRequest.setDescripcion("SI");
		
		Opcion opcion = new Opcion();
		opcion.setDescripcion("SI");
		opcion.setFechaUltimaModificacion(new Date());
		
		when(opcionRepository.save(opcion)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			opcionServiceImpl.guardarOpcion(opcionRequest);
		});
	}
	
	/**
	 * Dado: Guardado de opcion
	 * Cuando: ya esta registrada
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadoGuardadoOpcionCuandoYaEstaRegistradaRegresaDatoInvalidoException()
	{
		OpcionRequest opcionRequest = new OpcionRequest();
		opcionRequest.setDescripcion("SI");
		
		Opcion opcion = new Opcion();
		opcion.setDescripcion("SI");
		opcion.setFechaUltimaModificacion(new Date());
		
		when(opcionRepository.save(opcion)).thenThrow(DataIntegrityViolationException.class);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			opcionServiceImpl.guardarOpcion(opcionRequest);
		});
	}
	
	/**
	 * Dado: Guardado de opcion
	 * Cuando: la informacion es correcta
	 * Regresa: Opcion
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 * @throws DatoInvalidoException 
	 */
	@Test
	void dadaGuardadoOpcionCuandoLaInformacionEsCorrectaRegresaOpcion() throws SQLException, DatoNoEncontradoException, DatoInvalidoException
	{
		OpcionRequest opcionRequest = new OpcionRequest();
		opcionRequest.setDescripcion("SI");
		
		Opcion opcion = new Opcion();
		opcion.setDescripcion("SI");
		opcion.setFechaUltimaModificacion(new Date());
		
		Opcion opcionSave = new Opcion();
		opcionSave.setId(1L);
		opcionSave.setDescripcion("SI");
		opcionSave.setFechaUltimaModificacion(new Date());
		
		when(opcionRepository.save(opcion)).thenReturn(opcionSave);
				
		OpcionResponse respuesta = opcionServiceImpl.guardarOpcion(opcionRequest);
		assertEquals(1, respuesta.getId());
		assertEquals("SI", respuesta.getDescripcion());
	}
}
