package com.example.catalogo.infraestructures.repositories;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.catalogo.application.dtos.ActorShortDTO;
import com.example.catalogo.domain.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	List<ActorShortDTO> findByActorIdNotNull();
	<T> List<T> findByActorIdNotNull(Class<T> type);

	@Query("from Actor")
	Collection<ActorShortDTO> getAll();

}
