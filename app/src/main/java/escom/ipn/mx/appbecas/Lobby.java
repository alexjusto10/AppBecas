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

        fragmentDatosPersonales fragment = new fragmentDatosPersonales();

        Intent i = getIntent();
        String boleta = i.getStringExtra("boleta");
        Log.i("TAG2", boleta);

        Bundle bundle = new Bundle();
        bundle.putString("boleta",boleta);
        fragment.setArguments(bundle);

        LobbyProcess();
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
