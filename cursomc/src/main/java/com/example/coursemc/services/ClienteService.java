package com.example.coursemc.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coursemc.domain.Cidade;
import com.example.coursemc.domain.Cliente;
import com.example.coursemc.domain.Endereco;
import com.example.coursemc.domain.enums.TipoCliente;
import com.example.coursemc.dto.ClienteDTO;
import com.example.coursemc.dto.ClienteNewDTO;
import com.example.coursemc.repositories.ClienteRepository;
import com.example.coursemc.repositories.EnderecoRepository;
import com.example.coursemc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {       
	
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Nao e possivel pois existem entidades relacionadas!");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
	}
	
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		Cliente cli = new Cliente(null, objDto.getName(), objDto.getEmail(), objDto.getCpaOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),objDto.getBairro(), objDto.getCep(), cli, cid);	

		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null){
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null){
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

}
