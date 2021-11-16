/**
 * @autor Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

import com.co.cmartin.rf.entity.Encuesta;
import com.co.cmartin.rf.entity.Formulario;
import com.co.cmartin.rf.entity.Opcion;
import com.co.cmartin.rf.entity.Persona;
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.repository.EncuestaRepository;
import com.co.cmartin.rf.repository.FormularioRepository;
import com.co.cmartin.rf.request.EncuestaOpcionRequest;
import com.co.cmartin.rf.request.EncuestaPreguntaRequest;
import com.co.cmartin.rf.request.EncuestaRequest;
import com.co.cmartin.rf.request.PersonaRequest;
import com.co.cmartin.rf.response.EncuestaResponse;
import com.co.cmartin.rf.response.PersonaResponse;
import com.co.cmartin.rf.service.PersonaService;
import com.co.cmartin.rf.service.RespuestaService;

/**
 * Pruebas unitarias de la clase EncuestaServiceImpl
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@ExtendWith(MockitoExtension.class)
class EncuestaServiceImplTest {
	@Mock
	private EncuestaRepository encuestaRepository;
	@Mock
	private FormularioRepository formularioRepository;
	@Mock
	private PersonaService personaService;
	@Mock
	private RespuestaService respuestaService;
	@InjectMocks
	private EncuestaServiceImpl encuestaServiceImpl;
	
	/**
	 * Dado: Consulta de encuesta
	 * Cuando: no existe formulario
	 * Regresa: DatoNoEncontradoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaConsultaEncuestaCuandoNoExisteFormularioRegresaDatoNoEncontradoException()
	{
		when(formularioRepository.getById(1L)).thenThrow(EntityNotFoundException.class);
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			encuestaServiceImpl.consultarFormulario(1L);
		});
	}
	
	/**
	 * Dado: Consulta de encuesta
	 * Cuando: formulario esta inactivo
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaConsultaEncuestaCuandoFormularioEstaInactivoRegresaDatoInvalidoException()
	{
		Pregunta pregunta = new Pregunta();
		pregunta.setId(1L);
		pregunta.setDescripcion("¿Pregunta Prueba 1?");
		pregunta.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Formulario formulario = new Formulario();
		formulario.setId(1L);
		formulario.setTitulo("Formulario 1");
		formulario.setDescripcion("Formulario Prueba 1");
		formulario.setActivo(false);
		formulario.setPreguntasFormulario(Arrays.asList(pregunta));
		
		when(formularioRepository.getById(1L)).thenReturn(formulario);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			encuestaServiceImpl.consultarFormulario(1L);
		});
	}
	
	/**
	 * Dado: Consulta de encuesta
	 * Cuando: formulario es valido
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaEncuestaCuandoFormularioEsValidoRegresaFormularioResponse() throws SQLException, DatoInvalidoException, DatoNoEncontradoException
	{
		Opcion opcionSi = new Opcion();
		opcionSi.setId(2L);
		opcionSi.setDescripcion("Si");
		
		Opcion opcionNo = new Opcion();
		opcionNo.setId(2L);
		opcionNo.setDescripcion("No");

		Pregunta preguntaC = new Pregunta();
		preguntaC.setId(1L);
		preguntaC.setDescripcion("¿Pregunta Prueba 1?");
		preguntaC.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaC.setOpcionesPregunta(Arrays.asList(opcionSi, opcionNo));
		
		Pregunta preguntaA = new Pregunta();
		preguntaA.setId(2L);
		preguntaA.setDescripcion("¿Pregunta Prueba 2?");
		preguntaA.setTipoPregunta(TipoPregunta.ABIERTA);
		
		Formulario formulario = new Formulario();
		formulario.setId(1L);
		formulario.setTitulo("Formulario 1");
		formulario.setDescripcion("Formulario Prueba 1");
		formulario.setActivo(true);
		formulario.setPreguntasFormulario(Arrays.asList(preguntaC, preguntaA));
		
		when(formularioRepository.getById(1L)).thenReturn(formulario);
		
		EncuestaResponse respuesta = encuestaServiceImpl.consultarFormulario(1L);
		assertEquals(1L, respuesta.getIdFormulario());
		assertEquals("Formulario 1", respuesta.getTitulo());
		assertEquals("Formulario Prueba 1", respuesta.getDescripcion());
		assertEquals(2, respuesta.getPreguntas().size());
		assertEquals(1L, respuesta.getPreguntas().get(0).getId());
		assertEquals(2L, respuesta.getPreguntas().get(1).getId());
		assertEquals("¿Pregunta Prueba 1?", respuesta.getPreguntas().get(0).getDescripcion());
		assertEquals("¿Pregunta Prueba 2?", respuesta.getPreguntas().get(1).getDescripcion());
		assertEquals(TipoPregunta.CERRADA, respuesta.getPreguntas().get(0).getTipoPregunta());
		assertEquals(TipoPregunta.ABIERTA, respuesta.getPreguntas().get(1).getTipoPregunta());
		assertEquals(2, respuesta.getPreguntas().get(0).getRespuestas().size());
		assertNull(respuesta.getPreguntas().get(1).getRespuestas());
	}
	
	/**
	 * Dado: Guardar de encuesta
	 * Cuando: No se envian preguntas
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaGuardaEncuestaCuandoNoSeEnvianPreguntasRegresaDatoInvalidoException()
	{
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		});
	}
	
	/**
	 * Dado: Guardar de encuesta
	 * Cuando: No se responde pregunta abierta correctamente (null)
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaGuardaEncuestaCuandoNoSeResponsePreguntaAbiertaCorrectamenteNullRegresaDatoInvalidoException()
	{
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(2L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 2?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.ABIERTA);
		encuestaPreguntaRequest.setRespuestaAbierta(null);
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		});
	}
	
	/**
	 * Dado: Guardar de encuesta
	 * Cuando: No se responde pregunta abierta correctamente (vacio)
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaGuardaEncuestaCuandoNoSeResponsePreguntaAbiertaCorrectamenteVacioRegresaDatoInvalidoException()
	{
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(2L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 2?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.ABIERTA);
		encuestaPreguntaRequest.setRespuestaAbierta("");
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		});
	}
	
	/**
	 * Dado: Guardar de encuesta
	 * Cuando: No se responde pregunta cerrada correctamente sin opciones
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaGuardaEncuestaCuandoNoSeResponsePreguntaCerradaCorrectamenteSinOpcionesRegresaDatoInvalidoException()
	{
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		});
	}
	
	/**
	 * Dado: Guardar de encuesta
	 * Cuando: No se responde pregunta cerrada correctamente sin responder
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaGuardaEncuestaCuandoNoSeResponsePreguntaCerradaCorrectamenteSinResponderRegresaDatoInvalidoException()
	{
		EncuestaOpcionRequest encuestaOpcionRequest = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("Si");
		encuestaOpcionRequest.setSeleccionada(false);
		
		EncuestaOpcionRequest encuestaOpcionRequest2 = new EncuestaOpcionRequest();
		encuestaOpcionRequest2.setId(2L);
		encuestaOpcionRequest2.setDescripcion("No");
		encuestaOpcionRequest2.setSeleccionada(false);
		
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		encuestaPreguntaRequest.setRespuestas(Arrays.asList(encuestaOpcionRequest, encuestaOpcionRequest2));
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		});
	}
	
	/**
	 * Dado: Guardar de encuesta sin persona
	 * Cuando: Ocurre error inesperado al crear encuesta
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Test
	void dadaGuardaEncuestaSinPersonaCuandoOcurreErrorInesperadoAlCrearEncuestaRegresaSQLException()
	{
		EncuestaOpcionRequest encuestaOpcionRequest = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("Si");
		encuestaOpcionRequest.setSeleccionada(true);
		
		EncuestaOpcionRequest encuestaOpcionRequest2 = new EncuestaOpcionRequest();
		encuestaOpcionRequest2.setId(2L);
		encuestaOpcionRequest2.setDescripcion("No");
		encuestaOpcionRequest2.setSeleccionada(true);
		
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		encuestaPreguntaRequest.setRespuestas(Arrays.asList(encuestaOpcionRequest, encuestaOpcionRequest2));
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		
		Formulario formulario = new Formulario();
		formulario.setId(1L);
		
		Encuesta encuesta = new Encuesta();
		encuesta.setFormulario(formulario);
		
		when(encuestaRepository.save(encuesta)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		});
	}
	
	/**
	 * Dado: Guardar de encuesta sin persona
	 * Cuando: Informacion correcta
	 * No Regresa Error
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaGuardaEncuestaSinPersonaCuandoInformacionCorrectaNoRegresaError() throws SQLException, DatoInvalidoException
	{
		EncuestaOpcionRequest encuestaOpcionRequest = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("Si");
		encuestaOpcionRequest.setSeleccionada(true);
		
		EncuestaOpcionRequest encuestaOpcionRequest2 = new EncuestaOpcionRequest();
		encuestaOpcionRequest2.setId(2L);
		encuestaOpcionRequest2.setDescripcion("No");
		encuestaOpcionRequest2.setSeleccionada(true);
		
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		encuestaPreguntaRequest.setRespuestas(Arrays.asList(encuestaOpcionRequest, encuestaOpcionRequest2));
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		
		Formulario formulario = new Formulario();
		formulario.setId(1L);
		
		Encuesta encuesta = new Encuesta();
		encuesta.setFormulario(formulario);
		
		Encuesta encuestaSave = new Encuesta();
		encuestaSave.setId(1L);
		encuestaSave.setFormulario(formulario);
		
		when(encuestaRepository.save(encuesta)).thenReturn(encuestaSave);
		
		doNothing().when(respuestaService).guardarTodasRespuesta(encuestaSave, encuestaRequest);
		
		Assertions.assertDoesNotThrow(() -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		}, "Ocurrio Un Error Inesperado");
	}
	
	/**
	 * Dado: Guardar de encuesta con persona
	 * Cuando: Inconsistencias en la informacion enviada a respuestaService
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaGuardaEncuestaConPersonaCuandoInconsistenciasEnLaInformacionEnviadaARespuestaServiceRegresaDatoInvalidoException() throws SQLException, DatoInvalidoException
	{
		EncuestaOpcionRequest encuestaOpcionRequest = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("Si");
		encuestaOpcionRequest.setSeleccionada(true);
		
		EncuestaOpcionRequest encuestaOpcionRequest2 = new EncuestaOpcionRequest();
		encuestaOpcionRequest2.setId(2L);
		encuestaOpcionRequest2.setDescripcion("No");
		encuestaOpcionRequest2.setSeleccionada(true);
		
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		encuestaPreguntaRequest.setRespuestas(Arrays.asList(encuestaOpcionRequest, encuestaOpcionRequest2));
		
		EncuestaPreguntaRequest encuestaPreguntaRequest2 = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest2.setId(2L);
		encuestaPreguntaRequest2.setDescripcion("¿Pregunta Prueba 2?");
		encuestaPreguntaRequest2.setTipoPregunta(TipoPregunta.ABIERTA);
		encuestaPreguntaRequest2.setRespuestaAbierta("Respuesta");
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		preguntas.add(encuestaPreguntaRequest2);
		
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		encuestaRequest.setDatosPersona(personaRequest);
		
		Formulario formulario = new Formulario();
		formulario.setId(1L);
		
		PersonaResponse personaResponse = new PersonaResponse();
		personaResponse.setId(1L);
		personaResponse.setIdentificacion("1");
		personaResponse.setNombres("Test 1");
		personaResponse.setApellidos("Test 1");
		personaResponse.setCorreoElectronico("prueba@test.com");
		
		when(personaService.guardarPersona(personaRequest)).thenReturn(personaResponse);
		
		Persona persona = new Persona();
		persona.setId(1L);	
		
		Encuesta encuesta = new Encuesta();
		encuesta.setFormulario(formulario);
		encuesta.setPersona(persona);
		
		Encuesta encuestaSave = new Encuesta();
		encuestaSave.setId(1L);
		encuestaSave.setFormulario(formulario);
		
		when(encuestaRepository.save(encuesta)).thenReturn(encuestaSave);
		
		doThrow(new DatoInvalidoException()).when(respuestaService).guardarTodasRespuesta(encuestaSave, encuestaRequest);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		});
	}
	
	/**
	 * Dado: Guardar de encuesta con persona
	 * Cuando: Informacion correcta
	 * No Regresa Error
	 * @author Carlos Martin
	 * @version 0.0.1 16/11/2021
	 * @throws DatoInvalidoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaGuardaEncuestaConPersonaCuandoInformacionCorrectaNoRegresaError() throws SQLException, DatoInvalidoException
	{
		EncuestaOpcionRequest encuestaOpcionRequest = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("Si");
		encuestaOpcionRequest.setSeleccionada(true);
		
		EncuestaOpcionRequest encuestaOpcionRequest2 = new EncuestaOpcionRequest();
		encuestaOpcionRequest2.setId(2L);
		encuestaOpcionRequest2.setDescripcion("No");
		encuestaOpcionRequest2.setSeleccionada(true);
		
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		encuestaPreguntaRequest.setRespuestas(Arrays.asList(encuestaOpcionRequest, encuestaOpcionRequest2));
		
		EncuestaPreguntaRequest encuestaPreguntaRequest2 = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest2.setId(2L);
		encuestaPreguntaRequest2.setDescripcion("¿Pregunta Prueba 2?");
		encuestaPreguntaRequest2.setTipoPregunta(TipoPregunta.ABIERTA);
		encuestaPreguntaRequest2.setRespuestaAbierta("Respuesta");
		
		List<EncuestaPreguntaRequest> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaRequest);
		preguntas.add(encuestaPreguntaRequest2);
		
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setTitulo("Formulario 1");
		encuestaRequest.setDescripcion("Formulario Prueba 1");
		encuestaRequest.setPreguntas(preguntas);
		encuestaRequest.setDatosPersona(personaRequest);
		
		Formulario formulario = new Formulario();
		formulario.setId(1L);
		
		PersonaResponse personaResponse = new PersonaResponse();
		personaResponse.setId(1L);
		personaResponse.setIdentificacion("1");
		personaResponse.setNombres("Test 1");
		personaResponse.setApellidos("Test 1");
		personaResponse.setCorreoElectronico("prueba@test.com");
		
		when(personaService.guardarPersona(personaRequest)).thenReturn(personaResponse);
		
		Persona persona = new Persona();
		persona.setId(1L);	
		
		Encuesta encuesta = new Encuesta();
		encuesta.setFormulario(formulario);
		encuesta.setPersona(persona);
		
		Encuesta encuestaSave = new Encuesta();
		encuestaSave.setId(1L);
		encuestaSave.setFormulario(formulario);
		
		when(encuestaRepository.save(encuesta)).thenReturn(encuestaSave);
		
		doNothing().when(respuestaService).guardarTodasRespuesta(encuestaSave, encuestaRequest);
		
		Assertions.assertDoesNotThrow(() -> {
			encuestaServiceImpl.guardarEncuesta(encuestaRequest);
		}, "Ocurrio Un Error Inesperado");
	}
}
