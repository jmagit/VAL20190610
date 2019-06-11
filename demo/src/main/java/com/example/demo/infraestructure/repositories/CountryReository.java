package com.example.demo.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domains.entities.Country;

public interface CountryReository extends JpaRepository<Country, Integer> {

}
