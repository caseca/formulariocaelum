package br.com.caelum.formulariocaelum.vos;

import br.com.caelum.formulariocaelum.annotations.NotNull;
import br.com.caelum.formulariocaelum.annotations.Unique;

public class AlunoVO extends AbstractVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Unique
	@NotNull
	private String nome;
	
	private String telefone;
	
	private String endereco;
	
	private String site;
	
	private Double nota;
	
	private String foto;
	
	private String email;

	@Override
	public String toString() {
		return getNome();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
