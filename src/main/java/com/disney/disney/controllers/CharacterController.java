package com.disney.disney.controllers;

import com.disney.disney.entities.MoviesOrSeries;
import com.disney.disney.entities.Personaje;
import com.disney.disney.services.CharacterService;
import com.disney.disney.services.MoviesService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/character")
@Controller
public class CharacterController {

    private CharacterService characterService;
    private MoviesService moviesService;

    @Autowired
    public CharacterController(CharacterService characterService, MoviesService moviesService) {
        this.characterService = characterService;
        this.moviesService = moviesService;
    }
    
    @GetMapping
    public String listCharacters(ModelMap model){
        model.addAttribute("characters", characterService.listAll());
        return "character/listCharacters";
    }
    
    @GetMapping("/save")
    public String save(ModelMap model){
        model.addAttribute("character", new Personaje());
        return "character/save";
    }
    
    @PostMapping("/save")
    public String savePost(@ModelAttribute Personaje character, ModelMap model){
        try {
            characterService.save(character);
        } catch (Exception e) {
            model.put("error ", e.getMessage());
            model.put("character", character);
            return "character/save";
        }
        return "redirect:/character";
    }
    
    @GetMapping("/remove/{id}")
    public String removeCharacter(ModelMap model,@PathVariable String id){
        try {
            characterService.remove(id);
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "character/listCharacter";
        }
        return "redirect:/character";
    }
    
    @GetMapping("/edit/{id}")
    public String editCharacter(ModelMap model, @PathVariable String id){
        try {
            model.addAttribute("character", characterService.findById(id));
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "character/edit";
    }
    
    @PostMapping("/edit")
    public String edit(ModelMap model, @ModelAttribute Personaje character){
        try {
            characterService.edit(character);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("character", character);
            return "character/edit";
        }
        return "redirect:/character";
    }
    
    @GetMapping("/details/{id}")
    public String characterDetails(@PathVariable String id, ModelMap model){
        try {
            Personaje character = characterService.findById(id);
            model.addAttribute("character", character);
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "character/details";
    }
    
     @GetMapping("/addMovies/{id}")
    public String addMovies(@PathVariable String id, ModelMap model){
         try {
             Personaje character = characterService.findById(id);
             model.addAttribute("character", character);
             List<MoviesOrSeries> movies = moviesService.listAll();
             model.addAttribute("movies", movies);
         } catch (Exception e) {
             model.put("error", e.getMessage());
         }
        return "character/addMovies";
    }
    
    @PostMapping("/addMovies")
    public String addMoviesPost(ModelMap model, @RequestParam String characterId, @RequestParam List<String> moviesId){
        try {
            Personaje character = characterService.findById(characterId);
            List<MoviesOrSeries> movies = new ArrayList();
            for (String id : moviesId) {
                movies.add(moviesService.findById(id));
            }
            character.setMoviesOrSeries(movies);
            characterService.edit(character);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            List<MoviesOrSeries> movies = moviesService.listAll();
            model.put("movies", movies);
            return "character/addMovies";
        }
        return "redirect:/character";
    }
    
//    @GetMapping("/query")
//    public String findByName(@RequestParam("name") String name, ModelMap model){
//       model.addAttribute("character", characterService.findByName(name));
//       return "character/character";
//    }
    
}
