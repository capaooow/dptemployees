package com.uv.fiee.pdp2_2026.equipo_l.service;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import java.util.List;
import java.util.Map;

public interface IDepartmentService {
  List<EmployeeDepartment> getAllEmployees(Page page);

  List<EmployeeDepartment> getAllEmployeesInDpt(String department, Page page);

  List<String> findDistinctDepartments();

  Map<String, Object> processDepartmentWithThreads(String department, Page page);
}
