package org.edu.cadastrocliente.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Optional;

import org.edu.cadastrocliente.CadastroclienteApplication;
import org.edu.cadastrocliente.domain.Cidade;
import org.edu.cadastrocliente.domain.Estado;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.repository.CidadeRepository;
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
class CidadeServiceTest {
	
	@Autowired
	private CidadeService cidadeService;
	
	@MockBean
	private CidadeRepository cidadeRepository;
	@MockBean
	private EstadoService estadoService;
	
	@Test
	public void salvarCidadeSemEstado() {
		
		Cidade cidade = new Cidade();
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> cidadeService.salvarCidade(cidade));

		assertEquals("O estado é obrigatório", exception.getMessage());
		
	}
	
	@Test
	public void salvarCidadeJaCadastrado() {
		
		Cidade cidade = new Cidade();
		cidade.setNome("Nome Cidade");
		cidade.setEstado(new Estado("MG","Minas Gerais"));
		
		
		Mockito.when(cidadeRepository.buscarCidadePorNomeESiglaEstado(ArgumentMatchers.anyString(), 
				ArgumentMatchers.anyString())).thenReturn(Optional.of(new Cidade()));
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> cidadeService.salvarCidade(cidade));

		assertEquals("Já existe uma cidade com este nome para este estado", exception.getMessage());
		
	}
	
	@Test
	public void salvarCidadeSucesso() throws ValidacaoException {
		
		Cidade cidade = new Cidade();
		cidade.setNome("Nome Cidade");
		cidade.setEstado(new Estado("MG","Minas Gerais"));
		
		
		Mockito.when(cidadeRepository.buscarCidadePorNomeESiglaEstado(ArgumentMatchers.anyString(), 
				ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(null));
		Mockito.doNothing().when(estadoService).salvar(ArgumentMatchers.any(Estado.class));
		Mockito.when(cidadeRepository.save(ArgumentMatchers.any(Cidade.class))).thenReturn(new Cidade());
		

		assertDoesNotThrow(() -> cidadeService.salvarCidade(cidade));
		
	}
	
	@Test
	public void buscarCidadePorEstadoSemEstado() {
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> cidadeService.buscarCidadePorEstado(null));

		assertEquals("É obrigatório informar a sigla do estado para buscar as cidade de um Estado", exception.getMessage());

	}
	
	@Test
	public void buscarCidadePorEstadoSucesso() {
		
		
		Mockito.when(cidadeRepository.buscarCidadesPorSiglaEstado(ArgumentMatchers.anyString())).thenReturn(new ArrayList<Cidade>());
		
		assertDoesNotThrow(() -> cidadeService.buscarCidadePorEstado("MG"));

		

	}

}
