package com.example.catalogo.application.dtos;

import java.util.List;

import com.example.catalogo.domain.entities.Actor;
import com.example.catalogo.domain.entities.Category;
import com.example.catalogo.domain.entities.Film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovedadesDTO {
	private List<Film> films;
	private List<Actor> actors;
	private List<Category> categories;
	
}
