package my.quiz.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThemeClass {
    private int id;
    private String theme;
    private ArrayList<QuestionClass> questions = new ArrayList<QuestionClass>();

    public ThemeClass( String theme) {
        this.theme = theme;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getId() {
        return this.id;
    }
    public String getTheme() {
        return this.theme;
    }

    public void addQuestion(QuestionClass question) {
        questions.add(question);
    }
}
