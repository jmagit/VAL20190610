package com.example.catalogo.application.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.example.catalogo.domain.entities.Film;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "PeliculaDetalles", description = "Version completa de las peliculas")
@Data @AllArgsConstructor @NoArgsConstructor
public class FilmDetailsDTO {
	@ApiModelProperty(value = "Identificador de la pelicula", required = true, accessMode = AccessMode.READ_ONLY)
	private int filmId;
	@ApiModelProperty(value = "Descripcion de la pelicula")
	private String description;
	@ApiModelProperty(value = "Duración de la pelicula", required = true)
	private int length;
	private String rating;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	@ApiModelProperty(value = "Duración del alquiler")
	private Byte rentalDuration;
	@ApiModelProperty(value = "Precio del alquiler")
	private BigDecimal rentalRate;
	@ApiModelProperty(value = "Coste de remplazo")
	private BigDecimal replacementCost;
	@ApiModelProperty(value = "Titulo de la pelicula", required = true, allowableValues = "Un maximo de 255 caracteres")
	private String title;
	@ApiModelProperty(value = "Identificador del idioma de la pelicula")
	private String languageId;
	@ApiModelProperty(value = "Identificador del idioma de la versión original de la pelicula")
	private String languageVOId;
	private List<ActorDTO> filmActors;
	private List<String> filmCategories;
	
	public static FilmDetailsDTO from(Film source) {
		return new FilmDetailsDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getName(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
				source.getFilmActors().stream().map(item -> ActorDTO.from(item.getActor()))
					.collect(Collectors.toList()),
				source.getFilmCategories().stream().map(item -> item.getCategory().getName())
					.collect(Collectors.toList())
				);
	}
}
