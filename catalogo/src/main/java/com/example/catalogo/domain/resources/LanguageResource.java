package com.example.catalogo.domain.resources;

import java.net.URI;
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
import com.example.catalogo.domain.entities.Language;
import com.example.catalogo.infraestructures.repositories.LanguageRepository;
import com.example.demo.infraestructure.exception.BadRequestException;
import com.example.demo.infraestructure.exception.InvalidDataException;
import com.example.demo.infraestructure.exception.NotFoundException;

@RestController
@RequestMapping(path = "/idiomas")
public class LanguageResource {
	@Autowired
	private LanguageRepository dao;
	@Autowired
	private Validator validator;

	public Set<ConstraintViolation<@Valid Language>> validate(Language item) {
		return validator.validate(item);
	}

	public boolean isValid(Language item) {
		return validate(item).size() == 0;
	}

	public boolean notIsValid(Language item) {
		return !isValid(item);
	}

	@GetMapping
	public Page<Language> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@GetMapping(path = "/{id}")
	public Language getOne(@PathVariable byte id) throws Exception {
		Optional<Language> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get();
	}

	@GetMapping(path = "/{id}/peliculas")
	@Transactional
	public List<FilmShortDTO> getFilms(@PathVariable Byte id) throws Exception {
		Optional<Language> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilms().stream().map(item -> FilmShortDTO.from(item))
				.collect(Collectors.toList());
	}
	@GetMapping(path = "/{id}/vo")
	@Transactional
	public List<FilmShortDTO> getFilmsVO(@PathVariable Byte id) throws Exception {
		Optional<Language> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmsVO().stream().map(item -> FilmShortDTO.from(item))
				.collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> add(@Valid @RequestBody Language item) throws Exception {
		if (notIsValid(item))
			throw new InvalidDataException("Invalid");
		if (dao.findById(item.getLanguageId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		dao.save(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(item.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	public Language change(@PathVariable int id, @Valid @RequestBody Language item) throws Exception {
		if (notIsValid(item))
			throw new InvalidDataException("Invalid");
		if (!dao.findById(item.getLanguageId()).isPresent())
			throw new NotFoundException();
		if (item.getLanguageId() != id)
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

}
