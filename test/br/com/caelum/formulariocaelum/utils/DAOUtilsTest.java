package br.com.caelum.formulariocaelum.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.caelum.formulariocaelum.vos.AlunoVO;

public class DAOUtilsTest {

	@Test
	public void createCreateTableTest() {
		StringBuffer createTable = new StringBuffer();
		createTable.append("CREATE TABLE Aluno (")
		.append("id INTEGER PRIMARY KEY, ")
		.append("nome TEXT UNIQUE NOT NULL, ")
		.append("telefone TEXT, ")
		.append("endereco TEXT, ")
		.append("site TEXT, ")
		.append("nota REAL, ")
		.append("foto TEXT ")
		.append("); ");
		
		
		DAOUtils utils = new DAOUtils();
		assertEquals("Create table errado!", createTable.toString(), utils.createCreateTable(AlunoVO.class));
	}

}
