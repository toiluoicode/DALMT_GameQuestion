package com.example.demo.Model;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static List<Cauhoi> getListCauHoi (){
        List<Cauhoi> ls = new ArrayList<>();
        String content = "Thuận yêu ai nhất";
        List<String> listAnser = new ArrayList<>(Arrays.asList("Mỹ Hạnh","Bảo Hân","Như Quỳnh","Phụng"));
        ls.add(new Cauhoi(content,listAnser,1));
        String content1 = "Thuận yêu ai nhất 1";
        List<String> listAnser1 = new ArrayList<>(Arrays.asList("Mỹ Hạnh","Bảo Hân","Như Quỳnh","Phụng"));
        ls.add(new Cauhoi(content1,listAnser1,1));

        String content2 = "Thuận yêu ai nhất 2";
        List<String> listAnser2 = new ArrayList<>(Arrays.asList("Mỹ Hạnh","Bảo Hân","Như Quỳnh","Phụng"));
        ls.add(new Cauhoi(content2,listAnser2,1));
        return ls;
    }
}

