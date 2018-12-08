package escom.ipn.mx.appbecas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Lobby extends AppCompatActivity implements fragmentConvocatorias.OnFragmentInteractionListener, fragmentDatosPersonales.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);

        Intent i = getIntent();
        String boleta = i.getStringExtra("boleta");

        // FRAGMENT DATOS PERSONALES
        fragmentDatosPersonales fragDP = new fragmentDatosPersonales();
        FragmentManager fr = getFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString("boleta", boleta);
        fragDP.setArguments(bundle);

        fr.beginTransaction().replace(R.id.fragDP, fragDP).commit(); // REEMPLAZA EL CONTENEDOR FRAMELAYOUT POR EL FRAGMENT

        // FRAGMENT CONVOCATORIAS
        fragmentConvocatorias fragC = new fragmentConvocatorias();
        fr.beginTransaction().replace(R.id.fragConvocatorias, fragC).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void LobbyProcess() {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
