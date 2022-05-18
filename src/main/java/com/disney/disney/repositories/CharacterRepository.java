package com.disney.disney.repositories;

import com.disney.disney.entities.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Personaje, String>{

}
