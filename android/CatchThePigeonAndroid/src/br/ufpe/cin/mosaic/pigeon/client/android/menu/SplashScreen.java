package br.ufpe.cin.mosaic.pigeon.client.android.menu;

import br.ufpe.cin.mosaic.pigeon.client.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity implements Runnable{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Handler h = new Handler();
		h.postDelayed(this, 5000);
	}
	
	@Override
	public void run() {
		startActivity(new Intent(this,MainActivity.class));
		finish();
		
	}
		

}
