package com.example.turistelar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private Button button_CadastrarCadastro, button_CancelarCadastro;
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
        button_CancelarCadastro = findViewById(R.id.button_CancelarCadastro);

        button_CadastrarCadastro.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_CadastrarCadastro:
                cadastrar();
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
                    if(verificarInternet()){
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
            }
            else{
                String resposta = task.getException().toString();
                opcoesErro(resposta);
            }
        }
        });
    }

    private void opcoesErro(String resposta){
        if (resposta.contains("least 6 characters")){

            Toast.makeText(getBaseContext(),"Digite uma senha maior que 5 caracteres",Toast.LENGTH_LONG).show();

        }
        else if (resposta.contains("address is badly")){
            Toast.makeText(getBaseContext(),"E-mail inválido",Toast.LENGTH_LONG).show();
        }
        else if (resposta.contains("address is already")){
            Toast.makeText(getBaseContext(),"E-mail já cadastrado",Toast.LENGTH_LONG).show();
        }
        else if (resposta.contains("interrupted connection")){
            Toast.makeText(getBaseContext(),"Sem conexão com o Firebase",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getBaseContext(),resposta,Toast.LENGTH_LONG).show();
        }


    }

    private boolean verificarInternet(){
        ConnectivityManager conexao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo informacao = conexao.getActiveNetworkInfo();
        if( informacao != null && informacao.isConnected() ){
            return true;
        }
        else{
            return false;
        }
    }

}
