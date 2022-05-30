package com.disney.disney.controllers;

import com.disney.disney.entities.MoviesOrSeries;
import com.disney.disney.entities.Personaje;
import com.disney.disney.services.CharacterService;
import com.disney.disney.services.MoviesService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PreAuthorize("hasAnyRole('ROLE_USER')")
@RequestMapping("/movies")
@Controller
public class MoviesController {

    private CharacterService characterService;
    private MoviesService moviesService;

    @Autowired
    public MoviesController(MoviesService moviesService, CharacterService characterService) {
        this.moviesService = moviesService;
        this.characterService = characterService;
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
    public String removeMovie(ModelMap model,@PathVariable String id){
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
    
   @GetMapping("/addCharacter/{id}")
    public String addCharacter(@PathVariable String id, ModelMap model){
         try {
             MoviesOrSeries movie = moviesService.findById(id);
             model.addAttribute("movie", movie);
             List<Personaje> characters = characterService.listAll();
             model.addAttribute("characters", characters);
         } catch (Exception e) {
             model.put("error", e.getMessage());
         }
        return "movies/addCharacter";
    }
    
    @PostMapping("/addCharacter")
    public String addMoviesPost(ModelMap model, @RequestParam String movieId, @RequestParam List<String> characterId){
        try {
            MoviesOrSeries movie = moviesService.findById(movieId);
            List<Personaje> characters = new ArrayList();
            for (String id : characterId) {
                characters.add(characterService.findById(id));
            }
            movie.setCharacters(characters);
            moviesService.edit(movie);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            List<Personaje> characters = characterService.listAll();
            model.put("characters", characters);
            return "movie/addCharacter";
        }
        return "redirect:/movies";
    }
    
    @GetMapping("/details/{id}")
    public String details(ModelMap model, @PathVariable String id){
        try {
            model.addAttribute("movie",moviesService.findById(id));
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "redirect:/movies";
        }
        return "movies/details";
    }
    
    @GetMapping("/title")
    public String findByTitle(@RequestParam("title") String title, ModelMap model){
       model.addAttribute("movies", moviesService.findByTitle(title));
       return "movies/listMovies";
    }
}
