package com.main.app_firmas_segucor.models;

public class FirmaConformidad {
    private int idfirma;
    private int rut;
    private int idempresa;
    private String pathfirma;
    private String nombreFirma;

    public String getNombreFirma() {
        return nombreFirma;
    }

    public void setNombreFirma(String nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    public int getIdfirma() {
        return idfirma;
    }

    public void setIdfirma(int idfirma) {
        this.idfirma = idfirma;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getPathfirma() {
        return pathfirma;
    }

    public void setPathfirma(String pathfirma) {
        this.pathfirma = pathfirma;
    }
}
