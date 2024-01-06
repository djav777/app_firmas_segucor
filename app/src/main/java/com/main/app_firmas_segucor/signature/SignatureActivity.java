package com.main.app_firmas_segucor.signature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
//import com.kyanogen.signatureview.SignatureView;
import com.main.app_firmas_segucor.MainActivity;
import com.main.app_firmas_segucor.R;
import com.main.app_firmas_segucor.camera.CameraXActivity;
import com.main.app_firmas_segucor.config.db.data.FirmaConformidadData;
import com.main.app_firmas_segucor.config.db.data.FotoConformidadData;
import com.main.app_firmas_segucor.models.FirmaConformidad;
import com.main.app_firmas_segucor.models.FotoConformidad;
import com.main.app_firmas_segucor.session.SessionKeys;
import com.main.app_firmas_segucor.session.SessionOperarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SignatureActivity extends AppCompatActivity {

    private EditText etNombreFirma;
    private Button btnFirma;
    private SignaturePad signatureView;
    private Context context;
    private File imgfile;
    private AlertDialog alertDialog;
    private SessionOperarios sessionOperarios;
    private FirmaConformidadData firmaConformidadData;
    private FirmaConformidad firmaConformidad;
    private FotoConformidadData fotoConformidadData;
    private List<FotoConformidad> listFoto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        getSupportActionBar().setTitle("Firmar");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        firmaConformidadData = new FirmaConformidadData(SignatureActivity.this);
        fotoConformidadData = new FotoConformidadData(SignatureActivity.this);
        sessionOperarios = new SessionOperarios(SignatureActivity.this);
        alertDialog = new AlertDialog.Builder(this).create();
        etNombreFirma = findViewById(R.id.et_nombre_firma);
        signatureView = findViewById(R.id.signature_view);
        btnFirma = findViewById(R.id.btn_firma);
        btnFirma.setOnClickListener(onClickFirma);
        //
        /*
        try {
            firmaConformidadData.open();
            //SE OBTIENE LOS DATOS DE FIRMA AL INICIAR CLASE
            if(firmaConformidad.getIdfirma() > 0){
                firmaConformidadData.deleteFirma(sessionOperarios.getRecord().get(SessionKeys.opOtId),
                        sessionOperarios.getRecord().get(SessionKeys.empresaId));
                File fdel = new File(firmaConformidad.getPathfirma());
                if(fdel.exists())
                    fdel.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            firmaConformidadData.close();
        }
        */
        //
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String url_path = extras.getString("doc_path");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = FileProvider.getUriForFile(SignatureActivity.this, "com.opencode.appsegucorfirmas",
                        new File(url_path));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url_path), "application/pdf");
                intent = Intent.createChooser(intent, "Open File");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    private View.OnClickListener onClickFirma = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
            if(!etNombreFirma.getText().toString().isEmpty()) {
                if (ActivityCompat.checkSelfPermission(SignatureActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SignatureActivity.this, "Debe habilitar permisos CAMARA para continuar..", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", SignatureActivity.this.getPackageName(),
                            null);
                    intent.setData(uri);
                    SignatureActivity.this.startActivity(intent);
                } else {
                    //

                }
                //
                guardarFirma();
            }else{
                Toast.makeText(SignatureActivity.this, "falta nombre de quien firma", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_guarda:
                return true;
            case R.id.action_limpiar:
                signatureView.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void guardarFirma(){
        File folder= new File(this.getFilesDir() + "/firmas");
        if(!folder.exists()){
            folder.mkdirs();
        }
        String nomDoc = UUID.randomUUID().toString()+".png";
        imgfile = new File (folder+"/"+nomDoc);
        //
        FileOutputStream out = null;
        Bitmap bitmap = signatureView.getSignatureBitmap();
        try {
            out = new FileOutputStream(imgfile);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                    if (bitmap != null) {
                        Toast.makeText(getApplicationContext(),
                                "Firma Guardada",
                                Toast.LENGTH_LONG).show();
                        try {
                            firmaConformidadData.open();
                            //SE OBTIENE LOS DATOS DE FIRMA AL INICIAR CLASE

                            firmaConformidadData.insertFirma(sessionOperarios.getRecord().get(SessionKeys.opOtId),
                                    sessionOperarios.getRecord().get(SessionKeys.opRut),
                                    etNombreFirma.getText().toString(),
                                    sessionOperarios.getRecord().get(SessionKeys.empresaId),
                                    imgfile.toString());

                            Intent intent = new Intent(SignatureActivity.this, MainActivity.class);
                            intent.putExtra("ret_firma", "1");
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            firmaConformidadData.close();
                            Intent intent = new Intent(SignatureActivity.this, CameraXActivity.class);
                            intent.putExtra("camara_frontal", "2");
                            startActivity(intent);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}