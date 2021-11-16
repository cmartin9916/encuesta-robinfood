/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.Data;

/**
 * Entidad de persistencia para Encuesta
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@Data
@Entity
@Table(name = "encuesta")
public class Encuesta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="fecha_creacion", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date fechaCreacion;
	@ManyToOne
	@JoinColumn(name="formulario_id", nullable=false)
	private Formulario formulario;
	@Nullable
	@ManyToOne
    @JoinColumn(name="persona_id", nullable=true)
    private Persona persona;
	@OneToMany(mappedBy="encuesta")
    private List<Respuesta> respuestas;
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @autor Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Encuesta other = (Encuesta) obj;
		return Objects.equals(formulario, other.formulario) && Objects.equals(id, other.id) && Objects.equals(persona, other.persona)
				&& Objects.equals(respuestas, other.respuestas);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * @autor Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Override
	public int hashCode() {
		return Objects.hash(formulario, id, persona, respuestas);
	}
}
