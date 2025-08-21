package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Admin;
import com.caredesk.caredesk.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/login")
    public String showAdminLogin() {
        return "admin_login";
    }

    @PostMapping("/admin/login")
    public ModelAndView processLogin(@RequestParam String username, @RequestParam String password) {
        ModelAndView mv = new ModelAndView();

        try {
            Admin admin = adminService.findByUsername(username);
            if (admin.getPassword().equals(password)) {
                mv.setViewName("redirect:/admin/articles");  // Redirect to articles list dashboard
            } else {
                mv.setViewName("admin_login");
                mv.addObject("error", "Invalid password");
            }
        } catch (RuntimeException e) {
            mv.setViewName("admin_login");
            mv.addObject("error", "User not found");
        }
        return mv;
    }

    @GetMapping("/admin/dashboard")
    public String showDashboard() {
        return "redirect:/admin/articles";  // Redirect dashboard to articles list
    }
}
