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

  
  private byte[] csvBytes(String department,Page page){
     Map<String, Object> results = dptService.processDepartmentWithThreads(department, page);

    String csvText = (String) results.get("csvData");
    if (csvText == null) {
      csvText = "ID,First Name,Last Name,Department,Start Date,Job Title\n";
    }
    byte[] csvBytes = csvText.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    return csvBytes;
  }
    //Metodo que junta todos los csvbytes de una pagina.
    
  @GetMapping("/download")
  public org.springframework.http.ResponseEntity<byte[]> downloadCsv(
      @RequestParam(required = false) String department,
      @RequestParam(defaultValue = "500") int pageSize,
      @RequestParam(defaultValue = "1") int pageNum) {
    if (department.trim().isEmpty()) {
      department = null;
    }
    Page page = new Page(pageSize, pageNum, "id");
    String filename = (department == null)
        ? "All_Employees_Page"+pageNum+".csv"
        : department.trim().replace(" ", "_") + "_Employees_Page"+pageNum+".csv";

    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
    headers.setContentType(org.springframework.http.MediaType.parseMediaType("text/csv"));
    headers.setContentDispositionFormData("attachment", filename);

    return org.springframework.http.ResponseEntity.ok()
        .headers(headers)
        .body(csvBytes(department,page));
  }
}
