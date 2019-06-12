package com.example.demo.domains.entities.projections;

import org.springframework.data.rest.core.config.Projection;

import com.example.demo.domains.entities.Country;

@Projection(name = "paisCorto", types = { Country.class }) 
public interface CountryShortDTO {
	int getCountryId();
	String getCountry();
}
