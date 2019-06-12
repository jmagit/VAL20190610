package com.example.demo.application.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.dtos.CityEditDTO;
import com.example.demo.domains.contracs.CityService;
import com.example.demo.domains.entities.City;
import com.example.demo.infraestructure.exception.BadRequestException;

@RestController
@RequestMapping(path = "/ciudades")
public class CityResource {
	@Autowired
	private CityService srv;

	@GetMapping
	public List<City> getAll() {
		return srv.getAll();
	}
	@GetMapping("/{id}")
	public CityEditDTO getOne(@PathVariable int id) throws Exception {
		return CityEditDTO.from(srv.get(id));
	}
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void add(@Valid @RequestBody CityEditDTO item) throws Exception {
		City rslt = CityEditDTO.from(item);
		srv.add(rslt);
	}
	@PutMapping("/{id}")
	public CityEditDTO change(@PathVariable int id, @Valid @RequestBody CityEditDTO item) throws Exception {
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
