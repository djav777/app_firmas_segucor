package com.main.app_firmas_segucor.models;

import com.google.gson.annotations.SerializedName;

public class OtesConformidad {
    @SerializedName("empresa")
    private int empresa;

    @SerializedName("ot")
    private int ot;

    @SerializedName("pdfconformidad")
    private byte[] pdfconformidad;

    @SerializedName("registro")
    private int registro;



    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public int getOt() {
        return ot;
    }

    public void setOt(int ot) {
        this.ot = ot;
    }

    public byte[] getPdfconformidad() {
        return pdfconformidad;
    }

    public void setPdfconformidad(byte[] pdfconformidad) {
        this.pdfconformidad = pdfconformidad;
    }
}
