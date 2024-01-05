
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
    var question = document.querySelector(".question");
    var listAnswer = document.querySelector(".list-anwser");
    let  countdown =10;
    var currrentPlayer = null

    var username = null;
    var RoomID = null;

    joinButton.addEventListener('click', function(){
        roomId = prompt("Nhập RoomID");
        username = prompt("Nhập tên");
        menuPage.classList.add("hidden");

        if (roomId){
            var socket = new SockJS("/ws")
            stompClient = Stomp.over(socket);
            console.log(socket);
            gamePage.classList.remove("hidden");
            roomWait.classList.remove("hidden");
            stompClient.connect({},function (){
                stompClient.subscribe("/room/" + roomId,function (message){
                    var room= JSON.parse(message.body)
                    RoomID = room.roomId
                    var list = room.listplayer;
                    roomInf.innerHTML = "List Of Player Of Room: " + RoomID;
                    updatelist(list)
                    onConnect(username,RoomID)
                })
                stompClient.send('/app/joinRoom/'+ roomId,{},JSON.stringify(username));
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
             var socket = new SockJS("/ws");
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                stompClient.subscribe('/user/room/roomCreate', function (message) {
                    var room = JSON.parse(message.body);
                    roomId = room.roomId;
                    updatelist(room.listplayer)
                    onConnect(username,roomId);
                    roomInf.innerHTML = "List Of Player Of Room: " + roomId;
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

    function onConnect (name , roomInfo){
        stompClient.subscribe('/room/' + roomInfo , function (res){
            var room = JSON.parse(res.body)

            updatelist(room.listplayer)
            currrentPlayer = room.listplayer.length -1
        })
        play_button.addEventListener('click',function (){
            stompClient.send('/app/play/'+ roomInfo)

        });
        stompClient.subscribe('/room/play/' + roomInfo, function (message){
            // var question = JSON.parse(message.body);
            roomWait.classList.add('hidden')
            roomPlay.classList.remove('hidden')
            console.log("vào hàm này")
            stompClient.subscribe('/room/time/'+roomInfo,function (message){
                console.log("vào hàm này")
                var timer = JSON.parse(message.body)
                 countdownElement.innerHTML = timer;
                if (timer < 0 )
                {
                    countdownElement.innerHTML = "Het";
                }
            });
            stompClient.subscribe("/room/question/"+roomInfo,function (message){
                var cauhoi = JSON.parse(message.body)
                console.log(" đổ câu hỏi")
                pullQuestion(cauhoi.content,cauhoi.answer)
            });


        });
        stompClient.subscribe('/room/time/'+roomInfo,function (message){
            console.log("vào hàm này")
           // var timer = JSON.parse(message.body)
           //  countdownElement.innerHTML = timer;
           // if (timer < 0 )
           // {
           //     countdownElement.innerHTML = "Het";
           // }
        });

    }
    function pullQuestion (Question, anwser){
        listAnswer.innerHTML="";
        question.innerHTML= Question;
        anwser.forEach(function (choice ,index){
            var  li = document.createElement("li");
            li.innerHTML = `<strong>${String.fromCharCode(65 + index)}</strong> ${choice}`;
            listAnswer.appendChild(li);
        })
        answerItems = document.querySelectorAll('.room-play li');
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
        // startCountdown()
    }
    function  updatelist (listPlayer){
        listPlayerRoom.innerHTML=""
        listPlayer.forEach(function (player){
            var li = document.createElement('li')
            li.appendChild(document.createTextNode(player.username));
            listPlayerRoom.appendChild(li)
        })
    }
    // function startCountdown() {
    //     const countdownInterval = setInterval(function () {
    //         countdown--;
    //         countdownElement.textContent = countdown;
    //         if (countdown <= 0) {
    //             clearInterval(countdownInterval);
    //             timerElement.textContent = "Hết thời gian!";
    //         }
    //     }, 1000);
    // }

});
