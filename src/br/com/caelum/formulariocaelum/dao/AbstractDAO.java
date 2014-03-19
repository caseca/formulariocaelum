package br.com.caelum.formulariocaelum.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.caelum.formulariocaelum.utils.DAOUtils;
import br.com.caelum.formulariocaelum.vos.AbstractVO;

public abstract class AbstractDAO<T extends AbstractVO> extends SQLiteOpenHelper {

	private Class<T> clazz;
	
	protected static String TABLENAME; 
	private static final String DBNAME = "FJ57";
	private static final int DBVERSION = 3;
	private static final String DELETEWHERECLAUSE = "id = ?";
	private static final String UPDATEWHERECLAUSE = "id = ?";
	
	@Inject
	private DAOUtils daoUtils;
	
	public AbstractDAO(Context context) {
		super(context, DBNAME, null, DBVERSION);
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		TABLENAME = this.clazz.getSimpleName().replace("VO", "");
	}
	
	public AbstractDAO(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	public abstract ContentValues generateContentValues(T t);
	
	public abstract List<T> generateListVO(Cursor cursor);
	
	public void insert(T aluno) {
		ContentValues values = generateContentValues(aluno);
		getWritableDatabase().insert(TABLENAME, null, values);
	}
	
	public List<T> getList() {
		ArrayList<Field> fields = this.daoUtils.getFieldsList(clazz);
		String[] fieldsNames = getFieldsNamesList(fields);
		Cursor cursor = getWritableDatabase().query(TABLENAME, fieldsNames, null, null, null, null, null);
		List<T> resultado = generateListVO(cursor);
		cursor.close();
		return resultado;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable = this.daoUtils.createCreateTable(this.clazz);
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String dropTable = this.daoUtils.createDropTable(this.clazz);
		db.execSQL(dropTable);
		onCreate(db);
	}
	
	public void delete(T value) {
		String[] clauseValues = getIdentifier(value);
		getWritableDatabase().delete(TABLENAME, DELETEWHERECLAUSE, clauseValues);
	}

	private String[] getIdentifier(T value) {
		String[] clauseValues = {value.getId().toString()};
		return clauseValues;
	}
	
	public int convertBooleanToInteger(boolean b) {
		if (b) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public boolean convertIntegerToBoolean(int i) throws ClassCastException {
		if (i == 1) {
			return true;
		} else if (i == 0) {
			return false;
		} else {
			throw new ClassCastException("Só pode converter 0 ou 1");
		}
	}
	
	private String[] getFieldsNamesList(ArrayList<Field> fields) {
		String[] fieldsNames = new String[fields.size()];
		int posicao = 0;
		for (Field field : fields) {
			fieldsNames[posicao] = field.getName();
			posicao++;
		}
		
		return fieldsNames;
	}
	
	public void update(T value) {
		ContentValues values = generateContentValues(value);
		String[] clauseValues = getIdentifier(value);
		getWritableDatabase().update(TABLENAME, values, UPDATEWHERECLAUSE, clauseValues);
	}
	
}
