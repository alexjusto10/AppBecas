package escom.ipn.mx.appbecas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;

import java.util.Random;
import java.util.regex.Pattern;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// EMAIL
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static java.security.AccessController.getContext;


public class Registro extends Activity {

    Button btnAceptar, btnCancelar;
    EditText txtBoleta, txtEmail;

    // VARIABLES PARA MAIL
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    String rec, subject, textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        txtBoleta = (EditText)findViewById(R.id.txtBoleta);
        txtEmail = (EditText)findViewById(R.id.txtEmail);

        context = this;

        RegisterProcess();
    }

    @Override
    protected void onResume(){
        super.onResume();
        RegisterProcess();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        RegisterProcess();
    }

    private Boolean validarEmail(String correo) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(correo).matches();
    }

    public void RegisterProcess(){

        BaseDeDatos helper = new BaseDeDatos(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // VERIFICANDO QUE NO ESTEN VACIOS LOS CAMPOS
                if(txtBoleta.getText().toString().isEmpty() || txtEmail.getText().toString().isEmpty()) {
                    Toast.makeText(Registro.this,"Llena todos los campos, por favor", Toast.LENGTH_SHORT).show();
                } else {
                    if(!validarEmail(txtEmail.getText().toString())) {    // VERIFICANDO QUE SEA UN EMAIL
                        Toast.makeText(Registro.this,"Coloca una dirección de correo válida", Toast.LENGTH_SHORT).show();
                    } else {

                        // VERIFICANDO QUE EXISTA ESA BOLETA
                        // HACIENDO CONSULTA A LA BD
                        String SQL = "SELECT * FROM Alumno WHERE boleta = '" + txtBoleta.getText().toString() + "'";
                        Cursor c = db.rawQuery(SQL, null);
                        c.moveToFirst();
                        //System.out.println("BOLETA: " + c.getString(0));
                        int filas = c.getCount();
                        c.close();
                        System.out.println("FILAS: " + filas);

                        if(filas == 0) {        // SI LA BOLETA NO EXISTE
                            Toast.makeText(Registro.this,"La boleta no existe", Toast.LENGTH_SHORT).show();
                        } else {                // LA BOLETA EXISTE

                            if (FirstTime(txtBoleta.getText().toString())) { // SI NO SE HA REGISTRADO, SE ENVIA CORREO Y SE ACTUALIZAN SUS DATOS

                                ConnectivityManager conexion = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = conexion.getActiveNetworkInfo();

                                if (networkInfo != null && networkInfo.isConnected()) {
                                    String actualizaAlumno = "UPDATE Alumno SET email = '" + txtEmail.getText().toString()
                                            + "' WHERE boleta ='" + txtBoleta.getText().toString() + "'";
                                    db.execSQL(actualizaAlumno);

                                    // GENERANDO CONTRASEÑA
                                    Random generador = new Random();
                                    int numero = 0;
                                    String passwd = "";

                                    for (int i = 0; i < 8; i++) {
                                        numero = Math.abs(generador.nextInt() % 9);
                                        System.out.println("NUMERO GENERADO: " + numero);
                                        passwd += String.valueOf(numero);
                                    }
                                    System.out.println("PASSWD GENERADO: " + passwd);

                                    // ACTUALIZANDO PASSWORD EN LA BD
                                    actualizaAlumno = "UPDATE Alumno SET password = '" + passwd
                                            + "' WHERE boleta = '" + txtBoleta.getText().toString() + "'";
                                    db.execSQL(actualizaAlumno);

                                    // ACTUALIZANDO FIRST TIME
                                    actualizaAlumno = "UPDATE Alumno SET first_time = 1 WHERE boleta = '" +
                                            txtBoleta.getText().toString() + "'";
                                    db.execSQL(actualizaAlumno);

                                    // ENVIANDO CORREO CON PASSWD GENERADO
                                    EnviaCorreo(txtEmail.getText().toString(), passwd);

                                    db.close();
                                } else {
                                    Toast.makeText(Registro.this, "No se pudo realizar el registro" +
                                            ", verifica tu acceso a Internet e intenta nuevamente", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Registro.this,"Esta boleta ya ha sido registrada", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private Boolean FirstTime(String user){
        BaseDeDatos helper = new BaseDeDatos(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        int result = 0;

        String SQL = "SELECT first_time FROM Alumno WHERE boleta = '" + user + "'";
        Cursor c = db.rawQuery(SQL, null);
        if (c != null) {
            c.moveToFirst();
            result = c.getInt(c.getColumnIndex("first_time"));
            System.out.println("First Time: " + result);
        }

        c.close();

        if (result == 0) {      // SI ES PRIMERA VEZ
            return true;
        } else {                // SI YA SE HA REGISTRADO
            return false;
        }
    }

    private void EnviaCorreo(String mail, String passwd) {
        rec = mail; // ENVIAR A
        subject = "Registro en App Becas ESCOM";
        textMessage = "Tu contraseña para acceder a la aplicación es: " + passwd;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("escom.becas@gmail.com", "proyectoMoviles6");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Enviando correo...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("testfrom354@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            Toast.makeText(getApplicationContext(), "¡Correo enviado exitosamente!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Registro.this, Login.class);
            setResult(RESULT_OK);
            finish();
        }
    }
}
