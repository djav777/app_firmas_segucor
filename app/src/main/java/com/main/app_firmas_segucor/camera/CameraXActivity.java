package com.main.app_firmas_segucor.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.common.util.concurrent.ListenableFuture;
import com.main.app_firmas_segucor.PrincipalActivity;
import com.main.app_firmas_segucor.R;
import com.main.app_firmas_segucor.config.db.data.FotoConformidadData;
import com.main.app_firmas_segucor.models.FotoConformidad;
import com.main.app_firmas_segucor.session.SessionKeys;
import com.main.app_firmas_segucor.session.SessionOperarios;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraXActivity extends AppCompatActivity {
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS=1001; // , rotation=0
    //private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    private AlertDialog alertDialog;
    private PreviewView previewView;
    private Button btnCaptureImage;
    private OrientationEventListener myOrientationEventListener;
    private FotoConformidadData fotoConformidadData;
    private FotoConformidad fotoConformidad;
    private SessionOperarios sessionOperarios;
    private List<FotoConformidad> listFoto = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LinearLayout linearCamaraIndiscreta;
    private TextView txtOrientation;
    private CameraSelector cameraSelector;
    private ImageAnalysis imageAnalysis;
    private ImageCapture imageCapture;
    private Preview preview;
    private Camera camera;
    private int rotation = 0;
    private Bundle extras;
    private Button btnatras;
    CameraControl cameraControl;
    CameraInfo cameraInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_xactivity);
        getSupportActionBar().hide();
        txtOrientation = findViewById(R.id.orientation);
        fotoConformidadData = new FotoConformidadData(CameraXActivity.this);
        sessionOperarios = new SessionOperarios(CameraXActivity.this);
        progressDialog = new ProgressDialog(CameraXActivity.this);
        previewView = findViewById(R.id.previewView);
        btnCaptureImage = findViewById(R.id.btn_captura_foto);
        btnatras = findViewById(R.id.btn_atras);
        linearCamaraIndiscreta = findViewById(R.id.linear_camara_indiscreta);
        alertDialog = new AlertDialog.Builder(CameraXActivity.this).create();
        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        startCamera();
    }

    private void startCamera(){
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(CameraXActivity.this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(CameraXActivity.this));
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private Bitmap imageToBitmap(Image image){
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
        return bitmap;
    }

    @SuppressLint("RestrictedApi")
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider){
        //
        extras = getIntent().getExtras();

        boolean camaraFrontal = false;
        if (getIntent().hasExtra("camara_frontal")) {
            if("2".equals(getIntent().getStringExtra("camara_frontal"))){
                camaraFrontal = true;
            }
        }

        if(camaraFrontal != true) {
            btnCaptureImage.setVisibility(View.VISIBLE);
            cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build();
        }else {
            cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
        }

        ImageCapture.Builder builder = new ImageCapture.Builder();

        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        if(hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)){
            hdrImageCaptureExtender.enableExtension(cameraSelector);
        }

        imageAnalysis = new ImageAnalysis.Builder().build();

        preview = new Preview.Builder()
                .build();

        imageCapture = new ImageCapture.Builder()
                .setMaxResolution(new Size(800, 600))
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        //int rotation = 0;
        OrientationEventListener orientationEventListener = new OrientationEventListener(CameraXActivity.this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                // Monitors orientation values to determine the target rotation value
                //int rotation = 0;
                if (orientation >= 45 && orientation < 135) {
                    //rotation = Surface.ROTATION_270;
                    rotation = 180;

                } else if (orientation >= 135 && orientation < 270) {
                    //rotation = Surface.ROTATION_180; //posicion izq
                    rotation = 360;

                } else if (orientation >= 225 && orientation < 370) {
                    //rotation = Surface.ROTATION_90;
                    rotation = 90;

                } else {
                    //rotation = Surface.ROTATION_0;
                    rotation = 0;
                }

                //imageAnalysis.setTargetRotation(rotation);
                //imageCapture.setTargetRotation(rotation);
                //txtOrientation.setText(""+orientation);
                //Log.e("ORIENTATION---->", ""+orientation);
            }
        };
        orientationEventListener.enable();

        camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageAnalysis, imageCapture);

        preview.setSurfaceProvider(previewView.createSurfaceProvider());
        //imageCapture.setTargetRotation(rotation);
        /** CAPTURA IMAGEN INDISCRETA CAMARA FRONTAL*/
        if(camaraFrontal) {
            //AL ENTRAR EN MODO FRONTAL SE LEVANTA LINEAR PARA TAPAR EL SURFACE DE LA CAMARA
            linearCamaraIndiscreta.setVisibility(View.VISIBLE);
            progressDialog.setMessage("Guardando firma..");
            progressDialog.show();
            //guardar archivo
            File folder= new File(getFilesDir() + "/fotos");
            if(!folder.exists())
                folder.mkdirs();
            String nomFoto = UUID.randomUUID().toString()+".jpg";
            final File file = new File (folder+"/"+nomFoto);
            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
            imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                fotoConformidadData.open();
                                fotoConformidad = fotoConformidadData.getFotoFrontalOt(sessionOperarios.getRecord().get(SessionKeys.opOtId),
                                        sessionOperarios.getRecord().get(SessionKeys.empresaId));
                                if (fotoConformidad.getIdFoto() > 0) {
                                    fotoConformidadData.deleteFotoId(String.valueOf(fotoConformidad.getIdFoto()),
                                            sessionOperarios.getRecord().get(SessionKeys.opOtId),
                                            sessionOperarios.getRecord().get(SessionKeys.empresaId));
                                    File fdel = new File(fotoConformidad.getPathFoto());
                                    if (fdel.exists())
                                        fdel.delete();
                                }
                                fotoConformidadData.insertFotoFrontal(sessionOperarios.getRecord().get(SessionKeys.opOtId),
                                        sessionOperarios.getRecord().get(SessionKeys.empresaId),
                                        file.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                fotoConformidadData.close();
                            }
                            //

                            finish();
                        }
                    });
                }

                @Override
                public void onError(@NonNull ImageCaptureException error) {
                    error.printStackTrace();
                }
            });

        }
        /** CAPTURA IMAGEN CAMARA TRASERA*/
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File folder= new File(getFilesDir() + "/fotos");
                if(!folder.exists())
                    folder.mkdirs();
                String nomFoto = UUID.randomUUID().toString()+".jpg";
                final File file2 = new File (folder+"/"+nomFoto);
                //
                imageCapture.takePicture(executor, new ImageCapture.OnImageCapturedCallback() {
                    @SuppressLint("UnsafeExperimentalUsageError")
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy image) {
                        shootSound();
                        @SuppressLint("UnsafeOptInUsageError") Bitmap imageBitmap = rotateImage(imageToBitmap(Objects.requireNonNull(image.getImage())), rotation);
                        //txtOrientation.setText(""+ rotation);
                        try {
                            OutputStream fos = new FileOutputStream(file2);
                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            Objects.requireNonNull(fos).close();

                            fotoConformidadData.open();
                            fotoConformidadData.insertFoto(sessionOperarios.getRecord().get(SessionKeys.opOtId),
                                    sessionOperarios.getRecord().get(SessionKeys.empresaId),
                                    file2.toString());

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            fotoConformidadData.close();
                        }
                        super.onCaptureSuccess(image);
                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        super.onError(exception);
                    }
                });



            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CameraXActivity.this, PrincipalActivity.class);
        intent.putExtra("ret_foto", "0");
        startActivity(intent);
        finish();
    }

    public void shootSound() {
        MediaActionSound sound = new MediaActionSound();
        sound.play(MediaActionSound.SHUTTER_CLICK);
    }


}