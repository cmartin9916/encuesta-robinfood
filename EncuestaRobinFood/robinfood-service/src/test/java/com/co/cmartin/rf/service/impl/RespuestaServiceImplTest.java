/**
 * @autor Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
package com.co.cmartin.rf.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.co.cmartin.rf.entity.Encuesta;
import com.co.cmartin.rf.entity.Opcion;
import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.entity.Respuesta;
import com.co.cmartin.rf.enums.TipoPregunta;
import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.repository.RespuestaRepository;
import com.co.cmartin.rf.request.EncuestaOpcionRequest;
import com.co.cmartin.rf.request.EncuestaPreguntaRequest;
import com.co.cmartin.rf.request.EncuestaRequest;

/**
 * Pruebas unitarias de la clase RespuestaServiceImpl
 * @author Carlos Martin
 * @version 0.0.1 16/11/2021
 *
 */
@ExtendWith(MockitoExtension.class)
class RespuestaServiceImplTest {

	@Mock
	private RespuestaRepository respuestaRepository;
	@InjectMocks
	private RespuestaServiceImpl respuestaServiceImpl;
	
	/**
	 * Dado: Respuestas para guardar
	 * Cuando: pregunta cerrada no tiene opcion seleccionada
	 * Regresa: DatoInvalidoException
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaRespuestaParaGuardarCuandoPreguntaCerradaNoTieneOpcionSeleccionadaRegresaDatoInvalidoException()
	{ 
		EncuestaPreguntaRequest encuestaPreguntaARequest = new EncuestaPreguntaRequest();
		encuestaPreguntaARequest.setId(1L);
		encuestaPreguntaARequest.setDescripcion("¿Pregunta 1?");
		encuestaPreguntaARequest.setTipoPregunta(TipoPregunta.ABIERTA);
		encuestaPreguntaARequest.setRespuestaAbierta("Respuesta");
		
		EncuestaPreguntaRequest encuestaPreguntaCRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaCRequest.setId(2L);
		encuestaPreguntaCRequest.setDescripcion("¿Pregunta 2?");
		encuestaPreguntaCRequest.setTipoPregunta(TipoPregunta.CERRADA);		
		
		EncuestaOpcionRequest encuestaOpcionRequest = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("Si");
		
		EncuestaOpcionRequest encuestaOpcionRequest2 = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("No");
		
		List<EncuestaOpcionRequest> listaOpciones = new ArrayList<>();
		listaOpciones.add(encuestaOpcionRequest);
		listaOpciones.add(encuestaOpcionRequest2);
		
		encuestaPreguntaCRequest.setRespuestas(listaOpciones);
		
		List<EncuestaPreguntaRequest> listaPreguntas = new ArrayList<>();
		listaPreguntas.add(encuestaPreguntaARequest);
		listaPreguntas.add(encuestaPreguntaCRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setPreguntas(listaPreguntas);
		
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			respuestaServiceImpl.guardarTodasRespuesta(null, encuestaRequest);
		});
	}
	
	/**
	 * Dado: Respuestas para guardar
	 * Cuando: informacion correcta
	 * No Regresa error 
	 * @author Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Test
	void dadaRespuestaParaGuardarCuandoInformacionCorrectaNoRegresaError()
	{ 
		EncuestaPreguntaRequest encuestaPreguntaARequest = new EncuestaPreguntaRequest();
		encuestaPreguntaARequest.setId(1L);
		encuestaPreguntaARequest.setDescripcion("¿Pregunta 1?");
		encuestaPreguntaARequest.setTipoPregunta(TipoPregunta.ABIERTA);
		encuestaPreguntaARequest.setRespuestaAbierta("Respuesta");
		
		EncuestaPreguntaRequest encuestaPreguntaCRequest = new EncuestaPreguntaRequest();
		encuestaPreguntaCRequest.setId(2L);
		encuestaPreguntaCRequest.setDescripcion("¿Pregunta 2?");
		encuestaPreguntaCRequest.setTipoPregunta(TipoPregunta.CERRADA);		
		
		EncuestaOpcionRequest encuestaOpcionRequest = new EncuestaOpcionRequest();
		encuestaOpcionRequest.setId(1L);
		encuestaOpcionRequest.setDescripcion("Si");
		encuestaOpcionRequest.setSeleccionada(true);
		
		EncuestaOpcionRequest encuestaOpcionRequest2 = new EncuestaOpcionRequest();
		encuestaOpcionRequest2.setId(1L);
		encuestaOpcionRequest2.setDescripcion("No");
		
		List<EncuestaOpcionRequest> listaOpciones = new ArrayList<>();
		listaOpciones.add(encuestaOpcionRequest);
		listaOpciones.add(encuestaOpcionRequest2);
		
		encuestaPreguntaCRequest.setRespuestas(listaOpciones);
		
		List<EncuestaPreguntaRequest> listaPreguntas = new ArrayList<>();
		listaPreguntas.add(encuestaPreguntaARequest);
		listaPreguntas.add(encuestaPreguntaCRequest);
		
		EncuestaRequest encuestaRequest = new EncuestaRequest();
		encuestaRequest.setIdFormulario(1L);
		encuestaRequest.setPreguntas(listaPreguntas);
		
		Encuesta encuesta = new Encuesta();
		encuesta.setId(1L);
		
		Pregunta preguntaA = new Pregunta();
		preguntaA.setId(1L);
		
		Respuesta respuestaPreguntaA = new Respuesta();
		respuestaPreguntaA.setEncuesta(encuesta);
		respuestaPreguntaA.setRespuestaAbierta("Respuesta");
		respuestaPreguntaA.setPregunta(preguntaA);
		
		Pregunta preguntaC = new Pregunta();
		preguntaC.setId(2L);
		
		Opcion opcion1 = new Opcion();
		opcion1.setId(1L);
		opcion1.setDescripcion("Si");
		
		Respuesta respuestaPreguntaC = new Respuesta();
		respuestaPreguntaC.setEncuesta(encuesta);
		respuestaPreguntaC.setPregunta(preguntaC);
		respuestaPreguntaC.setRespuestaCerrada(opcion1);
		
		List<Respuesta> respuestas = new ArrayList<>();
		respuestas.add(respuestaPreguntaA);
		respuestas.add(respuestaPreguntaC);
		
		when(respuestaRepository.saveAll(respuestas)).thenReturn(respuestas);
		
		Assertions.assertDoesNotThrow(() -> {
			respuestaServiceImpl.guardarTodasRespuesta(encuesta, encuestaRequest);
		}, "Ocurrio Un Error Inesperado");
	}
}
