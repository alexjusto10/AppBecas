package escom.ipn.mx.appbecas;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Transporte extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transporteprecios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // APARECE BOTÓN DE REGRESAR EN EL TITULO DE LA ACTIVITY

        //******************************************************************************************
        TextView txt_vehicles1 = findViewById(R.id.txt_vehicle1);
        TextView txt_vehicles2 = findViewById(R.id.txt_vehicles2);
        TextView txt_vehicles3 = findViewById(R.id.txt_vehicles3);
        TextView txt_vehicles4 = findViewById(R.id.txt_vehicles4);
        TextView txt_vehicles5 = findViewById(R.id.txt_vehicles5);
        TextView txt_vehicles6 = findViewById(R.id.txt_vehicles6);
        TextView txt_vehicles7 = findViewById(R.id.txt_vehicles7);
        TextView txt_vehicles8 = findViewById(R.id.txt_vehicles8);

        final EditText edit_address1 = findViewById(R.id.edit_address1);
        final EditText edit_address2 = findViewById(R.id.edit_address2);
        final EditText edit_address3 = findViewById(R.id.edit_address3);
        final EditText edit_address4 = findViewById(R.id.edit_address4);
        final EditText edit_address5 = findViewById(R.id.edit_address5);
        final EditText edit_address6 = findViewById(R.id.edit_address6);
        final EditText edit_address7 = findViewById(R.id.edit_address7);
        final EditText edit_address8 = findViewById(R.id.edit_address8);

        final EditText edit_price1 = findViewById(R.id.edit_price1);
        final EditText edit_price2 = findViewById(R.id.edit_price2);
        final EditText edit_price3 = findViewById(R.id.edit_price3);
        final EditText edit_price4 = findViewById(R.id.edit_price4);
        final EditText edit_price5 = findViewById(R.id.edit_price5);
        final EditText edit_price6 = findViewById(R.id.edit_price6);
        final EditText edit_price7 = findViewById(R.id.edit_price7);
        final EditText edit_price8 = findViewById(R.id.edit_price8);

        final Spinner spinn_type1 = findViewById(R.id.spinn_type1);
        final Spinner spinn_type2 = findViewById(R.id.spinn_type2);
        final Spinner spinn_type3 = findViewById(R.id.spinn_type3);
        final Spinner spinn_type4 = findViewById(R.id.spinn_type4);
        final Spinner spinn_type5 = findViewById(R.id.spinn_type5);
        final Spinner spinn_type6 = findViewById(R.id.spinn_type6);
        final Spinner spinn_type7 = findViewById(R.id.spinn_type7);
        final Spinner spinn_type8 = findViewById(R.id.spinn_type8);

        TextView txt_peso1 = findViewById(R.id.txt_peso1);
        TextView txt_peso2 = findViewById(R.id.txt_peso2);
        TextView txt_peso3 = findViewById(R.id.txt_peso3);
        TextView txt_peso4 = findViewById(R.id.txt_peso4);
        TextView txt_peso5 = findViewById(R.id.txt_peso5);
        TextView txt_peso6 = findViewById(R.id.txt_peso6);
        TextView txt_peso7 = findViewById(R.id.txt_peso7);

        Button btn_send = findViewById(R.id.btn_send);
        //******************************************************************************************

        final Intent intent=getIntent();
        int posicion = intent.getIntExtra("posicion",0);
        Log.i("INTENT_MAIN","Posicion: "+posicion);
        posicion = posicion+1;

        switch (posicion){
            case 1:
                txt_vehicles1.setVisibility(View.GONE);
                txt_vehicles2.setVisibility(View.GONE);
                txt_vehicles3.setVisibility(View.GONE);
                txt_vehicles4.setVisibility(View.GONE);
                txt_vehicles5.setVisibility(View.GONE);
                txt_vehicles6.setVisibility(View.GONE);
                txt_vehicles7.setVisibility(View.GONE);

                edit_address1.setVisibility(View.GONE);
                edit_address2.setVisibility(View.GONE);
                edit_address3.setVisibility(View.GONE);
                edit_address4.setVisibility(View.GONE);
                edit_address5.setVisibility(View.GONE);
                edit_address6.setVisibility(View.GONE);
                edit_address7.setVisibility(View.GONE);
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                edit_price1.setVisibility(View.GONE);
                edit_price2.setVisibility(View.GONE);
                edit_price3.setVisibility(View.GONE);
                edit_price4.setVisibility(View.GONE);
                edit_price5.setVisibility(View.GONE);
                edit_price6.setVisibility(View.GONE);
                edit_price7.setVisibility(View.GONE);

                spinn_type1.setVisibility(View.GONE);
                spinn_type2.setVisibility(View.GONE);
                spinn_type3.setVisibility(View.GONE);
                spinn_type4.setVisibility(View.GONE);
                spinn_type5.setVisibility(View.GONE);
                spinn_type6.setVisibility(View.GONE);
                spinn_type7.setVisibility(View.GONE);

                txt_peso1.setVisibility(View.GONE);
                txt_peso2.setVisibility(View.GONE);
                txt_peso3.setVisibility(View.GONE);
                txt_peso4.setVisibility(View.GONE);
                txt_peso5.setVisibility(View.GONE);
                txt_peso6.setVisibility(View.GONE);
                txt_peso7.setVisibility(View.GONE);

                txt_vehicles8.setText(getString(R.string.vehicles4));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_price8.getText().toString())){
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        }else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",1);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;

            case 2:
                txt_vehicles1.setVisibility(View.GONE);
                txt_vehicles2.setVisibility(View.GONE);
                txt_vehicles3.setVisibility(View.GONE);
                txt_vehicles4.setVisibility(View.GONE);
                txt_vehicles5.setVisibility(View.GONE);
                txt_vehicles6.setVisibility(View.GONE);

                edit_address1.setVisibility(View.GONE);
                edit_address2.setVisibility(View.GONE);
                edit_address3.setVisibility(View.GONE);
                edit_address4.setVisibility(View.GONE);
                edit_address5.setVisibility(View.GONE);
                edit_address6.setVisibility(View.GONE);
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                edit_price1.setVisibility(View.GONE);
                edit_price2.setVisibility(View.GONE);
                edit_price3.setVisibility(View.GONE);
                edit_price4.setVisibility(View.GONE);
                edit_price5.setVisibility(View.GONE);
                edit_price6.setVisibility(View.GONE);

                spinn_type1.setVisibility(View.GONE);
                spinn_type2.setVisibility(View.GONE);
                spinn_type3.setVisibility(View.GONE);
                spinn_type4.setVisibility(View.GONE);
                spinn_type5.setVisibility(View.GONE);
                spinn_type6.setVisibility(View.GONE);

                txt_peso1.setVisibility(View.GONE);
                txt_peso2.setVisibility(View.GONE);
                txt_peso3.setVisibility(View.GONE);
                txt_peso4.setVisibility(View.GONE);
                txt_peso5.setVisibility(View.GONE);
                txt_peso6.setVisibility(View.GONE);

                txt_vehicles7.setText(getString(R.string.vehicles1));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_address7.getText().toString())
                                ||TextUtils.isEmpty(edit_price7.getText().toString())
                                ||TextUtils.isEmpty(edit_price8.getText().toString()))
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            String str_spinn_type7 = spinn_type7.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",2);
                            intent1.putExtra("edit"+Integer.toString(1),edit_address7.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(1),geoLocalizar(edit_address7).latitude);
                            intent1.putExtra("lng"+Integer.toString(1),geoLocalizar(edit_address7).longitude);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price7.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type7);
                            intent1.putExtra("precio" + Integer.toString(2), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(2), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;

            case 3:
                txt_vehicles1.setVisibility(View.GONE);
                txt_vehicles2.setVisibility(View.GONE);
                txt_vehicles3.setVisibility(View.GONE);
                txt_vehicles4.setVisibility(View.GONE);
                txt_vehicles5.setVisibility(View.GONE);

                edit_address1.setVisibility(View.GONE);
                edit_address2.setVisibility(View.GONE);
                edit_address3.setVisibility(View.GONE);
                edit_address4.setVisibility(View.GONE);
                edit_address5.setVisibility(View.GONE);
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                edit_price1.setVisibility(View.GONE);
                edit_price2.setVisibility(View.GONE);
                edit_price3.setVisibility(View.GONE);
                edit_price4.setVisibility(View.GONE);
                edit_price5.setVisibility(View.GONE);

                spinn_type1.setVisibility(View.GONE);
                spinn_type2.setVisibility(View.GONE);
                spinn_type3.setVisibility(View.GONE);
                spinn_type4.setVisibility(View.GONE);
                spinn_type5.setVisibility(View.GONE);

                txt_peso1.setVisibility(View.GONE);
                txt_peso2.setVisibility(View.GONE);
                txt_peso3.setVisibility(View.GONE);
                txt_peso4.setVisibility(View.GONE);
                txt_peso5.setVisibility(View.GONE);

                txt_vehicles6.setText(getString(R.string.vehicles1));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_address6.getText().toString())
                                ||TextUtils.isEmpty(edit_price6.getText().toString())
                                ||TextUtils.isEmpty(edit_address7.getText().toString())
                                ||TextUtils.isEmpty(edit_price7.getText().toString())
                                ||TextUtils.isEmpty(edit_price8.getText().toString()))
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            String str_spinn_type7 = spinn_type7.getSelectedItem().toString();
                            String str_spinn_type6 = spinn_type6.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",3);
                            intent1.putExtra("edit"+Integer.toString(1),edit_address6.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(1),geoLocalizar(edit_address6).latitude);
                            intent1.putExtra("lng"+Integer.toString(1),geoLocalizar(edit_address6).longitude);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price6.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type6);
                            intent1.putExtra("edit"+Integer.toString(2),edit_address7.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(2),geoLocalizar(edit_address7).latitude);
                            intent1.putExtra("lng"+Integer.toString(2),geoLocalizar(edit_address7).longitude);
                            intent1.putExtra("precio" + Integer.toString(2), edit_price7.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(2), str_spinn_type7);
                            intent1.putExtra("precio" + Integer.toString(3), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(3), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;

            case 4:
                txt_vehicles1.setVisibility(View.GONE);
                txt_vehicles2.setVisibility(View.GONE);
                txt_vehicles3.setVisibility(View.GONE);
                txt_vehicles4.setVisibility(View.GONE);

                edit_address1.setVisibility(View.GONE);
                edit_address2.setVisibility(View.GONE);
                edit_address3.setVisibility(View.GONE);
                edit_address4.setVisibility(View.GONE);
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                edit_price1.setVisibility(View.GONE);
                edit_price2.setVisibility(View.GONE);
                edit_price3.setVisibility(View.GONE);
                edit_price4.setVisibility(View.GONE);

                spinn_type1.setVisibility(View.GONE);
                spinn_type2.setVisibility(View.GONE);
                spinn_type3.setVisibility(View.GONE);
                spinn_type4.setVisibility(View.GONE);

                txt_peso1.setVisibility(View.GONE);
                txt_peso2.setVisibility(View.GONE);
                txt_peso3.setVisibility(View.GONE);
                txt_peso4.setVisibility(View.GONE);

                txt_vehicles5.setText(getString(R.string.vehicles1));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_address5.getText().toString())
                                ||TextUtils.isEmpty(edit_price5.getText().toString())
                                ||TextUtils.isEmpty(edit_address6.getText().toString())
                                ||TextUtils.isEmpty(edit_price6.getText().toString())
                                ||TextUtils.isEmpty(edit_address7.getText().toString())
                                ||TextUtils.isEmpty(edit_price7.getText().toString())
                                ||TextUtils.isEmpty(edit_price8.getText().toString()))
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            String str_spinn_type7 = spinn_type7.getSelectedItem().toString();
                            String str_spinn_type6 = spinn_type6.getSelectedItem().toString();
                            String str_spinn_type5 = spinn_type5.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",4);
                            intent1.putExtra("edit"+Integer.toString(1),edit_address5.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(1),geoLocalizar(edit_address5).latitude);
                            intent1.putExtra("lng"+Integer.toString(1),geoLocalizar(edit_address5).longitude);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price5.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type5);
                            intent1.putExtra("edit"+Integer.toString(2),edit_address6.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(2),geoLocalizar(edit_address6).latitude);
                            intent1.putExtra("lng"+Integer.toString(2),geoLocalizar(edit_address6).longitude);
                            intent1.putExtra("precio" + Integer.toString(2), edit_price6.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(2), str_spinn_type6);
                            intent1.putExtra("edit"+Integer.toString(3),edit_address7.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(3),geoLocalizar(edit_address7).latitude);
                            intent1.putExtra("lng"+Integer.toString(3),geoLocalizar(edit_address7).longitude);
                            intent1.putExtra("precio" + Integer.toString(3), edit_price7.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(3), str_spinn_type7);
                            intent1.putExtra("precio" + Integer.toString(4), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(4), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;

            case 5:
                txt_vehicles1.setVisibility(View.GONE);
                txt_vehicles2.setVisibility(View.GONE);
                txt_vehicles3.setVisibility(View.GONE);

                edit_address1.setVisibility(View.GONE);
                edit_address2.setVisibility(View.GONE);
                edit_address3.setVisibility(View.GONE);
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                edit_price1.setVisibility(View.GONE);
                edit_price2.setVisibility(View.GONE);
                edit_price3.setVisibility(View.GONE);

                spinn_type1.setVisibility(View.GONE);
                spinn_type2.setVisibility(View.GONE);
                spinn_type3.setVisibility(View.GONE);

                txt_peso1.setVisibility(View.GONE);
                txt_peso2.setVisibility(View.GONE);
                txt_peso3.setVisibility(View.GONE);

                txt_vehicles4.setText(getString(R.string.vehicles1));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_address4.getText().toString())
                                ||TextUtils.isEmpty(edit_price4.getText().toString())
                                ||TextUtils.isEmpty(edit_address5.getText().toString())
                                ||TextUtils.isEmpty(edit_price5.getText().toString())
                                ||TextUtils.isEmpty(edit_address6.getText().toString())
                                ||TextUtils.isEmpty(edit_price6.getText().toString())
                                ||TextUtils.isEmpty(edit_address7.getText().toString())
                                ||TextUtils.isEmpty(edit_price7.getText().toString())
                                ||TextUtils.isEmpty(edit_price8.getText().toString()))
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            String str_spinn_type7 = spinn_type7.getSelectedItem().toString();
                            String str_spinn_type6 = spinn_type6.getSelectedItem().toString();
                            String str_spinn_type5 = spinn_type5.getSelectedItem().toString();
                            String str_spinn_type4 = spinn_type4.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",5);
                            intent1.putExtra("edit"+Integer.toString(1),edit_address4.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(1),geoLocalizar(edit_address4).latitude);
                            intent1.putExtra("lng"+Integer.toString(1),geoLocalizar(edit_address4).longitude);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price4.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type4);
                            intent1.putExtra("edit"+Integer.toString(2),edit_address5.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(2),geoLocalizar(edit_address5).latitude);
                            intent1.putExtra("lng"+Integer.toString(2),geoLocalizar(edit_address5).longitude);
                            intent1.putExtra("precio" + Integer.toString(2), edit_price5.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(2), str_spinn_type5);
                            intent1.putExtra("edit"+Integer.toString(3),edit_address6.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(3),geoLocalizar(edit_address6).latitude);
                            intent1.putExtra("lng"+Integer.toString(3),geoLocalizar(edit_address6).longitude);
                            intent1.putExtra("precio" + Integer.toString(3), edit_price6.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(3), str_spinn_type6);
                            intent1.putExtra("edit"+Integer.toString(4),edit_address7.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(4),geoLocalizar(edit_address7).latitude);
                            intent1.putExtra("lng"+Integer.toString(4),geoLocalizar(edit_address7).longitude);
                            intent1.putExtra("precio" + Integer.toString(4), edit_price7.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(4), str_spinn_type7);
                            intent1.putExtra("precio" + Integer.toString(5), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(5), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;

            case 6:
                txt_vehicles1.setVisibility(View.GONE);
                txt_vehicles2.setVisibility(View.GONE);

                edit_address1.setVisibility(View.GONE);
                edit_address2.setVisibility(View.GONE);
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                edit_price1.setVisibility(View.GONE);
                edit_price2.setVisibility(View.GONE);

                spinn_type1.setVisibility(View.GONE);
                spinn_type2.setVisibility(View.GONE);

                txt_peso1.setVisibility(View.GONE);
                txt_peso2.setVisibility(View.GONE);

                txt_vehicles3.setText(getString(R.string.vehicles1));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_address3.getText().toString())
                                ||TextUtils.isEmpty(edit_price3.getText().toString())
                                ||TextUtils.isEmpty(edit_address4.getText().toString())
                                ||TextUtils.isEmpty(edit_price4.getText().toString())
                                ||TextUtils.isEmpty(edit_address5.getText().toString())
                                ||TextUtils.isEmpty(edit_price5.getText().toString())
                                ||TextUtils.isEmpty(edit_address6.getText().toString())
                                ||TextUtils.isEmpty(edit_price6.getText().toString())
                                ||TextUtils.isEmpty(edit_address7.getText().toString())
                                ||TextUtils.isEmpty(edit_price7.getText().toString())
                                ||TextUtils.isEmpty(edit_price8.getText().toString()))
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            String str_spinn_type7 = spinn_type7.getSelectedItem().toString();
                            String str_spinn_type6 = spinn_type6.getSelectedItem().toString();
                            String str_spinn_type5 = spinn_type5.getSelectedItem().toString();
                            String str_spinn_type4 = spinn_type4.getSelectedItem().toString();
                            String str_spinn_type3 = spinn_type3.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",6);
                            intent1.putExtra("edit"+Integer.toString(1),edit_address3.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(1),geoLocalizar(edit_address3).latitude);
                            intent1.putExtra("lng"+Integer.toString(1),geoLocalizar(edit_address3).longitude);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price3.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type3);
                            intent1.putExtra("edit"+Integer.toString(2),edit_address4.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(2),geoLocalizar(edit_address4).latitude);
                            intent1.putExtra("lng"+Integer.toString(2),geoLocalizar(edit_address4).longitude);
                            intent1.putExtra("precio" + Integer.toString(2), edit_price4.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(2), str_spinn_type4);
                            intent1.putExtra("edit"+Integer.toString(3),edit_address5.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(3),geoLocalizar(edit_address5).latitude);
                            intent1.putExtra("lng"+Integer.toString(3),geoLocalizar(edit_address5).longitude);
                            intent1.putExtra("precio" + Integer.toString(3), edit_price5.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(3), str_spinn_type5);
                            intent1.putExtra("edit"+Integer.toString(4),edit_address6.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(4),geoLocalizar(edit_address6).latitude);
                            intent1.putExtra("lng"+Integer.toString(4),geoLocalizar(edit_address6).longitude);
                            intent1.putExtra("precio" + Integer.toString(4), edit_price6.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(4), str_spinn_type6);
                            intent1.putExtra("edit"+Integer.toString(5),edit_address7.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(5),geoLocalizar(edit_address7).latitude);
                            intent1.putExtra("lng"+Integer.toString(5),geoLocalizar(edit_address7).longitude);
                            intent1.putExtra("precio" + Integer.toString(5), edit_price7.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(5), str_spinn_type7);
                            intent1.putExtra("precio" + Integer.toString(6), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(6), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;

            case 7:
                txt_vehicles1.setVisibility(View.GONE);

                edit_address1.setVisibility(View.GONE);
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                edit_price1.setVisibility(View.GONE);

                spinn_type1.setVisibility(View.GONE);

                txt_peso1.setVisibility(View.GONE);

                txt_vehicles2.setText(getString(R.string.vehicles1));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_address2.getText().toString())
                                ||TextUtils.isEmpty(edit_price2.getText().toString())
                                ||TextUtils.isEmpty(edit_address3.getText().toString())
                                ||TextUtils.isEmpty(edit_price3.getText().toString())
                                ||TextUtils.isEmpty(edit_address4.getText().toString())
                                ||TextUtils.isEmpty(edit_price4.getText().toString())
                                ||TextUtils.isEmpty(edit_address5.getText().toString())
                                ||TextUtils.isEmpty(edit_price5.getText().toString())
                                ||TextUtils.isEmpty(edit_address6.getText().toString())
                                ||TextUtils.isEmpty(edit_price6.getText().toString())
                                ||TextUtils.isEmpty(edit_address7.getText().toString())
                                ||TextUtils.isEmpty(edit_price7.getText().toString())
                                ||TextUtils.isEmpty(edit_price8.getText().toString()))
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            String str_spinn_type7 = spinn_type7.getSelectedItem().toString();
                            String str_spinn_type6 = spinn_type6.getSelectedItem().toString();
                            String str_spinn_type5 = spinn_type5.getSelectedItem().toString();
                            String str_spinn_type4 = spinn_type4.getSelectedItem().toString();
                            String str_spinn_type3 = spinn_type3.getSelectedItem().toString();
                            String str_spinn_type2 = spinn_type2.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",7);
                            intent1.putExtra("edit"+Integer.toString(1),edit_address2.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(1),geoLocalizar(edit_address2).latitude);
                            intent1.putExtra("lng"+Integer.toString(1),geoLocalizar(edit_address2).longitude);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price2.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type2);
                            intent1.putExtra("edit"+Integer.toString(2),edit_address3.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(2),geoLocalizar(edit_address3).latitude);
                            intent1.putExtra("lng"+Integer.toString(2),geoLocalizar(edit_address3).longitude);
                            intent1.putExtra("precio" + Integer.toString(2), edit_price3.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(2), str_spinn_type3);
                            intent1.putExtra("edit"+Integer.toString(3),edit_address4.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(3),geoLocalizar(edit_address4).latitude);
                            intent1.putExtra("lng"+Integer.toString(3),geoLocalizar(edit_address4).longitude);
                            intent1.putExtra("precio" + Integer.toString(3), edit_price4.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(3), str_spinn_type4);
                            intent1.putExtra("edit"+Integer.toString(4),edit_address5.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(4),geoLocalizar(edit_address5).latitude);
                            intent1.putExtra("lng"+Integer.toString(4),geoLocalizar(edit_address5).longitude);
                            intent1.putExtra("precio" + Integer.toString(4), edit_price5.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(4), str_spinn_type5);
                            intent1.putExtra("edit"+Integer.toString(5),edit_address6.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(5),geoLocalizar(edit_address6).latitude);
                            intent1.putExtra("lng"+Integer.toString(5),geoLocalizar(edit_address6).longitude);
                            intent1.putExtra("precio" + Integer.toString(5), edit_price6.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(5), str_spinn_type6);
                            intent1.putExtra("edit"+Integer.toString(6),edit_address7.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(6),geoLocalizar(edit_address7).latitude);
                            intent1.putExtra("lng"+Integer.toString(6),geoLocalizar(edit_address7).longitude);
                            intent1.putExtra("precio" + Integer.toString(6), edit_price7.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(6), str_spinn_type7);
                            intent1.putExtra("precio" + Integer.toString(7), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(7), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;

            case 8:
                edit_address8.setFocusable(false);
                edit_address8.setHint(getString(R.string.hint_change));

                txt_vehicles1.setText(getString(R.string.vehicles1));

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edit_address1.getText().toString())
                                ||TextUtils.isEmpty(edit_price1.getText().toString())
                                ||TextUtils.isEmpty(edit_address2.getText().toString())
                                ||TextUtils.isEmpty(edit_price2.getText().toString())
                                ||TextUtils.isEmpty(edit_address3.getText().toString())
                                ||TextUtils.isEmpty(edit_price3.getText().toString())
                                ||TextUtils.isEmpty(edit_address4.getText().toString())
                                ||TextUtils.isEmpty(edit_price4.getText().toString())
                                ||TextUtils.isEmpty(edit_address5.getText().toString())
                                ||TextUtils.isEmpty(edit_price5.getText().toString())
                                ||TextUtils.isEmpty(edit_address6.getText().toString())
                                ||TextUtils.isEmpty(edit_price6.getText().toString())
                                ||TextUtils.isEmpty(edit_address7.getText().toString())
                                ||TextUtils.isEmpty(edit_price7.getText().toString())
                                ||TextUtils.isEmpty(edit_price8.getText().toString()))
                            Toast.makeText(getApplicationContext(),"Algún campo está vacío.",Toast.LENGTH_LONG).show();
                        else {
                            String str_spinn_type8 = spinn_type8.getSelectedItem().toString();
                            String str_spinn_type7 = spinn_type7.getSelectedItem().toString();
                            String str_spinn_type6 = spinn_type6.getSelectedItem().toString();
                            String str_spinn_type5 = spinn_type5.getSelectedItem().toString();
                            String str_spinn_type4 = spinn_type4.getSelectedItem().toString();
                            String str_spinn_type3 = spinn_type3.getSelectedItem().toString();
                            String str_spinn_type2 = spinn_type2.getSelectedItem().toString();
                            String str_spinn_type1 = spinn_type1.getSelectedItem().toString();
                            Intent intent1 = new Intent(getApplicationContext(), Mapa.class);
                            intent1.putExtra("posicion",8);
                            intent1.putExtra("edit"+Integer.toString(1),edit_address1.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(1),geoLocalizar(edit_address1).latitude);
                            intent1.putExtra("lng"+Integer.toString(1),geoLocalizar(edit_address1).longitude);
                            intent1.putExtra("precio" + Integer.toString(1), edit_price1.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(1), str_spinn_type1);
                            intent1.putExtra("edit"+Integer.toString(2),edit_address2.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(2),geoLocalizar(edit_address2).latitude);
                            intent1.putExtra("lng"+Integer.toString(2),geoLocalizar(edit_address2).longitude);
                            intent1.putExtra("precio" + Integer.toString(2), edit_price2.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(2), str_spinn_type2);
                            intent1.putExtra("edit"+Integer.toString(3),edit_address3.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(3),geoLocalizar(edit_address3).latitude);
                            intent1.putExtra("lng"+Integer.toString(3),geoLocalizar(edit_address3).longitude);
                            intent1.putExtra("precio" + Integer.toString(3), edit_price3.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(3), str_spinn_type3);
                            intent1.putExtra("edit"+Integer.toString(4),edit_address4.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(4),geoLocalizar(edit_address4).latitude);
                            intent1.putExtra("lng"+Integer.toString(4),geoLocalizar(edit_address4).longitude);
                            intent1.putExtra("precio" + Integer.toString(4), edit_price4.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(4), str_spinn_type4);
                            intent1.putExtra("edit"+Integer.toString(5),edit_address5.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(5),geoLocalizar(edit_address5).latitude);
                            intent1.putExtra("lng"+Integer.toString(5),geoLocalizar(edit_address5).longitude);
                            intent1.putExtra("precio" + Integer.toString(5), edit_price5.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(5), str_spinn_type5);
                            intent1.putExtra("edit"+Integer.toString(6),edit_address6.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(6),geoLocalizar(edit_address6).latitude);
                            intent1.putExtra("lng"+Integer.toString(6),geoLocalizar(edit_address6).longitude);
                            intent1.putExtra("precio" + Integer.toString(6), edit_price6.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(6), str_spinn_type6);
                            intent1.putExtra("edit"+Integer.toString(7),edit_address7.getText().toString());
                            intent1.putExtra("lat"+Integer.toString(7),geoLocalizar(edit_address7).latitude);
                            intent1.putExtra("lng"+Integer.toString(7),geoLocalizar(edit_address7).longitude);
                            intent1.putExtra("precio" + Integer.toString(7), edit_price7.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(7), str_spinn_type7);
                            intent1.putExtra("precio" + Integer.toString(8), edit_price8.getText().toString());
                            intent1.putExtra("tipo" + Integer.toString(8), str_spinn_type8);
                            startActivity(intent1);
                        }
                    }
                });

                break;
        }
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

    private LatLng geoLocalizar(EditText edit_address){
        String buscador = edit_address.getText().toString();
        Geocoder geocodificador = new Geocoder(getApplicationContext());
        List<Address> lista = new ArrayList<>();

        try {
            lista = geocodificador.getFromLocationName(buscador,1);
        }catch (IOException e){
            Log.e("ERROR1","Geolocalizar: IOException"+e.getMessage());
        }

        Address direccion = lista.get(0);
        Log.d("DIRECCION","Dirección: "+direccion.toString());

        LatLng latlngDireccion = new LatLng(direccion.getLatitude(),direccion.getLongitude());

        return latlngDireccion;
    }
}
