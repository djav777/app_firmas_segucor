package com.main.app_firmas_segucor.models;

import com.google.gson.annotations.SerializedName;

public class PdfFrontalConformidad {
    @SerializedName("registro")
    private int registro;

    @SerializedName("pdf_conformidad")
    private byte[] pdf_conformidad;

    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public byte[] getPdf_conformidad() {
        return pdf_conformidad;
    }

    public void setPdf_conformidad(byte[] pdf_conformidad) {
        this.pdf_conformidad = pdf_conformidad;
    }
}
