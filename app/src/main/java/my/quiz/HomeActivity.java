package my.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import my.quiz.classes.GameClass;
import my.quiz.classes.UserClass;

public class HomeActivity extends AppCompatActivity {
    private ListView listGamesPlayed;
    TextView loginSuccessful;
    Button beginQuiz, logout;
    FirebaseAuth mAuth;
    UserClass user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userF = mAuth.getCurrentUser();

        beginQuiz = findViewById(R.id.beginQuiz);
        logout = findViewById(R.id.logout);
        listGamesPlayed = findViewById(R.id.listFriends);
        loginSuccessful = findViewById(R.id.loginSuccessful);

        ArrayList<String> listG = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, listG);
        listGamesPlayed.setAdapter(adapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Vous vous êtes déconnecté avec succès", Toast.LENGTH_SHORT).show();
            }
        });
        beginQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance("https://myquiz-9cc5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listG.clear();
                for(DataSnapshot snapshotUser : snapshot.getChildren()) {
                    String email = snapshotUser.child("email").getValue().toString();
                    String name = snapshotUser.child("name").getValue().toString();
                    String password = snapshotUser.child("password").getValue().toString();

                    if(email.equals(userF.getEmail())) {
                        user = new UserClass(name, email, password );

                        for(DataSnapshot snapshotGame : snapshotUser.child("gamesPlayed").getChildren()) {
                            String theme = snapshotGame.child("theme").getValue().toString();
                            String scored = snapshotGame.child("score").getValue().toString();
                            int score = Integer.parseInt(scored);
                            user.addGame(theme,score);
                            listG.add(score+"");
                        }
                    }
                }

                loginSuccessful.setText("Joueur " + user.getName()+ " (" + user.getEmail() +")");
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}