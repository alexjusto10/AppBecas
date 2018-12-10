package escom.ipn.mx.appbecas;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Convocatorias extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convocatorias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // APARECE BOTÓN DE REGRESAR EN EL TITULO DE LA ACTIVITY

        fragmentBecas frag = new fragmentBecas();
        FragmentManager fr = getFragmentManager();
        //fr.beginTransaction().replace(R.id.fragment_becas,frag).commit(); // REEMPLAZA EL CONTENEDOR FRAMELAYOUT POR EL FRAGMENT
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
