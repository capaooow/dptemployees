package com.uv.fiee.pdp2_2026.equipo_l.controller;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import com.uv.fiee.pdp2_2026.equipo_l.service.IDepartmentService;
import java.util.List;

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
      @RequestParam(defaultValue = "150") int pageSize,
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
    model.addAttribute("oldest", results.get("oldestEmployee"));

    // Para la búsqueda de los departamentos únicos.
    model.addAttribute("allDepartments", dptService.findDistinctDepartments());

    // Departamento seleccionado por el usuario.
    model.addAttribute("currentDepartment", department);
    
    //Info de paginacion
    model.addAttribute("pageNum",results.get("pageNum"));
    model.addAttribute("totalEmployeesInPage", results.get("totalEmployeesInPage"));
    model.addAttribute("pageCount",results.get("pageCount"));
    // "index" es el nombre del archivo html.
    return "index";
  }

  
  private byte[] csvBytes(String department,int pageSize){
    Map<String, Object> results = dptService.processDepartmentWithThreads(department, new Page(pageSize,1,"id"));
    StringBuilder fullCSVText = new StringBuilder("ID,First Name,Last Name,Department,Start Date,Job Title\n");
    int pageCount=(int) results.get("pageCount");
    List<EmployeeDepartment> list;
    for(int i=2;i<=pageCount;i++){
        list=dptService.getAllEmployees(department, new Page(pageSize,i,"id"));
        fullCSVText.append(dptService.buildCSVString(list));
    }
    byte[] csvBytes = fullCSVText.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
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
        ? "All_Employees.csv"
        : department.trim().replace(" ", "_") + "_Employees.csv";

    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
    headers.setContentType(org.springframework.http.MediaType.parseMediaType("text/csv"));
    headers.setContentDispositionFormData("attachment", filename);

    return org.springframework.http.ResponseEntity.ok()
        .headers(headers)
        .body(csvBytes(department,page.size()));
  }
}
