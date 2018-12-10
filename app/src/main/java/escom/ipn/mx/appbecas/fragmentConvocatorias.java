package escom.ipn.mx.appbecas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class fragmentConvocatorias extends Fragment {
    private OnFragmentInteractionListener mListener;
    TextView txtBeca;
    String becas = "\n",boleta="";
    Button btnConv;


    public fragmentConvocatorias() {
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

        View view = inflater.inflate(R.layout.fragment_convocatorias, container, false);

        btnConv = view.findViewById(R.id.btnConv);
        txtBeca = view.findViewById(R.id.txtBeca);

        if (getArguments() != null) {
            boleta = getArguments().getString("boleta");
        }

        BaseDeDatos helper = new BaseDeDatos(getActivity());
        final SQLiteDatabase db = helper.getWritableDatabase();

        String SQLA = "SELECT * FROM Alumno WHERE boleta = '" + boleta + "'";
        Cursor ca = db.rawQuery(SQLA, null);

        String SQLB = "SELECT * FROM Beca";
        Cursor cb = db.rawQuery(SQLB, null);

        if (cb != null) {
            ca.moveToFirst();
            cb.moveToFirst();
            if(ca.getInt(ca.getColumnIndex("adeudos"))==0){
                do {
                    if(ca.getDouble(ca.getColumnIndex("promedio"))>=cb.getDouble(cb.getColumnIndex("minPromedio"))) {
                        String name = cb.getString(cb.getColumnIndex("nombre"));
                        becas += "- " + name+"\n\n";
                    }
                } while (cb.moveToNext());
            }else if(ca.getInt(ca.getColumnIndex("adeudos"))==1) {
                cb.moveToNext();
                cb.moveToNext();
                cb.moveToNext();
                String name = cb.getString(cb.getColumnIndex("nombre"));
                becas += "- " + name+"\n\n";
            }
        }
        cb.close();
        ca.close();
        db.close();
        txtBeca.setText(becas);

        btnConv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Convocatorias.class);
                i.putExtra("boleta",boleta);
                startActivity(i);
            }
        });

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
