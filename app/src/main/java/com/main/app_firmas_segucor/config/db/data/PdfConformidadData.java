package com.main.app_firmas_segucor.config.db.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.main.app_firmas_segucor.config.db.DbHelper;
import com.main.app_firmas_segucor.models.PdfConformidad;

import java.util.ArrayList;
import java.util.List;

public class PdfConformidadData {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public PdfConformidadData(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void deletePdf(String idot, String idempresa) throws Exception {
        try{
            String query = "DELETE FROM  PDF_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ?";
            db.execSQL(query,  new Object[]{idot, idempresa});
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<PdfConformidad> getListPdf(String idempresa) throws Exception {
        List<PdfConformidad> list = null;
        try{
            String query = "SELECT * FROM PDF_CONFORMIDAD WHERE  ID_EMPRESA = ? AND ID_FRONTAL = 0 LIMIT 1";
            String[] selectionsArgs = new String[]{idempresa};
            Cursor cursor = db.rawQuery(query, selectionsArgs);
            list = new ArrayList<>();
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    PdfConformidad model = new PdfConformidad();
                    model.setIdPdf(cursor.getInt(cursor.getColumnIndexOrThrow("ID_PDF")));
                    model.setPathPdf(cursor.getString(cursor.getColumnIndexOrThrow("PATH_PDF")));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return list;
    }

    public PdfConformidad getPdf(String idot, String idempresa) throws Exception {
        PdfConformidad model = new PdfConformidad();
        try{
            String query = "SELECT * FROM PDF_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ? AND ID_FRONTAL = 0 LIMIT 1";
            String[] selectionsArgs = new String[]{idot, idempresa};
            Cursor cursor = db.rawQuery(query, selectionsArgs);
            if (cursor != null && cursor.moveToFirst()) {
                model.setIdPdf(cursor.getInt(cursor.getColumnIndexOrThrow("ID_PDF")));
                model.setPathPdf(cursor.getString(cursor.getColumnIndexOrThrow("PATH_PDF")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return model;
    }

    public PdfConformidad getPdfFrontal(String idot, String idempresa) throws Exception {
        PdfConformidad model = new PdfConformidad();
        try{
            String query = "SELECT * FROM PDF_CONFORMIDAD WHERE ID_OT = ? AND ID_EMPRESA = ? AND ID_FRONTAL = 1 LIMIT 1";
            String[] selectionsArgs = new String[]{idot, idempresa};
            Cursor cursor = db.rawQuery(query, selectionsArgs);
            if (cursor != null && cursor.moveToFirst()) {
                model.setIdPdf(cursor.getInt(cursor.getColumnIndexOrThrow("ID_PDF")));
                model.setPathPdf(cursor.getString(cursor.getColumnIndexOrThrow("PATH_PDF")));
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return model;
    }

    public void insertPdf(String idOt, String idEmpresa, String pathFirma) throws Exception{
        try {
            String query = "INSERT INTO PDF_CONFORMIDAD ( " +
                    "ID_OT, ID_EMPRESA, PATH_PDF, ID_FRONTAL ) VALUES (?, ?, ?, ?);";
            db.execSQL(
                    query,
                    new Object[] {
                            idOt,
                            idEmpresa,
                            pathFirma,
                            0
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void insertPdfFrontal(String idOt, String idEmpresa, String pathFirma) throws Exception{
        try {
            String query = "INSERT INTO PDF_CONFORMIDAD ( " +
                    "ID_OT, ID_EMPRESA, PATH_PDF, ID_FRONTAL ) VALUES (?, ?, ?, ?);";
            db.execSQL(
                    query,
                    new Object[] {
                            idOt,
                            idEmpresa,
                            pathFirma,
                            1
                    });
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
