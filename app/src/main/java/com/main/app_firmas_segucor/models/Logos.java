package com.main.app_firmas_segucor.models;

import com.google.gson.annotations.SerializedName;

public class Logos {
    private int idempresa;
    private int otid;
    private int sc;
    @SerializedName("logo")
    private String logos;

    @SerializedName("firmavb1oc")
    private String firmavb1oc;

    @SerializedName("firmavb2oc")
    private String firmavb2oc;

    @SerializedName("firmavb3oc")
    private String firmavb3oc;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("pdfcotizacion")
    private String pdfcotizacion;

    public String getPdfcotizacion() {
        return pdfcotizacion;
    }

    public void setPdfcotizacion(String pdfcotizacion) {
        this.pdfcotizacion = pdfcotizacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdempresa() {
        return idempresa;
    }
    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getSc() {
        return sc;
    }
    public void setSc(int sc) {
        this.sc = sc;
    }
    public int getOTid() {
        return otid;
    }

    public void setOTid(int otid) {
        this.otid = otid;
    }


    public String getLogos() {
        return logos;
    }

    public void setLogos(String logos) {
        this.logos = logos;
    }

    public String getFirmavb1oc() {
        return firmavb1oc;
    }

    public void setFirmavb1oc(String firmavb1oc) {
        this.firmavb1oc = firmavb1oc;
    }

    public String getFirmavb2oc() {
        return firmavb2oc;
    }

    public void setFirmavb2oc(String firmavb2oc) {
        this.firmavb2oc = firmavb2oc;
    }

    public String getFirmavb3oc() {
        return firmavb3oc;
    }

    public void setFirmavb3oc(String firmavb3oc) {
        this.firmavb3oc = firmavb3oc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
