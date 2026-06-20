package com.uv.fiee.pdp2_2026.equipo_l.service;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import com.uv.fiee.pdp2_2026.equipo_l.io.EmployeeDptIO;
import com.uv.fiee.pdp2_2026.equipo_l.repository.EmployeeDepartmentRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements IDepartmentService {
  @Autowired
  private EmployeeDepartmentRepository edrepo;

  @Autowired
  private EmployeeDptIO fileIO;

  @Override
  public List<EmployeeDepartment> getAllEmployees(Page page) {
    return edrepo
        .findAllEmployees(PageRequest.of(page.number() - 1, page.size(), Sort.by(Sort.Order.asc(page.sort()))));
  }

  @Override
  public List<EmployeeDepartment> getAllEmployeesInDpt(String department, Page page) {
    return edrepo.findByDepartment(department,
        PageRequest.of(page.number() - 1, page.size(), Sort.by(Sort.Order.asc(page.sort()))));
  }

  @Override
  public List<String> findDistinctDepartments() {
    return edrepo.findDistinctDepartments();
  }

  // Threads
  @Override
  public Map<String, Object> processDepartmentWithThreads(String department, Page page) {
    Map<String, Object> result = new HashMap<>();

    // Conexión a Azure.
    List<EmployeeDepartment> employees;
    if (department == null || department.trim().isEmpty()) {
      employees = getAllEmployees(page);
    } else {
      employees = getAllEmployeesInDpt(department, page);
    }

    // Guardar la lista para la tabla de la página.
    result.put("employeeList", employees);

    if (!employees.isEmpty()) {
      String fileName = (department == null || department.trim().isEmpty())
          ? "All"
          : department.trim().replace(" ", "_");

      String dir = "src/main/resources/data/result_" + fileName + ".csv";

      fileIO.write(employees, dir);
    }

    final int[] totalCounter = new int[1];
    final EmployeeDepartment[] oldestEmployee = new EmployeeDepartment[1];

    // Primer hilo: Hilo que manda la cantidad de empleados de cierto departamento.
    Thread t1 = new Thread(() -> {
      totalCounter[0] = employees.size();
    });

    // Segundo hilo: Hilo que calcula el empleado más antiguo en cierto
    // departamento.
    Thread t2 = new Thread(() -> {
      if (employees.isEmpty()) {
        oldestEmployee[0] = null;
        return;
      }

      EmployeeDepartment theOldest = employees.get(0);
      for (EmployeeDepartment emp : employees) {
        if (emp.getStartDate().isBefore(theOldest.getStartDate())) {
          theOldest = emp;
        }
      }

      oldestEmployee[0] = theOldest;
    });

    try {
      t1.start();
      t2.start();
      t1.join();
      t2.join();
    } catch (InterruptedException ex) {
      Logger.getLogger(DepartmentService.class.getName()).log(Level.SEVERE, "Fallo en la concurrencia de los hilos",
          ex);
      Thread.currentThread().interrupt();
    }

    result.put("totalEmployeesCalculated", totalCounter[0]);
    result.put("oldestEmployeeCalculated", oldestEmployee[0]);

    return result;
  }
}
