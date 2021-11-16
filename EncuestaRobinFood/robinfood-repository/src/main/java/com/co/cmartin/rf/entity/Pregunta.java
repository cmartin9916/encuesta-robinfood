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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.co.cmartin.rf.enums.TipoPregunta;

import lombok.Data;

/**
 * Entidad de persistencia para Pregunta
 * 
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@Data
@Entity
@Table(name = "pregunta", uniqueConstraints = { @UniqueConstraint(columnNames = { "descripcion", "tipo_pregunta" }) })
public class Pregunta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "descripcion", nullable=false)
	private String descripcion;
	@Column(name = "tipo_pregunta", nullable = false, length = 8)
	@Enumerated(value = EnumType.STRING)
	private TipoPregunta tipoPregunta;
	@Column(name="fecha_creacion", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date fechaCreacion;
	@Column(name = "fecha_ultima_modificacion", columnDefinition="TIMESTAMP", nullable=false)
	private Date fechaUltimaModificacion;
	@OneToMany(mappedBy="pregunta")
    private List<Respuesta> respuestas;
	@ManyToMany(mappedBy = "preguntasFormulario")
	List<Formulario> formularios;
	@ManyToMany
	@JoinTable(name = "detalle_pregunta_opcion", joinColumns = @JoinColumn(name = "pregunta_id"), inverseJoinColumns = @JoinColumn(name = "opcion_id"))
	List<Opcion> opcionesPregunta;
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
		Pregunta other = (Pregunta) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(formularios, other.formularios) && Objects.equals(id, other.id)
				&& Objects.equals(opcionesPregunta, other.opcionesPregunta) && Objects.equals(respuestas, other.respuestas)
				&& tipoPregunta == other.tipoPregunta;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * @autor Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Override
	public int hashCode() {
		return Objects.hash(descripcion, formularios, id, opcionesPregunta, respuestas, tipoPregunta);
	}
}
