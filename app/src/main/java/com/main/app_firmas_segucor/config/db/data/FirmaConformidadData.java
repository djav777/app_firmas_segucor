package com.main.app_firmas_segucor.config.db.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.main.app_firmas_segucor.config.db.DbHelper;
import com.main.app_firmas_segucor.models.FirmaConformidad;

public class FirmaConformidadData {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public FirmaConformidadData(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void deleteFirma(String idot, String idempresa) throws Exception {
        try{
            String query = "DELETE FROM  FIRMA_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ?";
            db.execSQL(query,  new Object[]{idot, idempresa});
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public FirmaConformidad getFirma(String otId, String rut, String idempresa) throws Exception {
        FirmaConformidad model = new FirmaConformidad();
        try{
            String query = "SELECT * FROM FIRMA_CONFORMIDAD WHERE ID_OT = ? AND RUT = ? AND ID_EMPRESA = ? LIMIT 1";
            String[] selectionsArgs = new String[]{otId, rut, idempresa};
            Cursor cursor = db.rawQuery(query, selectionsArgs);

            if (cursor != null && cursor.moveToFirst()) {
                model.setIdfirma(cursor.getInt(cursor.getColumnIndexOrThrow("ID_FIRMA")));
                model.setRut(cursor.getInt(cursor.getColumnIndexOrThrow("RUT")));
                model.setIdempresa(cursor.getInt(cursor.getColumnIndexOrThrow("ID_EMPRESA")));
                model.setNombreFirma(cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_FIRMA")));
                model.setPathfirma(cursor.getString(cursor.getColumnIndexOrThrow("PATH_FIRMA")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return model;
    }

    public void insertFirma(String idOt, String rut, String nombreFirma, String idEmpresa, String pathFirma) throws Exception{
        try {
            String query = "INSERT INTO FIRMA_CONFORMIDAD ( " +
                    "ID_OT, RUT, ID_EMPRESA, NOMBRE_FIRMA, PATH_FIRMA ) VALUES (?, ?, ?, ?, ?);";
            db.execSQL(
                    query,
                    new Object[] {
                            idOt,
                            rut,
                            idEmpresa,
                            nombreFirma,
                            pathFirma
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    public void updateFirma(String idOt, String rut, String idEmpresa, String pathFirma){
        try{
            String query = "UPDATE FIRMA_CONFORMIDAD SET " +
                    "PATH_FIRMA = ? "+
                    "WHERE ID_OT = ? " +
                    "AND RUT = ? AND ID_EMPRESA = ?" +
                    ";";

            db.execSQL(
                    query,
                    new Object[] {
                            pathFirma,
                            idOt,
                            rut,
                            idEmpresa
                    });
        }catch (Exception ex){

        }
    }

}
