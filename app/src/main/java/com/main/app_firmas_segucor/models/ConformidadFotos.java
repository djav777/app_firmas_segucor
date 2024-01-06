package com.main.app_firmas_segucor.models;

import com.google.gson.annotations.SerializedName;

public class ConformidadFotos {
    @SerializedName("registro")
    private int registro;

    @SerializedName("archivo_fotos")
    private byte[] archivo_fotos;

    public int getRegistro() {
        return registro;
    }

    public void setRegistro(int registro) {
        this.registro = registro;
    }

    public byte[] getArchivo_fotos() {
        return archivo_fotos;
    }

    public void setArchivo_fotos(byte[] archivo_fotos) {
        this.archivo_fotos = archivo_fotos;
    }
}
