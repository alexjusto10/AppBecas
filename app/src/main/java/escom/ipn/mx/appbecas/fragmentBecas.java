package escom.ipn.mx.appbecas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class fragmentBecas extends Fragment {
    private fragmentConvocatorias.OnFragmentInteractionListener mListener;
    TextView txtBecas,lbBecas,txtBecasTitulo,txtBecasError;
    Switch switchBecas;
    Button btnBecas,btnDer;
    String boleta="",becas="",becasTit="",becasError="",SQLA,SQLB;
    Cursor ca,cb;
    Integer intBeca=0;

    public fragmentBecas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_becas, container, false);

        txtBecas = view.findViewById(R.id.txtBecas);
        lbBecas = view.findViewById(R.id.lbBecas);
        switchBecas = view.findViewById(R.id.switchBecas);
        btnBecas = view.findViewById(R.id.btnBecas);
        txtBecasTitulo = view.findViewById(R.id.txtBecasTitulo);
        btnDer = view.findViewById(R.id.btnDer);
        txtBecasError = view.findViewById(R.id.txtBecasError);

        if (getArguments() != null) {
            boleta = getArguments().getString("boleta");
        }

        BaseDeDatos helper = new BaseDeDatos(getActivity());
        final SQLiteDatabase db = helper.getWritableDatabase();

        SQLA = "SELECT * FROM Alumno WHERE boleta = '" + boleta + "'";
        ca = db.rawQuery(SQLA, null);

        SQLB = "SELECT * FROM Beca";
        cb = db.rawQuery(SQLB, null);

        if (cb != null && ca!=null) {
            ca.moveToFirst();
            cb.moveToFirst();
            comprobarDer();
            txtBecasTitulo.setText(becasTit);
            txtBecas.setText(becas);

            btnDer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (switchBecas.isChecked()) {
                        comprobarDerCheck();
                    } else {
                        comprobarDer();
                    }
                    txtBecasTitulo.setText(becasTit);
                    txtBecas.setText(becas);
                    txtBecasError.setText(becasError);
                }
            });
        }

        btnBecas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intBeca!=0) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    i.putExtra("beca",intBeca);
                    startActivity(i);
                }else{
                    Toast.makeText(getActivity(), "No cumples los requisitos para ser candidato", Toast.LENGTH_SHORT).show();
                }
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

    public void comprobarDer(){
        becas="";
        becasTit="";
        becasError="";
        if (ca.getInt(ca.getColumnIndex("adeudos")) == 0) {
            if (ca.getDouble(ca.getColumnIndex("promedio")) >= cb.getDouble(cb.getColumnIndex("minPromedio"))) {
                String name = cb.getString(cb.getColumnIndex("nombre"));
                becasTit += name;
                String descr = cb.getString(cb.getColumnIndex("descr"));
                becas += descr;
                intBeca = cb.getInt(cb.getColumnIndex("idBeca"));

                if(cb.moveToNext()){
                }else {
                    cb.moveToFirst();
                }
            }else {
                if(cb.moveToNext()){
                    comprobarDer();
                }else {
                    cb.moveToFirst();
                    comprobarDer();
                }
            }
        } else if (ca.getInt(ca.getColumnIndex("adeudos")) == 1) {
            cb.moveToNext();
            cb.moveToNext();
            cb.moveToNext();
            String name = cb.getString(cb.getColumnIndex("nombre"));
            becasTit += name;
            String descr = cb.getString(cb.getColumnIndex("descr"));
            becas += descr;
            intBeca = cb.getInt(cb.getColumnIndex("idBeca"));
        }
    }

    public void comprobarDerCheck(){
        becas="";
        becasTit="";
        becasError="";
        String name = cb.getString(cb.getColumnIndex("nombre"));
        becasTit += name;
        String descr = cb.getString(cb.getColumnIndex("descr"));
        becas += descr;

        if (ca.getInt(ca.getColumnIndex("adeudos")) == 1 && cb.getInt(cb.getColumnIndex("idBeca"))!= 4) {
            becasError += "Tienes " + ca.getInt(ca.getColumnIndex("adeudos")) + " materias adeudadas\n\n";
            intBeca = 0;
        }else if (ca.getInt(ca.getColumnIndex("adeudos")) > 1) {
            becasError += "Tienes " + ca.getInt(ca.getColumnIndex("adeudos")) + " materias adeudadas\n\n";
            intBeca=0;
        }
        if (ca.getDouble(ca.getColumnIndex("promedio")) < cb.getDouble(cb.getColumnIndex("minPromedio"))) {
            becasError += "Tu promedio es  " + ca.getDouble(ca.getColumnIndex("promedio")) +
                    ", el mínimo solicitado es " + cb.getDouble(cb.getColumnIndex("minPromedio"));
            intBeca=0;
        }

        if (becasError.equals(""))
            intBeca = cb.getInt(cb.getColumnIndex("idBeca"));

        if(cb.moveToNext()){
        }else {
            cb.moveToFirst();
        }
    }
}
