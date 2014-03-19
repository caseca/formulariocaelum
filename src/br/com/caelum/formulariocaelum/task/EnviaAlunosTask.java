package br.com.caelum.formulariocaelum.task;

import java.util.List;

import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.business.AlunoBusiness;
import br.com.caelum.formulariocaelum.utils.converter.AlunoConverter;
import br.com.caelum.formulariocaelum.vos.AlunoVO;
import br.com.caelum.formulariocaelum.web.WebClient;

import com.google.inject.Inject;

import roboguice.inject.InjectResource;
import roboguice.util.RoboAsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class EnviaAlunosTask extends RoboAsyncTask<String> {

	@Inject
	private AlunoBusiness alunoBusiness;
	
	@Inject
	private WebClient webClient;
	
	private Context context;
	
	@InjectResource(R.string.msgEnviandoAlunos)
	private String msgEnviandoAlunos;
	
	@InjectResource(R.string.msgEnvioPelaWeb)
	private String msgEnvioPelaWeb;
	
	private ProgressDialog progress;
	
	@Inject
	private AlunoConverter alunoConverter;
	
	@Inject
	protected EnviaAlunosTask(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public String call() throws Exception {
		List<AlunoVO> alunos = this.alunoBusiness.getList();
		String alunosJson = this.alunoConverter.toJSON(alunos);
		
		return this.webClient.post(alunosJson);
	}

	@Override
	protected void onSuccess(String t) throws Exception {
		super.onSuccess(t);
		stopProgressDialog();
		Toast.makeText(this.context, R.string.msgAlunosEnviadosComSucesso, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onException(Exception e) throws RuntimeException {
		super.onException(e);
		stopProgressDialog();
		Toast.makeText(this.context, R.string.msgErroNoEnvioDeAlunos, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPreExecute() throws Exception {
		super.onPreExecute();
		this.progress = ProgressDialog.show(this.context, this.msgEnviandoAlunos, this.msgEnvioPelaWeb, true, true);
	}
	
	protected void stopProgressDialog() {
		this.progress.dismiss();
	}
}
