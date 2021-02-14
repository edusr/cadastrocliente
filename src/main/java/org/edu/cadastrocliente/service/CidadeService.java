package org.edu.cadastrocliente.service;

import java.util.List;

import org.edu.cadastrocliente.domain.Cidade;
import org.edu.cadastrocliente.exceptions.ValidacaoException;

public interface CidadeService {
	
	Cidade salvarCidade(Cidade cidade) throws ValidacaoException;

	List<Cidade> buscarCidadePorNome(String nomeCidade);

	List<Cidade> buscarCidadePorEstado(String siglaEstado) throws ValidacaoException;

}
