package org.edu.cadastrocliente.repository;

import java.util.List;

import org.edu.cadastrocliente.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Modifying
	@Query("update Cliente set nome = :nome where codigo = :codigo")
	int alterarNomeCliente(@Param("codigo")Long codigo,@Param("nome") String nome);

	List<Cliente> findByNome(String nomeCliente);

}
