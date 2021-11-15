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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.co.cmartin.rf.entity.Opcion;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.exception.DatoNoEncontradoException;
import com.co.cmartin.rf.repository.OpcionRepository;
import com.co.cmartin.rf.request.OpcionRequest;
import com.co.cmartin.rf.response.OpcionResponse;
import com.co.cmartin.rf.service.OpcionService;

import lombok.Getter;

/**
 * Clase implementacion de OpcionService
 * 
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Service
public class OpcionServiceImpl implements OpcionService {
	
	@Autowired
	@Getter
	private OpcionRepository opcionRepository;

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.OpcionService#listarOpciones()
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public List<OpcionResponse> listarOpciones() throws SQLException, DatoNoEncontradoException {
		try {
			List<Opcion> opciones = getOpcionRepository().findAll();
		
			if(!opciones.isEmpty()) {				
				return opciones.stream().map(this::mapeoEntidadOpcionAOpcionResponse)
						.collect(Collectors.toList());				
			} else {
				throw new DatoNoEncontradoException("No se encontro informacion de opciones");
			}
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de las opciones", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.OpcionService#consultarOpcion(java.lang.Long)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public OpcionResponse consultarOpcion(Long id) throws SQLException, DatoNoEncontradoException {
		try {
			Opcion opcion = getOpcionRepository().getById(id);
			
			return mapeoEntidadOpcionAOpcionResponse(opcion);
		} catch (EntityNotFoundException e) {
			throw new DatoNoEncontradoException("No se encontro informacion asociada a la opcion: " + id, e);
		} catch (Exception e) {
			throw new SQLException("Error al consultar informacion de la opcion: " + id, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.co.cmartin.rf.service.OpcionService#guardarOpcion(com.co.cmartin.rf.request.OpcionRequest)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public OpcionResponse guardarOpcion(OpcionRequest opcionRequest) throws SQLException, DatoInvalidoException {
		try {
			Opcion respuesta = getOpcionRepository().save(mapeoOpcionRequestAEntidadOpcion(opcionRequest));
			
			return mapeoEntidadOpcionAOpcionResponse(respuesta);
		} catch (DataIntegrityViolationException e) {
			throw new DatoInvalidoException("La opcion: '" + opcionRequest.getDescripcion() + "' ya se encuentra registrada", e);
		} catch (Exception e) {
			throw new SQLException("Error al registrar opcion: " + opcionRequest.getDescripcion(), e);
		}
	}
	
	/**
	 * Metodo que mapea la informacion de OpcionRequest a Opcion
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param id Id de la opcion en caso tal de ya estar registrada
	 * @param opcionRequest Informacion de entrada de Opcion
	 * @return Entidad Opcion
	 */
	private Opcion mapeoOpcionRequestAEntidadOpcion(OpcionRequest opcionRequest) {
		Opcion opcion = new Opcion();
		opcion.setDescripcion(opcionRequest.getDescripcion());
		opcion.setFechaUltimaModificacion(new Date());
		
		return opcion;
	}
	
	/**
	 * Metodo que mapea la informacion de Opcion a OpcionResponse
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 * @param opcion Entidad Opcion
	 * @return Informacion de salida para Opcion
	 */
	private OpcionResponse mapeoEntidadOpcionAOpcionResponse(Opcion opcion) {
		OpcionResponse opcionResponse = new OpcionResponse();
		opcionResponse.setId(opcion.getId());
		opcionResponse.setDescripcion(opcion.getDescripcion());
		
		return opcionResponse;
	}

}
