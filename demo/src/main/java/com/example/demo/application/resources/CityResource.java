package com.example.demo.application.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.dtos.CityEditDTO;
import com.example.demo.domains.contracs.CityService;
import com.example.demo.domains.entities.Address;
import com.example.demo.domains.entities.City;
import com.example.demo.infraestructure.exception.BadRequestException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/ciudades")
@Api(value = "Mantenimiento de personas", description = "API que permite el ...")
public class CityResource {
	@Autowired
	private CityService srv;

	@GetMapping
	public Page<City> getAll(Pageable page) {
		return srv.getAll(page);
	}
	@GetMapping(params = "paginado=no")
	public List<CityEditDTO> getAll() {
		return srv.getAll().stream()
				.map(item->CityEditDTO.from(item))
				.collect(Collectors.toList());
	}
	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar una ciudad", notes = "Devuelve una ciudad por su identificador" )
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ciudad encontrada"),
		@ApiResponse(code = 404, message = "Ciudad no encontrada")
	})
	public CityEditDTO getOne(@PathVariable int id) throws Exception {
		return CityEditDTO.from(srv.get(id));
	}
	
	@Transactional
	@GetMapping("/{id}/direcciones")
	public List<Address> getDirecciones(@PathVariable int id) throws Exception {
		return srv.get(id).getAddresses();
	}
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void add(@Valid @RequestBody CityEditDTO item) throws Exception {
		City rslt = CityEditDTO.from(item);
		srv.add(rslt);
	}
	@PutMapping("/{id}")
	@ApiOperation(value = "Modificar una ciudad", notes = "Modifica una ciudad por su identificador" )
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ciudad modificada"),
		@ApiResponse(code = 400, message = "Datos invalidos"),
		@ApiResponse(code = 404, message = "Ciudad no encontrada")
	})
	public CityEditDTO change(@ApiParam(name = "Id de ciudad", required = true) @PathVariable int id, @Valid @RequestBody CityEditDTO item) throws Exception {
		if(id != item.getCityId())
			throw new BadRequestException("No coinciden los identificadores");
		City rslt = CityEditDTO.from(item);
		return CityEditDTO.from(srv.change(rslt));
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws Exception {
		srv.delete(id);
	}
}
