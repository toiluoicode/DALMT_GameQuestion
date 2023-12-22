document.addEventListener('DOMContentLoaded', function () {
    var joinButton = document.querySelector('.btn-join');
    var menuPage  = document.getElementById("menu-page");
    var createRoomButoon = document.querySelector('.btn-create');
    var gamePage = document.getElementById("game-page");
    var username = null;
    joinButton.addEventListener('click', function(){
        var roomID =  prompt("Nhập RoomID");
        if (roomID != null && roomID.trim !== ''){
            alert("Bạn Đã vào phòng "+ roomID)
        }
        else {
            alert("Bạn đã hủy nhập phòng")
        }
    })
    createRoomButoon.addEventListener('click',function (){
        username = prompt("Nhập tên");
        menuPage.classList.add("hidden");
        gamePage.classList.remove("hidden");
    },true)
});
