package com.example.demo.Model;

import java.util.*;

public class Room {
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

//    private List<Cauhoi> questions ;
    private int currentQuestion;

    private String StatusRoom ;
    private List<Player> listplayer;
    private int countPlayer;

//    public List<Cauhoi> getQuestions() {
//        return questions;
//    }
//
//    public void setQuestions(List<Cauhoi> questions) {
//        this.questions = questions;
//    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public String getStatusRoom() {
        return StatusRoom;
    }

    public void setStatusRoom(String statusRoom) {
        StatusRoom = statusRoom;
    }

    public List<Player> getListplayer() {
        return listplayer;
    }

    public void setListplayer(List<Player> listplayer) {
        this.listplayer = listplayer;
    }

    public int getCountPlayer() {
        return countPlayer;
    }

    public void setCountPlayer(int countPlayer) {
        this.countPlayer = countPlayer;
    }

    public Room() {
        this.roomId = UUID.randomUUID().toString();
//        this.questions = new ArrayList<>();
        this.currentQuestion = 0;
        StatusRoom = "wait";
        this.listplayer = new ArrayList<>();
        this.countPlayer = this.listplayer.size()+1;
    }

    public void addPlayer (String player){
        listplayer.add(new Player(player,0,false));
    }
    public void ready (String player){
        for (Player playerCurrent : listplayer){
            if (playerCurrent.getUsername().equals(player)){
                  playerCurrent.setStatus(true);
            }
        }
    }
    public boolean roomReady(){
        for (Player playerCurrent : listplayer){
            if (!playerCurrent.isStatus()){
                return false;
            }
        }
        return true;
    }
//    public void addListQuestion (){
//        String content = "Thuận yêu ai nhất";
//        List<String> listAnser = new ArrayList<>(Arrays.asList("Mỹ Hạnh","Chọn A đi","Hãy Chọn A","Xin hãy chọn A"));
//        questions.add(new Cauhoi(content,listAnser,1));
//    }
}
