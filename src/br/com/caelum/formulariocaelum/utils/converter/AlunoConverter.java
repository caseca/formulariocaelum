package br.com.caelum.formulariocaelum.utils.converter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import br.com.caelum.formulariocaelum.vos.AlunoVO;

public class AlunoConverter extends AbstractConverter {

	public AlunoConverter() {
		// Guice Constructor
	}

	public String toJSON(List<AlunoVO> alunos) throws JSONException {
		jsonStringer.object().key("list").array().object().key("aluno").array();

		for (AlunoVO aluno : alunos) {
			jsonStringer.object().key("id").value(aluno.getId()).key("nome")
					.value(aluno.getNome()).key("telefone")
					.value(aluno.getTelefone()).key("endereco")
					.value(aluno.getEndereco()).key("email")
					.value(aluno.getEmail()).key("site").value(aluno.getSite())
					.key("nota").value(aluno.getNota()).key("foto")
					.value(aluno.getFoto()).endObject();
		}

		return jsonStringer.endArray().endObject().endArray().endObject()
				.toString();
	}

	public String formataNumeroTelefone(String telefone) {
		StringBuffer telefoneFormatado = new StringBuffer();
		telefoneFormatado.append("(");

		for (int i = 0; i < telefone.length(); i++) {
			telefoneFormatado.append(telefone.charAt(i));

			if (telefoneFormatado.length() == 3) {
				telefoneFormatado.append(") ");
			} else if (telefoneFormatado.length() == 9) {
				telefoneFormatado.append("-");
			}
		}

		return telefoneFormatado.toString();
	}

	public boolean verifyEmail(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;
	}

}
