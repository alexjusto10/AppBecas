package escom.ipn.mx.appbecas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper { //HELPER PARA LA BD

    public BaseDeDatos(Context context){
        super(context, "AppBecas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_BECA = "CREATE TABLE Beca (idBeca INTEGER NOT NULL PRIMARY KEY, nombre TEXT NOT NULL, " +
                "monto FLOAT NOT NULL, descripcion TEXT NOT NULL, duracion INT NOT NULL, minPromedio FLOAT NOT NULL)";
        db.execSQL(SQL_BECA);        // EJECUTANDO SENTENCIA SQL TABLA BECA

        String SQL_ALUMNO = "CREATE TABLE Alumno (boleta TEXT NOT NULL PRIMARY KEY, nombre TEXT NOT NULL, " +
                "paterno TEXT NOT NULL, materno TEXT NOT NULL, promedio FLOAT NOT NULL, adeudos INTEGER NOT NULL, " +
                "email TEXT DEFAULT 'a@a.a', password TEXT DEFAULT '00000000', first_time INT DEFAULT 0, idBeca INTEGER DEFAULT 0, " +
                "FOREIGN KEY(idBeca) REFERENCES Beca(idBeca))";
        db.execSQL(SQL_ALUMNO);      // EJECUTANDO SENTENCIA SQL TABLA ALUMNO


        // INSERTANDO EN TABLA BECA
        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, descripcion, duracion, minPromedio) " +
                "VALUES (1, 'Excelencia', '-De cuarto a octavo semestre\n\n-Ser alumno regular\n\n" +
                "-Cursar al menos carga mínima\n\n-Tener una actividad extracurricular\n\n" +
                "-Promedio mayor a 8.5', 2650.00, 6, 8.5)");

        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, descripcion, duracion, minPromedio) " +
                "VALUES (2, 'Institucional A', '-De primero a octavo semestre\n\n" +
                "-Ser alumno regular\n\n-Cursar al menos carga mínima\n\n-Ingresos familiares no mayores a un salario" +
                "mínimo per cápita\n\n-Promedio mayor a 6.0', 950.00, 6, 6.0)");

        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, descripcion, duracion, minPromedio) " +
                "VALUES (3, 'Institucional B', 'Institucional A', '-De primero a octavo semestre\n\n" +
                "-Ser alumno regular\n\n-Cursar al menos carga mínima\n\n-Ingresos familiares no mayores a un salario\\n" +
                "mínimo per cápita\n\n-Promedio mayor a 8.0', 1100.00, 6, 8.0)");

        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, descripcion, duracion, minPromedio) " +
                "VALUES (4, 'De aprobación', '-De primero a octavo semestre\n\n-Tener exactamente una unidad " +
                "de aprendizaje reprobada\n\n-Cursar al menos carga mínima\n\n-Ingresos familiares " +
                "no mayores a un salario mínimo mensual\n\nPromedio mayor a 6.0', 500.00, 6, 6.0)");


        // INSERTANDO EN TABLA ALUMNO
        db.execSQL("INSERT INTO Alumno(boleta, nombre, paterno, materno, promedio, adeudos) " +
                "VALUES ('2014081269', 'Gibran Armando', 'Ramos', 'Guerrero', 8.92, 0)");

        db.execSQL("INSERT INTO Alumno(boleta, nombre, paterno, materno, promedio, adeudos) " +
                "VALUES ('2014090365', 'Jaime Alejandro', 'Justo', 'Vizcarra', 8.3, 0)");

        db.execSQL("INSERT INTO Alumno(boleta, nombre, paterno, materno, promedio, adeudos) " +
                "VALUES ('2017000000', 'Berenice', 'Maya', 'Calderón', 8.3, 1)");

        db.execSQL("INSERT INTO Alumno(boleta, nombre, paterno, materno, promedio, adeudos) " +
                "VALUES ('2017612210', 'Miguel', 'Correa', 'Medina', 6.9, 2)");

        String SQL = "SELECT * FROM Beca";
        Cursor c = db.rawQuery(SQL, null);
        if (c != null) {
            c.moveToFirst();
            do {
                String name = c.getString(c.getColumnIndex("nombre"));
                System.out.println("BECAS: " + name);
            } while (c.moveToNext());
        }


        SQL = "SELECT * FROM Alumno";
        c = db.rawQuery(SQL, null);
        if (c != null) {
            c.moveToFirst();
            do {
                String boleta = c.getString(c.getColumnIndex("boleta"));
                System.out.println("ALUMNO: " + boleta);
            } while (c.moveToNext());
        }

        c.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // SIN INSTRUCCIONES
    }
}
