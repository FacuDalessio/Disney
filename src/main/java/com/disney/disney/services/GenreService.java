package com.disney.disney.services;

import com.disney.disney.entities.Genre;
import com.disney.disney.enums.Genres;
import com.disney.disney.repositories.GenreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
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
    }
    
    @Transactional(rollbackOn={Exception.class})
    public void edit(Genre genre) throws Exception{
        genreRepository.save(genre);
    }
    public List<Genre> listAll(){
        return genreRepository.findAll();
    } 
    
    public List<String> listEnum(){
        List<String> genders = new ArrayList();
        for (Genres genre : Genres.values()) {
            genders.add(genre.getValor());
        }
        return genders;
    }
    
    public Genre findById(String id) throws Exception{
        if (id.trim().isEmpty() || id == null) {
            throw new Exception("el id esta vacio");
        }
        Optional<Genre> genreId = genreRepository.findById(id);
        if (genreId.isPresent()) {
            Genre genre =  genreId.get();
            return genre;
        } else {
            throw new Exception("no se encontro un genero con ese id");
        }
    }
}
