/**
 * @autor Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
package com.co.cmartin.rf.rest.administracion;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.PreguntaRequest;
import com.co.cmartin.rf.response.OpcionResponse;
import com.co.cmartin.rf.response.PreguntaResponse;
import com.co.cmartin.rf.service.PreguntaService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase con pruebas unitarias de la clase PreguntaRestController
 * 
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class PreguntaRestControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PreguntaService preguntaService;

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Dado: Consulta de preguntas Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPreguntasCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		PreguntaResponse preguntaAResponse = new PreguntaResponse();
		preguntaAResponse.setId(1L);
		preguntaAResponse.setDescripcion("¿Pruebas 1?");
		preguntaAResponse.setTipoPregunta(TipoPregunta.ABIERTA);
		
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(1L);
		opcionResponse.setDescripcion("Si");
		
		List<OpcionResponse> opcionesPregunta = new ArrayList<>();
		opcionesPregunta.add(opcionResponse);
		
		PreguntaResponse preguntaCResponse = new PreguntaResponse();
		preguntaCResponse.setId(1L);
		preguntaCResponse.setDescripcion("¿Pruebas 2?");
		preguntaCResponse.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaCResponse.setOpciones(opcionesPregunta);

		List<PreguntaResponse> preguntasResponse = new ArrayList<>();
		preguntasResponse.add(preguntaAResponse);
		preguntasResponse.add(preguntaCResponse);

		when(preguntaService.listarPreguntas()).thenReturn(preguntasResponse);

		mockMvc.perform(get("/pregunta")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta de preguntas Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPreguntasCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(preguntaService.listarPreguntas()).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/pregunta")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta de preguntas Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPreguntasCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(preguntaService.listarPreguntas()).thenThrow(SQLException.class);

		mockMvc.perform(get("/pregunta")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPreguntaPorIdCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		PreguntaResponse preguntaAResponse = new PreguntaResponse();
		preguntaAResponse.setId(1L);
		preguntaAResponse.setDescripcion("¿Pruebas 1?");
		preguntaAResponse.setTipoPregunta(TipoPregunta.ABIERTA);

		when(preguntaService.consultarPregunta(1L)).thenReturn(preguntaAResponse);

		mockMvc.perform(get("/pregunta/1")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPreguntaPorIdCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(preguntaService.consultarPregunta(1L)).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/pregunta/1")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPreguntaPorIdCuandoDatoDeEntradaIncorrectoRegresa400BadRequest() throws Exception {
		mockMvc.perform(get("/pregunta/test")).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPreguntaPorIdCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(preguntaService.consultarPregunta(1L)).thenThrow(SQLException.class);

		mockMvc.perform(get("/pregunta/1")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Guardado de pregunta Cuando: Informacion es correcta Regresa: 201 Created
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoPreguntaCuandoLaInformacionEsCorrectaRegresa201Created() throws Exception {
		List<Long> opcionesRequest = new ArrayList<>();
		opcionesRequest.add(1L);
		
		PreguntaRequest preguntaRequest = new PreguntaRequest();
		preguntaRequest.setDescripcion("¿Pruebas 2?");
		preguntaRequest.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaRequest.setOpciones(opcionesRequest);
		
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(1L);
		opcionResponse.setDescripcion("Si");
		
		List<OpcionResponse> opcionesPregunta = new ArrayList<>();
		opcionesPregunta.add(opcionResponse);
		
		PreguntaResponse preguntaCResponse = new PreguntaResponse();
		preguntaCResponse.setId(1L);
		preguntaCResponse.setDescripcion("¿Pruebas 2?");
		preguntaCResponse.setTipoPregunta(TipoPregunta.CERRADA);
		preguntaCResponse.setOpciones(opcionesPregunta);	

		when(preguntaService.guardarPregunta(preguntaRequest)).thenReturn(preguntaCResponse);

		String jsonRequest = mapper.writeValueAsString(preguntaRequest);

		mockMvc.perform(post("/pregunta").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
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
	void dadoGuardadoPreguntaCuandoDatoDeEntradaEsIncorrectoRegresa400BadRequest() throws Exception {
		PreguntaRequest preguntaRequest = new PreguntaRequest();

		String jsonRequest = mapper.writeValueAsString(preguntaRequest);

		mockMvc.perform(post("/pregunta").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
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
	void dadoGuardadoPreguntaCuandoOcurreErrorInesperadoRegresaRegresa500InternalError() throws Exception {
		PreguntaRequest preguntaARequest = new PreguntaRequest();
		preguntaARequest.setDescripcion("¿Pruebas 1?");
		preguntaARequest.setTipoPregunta(TipoPregunta.ABIERTA);

		when(preguntaService.guardarPregunta(preguntaARequest)).thenThrow(SQLException.class);

		String jsonRequest = mapper.writeValueAsString(preguntaARequest);
		mockMvc.perform(post("/pregunta").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
}
