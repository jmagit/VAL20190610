package com.example.demo.domains.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domains.entities.City;
import com.example.demo.infraestructure.repositories.CityRepository;

@Service
public class CityService {
	@Autowired
	private CityRepository dao;
	
	@Autowired
	private Validator validator;
	
	public Set<ConstraintViolation<City>> validate(City item) {
		return validator.validate(item);
	}
	public boolean isValid(City item) {
		return validate(item).size() == 0;
	}
	public boolean isNotValid(City item) {
		return !isValid(item);
	}

	public List<City> getAll() {
		return dao.findAll();
	}
	public Page<City> getAll(Pageable page) {
		return dao.findAll(page);
	}
	public City get(int id) throws Exception {
		Optional<City> rslt = dao.findById(id);
		if(!rslt.isPresent())
			throw new Exception();
		return rslt.get();
	}
}
