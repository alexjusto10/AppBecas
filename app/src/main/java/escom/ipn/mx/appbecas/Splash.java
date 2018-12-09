package escom.ipn.mx.appbecas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Splash extends Activity {
    private final int DURACION_SPLASH = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // VERIFICANDO SI YA SE HA LOGGEADO Y SI MARCÓ EL CHECKBOX 'RECORDAR' PARA SALTARSE EL LOGIN
                SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                String prefsUser = prefs.getString("usuario", "");

                if (!prefsUser.equals("")) {
                    Intent intent = new Intent(Splash.this, Menu_Lobby.class);
                    intent.putExtra("boleta", prefsUser);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            };
        }, DURACION_SPLASH);
    }
}
