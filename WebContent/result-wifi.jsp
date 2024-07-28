<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>근처 WIFI 정보</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>근처 WIFI 정보</h1>
    <table>
        <thead>
            <tr>
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
        <tbody>
            <c:forEach var="wifi" items="${wifiList}">
                <tr>
                    <td>${wifi.managerNo}</td>
                    <td>${wifi.district}</td>
                    <td>${wifi.wifiName}</td>
                    <td>${wifi.roadAddress}</td>
                    <td>${wifi.detailAddress}</td>
                    <td>${wifi.installFloor}</td>
                    <td>${wifi.installType}</td>
                    <td>${wifi.installAgency}</td>
                    <td>${wifi.serviceType}</td>
                    <td>${wifi.networkType}</td>
                    <td>${wifi.installYear}</td>
                    <td>${wifi.indoorOutdoor}</td>
                    <td>${wifi.wifiEnvironment}</td>
                    <td>${wifi.xCoord}</td>
                    <td>${wifi.yCoord}</td>
                    <td>${wifi.workDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="index.jsp">홈으로 가기</a>
</body>
</html>

