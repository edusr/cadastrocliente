package org.edu.cadastrocliente.controller;

import java.util.Optional;

import org.edu.cadastrocliente.domain.Cliente;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
	public ResponseEntity<?> salvarCliente(@RequestBody Cliente cliente) throws ValidacaoException {

		return ResponseEntity.ok(clienteService.salvar(cliente));
	}

	@PutMapping("alterarNome")
	public ResponseEntity<?> alterarNomeCliente(@RequestBody Cliente cliente) throws ValidacaoException {

		return ResponseEntity.ok(clienteService.alterarNomeCliente(cliente));

	}
	
	@GetMapping("nome")
	public ResponseEntity<?> buscarPorNome(@RequestParam String nomeCliente) {

		try {
			return ResponseEntity.ok(clienteService.buscarPorNome(nomeCliente));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> buscarPorCodigo(@RequestParam Long codigoCliente) {

		try {
			Optional<Cliente> clienteOptional = clienteService.buscarPorCodigo(codigoCliente);
			if (clienteOptional.isPresent()) {
				return ResponseEntity.ok(clienteOptional.get());
			}
			return ResponseEntity.notFound().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> excluirCliente(@RequestParam Long codigoCliente) throws ValidacaoException {

		clienteService.excluirCliente(codigoCliente);
		return ResponseEntity.ok("Cliente excluído com sucesso!");

	}

}
