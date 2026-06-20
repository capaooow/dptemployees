package com.uv.fiee.pdp2_2026.equipo_l.service;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import java.util.List;
import java.util.Map;

public interface IDepartmentService {
  List<EmployeeDepartment> getAllEmployees(String department, Page page);

  Long getEmployeeCount(String department);
  
  EmployeeDepartment getSeniorEmployee(String department);
  
  List<String> findDistinctDepartments();

  Map<String, Object> processDepartmentWithThreads(String department, Page page);
}
