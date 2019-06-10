package com.example.demo.ioc;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Linea implements Servicio {
	@Autowired
	private Punto pIni, pFinal;

	public Linea() {
		//String cadString = pFinal.toString();
	}
	@PostConstruct
	private void inicia() {
		System.out.println(pFinal.toString());
	}
	@Override
	public String toString() {
		return "Linea [pIni=" + pIni + ", pFinal=" + pFinal + "]";
	}
	@Override
	public void saluda() {
		System.out.println("Hola soy la linea");
	}
	
	
}
