package escom.ipn.mx.appbecas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class fragmentConvocatorias extends Fragment {
    private OnFragmentInteractionListener mListener;
    TextView txtBecas;

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

        TextView txtBeca = view.findViewById(R.id.txtBeca);

        String becas = "";

        BaseDeDatos helper = new BaseDeDatos(getActivity());
        final SQLiteDatabase db = helper.getWritableDatabase();

        String SQL = "SELECT * FROM Beca";
        Cursor c = db.rawQuery(SQL, null);
        if (c != null) {
            c.moveToFirst();
            do {
                String name = c.getString(c.getColumnIndex("nombre"));
                becas += "\t\t\t\t\t- " + name + "\n";
                System.out.println("BECAS: " + name);
            } while (c.moveToNext());
        }

        txtBeca.setText(becas);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
