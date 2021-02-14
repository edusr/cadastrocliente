package org.edu.cadastrocliente.service;

import java.util.List;
import java.util.Optional;

import org.edu.cadastrocliente.domain.Cliente;
import org.edu.cadastrocliente.exceptions.ValidacaoException;

public interface ClienteService {

	Cliente salvar(Cliente cliente) throws ValidacaoException;

	Cliente alterarNomeCliente(Cliente cliente) throws ValidacaoException;

	Optional<Cliente> buscarPorCodigo(Long codigoCliente) throws ValidacaoException;

	void excluirCliente(Long codigoCliente) throws ValidacaoException;

	List<Cliente> buscarPorNome(String nomeCliente) throws ValidacaoException;

}
