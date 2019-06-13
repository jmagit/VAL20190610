package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.application.dtos.ActorDTO;
import com.example.demo.application.dtos.ActorShortDTO;
import com.example.demo.application.dtos.CityEditDTO;
import com.example.demo.application.dtos.CityShortDTO;
import com.example.demo.application.proxies.ActorProxy;
import com.example.demo.domains.contracs.CityService;
import com.example.demo.domains.entities.City;
import com.example.demo.infraestructure.repositories.CityRepository;
import com.example.demo.ioc.Linea;
import com.example.demo.ioc.Punto;
import com.example.demo.ioc.Servicio;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients("com.example.demo.application.proxies")
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Autowired
//	@Qualifier("2D")
//	private Punto p1;
//	@Autowired
//	@Qualifier("3D")
//	private Punto p2;
	
//	@Autowired
//	private Linea linea;
//	
//	@Value("${mi.propiedad}")
//	private String cad;
//	
//	@Autowired
//	private Servicio miServicio;
	
//	@Autowired
//	private CityRepository dao;
	
//	@Autowired
//	private CityService srv;
	
//	@Autowired
//	private RestTemplate rest;
	
	@Autowired
	private ActorProxy proxy;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
//		System.out.println(p1.toString());
//		p1.setX(100);
//		System.out.println(p2);
//		System.out.println(linea);
//		System.out.println(cad);
//		miServicio.saluda();
//		dao.findByCityStartingWith("a")
//			.forEach(item -> System.out.println(item));
//		dao.findByCityCode(5)
//			.forEach(item -> System.out.println(item));
//		dao.findByCityCode(5).stream()
//			.map(item -> CityEditDTO.from(item))
//			.forEach(item -> System.out.println(item));
//		Optional<City> r = dao.findById(777);
//		if(r.isPresent()) {
//			System.out.println(CityEditDTO.from(r.get()).getCountryId());
//		} else {
//			System.out.println("No encontrado");
//		}
//		dao.findByCityIdNotNull(CityShortDTO.class)
//			.forEach(item -> System.out.println(item.getCityId() + " " + item.getCity()));
//		City c = CityEditDTO.from(new CityEditDTO(7780, "valencia", 87));
//		if(srv.isNotValid(c))
//			System.out.println("Invalido");
//		else
//			srv.change(c);
//		srv.delete(7778);
//		srv.delete(7779);
//		srv.getAll().forEach(item -> System.out.println(item));
//		City c = srv.get(1);
//		c.getAddresses().forEach(item -> System.out.println(item));
//		ActorDTO a = rest.getForObject("http://localhost:8002/actores/{id}", ActorDTO.class, 1);
//		System.out.println(a);
//		ResponseEntity<List<ActorShortDTO>> response = rest.exchange(
//				"http://localhost:8002/actores/lista", 
//				HttpMethod.GET,
//				HttpEntity.EMPTY, 
//				new ParameterizedTypeReference<List<ActorShortDTO>>() {
//				});
//		List<ActorShortDTO> rsltActorDTOs = response.getBody();
//		rsltActorDTOs.forEach(item -> System.out.println(item));
		ActorDTO a = proxy.getOne(1);
		System.out.println(a);
		proxy.getAll().forEach(item -> System.out.println(item));

	}

}
