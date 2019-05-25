package br.com.danieloliveira.studentshare.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.danieloliveira.studentshare.R;
import br.com.danieloliveira.studentshare.config.ConfiguracaoFirebase;
import br.com.danieloliveira.studentshare.helper.Base64Custom;
import br.com.danieloliveira.studentshare.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    // Declaração de variáveis dos componentes
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        //Recuperando os componentes com as variaveis
        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // o código abaixo verificar se os campos estão preenchidos
                if(
                    nome.getText().length() > 0 &&
                    email.getText().length() >0 &&
                    senha.getText().length() >0
                ){

                    // Instanciando o objeto Usuário
                    usuario = new Usuario();

                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());

                    cadastrarUsuario();
                }else{
                    Toast.makeText(CadastroUsuarioActivity.this, "ATENÇÃO: Todos os campos devem ser preenchidos!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // Método que realiza o cadastro do usuário no Firebase
    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(

                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    // Exibe mensagem ao usuário informando que o Cadastro foi realizado
                    Toast.makeText(CadastroUsuarioActivity.this, "Usuário Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();

                    // Obtendo resultado do cadastro do Usuário pelo Firebase,
                    FirebaseUser dadosUsuario = task.getResult().getUser();

                    // pega o email digitado pelo usuário, codifica em uma String em Base64, em seguida
                    // define a String em Base64 como ID no Firebase
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);

                    usuario.CadastrarUsuario();

                   abrirLoginUsuario();
                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "A senha deve conter mais de 8 caracteres, incluindo letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O e-mail digitado é inválido, tente outro e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Esse e-mail já está em uso, utilize outro e-mail";
                    } catch (Exception e) {
                        erroExcecao = "Não foi possível realizar seu cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
