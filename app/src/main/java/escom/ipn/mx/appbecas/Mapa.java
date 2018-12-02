package escom.ipn.mx.appbecas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

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
    private LocationManager locationManager;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> puntosLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_REQUEST);
        }

        puntosLista = new ArrayList<>();

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        MapFragment fragmentoMapa = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentoMapa);
        fragmentoMapa.getMapAsync(this);

        double longitud = location.getLongitude();
        double latitud = location.getLatitude();

        lugarOrigen = new MarkerOptions().position(new LatLng(latitud,longitud)).title("Casa");
        escom = new MarkerOptions().position(new LatLng(19.5046539,-99.1468518)).title("ESCOM");

        new FetchURL(Mapa.this).execute(getUrl(lugarOrigen.getPosition(), escom.getPosition(), "driving"), "driving");
        //Zoom(mMap,latitud,longitud);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lugarOrigen.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        escom.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mMap.addMarker(lugarOrigen);
        mMap.addMarker(escom);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
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
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}