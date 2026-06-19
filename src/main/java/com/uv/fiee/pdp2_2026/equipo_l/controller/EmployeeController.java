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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    DepartmentService dptService;
    private String getDepartments(Model model){
        model.addAttribute("allDepartments", dptService.findDistinctDepartments());
        return "allDepartments";
    }
    private void getEmployee_ID(Model model){
        model.addAttribute("oldest", dptService.findOldest());
    }
    /*
    @GetMapping("/employee")
    public String getEmployee_ID(@RequestParam Long employee,Model model){
        model.addAttribute("employeeByID", dptService.findByBusID(employee));
        return "employeeByID";
    }
    */
    @GetMapping("")
    public String getEmployees(@RequestParam(required=false) String department,@RequestParam int pageSize,@RequestParam int pageNum,Model model){
        List <EmployeeDepartment> employeeList;
        if(department==null){
            employeeList=dptService.getAllEmployees(new Page(pageSize,pageNum,"BusinessEntityID"));
            model.addAttribute("oldest", dptService.findOldest());
        }else {
            employeeList=dptService.getAllEmployeesInDpt(department,new Page(pageSize,pageNum,"BusinessEntityID"));
            model.addAttribute("oldest", dptService.findOldest(department));
        }
        model.addAttribute("employeeList",employeeList);
        return "index";
    }
}
