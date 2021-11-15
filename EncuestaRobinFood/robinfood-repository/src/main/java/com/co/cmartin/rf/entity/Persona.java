/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

/**
 * Entidad de persistencia para Persona
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 */
@Data
@Entity
@Table(name = "persona", uniqueConstraints = { @UniqueConstraint(columnNames = { "identificacion" }) })
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "identificacion", nullable=false)
	private String identificacion;
	@Column(name = "nombres", nullable=false)
	private String nombres;
	@Column(name = "apellidos", nullable=false)
	private String apellidos;
	@Column(name = "correo_electronico", nullable=false)
	private String correoElectronico;
	@Column(name="fecha_creacion", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date fechaCreacion;
	@Column(name = "fecha_ultima_modificacion", columnDefinition="TIMESTAMP", nullable=false)
	private Date fechaUltimaModificacion;
	@OneToMany(mappedBy="persona")
    private List<Encuesta> encuestas;
}
