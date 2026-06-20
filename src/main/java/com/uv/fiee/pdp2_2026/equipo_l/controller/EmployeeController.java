package com.uv.fiee.pdp2_2026.equipo_l.controller;

import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import com.uv.fiee.pdp2_2026.equipo_l.service.IDepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  private IDepartmentService dptService;

  @GetMapping
  public String getEmployees(
      @RequestParam(required = false) String department,
      @RequestParam(defaultValue = "100") int pageSize,
      @RequestParam(defaultValue = "1") int pageNum,
      Model model) {

    if (department != null && department.trim().isEmpty()) {
      department = null;
    }

    Page page = new Page(pageSize, pageNum, "id");

    Map<String, Object> results = dptService.processDepartmentWithThreads(department, page);

    // Lista de empleados.
    model.addAttribute("employeeList", results.get("employeeList"));

    // Resultados de los threads.
    model.addAttribute("totalEmployees", results.get("totalEmployeesCalculated"));
    model.addAttribute("oldest", results.get("oldestEmployeeCalculated"));

    // Para la búsqueda de los departamentos únicos.
    model.addAttribute("allDepartments", dptService.findDistinctDepartments());

    // Departamento seleccionado por el usuario.
    model.addAttribute("currentDepartment", department);

    // "index" es el nombre del archivo html.
    return "index";
  }

}
