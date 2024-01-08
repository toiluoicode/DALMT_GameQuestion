package com.example.demo.Controller;

import com.example.demo.Model.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameController {

    @Autowired
    public SimpMessagingTemplate template;
    private final Map<String, Room> rooms =new HashMap<>();
    private final List<Cauhoi> listQuestion = Cauhoi.getListCauHoi();
    @MessageMapping("/createRoom")
    @SendToUser("/room/roomCreate")
    public Room createRoom (@Payload Player player, SimpMessageHeaderAccessor headerAccessor){
        Room room = new Room();


        System.out.println("Room ID: " + room.getRoomId());

        String s = "kết nối thành công";
        headerAccessor.getSessionAttributes().put("Notification", s);
        room.addPlayer(player.getUsername());
        headerAccessor.getSessionAttributes().put("user", player.getUsername());
//       headerAccessor.getSessionAttributes().put("RoomID", room.getRoomId());
//        ROOMID = room.getRoomId();
        rooms.put(room.getRoomId(),room);
        return room;
    }
    @MessageMapping("/joinRoom/{roomID}")
    public Room joinRoom  (@DestinationVariable String roomID, @Payload Player player, SimpMessageHeaderAccessor headerAccessor )
    {
        Room joinRoom = rooms.get(roomID);
        System.out.println("Vao joinRoom " + roomID);
        System.out.println(joinRoom.getRoomId());
        joinRoom.addPlayer(player.getUsername());
        template.convertAndSend("/room/"+ roomID,joinRoom);
        return joinRoom;
    }

    @MessageMapping("/play/{roomID}")
    public void cauhoi (@DestinationVariable String roomID){
        String  repones = "Play";
        Room roomInstain = rooms.get(roomID);
        roomInstain.setStatusRoom("Play");
        template.convertAndSend("/room/play/"+roomID,repones);
        countDown(roomInstain);
    }
    public void countDown(Room room)
    {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int time = -1;
            int couter = 0;

            @Override
            public void run() {
               if (couter <= listQuestion.size()-1 )
               {

                   if (time < 0)
                   {
                       Cauhoi cauhoi = listQuestion.get(couter);
                       CauhoiClient cauhoiClient = new CauhoiClient(couter,cauhoi.getContent(),cauhoi.getAnswer());
                       template.convertAndSend("/room/question/"+room.getRoomId(),cauhoiClient);
                       time = 2;
                       couter++;
                   }
                   else{
                       template.convertAndSend("/room/time/"+room.getRoomId(),time);
                       time--;
                   }
               }
               else {
                   timer.cancel();
                   room.setStatusRoom("Rating");
                  String S = room.getStatusRoom();
                   template.convertAndSend("/room/rating/"+room.getRoomId(),room.getListplayer());
                   cancel();
               }
            }
        };
        timer.scheduleAtFixedRate(task,0,1000);
    }
    @MessageMapping("/sendAnswer/{roomID}")
    public void checkAnwser (@DestinationVariable String roomID, @Payload RequestClient requestClient)
    {
        Room room = rooms.get(roomID);
        Player player = new Player(requestClient.getUsername());
        for (Player currentPlayer : room.getListplayer())
        {
            if (currentPlayer.getUsername().equals(player.getUsername())){
               if (listQuestion.get(requestClient.getIdquestion()).getCorrectAnsser() == requestClient.getAnwser())
               {
                   currentPlayer.setScore(currentPlayer.getScore()+1);
                   System.out.println(currentPlayer.getUsername() +" có "+currentPlayer.getScore());
               }
            }
        }



    }


}
