package org.edu.cadastrocliente.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.edu.cadastrocliente.domain.Cliente;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.repository.ClienteRepository;
import org.edu.cadastrocliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente salvar(Cliente cliente) throws ValidacaoException {

		podeSalvarCliente(cliente);

		return clienteRepository.save(cliente);
	}

	private void podeSalvarCliente(Cliente cliente) throws ValidacaoException {
		if (!StringUtils.hasText(cliente.getNome())) {
			throw new ValidacaoException("O nome é obrigatorio");
		}
		if (cliente.getDataNascimento() == null) {
			throw new ValidacaoException("A data de nascimento é obrigatório");
		}
		if (cliente.getCidade() == null) {
			throw new ValidacaoException("É necessário informar a cidade");
		}
	}

	@Override
	@Transactional
	public Cliente alterarNomeCliente(Cliente cliente) throws ValidacaoException {

		validarAlteracaoDeNome(cliente);

		int quantidadeClientesAlterados = clienteRepository.alterarNomeCliente(cliente.getCodigo(), cliente.getNome());

		if (quantidadeClientesAlterados == 0) {
			throw new ValidacaoException("Nenhum cliente foi alterado");
		}

		return clienteRepository.getOne(cliente.getCodigo());

	}

	private void validarAlteracaoDeNome(Cliente cliente) throws ValidacaoException {
		if (cliente.getCodigo() == null) {
			throw new ValidacaoException("É necessário informar o código do cliente para realizar a alterção do nome");
		}
		if (!StringUtils.hasText(cliente.getNome())) {
			throw new ValidacaoException("É necessário informar o nome do cliente para realizar a alterção do nome");
		}
	}

	@Override
	public Optional<Cliente> buscarPorCodigo(Long codigoCliente) throws ValidacaoException {

		if (codigoCliente == null) {
			throw new ValidacaoException("É necessário informar o código do cliente para realizar a busca");
		}

		return clienteRepository.findById(codigoCliente);

	}

	@Override
	public void excluirCliente(Long codigoCliente) throws ValidacaoException {
		if (codigoCliente == null) {
			throw new ValidacaoException("É necessário informar o código do cliente para realizar a exclusão");
		}
		if (clienteRepository.existsById(codigoCliente))
			clienteRepository.deleteById(codigoCliente);
		else
			throw new ValidacaoException("Não existe nenhum cliente com este código");

	}

	@Override
	public List<Cliente> buscarPorNome(String nomeCliente) throws ValidacaoException {
		if(!StringUtils.hasText(nomeCliente)) {
			throw new ValidacaoException("Nome do cliente é obrigatório para a consulta");
		}
		return clienteRepository.findByNome(nomeCliente);
	}

}
