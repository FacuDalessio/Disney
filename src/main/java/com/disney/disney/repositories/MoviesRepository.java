package com.disney.disney.repositories;

import com.disney.disney.entities.MoviesOrSeries;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<MoviesOrSeries, String>{

    public List<MoviesOrSeries> findByTitle(String title);
}
