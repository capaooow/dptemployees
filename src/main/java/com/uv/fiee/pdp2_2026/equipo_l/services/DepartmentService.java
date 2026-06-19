/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uv.fiee.pdp2_2026.equipo_l.services;

import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import com.uv.fiee.pdp2_2026.equipo_l.repository.EmployeeDepartmentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author iflme
 */
@Service
public class DepartmentService implements IDepartmentService{
    @Autowired
    EmployeeDepartmentRepository edrepo;

    @Override
    public List<EmployeeDepartment> getAllEmployees(Page page) {
        var pageable=PageRequest.of(
                page.number(),
                page.size(),
                Sort.by(Sort.Order.asc(page.sort()))
        );
        return edrepo.findAllEmployee(pageable);
    }

    @Override
    public List<EmployeeDepartment> getAllEmployeesInDpt(String department,Page page) {
        var pageable=PageRequest.of(
                page.number(),
                page.size(),
                Sort.by(Sort.Order.asc(page.sort()))
        );
        return edrepo.findByDepartment(department,pageable);
    }

    @Override
    public EmployeeDepartment findByBusID(Long id) {
        return edrepo.findByBusID(id);
    }
    @Override
    public EmployeeDepartment findOldest(){
        return edrepo.findOldest();
    }
    @Override
    public EmployeeDepartment findOldest(String department){
        return edrepo.findOldest();
    }
    @Override
    public List<String> findDistinctDepartments(){
        return edrepo.findDistinctDepartments();
    }
}
