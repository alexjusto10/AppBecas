package escom.ipn.mx.appbecas;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtTransp1,txtTransp2,txtActv,btnActv,btnEstudio,btnCarta,btnIngrEgr,btnIngr,btnDom;
    Button btn_mapa;
    Spinner cantidad;
    int posicionSpinn=0,beca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // APARECE BOTÓN DE REGRESAR EN EL TITULO DE LA ACTIVITY

        btn_mapa = findViewById(R.id.btn_mapa);
        txtTransp1 = findViewById(R.id.txtTransp1);
        txtTransp2 = findViewById(R.id.txtTransp2);
        txtActv = findViewById(R.id.txtActv);
        btnActv = findViewById(R.id.btnActv);
        cantidad = findViewById(R.id.spin_cantidad);
        btnEstudio =findViewById(R.id.btnEstudio);
        btnCarta = findViewById(R.id.btnCarta);
        btnIngrEgr = findViewById(R.id.btnIngrEgr);
        btnDom = findViewById(R.id.btnDom);
        btnIngr = findViewById(R.id.btnIngr);
        cantidad.setOnItemSelectedListener(new ItemSelectedListener());

        Intent i = getIntent();
        beca = i.getIntExtra("beca",1);

        if(beca==1){
            txtTransp1.setVisibility(View.GONE);
            txtTransp2.setVisibility(View.GONE);
            cantidad.setVisibility(View.INVISIBLE);
        }else{
            txtActv.setVisibility(View.GONE);
            btnActv.setVisibility(View.GONE);
            btn_mapa.setText("Crear ruta de transporte");
        }

        btnEstudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()); // a directory
                intent.setDataAndType(uri, "application/pdf");
                startActivity(Intent.createChooser(intent, "Open folder"));
            }
        });

        btnCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()); // a directory
                intent.setDataAndType(uri, "application/pdf");
                startActivity(Intent.createChooser(intent, "Open folder"));
            }
        });

        btnIngrEgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()); // a directory
                intent.setDataAndType(uri, "application/pdf");
                startActivity(Intent.createChooser(intent, "Open folder"));
            }
        });

        btnDom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()); // a directory
                intent.setDataAndType(uri, "application/pdf");
                startActivity(Intent.createChooser(intent, "Open folder"));
            }
        });

        btnIngr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()); // a directory
                intent.setDataAndType(uri, "application/pdf");
                startActivity(Intent.createChooser(intent, "Open folder"));
            }
        });

        btnActv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()); // a directory
                intent.setDataAndType(uri, "application/pdf");
                startActivity(Intent.createChooser(intent, "Open folder"));
            }
        });

        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(beca==1) {
                    onBackPressed();
                    Toast.makeText(MainActivity.this, "Documentos enviados", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), Transporte.class);
                    intent.putExtra("posicion", posicionSpinn);
                    startActivity(intent);
                }
            }
        });
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

    private class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            posicionSpinn = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            return;
        }
    }
}
