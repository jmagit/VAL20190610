package com.example.demo.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domains.entities.City;

public interface CityRepository extends JpaRepository<City, Integer> {

}
