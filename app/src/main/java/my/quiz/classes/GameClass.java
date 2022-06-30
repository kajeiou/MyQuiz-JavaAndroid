package my.quiz.classes;

public class GameClass {
    private int id;
    private String theme;
    private int score=0;

    public GameClass(String theme, int score) {
        this.theme = theme;
        this.score = score;
    }
    public GameClass(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
