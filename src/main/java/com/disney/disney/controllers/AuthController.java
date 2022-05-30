package com.disney.disney.controllers;

import com.disney.disney.entities.Customer;
import com.disney.disney.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("auth")
@Controller
public class AuthController {

    private CustomerService customerService;

    @Autowired
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @GetMapping("/register")
    public String showForm(ModelMap model) {
        model.addAttribute("customer", new Customer());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processForm(@ModelAttribute Customer customer, ModelMap model) {

        try {
            customerService.register(customer);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error ", e.getMessage());
            model.addAttribute("customer", customer);
            return "auth/register";
        }
    }
    
    @GetMapping("/login")
    public String login( @RequestParam(required = false) String error, ModelMap model){
        if (error != null) {
            model.put("error", "Email o Contraseña incorrectos");
        }
        return "auth/login";
    }
}
