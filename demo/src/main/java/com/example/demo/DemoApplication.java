package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.infraestructure.repositories.CityRepository;
import com.example.demo.ioc.Linea;
import com.example.demo.ioc.Punto;
import com.example.demo.ioc.Servicio;

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
	
	@Autowired
	private CityRepository dao;
	
	@Override
	public void run(String... args) throws Exception {
//		System.out.println(p1.toString());
//		p1.setX(100);
//		System.out.println(p2);
//		System.out.println(linea);
//		System.out.println(cad);
//		miServicio.saluda();
		dao.findAll()
			.forEach(item -> System.out.println(item));
	}

}
