/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uv.fiee.pdp2_2026.equipo_l.controller;
import com.uv.fiee.pdp2_2026.equipo_l.model.Page;
import com.uv.fiee.pdp2_2026.equipo_l.services.DepartmentService;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @GetMapping
    public String getEmployees(@RequestParam(required=false) String department,@RequestParam int pageSize,@RequestParam int pageNum,Model model){
            Thread t1,t2;
            if(department==null){
                t1= new Thread(()->{
                    model.addAttribute("employeeList",dptService.getAllEmployees(new Page(pageSize,pageNum,"BusinessEntityID")));
                });
                t2= new Thread(()->{
                    model.addAttribute("oldest",dptService.getAllEmployees(new Page(pageSize,pageNum,"BusinessEntityID")));
                });
            }else {
                t1= new Thread(()->{
                    model.addAttribute("employeeList",dptService.getAllEmployeesInDpt(department,new Page(pageSize,pageNum,"BusinessEntityID")));
                });
                t2= new Thread(()->{
                    model.addAttribute("oldest", dptService.findOldest(department));  
                });
            }   
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }
}
