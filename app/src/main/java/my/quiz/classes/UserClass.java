package my.quiz.classes;

import java.util.ArrayList;

public class UserClass {
    private String email;
    private String name;
    private String password;
    private ArrayList<UserClass> friends = new ArrayList<UserClass>();
    private ArrayList<GameClass> gamesPlayed = new ArrayList<GameClass>();

    public UserClass(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public ArrayList<GameClass> getGamesPlayed() {
        return gamesPlayed;
    }
    public GameClass addGame(String theme, int score) {
        GameClass game = new GameClass(theme, score);
        gamesPlayed.add(game);
        return game;
    }

    public String getName() {
        return name;
    }

    public GameClass newGame(String theme) {
        GameClass game = new GameClass(theme);
        gamesPlayed.add(game);
        return game;
    }

    public ArrayList<UserClass> getFriends() {
        return friends;
    }

    public UserClass(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addFriend(UserClass user) {
        friends.add(user);
    }
    public int getTotalScore() {
        int total = 0;
        for (GameClass game : this.gamesPlayed) {
            total+= game.getScore();
        }
        return total;
    }

}
