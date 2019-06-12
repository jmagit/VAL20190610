package com.example.catalogo.infraestructures.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogo.domain.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Byte> {

}
