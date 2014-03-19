package br.com.caelum.formulariocaelum.vos;

import java.io.Serializable;

import br.com.caelum.formulariocaelum.annotations.PrimaryKey;

public abstract class AbstractVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey()
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
