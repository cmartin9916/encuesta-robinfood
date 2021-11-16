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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

/**
 * Entidad de persistencia para Formulario
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
@Data
@Entity
@Table(name = "formulario", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo", "descripcion" }) })
public class Formulario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "titulo", nullable=false)
	private String titulo;
	@Column(name = "descripcion", nullable=false)
	private String descripcion;
	@Column(name = "activo", nullable=false)
	private boolean activo;
	@Column(name="fecha_creacion", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date fechaCreacion;
	@Column(name = "fecha_ultima_modificacion", columnDefinition="TIMESTAMP", nullable=false)
	private Date fechaUltimaModificacion;
	@OneToMany(mappedBy="formulario")
    private List<Encuesta> encuestas;
	@ManyToMany
	@JoinTable(name = "detalle_formulario_pregunta", joinColumns = @JoinColumn(name = "formulario_id"), inverseJoinColumns = @JoinColumn(name = "pregunta_id"))
    private List<Pregunta> preguntasFormulario;
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
		Formulario other = (Formulario) obj;
		return activo == other.activo && Objects.equals(descripcion, other.descripcion) && Objects.equals(encuestas, other.encuestas)
				&& Objects.equals(id, other.id) && Objects.equals(preguntasFormulario, other.preguntasFormulario)
				&& Objects.equals(titulo, other.titulo);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * @autor Carlos Martin
	 * @version 0.0.1 16/11/2021
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activo, descripcion, encuestas, id, preguntasFormulario, titulo);
	}	
}
