package com.disney.disney.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

 @PreAuthorize("hasAnyRole('ROLE_USER')")
@RequestMapping("/")
@Controller
public class IndexController {

    @GetMapping
    public String index(){
        return "index";
    }
}
