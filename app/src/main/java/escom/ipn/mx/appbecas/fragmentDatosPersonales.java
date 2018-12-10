package escom.ipn.mx.appbecas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class fragmentDatosPersonales extends Fragment{
    private fragmentConvocatorias.OnFragmentInteractionListener mListener;
    TextView txtData;
    String boleta="",becas="\n";

    public fragmentDatosPersonales() {
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
        View view = inflater.inflate(R.layout.fragment_datos_personales, container, false);

        txtData = view.findViewById(R.id.txtData);

        if (getArguments() != null) {
            boleta = getArguments().getString("boleta");
        }

        //Toast.makeText(getActivity(), "BOLETA: " + boleta, Toast.LENGTH_SHORT).show();

        BaseDeDatos helper = new BaseDeDatos(getActivity());
        final SQLiteDatabase db = helper.getWritableDatabase();

        String SQL = "SELECT * FROM Alumno WHERE boleta = '" + boleta + "'";
        Cursor c = db.rawQuery(SQL, null);
        if (c != null) {
            c.moveToFirst();
            String name = c.getString(c.getColumnIndex("nombre"));
            becas += "- Nombre: " + name;
            String pat = c.getString(c.getColumnIndex("paterno"));
            becas += " "+pat;
            String mat = c.getString(c.getColumnIndex("materno"));
            becas += " "+mat+"\n\n";
            String prom = c.getString(c.getColumnIndex("promedio"));
            becas += "- Promedio: " + prom+"\n\n";
            String adeudos = c.getString(c.getColumnIndex("adeudos"));
            becas += "- Adeudos: " + adeudos+"\n\n";
            String mail = c.getString(c.getColumnIndex("email"));
            becas += "- Correo: " + mail+"\n\n";
        }
        txtData.setText(becas);

        c.close();
        db.close();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
