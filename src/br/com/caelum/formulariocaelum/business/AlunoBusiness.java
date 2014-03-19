package br.com.caelum.formulariocaelum.business;

import java.util.List;

import android.content.Context;
import br.com.caelum.formulariocaelum.dao.AlunoDAO;
import br.com.caelum.formulariocaelum.vos.AlunoVO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AlunoBusiness extends AbstractBusiness<AlunoVO> {

	@Inject
	private AlunoDAO dao;
	
	@Inject
	public AlunoBusiness(Provider<Context> contextProvider) {
		// Guice constructor
	}
	
	@Override
	public void addValue(AlunoVO value) {
		dao.insert(value);
	}

	@Override
	public void close() {
		dao.close();
	}

	@Override
	public List<AlunoVO> getList() {
		return dao.getList();
	}

	@Override
	public void removeValue(AlunoVO value) {
		dao.delete(value);
	}

	@Override
	public void updateValue(AlunoVO value) {
		dao.update(value);
	}
	
	public boolean isAluno(String telefone) {
		String telefoneParaPesquisa = telefone.replace("+55", "");
		return dao.isTelefoneDeAluno(telefoneParaPesquisa);
	}

}
