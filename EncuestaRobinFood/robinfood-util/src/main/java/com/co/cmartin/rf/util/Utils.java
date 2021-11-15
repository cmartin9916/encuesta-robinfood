/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.util;

import java.util.List;
import java.util.stream.Collectors;

import com.co.cmartin.rf.exception.DatoInvalidoException;

/**
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public class Utils {
	
	private Utils() throws DatoInvalidoException {
		throw new DatoInvalidoException("Clase Utilitaria");
	}
	
	/**
	 * Metodo que valida campo obligatorio
	 * @author Carlos Martin
	 * @param <T>
	 * @param valor Valor
	 * @throws Exception Retorna excepcion si la información es nula o vacia
	 */
	public static <T> void validarListaObligatoria(List<T> valor) throws DatoInvalidoException
	{
		if(valor == null || valor.isEmpty())
		{			
			throw new DatoInvalidoException("La lista ingresada es invalida");
		}
	}
	
	/**
	 * Metodo que valida campo obligatorio y recibe nombre para complementar mensaje de error
	 * @author Carlos Martin
	 * @param <T>
	 * @param valor Valor
	 * @param nombre Nombre del campo
	 * @throws Exception Retorna excepcion si la información es nula o vacia
	 */
	public static <T> void validarListaObligatoria(List<T> valor, String nombre) throws DatoInvalidoException
	{
		if(valor == null || valor.isEmpty())
		{
			StringBuilder mensaje = new StringBuilder();
			mensaje.append("La lista ").append(nombre).append(" es invalida");
			
			throw new DatoInvalidoException(mensaje.toString());
		}
	}
	
	/**
	 * Metodo que valida que un valor exista en la lista enviada
	 * @author Carlos Martin
	 * @param listaValores Lista de valores
	 * @param valor Valor
	 * @throws Exception Retorna excepcion si no se encuentra el registro
	 */
	public static void validarExistenciaValorLista(List<String> listaValores, String valor) throws DatoInvalidoException
	{
		if(!listaValores.stream().map(String::toLowerCase).collect(Collectors.toList()).contains(valor.toLowerCase()))
		{
			StringBuilder mensaje = new StringBuilder();
			mensaje.append("No se encontro el valor: ").append(valor).append(" en la lista");
			
			throw new DatoInvalidoException(mensaje.toString());
		}
	}
}
