package com.disney.disney.services;

import com.disney.disney.entities.MoviesOrSeries;
import com.disney.disney.entities.Picture;
import com.disney.disney.repositories.MoviesRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MoviesService {

    private MoviesRepository moviesRepository;
    private PictureService pictureService;
    
    @Autowired
    public MoviesService(MoviesRepository moviesRepository, PictureService pictureService) {
        this.moviesRepository = moviesRepository;
        this.pictureService = pictureService;
    }
    
    public void save(MoviesOrSeries movies, MultipartFile file) throws Exception{
        validate(movies);
        Picture picture = pictureService.save(file);
        movies.setPicture(picture);
        moviesRepository.save(movies);
    }
    
    public void edit(MoviesOrSeries movies){
        moviesRepository.save(movies);
    }
    
    public MoviesOrSeries findById(String id) throws Exception{
        if (id.trim().isEmpty() || id == null) {
            throw new Exception("El id esta vacio");
        }
        Optional<MoviesOrSeries> moviesId = moviesRepository.findById(id);
        if (moviesId.isPresent()) {
            MoviesOrSeries movies = moviesId.get();
            return movies;
        } else {
            throw new Exception("No se encontro ninguna pelicula o serie con este id");
        }
    }
    
    public void remove(String id) throws Exception{
        moviesRepository.deleteById(id);
    }
    
    public void validate(MoviesOrSeries movies) throws Exception{
        if (movies.getTitle().trim().isEmpty() || movies.getTitle() == null) {
            throw new Exception("El titulo esta vacio");
        }
        if (movies.getCreationDate() == null) {
            throw new Exception("La fecha de creacion esta vacia");
        }
        if (movies.getQualification().toString().isEmpty() || movies.getQualification() == null) {
            throw new Exception("La calificacion esta vacia");
        }
    }
    
    public List<MoviesOrSeries> listAll(){
        return moviesRepository.findAll();
    }
    
    public List<MoviesOrSeries> findByTitle(String title){
        return moviesRepository.findByTitle(title);
    }
}
