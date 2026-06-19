/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uv.fiee.pdp2_2026.equipo_l.services;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import java.util.List;

public interface IDepartmentService {
    List<EmployeeDepartment> getAllEmployees(Page page);
    List<EmployeeDepartment> getAllEmployeesInDpt(String deparment,Page page);
    EmployeeDepartment findOldest();
    EmployeeDepartment findOldest(String department);
    List<String> findDistinctDepartments();
}
