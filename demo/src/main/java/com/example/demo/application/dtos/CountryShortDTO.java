package com.example.demo.application.dtos;

import org.springframework.data.rest.core.config.Projection;

import com.example.demo.domains.entities.Country;

@Projection(name = "PaisCorto", types = { Country.class }) 
public interface CountryShortDTO {
	int getCountryId();
	String getCountry();
}
