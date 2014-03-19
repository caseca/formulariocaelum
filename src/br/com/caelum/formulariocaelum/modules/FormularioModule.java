package br.com.caelum.formulariocaelum.modules;

import br.com.caelum.formulariocaelum.helper.AlunoHelper;
import br.com.caelum.formulariocaelum.helper.FormularioHelper;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class FormularioModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FormularioHelper.class).in(Singleton.class);
		bind(AlunoHelper.class).in(Singleton.class);
		
	}

}
