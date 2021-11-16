/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.Data;

/**
 * Entidad de persistencia para Respuesta
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@Data
@Entity
@Table(name = "respuesta")
public class Respuesta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
    @JoinColumn(name="pregunta_id", nullable=false)
    private Pregunta pregunta;
	@ManyToOne
    @JoinColumn(name="encuesta_id", nullable=false)
    private Encuesta encuesta;
	@Nullable
	@Column(name = "respuesta_abierta", nullable=true)
	private String respuestaAbierta;
	@Nullable
	@ManyToOne
    @JoinColumn(name="opcion_id", nullable=true)
    private Opcion respuestaCerrada;
}
