<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 가져오기 결과</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        h1 {
            font-size: 2em;
        }
        p {
            font-size: 2em;
        }
    </style>
</head>
<body>
    <h1>와이파이 정보 가져오기 결과</h1>
    <p>${message}</p>
    <a href="index.jsp">홈으로 가기</a>
</body>
</html>
