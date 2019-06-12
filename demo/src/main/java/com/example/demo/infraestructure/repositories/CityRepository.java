package com.example.demo.infraestructure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.domains.entities.City;

@RepositoryRestResource(exported = false)
public interface CityRepository extends JpaRepository<City, Integer> {
	List<City> findByCityStartingWith(String pefijo);
	@Query("from City where cityId < ?1")
	List<City> findByCityCode(int limite);
	
	<T> List<T> findByCityIdNotNull(Class<T> type);
	
}
