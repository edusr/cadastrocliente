package org.edu.cadastrocliente.service.impl;

import org.edu.cadastrocliente.domain.Estado;
import org.edu.cadastrocliente.exceptions.ValidacaoException;
import org.edu.cadastrocliente.repository.EstadoRepository;
import org.edu.cadastrocliente.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
class EstadoServiceImpl implements EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public void salvar(Estado estado) throws ValidacaoException {
		if(!StringUtils.hasText(estado.getSigla()) || !StringUtils.hasText(estado.getNome())) {
			throw new ValidacaoException("Os campos Sigla e Nome são obrigatórios");
		}
		estadoRepository.save(estado);
	}

}
