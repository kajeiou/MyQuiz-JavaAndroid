package my.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

import my.quiz.classes.QuestionClass;
import my.quiz.classes.ThemeClass;
import my.quiz.classes.UserClass;

public class QuizActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private ListView listThemes;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userF = mAuth.getCurrentUser();
        Intent intent = getIntent();
        UserClass user = (UserClass) intent.getSerializableExtra("user");
        listThemes = findViewById(R.id.listThemes);
        cancel = findViewById(R.id.cancel);
        ArrayList<ThemeClass> themes = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listThemes.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance("https://myquiz-9cc5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Themes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ThemeClass theme =  new ThemeClass(snapshot.child("theme").getValue().toString());

                    for(DataSnapshot snapshotQuestion : snapshot.child("questions").getChildren()) {
                        String quest = snapshotQuestion.child("question").getValue().toString();
                        String gAnswer = snapshotQuestion.child("goodAnswer").getValue().toString();
                        String ansA = snapshotQuestion.child("answerA").getValue().toString();
                        String ansB = snapshotQuestion.child("answerB").getValue().toString();
                        String ansC = snapshotQuestion.child("answerC").getValue().toString();
                        QuestionClass question = new QuestionClass(quest,gAnswer);
                        question.addAnswer(ansA);
                        question.addAnswer(ansB);
                        question.addAnswer(ansC);
                        theme.addQuestion(question);
                    }

                    themes.add(theme);
                    list.add(theme.getTheme());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listThemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                ThemeClass themeSelected = null;
                for (ThemeClass theme: themes) {
                    if(theme.getTheme().equals(selectedItem)) {
                        themeSelected = theme;
                        break;
                    }
                }
                //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(QuizActivity.this, StartedActivity.class);
                intent.putExtra("themeSelected", themeSelected);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( QuizActivity.this, HomeActivity.class);

                startActivity(intent);
            }
        });
    }
}