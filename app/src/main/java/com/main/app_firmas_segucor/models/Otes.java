package com.main.app_firmas_segucor.models;

import com.google.gson.annotations.SerializedName;

public class Otes {

    @SerializedName("ot")
    private int ot;

    @SerializedName("conformidad")
    private int conformidad;

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
