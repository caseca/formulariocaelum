package br.com.caelum.formulariocaelum.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import br.com.caelum.formulariocaelum.vos.AlunoVO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class AlunoDAO extends AbstractDAO<AlunoVO> {

	@Inject
	public AlunoDAO(Provider<Context> contextProvider) {
		super(contextProvider.get());
	}

	@Override
	public ContentValues generateContentValues(AlunoVO aluno) {
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("email", aluno.getEmail());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		
		return values;
	}

	@Override
	public List<AlunoVO> generateListVO(Cursor cursor) {
		List<AlunoVO> alunos = new ArrayList<AlunoVO>();
		
		while(cursor.moveToNext()) {
			AlunoVO alunoAtual = new AlunoVO();
			alunoAtual.setId(cursor.getInt(cursor.getColumnIndex("id")));
			alunoAtual.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			alunoAtual.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
			alunoAtual.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
			alunoAtual.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
			alunoAtual.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			alunoAtual.setSite(cursor.getString(cursor.getColumnIndex("site")));
			alunoAtual.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
			
			alunos.add(alunoAtual);
		}
		
		return alunos;
	}
	
	public boolean isTelefoneDeAluno(String telefone) {
		String query = createFindByPhone();
		Cursor rawQuery = getReadableDatabase().rawQuery(query, new String[]{telefone, telefone.substring(2), telefone.substring(5)});
		int total = rawQuery.getCount();
		rawQuery.close();
		
		return (total > 0);
	}
	
	public String createFindByPhone() {
		StringBuffer query = new StringBuffer();
		query.append("SELECT telefone FROM " + TABLENAME)
		.append(" WHERE telefone = ? or telefone = ? or telefone = ?");
		
		return query.toString();
	}
	
	public boolean isEmailDeAluno(String email) {
		String query = createFindByPhone();
		Cursor rawQuery = getReadableDatabase().rawQuery(query, new String[]{email});
		int total = rawQuery.getCount();
		rawQuery.close();
		
		return (total > 0);
	}
	
	public String createFindByEmail() {
		StringBuffer query = new StringBuffer();
		query.append("SELECT email FROM " + TABLENAME)
		.append(" WHERE email = ?");
		
		return query.toString();
	}

}
