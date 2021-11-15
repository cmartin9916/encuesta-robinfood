/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.co.cmartin.rf.entity.Pregunta;
import com.co.cmartin.rf.enums.TipoPregunta;

/**
 * Interfaz que extiende de JpaRepository para acceso a datos de Preguntas
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface PreguntaRepository extends JpaRepository<Pregunta, Long>  {

	@Query("SELECT p FROM Pregunta p WHERE p.descripcion = :descripcion and p.tipoPregunta = :tipoPregunta")
    public Pregunta buscarPreguntaPorDescripcionTipo(@Param("descripcion") String descripcion, @Param("tipoPregunta") TipoPregunta tipoPregunta);
	
}
