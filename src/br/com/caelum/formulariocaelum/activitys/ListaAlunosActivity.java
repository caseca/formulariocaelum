package br.com.caelum.formulariocaelum.activitys;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.adapters.ListaAlunosAdapter;
import br.com.caelum.formulariocaelum.business.AlunoBusiness;
import br.com.caelum.formulariocaelum.enums.Extras;
import br.com.caelum.formulariocaelum.menus.AlunoContextMenu;
import br.com.caelum.formulariocaelum.menus.ApplicationMenu;
import br.com.caelum.formulariocaelum.modules.FormularioModule;
import br.com.caelum.formulariocaelum.vos.AlunoVO;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class ListaAlunosActivity extends RoboActivity {

	@Inject
	private AlunoBusiness alunoBusiness;
	
	private AlunoVO alunoSelecionado;
	
	@Inject
	private AlunoContextMenu alunoContextMenu;
	
	@Inject
	private ApplicationMenu applicationMenu;
	
	@InjectView(R.idLayoutListaAlunos.lista)
	private ListView listaAlunos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_alunos);
		
		Guice.createInjector(new FormularioModule());
		
		criaListaAlunos();
	}

	private void criaListaAlunos() {
		registerForContextMenu(this.listaAlunos);
		
		populateMainList();
		
		this.listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				alunoSelecionado = (AlunoVO) arg0.getItemAtPosition(arg2);
				return false;
			}
			
		});
		
		this.listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				alunoSelecionado = (AlunoVO) arg0.getItemAtPosition(arg2);
				Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
				intent.putExtra(Extras.alunoSelecionado.getId(), alunoSelecionado);
				startActivity(intent);
			}
		});
	}

	public void populateMainList() {
		List<AlunoVO> alunos = alunoBusiness.getList();
		if (alunos == null) {
			alunos = new ArrayList<AlunoVO>();
		}
		
		ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);
		this.listaAlunos.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_alunos, menu);
		this.applicationMenu.createDefaultApplicationMenu(menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		this.applicationMenu.performMenuActions(item);
		
		return false;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		this.alunoContextMenu.performMenuActions(item, this.alunoSelecionado);
		
		return false;
	}
	
	@Override
	protected void onResume() {
		populateMainList();
		super.onResume();
	}
	
}
