package com.example.apache.camera;


import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity implements SurfaceHolder.Callback,
        View.OnClickListener, Camera.PictureCallback,
        Camera.PreviewCallback, Camera.AutoFocusCallback {

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    Button takeImage;
    SurfaceView image;

    static final int CAM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        image = (SurfaceView) findViewById(R.id.surfaceView);

        surfaceHolder = image.getHolder();
        surfaceHolder.addCallback((SurfaceHolder.Callback) MainActivity.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        takeImage = (Button) findViewById(R.id.button);
        takeImage.setOnClickListener((View.OnClickListener) MainActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback((Camera.PreviewCallback) MainActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;

        int previewSurfaceWidth = image.getWidth();
        int previewSurfaceHeight = image.getHeight();

        LayoutParams lp = image.getLayoutParams();

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
            lp.height = previewSurfaceHeight;
            lp.width = (int) (previewSurfaceHeight / aspect);
            ;
        } else {
            camera.setDisplayOrientation(0);
            lp.width = previewSurfaceWidth;
            lp.height = (int) (previewSurfaceWidth / aspect);
        }

        image.setLayoutParams(lp);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onClick(View v) {
        try {
            if (v == takeImage) {
                camera.takePicture(null, null, null, this);
                Toast.makeText(getApplicationContext(), "Foto made!!!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.d("Oshibka", e.toString());
            Toast.makeText(getApplicationContext(), "Exception!!!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {
        try {
            File saveDir = new File("/sdcard/Camera_App/");

            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            FileOutputStream os = new FileOutputStream(String.format("/sdcard/Camera_App/%d.jpg", System.currentTimeMillis()));
            os.write(paramArrayOfByte);
            os.close();
        } catch (Exception e) {
        }
        paramCamera.startPreview();
    }

    @Override
    public void onAutoFocus(boolean paramBoolean, Camera paramCamera) {
        if (paramBoolean) {
            paramCamera.takePicture(null, null, null, this);
        }
    }

    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {

    }
}
