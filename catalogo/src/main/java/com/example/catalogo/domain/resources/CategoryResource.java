package com.example.catalogo.domain.resources;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.catalogo.application.dtos.FilmShortDTO;
import com.example.catalogo.domain.entities.Category;
import com.example.catalogo.infraestructures.repositories.CategoryRepository;
import com.example.demo.infraestructure.exception.BadRequestException;
import com.example.demo.infraestructure.exception.InvalidDataException;
import com.example.demo.infraestructure.exception.NotFoundException;

@RestController
@RequestMapping(path = "/categorias")
public class CategoryResource {
	@Autowired
	private CategoryRepository dao;
	@Autowired
	private Validator validator;

	public Set<ConstraintViolation<@Valid Category>> validate(Category item) {
		return validator.validate(item);
	}

	public boolean isValid(Category item) {
		return validate(item).size() == 0;
	}

	public boolean notIsValid(Category item) {
		return !isValid(item);
	}

	@GetMapping
	public Page<Category> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@GetMapping(path = "/{id}")
	public Category getOne(@PathVariable byte id) throws Exception {
		Optional<Category> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get();
	}

	@GetMapping(path = "/{id}/peliculas")
	@Transactional
	public List<FilmShortDTO> getFilms(@PathVariable Byte id) throws Exception {
		Optional<Category> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmCategories().stream().map(item -> FilmShortDTO.from(item.getFilm()))
				.collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> add(@Valid @RequestBody Category item) throws Exception {
		if (notIsValid(item))
			throw new InvalidDataException("Invalid");
		if (dao.findById(item.getCategoryId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		dao.save(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(item.getCategoryId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	public Category change(@PathVariable int id, @Valid @RequestBody Category item) throws Exception {
		if (notIsValid(item))
			throw new InvalidDataException("Invalid");
		if (!dao.findById(item.getCategoryId()).isPresent())
			throw new NotFoundException("Missing item");
		if (item.getCategoryId() != id)
			throw new BadRequestException("No coinciden los ID");
		dao.save(item);
		return item;
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Byte id) throws Exception {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			throw new InvalidDataException("Missing item", e);
		}
	}
	public List<Category> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
