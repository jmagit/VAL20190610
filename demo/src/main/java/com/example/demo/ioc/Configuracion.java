package com.example.demo.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracion {
	@Value("${puntos.3D}")
	private boolean tipoPunto;
	
	@Bean
	public Punto punto() {
		return tipoPunto ? new Punto3D() : new Punto();
	}
}
