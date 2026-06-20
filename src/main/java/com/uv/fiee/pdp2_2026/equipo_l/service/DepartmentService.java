package com.uv.fiee.pdp2_2026.equipo_l.service;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
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
  @Override
  public List<EmployeeDepartment> getAllEmployees(String department,Page page) {
    if(department==null|| department.trim().isEmpty())
        return edrepo
            .findAllEmployees(PageRequest.of(page.number() - 1, page.size(), Sort.by(Sort.Order.asc(page.sort()))));
    return edrepo.findByDepartment(department,
        PageRequest.of(page.number() - 1, page.size(), Sort.by(Sort.Order.asc(page.sort()))));
  }
  @Override
  public EmployeeDepartment getSeniorEmployee(String department) {
    if(department==null)
        return edrepo.getSeniorEmployee();
    return edrepo.getSeniorEmployeeInDpt(department);
    }
  
  @Override
  public Long getEmployeeCount(String department) {
    if(department==null)
        return edrepo.getEmployeeCount();
    return edrepo.getEmployeeCountInDpt(department);
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
    employees = getAllEmployees(department,page);
    // Guardar la lista para la tabla de la página.
    result.put("employeeList", employees);
    StringBuilder csvBuilder = new StringBuilder();
    csvBuilder.append("ID,First Name,Last Name,Department,Start Date,Job Title\n");
    for (EmployeeDepartment emp : employees) {
      csvBuilder.append(emp.getId()).append(",")
          .append(emp.getFirstName()).append(",")
          .append(emp.getLastName()).append(",")
          .append(emp.getDepartment()).append(",")
          .append(emp.getStartDate()).append(",")
          .append(emp.getJobTitle()).append("\n");
    }
    result.put("csvData", csvBuilder.toString());
    // Primer hilo: Hilo que manda la cantidad de empleados de cierto departamento.
    Thread t1 = new Thread(() -> {
        result.put("totalEmployeesInPage", employees.size());
        long employeeCount=getEmployeeCount(department);
        result.put("totalEmployeesCalculated", employeeCount);
        result.put("pageCount", employeeCount/employees.size());
    });
    // Segundo hilo: Hilo que calcula el empleado más antiguo en cierto
    // departamento.
    Thread t2 = new Thread(() -> {
      if (employees.isEmpty()) {
        result.put("oldestEmployeeCalculated", null);
        return;
      }
      EmployeeDepartment theOldest = employees.get(0);
      for (EmployeeDepartment emp : employees) {
        if (emp.getStartDate().isBefore(theOldest.getStartDate())) {
          theOldest = emp;
        }
      }
      result.put("oldestEmployeeCalculated", getSeniorEmployee(department));
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
    return result;
  }

    
}