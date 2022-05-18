package com.disney.disney.services;

import com.disney.disney.entities.Personaje;
import com.disney.disney.repositories.CharacterRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    private CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }
    
    @Transactional(rollbackOn = {Exception.class})
    public void save(Personaje character) throws Exception{
        validate(character);
        characterRepository.save(character);
    }
    
    @Transactional(rollbackOn={Exception.class})
    public void edit(Personaje character){
        characterRepository.save(character);
    }
    
    public Personaje findById(String id) throws Exception{
        if (id.trim().isEmpty() || id == null) {
            throw new Exception("el id esta vacio");
        }
        Optional<Personaje> characterId = characterRepository.findById(id);
        if (characterId.isPresent()) {
            Personaje character =  characterId.get();
            return character;
        } else {
            throw new Exception("no se encontro un personaje con ese id");
        }
    }
    
    public void remove(String id) throws Exception{
        characterRepository.deleteById(id);
    }
    
    public void validate(Personaje character) throws Exception{
        if (character.getName().trim().isEmpty() || character.getName() == null) {
            throw new Exception("El nombre esta vacio");
        }
        if (character.getAge().toString().trim().isEmpty() || character.getAge() == null) {
            throw new Exception("La edad esta vacia");
        }
        if (character.getWeight().toString().trim().isEmpty() || character.getWeight() == null) {
            throw new Exception("El peso esta vacio");
        }
        if (character.getHistory().trim().isEmpty() || character.getHistory() == null) {
            throw new Exception("La historia esta vacia");
        }
        if (character.getMoviesOrSeries() == null) {
            throw new Exception("Debe seleccionar al menos un personaje");
        }
    }
}
