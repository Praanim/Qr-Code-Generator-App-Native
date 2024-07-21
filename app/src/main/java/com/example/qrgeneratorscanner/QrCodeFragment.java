package com.example.qrgeneratorscanner;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        // generate qr code
        final Bitmap bitmap = QrCodeGenerator.generateQrCode(text);

        // Set qr code Bitmap image
        imageView.setImageBitmap(bitmap);

        return  view;

    }
}