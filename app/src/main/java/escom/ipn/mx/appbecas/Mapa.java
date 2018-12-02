package escom.ipn.mx.appbecas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
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

public class Mapa extends FragmentActivity implements OnMapReadyCallback,TaskLoadedCallback {
    private GoogleMap mMap;
    private MarkerOptions lugarOrigen, escom;
    private Polyline currentPolyline;
    private Button btn_route,btn_main,btn_pdf;
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
                    Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_REQUEST);
        }

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);//Obtiene las coordenadas actuales
        final double longitud = location.getLongitude();
        final double latitud = location.getLatitude();

        btn_route = findViewById(R.id.btn_route);
        btn_main = findViewById(R.id.btn_main);

        Intent intent = getIntent();
        int posicion = intent.getIntExtra("posicion",1);//Obtiene las coordenadas del activity Transporte

        lugarOrigen = new MarkerOptions().position(new LatLng(latitud,longitud)).title("Casa");//Se define el lugar del marcador
        escom = new MarkerOptions().position(new LatLng(19.5046539,-99.1468518)).title("ESCOM");

        /*switch (posicion){
            case 1:*/
                btn_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FetchURL(Mapa.this).execute(getUrl(lugarOrigen.getPosition()
                                , escom.getPosition(), "driving"), "driving");//Une los datos y los manda en un Json
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(puntoMedio(lugarOrigen.getPosition(),escom.getPosition())
                                ,13),1000,null);//Enfoca la cámara  13-distancia  1000-velocidad
                    }
                });

                MapFragment fragmentoMapa = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentoMapa);
                fragmentoMapa.getMapAsync(this);
/*
            break;

            case 2:

            break;
        }*/

        btn_main.setOnClickListener(new View.OnClickListener() {//Regresa a la pantalla principal
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//Cuando el mapa ha cargado
        mMap = googleMap;

        lugarOrigen.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));//Pone el marcador azul
        escom.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mMap.addMarker(lugarOrigen);//Pone el marcador en la casa
        mMap.addMarker(escom);//Pone el marcador en ESCOM

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(puntoMedio(lugarOrigen.getPosition(),escom.getPosition())
                ,13),5000,null);//Hace zoom en las coordenadas dadas.

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

    public LatLng puntoMedio (LatLng origen,LatLng destino){//Obtiene la distancia media entre los puntos para poder enfocar la cámara entre los dos

        double lat = (origen.latitude + destino.latitude) / 2;

        double lng = (origen.longitude + destino.longitude) /2;

        LatLng resultado = new LatLng(lat,lng);

        return  resultado;
    }
}