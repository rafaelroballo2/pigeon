package br.ufpe.cin.mosaic.pigeon.client.android.menu;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import br.eng.mosaic.pigeon.andenginetest.Stage;
import br.ufpe.cin.mosaic.pigeon.business.android.facebook.LoginFacebook;
import br.ufpe.cin.mosaic.pigeon.client.android.R;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.start_player).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						startGame(true);
					}
				});

		findViewById(R.id.start_comp).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startGame(false);
			}
		});

		findViewById(R.id.connect_facebook).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						loginFacebook();
					}
				});
	}

	private void startGame(boolean startWithHuman) {
		Intent i = new Intent(this, Stage.class);
		startActivity(i);
	}
    
	private void loginFacebook() {
        Intent i = new Intent(this, LoginFacebook.class);
        startActivity(i);    	
    }	
}