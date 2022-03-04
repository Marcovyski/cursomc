package com.example.coursemc.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.coursemc.domain.Categoria;
import com.example.coursemc.repositories.CategoriaRepository;
import com.example.coursemc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {       
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}