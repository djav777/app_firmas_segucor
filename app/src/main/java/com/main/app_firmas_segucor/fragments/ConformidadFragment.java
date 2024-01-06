package com.main.app_firmas_segucor.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.main.app_firmas_segucor.R;
import com.main.app_firmas_segucor.adapters.FotoRecyclerAdapter;
import com.main.app_firmas_segucor.config.db.data.FirmaConformidadData;
import com.main.app_firmas_segucor.config.db.data.FotoConformidadData;
import com.main.app_firmas_segucor.config.db.data.LogosOtData;
import com.main.app_firmas_segucor.config.db.data.PdfConformidadData;
import com.main.app_firmas_segucor.itext.ConformidadDoc;
import com.main.app_firmas_segucor.models.FirmaConformidad;
import com.main.app_firmas_segucor.models.FotoConformidad;
import com.main.app_firmas_segucor.models.PdfConformidad;
import com.main.app_firmas_segucor.session.SessionKeys;
import com.main.app_firmas_segucor.session.SessionOperarios;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConformidadFragment extends Fragment {


    private EditText editComentario;
    private Button btnAgregaFirma, btnGeneraDoc, btnTomarFoto;
    private FirmaConformidadData firmaConformidadData;
    private FotoConformidadData fotoConformidadData;
    private SessionOperarios sessionOperarios;
    private FirmaConformidad firmaConformidad;
    private PdfConformidadData pdfConformidadData;
    private PdfConformidad pdfConformidad;
    private ConformidadDoc conformidadDoc, conformidadFrontalDoc;
    private Bitmap firmaRepresentante, firmaVendedor;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private TextView btnBorraComentario;
    private File[] allFiles;
    private File fileToPlay = null;
    private List<String> listPathFoto = new ArrayList<>();
    private List<FotoConformidad> listFoto = new ArrayList<>();
    private RecyclerView recyclerView;
    private FotoRecyclerAdapter fotoRecyclerAdapter;
    private LogosOtData logosOtData;

    public ConformidadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conformidad, container, false);
        //setViews(view);
        alertDialog = new AlertDialog.Builder(getContext()).create();
        progressDialog = new ProgressDialog(getContext());
        firmaConformidadData = new FirmaConformidadData(getContext());
        pdfConformidadData = new PdfConformidadData(getContext());
return  view;
    }


}