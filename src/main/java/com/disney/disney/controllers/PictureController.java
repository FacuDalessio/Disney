package com.disney.disney.controllers;

import com.disney.disney.entities.MoviesOrSeries;
import com.disney.disney.entities.Personaje;
import com.disney.disney.services.CharacterService;
import com.disney.disney.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/picture")
@Controller
public class PictureController {

    private CharacterService characterService;
    private MoviesService moviesService;
    
    @Autowired
    public PictureController(CharacterService characterService, MoviesService moviesService) {
        this.characterService = characterService;
        this.moviesService = moviesService;
    }
    
    @GetMapping("/character")
    public ResponseEntity<byte[]> pictureCharacter(@RequestParam String id, ModelMap model){
        try {
            Personaje character = characterService.findById(id);
            if (character.getPicture() == null) {
                throw new Exception("The character does not have a photo uploaded");
            }
            
            byte[] picture = character.getPicture().getContents();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            
            return new ResponseEntity<>(picture, headers, HttpStatus.OK);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/movie")
    public ResponseEntity<byte[]> pictureMovie(@RequestParam String id, ModelMap model){
        try {
            MoviesOrSeries movie = moviesService.findById(id);
            if (movie.getPicture() == null) {
                throw new Exception("The character does not have a photo uploaded");
            }
            
            byte[] picture = movie.getPicture().getContents();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            
            return new ResponseEntity<>(picture, headers, HttpStatus.OK);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
