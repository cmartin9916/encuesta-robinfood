/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.cmartin.rf.entity.Persona;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.repository.PersonaRepository;
import com.co.cmartin.rf.request.PersonaRequest;
import com.co.cmartin.rf.response.PersonaResponse;
import com.co.cmartin.rf.service.PersonaService;

import lombok.Getter;

/**
 * Clase implementacion de PersonaService
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	@Getter
	private PersonaRepository personaRepository;

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.PersonaService#listarPersonas()
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public List<PersonaResponse> listarPersonas() throws SQLException, DatoNoEncontradoException {
		try {
			List<Persona> personas = getPersonaRepository().findAll();
		
			if(!personas.isEmpty()) {				
				return personas.stream().map(this::mapeoEntidadPersonaAPersonaResponse)
						.collect(Collectors.toList());				
			} else {
				throw new DatoNoEncontradoException("No se encontro informacion de personas");
			}
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de las personas", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.PersonaService#consultarPersona(java.lang.Long)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public PersonaResponse consultarPersona(Long id) throws SQLException, DatoNoEncontradoException {
		try {
			Persona persona = getPersonaRepository().getById(id);
			
			return mapeoEntidadPersonaAPersonaResponse(persona);
		} catch (EntityNotFoundException e) {
			throw new DatoNoEncontradoException("No se encontro informacion asociada a la persona: " + id, e);
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de la persona : " + id, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.co.cmartin.rf.service.PersonaService#guardarPersona(com.co.cmartin.rf.request.PersonaRequest)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public PersonaResponse guardarPersona(PersonaRequest personaRequest) throws SQLException {
		Persona persona = buscarPersonaPorIdentificacion(personaRequest.getIdentificacion());				
		try {
			Persona respuesta = getPersonaRepository().save(mapeoPersonaRequestAEntidadPersona(persona == null ? null : persona.getId(), personaRequest));
			return mapeoEntidadPersonaAPersonaResponse(respuesta);
		} catch (Exception e) {
			throw new SQLException("Error al guardar informacion de la persona: " + personaRequest.getIdentificacion(), e);
		}
	}

	/**
	 * Metodo que busca informacion de persona por identificacion
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param identificacion Identificacion
	 * @return Informacion de la persona
	 * @throws SQLException Ocurre si se presenta una excepcion no controlado al momento de acceder a la capa de datos
	 */
	private Persona buscarPersonaPorIdentificacion(String identificacion) throws SQLException {
		try {
			return getPersonaRepository().buscarPersonaPorIdentificacion(identificacion);
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de la persona : " + identificacion, e);
		}
	}

	/**
	 * Metodo que mapea la informacion de PersonaRequest a Persona
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Id de la persona en caso tal de ya estar registrada
	 * @param personaRequest Informacion de entrada de Persona
	 * @return Entidad Persona
	 */
	private Persona mapeoPersonaRequestAEntidadPersona(Long id, PersonaRequest personaRequest) {
		Persona persona = new Persona();
		persona.setIdentificacion(personaRequest.getIdentificacion());
		persona.setNombres(personaRequest.getNombres());
		persona.setApellidos(personaRequest.getApellidos());
		persona.setCorreoElectronico(personaRequest.getCorreoElectronico());
		persona.setFechaUltimaModificacion(new Date());
		if (id != null) {
			persona.setId(id);
		}
		return persona;
	}
	
	/**
	 * Metodo que mapea la informacion de Persona a PersonaResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param persona Entidad Persona
	 * @return Informacion de salida para Persona
	 */
	private PersonaResponse mapeoEntidadPersonaAPersonaResponse(Persona persona) {
		PersonaResponse personaResponse = new PersonaResponse();
		personaResponse.setId(persona.getId());
		personaResponse.setIdentificacion(persona.getIdentificacion());
		personaResponse.setNombres(persona.getNombres());
		personaResponse.setApellidos(persona.getApellidos());
		personaResponse.setCorreoElectronico(persona.getCorreoElectronico());
		
		return personaResponse;
	}
}
