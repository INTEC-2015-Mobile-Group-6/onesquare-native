package com.example.onesquare.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by skywalker on 04/02/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Tabla
    private final String table1 = "places";

    //Columnas
    private final String Col1 = "name";
    private final String Col2 = "place";
    private final String Col3 = "country";
    private final String Col4 = "establishment";
    private final String Col5 = "website";
    private final String Col6 = "photo_url";
    private final String Col7 = "remember";

    //Se define el context, nombre de la BD y version
    public DatabaseHelper(Context context) { super(context, "Places", null, 1); }

    //Metodo heredado de SQLiteOpenHelper
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onCreate(SQLiteDatabase db) {

        //tabla places
        createTable(db,this.table1);

        //agregando las columnas (con el pk que se genero junto con la tabla)
        addTableColumn(db,this.table1, this.Col1,"TEXT");
        addTableColumn(db,this.table1, this.Col2,"TEXT");
        addTableColumn(db,this.table1, this.Col3,"TEXT");
        addTableColumn(db,this.table1, this.Col4,"TEXT");
        addTableColumn(db,this.table1, this.Col5,"TEXT");
        addTableColumn(db,this.table1, this.Col6,"TEXT");
        addTableColumn(db,this.table1, this.Col7,"TEXT");

    }

    private void addTableColumn(SQLiteDatabase db, String tableName, String columnName, String typeColumn) {
        db.execSQL("ALTER TABLE" +tableName+ " ADD COLUMN" +columnName+" " +typeColumn+";" );
    }


    private void createTable(SQLiteDatabase db, String tableName) {
        db.execSQL("CREATE TABLE " +tableName+"(id"+tableName+" Integer PRIMARY KEY);");
    }

    //MOTRENCO INSERT(PERO FUNCIONA)
    public void inserTable(String tableName, String col1, String data1, String col2, String data2, String col3, String data3, String col4, String data4, String col5,String data5, String col6,String data6, String col7,String data7) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, data1);
        values.put(col2, data2);
        values.put(col3, data3);
        values.put(col4, data4);
        values.put(col5, data5);
        values.put(col6, data6);
        values.put(col7, data7);

        db.insert(tableName, null, values);
        db.close();

    }

    public Cursor getAll(String table) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id"+table+"as _id, nombre||' '||place as lugar FROM "+table+";",null);
        return c;
    }

    public Cursor getRemembered(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id"+table+"as _id, nombre||' '||place as lugar FROM "+table+" where remember=yes;",null);
        return c;
    }



}
