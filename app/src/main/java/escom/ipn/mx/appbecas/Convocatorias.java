package escom.ipn.mx.appbecas;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class Convocatorias extends AppCompatActivity {
    String boleta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convocatorias);

        Intent i = getIntent();
        boleta = i.getStringExtra("boleta");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // APARECE BOTÓN DE REGRESAR EN EL TITULO DE LA ACTIVITY

        fragmentBecas frag = new fragmentBecas();
        Bundle bundle = new Bundle();
        bundle.putString("boleta", boleta);
        frag.setArguments(bundle);
        android.support.v4.app.FragmentManager fr = getSupportFragmentManager();
        fr.beginTransaction().replace(R.id.fragment_becas, frag).commit(); // REEMPLAZA EL CONTENEDOR FRAMELAYOUT POR EL FRAGMENT
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
