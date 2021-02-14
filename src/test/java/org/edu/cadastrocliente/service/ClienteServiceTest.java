package org.edu.cadastrocliente.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.edu.cadastrocliente.CadastroclienteApplication;
import org.edu.cadastrocliente.domain.Cidade;
import org.edu.cadastrocliente.domain.Cliente;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.repository.ClienteRepository;
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
class ClienteServiceTest {

	@Autowired
	private ClienteService clienteService;
	@MockBean
	private ClienteRepository clienteRepository;

	@Test
	public void salvarClienteSemNome() {

		Cliente cliente = new Cliente();

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.salvar(cliente));

		assertEquals("O nome é obrigatorio", exception.getMessage());

	}

	@Test
	public void salvarClienteDataNascimento() {

		Cliente cliente = new Cliente();
		cliente.setNome("Nome Cliente");

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.salvar(cliente));

		assertEquals("A data de nascimento é obrigatório", exception.getMessage());

	}

	@Test
	public void salvarClienteSemCidade() {

		Cliente cliente = new Cliente();
		cliente.setNome("Nome Cliente");
		cliente.setDataNascimento(LocalDate.now());

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.salvar(cliente));

		assertEquals("É necessário informar a cidade", exception.getMessage());

	}

	@Test
	public void salvarClienteSucesso() {

		Cliente cliente = new Cliente();
		cliente.setNome("Nome Cliente");
		cliente.setDataNascimento(LocalDate.now());
		cliente.setCidade(new Cidade());

		Mockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(new Cliente());

		assertDoesNotThrow(() -> clienteService.salvar(cliente));

	}
	
	@Test
	public void alterarNomeClienteSemCodigo() {

		Cliente cliente = new Cliente();
		cliente.setNome("Nome Cliente");
		cliente.setDataNascimento(LocalDate.now());
		cliente.setCidade(new Cidade());

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.alterarNomeCliente(cliente));

		assertEquals("É necessário informar o código do cliente para realizar a alterção do nome", exception.getMessage());

	}
	
	@Test
	public void alterarNomeClienteSemNome() {

		Cliente cliente = new Cliente();
		cliente.setCodigo(1L);
		cliente.setNome("");

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.alterarNomeCliente(cliente));

		assertEquals("É necessário informar o nome do cliente para realizar a alterção do nome", exception.getMessage());

	}
	
	@Test
	public void alterarNomeClienteNenhumClienteAlterado() {

		Cliente cliente = new Cliente();
		cliente.setCodigo(1L);
		cliente.setNome("Nome Cliente");

		Mockito.when(clienteRepository.alterarNomeCliente(ArgumentMatchers.anyLong(),ArgumentMatchers.anyString())).thenReturn(0);

		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.alterarNomeCliente(cliente));

		assertEquals("Nenhum cliente foi alterado", exception.getMessage());

	}
	
	@Test
	public void alterarNomeClienteSucesso() {

		Cliente cliente = new Cliente();
		cliente.setCodigo(1L);
		cliente.setNome("Nome Cliente");

		Mockito.when(clienteRepository.alterarNomeCliente(ArgumentMatchers.anyLong(),ArgumentMatchers.anyString())).thenReturn(1);

		assertDoesNotThrow(() -> clienteService.alterarNomeCliente(cliente));

	}
	
	@Test
	public void buscarClientePorCodigoSemCodigo() {
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.buscarPorCodigo(null));

		assertEquals("É necessário informar o código do cliente para realizar a busca", exception.getMessage());

	}
	
	@Test
	public void buscarClientePorCodigoSucesso() {
		
		Mockito.when(clienteRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(new Cliente()));
		
		assertDoesNotThrow( () -> clienteService.buscarPorCodigo(1L));

	}
	
	@Test
	public void excluirClienteSemCodigo() {
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.excluirCliente(null));

		assertEquals("É necessário informar o código do cliente para realizar a exclusão", exception.getMessage());

	}
	
	@Test
	public void excluirClienteInexistente() {
		
		Mockito.when(clienteRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.excluirCliente(1l));

		assertEquals("Não existe nenhum cliente com este código", exception.getMessage());

	}
	
	@Test
	public void excluirClienteSucesso() {
		
		Mockito.when(clienteRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.doNothing().when(clienteRepository).deleteById(ArgumentMatchers.anyLong());
		
		assertDoesNotThrow(() -> clienteService.excluirCliente(1l));

	}
	
	@Test
	public void buscarClientePorNomeoSemNome() {
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> clienteService.buscarPorNome(""));

		assertEquals("Nome do cliente é obrigatório para a consulta", exception.getMessage());

	}
	
	@Test
	public void buscarClientePorNomeSucesso() {
		
		Mockito.when(clienteRepository.findByNome(ArgumentMatchers.anyString())).thenReturn(new ArrayList<Cliente>());
		
		assertDoesNotThrow( () -> clienteService.buscarPorNome("Nome Cliente"));

	}

}
