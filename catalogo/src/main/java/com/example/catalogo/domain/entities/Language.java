package com.example.catalogo.domain.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the language database table.
 * 
 */
@Entity
public class Language implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="language_id")
	private byte languageId;

	@Column(name="last_update")
	@JsonIgnore
	private Timestamp lastUpdate;

	@NotNull
	@Size(max=20)
	private String name;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="language")
	@JsonIgnore
	private List<Film> films;

	//bi-directional many-to-one association to Film
	@OneToMany(mappedBy="languageVO")
	@JsonIgnore
	private List<Film> filmsVO;

	public Language() {
	}

	public Language(byte languageId) {
		this.languageId = languageId;
	}

	public Language(byte languageId, String name) {
		this.languageId = languageId;
		this.name = name;
	}

	public byte getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(byte languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilms() {
		return this.films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	public Film addFilm(Film film) {
		getFilms().add(film);
		film.setLanguage(this);

		return film;
	}

	public Film removeFilm(Film film) {
		getFilms().remove(film);
		film.setLanguage(null);

		return film;
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	public Film addFilmsVO(Film filmsVO) {
		getFilmsVO().add(filmsVO);
		filmsVO.setLanguageVO(this);

		return filmsVO;
	}

	public Film removeFilmsVO(Film filmsVO) {
		getFilmsVO().remove(filmsVO);
		filmsVO.setLanguageVO(null);

		return filmsVO;
	}

	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", name=" + name + "]";
	}

}