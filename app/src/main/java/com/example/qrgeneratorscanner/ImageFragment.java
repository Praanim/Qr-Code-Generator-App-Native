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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.security.Permission;
import java.util.List;

import javax.xml.transform.Result;


public class ImageFragment extends Fragment {

   private  ImageView imageView;

   private TextView scannedTextView;

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

                    scanTheQrCode(imageBitmap);
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
        scannedTextView = view.findViewById(R.id.scanned_text);

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

    private void scanTheQrCode(Bitmap bitmap){
        // get Input image from bitmap
        InputImage inputImage = InputImage.fromBitmap(bitmap,0);

        // start the scanning process
        BarCodeScanningHelperClass.scanner.process(inputImage).addOnSuccessListener(
                new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        if(barcodes.isEmpty()){
                            Toast.makeText(getContext(), "No QR code found", Toast.LENGTH_SHORT).show();
                        }else{
                            handleBarCodeResults(barcodes);
                        }
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to scan image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleBarCodeResults(List<Barcode> barcodes){

        StringBuilder resultText = new StringBuilder();

        for (Barcode barcode : barcodes) {
            String rawValue = barcode.getRawValue();
            int valueType = barcode.getValueType();

            switch (valueType) {
                case Barcode.TYPE_WIFI:
                    String ssid = barcode.getWifi().getSsid();
                    String password = barcode.getWifi().getPassword();
                    int type = barcode.getWifi().getEncryptionType();
                    resultText.append("WiFi Info:\nSSID: ").append(ssid)
                            .append("\nPassword: ").append(password)
                            .append("\nEncryption Type: ").append(type).append("\n\n");
                    break;
                case Barcode.TYPE_URL:
                    String title = barcode.getUrl().getTitle();
                    String url = barcode.getUrl().getUrl();
                    resultText.append("URL Info:\nTitle: ").append(title)
                            .append("\nURL: ").append(url).append("\n\n");
                    break;
                case Barcode.TYPE_TEXT:
                    resultText.append("Text Info:\n").append(rawValue).append("\n\n");
                    break;
                // Add more cases as needed for other barcode types
                default:
                    resultText.append("Unknown barcode type: ").append(rawValue).append("\n\n");
                    break;
            }
        }

        // Update the TextView with the result
        scannedTextView.setText(resultText.toString());
    }


}