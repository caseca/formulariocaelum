package br.com.caelum.formulariocaelum.business;

import java.util.List;

import br.com.caelum.formulariocaelum.vos.AbstractVO;

public abstract class AbstractBusiness<T extends AbstractVO> {

	public abstract void addValue(T value);
	
	public abstract void close();
	
	public abstract List<T> getList();
	
	public abstract void removeValue(T value);
	
	public abstract void updateValue(T value);
	
}
