package com.example.catalogo.domain.resources;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogo.application.dtos.NovedadesDTO;

@RestController
@RequestMapping(path = "/")
public class CatalogoResource {
	@Autowired
	private FilmResource filmSrv;
	@Autowired
	private ActorResource artorSrv;
	@Autowired
	private CategoryResource categorySrv;
	
	@GetMapping(path = "/novedades")
	public NovedadesDTO novedades(@RequestParam Timestamp fecha) {
		//Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
		return new NovedadesDTO(
				filmSrv.novedades(fecha),
				artorSrv.novedades(fecha),
				categorySrv.novedades(fecha));
	}

}
