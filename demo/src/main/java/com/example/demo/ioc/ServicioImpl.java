package com.example.demo.ioc;

import org.springframework.stereotype.Service;

//@Service
public class ServicioImpl implements Servicio {
	@Override
	public void saluda() {
		System.out.println("Hola MUNDO");
	}
}
