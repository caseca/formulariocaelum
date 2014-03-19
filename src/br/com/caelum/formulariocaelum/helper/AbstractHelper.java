package br.com.caelum.formulariocaelum.helper;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.utils.converter.AlunoConverter;

import com.google.inject.Inject;

public abstract class AbstractHelper {

	public final int CHAVE_FOTO = 9514;
	
	@Inject
	private Activity activity;
	
	private String localArquivoFoto;
	
	@Inject
	protected AlunoConverter converter;
	
	@InjectView(R.idLayoutFormulario.foto)
	private ImageButton fotoImageButton;
	
	public String getLocalArquivoFoto() {
		return localArquivoFoto;
	}

	public void setLocalArquivoFoto(String localArquivoFoto) {
		this.localArquivoFoto = localArquivoFoto;
	}
	
	public ImageButton getFotoImageButton() {
		return this.fotoImageButton;
	}
	
	public void carregaFoto() {
		this.fotoImageButton.setImageBitmap(getBitmap());
	}
	
	public void carregaFoto(String localFoto) {
		if (localFoto != null && !"".equals(localFoto)) {
			setLocalArquivoFoto(localFoto);
		} else {
			setLocalArquivoFoto(null);
		}
		carregaFoto();
	}

	public Bitmap getBitmap() {
		Bitmap scaledFoto = null;
		if (getLocalArquivoFoto() != null) {
			Bitmap fotoAluno = BitmapFactory.decodeFile(getLocalArquivoFoto());
			scaledFoto = Bitmap.createScaledBitmap(fotoAluno, 100, 100, true);
		} else {
			Bitmap fotoAluno = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
			scaledFoto = Bitmap.createScaledBitmap(fotoAluno, 100, 100, true);
		}
		
		return scaledFoto;
	}
}
