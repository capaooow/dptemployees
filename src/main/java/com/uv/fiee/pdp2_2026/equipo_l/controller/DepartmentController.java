package com.uv.fiee.pdp2_2026.equipo_l.controller;

import com.uv.fiee.pdp2_2026.equipo_l.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class DepartmentController {
  @Autowired
  private DepartmentService dptService;

  @GetMapping
  public String getDepartments(Model model) {
    model.addAttribute("allDepartments", dptService.findDistinctDepartments());
    return "index";
  }
}
