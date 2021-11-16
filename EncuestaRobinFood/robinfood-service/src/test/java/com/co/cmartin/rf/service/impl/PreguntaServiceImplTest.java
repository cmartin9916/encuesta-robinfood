/**
 * @autor Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.repository.PreguntaRepository;
import com.co.cmartin.rf.request.PreguntaRequest;
import com.co.cmartin.rf.response.OpcionResponse;
import com.co.cmartin.rf.response.PreguntaResponse;
import com.co.cmartin.rf.service.OpcionService;

/**
 * Pruebas unitarias de la clase PreguntaServiceImpl
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class PreguntaServiceImplTest {
	@Mock
	private PreguntaRepository preguntaRepository;
	@Mock
	private OpcionService opcionService;
	@InjectMocks
	private PreguntaServiceImpl preguntaServiceImpl;
	
	/**
	 * Dado: Consulta de preguntas
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaPreguntasCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(preguntaRepository.findAll()).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			preguntaServiceImpl.listarPreguntas();
		});
	}
	
	/**
	 * Dado: Consulta de preguntas
	 * Cuando: no hay informacion registrada
	 * Regresa: DatoNoEncontradoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaPreguntasCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(preguntaRepository.findAll()).thenReturn(new ArrayList<>());
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			preguntaServiceImpl.listarPreguntas();
		});
	}
	
	/**
	 * Dado: Consulta de preguntas
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de PreguntaResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaPreguntasCuandoHayInformacionRegistradaRegresaListaPreguntaResponse() throws SQLException, DatoNoEncontradoException
	{
		Pregunta pregunta1 = new Pregunta();
		pregunta1.setId(1L);
		pregunta1.setDescripcion("¿Pregunta Prueba 1?");
		pregunta1.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Pregunta pregunta2 = new Pregunta();
		pregunta2.setId(2L);
		pregunta2.setDescripcion("¿Pregunta Prueba 2?");
		pregunta1.setTipoPregunta(TipoPregunta.CERRADA);
		
		List<Pregunta> preguntas = new ArrayList<>();
		preguntas.add(pregunta1);
		preguntas.add(pregunta2);
		
		when(preguntaRepository.findAll()).thenReturn(preguntas);
				
		List<PreguntaResponse> respuesta = preguntaServiceImpl.listarPreguntas();
		assertEquals(2, respuesta.size());
		assertEquals(1L, respuesta.get(0).getId());
		assertEquals(2L, respuesta.get(1).getId());
		assertEquals("¿Pregunta Prueba 1?", respuesta.get(0).getDescripcion());
		assertEquals("¿Pregunta Prueba 2?", respuesta.get(1).getDescripcion());
	}
	
	/**
	 * Dado: Consulta una opcion por id
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaPreguntaPorIdCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(preguntaRepository.getById(1L)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			preguntaServiceImpl.consultarPregunta(1L);
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
	void dadaConsultaPreguntaPorIdCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(preguntaRepository.getById(1L)).thenThrow(EntityNotFoundException.class);
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			preguntaServiceImpl.consultarPregunta(1L);
		});
	}
	
	/**
	 * Dado: Consulta de preguntas
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de PreguntaResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaPreguntaPorIdCuandoHayInformacionRegistradaRegresaPreguntaResponse() throws SQLException, DatoNoEncontradoException
	{
		Pregunta pregunta1 = new Pregunta();
		pregunta1.setId(1L);
		pregunta1.setDescripcion("¿Pregunta Prueba 1?");
		pregunta1.setTipoPregunta(TipoPregunta.ABIERTA);
		
		when(preguntaRepository.getById(1L)).thenReturn(pregunta1);
				
		PreguntaResponse respuesta = preguntaServiceImpl.consultarPregunta(1L);
		assertEquals(1L, respuesta.getId());
		assertEquals("¿Pregunta Prueba 1?", respuesta.getDescripcion());
	}
	
	/**
	 * Dado: Guardado de opcion
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadoGuardadoPreguntaCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);
		
		when(preguntaRepository.save(pregunta)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			preguntaServiceImpl.guardarPregunta(preguntaRequest);
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
	void dadoGuardadoPreguntaCuandoYaEstaRegistradaRegresaDatoInvalidoException()
	{
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);
		
		when(preguntaRepository.save(pregunta)).thenThrow(DataIntegrityViolationException.class);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			preguntaServiceImpl.guardarPregunta(preguntaRequest);
		});
	}
	
	/**
	 * Dado: Guardado de pregunta abierta
	 * Cuando: la informacion es correcta
	 * Regresa: Pregunta
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 * @throws DatoInvalidoException 
	 * @throws ServiceException 
	 */
	@Test
	void dadaGuardadoPreguntaAbiertaCuandoLaInformacionEsCorrectaRegresaPregunta() throws SQLException, DatoNoEncontradoException, DatoInvalidoException, ServiceException
	{
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Pregunta preguntaSave = new Pregunta();
		preguntaSave.setId(1L);
		preguntaSave.setDescripcion("¿Pregunta Prueba 1?");
		preguntaSave.setTipoPregunta(TipoPregunta.ABIERTA);
		
		when(preguntaRepository.save(pregunta)).thenReturn(preguntaSave);
				
		PreguntaResponse respuesta = preguntaServiceImpl.guardarPregunta(preguntaRequest);
		assertEquals(1L, respuesta.getId());
		assertEquals("¿Pregunta Prueba 1?", respuesta.getDescripcion());
		assertEquals(TipoPregunta.ABIERTA, respuesta.getTipoPregunta());
	}
	
	/**
	 * Dado: Guardado de pregunta abierta
	 * Cuando: la informacion es correcta con lista de opciones vacia
	 * Regresa: Pregunta
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 * @throws DatoInvalidoException 
	 * @throws ServiceException 
	 */
	@Test
	void dadaGuardadoPreguntaAbiertaCuandoLaInformacionEsCorrectaConListaOpcionesVaciaRegresaPregunta() throws SQLException, DatoNoEncontradoException, DatoInvalidoException, ServiceException
	{
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.ABIERTA);
		preguntaRequest.setOpciones(new ArrayList<>());
		
		Pregunta pregunta = new Pregunta();
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Pregunta preguntaSave = new Pregunta();
		preguntaSave.setId(1L);
		preguntaSave.setDescripcion("¿Pregunta Prueba 1?");
		preguntaSave.setTipoPregunta(TipoPregunta.ABIERTA);
		
		when(preguntaRepository.save(pregunta)).thenReturn(preguntaSave);
				
		PreguntaResponse respuesta = preguntaServiceImpl.guardarPregunta(preguntaRequest);
		assertEquals(1L, respuesta.getId());
		assertEquals("¿Pregunta Prueba 1?", respuesta.getDescripcion());
		assertEquals(TipoPregunta.ABIERTA, respuesta.getTipoPregunta());
	}
	
	/**
	 * Dado: Guardado de pregunta abierta
	 * Cuando: la informacion es correcta con lista de opciones
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 * @throws DatoInvalidoException 
	 * @throws ServiceException 
	 */
	@Test
	void dadaGuardadoPreguntaAbiertaCuandoLaInformacionEsCorrectaConListaOpcionesRegresaDatoInvalidoException() throws SQLException, DatoNoEncontradoException, DatoInvalidoException, ServiceException
	{
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.ABIERTA);
		preguntaRequest.setOpciones(Arrays.asList(1L));
				
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			preguntaServiceImpl.guardarPregunta(preguntaRequest);
		});
	}
	
	/**
	 * Dado: Guardado de opcion
	 * Cuando: ocurre un error en la capa repository al consultar opciones
	 * Regresa: ServiceException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoPreguntaCuandoOcurreErrorEnLaCapaRepositoryAlConsultarOpcionesRegresaServiceException() throws SQLException, DatoNoEncontradoException
	{		
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaRequest.setOpciones(Arrays.asList(1L));
		
		when(opcionService.consultarOpcion(1L)).thenThrow(SQLException.class);
		
		Assertions.assertThrows(ServiceException.class, () -> {
			preguntaServiceImpl.guardarPregunta(preguntaRequest);
		});
	}
	
	/**
	 * Dado: Guardado de opcion
	 * Cuando: no encuentra opcion
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoPreguntaCuandoNoEncuentraOpcionRegresaDatoInvalidoException() throws SQLException, DatoNoEncontradoException
	{		
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaRequest.setOpciones(Arrays.asList(1L));
		
		when(opcionService.consultarOpcion(1L)).thenThrow(DatoNoEncontradoException.class);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			preguntaServiceImpl.guardarPregunta(preguntaRequest);
		});
	}
	
	/**
	 * Dado: Guardado de pregunta cerrada
	 * Cuando: la informacion es correcta
	 * Regresa: Pregunta
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 * @throws ServiceException 
	 * @throws DatoInvalidoException 
	 */
	@Test
	void dadaGuardadoPreguntaCerradaCuandoLaInformacionEsCorrectaRegresaPregunta() throws SQLException, DatoNoEncontradoException, DatoInvalidoException, ServiceException
	{		
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaRequest.setOpciones(Arrays.asList(1L));
		
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(1L);
		opcionResponse.setDescripcion("Si");
		
		when(opcionService.consultarOpcion(1L)).thenReturn(opcionResponse);
		
		Opcion opcion = new Opcion();
		opcion.setId(1L);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.CERRADA);
		pregunta.setOpcionesPregunta(Arrays.asList(opcion));
		
		Pregunta preguntaSave = new Pregunta();
		preguntaSave.setId(1L);
		preguntaSave.setDescripcion("¿Pregunta Prueba 1?");
		preguntaSave.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaSave.setOpcionesPregunta(Arrays.asList(opcion));
		
		when(preguntaRepository.save(pregunta)).thenReturn(preguntaSave);
		
		PreguntaResponse respuesta = preguntaServiceImpl.guardarPregunta(preguntaRequest);
		assertEquals(1L, respuesta.getId());
		assertEquals("¿Pregunta Prueba 1?", respuesta.getDescripcion());
		assertEquals(TipoPregunta.CERRADA, respuesta.getTipoPregunta());
		assertEquals(1, respuesta.getOpciones().size());
	}
	
	/**
	 * Dado: Guardado de pregunta cerrada
	 * Cuando: la pregunta ya existe
	 * Regresa: Pregunta
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 * @throws ServiceException 
	 * @throws DatoInvalidoException 
	 */
	@Test
	void dadaGuardadoPreguntaCerradaCuandoLaPreguntaYaExisteRegresaPregunta() throws SQLException, DatoNoEncontradoException, DatoInvalidoException, ServiceException
	{		
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		preguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaRequest.setOpciones(Arrays.asList(2L));
		
		
		Opcion opcionConsulta = new Opcion();
		opcionConsulta.setId(1L);
		
		Pregunta preguntaConsulta = new Pregunta();
		preguntaConsulta.setId(1L);
		preguntaConsulta.setDescripcion("¿Pregunta Prueba 1?");
		preguntaConsulta.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaConsulta.setOpcionesPregunta(Arrays.asList(opcionConsulta));
		
		when(preguntaRepository.buscarPreguntaPorDescripcionTipo("¿Pregunta Prueba 1?", TipoPregunta.CERRADA)).thenReturn(preguntaConsulta);
		
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(2L);
		opcionResponse.setDescripcion("Si");
		
		when(opcionService.consultarOpcion(2L)).thenReturn(opcionResponse);
		
		Opcion opcion = new Opcion();
		opcion.setId(2L);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.CERRADA);
		pregunta.setOpcionesPregunta(Arrays.asList(opcion));
		
		Pregunta preguntaSave = new Pregunta();
		preguntaSave.setId(1L);
		preguntaSave.setDescripcion("¿Pregunta Prueba 1?");
		preguntaSave.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaSave.setOpcionesPregunta(Arrays.asList(opcion));
		
		when(preguntaRepository.save(pregunta)).thenReturn(preguntaSave);
		
		PreguntaResponse respuesta = preguntaServiceImpl.guardarPregunta(preguntaRequest);
		assertEquals(1L, respuesta.getId());
		assertEquals("¿Pregunta Prueba 1?", respuesta.getDescripcion());
		assertEquals(TipoPregunta.CERRADA, respuesta.getTipoPregunta());
		assertEquals(1, respuesta.getOpciones().size());
	}
}
