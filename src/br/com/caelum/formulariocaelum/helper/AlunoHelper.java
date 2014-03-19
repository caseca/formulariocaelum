package br.com.caelum.formulariocaelum.helper;

import roboguice.inject.InjectView;
import android.widget.EditText;
import android.widget.RatingBar;
import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.vos.AlunoVO;

public class AlunoHelper extends AbstractHelper {

	@InjectView(R.idLayoutFormulario.nomeText)
	private EditText nome;
	@InjectView(R.idLayoutFormulario.telefoneText)
	private EditText telefone;
	@InjectView(R.idLayoutFormulario.enderecoText)
	private EditText endereco;
	@InjectView(R.idLayoutFormulario.emailText)
	private EditText email;
	@InjectView(R.idLayoutFormulario.siteText)
	private EditText site;
	@InjectView(R.idLayoutFormulario.nota)
	private RatingBar nota;
	
	private AlunoVO alunoExistente;
	
	public AlunoHelper() {
		// Guice contructor
	}
	
	public AlunoVO criaAluno() {
		AlunoVO aluno;
		if (this.alunoExistente != null) {
			aluno = this.alunoExistente;
		} else {
			aluno = new AlunoVO();
		}
		aluno.setNome(this.nome.getText().toString());
		aluno.setTelefone(this.telefone.getText().toString().replace("(", "").replace(") ", "").replace("-", ""));
		aluno.setEndereco(this.endereco.getText().toString());
		aluno.setEmail(this.email.getText().toString());
		aluno.setSite(this.site.getText().toString());
		aluno.setNota(Double.valueOf(this.nota.getRating()));
		aluno.setFoto(getLocalArquivoFoto());
		
		return aluno;
	}
	
	public void fillFileds(AlunoVO aluno) {
		this.alunoExistente = aluno;
		
		this.nome.setText(aluno.getNome());
		
		this.telefone.setText(this.converter.formataNumeroTelefone(aluno.getTelefone()));
		this.endereco.setText(aluno.getEndereco());
		this.email.setText(aluno.getEmail());
		this.site.setText(aluno.getSite());
		this.nota.setRating(Float.parseFloat(aluno.getNota().toString()));
		carregaFoto(aluno.getFoto());
	}
	
}
