package com.disney.disney.controllers;

import com.disney.disney.entities.Personaje;
import com.disney.disney.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/character")
@Controller
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }
    
    @GetMapping("/save")
    public String save(ModelMap model, @RequestParam(required = false) String id){
        try {
            if (id == null || id.trim().isEmpty()) {
                model.addAttribute("character", new Personaje());
            } else {
                model.addAttribute("character", characterService.findById(id));
            }
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "save.html";
        }
        return "save.html";
    }
    
    @PostMapping("/save")
    public String savePost(@RequestParam Personaje character, ModelMap model){
        try {
            characterService.save(character);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "save.html";
        }
        return "redirect:/character";
    }
}
