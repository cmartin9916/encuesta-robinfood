/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.co.cmartin.rf.exception.DatoInvalidoException;
import com.co.cmartin.rf.util.Utils;

/**
 * Clase con pruebas unitarias de la clase utilitaria Utils
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
class UtilsTest {
	
	@Test
	@DisplayName("Valida Lista Obligatoria Cuando Se Envía Null")
    void validarListaObligatoriaCuandoEsNull() throws Exception 
	{
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			Utils.validarListaObligatoria(null);
		});
    }
	
	@Test
	@DisplayName("Valida Lista Obligatoria Cuando Se Envía Vacia")
    void validarListaObligatoriaCuandoEsVacia() throws Exception 
	{
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			Utils.validarListaObligatoria(new ArrayList<String>(0));
		});
    }
	
	@Test
	@DisplayName("Valida Lista Obligatoria Con Nombre Cuando Se Envía Null")
    void validarListaObligatoriaConNombreCuandoEsNull() throws Exception 
	{
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			Utils.validarListaObligatoria(null, "Lista Prueba");
		});
    }
	
	@Test
	@DisplayName("Valida Lista Obligatoria Cuando Se Envía Vacia")
    void validarListaObligatoriaConNombreCuandoEsVacia() throws Exception 
	{
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			Utils.validarListaObligatoria(new ArrayList<String>(0), "Lista Prueba");
		});
    }
	
	@Test
	@DisplayName("Valida Existencia De Valor En Lista Cuando No Encuentra Coincidencias")
    void validarExistenciaValorEnListaCuandoNoEncuentraCoincidencia() throws Exception 
	{
		List<String> valores = Arrays.asList("Prueba", "Test");
		Assertions.assertThrows(DatoInvalidoException.class, () -> {
			Utils.validarExistenciaValorLista(valores, "PruebaTest");
		});
    }
	
	@Test
	@DisplayName("Valida Existencia De Valor En Lista Cuando Encuentra Coincidencias")
    void validarExistenciaValorEnListaCuandoEncuentraCoincidencia() throws Exception 
	{
		List<String> valores = Arrays.asList("Prueba", "Test");
		
		Assertions.assertDoesNotThrow(() -> {
			Utils.validarExistenciaValorLista(valores, "Test");
		}, "Ocurrio Un Error Inesperado");
    }
	
	@Test
	@DisplayName("Valida Lista Obligatoria Cuando Se Envía Informacion")
    void validarListaObligatoriaCuandoSeEnviaInformacion() throws Exception 
	{
		Assertions.assertDoesNotThrow(() -> {
			Utils.validarListaObligatoria(Arrays.asList("Prueba", "Test"));
		}, "Ocurrio Un Error Inesperado");
    }
	
	@Test
	@DisplayName("Valida Lista Obligatoria Con Nombre Cuando Se Envía informacion")
    void validarListaObligatoriaConNombreCuandoSeEnviaInformacion() throws Exception 
	{
		Assertions.assertDoesNotThrow(() -> {
			Utils.validarListaObligatoria(Arrays.asList("Prueba", "Test"), "Lista Prueba");
		}, "Ocurrio Un Error Inesperado");
    }
	
}
