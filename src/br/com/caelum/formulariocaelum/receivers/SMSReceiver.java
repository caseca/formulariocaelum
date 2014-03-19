package br.com.caelum.formulariocaelum.receivers;

import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import br.com.caelum.formulariocaelum.R;
import br.com.caelum.formulariocaelum.business.AlunoBusiness;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SMSReceiver extends RoboBroadcastReceiver {

	@Inject
	private Provider<AlunoBusiness> alunoBusinessProvider;
	
	@Override
	public void handleReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Object[] messages = (Object[]) bundle.get("pdus");
		byte[] message = (byte[]) messages[0];
		SmsMessage sms = SmsMessage.createFromPdu(message);
		String numeroSMS = sms.getDisplayOriginatingAddress();
		
		if (alunoBusinessProvider.get().isAluno(numeroSMS)) {
			MediaPlayer mp = MediaPlayer.create(context, R.raw.olhaamensagem);
			
			if (mp.isPlaying()) {
				mp.stop();
			}
			
			mp.start();
			Toast.makeText(context, "Chegou uma mensagem de " + numeroSMS, Toast.LENGTH_LONG).show();
		}
		
	}

}
