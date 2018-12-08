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
                "monto FLOAT NOT NULL, duracion INT NOT NULL, minPromedio FLOAT NOT NULL)";
        db.execSQL(SQL_BECA);        // EJECUTANDO SENTENCIA SQL TABLA BECA

        String SQL_ALUMNO = "CREATE TABLE Alumno (boleta TEXT NOT NULL PRIMARY KEY, nombre TEXT NOT NULL, " +
                "paterno TEXT NOT NULL, materno TEXT NOT NULL, promedio FLOAT NOT NULL, adeudos INTEGER NOT NULL, " +
                "email TEXT DEFAULT 'a@a.a', password TEXT DEFAULT '00000000', first_time INT DEFAULT 0, idBeca INTEGER DEFAULT 0, " +
                "FOREIGN KEY(idBeca) REFERENCES Beca(idBeca))";
        db.execSQL(SQL_ALUMNO);      // EJECUTANDO SENTENCIA SQL TABLA ALUMNO


        // INSERTANDO EN TABLA BECA
        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, duracion, minPromedio) " +
                "VALUES (1, 'Excelencia', 2000.00, 12, 8.5)");

        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, duracion, minPromedio) " +
                "VALUES (2, 'Institucional A', 900.00, 12, 7.0)");

        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, duracion, minPromedio) " +
                "VALUES (3, 'Institucional B', 1100.00, 12, 7.0)");

        db.execSQL("INSERT INTO Beca(idBeca, nombre, monto, duracion, minPromedio) " +
                "VALUES (4, 'Manutencion', 850.00, 12, 6.0)");


        // INSERTANDO EN TABLA ALUMNO
        db.execSQL("INSERT INTO Alumno(boleta, nombre, paterno, materno, promedio, adeudos) " +
                "VALUES ('2014081269', 'Luis Ernesto', 'Gutiérrez', 'Chávez', 8.92, 0)");

        db.execSQL("INSERT INTO Alumno(boleta, nombre, paterno, materno, promedio, adeudos) " +
                "VALUES ('2014090365', 'José', 'López', 'Ramírez', 8.3, 0)");

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
