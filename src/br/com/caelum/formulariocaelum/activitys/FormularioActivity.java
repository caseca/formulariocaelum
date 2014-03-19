package br.com.caelum.formulariocaelum.activitys;

import java.io.File;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.business.AlunoBusiness;
import br.com.caelum.formulariocaelum.enums.Extras;
import br.com.caelum.formulariocaelum.helper.AlunoHelper;
import br.com.caelum.formulariocaelum.helper.FormularioHelper;
import br.com.caelum.formulariocaelum.utils.converter.AlunoConverter;
import br.com.caelum.formulariocaelum.vos.AlunoVO;

import com.google.inject.Inject;

public class FormularioActivity extends RoboActivity {

	@Inject
	private AlunoBusiness alunoBusiness;
	
	@Inject
	private AlunoHelper alunoHelper;
	
	@InjectView(R.idLayoutFormulario.botao)
	private Button saveButton;
	
	@InjectView(R.idLayoutFormulario.telefoneText)
	private EditText telefone;
	
	@InjectView(R.idLayoutFormulario.emailText)
	private EditText email;
	
	@Inject
	private FormularioHelper helper;
	
	@Inject
	private AlunoConverter converter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		this.saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (email.getText().toString().length() == 0 || converter.verifyEmail(email.getText().toString())) {
					AlunoVO aluno = alunoHelper.criaAluno();
					
					if (aluno.getId() == null) {
						alunoBusiness.addValue(aluno);
						Toast.makeText(FormularioActivity.this, R.string.msgSalvoComSucesso, Toast.LENGTH_SHORT).show();
					} else {
						alunoBusiness.updateValue(aluno);
						Toast.makeText(FormularioActivity.this, R.string.msgAlteradoComSucesso, Toast.LENGTH_SHORT).show();
					}
					
					alunoBusiness.close();
					finish();
				} else {
					Toast.makeText(FormularioActivity.this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		this.helper.getFotoImageButton().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.setLocalArquivoFoto(Environment.getExternalStorageState() + "/" + System.currentTimeMillis() + ".jpg");
				
				Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri localFoto = Uri.fromFile(new File(helper.getLocalArquivoFoto()));
				fotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
				startActivityForResult(fotoIntent, helper.CHAVE_FOTO);
			}
		});
		
		this.telefone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 1) {
					s.insert(0, "(");
				} else if (s.length() == 3) {
					s.insert(3, ") ");
				} else if (s.length() == 9) {
					s.insert(9, "-");
				}
			}
		});
		
		AlunoVO aluno = (AlunoVO) getIntent().getSerializableExtra(Extras.alunoSelecionado.getId());
		if (aluno != null) {
			editarButtonLabel();
			this.alunoHelper.fillFileds(aluno);
		} else {
			inserirButtonLabel();
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == this.helper.CHAVE_FOTO) {
			if (resultCode == Activity.RESULT_OK) {
				helper.carregaFoto();
			} else {
				helper.setLocalArquivoFoto(null);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void editarButtonLabel() {
		this.saveButton.setText(R.string.editar);
	}
	
	private void inserirButtonLabel() {
		this.saveButton.setText(R.string.inserir);
	}

}
