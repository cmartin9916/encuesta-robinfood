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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

/**
 * Entidad de persistencia para Opcion
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@Data
@Entity
@Table(name = "opcion", uniqueConstraints = { @UniqueConstraint(columnNames = { "descripcion" }) })
public class Opcion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "descripcion", nullable=false)
	private String descripcion;
	@Column(name="fecha_creacion", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date fechaCreacion;
	@Column(name = "fecha_ultima_modificacion", columnDefinition="TIMESTAMP", nullable=false)
	private Date fechaUltimaModificacion;
	@ManyToMany(mappedBy = "opcionesPregunta")
	List<Pregunta> preguntas;
	@OneToMany(mappedBy="respuestaCerrada")
    private List<Respuesta> respuestas;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opcion other = (Opcion) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(id, other.id) && Objects.equals(preguntas, other.preguntas)
				&& Objects.equals(respuestas, other.respuestas);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * @autor Carlos Martin
	 * @version 0.0.1 15/11/2021
	 */
	@Override
	public int hashCode() {
		return Objects.hash(descripcion, id, preguntas, respuestas);
	}
}
