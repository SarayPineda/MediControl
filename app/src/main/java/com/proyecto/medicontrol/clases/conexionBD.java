package com.proyecto.medicontrol.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class conexionBD extends SQLiteOpenHelper {

    public conexionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(utilidades.crearTablaProductos);
        DB.execSQL(utilidades.crearTablaIngresos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS " + utilidades.tablaProductos);
        DB.execSQL("DROP TABLE IF EXISTS " + utilidades.tablaIngresos);
        onCreate(DB);
    }
}
