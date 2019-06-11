package com.example.demo.application.dtos;

import com.example.demo.domains.entities.City;
import com.example.demo.domains.entities.Country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CityEditDTO {
	private int cityId;
	private String city;
	private Integer countryId;

	public static CityEditDTO from(City source) {
		return new CityEditDTO(
					source.getCityId(),
					source.getCity(),
					source.getCountry() == null ? null : source.getCountry().getCountryId()
				);
	}
	public static City from(CityEditDTO source) {
		return new City(
					source.getCityId(),
					source.getCity(),
					source.getCountryId() == null ? null : new Country(source.getCountryId())
				);
	}
}
