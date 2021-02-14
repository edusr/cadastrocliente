package org.edu.cadastrocliente.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.edu.cadastrocliente.CadastroclienteApplication;
import org.edu.cadastrocliente.domain.Estado;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.repository.EstadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CadastroclienteApplication.class)
@WebAppConfiguration
class EstadoServiceTest {

	@Autowired
	private EstadoService estadoService;
	@MockBean
	private EstadoRepository estadoRepository;

	@Test
	public void salvarEstadoSemSigla() {

		Estado estado = new Estado();

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> estadoService.salvar(estado));

		assertEquals("Os campos Sigla e Nome são obrigatórios", exception.getMessage());
	}

	@Test
	public void salvarEstadoSemNome() {

		Estado estado = new Estado();
		estado.setSigla("MG");

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> estadoService.salvar(estado));

		assertEquals("Os campos Sigla e Nome são obrigatórios", exception.getMessage());
	}

	@Test
	public void salvarSucesso() {

		Estado estado = new Estado();
		estado.setSigla("MG");
		estado.setNome("Minas Gerais");

		Mockito.when(estadoRepository.save(ArgumentMatchers.any(Estado.class))).thenReturn(new Estado());

		assertDoesNotThrow(() -> estadoService.salvar(estado));

	}

}
