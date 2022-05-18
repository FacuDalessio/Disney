package com.disney.disney.repositories;

import com.disney.disney.entities.MoviesOrSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<MoviesOrSeries, String>{

    
}
