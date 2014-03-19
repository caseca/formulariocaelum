package br.com.caelum.formulariocaelum.utils.converter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.caelum.formulariocaelum.vos.AlunoVO;

import com.google.inject.Inject;

public abstract class AbstractConverter {

	@Inject
	protected JSONStringer jsonStringer;
	
	public AbstractConverter() {
		//Guice constructor
	}
	
	public abstract String toJSON(List<AlunoVO> alunos) throws JSONException;
}
