package br.com.caelum.formulariocaelum.menus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.activitys.ListaAlunosActivity;
import br.com.caelum.formulariocaelum.business.AlunoBusiness;
import br.com.caelum.formulariocaelum.vos.AlunoVO;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class AlunoContextMenu extends AbstractMenu {
	
	@Inject
	private Injector injector;
	
	@Inject
	public AlunoContextMenu(Context context) {
		super(context);
	}
	
	public void performMenuActions(MenuItem item, AlunoVO alunoSelecionado) {
		switch (item.getItemId()) {
		case R.idContextMenu.acharMapa:
			acharMapaMenuAction(item, alunoSelecionado);
			break;
			
		case R.idContextMenu.deletar:
			deletarMenuAction(alunoSelecionado);
			break;
			
		case R.idContextMenu.enviarEmail:
			enviarEmailAction(item, alunoSelecionado);
			break;
			
		case R.idContextMenu.enviarSMS:
			smsMenuAction(item, alunoSelecionado);
			break;
			
		case R.idContextMenu.ligar:
			ligarMenuAction(item, alunoSelecionado);
			break;
			
		case R.idContextMenu.navegarSite:
			siteMenuAction(item, alunoSelecionado);
			break;
			
		}
	}

	private void enviarEmailAction(MenuItem item, AlunoVO alunoSelecionado) {
		if (alunoSelecionado.getEmail() != null && !"".equals(alunoSelecionado.getEmail())) {
			Intent intentSMS = new Intent(Intent.ACTION_SEND);
			intentSMS.setData(Uri.parse(Intent.EXTRA_EMAIL + alunoSelecionado.getEmail()));
			item.setIntent(intentSMS);
		} else {
			Toast.makeText(this.context, R.string.msgSemEmail, Toast.LENGTH_SHORT).show();
		}
	}

	private void siteMenuAction(MenuItem item, AlunoVO alunoSelecionado) {
		if (alunoSelecionado.getSite() != null && !"".equals(alunoSelecionado.getSite())) {
			Intent intentSite = new Intent(Intent.ACTION_VIEW);
			
			String site = alunoSelecionado.getSite();
			if (!site.startsWith("http://")) {
				site = "http://" + site;
			}
			
			intentSite.setData(Uri.parse(site));
			item.setIntent(intentSite);
		} else {
			Toast.makeText(this.context, R.string.msgSemSite, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void smsMenuAction(MenuItem item, AlunoVO alunoSelecionado) {
		if (alunoSelecionado.getTelefone() != null && !"".equals(alunoSelecionado.getTelefone())) {
			Intent intentSMS = new Intent(Intent.ACTION_VIEW);
			intentSMS.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
			item.setIntent(intentSMS);
		} else {
			Toast.makeText(this.context, R.string.msgSemTelefone, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void ligarMenuAction(MenuItem item, AlunoVO alunoSelecionado) {
		if (alunoSelecionado.getTelefone() != null && !"".equals(alunoSelecionado.getTelefone())) {
			Intent intentLigar = new Intent(Intent.ACTION_CALL);
			intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
			item.setIntent(intentLigar);
		} else {
			Toast.makeText(this.context, R.string.msgSemTelefone, Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void acharMapaMenuAction(MenuItem item, AlunoVO alunoSelecionado) {
		if (alunoSelecionado.getEndereco() != null && !"".equals(alunoSelecionado.getEndereco())) {
			Intent intentMapa = new Intent(Intent.ACTION_VIEW);
			intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + alunoSelecionado.getEndereco()));
			item.setIntent(intentMapa);
		} else {
			Toast.makeText(this.context, R.string.msgSemEndereco, Toast.LENGTH_SHORT).show();
		}
	}

	private void deletarMenuAction(final AlunoVO alunoSelecionado) {
		new AlertDialog.Builder(context)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle(R.string.deletar)
		.setMessage(R.string.desejaDeletar)
		.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlunoBusiness business = injector.getInstance(AlunoBusiness.class);
				business.removeValue(alunoSelecionado);
				business.close();
				Toast.makeText(context, R.string.msgDeletadoComSucesso, Toast.LENGTH_SHORT).show();
				((ListaAlunosActivity) context).populateMainList();
			}
		}).setNegativeButton(R.string.nao, null).show();
	}

}
