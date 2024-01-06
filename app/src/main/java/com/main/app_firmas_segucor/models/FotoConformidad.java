package com.main.app_firmas_segucor.models;

public class FotoConformidad {
    private int idFoto;
    private int idOt;
    private int idEmpresa;
    private int idFrontal;

    public int getIdFrontal() {
        return idFrontal;
    }

    public void setIdFrontal(int idFrontal) {
        this.idFrontal = idFrontal;
    }

    public int getIdOt() {
        return idOt;
    }

    public void setIdOt(int idOt) {
        this.idOt = idOt;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    private String pathFoto;

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }
}
