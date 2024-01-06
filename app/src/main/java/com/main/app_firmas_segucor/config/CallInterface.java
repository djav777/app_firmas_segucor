package com.main.app_firmas_segucor.config;

import com.main.app_firmas_segucor.models.ConformidadFotos;
import com.main.app_firmas_segucor.models.IDSolicitud;
import com.main.app_firmas_segucor.models.Login;
import com.main.app_firmas_segucor.models.Logos;
import com.main.app_firmas_segucor.models.OtesConformidad;
import com.main.app_firmas_segucor.models.PdfFrontalConformidad;
import com.main.app_firmas_segucor.models.SolCotizacionOT;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CallInterface {
    @GET("api/Operarios")
    Call<Login> getInitData(@Query("email") String email, @Query("contrasena") String contrasena);

    @GET("api/OtesAsignacionHh")
    Call<List<SolCotizacionOT>> getOtes(@Query("empresaid") int empresaid, @Query("rut") int rut);

    @GET("api/Logos")
    Call<Logos> getLogosEmpresa(@Query("idempresa") int idempresa);


    @POST("api/Cotizaciones")
    Call<List<Logos>> postDatosFirma(@Body List<IDSolicitud> idsol);

    @GET("api/FechaHora")
    Call<String> getFechaHora();

    @POST("api/OtesConformidad")
    Call<OtesConformidad> postPdfConformidad(@Body RequestBody body);

    @POST("api/ConformidadFotos")
    Call<ConformidadFotos> postConfFotos(@Body RequestBody body);

    @POST("api/ConformidadPdfFrontal")
    Call<PdfFrontalConformidad> postConfPdfFrontal(@Body RequestBody body);
}
