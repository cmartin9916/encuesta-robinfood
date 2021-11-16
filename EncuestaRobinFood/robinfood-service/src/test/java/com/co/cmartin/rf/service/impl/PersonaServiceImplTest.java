/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.co.cmartin.rf.entity.Persona;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.exception.SQLException;
import com.co.cmartin.rf.repository.PersonaRepository;
import com.co.cmartin.rf.request.PersonaRequest;
import com.co.cmartin.rf.response.PersonaResponse;

/**
 * Pruebas unitarias de la clase PersonaServiceImpl
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@ExtendWith(MockitoExtension.class)
class PersonaServiceImplTest {
	@Mock
	private PersonaRepository personaRepository;
	@InjectMocks
	private PersonaServiceImpl personaServiceImpl;
	
	/**
	 * Dado: Consulta de personas
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaPersonaesCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(personaRepository.findAll()).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			personaServiceImpl.listarPersonas();
		});
	}
	
	/**
	 * Dado: Consulta de personas
	 * Cuando: no hay informacion registrada
	 * Regresa: DatoNoEncontradoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaPersonaesCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(personaRepository.findAll()).thenReturn(new ArrayList<>());
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			personaServiceImpl.listarPersonas();
		});
	}
	
	/**
	 * Dado: Consulta de personas
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de PersonaResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaPersonaesCuandoHayInformacionRegistradaRegresaListaPersonaResponse() throws SQLException, DatoNoEncontradoException
	{
		Persona persona1 = new Persona();
		persona1.setId(1L);
		persona1.setIdentificacion("1");
		persona1.setNombres("Test 1");
		persona1.setApellidos("Test 1");
		persona1.setCorreoElectronico("prueba@test.com");
		
		Persona persona2 = new Persona();
		persona2.setId(2L);
		persona2.setIdentificacion("2");
		persona2.setNombres("Test 2");
		persona2.setApellidos("Test 2");
		persona2.setCorreoElectronico("prueba2@test.com");
		
		List<Persona> personas = new ArrayList<>();
		personas.add(persona1);
		personas.add(persona2);
		
		when(personaRepository.findAll()).thenReturn(personas);
				
		List<PersonaResponse> respuesta = personaServiceImpl.listarPersonas();
		assertEquals(2, respuesta.size());
		assertEquals(1L, respuesta.get(0).getId());
		assertEquals(2L, respuesta.get(1).getId());
		assertEquals("1", respuesta.get(0).getIdentificacion());
		assertEquals("2", respuesta.get(1).getIdentificacion());
	}
	
	/**
	 * Dado: Consulta una persona por id
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaPersonaPorIdCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{
		when(personaRepository.getById(1L)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			personaServiceImpl.consultarPersona(1L);
		});
	}
	
	/**
	 * Dado: Consulta una persona por id
	 * Cuando: no hay informacion registrada
	 * Regresa: DatoNoEncontradoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaConsultaPersonaPorIdCuandoNoHayInformacionRegistradaRegresaDatoNoEncontradoException()
	{
		when(personaRepository.getById(1L)).thenThrow(EntityNotFoundException.class);
		
		Assertions.assertThrows(DatoNoEncontradoException.class, () -> {
			personaServiceImpl.consultarPersona(1L);
		});
	}
	
	/**
	 * Dado: Consulta de personas
	 * Cuando: hay informacion registrada
	 * Regresa: Listado de PersonaResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 */
	@Test
	void dadaConsultaPersonaPorIdCuandoHayInformacionRegistradaRegresaPersonaResponse() throws SQLException, DatoNoEncontradoException
	{
		Persona persona1 = new Persona();
		persona1.setId(1L);
		persona1.setIdentificacion("1");
		persona1.setNombres("Test 1");
		persona1.setApellidos("Test 1");
		persona1.setCorreoElectronico("prueba@test.com");
		
		when(personaRepository.getById(1L)).thenReturn(persona1);
				
		PersonaResponse respuesta = personaServiceImpl.consultarPersona(1L);
		assertEquals(1, respuesta.getId());
		assertEquals("1", respuesta.getIdentificacion());
		assertEquals("Test 1", respuesta.getNombres());
		assertEquals("Test 1", respuesta.getApellidos());
		assertEquals("prueba@test.com", respuesta.getCorreoElectronico());
	}
	
	/**
	 * Dado: Guardado de persona
	 * Cuando: ocurre un error en la capa repository
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadoGuardadoPersonaCuandoOcurreErrorEnLaCapaRepositoryRegresaSQLException()
	{		
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");
		
		Persona persona = new Persona();
		persona.setIdentificacion("1");
		persona.setNombres("Test 1");
		persona.setApellidos("Test 1");
		persona.setCorreoElectronico("prueba@test.com");
		
		when(personaRepository.save(persona)).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			personaServiceImpl.guardarPersona(personaRequest);
		});
	}
	
	/**
	 * Dado: Guardado de persona
	 * Cuando: error inesperado al consultar persona
	 * Regresa: SQLException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoPersonaCuandoErrorInesperadoConsultarPersonaRegresaSQLException() throws SQLException
	{
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");
		
		Persona personaConsulta = new Persona();
		personaConsulta.setId(1L);
		personaConsulta.setIdentificacion("1");
		personaConsulta.setNombres("Test 1");
		personaConsulta.setApellidos("Test 1");
		personaConsulta.setCorreoElectronico("prueba");
		
		Persona persona = new Persona();
		persona.setId(1L);
		persona.setIdentificacion("1");
		persona.setNombres("Test 1");
		persona.setApellidos("Test 1");
		persona.setCorreoElectronico("prueba@test.com");
		
		Persona personaSave = new Persona();
		personaSave.setId(1L);
		personaSave.setIdentificacion("1");
		personaSave.setNombres("Test 1");
		personaSave.setApellidos("Test 1");
		personaSave.setCorreoElectronico("prueba@test.com");
		
		when(personaRepository.buscarPersonaPorIdentificacion("1")).thenThrow(PersistenceException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			personaServiceImpl.guardarPersona(personaRequest);
		});
	}
	
	/**
	 * Dado: Guardado de persona
	 * Cuando: ya esta registrada (actualiza)
	 * Regresa: Persona
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws SQLException 
	 */
	@Test
	void dadoGuardadoPersonaCuandoYaEstaRegistradaActualizaRegresaPersona() throws SQLException
	{
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");
		
		Persona personaConsulta = new Persona();
		personaConsulta.setId(1L);
		personaConsulta.setIdentificacion("1");
		personaConsulta.setNombres("Test 1");
		personaConsulta.setApellidos("Test 1");
		personaConsulta.setCorreoElectronico("prueba");
		
		Persona persona = new Persona();
		persona.setId(1L);
		persona.setIdentificacion("1");
		persona.setNombres("Test 1");
		persona.setApellidos("Test 1");
		persona.setCorreoElectronico("prueba@test.com");
		
		Persona personaSave = new Persona();
		personaSave.setId(1L);
		personaSave.setIdentificacion("1");
		personaSave.setNombres("Test 1");
		personaSave.setApellidos("Test 1");
		personaSave.setCorreoElectronico("prueba@test.com");
		
		when(personaRepository.buscarPersonaPorIdentificacion("1")).thenReturn(personaConsulta);
		
		when(personaRepository.save(persona)).thenReturn(personaSave);
				
		PersonaResponse respuesta = personaServiceImpl.guardarPersona(personaRequest);
		assertEquals(1, respuesta.getId());
		assertEquals("1", respuesta.getIdentificacion());
		assertEquals("Test 1", respuesta.getNombres());
		assertEquals("Test 1", respuesta.getApellidos());
		assertEquals("prueba@test.com", respuesta.getCorreoElectronico());
	}
	
	/**
	 * Dado: Guardado de persona
	 * Cuando: la informacion es correcta
	 * Regresa: Persona
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @throws DatoNoEncontradoException 
	 * @throws SQLException 
	 * @throws DatoInvalidoException 
	 */
	@Test
	void dadaGuardadoPersonaCuandoLaInformacionEsCorrectaRegresaPersona() throws SQLException, DatoNoEncontradoException, DatoInvalidoException
	{
		PersonaRequest personaRequest = new PersonaRequest();
		personaRequest.setIdentificacion("1");
		personaRequest.setNombres("Test 1");
		personaRequest.setApellidos("Test 1");
		personaRequest.setCorreoElectronico("prueba@test.com");
		
		Persona persona = new Persona();
		persona.setIdentificacion("1");
		persona.setNombres("Test 1");
		persona.setApellidos("Test 1");
		persona.setCorreoElectronico("prueba@test.com");
		
		Persona personaSave = new Persona();
		personaSave.setId(1L);
		personaSave.setIdentificacion("1");
		personaSave.setNombres("Test 1");
		personaSave.setApellidos("Test 1");
		personaSave.setCorreoElectronico("prueba@test.com");
		
		when(personaRepository.save(persona)).thenReturn(personaSave);
				
		PersonaResponse respuesta = personaServiceImpl.guardarPersona(personaRequest);
		assertEquals(1, respuesta.getId());
		assertEquals("1", respuesta.getIdentificacion());
		assertEquals("Test 1", respuesta.getNombres());
		assertEquals("Test 1", respuesta.getApellidos());
		assertEquals("prueba@test.com", respuesta.getCorreoElectronico());
	}
}
