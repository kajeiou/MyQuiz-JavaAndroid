package my.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import my.quiz.classes.GameClass;
import my.quiz.classes.ThemeClass;
import my.quiz.classes.UserClass;

public class FinishedActivity extends AppCompatActivity {
    TextView score;
    GameClass gamestarted;
    UserClass user;
    Button finishQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        Intent intent = getIntent();
        gamestarted = (GameClass) intent.getSerializableExtra("gameStarted");
        user = (UserClass) intent.getSerializableExtra("user");

        score = findViewById(R.id.score);
        finishQuiz = findViewById(R.id.finishQuiz);
        score.setText(score.getText().toString() + " " + gamestarted.getScore());
        finishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishedActivity.this, HomeActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        Toast.makeText(getApplicationContext(), "QUIZ TERMINÉ", Toast.LENGTH_SHORT).show();
    }

}