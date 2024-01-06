package com.main.app_firmas_segucor.config.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DbSegucor.db";

    private static final int DATABASE_VERSION = 1;
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_FIRMA_CONFORMIDAD);
        db.execSQL(CREAR_TABLA_PDF_CONFORMIDAD);
        db.execSQL(CREAR_TABLA_FOTO_CONFORMIDAD);
        db.execSQL(CREAR_TABLA_OTES);
        db.execSQL(CREAR_TABLA_LOGOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public static final String CREAR_TABLA_FOTO_CONFORMIDAD = " CREATE TABLE FOTO_CONFORMIDAD( " +
            "ID_FOTO     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " +
            "ID_OT       INTEGER NOT NULL, " +
            "ID_EMPRESA  INTEGER NOT NULL, " +
            "ID_FRONTAL  INTEGER NOT NULL, " +
            "PATH_FOTO   NVARCHAR(200) NOT NULL" +
            ");";

    public static final String CREAR_TABLA_PDF_CONFORMIDAD = " CREATE TABLE PDF_CONFORMIDAD( " +
            "ID_PDF      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " +
            "ID_OT       INTEGER NOT NULL, " +
            "ID_EMPRESA  INTEGER NOT NULL, " +
            "PATH_PDF    NVARCHAR(200) NOT NULL, " +
            "ID_FRONTAL INTEGER NOT NULL"+
            ");";

    public static final String CREAR_TABLA_FIRMA_CONFORMIDAD = " CREATE TABLE FIRMA_CONFORMIDAD( " +
            "ID_FIRMA     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  " +
            "ID_OT        INTEGER NOT NULL, " +
            "RUT          NVARCHAR(200) NOT NULL, " +
            "ID_EMPRESA   INTEGER NOT NULL, " +
            "NOMBRE_FIRMA NVARCHAR(200) NOT NULL, " +
            "PATH_FIRMA   NVARCHAR(200) NOT NULL" +
            ");";

    public static final String CREAR_TABLA_OTES = " CREATE TABLE OTES( " +
            "ID_OT        INTEGER NOT NULL, " +
            "ID_SC        INTEGER NOT NULL, " +
            "FECHA        NVARCHAR(100), " +
            "ID_CONFORMIDAD   INTEGER NOT NULL, " +
            "PATH_OT_PDF   NVARCHAR(200)" +
            ");";

    public static final String CREAR_TABLA_LOGOS = " CREATE TABLE LOGOS( " +
            "ID_EMPRESA        INTEGER NOT NULL, " +
            "ID_OT   INTEGER NOT NULL, "+
            "ID_SC   INTEGER NOT NULL, "+
            "LOGO   TEXT, " +
            "FIRMAVB1OC   TEXT, " +
            "FIRMAVB2OC   TEXT, " +
            "FIRMAVB3OC   TEXT, " +
            "PDFCOTIZACION   TEXT, " +
            "DIRECCION   TEXT, " +
            "FECHA   TEXT " +
            ");";
}
