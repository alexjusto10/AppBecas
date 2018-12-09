package escom.ipn.mx.appbecas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Lobby extends Fragment {

    String boleta = "";

    public Lobby() {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.lobby, container, false);

        if (getArguments() != null) {
            boleta = getArguments().getString("boleta");
        }

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

        return view;
    }
}
