package com.example.catalogo.domain.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="category_id")
	private byte categoryId;

	@Column(name="last_update")
	@JsonIgnore
	private Timestamp lastUpdate;

	@NotNull
	@Size(max=25)
	private String name;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="category")
	@JsonIgnore
	private List<FilmCategory> filmCategories;

	public Category() {
	}

	public Category(byte categoryId) {
		this.categoryId = categoryId;
	}

	public byte getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(byte categoryId) {
		this.categoryId = categoryId;
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

	public List<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(List<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setCategory(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setCategory(null);

		return filmCategory;
	}

}