package com.algaworks.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.osworks.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	//Pra consultar pelo nome
	List<Cliente> findByNome(String nome);
	
	//Consultando com like *tem que colocar Containing
	List<Cliente> findByNomeContaining(String string);

}
