package br.com.danieloliveira.studentshare.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import br.com.danieloliveira.studentshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import br.com.danieloliveira.studentshare.config.ConfiguracaoFirebase;
import br.com.danieloliveira.studentshare.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextView cadastro;
    private EditText email;
    private EditText senha;
    private Button   botaoEntrar;

    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email       = findViewById(R.id.editLoginEmail);
        senha       = findViewById(R.id.editLoginSenha);
        botaoEntrar = findViewById(R.id.botaoEntrar);
        cadastro    = findViewById(R.id.textCadastro);

        // Configurando a ação do botão entrar
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // caso os campos de email e senha estejam vazios o app, solicita ao usuário que preencha os campos
                if(email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Campos vazios não são aceitos, favor preencha os campos de e-mail e senha!", Toast.LENGTH_LONG).show();
                }else {

                    // Instanciando a classe Usuario
                    usuario = new Usuario();

                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());

                    // Chama o método de validação de usuário
                    validarLogin();
                }
            }
        });


    }

    public void abrirCadastroUsuario(View view) {
        Intent cadastroUsuario = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(cadastroUsuario);
    }

    // Método responsável por realizar a conexão com Firebase e verificar se o e-mail e senha digitados estão corretos.
    public void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
            usuario.getEmail(),
            usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                String mensagemDeErro = "";
                if(task.isSuccessful()) {
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        mensagemDeErro = "E-mail não encontrado ou desabilitado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        mensagemDeErro = "Senha não corresponde com e-mail, favor tente novamente!";
                    } catch (Exception e) {
                        mensagemDeErro = "na tentativa de realizar o login!";
                    }
                    Toast.makeText(LoginActivity.this, "ERRO: " + mensagemDeErro, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void abrirTelaPrincipal(){
        Intent telaPrincipal = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(telaPrincipal);
    }
}
