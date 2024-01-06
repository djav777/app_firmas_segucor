package com.main.app_firmas_segucor.config.db.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.main.app_firmas_segucor.config.db.DbHelper;
import com.main.app_firmas_segucor.models.SolCotizacionOT;

import java.util.ArrayList;
import java.util.List;

public class OTData {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    public OTData(Context context){dbHelper =new DbHelper(context); }
    public void open(){
        db = dbHelper.getWritableDatabase();
    int p=1;
    }
    public void close(){
        dbHelper.close();
    }

    public void borrarOtes() throws Exception{
        try{
            String query = "DELETE FROM OTES";
            db.execSQL(query);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<SolCotizacionOT> getOtes() throws Exception{
        List<SolCotizacionOT> list = null;
        try{
            String query = "SELECT * FROM OTES";
            String[] selectionsArgs = new String[]{};
            Cursor cursor = db.rawQuery(query, selectionsArgs);
            list = new ArrayList<SolCotizacionOT>();
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    SolCotizacionOT item = new SolCotizacionOT();
                    item.setOt(cursor.getInt(cursor.getColumnIndexOrThrow("ID_OT")));
                    item.setConformidad(cursor.getInt(cursor.getColumnIndexOrThrow("ID_CONFORMIDAD")));
                    item.setFecha_op(cursor.getString(cursor.getColumnIndexOrThrow("FECHA")));
                    item.setSolcotizacion(cursor.getString(cursor.getColumnIndexOrThrow("ID_SC")));
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

    public void insertOt(String ot, String sc, String fecha, String conformidad) throws Exception{
        try {
            String query = "INSERT INTO OTES ( " +
                    "ID_OT,ID_SC,FECHA,ID_CONFORMIDAD ) VALUES (?, ?, ?, ?);";
            db.execSQL(
                    query,
                    new Object[] {
                            ot,
                            sc,
                            fecha,
                            conformidad
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}