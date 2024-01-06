package com.main.app_firmas_segucor.models;

import com.google.gson.annotations.SerializedName;

public class SolCotizacionOT {
    @SerializedName("ot")
    private int ot;

    @SerializedName("conformidad")
    private int conformidad;
    @SerializedName("operario")
    private String operario;

    @SerializedName("solcotizacion")
    private String solcotizacion;

    @SerializedName("fecha_op")
    private String fecha_op;

    public SolCotizacionOT(int ot, int conformidad, String operario, String solcotizacion, String fecha_op){
        this.ot=ot;
        this.conformidad=conformidad;
        this.operario=operario;
        this.solcotizacion=solcotizacion;
        this.fecha_op=fecha_op;
    }
    public  SolCotizacionOT(){

    }


    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }
    public String getSolcotizacion() {
        return solcotizacion;
    }

    public void setSolcotizacion(String solcotizacion) {
        this.solcotizacion = solcotizacion;
    }

    public String getFecha_op() {
        return fecha_op;
    }

    public void setFecha_op(String fecha_op) {
        this.fecha_op = fecha_op;
    }

    private int pdf_listo;

    public int getPdf_listo() {
        return pdf_listo;
    }

    public void setPdf_listo(int pdf_listo) {
        this.pdf_listo = pdf_listo;
    }

    public int getConformidad() {
        return conformidad;
    }

    public void setConformidad(int conformidad) {
        this.conformidad = conformidad;
    }

    public int getOt() {
        return ot;
    }

    public void setOt(int ot) {
        this.ot = ot;
    }
}
