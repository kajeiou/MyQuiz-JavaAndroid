package my.quiz;

import static com.google.firebase.auth.FirebaseAuth.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText user,mdp;
    Button valider;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.utilisateur);
        mdp = findViewById(R.id.motdepasse);
        valider = findViewById(R.id.valider);
        valider.setEnabled(false);

        //Lors de la saisie d'un MDP activer le bouton
        mdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                valider.setEnabled(!editable.toString().isEmpty());
            }
        });
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void login() {
        String utilisateur = user.getText().toString();
        String motdepasse = mdp.getText().toString();
        if(utilisateur =="" && motdepasse=="") {
            Toast.makeText(getApplicationContext(), "Les champs ne doivent pas être vide.", Toast.LENGTH_LONG).show();
        }
        else {

            mAuth.signInWithEmailAndPassword(utilisateur, motdepasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "Connexion réussis en tant que "+ utilisateur, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Identifiant ou mot de passe incorrecte, veuillez reessayer.", Toast.LENGTH_LONG).show();

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(getApplicationContext(), "Identifiant incorrecte", Toast.LENGTH_LONG).show();

                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(getApplicationContext(), "Mot de passe incorrecte" + user, Toast.LENGTH_LONG).show();
                        } catch (FirebaseNetworkException e) {
                            Toast.makeText(getApplicationContext(), "Erreur de connexion à Internet",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Erreur Exception :  " + e,Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(), "signInWithEmail:échoué Exception : " + task.getException(),Toast.LENGTH_LONG).show();

                    }


                }
            });
        }

    }
}