<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="styles.css">
    <script defer src="script.js"></script>
</head>
<body>
    <div class="container">
        <header>
            <h1>와이파이 정보 구하기</h1>
            <nav>
                <a href="index.jsp">홈</a> |
                <a href="history.jsp">위치 히스토리 목록</a> |
                <a href="#" id="fetch-wifi-data">Open API 와이파이 정보 가져오기</a>
            </nav>
        </header>
        <main>
            <form class="location-form" id="location-form">
                <label for="lat">LAT: </label>
                <input type="text" id="lat" name="lat" value="0.0">
                <label for="lng">LNT: </label>
                <input type="text" id="lng" name="lng" value="0.0">
                <button type="button" class="location-button" onclick="getLocation()">내 위치 가져오기</button>
                <button type="button" class="info-button" onclick="fetchWifiInformation()">근처 WIFI 정보 보기</button>
            </form>
            <table>
                <thead>
                    <tr>
                        <th>거리(Km)</th>
                        <th>관리번호</th>
                        <th>자치구</th>
                        <th>와이파이명</th>
                        <th>도로명주소</th>
                        <th>상세주소</th>
                        <th>설치위치(층)</th>
                        <th>설치유형</th>
                        <th>설치기관</th>
                        <th>서비스구분</th>
                        <th>망종류</th>
                        <th>설치년도</th>
                        <th>실내외구분</th>
                        <th>WIFI접속환경</th>
                        <th>X좌표</th>
                        <th>Y좌표</th>
                        <th>작업일자</th>
                    </tr>
                </thead>
                <tbody id="wifi-results">
                    <tr>
                        <td colspan="20">위치정보를 입력한 후에 조회해 주세요</td>
                    </tr>
                </tbody>
            </table>
        </main>
    </div>
</body>
</html>




