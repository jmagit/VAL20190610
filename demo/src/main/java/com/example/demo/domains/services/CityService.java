package com.example.demo.domains.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domains.entities.City;
import com.example.demo.infraestructure.exception.InvalidDataException;
import com.example.demo.infraestructure.exception.NotFoundException;
import com.example.demo.infraestructure.repositories.CityRepository;

@Service
public class CityService implements com.example.demo.domains.contracs.CityService {
	@Autowired
	private CityRepository dao;
	
	@Autowired
	private Validator validator;
	
	@Override
	public Set<ConstraintViolation<City>> validate(City item) {
		return validator.validate(item);
	}
	@Override
	public boolean isValid(City item) {
		return validate(item).size() == 0;
	}
	@Override
	public boolean isNotValid(City item) {
		return !isValid(item);
	}

	@Override
	public List<City> getAll() {
		return dao.findAll();
	}
	@Override
	public Page<City> getAll(Pageable page) {
		return dao.findAll(page);
	}
	@Override
	public City get(int id) throws Exception {
		Optional<City> rslt = dao.findById(id);
		if(!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get();
	}
	@Override
	@Transactional
	public City add(City item) throws Exception {
		if(isNotValid(item))
			throw new InvalidDataException("Invalid data");
		Optional<City> rslt = dao.findById(item.getCityId());
		if(rslt.isPresent())
			throw new InvalidDataException("Duplicate key");
		return dao.save(item);		
	}
	@Override
	@Transactional
	public City change(City item) throws Exception {
		if(isNotValid(item))
			throw new InvalidDataException("Invalid data");
		Optional<City> rslt = dao.findById(item.getCityId());
		if(!rslt.isPresent())
			throw new NotFoundException();
		return dao.save(item);		
	}
	@Override
	@Transactional
	public void delete(int id) throws Exception {
		Optional<City> rslt = dao.findById(id);
		if(!rslt.isPresent())
			throw new NotFoundException();
		dao.deleteById(id);;		
	}
	@Override
	@Transactional
	public void delete(City item) throws Exception {
		delete(item.getCityId());		
	}
}
