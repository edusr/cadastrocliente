package org.edu.cadastrocliente.controller;

import java.util.List;

import org.edu.cadastrocliente.domain.Cidade;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@GetMapping("/nome")
	public ResponseEntity<?> buscarCidadesPorNome(@RequestParam(required = true) String nomeCidade) {

		List<Cidade> cidades = cidadeService.buscarCidadePorNome(nomeCidade);

		if (cidades == null || cidades.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(cidades);
		}
	}

	@GetMapping("/estado")
	public ResponseEntity<?> buscarCidadesPorEstado(@RequestParam(required = true) String siglaEstado) {

		try {
			List<Cidade> cidades = cidadeService.buscarCidadePorEstado(siglaEstado);
			if (cidades == null || cidades.isEmpty()) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(cidades);
			}
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {

		try {
			return ResponseEntity.ok(cidadeService.salvarCidade(cidade));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
