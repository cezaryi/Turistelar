package com.example.turistelar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.turistelar.R;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_Deslogar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        button_Deslogar = findViewById(R.id.button_Deslogar);
        button_Deslogar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

}
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
