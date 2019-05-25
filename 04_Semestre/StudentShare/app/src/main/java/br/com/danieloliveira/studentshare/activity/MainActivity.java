package br.com.danieloliveira.studentshare.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import br.com.danieloliveira.studentshare.R;
import br.com.danieloliveira.studentshare.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    // Declaração das variáveis para uso nos componentes.
    private Toolbar toolbar;

    // Variável de auxílio para o Firebase Athentication
    private FirebaseAuth usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciando o objeto ConfiguracaoFirebase
        usuarioLogado = ConfiguracaoFirebase.getFirebaseAutenticacao();

        // Vinculando as variaveis aos componentes
        toolbar = findViewById(R.id.toolbarMainActivity);

        // Definindo o título para a Toolbar
        toolbar.setTitle("Meus Projetos");
        setSupportActionBar(toolbar);

    }

    // Método que inicia e cria o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    // Método para trabalhar com o Menu, de acorodo com o que é selecionado.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Obtem do menu qual item foi selecionado

        switch(item.getItemId()){
            case R.id.itemSair:
                deslogarUsuario();
                return true;
            case R.id.itemNovoProjeto:

                return true;
            case R.id.itemAdicionarUsuario:
                abrirAdicionarUsuarioProjeto();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    // Método que será responsável por receber o e-mail do usuario para adicionar ao projeto
    private void abrirAdicionarUsuarioProjeto(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        //Configuração do Dialog
        alertDialog.setTitle("Novo Usuário");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        // Criando a caixa de diálogo para inserção do e-mail
        EditText recebeEmail = new EditText(MainActivity.this);
        alertDialog.setView(recebeEmail);

        

        // configurações do botão
        alertDialog.setPositiveButton("Inserir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    // Método para deslogar o Usuário
    public void deslogarUsuario(){
        usuarioLogado.signOut();
        Intent voltarTelaPrincipal = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(voltarTelaPrincipal);
        finish();
    }

    // Método no qual será utilizado para cadastrar projetos;

}
