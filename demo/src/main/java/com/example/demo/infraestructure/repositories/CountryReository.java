package com.example.demo.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.demo.domains.entities.Country;

@RepositoryRestResource(
		//excerptProjection = CountryShortDTO.class,
		path="paises", 
		itemResourceRel = "pais", collectionResourceRel="paises"
		)
public interface CountryReository extends JpaRepository<Country, Integer> {
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);
}
