package com.example.qrgeneratorscanner;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;


import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class QrCodeFragment extends Fragment {

    private static final String ARG_TEXT = "qr_code_text";
    private String text;


    public static QrCodeFragment newInstance(String text) {
        QrCodeFragment fragment = new QrCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_qr_code, container, false);

        final ImageView imageView = view.findViewById(R.id.qrcode_image_id);
        final Button shareQrBtn = view.findViewById(R.id.share_qr_btn);

        // generate qr code
        final Bitmap bitmap = QrCodeGenerator.generateQrCode(text);

        // Set qr code Bitmap image
        imageView.setImageBitmap(bitmap);

        // on share btn clicked event listener
        shareQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    // save bitmap to cache directory
                    File cachePath = new File(getContext().getCacheDir(),"images");
                    cachePath.mkdirs();
                    File file = new File(cachePath,"qr_code.png");

                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                    fileOutputStream.close();

                    // Get the uri for file using file provider
                    Uri contentUri = FileProvider.getUriForFile(getContext(),
                            "com.example.qrgeneratorscanner.provider",
                            file
                    );

                    // create sharing intent
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("image/png");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    //start Activity
                    startActivity(shareIntent);
                }catch (FileNotFoundException e){
                    Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
                    Log.e("File not found",e.toString());
                }catch (IOException e){
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return  view;

    }
}