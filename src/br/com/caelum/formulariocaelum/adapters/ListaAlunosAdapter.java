package br.com.caelum.formulariocaelum.adapters;

import java.util.List;

import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.vos.AlunoVO;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaAlunosAdapter extends BaseAdapter {

	private final Activity activity;
	
	private final List<AlunoVO> alunos;

	public ListaAlunosAdapter(Activity activity, List<AlunoVO> alunos) {
		this.activity = activity;
		this.alunos = alunos;
	}
	
	@Override
	public int getCount() {
		return this.alunos.size();
	}

	@Override
	public AlunoVO getItem(int position) {
		return this.alunos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.alunos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = this.activity.getLayoutInflater().inflate(R.layout.item, null);
		
		if (position % 2 == 0) {
			view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
		} else {
			view.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar));
		}
		
		AlunoVO aluno = getItem(position);
		
		ImageView foto = (ImageView) view.findViewById(R.idLayoutItem.foto);
		TextView nome = (TextView) view.findViewById(R.idLayoutItem.nome);
		
		nome.setText(aluno.getNome());
		
		Bitmap bm; 
		if (aluno.getFoto() != null && !"".equals(aluno.getFoto())) {
			bm = BitmapFactory.decodeFile(aluno.getFoto());
		} else {
			bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
		}
		
		bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
		foto.setImageBitmap(bm);
		
		return view;
	}

}
