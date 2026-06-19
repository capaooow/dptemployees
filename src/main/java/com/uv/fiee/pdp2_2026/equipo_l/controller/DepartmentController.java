/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uv.fiee.pdp2_2026.equipo_l.controller;
import com.uv.fiee.pdp2_2026.equipo_l.model.EmployeeDepartment;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import com.uv.fiee.pdp2_2026.equipo_l.services.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author iflme
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService dptService;
    @GetMapping
    public String getDepartments(Model model){
        model.addAttribute("allDepartments", dptService.findDistinctDepartments());
        return "departments";
    }
}
