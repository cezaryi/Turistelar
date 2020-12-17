package com.example.turistelar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.turistelar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{
    private BottomNavigationView bottomNavigationView;
    private Button button_Deslogar;
    private FirebaseAuth auth;
    private EditText editText_email;
    private EditText editText_nome;
    private Button button_Salvar;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.perfil);
        button_Deslogar = findViewById(R.id.button_Deslogar);
        button_Deslogar.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case  R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                PrincipalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.map:
                        startActivity(new Intent(getApplicationContext(),
                                MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.perfil:
                        return true;

                }

                return false;
            }
        });


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_Deslogar:
                auth.signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
                break;
            case R.id.button_database_salvar:
                updateEmail(user.getEmail());
                break;
        }
    }

    private void updateEmail(String email){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user != null){
            Toast.makeText(getBaseContext(),"usuário"+user.getEmail()+" está logado",Toast.LENGTH_LONG).show();
        }
    }
}