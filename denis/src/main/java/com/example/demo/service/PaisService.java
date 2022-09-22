package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Pais;

public interface PaisService {
	
	public abstract List<Pais> listAllPaises();
	
	public abstract Pais addpais (Pais pais);

	public Iterable<Pais> findAll();

	public Page<Pais> findAll(Pageable pageable);

	public Optional<Pais> findById(Long id);

	public Pais save(Pais pais);

	public void deleteById(Long id);
	
}
