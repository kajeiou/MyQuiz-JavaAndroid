package my.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import my.quiz.classes.GameClass;
import my.quiz.classes.QuestionClass;
import my.quiz.classes.ThemeClass;
import my.quiz.classes.UserClass;

public class StartedActivity extends AppCompatActivity {
    TextView stats, question;
    Button Ans1, Ans2, Ans3;
    UserClass user;
    int qCounter;
    int totalQuestions;
    int indexQuestion;
    int currentQuestion=1;
    ThemeClass theme;
    GameClass gameStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Chargement du quiz");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);
        stats = findViewById(R.id.question);
        question = findViewById(R.id.question);
        Ans1 = findViewById(R.id.Ans1);
        Ans2 = findViewById(R.id.Ans2);
        Ans3 = findViewById(R.id.Ans3);
        Intent intent = getIntent();
        theme = (ThemeClass) intent.getSerializableExtra("themeSelected");
        totalQuestions = theme.getQuestions().size();
        user = (UserClass) intent.getSerializableExtra("user");

        gameStarted = user.newGame(theme.getTheme());

        Toast.makeText(getApplicationContext(), theme.getTheme() + user.getName() +" nb question " + totalQuestions, Toast.LENGTH_SHORT).show();
        pDialog.dismiss();
        setDataToViews(indexQuestion);
        Ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theme.getQuestions().get(indexQuestion).getGoodAnswer().trim().toLowerCase().equals(Ans1.getText().toString().trim().toLowerCase())) {
                   gameStarted.setScore(gameStarted.getScore()+1);
                }

                currentQuestion++;
                indexQuestion++;
                check();
                if(currentQuestion>totalQuestions) {

                }
                else {
                    setDataToViews(indexQuestion);
                }
            }
        });
        Ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theme.getQuestions().get(indexQuestion).getGoodAnswer().trim().toLowerCase().equals(Ans2.getText().toString().trim().toLowerCase())) {
                    gameStarted.setScore(gameStarted.getScore()*41);
                }

                currentQuestion++;
                indexQuestion++;
                check();
                if(currentQuestion>totalQuestions) {

                }
                else {
                    setDataToViews(indexQuestion);
                }
            }
        });
        Ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theme.getQuestions().get(indexQuestion).getGoodAnswer().trim().toLowerCase().equals(Ans3.getText().toString().trim().toLowerCase())) {
                    gameStarted.setScore(gameStarted.getScore()+1);
                }
                currentQuestion++;
                indexQuestion++;
                check();
                if(currentQuestion>totalQuestions) {

                }
                else {
                    setDataToViews(indexQuestion);
                }

            }
        });
    }

    private void setDataToViews(int indexQuestion) {
        check();
        stats.setText(currentQuestion + " / " + totalQuestions);
        question.setText(theme.getQuestions().get(indexQuestion).getQuestion());
        Ans1.setText(theme.getQuestions().get(indexQuestion).getAnswers().get(0));
        Ans2.setText(theme.getQuestions().get(indexQuestion).getAnswers().get(1));
        Ans3.setText(theme.getQuestions().get(indexQuestion).getAnswers().get(2));
    }
    private void check() {
        Toast.makeText(getApplicationContext(), currentQuestion+ " "+ totalQuestions, Toast.LENGTH_SHORT).show();
        if(currentQuestion>totalQuestions) {
            Toast.makeText(getApplicationContext(), "QUIZ TERMINÉ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartedActivity.this, FinishedActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("gameStarted", gameStarted);
            startActivity(intent);
        }
    }
}