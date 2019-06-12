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
import com.example.catalogo.application.dtos.FilmDetailsDTO;
import com.example.catalogo.application.dtos.FilmEditDTO;
import com.example.catalogo.application.dtos.FilmShortDTO;
import com.example.catalogo.domain.entities.Category;
import com.example.catalogo.domain.entities.Film;
import com.example.catalogo.infraestructures.repositories.FilmRepository;
import com.example.demo.infraestructure.exception.BadRequestException;
import com.example.demo.infraestructure.exception.InvalidDataException;
import com.example.demo.infraestructure.exception.NotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(value = "/peliculas", description = "Mantenimiento de peliculas", produces = "application/json, application/xml", consumes="application/json, application/xml")
@RequestMapping(path = "/peliculas")
public class FilmResource {
	@Autowired
	private FilmRepository dao;
	@Autowired
	private Validator validator;

	public Set<ConstraintViolation<@Valid Film>> validate(Film item) {
		return validator.validate(item);
	}

	public boolean isValid(Film item) {
		return validate(item).size() == 0;
	}

	public boolean notIsValid(Film item) {
		return !isValid(item);
	}

	@ApiOperation(value = "Listado de las peliculas")
	@ApiParam(name = "mode", allowableValues = "short, details")
	@GetMapping
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}
	@ApiOperation(value = "Listado con la versión mínima de las peliculas")
	@GetMapping(params = "mode=short")
	public List<FilmShortDTO> getAll() {
		return dao.findAll().stream()
				.map(item-> FilmShortDTO.from(item))
				.collect(Collectors.toList());
	}
	@GetMapping(path = "/{id}", params = "mode=short")
	@ApiOperation(value = "Recupera la versión mínima de una pelicula")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Pelicula encontrada"),
		@ApiResponse(code = 404, message = "Pelicula no encontrada")
	})
	public FilmShortDTO getOneCorto(
			@ApiParam(value = "Identificador de la pelicula", required = true) @PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmShortDTO.from(rslt.get());
	}

	@ApiOperation(value = "Recupera la versión completa de una pelicula")
	@GetMapping(path = "/{id}", params = "mode=details")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Pelicula encontrada"),
		@ApiResponse(code = 404, message = "Pelicula no encontrada")
	})
	public FilmDetailsDTO getOneDetalle(@ApiParam(value = "Identificador de la pelicula", required = true) @PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmDetailsDTO.from(rslt.get());
	}
	@ApiOperation(value = "Recupera la versión editable de una pelicula")
	@GetMapping(path = "/{id}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Pelicula encontrada"),
		@ApiResponse(code = 404, message = "Pelicula no encontrada")
	})
	public FilmEditDTO getOne(@ApiParam(value = "Identificador de la pelicula", required = true) @PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return FilmEditDTO.from(rslt.get());
	}

	@ApiOperation(value = "Listado de los actores de la pelicula")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Pelicula encontrada"),
		@ApiResponse(code = 404, message = "Pelicula no encontrada")
	})
	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getFilms(@ApiParam(value = "Identificador de la pelicula", required = true) @PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmActors().stream().map(item -> ActorDTO.from(item.getActor()))
				.collect(Collectors.toList());
	}
	@ApiOperation(value = "Listado de las categorias de la pelicula")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Pelicula encontrada"),
		@ApiResponse(code = 404, message = "Pelicula no encontrada")
	})
	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(@ApiParam(value = "Identificador de la pelicula", required = true) @PathVariable int id) throws Exception {
		Optional<Film> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmCategories().stream().map(item -> item.getCategory())
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Añadir una nueva pelicula")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> add(@Valid @RequestBody FilmEditDTO item) throws Exception {
		Film rslt = dao.save(FilmEditDTO.from(item));
		if (notIsValid(rslt))
			throw new InvalidDataException("Invalid");
		if (dao.findById(item.getFilmId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		dao.save(rslt);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(rslt.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@ApiOperation(value = "Modificar una pelicula existente", notes = "Los identificadores deben coincidir")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Pelicula encontrada"),
		@ApiResponse(code = 404, message = "Pelicula no encontrada")
	})
	@Transactional
	@PutMapping(path = "/{id}")
	public FilmEditDTO change(
			@ApiParam(value = "Identificador de la pelicula", required = true) @PathVariable int id, 
			@Valid @RequestBody FilmEditDTO item) throws Exception {
		Film rslt = FilmEditDTO.from(item);
		if (notIsValid(rslt))
			throw new InvalidDataException("Invalid");
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los ID");
		Optional<Film> act = dao.findById(item.getFilmId());
		if (!act.isPresent())
			throw new NotFoundException("Missing item");
		return FilmEditDTO.from(dao.save(item.update(act.get())));
	}

	@ApiOperation(value = "Borrar una pelicula existente")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Pelicula encontrada"),
		@ApiResponse(code = 404, message = "Pelicula no encontrada")
	})
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(
			@ApiParam(value = "Identificador de la pelicula", required = true) @PathVariable int id) throws Exception {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			throw new NotFoundException("Missing item", e);
		}
	}

	public List<Film> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}
