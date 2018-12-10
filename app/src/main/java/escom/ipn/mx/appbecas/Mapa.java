package escom.ipn.mx.appbecas;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import escom.ipn.mx.appbecas.directionhelpers.FetchURL;
import escom.ipn.mx.appbecas.directionhelpers.TaskLoadedCallback;

public class Mapa extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap mMap;
    private MarkerOptions lugarOrigen, escom, destino1, destino2, destino3, destino4, destino5, destino6, destino7, destino8;
    private Polyline currentPolyline;
    private Button btn_route, btn_main, btn_pdf;
    private TextView txt_price, txt_type;
    private Spinner spinnDireccion;
    private static final int LOCATION_REQUEST = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)//Verifica si se tienen los permisos
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,//Se solicitan los permisos
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST);
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,mListener);
        Criteria criteria = new Criteria();
        String bestProvider = lm.getBestProvider(criteria, false);
        Location location = lm.getLastKnownLocation(bestProvider);//Obtiene las coordenadas actuales
        final double longitud = location.getLongitude();
        final double latitud = location.getLatitude();

        btn_route = findViewById(R.id.btn_route);
        btn_main = findViewById(R.id.btn_main);
        txt_price = findViewById(R.id.txt_price);
        txt_type = findViewById(R.id.txt_type);

        Intent intent = getIntent();
        int posicion = intent.getIntExtra("posicion", 1);
        Double lat1 = intent.getDoubleExtra("lat1", 0);
        Double lng1 = intent.getDoubleExtra("lng1", 0);
        final String precio1 = intent.getStringExtra("precio1");
        final String tipo1 = intent.getStringExtra("tipo1");
        final String edit1 = intent.getStringExtra("edit1");
        Double lat2 = intent.getDoubleExtra("lat2", 0);
        Double lng2 = intent.getDoubleExtra("lng2", 0);
        final String precio2 = intent.getStringExtra("precio2");
        final String tipo2 = intent.getStringExtra("tipo2");
        final String edit2 = intent.getStringExtra("edit2");
        Double lat3 = intent.getDoubleExtra("lat3", 0);
        Double lng3 = intent.getDoubleExtra("lng3", 0);
        final String precio3 = intent.getStringExtra("precio3");
        final String tipo3 = intent.getStringExtra("tipo3");
        final String edit3 = intent.getStringExtra("edit3");
        Double lat4 = intent.getDoubleExtra("lat4", 0);
        Double lng4 = intent.getDoubleExtra("lng4", 0);
        final String precio4 = intent.getStringExtra("precio4");
        final String tipo4 = intent.getStringExtra("tipo4");
        final String edit4 = intent.getStringExtra("edit4");
        Double lat5 = intent.getDoubleExtra("lat5", 0);
        Double lng5 = intent.getDoubleExtra("lng5", 0);
        final String precio5 = intent.getStringExtra("precio5");
        final String tipo5 = intent.getStringExtra("tipo5");
        final String edit5 = intent.getStringExtra("edit5");
        Double lat6 = intent.getDoubleExtra("lat6", 0);
        Double lng6 = intent.getDoubleExtra("lng6", 0);
        final String precio6 = intent.getStringExtra("precio6");
        final String tipo6 = intent.getStringExtra("tipo6");
        final String edit6 = intent.getStringExtra("edit6");
        Double lat7 = intent.getDoubleExtra("lat7", 0);
        Double lng7 = intent.getDoubleExtra("lng7", 0);
        final String precio7 = intent.getStringExtra("precio7");
        final String tipo7 = intent.getStringExtra("tipo7");
        final String edit7 = intent.getStringExtra("edit7");
        final String precio8 = intent.getStringExtra("precio8");
        final String tipo8 = intent.getStringExtra("tipo8");


        lugarOrigen = new MarkerOptions().position(new LatLng(latitud, longitud)).title("Casa");//Se define el lugar del marcador
        escom = new MarkerOptions().position(new LatLng(19.5046539, -99.1468518)).title("ESCOM");

        MapFragment fragmentoMapa = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentoMapa);
        fragmentoMapa.getMapAsync(this);

        ArrayList<String> lista = new ArrayList<String>();
        ArrayAdapter<String> adapter;
        spinnDireccion = findViewById(R.id.spinn_route);


        switch (posicion) {
            case 1:
                lista.add("Casa - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);

                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        trazarRuta(lugarOrigen, escom);
                        txt_price.setText(getString(R.string.price) + precio1);
                        txt_type.setText(getString(R.string.type) + " " + tipo1);
                    }
                });
                break;

            case 2:
                lista.add("Casa - " + edit1);
                lista.add(edit1 + " - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);
                destino1 = new MarkerOptions().position(new LatLng(lat1, lng1)).title(edit1);//Se define el lugar del marcador

                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnDireccion.getSelectedItem().toString().equals("Casa - " + edit1)) {
                            trazarRuta(lugarOrigen, destino1);
                            txt_price.setText(getString(R.string.price) + precio1);
                            txt_type.setText(getString(R.string.type) + " " + tipo1);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit1 + " - ESCOM")) {
                            trazarRuta(destino1, escom);
                            txt_price.setText(getString(R.string.price) + precio2);
                            txt_type.setText(getString(R.string.type) + " " + tipo2);
                        }
                    }
                });
                break;

            case 3:
                lista.add("Casa - " + edit1);
                lista.add(edit1 + " - " + edit2);
                lista.add(edit2 + " - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);
                destino1 = new MarkerOptions().position(new LatLng(lat1, lng1)).title(edit1);//Se define el lugar del marcador
                destino2 = new MarkerOptions().position(new LatLng(lat2, lng2)).title(edit2);//Se define el lugar del marcador

                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnDireccion.getSelectedItem().toString().equals("Casa - " + edit1)) {
                            trazarRuta(lugarOrigen, destino1);
                            txt_price.setText(getString(R.string.price) + precio1);
                            txt_type.setText(getString(R.string.type) + " " + tipo1);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit1 + " - " + edit2)) {
                            trazarRuta(destino1, destino2);
                            txt_price.setText(getString(R.string.price) + precio2);
                            txt_type.setText(getString(R.string.type) + " " + tipo2);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit2 + " - ESCOM")) {
                            trazarRuta(destino2, escom);
                            txt_price.setText(getString(R.string.price) + precio3);
                            txt_type.setText(getString(R.string.type) + " " + tipo3);
                        }
                    }
                });
                break;

            case 4:
                lista.add("Casa - " + edit1);
                lista.add(edit1 + " - " + edit2);
                lista.add(edit2 + " - " + edit3);
                lista.add(edit3 + " - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);
                destino1 = new MarkerOptions().position(new LatLng(lat1, lng1)).title(edit1);//Se define el lugar del marcador
                destino2 = new MarkerOptions().position(new LatLng(lat2, lng2)).title(edit2);//Se define el lugar del marcador
                destino3 = new MarkerOptions().position(new LatLng(lat3, lng3)).title(edit3);//Se define el lugar del marcador

                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnDireccion.getSelectedItem().toString().equals("Casa - " + edit1)) {
                            trazarRuta(lugarOrigen, destino1);
                            txt_price.setText(getString(R.string.price) + precio1);
                            txt_type.setText(getString(R.string.type) + " " + tipo1);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit1 + " - " + edit2)) {
                            trazarRuta(destino1, destino2);
                            txt_price.setText(getString(R.string.price) + precio2);
                            txt_type.setText(getString(R.string.type) + " " + tipo2);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit2 + " - " + edit3)) {
                            trazarRuta(destino2, destino3);
                            txt_price.setText(getString(R.string.price) + precio3);
                            txt_type.setText(getString(R.string.type) + " " + tipo3);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit3 + " - ESCOM")) {
                            trazarRuta(destino3, escom);
                            txt_price.setText(getString(R.string.price) + precio4);
                            txt_type.setText(getString(R.string.type) + " " + tipo4);
                        }
                    }
                });
                break;

            case 5:
                lista.add("Casa - " + edit1);
                lista.add(edit1 + " - " + edit2);
                lista.add(edit2 + " - " + edit3);
                lista.add(edit3 + " - " + edit4);
                lista.add(edit4 + " - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);
                destino1 = new MarkerOptions().position(new LatLng(lat1, lng1)).title(edit1);//Se define el lugar del marcador
                destino2 = new MarkerOptions().position(new LatLng(lat2, lng2)).title(edit2);//Se define el lugar del marcador
                destino3 = new MarkerOptions().position(new LatLng(lat3, lng3)).title(edit3);//Se define el lugar del marcador
                destino4 = new MarkerOptions().position(new LatLng(lat4, lng4)).title(edit4);//Se define el lugar del marcador

                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnDireccion.getSelectedItem().toString().equals("Casa - " + edit1)) {
                            trazarRuta(lugarOrigen, destino1);
                            txt_price.setText(getString(R.string.price) + precio1);
                            txt_type.setText(getString(R.string.type) + " " + tipo1);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit1 + " - " + edit2)) {
                            trazarRuta(destino1, destino2);
                            txt_price.setText(getString(R.string.price) + precio2);
                            txt_type.setText(getString(R.string.type) + " " + tipo2);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit2 + " - " + edit3)) {
                            trazarRuta(destino2, destino3);
                            txt_price.setText(getString(R.string.price) + precio3);
                            txt_type.setText(getString(R.string.type) + " " + tipo3);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit3 + " - " + edit4)) {
                            trazarRuta(destino3, destino4);
                            txt_price.setText(getString(R.string.price) + precio4);
                            txt_type.setText(getString(R.string.type) + " " + tipo4);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit4 + " - ESCOM")) {
                            trazarRuta(destino4, escom);
                            txt_price.setText(getString(R.string.price) + precio5);
                            txt_type.setText(getString(R.string.type) + " " + tipo5);
                        }
                    }
                });
                break;

            case 6:
                lista.add("Casa - " + edit1);
                lista.add(edit1 + " - " + edit2);
                lista.add(edit2 + " - " + edit3);
                lista.add(edit3 + " - " + edit4);
                lista.add(edit4 + " - " + edit5);
                lista.add(edit5 + " - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);
                destino1 = new MarkerOptions().position(new LatLng(lat1, lng1)).title(edit1);//Se define el lugar del marcador
                destino2 = new MarkerOptions().position(new LatLng(lat2, lng2)).title(edit2);//Se define el lugar del marcador
                destino3 = new MarkerOptions().position(new LatLng(lat3, lng3)).title(edit3);//Se define el lugar del marcador
                destino4 = new MarkerOptions().position(new LatLng(lat4, lng4)).title(edit4);//Se define el lugar del marcador
                destino5 = new MarkerOptions().position(new LatLng(lat5, lng5)).title(edit5);//Se define el lugar del marcador


                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnDireccion.getSelectedItem().toString().equals("Casa - " + edit1)) {
                            trazarRuta(lugarOrigen, destino1);
                            txt_price.setText(getString(R.string.price) + precio1);
                            txt_type.setText(getString(R.string.type) + " " + tipo1);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit1 + " - " + edit2)) {
                            trazarRuta(destino1, destino2);
                            txt_price.setText(getString(R.string.price) + precio2);
                            txt_type.setText(getString(R.string.type) + " " + tipo2);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit2 + " - " + edit3)) {
                            trazarRuta(destino2, destino3);
                            txt_price.setText(getString(R.string.price) + precio3);
                            txt_type.setText(getString(R.string.type) + " " + tipo3);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit3 + " - " + edit4)) {
                            trazarRuta(destino3, destino4);
                            txt_price.setText(getString(R.string.price) + precio4);
                            txt_type.setText(getString(R.string.type) + " " + tipo4);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit4 + " - " + edit5)) {
                            trazarRuta(destino4, destino5);
                            txt_price.setText(getString(R.string.price) + precio5);
                            txt_type.setText(getString(R.string.type) + " " + tipo5);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit5 + " - ESCOM")) {
                            trazarRuta(destino5, escom);
                            txt_price.setText(getString(R.string.price) + precio6);
                            txt_type.setText(getString(R.string.type) + " " + tipo6);
                        }
                    }
                });
                break;

            case 7:
                lista.add("Casa - " + edit1);
                lista.add(edit1 + " - " + edit2);
                lista.add(edit2 + " - " + edit3);
                lista.add(edit3 + " - " + edit4);
                lista.add(edit4 + " - " + edit5);
                lista.add(edit5 + " - " + edit6);
                lista.add(edit6 + " - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);
                destino1 = new MarkerOptions().position(new LatLng(lat1, lng1)).title(edit1);//Se define el lugar del marcador
                destino2 = new MarkerOptions().position(new LatLng(lat2, lng2)).title(edit2);//Se define el lugar del marcador
                destino3 = new MarkerOptions().position(new LatLng(lat3, lng3)).title(edit3);//Se define el lugar del marcador
                destino4 = new MarkerOptions().position(new LatLng(lat4, lng4)).title(edit4);//Se define el lugar del marcador
                destino5 = new MarkerOptions().position(new LatLng(lat5, lng5)).title(edit5);//Se define el lugar del marcador
                destino6 = new MarkerOptions().position(new LatLng(lat6, lng6)).title(edit6);//Se define el lugar del marcador

                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnDireccion.getSelectedItem().toString().equals("Casa - " + edit1)) {
                            trazarRuta(lugarOrigen, destino1);
                            txt_price.setText(getString(R.string.price) + precio1);
                            txt_type.setText(getString(R.string.type) + " " + tipo1);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit1 + " - " + edit2)) {
                            trazarRuta(destino1, destino2);
                            txt_price.setText(getString(R.string.price) + precio2);
                            txt_type.setText(getString(R.string.type) + " " + tipo2);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit2 + " - " + edit3)) {
                            trazarRuta(destino2, destino3);
                            txt_price.setText(getString(R.string.price) + precio3);
                            txt_type.setText(getString(R.string.type) + " " + tipo3);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit3 + " - " + edit4)) {
                            trazarRuta(destino3, destino4);
                            txt_price.setText(getString(R.string.price) + precio4);
                            txt_type.setText(getString(R.string.type) + " " + tipo4);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit4 + " - " + edit5)) {
                            trazarRuta(destino4, destino5);
                            txt_price.setText(getString(R.string.price) + precio5);
                            txt_type.setText(getString(R.string.type) + " " + tipo5);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit5 + " - " + edit6)) {
                            trazarRuta(destino5, destino6);
                            txt_price.setText(getString(R.string.price) + precio6);
                            txt_type.setText(getString(R.string.type) + " " + tipo6);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit6 + " - ESCOM")) {
                            trazarRuta(destino6, escom);
                            txt_price.setText(getString(R.string.price) + precio7);
                            txt_type.setText(getString(R.string.type) + " " + tipo7);
                        }
                    }
                });
                break;

            case 8:
                lista.add("Casa - " + edit1);
                lista.add(edit1 + " - " + edit2);
                lista.add(edit2 + " - " + edit3);
                lista.add(edit3 + " - " + edit4);
                lista.add(edit4 + " - " + edit5);
                lista.add(edit5 + " - " + edit6);
                lista.add(edit6 + " - " + edit7);
                lista.add(edit7 + " - ESCOM");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnDireccion.setAdapter(adapter);
                destino1 = new MarkerOptions().position(new LatLng(lat1, lng1)).title(edit1);//Se define el lugar del marcador
                destino2 = new MarkerOptions().position(new LatLng(lat2, lng2)).title(edit2);//Se define el lugar del marcador
                destino3 = new MarkerOptions().position(new LatLng(lat3, lng3)).title(edit3);//Se define el lugar del marcador
                destino4 = new MarkerOptions().position(new LatLng(lat4, lng4)).title(edit4);//Se define el lugar del marcador
                destino5 = new MarkerOptions().position(new LatLng(lat5, lng5)).title(edit5);//Se define el lugar del marcador
                destino6 = new MarkerOptions().position(new LatLng(lat6, lng6)).title(edit6);//Se define el lugar del marcador
                destino7 = new MarkerOptions().position(new LatLng(lat7, lng7)).title(edit7);//Se define el lugar del marcador

                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (spinnDireccion.getSelectedItem().toString().equals("Casa - " + edit1)) {
                            trazarRuta(lugarOrigen, destino1);
                            txt_price.setText(getString(R.string.price) + precio1);
                            txt_type.setText(getString(R.string.type) + " " + tipo1);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit1 + " - " + edit2)) {
                            trazarRuta(destino1, destino2);
                            txt_price.setText(getString(R.string.price) + precio2);
                            txt_type.setText(getString(R.string.type) + " " + tipo2);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit2 + " - " + edit3)) {
                            trazarRuta(destino2, destino3);
                            txt_price.setText(getString(R.string.price) + precio3);
                            txt_type.setText(getString(R.string.type) + " " + tipo3);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit3 + " - " + edit4)) {
                            trazarRuta(destino3, destino4);
                            txt_price.setText(getString(R.string.price) + precio4);
                            txt_type.setText(getString(R.string.type) + " " + tipo4);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit4 + " - " + edit5)) {
                            trazarRuta(destino4, destino5);
                            txt_price.setText(getString(R.string.price) + precio5);
                            txt_type.setText(getString(R.string.type) + " " + tipo5);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit5 + " - " + edit6)) {
                            trazarRuta(destino5, destino6);
                            txt_price.setText(getString(R.string.price) + precio6);
                            txt_type.setText(getString(R.string.type) + " " + tipo6);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit6 + " - " + edit7)) {
                            trazarRuta(destino6, destino7);
                            txt_price.setText(getString(R.string.price) + precio7);
                            txt_type.setText(getString(R.string.type) + " " + tipo7);
                        } else if (spinnDireccion.getSelectedItem().toString().equals(edit7 + " - ESCOM")) {
                            trazarRuta(destino7, escom);
                            txt_price.setText(getString(R.string.price) + precio8);
                            txt_type.setText(getString(R.string.type) + " " + tipo8);
                        }
                    }
                });
                break;
        }

        btn_main.setOnClickListener(new View.OnClickListener() {//Regresa a la pantalla principal
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        /*btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PDF.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//Cuando el mapa ha cargado
        mMap = googleMap;

        lugarOrigen.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));//Pone el marcador azul
        escom.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mMap.addMarker(lugarOrigen);//Pone el marcador en la casa
        mMap.addMarker(escom);//Pone el marcador en ESCOM

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(puntoMedio(lugarOrigen.getPosition(), escom.getPosition())
                , 13), 3000, null);//Hace zoom en las coordenadas dadas.

    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {//Manda la petición de ruta
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {//Crea la ruta
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    public LatLng puntoMedio(LatLng origen, LatLng destino) {//Obtiene la distancia media entre los puntos para poder enfocar la cámara entre los dos

        double lat = (origen.latitude + destino.latitude) / 2;

        double lng = (origen.longitude + destino.longitude) / 2;

        LatLng resultado = new LatLng(lat, lng);

        return resultado;
    }

    public void trazarRuta(MarkerOptions lugarPrimero, MarkerOptions lugarDestino) {
        new FetchURL(Mapa.this).execute(getUrl(lugarPrimero.getPosition(), lugarDestino.getPosition(), "driving"), "driving");//Une los datos y los manda en un Json
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(puntoMedio(lugarPrimero.getPosition(), lugarDestino.getPosition()), 13), 1000, null);//Enfoca la cámara  13-distancia  1000-velocidad
        lugarDestino.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(lugarDestino);//Pone el marcador en el destino
    }

    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Previously mock location is cleared.
            // getLastKnownLocation(LocationManager.GPS_PROVIDER); will not return mock location.
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    // LÍNEA 67 DE ESTE ARCHIVO
    // ME SALIÓ ESE ERROR PARA USAR EL MAPA Y AL PARECER AQUÍ ESTÁ LA SOLUCIÓN
    // CHECAR https://stackoverflow.com/questions/32290045/error-invoke-virtual-method-double-android-location-location-getlatitude-on
}