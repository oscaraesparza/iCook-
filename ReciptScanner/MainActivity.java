/*
Paul Figueroa
Text scanner applet for iCook prior to integration
*/
 package com.example.peanutbutterpaul.textscan;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.google.android.gms.vision.CameraSource.*;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    String fileName="receipt",string1;
    final int RequestCameraPermissionID = 1001;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    File file = new File(path,fileName);

    public MainActivity() throws FileNotFoundException {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case RequestCameraPermissionID:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);
        final Button takePicButt = (Button) findViewById(R.id.picButt);


        Toast.makeText(getBaseContext(), path.toString(),Toast.LENGTH_SHORT).show();
        ///////////////

        final CameraSource.ShutterCallback shutter = new ShutterCallback() {
            @Override
            public void onShutter() {
            }
        };
        final CameraSource.PictureCallback pcb = new PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes) {
            }
        };
        ///////////////

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {                                                    //if textRecognizer works
            cameraSource = new Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(30.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                           ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    }catch(IOException e){e.printStackTrace();}



                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size()!=0)
                    {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i=0;i<items.size();++i)
                                {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                textView.setText(stringBuilder.toString());
                                string1=stringBuilder.toString();
                            }
                        });
                    }
                }
            });

///////////capture text///////////

            takePicButt.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v1) {
                            //cameraSource.takePicture(shutter,pcb); // takes pic
                            ///save to file here
                                // add-write text into file
                            try {
                                //display file saved message
                                FileOutputStream stream = new FileOutputStream(file);
                                try {
                                    stream.write(string1.getBytes());
                                } finally {
                                    stream.close();
                                }
                                Toast.makeText(getBaseContext(), "Button Pressed!",
                                        Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }




                    }
            );
///////////capture text///////////
        }
    }
}
