package escom.ipn.mx.appbecas;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Menu_Lobby extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView lbNombre, lbEmail;
    String boleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // *************************************** AGREGANDO HOME
        Intent i = getIntent();
        boleta = i.getStringExtra("boleta");

        Lobby fragment = new Lobby();
        FragmentManager fr = getFragmentManager();
        fr.beginTransaction().add(R.id.Lobby_Layout, fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString("boleta", boleta);
        fragment.setArguments(bundle);
        // ****************************************

        setContentView(R.layout.activity_menu__lobby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*setNameAndEmail(boleta);
        setContentView(R.layout.activity_menu__lobby);*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);           // PARA QUE NO PINTE LOS ICONOS DEL MENU EN GRIS
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu__lobby, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Lobby fragment = new Lobby();
            FragmentManager fr = getFragmentManager();
            fr.beginTransaction().add(R.id.Lobby_Layout, fragment).commit();

            Bundle bundle = new Bundle();
            bundle.putString("boleta", boleta);
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_user) {

        } else if (id == R.id.nav_contest) {
            Intent i = new Intent (Menu_Lobby.this, Convocatorias.class);
            startActivity(i);
        } else if (id == R.id.nav_route) {
            Intent i = new Intent (Menu_Lobby.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("usuario", "");
            editor.putString("password", "");
            editor.commit();

            Intent i = new Intent(Menu_Lobby.this, Login.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNameAndEmail(String boleta) {

        BaseDeDatos helper = new BaseDeDatos(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        String SQL = "SELECT nombre, email FROM Alumno WHERE boleta = '" + boleta +"'";
        Cursor c = db.rawQuery(SQL, null);
        c.moveToFirst();

        String nombre, email;
        nombre = c.getString(0);        // OBTENIENDO NOMBRE
        email = c.getString(1);         // OBTENIENDO EMAIL

        setContentView(R.layout.nav_header_menu__lobby);
        lbNombre = findViewById(R.id.lbNombreMenu);
        lbEmail = findViewById(R.id.lbEmailMenu);
        lbNombre.setText("" + nombre);
        lbEmail.setText("" + email);

        db.close();
    }
}