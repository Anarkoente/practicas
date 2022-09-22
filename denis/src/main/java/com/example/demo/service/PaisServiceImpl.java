package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Pais;
import com.example.demo.repository.PaisesRepository;

@Service("paisesservice")
public class PaisServiceImpl implements PaisService {

	@Autowired
	@Qualifier("paisesRepository")
	private PaisesRepository paisesRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Pais> findAll() {

		return paisesRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pais> findAll(Pageable pageable) {

		return paisesRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pais> findById(Long id) {

		return paisesRepository.findById(id);
	}

	@Override
	@Transactional
	public Pais save(Pais pais) {

		return paisesRepository.save(pais);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		paisesRepository.deleteById(id);

	}

	@Override
	public List<Pais> listAllPaises() {

		return paisesRepository.findAll();
	}

	@Override
	public Pais addpais(Pais pais) {

		return paisesRepository.save(pais);
	}



	

	

}
