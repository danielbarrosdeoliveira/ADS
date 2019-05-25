package br.com.danieloliveira.studentshare.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Classe que será utilizada para lidar com o Firebase
// Tanto o Banco de Dados como autenticação

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;

    // Obtendo a instancia do Banco de dados;
    public static DatabaseReference getBancodeDados(){

        if(referenciaFirebase==null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    // Obtendo a instancia da Autenticação
    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao==null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}


