package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Pais;

@Repository
public interface PaisesRepository extends JpaRepository<Pais, Long> {

}
