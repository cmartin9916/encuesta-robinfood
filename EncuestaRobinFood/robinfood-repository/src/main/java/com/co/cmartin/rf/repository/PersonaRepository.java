/**
 * @autor Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
package com.co.cmartin.rf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.co.cmartin.rf.entity.Persona;

/**
 * Interfaz que extiende de JpaRepository para acceso a datos de Personas
 * @author Carlos Martin
 * @version 0.0.1 15/11/2021
 *
 */
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	@Query("SELECT p FROM Persona p WHERE p.identificacion = :identificacion")
    public Persona buscarPersonaPorIdentificacion(@Param("identificacion") String identificacion);

}
