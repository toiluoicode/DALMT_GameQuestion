package com.example.demo.Controller;

import com.example.demo.Model.Cauhoi;
import com.example.demo.Model.Player;
import com.example.demo.Model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {

    @Autowired
    public SimpMessagingTemplate template;
    private Map<String, Room> rooms =new HashMap<>();
    private List<Cauhoi> listQuestion = Cauhoi.getListCauHoi();


    @MessageMapping("/createRoom")
    @SendTo("/room/roomCreate")
    public Room createRoom (@Payload Player player, SimpMessageHeaderAccessor headerAccessor){
        Room room = new Room();


        System.out.println("Room ID: " + room.getRoomId());

        String s = "kết nối thành công";
        headerAccessor.getSessionAttributes().put("Notification", s);
        room.addPlayer(player.getUsername());
//        headerAccessor.getSessionAttributes().put("user", player.getUsername());
//        headerAccessor.getSessionAttributes().put("RoomID", room.getRoomId());
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
        for (Player currentPlayer : joinRoom.getListplayer())
        {
            System.out.println(currentPlayer.getUsername());
        }
        template.convertAndSend("/room/"+ roomID,joinRoom);
        return joinRoom;
    }

}
