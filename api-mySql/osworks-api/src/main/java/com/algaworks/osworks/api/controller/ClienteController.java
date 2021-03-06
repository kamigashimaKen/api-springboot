package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired 
	private ClienteRepository clienteRepository;
	
	@GetMapping("/comManager")
	public List<Cliente> listarComManager(){
		return manager.createQuery("from Cliente", Cliente.class).getResultList();
	}
	
	@GetMapping("/listar")
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();	
	}
	
	@GetMapping("/nome/{clienteNome}")
	public ResponseEntity<List<Cliente>> buscarPorNome(@PathVariable String clienteNome) {
//		return clienteRepository.findByNome("Ken");
//		return clienteRepository.findByNomeContaining("K");
		List<Cliente> clientes = clienteRepository.findByNomeContaining(clienteNome);
		
		if (clientes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(clientes);
	}
	
	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@PutMapping("/update/{idCliente}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long idCliente, @RequestBody Cliente cliente){
			if(!clienteRepository.existsById(idCliente)) {
				return ResponseEntity.notFound().build();
			}
			cliente.setId(idCliente);
			clienteRepository.save(cliente);
			
			return ResponseEntity.ok(cliente);
	}
	
	
}
