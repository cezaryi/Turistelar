package com.example.turistelar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turistelar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_login, button_cadastrar;
    private TextView textView_EsqueceuASenha;
    private EditText editText_UsuarioLogin, editText_SenhaLogin;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        button_login = (Button) findViewById(R.id.button_login);
        button_cadastrar = (Button) findViewById(R.id.button_cadastrar);

        editText_UsuarioLogin = (EditText) findViewById(R.id.editText_EmailLogin);
        editText_SenhaLogin = (EditText) findViewById(R.id.editText_SenhaLogin);
        textView_EsqueceuASenha = (TextView) findViewById(R.id.textView_EsqueceuASenha);

        button_login.setOnClickListener(this);
        button_cadastrar.setOnClickListener(this);
        textView_EsqueceuASenha.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        estadoAutenticacao();
        user = auth.getCurrentUser();
        if (user != null){
            startActivity(new Intent (this,PrincipalActivity.class));
        }

    }

    private void estadoAutenticacao(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(getBaseContext(),"usuário"+user.getEmail()+" está logado",Toast.LENGTH_LONG).show();
                }
            }
        };
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
                recuperarSenha();
                break;
        }
    }

    private  void recuperarSenha() {
        String email;
        email = editText_UsuarioLogin.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(getBaseContext(),"Insira o email para recuperar a senha.",
                    Toast.LENGTH_LONG).show();
        }else{
            enviarEmail(email );
        }

    }
   private void enviarEmail(String email){

       auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               Toast.makeText(getBaseContext(),"Enviamos um e-mail para você redefinir sua senha.",Toast.LENGTH_LONG).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               String resposta = e.toString();
               Util.opcoesErro(getBaseContext(),resposta);
           }
       });

   }
   private void loginEmail(){
        String email, senha;
        email = editText_UsuarioLogin.getText().toString().trim();
        senha = editText_SenhaLogin.getText().toString().trim();

        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(getBaseContext(),"Erro - Preencha os campos.",
                    Toast.LENGTH_LONG).show();
        }else{
            if(Util.verificarInternet(this)){
                ConnectivityManager conexao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
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
                   Util.opcoesErro(getBaseContext() , resposta);
                   //Toast.makeText(getBaseContext(),"Erro ao logar usuário",Toast.LENGTH_LONG).show();
               }
           }
       });
   }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
