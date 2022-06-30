package my.quiz.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionClass implements Serializable {

    private int id;
    private String question;
    private ArrayList<String> answers = new ArrayList<String>();
    private String goodAnswer;

    public QuestionClass(String question, String goodAnswer) {
        this.question = question;
        this.goodAnswer = goodAnswer;
    }
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public void setGoodAnswer(String goodAnswer) {
        this.goodAnswer = goodAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getGoodAnswer() {
        return goodAnswer;
    }
}
