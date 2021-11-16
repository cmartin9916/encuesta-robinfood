/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
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

import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.OpcionRequest;
import com.co.cmartin.rf.response.OpcionResponse;
import com.co.cmartin.rf.service.OpcionService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase con pruebas unitarias de la clase OpcionRestController
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class OpcionRestControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private OpcionService opcionService;

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Dado: Consulta de opciones Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaOpcionesCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(1L);
		opcionResponse.setDescripcion("SI");

		List<OpcionResponse> opcionesResponse = new ArrayList<>();
		opcionesResponse.add(opcionResponse);

		when(opcionService.listarOpciones()).thenReturn(opcionesResponse);

		mockMvc.perform(get("/opcion")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta de opciones Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaOpcionesCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(opcionService.listarOpciones()).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/opcion")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta de opciones Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaOpcionesCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(opcionService.listarOpciones()).thenThrow(SQLException.class);

		mockMvc.perform(get("/opcion")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Consulta opcion por id Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaOpcionPorIdCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(1L);
		opcionResponse.setDescripcion("SI");

		when(opcionService.consultarOpcion(1L)).thenReturn(opcionResponse);

		mockMvc.perform(get("/opcion/1")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta opcion por id Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaOpcionPorIdCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(opcionService.consultarOpcion(1L)).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/opcion/1")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta opcion por id Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaOpcionPorIdCuandoDatoDeEntradaIncorrectoRegresa400BadRequest() throws Exception {
		mockMvc.perform(get("/opcion/test")).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Consulta opcion por id Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaOpcionPorIdCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(opcionService.consultarOpcion(1L)).thenThrow(SQLException.class);

		mockMvc.perform(get("/opcion/1")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Guardado de opcion Cuando: Informacion es correcta Regresa: 201 Created
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoOpcionCuandoLaInformacionEsCorrectaRegresa201Created() throws Exception {
		OpcionRequest opcionRequest = new OpcionRequest();
		opcionRequest.setDescripcion("SI");

		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(1L);
		opcionResponse.setDescripcion("SI");

		when(opcionService.guardarOpcion(opcionRequest)).thenReturn(opcionResponse);

		String jsonRequest = mapper.writeValueAsString(opcionRequest);

		mockMvc.perform(post("/opcion").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	/**
	 * Dado: Guardado de opcion Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoOpcionCuandoDatoDeEntradaEsIncorrectoRegresa400BadRequest() throws Exception {
		OpcionRequest opcionRequest = new OpcionRequest();

		String jsonRequest = mapper.writeValueAsString(opcionRequest);

		mockMvc.perform(post("/opcion").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Guardado de opcion Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoOpcionCuandoOcurreErrorInesperadoRegresaRegresa500InternalError() throws Exception {
		OpcionRequest opcionRequest = new OpcionRequest();
		opcionRequest.setDescripcion("SI");

		when(opcionService.guardarOpcion(opcionRequest)).thenThrow(SQLException.class);

		String jsonRequest = mapper.writeValueAsString(opcionRequest);
		mockMvc.perform(post("/opcion").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
}
