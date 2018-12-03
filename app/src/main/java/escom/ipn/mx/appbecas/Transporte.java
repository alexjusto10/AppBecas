package escom.ipn.mx.appbecas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class Transporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transporteprecios);

        Intent intent=getIntent();
        int posicion = intent.getIntExtra("posicion",0);

        posicion = posicion+1;

        Toast.makeText(getApplicationContext(),"Posicion "+posicion,Toast.LENGTH_SHORT).show();
    }
}
