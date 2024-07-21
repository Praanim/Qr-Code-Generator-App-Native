package com.example.qrgeneratorscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            final int id = item.getItemId();

            // switch case alternative
            if(id == R.id.navigation_home){
                selectedFragment = new HomeFragment();
            }
            if(id == R.id.navigation_dashboard){
                selectedFragment = new ImageFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });



        bottomNavigationView.setSelectedItemId(R.id.navigation_home);


    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.bottom_layout_container,fragment);
        fragmentTransaction.commit();
    }
}
//        // get the image using its id
//        ImageView qrImageView = findViewById(R.id.qrcode_image_id);
//
//        // the text for which we want to generate qr
//        final String text = "Pranim is handsome hunk";
//
//        // generate the qr using qr code generator class
//        final Bitmap bitmap = QrCodeGenerator.generateQrCode(text);
//
//        if(bitmap == null){
//            Log.e("Null Value", "Null was returned");
//        }
//
//        qrImageView.setImageBitmap(bitmap);