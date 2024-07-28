//확인용
console.log('script.js 로드됨');

// 사용자의 위치 정보를 가져오는 함수
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, showError);
    } else {
        alert("위치 정보를 가져올 수 없습니다.");
    }
}

// 위치 정보 갱신 성공
function showPosition(position) {
    document.getElementById("lat").value = position.coords.latitude;
    document.getElementById("lng").value = position.coords.longitude;

    // 위치 정보를 서버로 전송
    sendLocationToServer(position.coords.latitude, position.coords.longitude);
}

// 위치 정보 갱신 실패
function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            alert("사용자가 위치정보 요청을 거부했습니다.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("위치정보를 이용할 수 없습니다.");
            break;
        case error.TIMEOUT:
            alert("요청 시간이 초과되었습니다.");
            break;
        case error.UNKNOWN_ERROR:
            alert("알 수없는 오류가 발생했습니다.");
            break;
    }
}

// 서버로 위치 정보를 전송하는 함수
function sendLocationToServer(xCoord, yCoord) {
    fetch('/MyWifiSite/getLocation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            'lat': xCoord,
            'lng': yCoord
        })
    })
    .then(response => response.text())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
}

//Open API 와이파이 정보 가져오기 클릭 이벤트 처리
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('fetch-wifi-data').addEventListener('click', function(event) {
        event.preventDefault();
        console.log('와이파이 정보 가져오기 실행됨');
        window.location.href = '/MyWifiSite/FetchWifiData'; //ㄹㄹ
    });
});

//근처 WIFI 정보를 가져오는 함수
function fetchWifiInformation() {
    const lat = document.getElementById("lat").value;
    const lng = document.getElementById("lng").value;

    fetch(`/MyWifiSite/GetWifiInformation`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `lat=${lat}&lng=${lng}`
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("wifi-results").innerHTML = data;
    })
    .catch(error => {
        console.error('Error:', error);
    });
}



