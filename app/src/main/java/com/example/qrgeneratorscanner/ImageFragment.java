package com.example.qrgeneratorscanner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;
import android.widget.Toast;

import java.security.Permission;

import javax.xml.transform.Result;


public class ImageFragment extends Fragment {

   private  ImageView imageView;
    private  static final int RESULT_OK =-1;

    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
      new ActivityResultContracts.RequestPermission(),
      isGranted ->{
          if(isGranted){
              dispatchCameraIntent();
          }else{
              Toast.makeText(getContext(), "Camera permission is required to take a picture", Toast.LENGTH_SHORT).show();
          }
      }
    );

    private final  ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
            result->{
                if(result.getResultCode() == RESULT_OK){
                    Intent data = result.getData();
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }
            }
    );


    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        //find image view & button
         imageView = view.findViewById(R.id.camera_uploaded_pic);
        final Button openCameraBtn = view.findViewById(R.id.upload_from_camera);

        //open the camera
        openCameraBtn.setOnClickListener(v ->
        {
            try{
                if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                }else{
                    dispatchCameraIntent();
                }
            }catch (Exception e){
                Log.e("Open Camera Issue", "onCreateIntent: " + e);
            }

        });

        return view;
    }

    private void dispatchCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager() ) != null){
            takePictureLauncher.launch(takePictureIntent);
        }
    }



}