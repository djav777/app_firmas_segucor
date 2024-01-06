package com.main.app_firmas_segucor.config.db.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.main.app_firmas_segucor.config.db.DbHelper;
import com.main.app_firmas_segucor.models.Logos;
import com.main.app_firmas_segucor.models.SolCotizacionOT;

import java.util.ArrayList;
import java.util.List;

public class LogosOtData {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    public LogosOtData(Context context){dbHelper =new DbHelper(context); }
    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }

    public void borrarLogos() throws Exception{
        try{
            String query = "DELETE FROM LOGOS";
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

    public Logos getLogo(String id_ot) throws Exception{
        Logos item = new Logos();
        try{
            String query = "SELECT * FROM LOGOS WHERE ID_OT = ? LIMIT 1";
            String[] selectionsArgs = new String[]{id_ot};
            Cursor cursor = db.rawQuery(query, selectionsArgs);

            if (cursor != null && cursor.moveToFirst()) {
                item.setIdempresa(cursor.getInt(cursor.getColumnIndexOrThrow("ID_EMPRESA")));
                item.setLogos(cursor.getString(cursor.getColumnIndexOrThrow("LOGO")));
                item.setFirmavb1oc(cursor.getString(cursor.getColumnIndexOrThrow("FIRMAVB1OC")));
                item.setFirmavb2oc(cursor.getString(cursor.getColumnIndexOrThrow("FIRMAVB2OC")));
                item.setFirmavb3oc(cursor.getString(cursor.getColumnIndexOrThrow("FIRMAVB3OC")));
                item.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow("DIRECCION")));
                item.setPdfcotizacion(cursor.getString(cursor.getColumnIndexOrThrow("PDFCOTIZACION")));
                item.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("FECHA")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return item;
    }

    public Logos getLogoPDF(String id_sc) throws Exception{
        Logos item = new Logos();
        try{
            String query = "SELECT PDFCOTIZACION FROM LOGOS WHERE ID_SC = ? LIMIT 1";
            String[] selectionsArgs = new String[]{id_sc};
            Cursor cursor = db.rawQuery(query, selectionsArgs);

            if (cursor != null && cursor.moveToFirst()) {
                item.setPdfcotizacion(cursor.getString(cursor.getColumnIndexOrThrow("PDFCOTIZACION")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return item;
    }

    public Logos getLogoEmpresa(String id_ot) throws Exception{
        Logos item = new Logos();
        try{
            String query = "SELECT FIRMAVB3OC, FIRMAVB2OC, FECHA, DIRECCION FROM LOGOS WHERE ID_OT = ? LIMIT 1";
            String[] selectionsArgs = new String[]{id_ot};
            Cursor cursor = db.rawQuery(query, selectionsArgs);

            if (cursor != null && cursor.moveToFirst()) {
                item.setFirmavb3oc(cursor.getString(cursor.getColumnIndexOrThrow("FIRMAVB3OC")));
                item.setFirmavb2oc(cursor.getString(cursor.getColumnIndexOrThrow("FIRMAVB2OC")));
                item.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow("DIRECCION")));
                item.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("FECHA")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return item;
    }

    public void insertLogo(int id_ot, Logos logos) throws Exception{
        try {
            String query = "INSERT INTO LOGOS ( " +
                    "ID_EMPRESA, ID_OT, ID_SC, LOGO, FIRMAVB1OC, FIRMAVB2OC, FIRMAVB3OC, PDFCOTIZACION, DIRECCION, FECHA)"+
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            db.execSQL(
                    query,
                    new Object[] {
                            logos.getIdempresa(),
                            id_ot,
                            logos.getSc(),
                            logos.getLogos(),
                            logos.getFirmavb1oc(),
                            logos.getFirmavb2oc(),
                            logos.getFirmavb3oc(),
                            logos.getPdfcotizacion(),
                            logos.getDireccion(),
                            logos.getFecha()
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}