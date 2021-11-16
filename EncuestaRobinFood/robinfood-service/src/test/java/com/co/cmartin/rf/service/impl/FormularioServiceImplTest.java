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

import com.co.cmartin.rf.entity.Formulario;
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.exception.ServiceException;
import com.co.cmartin.rf.repository.FormularioRepository;
import com.co.cmartin.rf.request.FormularioRequest;
import com.co.cmartin.rf.response.FormularioResponse;
import com.co.cmartin.rf.response.PreguntaResponse;
import com.co.cmartin.rf.service.PreguntaService;

/**
 * Pruebas unitarias de la clase FormularioServiceImpl
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@ExtendWith(MockitoExtension.class)
class FormularioServiceImplTest {
	@Mock
	private FormularioRepository formularioRepository;
	@Mock
	private PreguntaService preguntaService;
	@InjectMocks
	private FormularioServiceImpl formularioServiceImpl;
	
	/**
	 * Dado: Consulta de preguntas
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaFormulariosCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(formularioRepository.findAll()).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			formularioServiceImpl.listarFormularios();
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
	void dadaConsultaFormulariosCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(formularioRepository.findAll()).thenReturn(new ArrayList<>());
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			formularioServiceImpl.listarFormularios();
		});
	}
	
	/**
	 * Dado: Consulta de preguntas
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de FormularioResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaFormulariosCuandoHayInformacionRegistradaRegresaListaFormularioResponse() throws SQLException, DatoNoEncontradoException
	{
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Pregunta pregunta2 = new Pregunta();
		pregunta2.setId(2L);
		pregunta2.setDescripcion("¿Pregunta Prueba 2?");
		pregunta2.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Formulario formulario1 = new Formulario();
		formulario1.setId(1L);
		formulario1.setTitulo("Formulario 1");
		formulario1.setDescripcion("Formulario Prueba 1");
		formulario1.setActivo(true);
		formulario1.setPreguntasFormulario(Arrays.asList(pregunta));
		
		Formulario formulario2 = new Formulario();
		formulario2.setId(2L);
		formulario2.setTitulo("Formulario 2");
		formulario2.setDescripcion("Formulario Prueba 2");
		formulario2.setActivo(false);
		formulario2.setPreguntasFormulario(Arrays.asList(pregunta, pregunta2));
		
		List<Formulario> formularios = new ArrayList<>();
		formularios.add(formulario1);
		formularios.add(formulario2);
		
		when(formularioRepository.findAll()).thenReturn(formularios);
				
		List<FormularioResponse> respuesta = formularioServiceImpl.listarFormularios();
		assertEquals(2, respuesta.size());
		assertEquals(1L, respuesta.get(0).getId());
		assertEquals(2L, respuesta.get(1).getId());
		assertEquals("Formulario Prueba 1", respuesta.get(0).getDescripcion());
		assertEquals("Formulario Prueba 2", respuesta.get(1).getDescripcion());
	}
	
	/**
	 * Dado: Consulta una formulario por id
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaFormularioPorIdCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(formularioRepository.getById(1L)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			formularioServiceImpl.consultarFormulario(1L);
		});
	}
	
	/**
	 * Dado: Consulta una formulario por id
	 * Cuando: no hay informacion registrada
	 * Regresa: DatoNoEncontradoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaFormularioPorIdCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(formularioRepository.getById(1L)).thenThrow(EntityNotFoundException.class);
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			formularioServiceImpl.consultarFormulario(1L);
		});
	}
	
	/**
	 * Dado: Consulta de preguntas
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de FormularioResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaFormularioPorIdCuandoHayInformacionRegistradaRegresaFormularioResponse() throws SQLException, DatoNoEncontradoException
	{
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Formulario formulario1 = new Formulario();
		formulario1.setId(1L);
		formulario1.setTitulo("Formulario 1");
		formulario1.setDescripcion("Formulario Prueba 1");
		formulario1.setActivo(true);
		formulario1.setPreguntasFormulario(Arrays.asList(pregunta));
		
		when(formularioRepository.getById(1L)).thenReturn(formulario1);
				
		FormularioResponse respuesta = formularioServiceImpl.consultarFormulario(1L);
		assertEquals(1L, respuesta.getId());
		assertEquals("Formulario Prueba 1", respuesta.getDescripcion());
	}
	
	/**
	 * Dado: Guardado de formulario
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoFormularioCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException() throws SQLException, DatoNoEncontradoException
	{
		FormularioRequest formularioRequest = new FormularioRequest();
		formularioRequest.setTitulo("Formulario 1");
		formularioRequest.setDescripcion("Formulario Prueba 1");
		formularioRequest.setActivo(true);
		formularioRequest.setPreguntas(Arrays.asList(1L));
		
		PreguntaResponse preguntaResponse = new PreguntaResponse();
		preguntaResponse.setId(1L);
		preguntaResponse.setDescripcion("¿Pregunta Prueba 1?");
		preguntaResponse.setTipoPregunta(TipoPregunta.ABIERTA);
		
		when(preguntaService.consultarPregunta(1L)).thenReturn(preguntaResponse);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);

		Formulario formulario1 = new Formulario();
		formulario1.setId(1L);
		formulario1.setTitulo("Formulario 1");
		formulario1.setDescripcion("Formulario Prueba 1");
		formulario1.setActivo(true);
		formulario1.setPreguntasFormulario(Arrays.asList(pregunta));		
		
		when(formularioRepository.save(formulario1)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			formularioServiceImpl.guardarFormulario(formularioRequest);
		});
	}
	
	/**
	 * Dado: Guardado de formulario
	 * Cuando: no existe pregunta
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoFormularioCuandoNoExistePreguntaRegresaDatoInvalidoException() throws SQLException, DatoNoEncontradoException
	{
		FormularioRequest formularioRequest = new FormularioRequest();
		formularioRequest.setTitulo("Formulario 1");
		formularioRequest.setDescripcion("Formulario Prueba 1");
		formularioRequest.setActivo(true);
		formularioRequest.setPreguntas(Arrays.asList(1L));
		
		PreguntaResponse preguntaResponse = new PreguntaResponse();
		preguntaResponse.setId(1L);
		preguntaResponse.setDescripcion("¿Pregunta Prueba 1?");
		preguntaResponse.setTipoPregunta(TipoPregunta.ABIERTA);
		
		when(preguntaService.consultarPregunta(1L)).thenThrow(DatoNoEncontradoException.class);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			formularioServiceImpl.guardarFormulario(formularioRequest);
		});
	}
	
	/**
	 * Dado: Guardado de formulario
	 * Cuando: ya esta registrada
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadoGuardadoFormularioCuandoYaEstaRegistradaRegresaDatoInvalidoException()
	{
		FormularioRequest formularioRequest = new FormularioRequest();
		formularioRequest.setTitulo("Formulario 1");
		formularioRequest.setDescripcion("Formulario Prueba 1");
		formularioRequest.setActivo(true);
		formularioRequest.setPreguntas(Arrays.asList(1L));
		
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);

		Formulario formulario1 = new Formulario();
		formulario1.setTitulo("Formulario 1");
		formulario1.setDescripcion("Formulario Prueba 1");
		formulario1.setActivo(true);
		formulario1.setPreguntasFormulario(Arrays.asList(pregunta));
		
		when(formularioRepository.save(formulario1)).thenThrow(DataIntegrityViolationException.class);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			formularioServiceImpl.guardarFormulario(formularioRequest);
		});
	}
	
	/**
	 * Dado: Guardado de formulario
	 * Cuando: informacion correcta
	 * Regresa: Formulario
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws ServiceException 
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoFormularioCuandoInformacionCorrectaRegresaFormulario() throws SQLException, DatoInvalidoException, ServiceException
	{
		FormularioRequest formularioRequest = new FormularioRequest();
		formularioRequest.setTitulo("Formulario 1");
		formularioRequest.setDescripcion("Formulario Prueba 1");
		formularioRequest.setActivo(true);
		formularioRequest.setPreguntas(Arrays.asList(1L));
		
		when(formularioRepository.buscarFormularioPorTituloDescripcion("Formulario 1", "Formulario Prueba 1")).thenReturn(null);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);

		Formulario formulario1 = new Formulario();
		formulario1.setTitulo("Formulario 1");
		formulario1.setDescripcion("Formulario Prueba 1");
		formulario1.setActivo(true);
		formulario1.setPreguntasFormulario(Arrays.asList(pregunta));
		
		Formulario formularioSave = new Formulario();
		formularioSave.setId(1L);
		formularioSave.setTitulo("Formulario 1");
		formularioSave.setDescripcion("Formulario Prueba 1");
		formularioSave.setActivo(true);
		formularioSave.setPreguntasFormulario(Arrays.asList(pregunta));
		
		when(formularioRepository.save(formulario1)).thenReturn(formularioSave);
		
		FormularioResponse respuesta = formularioServiceImpl.guardarFormulario(formularioRequest);
		assertEquals(1L, respuesta.getId());
		assertEquals("Formulario 1", respuesta.getTitulo());
		assertEquals("Formulario Prueba 1", respuesta.getDescripcion());
		assertEquals(1, respuesta.getPreguntas().size());
	}
	
	/**
	 * Dado: Guardado de formulario
	 * Cuando: ya esta registrada
	 * Regresa: Formulario
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws ServiceException 
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoFormularioCuandoYaEstaRegistradaRegresaFormulario() throws SQLException, DatoInvalidoException, ServiceException
	{
		FormularioRequest formularioRequest = new FormularioRequest();
		formularioRequest.setTitulo("Formulario 1");
		formularioRequest.setDescripcion("Formulario Prueba 1");
		formularioRequest.setActivo(true);
		formularioRequest.setPreguntas(Arrays.asList(1L));
		
		Pregunta preguntaConsulta = new Pregunta();
		preguntaConsulta.setId(1L);

		Formulario formularioConsulta = new Formulario();
		formularioConsulta.setId(1L);
		formularioConsulta.setTitulo("Formulario 1");
		formularioConsulta.setDescripcion("Formulario Prueba");
		formularioConsulta.setActivo(true);
		formularioConsulta.setPreguntasFormulario(Arrays.asList(preguntaConsulta));
		
		when(formularioRepository.buscarFormularioPorTituloDescripcion("Formulario 1", "Formulario Prueba 1")).thenReturn(formularioConsulta);
		
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);

		Formulario formulario1 = new Formulario();
		formulario1.setId(1L);
		formulario1.setTitulo("Formulario 1");
		formulario1.setDescripcion("Formulario Prueba 1");
		formulario1.setActivo(true);
		formulario1.setPreguntasFormulario(Arrays.asList(pregunta));
		
		Formulario formularioSave = new Formulario();
		formularioSave.setId(1L);
		formularioSave.setTitulo("Formulario 1");
		formularioSave.setDescripcion("Formulario Prueba 1");
		formularioSave.setActivo(true);
		formularioSave.setPreguntasFormulario(Arrays.asList(pregunta));
		
		when(formularioRepository.save(formulario1)).thenReturn(formularioSave);
		
		FormularioResponse respuesta = formularioServiceImpl.guardarFormulario(formularioRequest);
		assertEquals(1L, respuesta.getId());
		assertEquals("Formulario 1", respuesta.getTitulo());
		assertEquals("Formulario Prueba 1", respuesta.getDescripcion());
		assertEquals(1, respuesta.getPreguntas().size());
	}
	
	/**
	 * Dado: Actualizar estado de formulario
	 * Cuando: no se actualizan registros
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws ServiceException 
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoActualizarEstadoFormularioCuandoNoSeActualizanRegistrosRegresaDatoInvalidoException() throws SQLException, DatoInvalidoException, ServiceException
	{		
		when(formularioRepository.actualizarEstadoFormulario(1L, false)).thenReturn(0);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			formularioServiceImpl.actualizarEstadoFormulario(1L, false);
		});
	}
	
	/**
	 * Dado: Actualizar estado de formulario
	 * Cuando: se actualizan registros
	 * No Regresa Error
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws ServiceException 
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadoActualizarEstadoFormularioCuandoSeActualizanRegistrosNoRegresaError() throws SQLException, DatoInvalidoException, ServiceException
	{		
		when(formularioRepository.actualizarEstadoFormulario(1L, false)).thenReturn(1);
		
		Assertions.assertDoesNotThrow(() -> {
			formularioServiceImpl.actualizarEstadoFormulario(1L, false);
		}, "Ocurrio Un Error Inesperado");
	}
}
