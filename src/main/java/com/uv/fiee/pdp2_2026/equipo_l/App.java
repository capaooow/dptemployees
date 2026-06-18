package com.uv.fiee.pdp2_2026.equipo_l;
import com.uv.fiee.pdp2_2026.equipo_l.io.EmployeeDpt_IO;
import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
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
    //SpringApplication.run(App.class, args);
    List<EmployeeDepartment> li;
    li=EmployeeDpt_IO.read("datos.txt");
      for (int i = 0; i < li.size(); i++) {
          System.out.println(li.get(i));
      }
    li.clear();
    li.add(new EmployeeDepartment(1,"Tony","Snoop","Tour Guide","Tourism",LocalDate.now()));
    li.add(new EmployeeDepartment(2,"Jacob","Wright","Vendor","Sales",LocalDate.of(2024, Month.MARCH, 11)));
    li.add(new EmployeeDepartment(3,"Nancy","Snow","Lawyer","Legal",LocalDate.of(2022, Month.APRIL, 18)));
    EmployeeDpt_IO.write(li, "datos.txt");
  }
}
