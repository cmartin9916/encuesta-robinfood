/**
 * @autor Carlos Martin
 * @version 0.0.1 10/11/2021
 *
 */
package com.co.cmartin.rf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Carlos Martin
 * @version 0.0.1 10/11/2021
 *
 */
@SpringBootApplication
public class EncuestaRobinFoodApplication {

	/**
	 * @author Carlos Martin
	 * @version 0.0.1 10/11/2021
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/robinfood-encuesta-api");
		
		SpringApplication.run(EncuestaRobinFoodApplication.class, args);
	}

}
