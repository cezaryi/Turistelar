package com.example.turistelar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.turistelar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastrarActivity extends AppCompatActivity  implements View.OnClickListener {
    private EditText editText_nomeUsuario, editText_Email, editText_Senha,editText_repetirSenha;
    private Button button_CadastrarCadastro, button_VoltarCadastro;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editText_nomeUsuario = (EditText) findViewById(R.id.editText_NomeUsuarioCadastro);
        editText_Email = (EditText) findViewById(R.id.editText_EmailCadastro);
        editText_Senha = (EditText) findViewById(R.id.editText_SenhaCadastro);
        editText_repetirSenha = (EditText) findViewById(R.id.editText_RepetirSenhaCadastro);

        button_CadastrarCadastro = findViewById(R.id.button_CadastrarCadastro);
        button_VoltarCadastro = findViewById(R.id.button_VoltarCadastro);



        button_CadastrarCadastro.setOnClickListener(this);
        button_VoltarCadastro.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_CadastrarCadastro:
                cadastrar();
                break;
            case R.id.button_VoltarCadastro:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void cadastrar(){
        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();
        String confirmaSenha = editText_repetirSenha.getText().toString().trim();

        if(email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()){
            Toast.makeText(getBaseContext(),"Erro - Preencha os campos.",
                    Toast.LENGTH_LONG).show();
        }else{
            if(senha.contentEquals(confirmaSenha) ){
                    if(Util.verificarInternet(this)){
                        criarUsuario(email,senha);
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Erro - Verifique sua conexão",Toast.LENGTH_LONG).show();
                    }

            }else{

                Toast.makeText(getBaseContext(),"Erro - Senhas diferentes",Toast.LENGTH_LONG).show();
            }
        }
    }


    public void criarUsuario(String email, String senha){
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful()){
                Toast.makeText(getBaseContext(),"Cadastro efetuado com sucesso.",Toast.LENGTH_LONG).show();
                auth.signOut();
            }
            else{
                String resposta = task.getException().toString();
                Util.opcoesErro(getBaseContext(),resposta);
            }
        }
        });
    }




}
