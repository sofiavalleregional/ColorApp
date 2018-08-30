package com.worldskills.colorapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBase extends SQLiteOpenHelper {

    public static final String NAME = "colorapp.db";
    public static final int VERSION= 1;
    public static final String TABLE_NAME = "CREATE TABLE PARTIDA (ID INTEGER PRIMARY KEY AUTOINCREMENT, PUNTAJE INTEGER)";


    public DataBase(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NAME);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CREATE"+ TABLE_NAME);
        db.execSQL(TABLE_NAME);

    }


    public void save(int puntaje){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put("PUNTAJE", puntaje);

        db.insert("PARTIDA", null, values);

    }

    public Cursor load(){
        SQLiteDatabase db= getWritableDatabase();
        Cursor cursor= null;

        try {
            String find []= {"PUNTAJE"};
            String limit ="4";
            String orderby = "DESC";

            cursor= db.query("PARTIDA", find, null, null, null, null, orderby, limit);
        }catch (Exception e){}

        return cursor;
    }
}
