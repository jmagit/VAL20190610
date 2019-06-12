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

import com.example.catalogo.application.dtos.ActorDTO;
import com.example.catalogo.application.dtos.ActorShortDTO;
import com.example.catalogo.application.dtos.FilmShortDTO;
import com.example.catalogo.domain.entities.Actor;
import com.example.catalogo.infraestructures.repositories.ActorRepository;
import com.example.demo.infraestructure.exception.BadRequestException;
import com.example.demo.infraestructure.exception.InvalidDataException;
import com.example.demo.infraestructure.exception.NotFoundException;

@RestController
@RequestMapping(path = "/actores")
public class ActorResource {
	@Autowired
	private ActorRepository dao;
	@Autowired
	private Validator validator;

	public Set<ConstraintViolation<@Valid Actor>> validate(Actor item) {
		return validator.validate(item);
	}

	public boolean isValid(Actor item) {
		return validate(item).size() == 0;
	}

	public boolean notIsValid(Actor item) {
		return !isValid(item);
	}

	@GetMapping
	public Page<Actor> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}
	@GetMapping(path = "/lista")
	public List<ActorShortDTO> getAll() {
		return dao.findByActorIdNotNull(ActorShortDTO.class);
	}


	@GetMapping(path = "/{id}")
	public ActorDTO getOne(@PathVariable int id) throws Exception {
		Optional<Actor> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return ActorDTO.from(rslt.get());
	}

	@GetMapping(path = "/{id}/peliculas")
	@Transactional
	public List<FilmShortDTO> getFilms(@PathVariable int id) throws Exception {
		Optional<Actor> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmActors().stream().map(item -> FilmShortDTO.from(item.getFilm()))
				.collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> add(@Valid @RequestBody ActorDTO item) throws Exception {
		Actor rslt = dao.save(ActorDTO.from(item));
		if (notIsValid(rslt))
			throw new InvalidDataException("Invalid");
		if (dao.findById(item.getActorId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		dao.save(rslt);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(rslt.getActorId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	public ActorDTO change(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws Exception {
		Actor rslt = dao.save(ActorDTO.from(item));
		if (notIsValid(rslt))
			throw new InvalidDataException("Invalid");
		if (!dao.findById(item.getActorId()).isPresent())
			throw new NotFoundException();
		if (item.getActorId() != id)
			throw new BadRequestException("No coinciden los ID");
		dao.save(rslt);
		return ActorDTO.from(dao.save(ActorDTO.from(item)));
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws Exception {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			throw new InvalidDataException("Missing item", e);
		}
	}
	public List<Actor> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
