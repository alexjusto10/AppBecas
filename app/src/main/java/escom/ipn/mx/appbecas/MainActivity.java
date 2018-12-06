package escom.ipn.mx.appbecas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Button btn_mapa;
    Spinner cantidad;
    int posicionSpinn=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_mapa = findViewById(R.id.btn_mapa);

        cantidad = findViewById(R.id.spin_cantidad);
        cantidad.setOnItemSelectedListener(new ItemSelectedListener());

        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Transporte.class);
                intent.putExtra("posicion", posicionSpinn);
                startActivity(intent);
            }
        });
    }

    private class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            posicionSpinn = position;
            Log.i("CANTIDAD","Opcion "+parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            return;
        }
    }
}
