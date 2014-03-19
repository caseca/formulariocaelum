package br.com.caelum.formulariocaelum.enums;

public enum Extras {

	alunoSelecionado("alunoSelecionado"),
	serialVersionUID("serialVersionUID"),
	mobileURL("http://www.caelum.com.br/mobile");
	
	private String id;
	
	Extras(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
