package com.main.app_firmas_segucor.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.main.app_firmas_segucor.MainActivity;

import java.util.HashMap;

public class SessionOperarios {
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    Context mContext;

    public SessionOperarios(Context mContext) {
        this.mContext = mContext;
        preferences=mContext.getSharedPreferences(SessionKeys.data_app.name(), Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    public HashMap<SessionKeys, String> getRecord(){
        HashMap<SessionKeys, String> map = new HashMap<>();
        //comentario observacion
        map.put(SessionKeys.opRut, preferences.getString(SessionKeys.opRut.name(), ""));
        map.put(SessionKeys.opDv, preferences.getString(SessionKeys.opDv.name(), ""));
        map.put(SessionKeys.opNombre, preferences.getString(SessionKeys.opNombre.name(), ""));
        map.put(SessionKeys.empresaId, preferences.getString(SessionKeys.empresaId.name(), ""));
        map.put(SessionKeys.opComentario, preferences.getString(SessionKeys.opComentario.name(), ""));
        map.put(SessionKeys.opOtId, preferences.getString(SessionKeys.opOtId.name(), ""));

        return  map;
    }

    public void loginSession(String rut, String dv, String nombre, String idempresa){
        editor.putString(SessionKeys.opRut.name(), rut);
        editor.putString(SessionKeys.opDv.name(), dv);
        editor.putString(SessionKeys.opNombre.name(), nombre);
        editor.putString(SessionKeys.empresaId.name(), idempresa);
        editor.putBoolean(SessionKeys.isLoggedIn.name(), true);
        editor.commit();
    }

    public void opOtId(String otid){
        editor.putString(SessionKeys.opOtId.name(), otid);
        editor.commit();
    }

    public void opComentario(String comentario){
        editor.putString(SessionKeys.opComentario.name(), comentario);
        editor.commit();
    }

    public boolean CheckSession(){
        boolean islog;

        if(preferences.getBoolean(SessionKeys.isLoggedIn.name(),false)){
            islog = true;
        }else {
            islog = false;
        }

        return islog;
    }

    public void Logout(){
        editor.clear();
        editor.commit();
        mContext.startActivity(new Intent(mContext, MainActivity.class));
    }
}

