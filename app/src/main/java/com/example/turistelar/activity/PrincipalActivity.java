package com.example.turistelar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.turistelar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_Deslogar;
    private FirebaseAuth auth;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        button_Deslogar = findViewById(R.id.button_Deslogar);
        button_Deslogar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

}

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.home:
                            fragment = new HomeFragment();
                            break;

                        case R.id.search:
                            fragment = new SearchFragment();
                            break;

                        case R.id.map:
                            fragment = new MapFragment();
                            break;

                        case R.id.parceria:
                            fragment = new ParceriasFragment();
                            break;

                        case R.id.perfil:
                            fragment = new PerfilFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    return true;
                }
            };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_Deslogar:
                auth.signOut();
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
                break;
        }
    }
}
