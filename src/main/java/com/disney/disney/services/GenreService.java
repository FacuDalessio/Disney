package com.disney.disney.services;

import com.disney.disney.entities.Genre;
import com.disney.disney.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    private GenreRepository genreRepository;
    
    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    
    public void save(Genre genre) throws Exception{
        validate(genre);
        genreRepository.save(genre);
    }
    public void validate(Genre genre) throws Exception{
        if (genre.getName() == null) {
            throw new Exception("Tiene que indicar un genero");
        }
        if (genre.getMoviesOrSeries() == null) {
            throw new Exception("Tiene que indicar al menos una pelicula");
        }
    }
}
