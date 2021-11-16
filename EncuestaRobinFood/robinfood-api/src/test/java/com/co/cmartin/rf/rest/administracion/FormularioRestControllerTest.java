/**
 * @autor Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
package com.co.cmartin.rf.rest.administracion;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.FormularioRequest;
import com.co.cmartin.rf.request.FormularioRequestEstado;
import com.co.cmartin.rf.response.FormularioResponse;
import com.co.cmartin.rf.response.PreguntaResponse;
import com.co.cmartin.rf.service.FormularioService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase con pruebas unitarias de la clase FormularioRestController
 * 
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class FormularioRestControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FormularioService formularioService;

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Dado: Consulta de formularios Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaFormulariosCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		FormularioResponse formularioResponse = new FormularioResponse();
		formularioResponse.setId(1L);
		formularioResponse.setTitulo("Formulario 1");
		formularioResponse.setDescripcion("Formulario Pruebas 1");
		
		PreguntaResponse preguntaResponse = new PreguntaResponse();
		preguntaResponse.setId(1L);
		preguntaResponse.setDescripcion("¿Pregunta Prueba 1?");
		preguntaResponse.setTipoPregunta(TipoPregunta.ABIERTA);
		
		PreguntaResponse preguntaResponse2 = new PreguntaResponse();
		preguntaResponse2.setId(2L);
		preguntaResponse2.setDescripcion("¿Pregunta Prueba 2?");
		preguntaResponse2.setTipoPregunta(TipoPregunta.ABIERTA);
		
		List<PreguntaResponse> preguntasFormulario = new ArrayList<>();
		preguntasFormulario.add(preguntaResponse);
		preguntasFormulario.add(preguntaResponse2);
		
		formularioResponse.setPreguntas(preguntasFormulario);

		List<FormularioResponse> formulariosResponse = new ArrayList<>();
		formulariosResponse.add(formularioResponse);

		when(formularioService.listarFormularios()).thenReturn(formulariosResponse);

		mockMvc.perform(get("/formulario")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta de formularios Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaFormulariosCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(formularioService.listarFormularios()).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/formulario")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta de formularios Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaFormulariosCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(formularioService.listarFormularios()).thenThrow(SQLException.class);

		mockMvc.perform(get("/formulario")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaFormularioPorIdCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		FormularioResponse formularioResponse = new FormularioResponse();
		formularioResponse.setId(1L);
		formularioResponse.setTitulo("Formulario 1");
		formularioResponse.setDescripcion("Formulario Pruebas 1");
		
		PreguntaResponse preguntaResponse = new PreguntaResponse();
		preguntaResponse.setId(1L);
		preguntaResponse.setDescripcion("¿Pregunta Prueba 1?");
		preguntaResponse.setTipoPregunta(TipoPregunta.ABIERTA);
		
		PreguntaResponse preguntaResponse2 = new PreguntaResponse();
		preguntaResponse2.setId(2L);
		preguntaResponse2.setDescripcion("¿Pregunta Prueba 2?");
		preguntaResponse2.setTipoPregunta(TipoPregunta.ABIERTA);
		
		List<PreguntaResponse> preguntasFormulario = new ArrayList<>();
		preguntasFormulario.add(preguntaResponse);
		preguntasFormulario.add(preguntaResponse2);
		
		formularioResponse.setPreguntas(preguntasFormulario);

		when(formularioService.consultarFormulario(1L)).thenReturn(formularioResponse);

		mockMvc.perform(get("/formulario/1")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaFormularioPorIdCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(formularioService.consultarFormulario(1L)).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/formulario/1")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaFormularioPorIdCuandoDatoDeEntradaIncorrectoRegresa400BadRequest() throws Exception {
		mockMvc.perform(get("/formulario/test")).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Consulta pregunta por id Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaFormularioPorIdCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(formularioService.consultarFormulario(1L)).thenThrow(SQLException.class);

		mockMvc.perform(get("/formulario/1")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Guardado de pregunta Cuando: Informacion es correcta Regresa: 201 Created
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoFormularioCuandoLaInformacionEsCorrectaRegresa201Created() throws Exception {
		List<Long> preguntas = new ArrayList<>();
		preguntas.add(1L);
		preguntas.add(2L);
		
		FormularioRequest formularioRequest = new FormularioRequest();
		formularioRequest.setTitulo("Formulario 1");
		formularioRequest.setDescripcion("Formulario Pruebas 1");
		formularioRequest.setPreguntas(preguntas);
		
		FormularioResponse formularioResponse = new FormularioResponse();
		formularioResponse.setId(1L);
		formularioResponse.setTitulo("Formulario 1");
		formularioResponse.setDescripcion("Formulario Pruebas 1");
		
		PreguntaResponse preguntaResponse = new PreguntaResponse();
		preguntaResponse.setId(1L);
		preguntaResponse.setDescripcion("¿Pregunta Prueba 1?");
		preguntaResponse.setTipoPregunta(TipoPregunta.ABIERTA);
		
		PreguntaResponse preguntaResponse2 = new PreguntaResponse();
		preguntaResponse2.setId(2L);
		preguntaResponse2.setDescripcion("¿Pregunta Prueba 2?");
		preguntaResponse2.setTipoPregunta(TipoPregunta.ABIERTA);
		
		List<PreguntaResponse> preguntasFormulario = new ArrayList<>();
		preguntasFormulario.add(preguntaResponse);
		preguntasFormulario.add(preguntaResponse2);
		
		formularioResponse.setPreguntas(preguntasFormulario);

		when(formularioService.guardarFormulario(formularioRequest)).thenReturn(formularioResponse);

		String jsonRequest = mapper.writeValueAsString(formularioRequest);

		mockMvc.perform(post("/formulario").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
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
	void dadoGuardadoFormularioCuandoDatoDeEntradaEsIncorrectoRegresa400BadRequest() throws Exception {
		FormularioRequest preguntaRequest = new FormularioRequest();

		String jsonRequest = mapper.writeValueAsString(preguntaRequest);

		mockMvc.perform(post("/formulario").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
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
	void dadoGuardadoFormularioCuandoOcurreErrorInesperadoRegresaRegresa500InternalError() throws Exception {
		List<Long> preguntas = new ArrayList<>();
		preguntas.add(1L);
		preguntas.add(2L);
		
		FormularioRequest formularioRequest = new FormularioRequest();
		formularioRequest.setTitulo("Formulario 1");
		formularioRequest.setDescripcion("Formulario Pruebas 1");
		formularioRequest.setPreguntas(preguntas);

		when(formularioService.guardarFormulario(formularioRequest)).thenThrow(SQLException.class);

		String jsonRequest = mapper.writeValueAsString(formularioRequest);
		mockMvc.perform(post("/formulario").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
	
	/**
	 * Dado: Actualizar estado de formulario Cuando: no actualiza resultados Regresa: 400 Bad request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoActualizarEstadoFormularioCuandoNoActualizaResultadosRegresa400BadRequest() throws Exception {
		
		FormularioRequestEstado formularioRequest = new FormularioRequestEstado();
		formularioRequest.setId(1L);
		formularioRequest.setActivo(false);

		doThrow(new DatoInvalidoException()).when(formularioService).actualizarEstadoFormulario(1L, false);

		String jsonRequest = mapper.writeValueAsString(formularioRequest);
		mockMvc.perform(patch("/formulario/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	/**
	 * Dado: Actualizar estado de formulario Cuando: actualiza resultados Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoActualizarEstadoFormularioCuandoActualizaResultadosRegresa200Ok() throws Exception {
		
		FormularioRequestEstado formularioRequest = new FormularioRequestEstado();
		formularioRequest.setId(1L);
		formularioRequest.setActivo(false);

		doNothing().when(formularioService).actualizarEstadoFormulario(1L, false);

		String jsonRequest = mapper.writeValueAsString(formularioRequest);
		mockMvc.perform(patch("/formulario/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
