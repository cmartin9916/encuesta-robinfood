/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.cmartin.rf.entity.Pregunta;

/**
 * Interfaz que extiende de JpaRepository para acceso a datos de Preguntas
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface PreguntaRepository extends JpaRepository<Pregunta, Long>  {

}
