package com.example.turistelar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.turistelar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.security.Principal;

public class PrincipalActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);



        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case  R.id.home:
                        return true;

                    case  R.id.map:
                        startActivity(new Intent(getApplicationContext(),
                                MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.perfil:
                        startActivity(new Intent(getApplicationContext(),
                                PerfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });





}




}
