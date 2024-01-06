package com.main.app_firmas_segucor.config.db.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.main.app_firmas_segucor.config.db.DbHelper;
import com.main.app_firmas_segucor.models.FotoConformidad;

import java.util.ArrayList;
import java.util.List;

public class FotoConformidadData {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public FotoConformidadData(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void deleteFotos(String idot, String idempresa) throws Exception {
        try{
            String query = "DELETE FROM FOTO_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ?";
            db.execSQL(query,  new Object[]{idot, idempresa});
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void deleteFotoId(String idFoto, String idot, String idempresa) throws Exception {
        try{
            String query = "DELETE FROM FOTO_CONFORMIDAD WHERE ID_FOTO = ? AND ID_OT = ? AND ID_EMPRESA = ?";
            db.execSQL(query,  new Object[]{idFoto, idot, idempresa});
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public FotoConformidad getFotoOt(String idot, String idempresa) throws Exception {
        FotoConformidad item = new FotoConformidad();
        try{
            String query = "SELECT * FROM FOTO_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ? LIMIT 1";
            String[] selectionsArgs = new String[]{idot, idempresa};
            Cursor cursor = db.rawQuery(query, selectionsArgs);

            if (cursor != null && cursor.moveToFirst()) {
                item.setIdFoto(cursor.getInt(cursor.getColumnIndexOrThrow("ID_FOTO")));
                item.setPathFoto(cursor.getString(cursor.getColumnIndexOrThrow("PATH_FOTO")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return item;
    }

    public FotoConformidad getFotoFrontalOt(String idot, String idempresa) throws Exception {
        FotoConformidad item = new FotoConformidad();
        try{
            String query = "SELECT * FROM FOTO_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ? AND ID_FRONTAL = 1 LIMIT 1";
            String[] selectionsArgs = new String[]{idot, idempresa};
            Cursor cursor = db.rawQuery(query, selectionsArgs);

            if (cursor != null && cursor.moveToFirst()) {
                item.setIdFoto(cursor.getInt(cursor.getColumnIndexOrThrow("ID_FOTO")));
                item.setPathFoto(cursor.getString(cursor.getColumnIndexOrThrow("PATH_FOTO")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return item;
    }

    /**NO SE NECESITA DE UNA LISTA DE FOTOS YA QUE SOLO SE REQUIERE UNA*/
    public List<FotoConformidad> getListFotoOt(String idot, String idempresa) throws Exception {
        List<FotoConformidad> list = null;
        try{
            String query = "SELECT * FROM FOTO_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ?";
            String[] selectionsArgs = new String[]{idot, idempresa};
            Cursor cursor = db.rawQuery(query, selectionsArgs);
            list = new ArrayList<>();
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    FotoConformidad item = new FotoConformidad();
                    item.setIdFoto(cursor.getInt(cursor.getColumnIndexOrThrow("ID_FOTO")));
                    item.setIdOt(cursor.getInt(cursor.getColumnIndexOrThrow("ID_OT")));
                    item.setIdFrontal(cursor.getInt(cursor.getColumnIndexOrThrow("ID_FRONTAL")));
                    item.setIdEmpresa(cursor.getInt(cursor.getColumnIndexOrThrow("ID_EMPRESA")));
                    item.setPathFoto(cursor.getString(cursor.getColumnIndexOrThrow("PATH_FOTO")));
                    list.add(item);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return list;
    }

    public void insertFoto(String idOt, String idEmpresa, String pathFoto) throws Exception{

        try {
            String query = "INSERT INTO FOTO_CONFORMIDAD ( " +
                    "ID_OT, ID_EMPRESA, ID_FRONTAL, PATH_FOTO ) VALUES (?, ?, ?, ?);";
            db.execSQL(
                    query,
                    new Object[] {
                            idOt,
                            idEmpresa,
                            0,
                            pathFoto
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void insertFotoFrontal(String idOt, String idEmpresa, String pathFoto) throws Exception{

        try {
            String query = "INSERT INTO FOTO_CONFORMIDAD ( " +
                    "ID_OT, ID_EMPRESA, ID_FRONTAL, PATH_FOTO ) VALUES (?, ?, ?, ?);";
            db.execSQL(
                    query,
                    new Object[] {
                            idOt,
                            idEmpresa,
                            1,
                            pathFoto
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}