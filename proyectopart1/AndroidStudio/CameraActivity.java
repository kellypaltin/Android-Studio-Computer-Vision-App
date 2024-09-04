package com.example.project;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CameraActivity extends org.opencv.android.CameraActivity {
    String TAG = "CameraActivity";
    Mat mRgba;

    int frameHeight, frameWhit;
    private final List<Mat> framesList = new ArrayList<>();
    List<Mat> framesCopy = new ArrayList<>();
    private boolean capturingFrames = false;
    native long camara(long mat);
    native long filterOne(long mat);
    native long filterTwo(long mat);
    native long filterThree(long mat);

    native long filterFour(long mat);

    CameraBridgeViewBase cameraBridgeViewBase;
    ImageView take_photo_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        getPermission();
        cameraBridgeViewBase = findViewById(R.id.cameraView);

        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {
                frameHeight = height;
                frameWhit = width;

            }

            @Override
            public void onCameraViewStopped() {

            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                mRgba = inputFrame.rgba();
                if (capturingFrames) {
                    framesList.add(mRgba.clone());
                }
                return mRgba;
            }
        });

        if(OpenCVLoader.initDebug()){
            cameraBridgeViewBase.enableView();
        }

        take_photo_btn = findViewById(R.id.take_photo_btn);
        take_photo_btn.setOnClickListener(v -> startCapturingFramesForEightSeconds());
    }

    private void startCapturingFramesForEightSeconds() {
        capturingFrames = true;
        framesList.clear();
        new Handler().postDelayed(() -> {
            capturingFrames = false;
            synchronized (framesList) {
                framesCopy = new ArrayList<>(framesList);
            }
            framesCopy.add(mRgba);
            processing(framesCopy);
        }, 5000);
    }


    //Codigo del Procesamiento de la Imagen con C++
    private void processing(List<Mat> mat) {
        List<Mat> newListFrame = new ArrayList<>();
        List<Mat> newListFilterOne = new ArrayList<>();
        List<Mat> newListFilterTwo = new ArrayList<>();
        List<Mat> newListFilterThree = new ArrayList<>();
        List<Mat> newListFilterFour = new ArrayList<>();


        if (mat.isEmpty()) {
            Log.i(TAG, "Error no hay datos....");
        } else {
            for (int i = 0; i < mat.size(); i++) {
                long resultmatCa = camara(mat.get(i).getNativeObjAddr());
                Mat newFrameImg = new Mat(resultmatCa);
                newListFrame.add(newFrameImg);
            }

            for (int a = 0; a < mat.size(); a++) {
                long resultfilterOne = filterOne(mat.get(a).getNativeObjAddr());
                Mat newImgFilterOne = new Mat(resultfilterOne);
                newListFilterOne.add(newImgFilterOne);
            }

            for (int a = 0; a < mat.size(); a++) {
                long resultfilterTwo = filterTwo(mat.get(a).getNativeObjAddr());
                Mat newImgFilterTwo= new Mat(resultfilterTwo);
                newListFilterTwo.add(newImgFilterTwo);
            }

            for (int a = 0; a < newListFrame.size(); a++) {
                long resultfilterThree = filterThree(newListFrame.get(a).getNativeObjAddr());
                Mat newImgFilterThree = new Mat(resultfilterThree);
                newListFilterThree.add(newImgFilterThree);
            }

            for (int a = 0; a < newListFrame.size(); a++) {
                long resultfilterFour = filterFour(newListFrame.get(a).getNativeObjAddr());
                Mat newImgFilterFour = new Mat(resultfilterFour);
                newListFilterFour.add(newImgFilterFour);
            }


            saveFrame(newListFrame, "Camara");
            save(newListFilterOne,"FiltroOne");
            save(newListFilterTwo,"FiltroTwo");
            save(newListFilterThree,"FiltroThree");
            save(newListFilterFour,"FiltroFour");

            for (Mat frame : newListFrame) {
                frame.release();
            }
            for (Mat filter : newListFilterOne) {
                filter.release();
            }
            for (Mat filterTw : newListFilterTwo) {
                filterTw.release();
            }
            for (Mat filterThree: newListFilterThree) {
                filterThree.release();
            }
            for (Mat filterFour : newListFilterFour) {
                filterFour.release();
            }

        }
    }

    // Guardar imágenes
    private void saveFrame(List<Mat> mat, String name) {
        Intent intent = new Intent(this, MainActivity.class);
        File filter = new File(getExternalFilesDir(null), name);
        if (!filter.exists()) filter.mkdirs();
        File[] files = filter.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        for (int i = 0; i < mat.size(); i++) {
            String filename = "Frame" + i + ".jpg";
            File file = new File(filter, filename);
            boolean bool = Imgcodecs.imwrite(file.toString(), mat.get(i));
            if (bool) {
                Log.i(TAG, "Guardada las imagenes corretamente" + file);
                intent.putExtra("imagePath", file.getAbsolutePath());
            } else {
                Log.i(TAG, "Fail writing image to external storage");
            }
        }
        startActivity(intent);
    }

    private void save(List<Mat> mat, String name) {
        File filter = new File(getExternalFilesDir(null), name);
        if (!filter.exists()) filter.mkdirs();
        File[] files = filter.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        for (int i = 0; i < mat.size(); i++) {
            String filename = "Frame" + i + ".jpg";
            File file = new File(filter, filename);
            boolean bool = Imgcodecs.imwrite(file.toString(), mat.get(i));
            if (bool) {
                Log.i(TAG, "SUCCESS writing image to external storage ");
            } else {
                Log.i(TAG, "Fail writing image to external storage");
            }
            mat.get(i).release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }


    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }

    void getPermission(){
        if(checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{android.Manifest.permission.CAMERA},101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0]!= PackageManager.PERMISSION_GRANTED){
            getPermission();
        }
    }

}