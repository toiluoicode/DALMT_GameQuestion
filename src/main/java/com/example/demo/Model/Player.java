package com.example.demo.Model;

public class Player {
    private String username;
    private int Score;
    // o is No really, 1 is ready
    private boolean status;

    public Player(String username, int score, boolean status) {
        this.username = username;
        Score = score;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
