
document.addEventListener('DOMContentLoaded', function () {
    var stompClient = null
    var joinButton = document.querySelector('.btn-join');
    var menuPage  = document.getElementById("menu-page");
    var createRoomButton = document.querySelector('.btn-create');
    var gamePage = document.getElementById("game-page");
    var roomId  = document.querySelector(".roomId");
    var user = document.querySelector('.username')
    var roomWait = document.querySelector('.room-wait')
    var roomInf = document.querySelector('.info-room')
    var listPlayerRoom = document.querySelector('.list-player-room')
    var play_button = document.querySelector('.btn-play')
    var roomPlay = document.querySelector('.room-play')
    var answerItems = document.querySelectorAll('.room-play li');
    var questionElement = document.querySelector('.room-play h2');
    var countdownElement = document.getElementById('countdown');
    var timerElement = document.getElementById('timer');
    let  countdown =10;
    var currrentPlayer = null

    var username = null;
    var RoomID = null;

    joinButton.addEventListener('click', function(){
        var roomID = prompt("Nhập RoomID");
        username = prompt("Nhập tên");
        menuPage.classList.add("hidden");

        if (roomID){
            var socket = new SockJS("/ws")
            stompClient = Stomp.over(socket);
            console.log(socket);
            gamePage.classList.remove("hidden");
            roomWait.classList.remove("hidden");
            stompClient.connect({},function (){
                stompClient.subscribe("/room/" + roomID,function (message){
                    var room= JSON.parse(message.body)
                    RoomID = room.roomId
                    var list = room.listplayer;
                    roomInf.innerHTML = "List Of Player Of Room: " + RoomID;
                    updatelist(list)
                    onConnect(username,RoomID)

                })
                stompClient.send('/app/joinRoom/'+ roomID,{},JSON.stringify(username));
            });
        } else {
            alert("Bạn đã hủy nhập phòng");
        }
    });

    createRoomButton.addEventListener('click', function (){
        username = prompt("Nhập tên");
        menuPage.classList.add("hidden");
        gamePage.classList.remove("hidden");
        if (username) {
            console.log("vào đây 1");
             var socket = new SockJS("/ws");
            stompClient = Stomp.over(socket);
            console.log(socket);
            stompClient.connect({}, function (frame) {

                console.log("vào đây 2");
                stompClient.subscribe('/room/roomCreate', function (message) {
                    console.log("Received message: " + message.body);
                    var room = JSON.parse(message.body);
                    RoomID = room.roomId;
                    updatelist(room.listplayer)
                    onConnect(username,RoomID);
                    roomInf.innerHTML = "List Of Player Of Room: " + RoomID;
                    if (room.listplayer.length > 0 && username == room.listplayer[0].username){
                        console.log(room.listplayer[0].username)
                        play_button.classList.remove("hidden");
                    }else{
                        play_button.classList.add("hidden")
                    }

                });
                stompClient.send('/app/createRoom',{},JSON.stringify(username))
            });
        }
    }, true);
    play_button.addEventListener('click',function (){
        roomWait.classList.add('hidden')
        roomPlay.classList.remove('hidden')
        startCountdown();
    });
    function onConnect (name , roomInfo){
        stompClient.subscribe('/room/' + roomInfo , function (res){
            var room = JSON.parse(res.body)

            updatelist(room.listplayer)
            currrentPlayer = room.listplayer.length -1
        })

    }
    function  updatelist (listPlayer){
        listPlayerRoom.innerHTML=""
        listPlayer.forEach(function (player){
            var li = document.createElement('li')
            li.appendChild(document.createTextNode(player.username));
            listPlayerRoom.appendChild(li)
        })
    }
    answerItems.forEach(function (item) {
        item.addEventListener('click', function () {
            const isSelected = item.classList.contains('selected');
            if (!isSelected) {
                answerItems.forEach(function (otherItem) {
                    otherItem.classList.remove('selected');
                });
                item.classList.add('selected');
            } else {
                item.classList.remove('selected');
            }
        });
    });

    questionElement.addEventListener('click', function () {
        answerItems.forEach(function (item) {
            item.classList.remove('selected');
        });
    });

    function startCountdown() {
        const countdownInterval = setInterval(function () {
            countdown--;
            countdownElement.textContent = countdown;
            if (countdown <= 0) {
                clearInterval(countdownInterval);
                timerElement.textContent = "Hết thời gian!";
            }
        }, 1000);
    }

});
