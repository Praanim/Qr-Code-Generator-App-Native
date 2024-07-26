package com.example.qrgeneratorscanner;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        final EditText editText = view.findViewById(R.id.editText);
        final Button btn = view.findViewById(R.id.gen_qr_btn);

        // generate qr
        btn.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();

            if(text.length() == 0){
                Toast.makeText(view.getContext(), "Your text is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new fragment and pass text as argument
            QrCodeFragment qrCodeFragment = QrCodeFragment.newInstance(text);

            // navigate to qr code screen
            navigateToQrImageScreen(qrCodeFragment);

        });

        // Inflate the layout for this fragment
        return view;


    }

    private void navigateToQrImageScreen(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bottom_layout_container, fragment);
        transaction.addToBackStack(null); // Add to back stack to allow navigation back
        transaction.commit();
    }
}