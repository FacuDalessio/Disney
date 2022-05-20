package com.disney.disney.controllers;

import com.disney.disney.entities.MoviesOrSeries;
import com.disney.disney.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/movies")
@Controller
public class MoviesController {

    private MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }
    
    @GetMapping
    public String listCharacters(ModelMap model){
        model.addAttribute("movies", moviesService.listAll());
        return "movies/listMovies";
    }
    
    @GetMapping("/save")
    public String save(ModelMap model){
        model.addAttribute("movie", new MoviesOrSeries());
        return "movies/save";
    }
    
    @PostMapping("/save")
    public String savePost(@ModelAttribute MoviesOrSeries movie, ModelMap model){
        try {
            moviesService.save(movie);
        } catch (Exception e) {
            model.put("error ", e.getMessage());
            model.put("movie", movie);
            return "movies/save";
        }
        return "redirect:/movies";
    }
    
    @GetMapping("/remove/{id}")
    public String removeCharacter(ModelMap model,@PathVariable String id){
        try {
            moviesService.remove(id);
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "movies/listMovies";
        }
        return "redirect:/movies";
    }
    
    @GetMapping("/edit/{id}")
    public String editCharacter(ModelMap model, @PathVariable String id){
        try {
            model.addAttribute("movie", moviesService.findById(id));
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "movies/edit";
    }
    
    @PostMapping("/edit")
    public String edit(ModelMap model, @ModelAttribute MoviesOrSeries movie){
        try {
            moviesService.edit(movie);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("movie", movie);
            return "movies/edit";
        }
        return "redirect:/movies";
    }
}
