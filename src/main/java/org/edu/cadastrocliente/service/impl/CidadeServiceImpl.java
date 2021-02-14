package org.edu.cadastrocliente.service.impl;

import java.util.List;
import java.util.Optional;

import org.edu.cadastrocliente.domain.Cidade;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.repository.CidadeRepository;
import org.edu.cadastrocliente.service.CidadeService;
import org.edu.cadastrocliente.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
class CidadeServiceImpl implements CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoService estadoService;

	@Override
	@Transactional
	public Cidade salvarCidade(Cidade cidade) throws ValidacaoException {

		if (cidade.getEstado() == null) {
			throw new ValidacaoException("O estado é obrigatório");
		}

		Optional<Cidade> cidadeExistente = cidadeRepository.buscarCidadePorNomeESiglaEstado(cidade.getNome(),
				cidade.getEstado().getSigla());

		if (cidadeExistente.isPresent()) {
			throw new ValidacaoException("Já existe uma cidade com este nome para este estado");
		}

		estadoService.salvar(cidade.getEstado());
		return cidadeRepository.save(cidade);
	}

	@Override
	public List<Cidade> buscarCidadePorNome(String nomeCidade) {

		return cidadeRepository.findByNome(nomeCidade);
	}

	public void removerCidade(Long codigoCidade) {
		cidadeRepository.deleteById(codigoCidade);
	}

	@Override
	public List<Cidade> buscarCidadePorEstado(String siglaEstado) throws ValidacaoException {

		if (!StringUtils.hasText(siglaEstado)) {
			throw new ValidacaoException("É obrigatório informar a sigla do estado para buscar as cidade de um Estado");
		}

		return cidadeRepository.buscarCidadesPorSiglaEstado(siglaEstado);
	}

}
