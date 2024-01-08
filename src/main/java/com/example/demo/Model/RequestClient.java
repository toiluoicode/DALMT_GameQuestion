package com.example.demo.Model;

public class RequestClient {
    private int idquestion;
    private int anwser;
    private String username;

    public RequestClient() {
    }

    public RequestClient(int idquestion, int anwser, String username) {
        this.idquestion = idquestion;
        this.anwser = anwser;
        this.username = username;
    }

    public int getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(int idquestion) {
        this.idquestion = idquestion;
    }

    public int getAnwser() {
        return anwser;
    }

    public void setAnwser(int anwser) {
        this.anwser = anwser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
