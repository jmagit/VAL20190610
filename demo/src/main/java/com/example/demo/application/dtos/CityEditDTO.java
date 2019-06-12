package com.example.demo.application.dtos;

import com.example.demo.domains.entities.City;
import com.example.demo.domains.entities.Country;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "CiudadEditable", description = "Versión editable de la ciudad")
@Data @AllArgsConstructor @NoArgsConstructor
public class CityEditDTO {
	@ApiModelProperty(value = "Identificador de la ciudad", required = true)
	private int cityId;
	@ApiModelProperty(value = "Nombre de la ciudad, tiene que tener entre 2 y 50", required = true)
	private String city;
	@ApiModelProperty(value = "Identificador del pais", required = true)
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
