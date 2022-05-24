package com.disney.disney.controllers;

import com.disney.disney.entities.Genre;
import com.disney.disney.entities.MoviesOrSeries;
import com.disney.disney.services.GenreService;
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

@RequestMapping("/genre")
@Controller
public class GenreController {
    
    private GenreService genreService;
    private MoviesService moviesService;

    @Autowired
    public GenreController(GenreService genreService, MoviesService moviesService) {
        this.genreService = genreService;
        this.moviesService = moviesService;
    }
    
    @GetMapping()
    public String ListGenders(ModelMap model){
        model.addAttribute("genders", genreService.listAll());
        return "genre/listGenders";
    }
    
    @GetMapping("/save")
    public String save(ModelMap model){
        model.addAttribute("genre", new Genre());
        model.addAttribute("genders", genreService.listEnum());
        return "genre/save";
    }
    
    @PostMapping("/save")
    public String savePost(@ModelAttribute Genre genre, ModelMap model){
        try {
            genreService.save(genre);
        } catch (Exception e) {
            model.put("error ", e.getMessage());
            model.put("genre", genre);
            model.put("genders", genreService.listEnum());
            return "genre/save";
        }
        return "redirect:/genre";
    }
    
    @GetMapping("/addMovies/{id}")
    public String addMovies(@PathVariable String id, ModelMap model){
         try {
             System.out.println(id);
             Genre genre = genreService.findById(id);
             model.addAttribute("genre", genre);
             List<MoviesOrSeries> movies = moviesService.listAll();
             model.addAttribute("movies", movies);
         } catch (Exception e) {
             model.put("error", e.getMessage());
         }
        return "genre/addMovies";
    }
    
    @PostMapping("/addMovies")
    public String addMoviesPost(ModelMap model, @RequestParam String genreId, @RequestParam List<String> moviesId){
        try {
            Genre genre = genreService.findById(genreId);
            List<MoviesOrSeries> movies = new ArrayList();
            for (String id : moviesId) {
                movies.add(moviesService.findById(id));
            }
            genre.setMoviesOrSeries(movies);
            genreService.edit(genre);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            List<MoviesOrSeries> movies = moviesService.listAll();
            model.put("movies", movies);
            return "genre/addMovies";
        }
        return "redirect:/genre";
    }
}
