/**
 * @autor Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
package com.co.cmartin.rf.rest.encuesta;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.EncuestaOpcionRequest;
import com.co.cmartin.rf.request.EncuestaPreguntaRequest;
import com.co.cmartin.rf.request.EncuestaRequest;
import com.co.cmartin.rf.request.PersonaRequest;
import com.co.cmartin.rf.response.EncuestaOpcionResponse;
import com.co.cmartin.rf.response.EncuestaPreguntaResponse;
import com.co.cmartin.rf.response.EncuestaResponse;
import com.co.cmartin.rf.service.EncuestaService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase con pruebas unitarias de la clase EncuestaRestController
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class EncuestaRestControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EncuestaService encuestaService;

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Dado: Consulta encuesta por id Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaEncuestaPorIdCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		EncuestaOpcionResponse encuestaOpcionResponse = new EncuestaOpcionResponse();
		encuestaOpcionResponse.setId(1L);
		encuestaOpcionResponse.setDescripcion("Si");
		encuestaOpcionResponse.setSeleccionada(true);
		
		EncuestaOpcionResponse encuestaOpcionResponse2 = new EncuestaOpcionResponse();
		encuestaOpcionResponse2.setId(2L);
		encuestaOpcionResponse2.setDescripcion("No");
		encuestaOpcionResponse2.setSeleccionada(true);
		
		EncuestaPreguntaResponse encuestaPreguntaResponse = new EncuestaPreguntaResponse();
		encuestaPreguntaResponse.setId(1L);
		encuestaPreguntaResponse.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaResponse.setTipoPregunta(TipoPregunta.CERRADA);
		encuestaPreguntaResponse.setRespuestas(Arrays.asList(encuestaOpcionResponse, encuestaOpcionResponse2));
		
		EncuestaPreguntaResponse encuestaPreguntaResponse2 = new EncuestaPreguntaResponse();
		encuestaPreguntaResponse2.setId(2L);
		encuestaPreguntaResponse2.setDescripcion("¿Pregunta Prueba 2?");
		encuestaPreguntaResponse2.setTipoPregunta(TipoPregunta.ABIERTA);
		encuestaPreguntaResponse2.setRespuestaAbierta("Respuesta");
		
		List<EncuestaPreguntaResponse> preguntas = new ArrayList<>();
		preguntas.add(encuestaPreguntaResponse);
		preguntas.add(encuestaPreguntaResponse2);
		
		EncuestaResponse encuestaResponse = new EncuestaResponse();
		encuestaResponse.setIdFormulario(1L);
		encuestaResponse.setTitulo("Formulario 1");
		encuestaResponse.setDescripcion("Formulario Prueba 1");
		encuestaResponse.setPreguntas(preguntas);

		when(encuestaService.consultarFormulario(1L)).thenReturn(encuestaResponse);

		mockMvc.perform(get("/encuesta/1")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaEncuestaPorIdCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(encuestaService.consultarFormulario(1L)).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/encuesta/1")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaEncuestaPorIdCuandoDatoDeEntradaIncorrectoRegresa400BadRequest() throws Exception {
		mockMvc.perform(get("/encuesta/test")).andDo(print()).andExpect(status().isBadRequest());
	}
	
	/**
	 * Dado: Consulta pregunta por id Cuando: se identifica dato invalido en capa de negocio Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaEncuestaPorIdCuandoSeIdentificaDatoInvalidoEnCapaNegocioRegresa400BadRequest() throws Exception {
		when(encuestaService.consultarFormulario(1L)).thenThrow(DatoInvalidoException.class);

		mockMvc.perform(get("/encuesta/1")).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaEncuestaPorIdCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(encuestaService.consultarFormulario(1L)).thenThrow(SQLException.class);

		mockMvc.perform(get("/encuesta/1")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Guardado de pregunta Cuando: Informacion es correcta Regresa: 201 Created
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoEncuestaCuandoLaInformacionEsCorrectaRegresa201Created() throws Exception {
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

		doNothing().when(encuestaService).guardarEncuesta(encuestaRequest);

		String jsonRequest = mapper.writeValueAsString(encuestaRequest);

		mockMvc.perform(post("/encuesta").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	/**
	 * Dado: Guardado de pregunta Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoEncuestaCuandoDatoDeEntradaEsIncorrectoRegresa400BadRequest() throws Exception {
		EncuestaRequest preguntaRequest = new EncuestaRequest();

		String jsonRequest = mapper.writeValueAsString(preguntaRequest);

		mockMvc.perform(post("/encuesta").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	/**
	 * Dado: Guardado de pregunta Cuando: se identifica dato invalido en capa de negocio Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoEncuestaCuandoSeIdentificaDatoInvalidoEnCapaNegocioRegresa400BadRequest() throws Exception {
		
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		
		EncuestaPreguntaRequest encuestaPreguntaRequest2 = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest2.setId(2L);
		encuestaPreguntaRequest2.setDescripcion("¿Pregunta Prueba 2?");
		encuestaPreguntaRequest2.setTipoPregunta(TipoPregunta.ABIERTA);
		
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

		doThrow(new DatoInvalidoException()).when(encuestaService).guardarEncuesta(encuestaRequest);

		String jsonRequest = mapper.writeValueAsString(encuestaRequest);

		mockMvc.perform(post("/encuesta").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Guardado de pregunta Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoEncuestaCuandoOcurreErrorInesperadoRegresaRegresa500InternalError() throws Exception {
		EncuestaPreguntaRequest encuestaPreguntaRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest.setId(1L);
		encuestaPreguntaRequest.setDescripcion("¿Pregunta Prueba 1?");
		encuestaPreguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		
		EncuestaPreguntaRequest encuestaPreguntaRequest2 = new EncuestaPreguntaRequest();
		encuestaPreguntaRequest2.setId(2L);
		encuestaPreguntaRequest2.setDescripcion("¿Pregunta Prueba 2?");
		encuestaPreguntaRequest2.setTipoPregunta(TipoPregunta.ABIERTA);
		
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

		doThrow(new SQLException()).when(encuestaService).guardarEncuesta(encuestaRequest);

		String jsonRequest = mapper.writeValueAsString(encuestaRequest);
		mockMvc.perform(post("/encuesta").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
}
