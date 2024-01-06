package com.example.demo.Model;

import java.io.Serializable;
import java.util.List;

public class CauhoiClient  {
    private  int idQuestion;
    private String content;
    private List<String> answer;

    public CauhoiClient(int idQuestion, String content, List<String> answer) {
        this.idQuestion = idQuestion;
        this.content = content;
        this.answer = answer;
    }
}
