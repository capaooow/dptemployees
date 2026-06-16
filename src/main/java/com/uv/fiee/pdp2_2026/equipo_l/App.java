package com.uv.fiee.pdp2_2026.equipo_l;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Clase principal de inicialización de Spring Boot.
 * Desde aquí se levanta el servidor web embebido (Tomcat) en el puerto 8080.
 */

@SpringBootApplication
public class App {
  public static void main(String[] args) {
    // Le dice a Spring que arranque la aplicación usando esta misma clase como
    // base.
    SpringApplication.run(App.class, args);
  }
}
