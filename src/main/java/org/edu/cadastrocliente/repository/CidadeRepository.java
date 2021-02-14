package org.edu.cadastrocliente.repository;

import java.util.List;
import java.util.Optional;

import org.edu.cadastrocliente.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	
	List<Cidade> findByNome(String nomeCidade);
	
	@Query("From Cidade cid where cid.nome = :nomeCidade and cid.estado.sigla = :siglaEstado ")
	Optional<Cidade> buscarCidadePorNomeESiglaEstado(@Param("nomeCidade") String nomeCidade, @Param("siglaEstado") String siglaEstado);
	
	@Query("From Cidade cid where cid.estado.sigla = :siglaEstado")
	List<Cidade> buscarCidadesPorSiglaEstado(@Param("siglaEstado") String siglaEstado);

}
