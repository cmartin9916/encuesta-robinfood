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

import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.request.PersonaRequest;
import com.co.cmartin.rf.response.PersonaResponse;
import com.co.cmartin.rf.service.PersonaService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase con pruebas unitarias de la clase PersonaRestController
 * 
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonaRestControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PersonaService personaService;

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Dado: Consulta de personas Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPersonaesCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		PersonaResponse personaResponse = new PersonaResponse();
		personaResponse.setId(1L);
		personaResponse.setIdentificacion("1");
		personaResponse.setNombres("Test 1");
		personaResponse.setApellidos("Test 1");
		personaResponse.setCorreoElectronico("prueba@test.com");

		List<PersonaResponse> personasResponse = new ArrayList<>();
		personasResponse.add(personaResponse);

		when(personaService.listarPersonas()).thenReturn(personasResponse);

		mockMvc.perform(get("/persona")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta de personas Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPersonaesCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(personaService.listarPersonas()).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/persona")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta de personas Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPersonaesCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(personaService.listarPersonas()).thenThrow(SQLException.class);

		mockMvc.perform(get("/persona")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Consulta persona por id Cuando: Hay informacion registrada Regresa: 200 Ok
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPersonaPorIdCuandoHayInformacionRegistradaRegresa200Ok() throws Exception {
		PersonaResponse personaResponse = new PersonaResponse();
		personaResponse.setId(1L);
		personaResponse.setIdentificacion("1");
		personaResponse.setNombres("Test 1");
		personaResponse.setApellidos("Test 1");
		personaResponse.setCorreoElectronico("prueba@test.com");

		when(personaService.consultarPersona(1L)).thenReturn(personaResponse);

		mockMvc.perform(get("/persona/1")).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Dado: Consulta persona por id Cuando: No Hay informacion registrada Regresa: 204 No Content
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPersonaPorIdCuandoNoHayInformacionRegistradaRegresa204NoContent() throws Exception {
		when(personaService.consultarPersona(1L)).thenThrow(DatoNoEncontradoException.class);

		mockMvc.perform(get("/persona/1")).andDo(print()).andExpect(status().isNoContent());
	}

	/**
	 * Dado: Consulta persona por id Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPersonaPorIdCuandoDatoDeEntradaIncorrectoRegresa400BadRequest() throws Exception {
		mockMvc.perform(get("/persona/test")).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Consulta persona por id Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadaConsultaPersonaPorIdCuandoOcurreErrorInesperadoRegresa500InternalError() throws Exception {
		when(personaService.consultarPersona(1L)).thenThrow(SQLException.class);

		mockMvc.perform(get("/persona/1")).andDo(print()).andExpect(status().isInternalServerError());
	}

	/**
	 * Dado: Guardado de persona Cuando: Informacion es correcta Regresa: 201 Created
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoPersonaCuandoLaInformacionEsCorrectaRegresa201Created() throws Exception {
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");

		PersonaResponse personaResponse = new PersonaResponse();
		personaResponse.setId(1L);
		personaResponse.setIdentificacion("1");
		personaResponse.setNombres("Test 1");
		personaResponse.setApellidos("Test 1");
		personaResponse.setCorreoElectronico("prueba@test.com");

		when(personaService.guardarPersona(personaRequest)).thenReturn(personaResponse);

		String jsonRequest = mapper.writeValueAsString(personaRequest);

		mockMvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	/**
	 * Dado: Guardado de persona Cuando: dato de entrada incorrecto Regresa: 400 Bad Request
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoPersonaCuandoDatoDeEntradaEsIncorrectoRegresa400BadRequest() throws Exception {
		PersonaRequest personaRequest = new PersonaRequest();

		String jsonRequest = mapper.writeValueAsString(personaRequest);

		mockMvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	/**
	 * Dado: Guardado de persona Cuando: ocurre error inesperado Regresa: 500 Internal Error
	 * 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws Exception
	 */
	@Test
	void dadoGuardadoPersonaCuandoOcurreErrorInesperadoRegresaRegresa500InternalError() throws Exception {
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");

		when(personaService.guardarPersona(personaRequest)).thenThrow(SQLException.class);

		String jsonRequest = mapper.writeValueAsString(personaRequest);
		mockMvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}
}
