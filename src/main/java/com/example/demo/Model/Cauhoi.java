package com.example.demo.Model;

import java.util.List;

public class Cauhoi {
    private String content;
    private List<String> answer;

    private int correctAnsser;

    public Cauhoi(String content, List<String> answer, int correctAnsser) {
        this.content = content;
        this.answer = answer;
        this.correctAnsser = correctAnsser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public int getCorrectAnsser() {
        return correctAnsser;
    }

    public void setCorrectAnsser(int correctAnsser) {
        this.correctAnsser = correctAnsser;
    }
}
