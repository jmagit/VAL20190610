package com.example.demo.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.demo.application.dtos.CountryShortDTO;
import com.example.demo.domains.entities.Country;

@RepositoryRestResource(
		path="paises", 
		itemResourceRel = "pais", collectionResourceRel="paises",
		excerptProjection = CountryShortDTO.class)
public interface CountryReository extends JpaRepository<Country, Integer> {
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);
}
