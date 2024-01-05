package com.example.demo.Controller;

import com.example.demo.Model.Cauhoi;
import com.example.demo.Model.Player;
import com.example.demo.Model.Room;
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
    private Map<String, Room> rooms =new HashMap<>();
    private List<Cauhoi> listQuestion = Cauhoi.getListCauHoi();

    int couter = 0;
    String ROOMID;

    @MessageMapping("/createRoom")
    @SendToUser("/room/roomCreate")
    public Room createRoom (@Payload Player player, SimpMessageHeaderAccessor headerAccessor){
        Room room = new Room();


        System.out.println("Room ID: " + room.getRoomId());

        String s = "kết nối thành công";
        headerAccessor.getSessionAttributes().put("Notification", s);
        room.addPlayer(player.getUsername());
        headerAccessor.getSessionAttributes().put("user", player.getUsername());
//        headerAccessor.getSessionAttributes().put("RoomID", room.getRoomId());
        ROOMID = room.getRoomId();
        rooms.put(room.getRoomId(),room);
        return room;
    }
    @MessageMapping("/joinRoom/{roomID}")
    public Room joinRoom  (@DestinationVariable String roomID, @Payload Player player, SimpMessageHeaderAccessor headerAccessor )
    {
        Room joinRoom = rooms.get(roomID);
        System.out.println("Vao joinRoom " + roomID);
        System.out.println(joinRoom.getRoomId());
//        if (joinRoom.getStatusRoom().equals("play"))
//        {
//            j\
//        }
        joinRoom.addPlayer(player.getUsername());
        for (Player currentPlayer : joinRoom.getListplayer())
        {
            System.out.println(currentPlayer.getUsername());
        }
        template.convertAndSend("/room/"+ roomID,joinRoom);
        return joinRoom;
    }

    @MessageMapping("/play/{roomID}")
    public void cauhoi (@DestinationVariable String roomID){
        String  repones = "Play";
        Room roomInstain = rooms.get(roomID);
        roomInstain.setStatusRoom("Play");
        template.convertAndSend("/room/play/"+roomID,repones);
        countDown(roomInstain.getRoomId());
        couter++;
    }

    public void countDown(String roomId)
    {
        String  repones = "Play 1";
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int time = 10;

            @Override
            public void run() {
                if (time < 0)
                {
                    time = 10;
                    // đổ câu hỏi
                }
                else{
                    template.convertAndSend("/room/time/"+roomId,time);
                    time--;
                }
            }


        };
        timer.scheduleAtFixedRate(task,0,1000);
    }

}
