package com.example.demo.domains.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="country_id")
	private int countryId;

	@NotBlank
	@Size(max = 50)
	private String country;

	@Column(name="last_update")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to City
	@OneToMany(mappedBy="country")
	@JsonIgnore
	private List<City> cities;

	public Country() {
	}

	public Country(int countryId) {
		super();
		this.countryId = countryId;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public City addCity(City city) {
		getCities().add(city);
		city.setCountry(this);

		return city;
	}

	public City removeCity(City city) {
		getCities().remove(city);
		city.setCountry(null);

		return city;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", country=" + country + "]";
	}

}