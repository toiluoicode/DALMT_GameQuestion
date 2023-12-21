document.addEventListener('DOMContentLoaded', function () {
    var joinButton = document.querySelector('.btn-join');

    joinButton.addEventListener('click', function(){
        var roomID =  prompt("Nhập RoomID");
        if (roomID != null && roomID.trim !== ''){
            alert("Bạn Đã vào phòng "+ roomID)
        }
        else {
            alert("Bạn đã hủy nhập phòng")
        }
    })
});
  