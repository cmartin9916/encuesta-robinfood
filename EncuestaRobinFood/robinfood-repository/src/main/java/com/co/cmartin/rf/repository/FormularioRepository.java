/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.co.cmartin.rf.entity.Formulario;

/**
 * Interfaz que extiende de JpaRepository para acceso a datos de Formulario
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
@Transactional
public interface FormularioRepository extends JpaRepository<Formulario, Long> {

	@Query("SELECT f FROM Formulario f WHERE f.titulo = :titulo and f.descripcion = :descripcion")
    public Formulario buscarFormularioPorTituloDescripcion(@Param("titulo") String titulo, @Param("descripcion") String descripcion);
	
	
	@Modifying
	@Query("update Formulario f set f.activo = :activo where f.id = :id")
	public int actualizarEstadoFormulario(@Param(value = "id") Long id, @Param(value = "activo") boolean activo);
}
