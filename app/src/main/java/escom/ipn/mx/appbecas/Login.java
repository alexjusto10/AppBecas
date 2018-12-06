package escom.ipn.mx.appbecas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    Button btnEntrar, btnRegistrar;
    TextView txtBoleta, txtPassword;
    CheckBox remember;
    int REG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = (Button)findViewById(R.id.btnEntrar);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        remember = findViewById(R.id.chkRecordar);
        txtBoleta = findViewById(R.id.txtBoleta);
        txtPassword = findViewById(R.id.txtPassword);

        LoginProcess();
    }

    @Override
    protected void onResume(){
        super.onResume();
        LoginProcess();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        LoginProcess();
    }

    public void LoginProcess(){
        BaseDeDatos helper = new BaseDeDatos(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        txtBoleta.setText(prefs.getString("usuario", ""));
        txtPassword.setText(prefs.getString("password", ""));

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     // BIENVENIDO
                // VERIFICANDO QUE NO ESTEN VACIOS LOS CAMPOS
                if(txtBoleta.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Ingresa los datos solicitados, por favor", Toast.LENGTH_LONG).show();
                } else {
                    // HACIENDO CONSULTA A LA BD
                    String SQL = "SELECT * FROM Alumno WHERE boleta = '" + txtBoleta.getText().toString() +
                            "' AND password = '" + txtPassword.getText().toString() + "'";
                    Cursor c = db.rawQuery(SQL, null);
                    c.moveToFirst();

                    int filas = c.getCount();
                    String nombre_usuario = "";

                    if (filas > 0){ // SI EL USUARIO ES VALIDO
                        nombre_usuario = c.getString(1); // OBTIENE EL NOMBRE DEL USUARIO
                    }
                    c.close();
                    System.out.println("FILAS: " + filas);

                    SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                    // SI EL USUARIO ES VALIDO, ENTRA
                    if (filas > 0) {
                        if (remember.isChecked()) {     // SI RECORDAR ESTA ACTIVO
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("usuario", txtBoleta.getText().toString());
                            editor.putString("password", txtPassword.getText().toString());
                            editor.commit();
                        } else {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("usuario", "");
                            editor.putString("password", "");
                            editor.commit();
                        }

                        Toast.makeText(Login.this, "LOGIN EXITOSO", Toast.LENGTH_SHORT).show();

                        /*Intent intent = new Intent(Login.this, Lobby.class);
                        intent.putExtra("usuario", nombre_usuario);
                        startActivity(intent);
                        finish();*/
                    } else {
                        Toast.makeText(Login.this, "ERROR. Datos Incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {      // REGISTRARSE
                Intent intent = new Intent(Login.this, Registro.class);
                startActivityForResult(intent, REG);     // REG es un identificador para obtener una respuesta de la otra clase
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent i){
        if(requestCode == REG){
            if(resultCode == RESULT_OK) { // Everything's OK
                Toast.makeText(this,"Te has registrado correctamente!", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,"Registro cancelado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Código inesperado", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this,"Código inesperado", Toast.LENGTH_LONG).show();
        }
    }
}
