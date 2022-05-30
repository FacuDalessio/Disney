package com.disney.disney.repositories;

import com.disney.disney.entities.Personaje;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Personaje, String>{

    public List<Personaje> findByName(String name);
    
    public List<Personaje> findByAge(Integer age);
    
//    @Query("SELECT p FROM Personaje p WHERE p.moviesOrSeries.id LIKE %:id%")
//    public List<Personaje> findByMoviesOrSeriesId(@Param("id") String id);
}
