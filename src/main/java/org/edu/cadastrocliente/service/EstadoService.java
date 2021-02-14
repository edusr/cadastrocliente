package org.edu.cadastrocliente.service;

import org.edu.cadastrocliente.domain.Estado;
import org.edu.cadastrocliente.exceptions.ValidacaoException;

public interface EstadoService {
	
	void salvar(Estado estado) throws ValidacaoException ;

}
