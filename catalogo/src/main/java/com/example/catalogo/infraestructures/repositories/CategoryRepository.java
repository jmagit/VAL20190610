package com.example.catalogo.infraestructures.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogo.domain.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Byte>  {
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
