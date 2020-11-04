package com.example.turistelar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turistelar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_login, button_cadastrar;
    private TextView textView_EsqueceuASenha;
    private EditText editText_UsuarioLogin, editText_SenhaLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button_login = (Button) findViewById(R.id.button_login);
        button_cadastrar = (Button) findViewById(R.id.button_cadastrar);

        editText_UsuarioLogin = (EditText) findViewById(R.id.editText_EmailLogin);
        editText_SenhaLogin = (EditText) findViewById(R.id.editText_SenhaLogin);
        textView_EsqueceuASenha = (TextView) findViewById(R.id.textView_EsqueceuASenha);

        button_login.setOnClickListener(this);
        button_cadastrar.setOnClickListener(this);
        textView_EsqueceuASenha.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login:
                loginEmail();
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
   private void loginEmail(){
        String email, senha;
        email = editText_UsuarioLogin.getText().toString().trim();
        senha = editText_SenhaLogin.getText().toString().trim();

        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(getBaseContext(),"Erro - Preencha os campos.",
                    Toast.LENGTH_LONG).show();
        }else{
            if(verificarInternet()){
                confirmarLoginEmail(email,senha);
            }else{
                Toast.makeText(getBaseContext(),"Erro - Verifique sua conexão",Toast.LENGTH_LONG).show();
            }

        }

   }

   private void confirmarLoginEmail(String email, String senha){
       auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   startActivity(new Intent(getBaseContext(),PrincipalActivity.class));
                   Toast.makeText(getBaseContext(),"usuario logado com sucesso",Toast.LENGTH_LONG).show();
                   finish();
               }
               else{
                   String resposta = task.getException().toString();
                   opcoesErro(resposta);
                   //Toast.makeText(getBaseContext(),"Erro ao logar usuário",Toast.LENGTH_LONG).show();
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
           else if (resposta.contains("interrupted connection")){
               Toast.makeText(getBaseContext(),"Sem conexão com o Firebase",Toast.LENGTH_LONG).show();
           }
           else if (resposta.contains("password is invalid")){
               Toast.makeText(getBaseContext(),"Senha inválida",Toast.LENGTH_LONG).show();
           }
           else if (resposta.contains("there is no user")){
               Toast.makeText(getBaseContext(),"E-mail não cadastrado",Toast.LENGTH_LONG).show();
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
