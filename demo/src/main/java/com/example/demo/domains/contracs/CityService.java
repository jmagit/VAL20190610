package com.example.demo.domains.contracs;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.domains.entities.City;

public interface CityService {

	Set<ConstraintViolation<City>> validate(City item);

	boolean isValid(City item);

	boolean isNotValid(City item);

	List<City> getAll();

	Page<City> getAll(Pageable page);

	City get(int id) throws Exception;

	City add(City item) throws Exception;

	City change(City item) throws Exception;

	void delete(int id) throws Exception;

	void delete(City item) throws Exception;

}