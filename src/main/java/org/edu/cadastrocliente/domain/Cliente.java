package org.edu.cadastrocliente.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.edu.cadastrocliente.enums.Sexo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@Table(name = "tcliente")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codCli")
	private Long codigo;

	@Column(name = "nomCli")
	@NotBlank(message = "O nome do cliente é obrigatório")
	private String nome;

	@NotNull(message = "A data de nascimento é obrigatório")
	@Column(name = "datNas")
	private LocalDate dataNascimento;

	@Column(name = "idtSex")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@ManyToOne
	@JoinColumn(name = "codCid")
	@NotNull(message = "A cidade é obrigatório")
	private Cidade cidade;
	
	
	

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Transient
	public Integer getIdade() {
		if(this.getDataNascimento() != null) {
			return Period.between(dataNascimento, LocalDate.now()).getYears();
		}
		return null;
	}

}
