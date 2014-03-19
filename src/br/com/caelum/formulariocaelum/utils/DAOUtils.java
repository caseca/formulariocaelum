package br.com.caelum.formulariocaelum.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.caelum.formulariocaelum.annotations.NotNull;
import br.com.caelum.formulariocaelum.annotations.PrimaryKey;
import br.com.caelum.formulariocaelum.annotations.Unique;
import br.com.caelum.formulariocaelum.enums.Extras;

public class DAOUtils {
	
	private HashMap<String, String> types;
	
	public DAOUtils() {
		this.types = new HashMap<String, String>();
		this.types.put(String.class.getCanonicalName(), "TEXT");
		this.types.put(double.class.getCanonicalName(), "REAL");
		this.types.put(Double.class.getCanonicalName(), "REAL");
		this.types.put(int.class.getCanonicalName(), "INTEGER");
		this.types.put(Integer.class.getCanonicalName(), "INTEGER");
		this.types.put(boolean.class.getCanonicalName(), "INTEGER");
		this.types.put(Boolean.class.getCanonicalName(), "INTEGER");
		this.types.put(long.class.getCanonicalName(), "REAL");
		this.types.put(Long.class.getCanonicalName(), "REAL");
	}
	
	public String createCreateTable(Class<?> vo) {
		String createTable = generateCreateTable(vo);
		
		return createTable;
	}
	
	public String createDropTable(Class<?> vo) {
		return "DROP TABLE " + vo.getSimpleName().replace("VO", "") + ";";
	}

	private String generateCreateTable(Class<?> vo) {
		String createTableBegin = "CREATE TABLE " + vo.getSimpleName().replace("VO", "") + " (";
		String primaryKey = "";
		StringBuffer createTableBody = new StringBuffer();
		
		ArrayList<Field> fieldlist = getFieldsList(vo);
		for (Field campo : fieldlist) {
			boolean isPrimaryKey = false;
			String nomeCampo = campo.getName(); 
			String tipoCampoNoBanco = convertTypes(campo.getType().getCanonicalName());
			String line = nomeCampo + " " + tipoCampoNoBanco;
			
			for (Annotation config : campo.getAnnotations()) {
				if (config.annotationType().toString().replace("interface ", "").equals(PrimaryKey.class.getCanonicalName())) {
					line += " PRIMARY KEY";
					
					if (((PrimaryKey)config).autoincrement()) {
						line += " AUTOINCREMENT";
					}
					
					isPrimaryKey = true;
				} 
				if (config.annotationType().toString().replace("interface ", "").equals(Unique.class.getCanonicalName())) {
					line += " UNIQUE";
				}
				if (config.annotationType().toString().replace("interface ", "").equals(NotNull.class.getCanonicalName())) {
					line += " NOT NULL";
				}
				
			}
			
			line += ", ";
			
			if (isPrimaryKey) {
				primaryKey = line;
				isPrimaryKey = false;
			} else {
				createTableBody.append(line);
			}
		}
		createTableBody.append("#");
		
		return createTableBegin + primaryKey + createTableBody.toString().replace(", #", ");");
	}

	private String convertTypes(String tipoCampo) {
		return this.types.get(tipoCampo);
	}

	public ArrayList<Field> getFieldsList(Class<?> vo) {
		ArrayList<Field> campos = new ArrayList<Field>();
		
		for (int i = 0; i < vo.getDeclaredFields().length; i++) {
			Field field = vo.getDeclaredFields()[i];
			
			if (!field.getName().equals(Extras.serialVersionUID.getId())) {
				campos.add(field);
			}
		}
		
		if (vo.getSuperclass() != null && !vo.getSuperclass().equals(Object.class)) {
			campos.addAll(getFieldsList(vo.getSuperclass()));
		}
		
		return campos;
	}
	
}
