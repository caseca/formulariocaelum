package br.com.caelum.formulariocaelum.menus;

import android.content.Context;
import br.com.caelum.formulariocaelum.task.EnviaAlunosTask;

import com.google.inject.Inject;

public abstract class AbstractMenu {

	protected Context context;
	
	@Inject
	private EnviaAlunosTask enviarAlunos;
	
	protected AbstractMenu(Context context) {
		this.context = context;
	}
	
	public void enviarAluno() {
		this.enviarAlunos.execute();
	}
}
