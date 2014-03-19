package br.com.caelum.formulariocaelum.menus;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.activitys.FormularioActivity;
import br.com.caelum.formulariocaelum.activitys.ListaAlunosActivity;

import com.google.inject.Inject;

public class ApplicationMenu extends AbstractMenu {

	public static final int NOVOID = 1;
	public static final int MAPAID = 2;
	public static final int SINCRONIZARID = 3;
	public static final int BAIXARPROVASID = 4;
	public static final int PREFERENCIASID = 5;
	
	@Inject
	public ApplicationMenu(Context context) {
		super(context);
	}
	
	public void performMenuActions(MenuItem item) {
		switch (item.getItemId()) {
		case R.idMenuListaAlunos.menu_novo:
			novoMenuAction();
			break;

		case NOVOID:
			novoMenuAction();
			break;
		
		case MAPAID:
			mapaMenuAction();
			break;
			
		case R.idMenuListaAlunos.menu_enviar_alunos:
			sincronizarMenuAction();
			break;
		
		case SINCRONIZARID:
			sincronizarMenuAction();
			break;
			
		case BAIXARPROVASID:
			baixarProvasMenuAction();
			break;
			
		case PREFERENCIASID:
			preferenciasMenuAction();
			break;
			
		default:
			((ListaAlunosActivity) context).onOptionsItemSelected(item);
			break;
		}
	}

	private void preferenciasMenuAction() {
		Toast.makeText(this.context, "Preferências selecionado", Toast.LENGTH_SHORT).show();
	}

	private void baixarProvasMenuAction() {
		Toast.makeText(this.context, "Receber provas selecionado", Toast.LENGTH_SHORT).show();
	}

	private void sincronizarMenuAction() {
		enviarAluno();
	}

	private void mapaMenuAction() {
		Toast.makeText(this.context, "Mapa selecionado", Toast.LENGTH_SHORT).show();
	}

	private void novoMenuAction() {
		Intent intent = new Intent(this.context, FormularioActivity.class);
		this.context.startActivity(intent);
	}
	
	public Menu createDefaultApplicationMenu(Menu menu) {
		menu.add(0, NOVOID, 0, R.string.novo);
		menu.add(0, MAPAID, 1, R.string.mapa);
		menu.add(0, SINCRONIZARID, 2, R.string.sincronizar);
		menu.add(0, BAIXARPROVASID, 3, R.string.baixarProvas);
		menu.add(0, PREFERENCIASID, 4, R.string.preferencias);
		
		return menu;
	}
	
}
