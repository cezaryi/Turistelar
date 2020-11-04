package com.example.turistelar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turistelar.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_login, button_cadastrar;
    private TextView textView_EsqueceuASenha;
    private EditText editText_UsuarioLogin, editText_SenhaLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button_login = (Button) findViewById(R.id.button_login);
        button_cadastrar = (Button) findViewById(R.id.button_cadastrar);

        editText_UsuarioLogin = (EditText) findViewById(R.id.editText_EmailLogin);
        editText_SenhaLogin = (EditText) findViewById(R.id.editText_EmailLogin);
        textView_EsqueceuASenha = (TextView) findViewById(R.id.textView_EsqueceuASenha);

        button_login.setOnClickListener(this);
        button_cadastrar.setOnClickListener(this);
        textView_EsqueceuASenha.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login:

                break;
            case R.id.button_cadastrar:
                Intent intent = new Intent(this,CadastrarActivity.class);
                startActivity(intent);
                break;
            case R.id.textView_EsqueceuASenha:
                Toast.makeText(this,"Recuperar clicado", Toast.LENGTH_LONG).show();
                break;
        }
    }
}