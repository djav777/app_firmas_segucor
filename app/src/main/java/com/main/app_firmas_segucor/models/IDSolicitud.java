package com.main.app_firmas_segucor.models;

import com.google.gson.annotations.SerializedName;

public class IDSolicitud {
    @SerializedName("idsol")
    private int idsol;

    public IDSolicitud(int idsol){
        this.idsol=idsol;
    }
    public int getIdsol() {
        return idsol;
    }

    public void setIdsol(int idsol) {
        this.idsol = idsol;
    }
}
