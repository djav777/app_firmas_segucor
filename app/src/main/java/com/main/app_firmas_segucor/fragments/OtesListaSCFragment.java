package com.main.app_firmas_segucor.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.main.app_firmas_segucor.MainActivity;
import com.main.app_firmas_segucor.OtesSCActivity;
import com.main.app_firmas_segucor.R;
import com.main.app_firmas_segucor.adapters.OtesSCAdapter;
import com.main.app_firmas_segucor.config.ApiConf;
import com.main.app_firmas_segucor.config.CallInterface;
import com.main.app_firmas_segucor.config.db.data.LogosOtData;
import com.main.app_firmas_segucor.config.db.data.OTData;
import com.main.app_firmas_segucor.models.IDSolicitud;
import com.main.app_firmas_segucor.models.Logos;
import com.main.app_firmas_segucor.models.SolCotizacionOT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtesListaSCFragment extends Fragment {
    RecyclerView rv_otesoperarioslist;
    OtesSCAdapter otesRecyclerAdapter;
    List<SolCotizacionOT> solCotizacionList=new ArrayList<>();
    ProgressBar progressBar;
    CallInterface service;
    private ProgressDialog progressDialog;


    private LogosOtData logosData;
    private OTData otData;

    private List<IDSolicitud> listIDOtes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_otes_lista_s_c, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Listado OTES");
        int colorBanner= Color.parseColor("#1754EC");
        setActionBarColor(colorBanner);
        otData = new OTData(getContext());
        logosData = new LogosOtData(getContext());
        setViews(view);

        //rv_otesoperarioslist = view.findViewById(R.id.rv_otesoperarioslist);
        rv_otesoperarioslist.setLayoutManager(new LinearLayoutManager(getContext()));
        otesRecyclerAdapter=new OtesSCAdapter(getContext(),solCotizacionList);
        rv_otesoperarioslist.setAdapter(otesRecyclerAdapter);
        //progressBar=findViewById(R.id.progressBarOT);

        SharedPreferences pref = this.getActivity().getSharedPreferences("datos_operador", Context.MODE_PRIVATE);
        String empresa=pref.getString("empresa","");
        String operador=pref.getString("rut","");
        llenarDatosOteAPI(empresa,operador);
        otesRecyclerAdapter.setOnClickListener(new OtesSCAdapter.OnClickListener() {
            @Override
            public void onConformidad(View view, int position) {

            }

            @Override
            public void onCotizacion(View view, int position) throws Exception {
                SolCotizacionOT item = solCotizacionList.get(position);
                //progressDialog.setMessage("Abriendo Documento");//no actualizado
                //progressDialog.show();
                String nomDoc = UUID.randomUUID().toString() + ".pdf";
                File pdfFile = new File(getContext().getCacheDir(), nomDoc);
                logosData.open();
                Logos logoItem = logosData.getLogoPDF(""+item.getSolcotizacion());
                logosData.close();
                if (logoItem.getPdfcotizacion() != null) {

                    byte[] decodedString3 = Base64.decode(logoItem.getPdfcotizacion(), Base64.DEFAULT);
                    try {
                        // Initialize a pointer in file using OutputStream
                        OutputStream os = new FileOutputStream(pdfFile);
                        os.write(decodedString3);
                        os.flush();
                        os.close();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Uri uri = FileProvider.getUriForFile(getContext(), "com.main.app_firmas_segucor",
                                    new File(pdfFile.getPath()));
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(intent);
                        } else
                        {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(pdfFile.getPath()), "application/pdf");
                            intent = Intent.createChooser(intent, "Open File");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception: " + e);
                    }
                } else {
                    Toast.makeText(getContext(), "La OT no tiene una cotización relacionada", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(OtesSCActivity.this, "prueba", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSubir(View view, int position) {

            }
        });
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
       /* MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_solcot_ot,menu);
        return true;*/
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_solcot_ot, menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_salir_sol_cot:
                //Borrar datos de la session
                SharedPreferences preferencias = this.getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.remove("estado_usu");
                editor.apply();
                //Borrar datos del operario
                SharedPreferences pre_operario = this.getActivity().getSharedPreferences("datos_operador", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_op=pre_operario.edit();
                editor_op.remove("rut");
                editor_op.remove("dv");
                editor_op.remove("nombre");
                editor_op.remove("empresa");
                editor_op.apply();
                //fin de datos de la sesión
                Intent pantallaLogin = new Intent(requireActivity(), MainActivity.class);
                startActivity(pantallaLogin);
                //finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionBarColor(int parsedColor){
        ActionBar mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(parsedColor));
        //mActionBar.setDisplayShowTitleEnabled(false);
        //mActionBar.setDisplayShowTitleEnabled(true);
    }
    private void llenarDatosOteAPI(String empresaid, String rut){
        progressBar.setVisibility(View.VISIBLE);

        int empresa=Integer.parseInt(empresaid);
        int operador=Integer.parseInt(rut);
        Call<List<SolCotizacionOT>> call = ApiConf.getData().getOtes(empresa, operador);
        call.enqueue(new Callback<List<SolCotizacionOT>>() {
            @Override
            public void onResponse(Call<List<SolCotizacionOT>> call, Response<List<SolCotizacionOT>> response) {
                if(response.isSuccessful()){
                    otData.open();
                    List<SolCotizacionOT> result=response.body();
                    solCotizacionList.addAll(response.body());
                    otesRecyclerAdapter.notifyDataSetChanged();
                    try {
                        otData.borrarOtes();

                        for(SolCotizacionOT otes: result){
                            SolCotizacionOT item = new SolCotizacionOT();
                            int ot=otes.getOt();
                            item.setOt(otes.getOt());
                            item.setFecha_op(otes.getFecha_op());
                            item.setOperario(otes.getOperario());
                            item.setSolcotizacion(otes.getSolcotizacion());
                            //Deben guardarse los que son distintos de 0
                            item.setConformidad(otes.getConformidad());
                            otData.insertOt(""+item.getOt(),""+item.getSolcotizacion(),""+item.getFecha_op(),""+item.getConformidad());
                            listIDOtes.add( new IDSolicitud(otes.getOt()));

                            //SolCotizacionOT item=new SolCotizacionOT(ot,conformidad,operario,solcotizacion,fecha);
                            //solCotizacionList.add(item);
                        }
                        otData.close();
                        guardarLogoBd(listIDOtes);
                        progressBar.setVisibility(View.GONE);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    //rvServicesListAdapter.notifyItemInserted(solCotizacionList.size()-1);


                }
            }

            @Override
            public void onFailure(Call<List<SolCotizacionOT>> call, Throwable t) {
                otData.open();

                try {
                    List<SolCotizacionOT> otDataList = otData.getOtes();
                    for (SolCotizacionOT otes : otDataList) {
                        SolCotizacionOT item = new SolCotizacionOT();
                        String solcto=otes.getSolcotizacion();
                        String fec=otes.getFecha_op();
                        item.setOt(otes.getOt());
                        item.setSolcotizacion(otes.getSolcotizacion());
                        item.setFecha_op(otes.getFecha_op());
                        item.setConformidad(otes.getConformidad());
                        solCotizacionList.add(item);
                    }
                    otesRecyclerAdapter.notifyDataSetChanged();
                    //loadOtes();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                otData.close();
                progressBar.setVisibility(View.GONE);

                //Toast.makeText(OtesSCActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void guardarLogoBd(List<IDSolicitud> id_ot){
        service= ApiConf.postDatosFirmasService();
        Call<List<Logos>> call = ApiConf.getData().postDatosFirma(id_ot);
        call.enqueue(new Callback<List<Logos>>() {
            @Override
            public void onResponse(Call<List<Logos>> call, Response<List<Logos>> response) {
                logosData.open();
                try {
                    logosData.borrarLogos();
                    for(Logos post : response.body()) {
                        Logos item = new Logos();
                        item.setSc(post.getSc());
                        item.setIdempresa(post.getIdempresa());
                        item.setLogos(post.getLogos());
                        item.setFirmavb1oc(post.getFirmavb1oc());
                        item.setFirmavb2oc(post.getFirmavb2oc());
                        item.setFirmavb3oc(post.getFirmavb3oc());
                        item.setPdfcotizacion(post.getPdfcotizacion());
                        item.setDireccion(post.getDireccion());
                        item.setFecha(post.getFecha());
                        int idot=post.getOTid();
                        logosData.insertLogo(idot, item);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                logosData.close();
            }

            @Override
            public void onFailure(Call<List<Logos>> call, Throwable t) {
                Log.e("ERROR-->", t.toString());

            }
        });
    }
    void setViews(View view) {
        rv_otesoperarioslist = view.findViewById(R.id.rv_otesoperarioslist);
        progressBar=view.findViewById(R.id.progressBarOT);
    }

}