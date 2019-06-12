package com.example.catalogo.infraestructures.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogo.domain.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	<T> List<T> findByFilmIdNotNull(Class<T> type);
}
