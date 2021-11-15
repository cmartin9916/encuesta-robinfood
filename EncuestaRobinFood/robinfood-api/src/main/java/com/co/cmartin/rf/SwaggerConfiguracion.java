/**
 * @autor Carlos Martin
 * @version 0.0.1 14/11/2021
 *
 */
package com.co.cmartin.rf;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Clase con configuracion Swagger
 * 
 * @author Carlos Martin
 * @version 0.0.1 14/11/2021
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguracion {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.tags(
					new Tag("Persona", "Servicios para administrar información de Personas en la aplicación"), 
					new Tag("Formulario", "Servicios para administrar información de Formularios en la aplicación"),
					new Tag("Pregunta", "Servicios para administrar información de Preguntas en la aplicación"),
					new Tag("Opción Pregunta", "Servicios para administrar información de Opciones de Preguntas en la aplicación"))
				.groupName("Administración")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.co.cmartin.rf.rest.administracion"))
				.build()
				.apiInfo(getApiInfoAdministracion());
	}
	
	@Bean
	public Docket apiDocket2() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Encuesta")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.co.cmartin.rf.rest.encuesta"))
				.build()
				.apiInfo(getApiInfoEncuesta());
	}

	private ApiInfo getApiInfoAdministracion() {
		return new ApiInfo("Prueba Técnica RobinFoof - Encuesta API",
				"API con servicios para administración de información necesaria para realizar encuestas - Desarrollado por Carlos Martín", 
				"1.0", 
				"", 
				null, 
				"", 
				"",
				Collections.emptyList());
	}
	
	private ApiInfo getApiInfoEncuesta() {
		return new ApiInfo("Prueba Técnica RobinFoof - Encuesta API",
				"API con servicios para almacenar encuestas realizadas por los usuarios - Desarrollado por Carlos Martín", 
				"1.0", 
				"", 
				null, 
				"", 
				"",
				Collections.emptyList());
	}

}
