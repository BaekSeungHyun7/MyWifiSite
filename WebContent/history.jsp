<%@ page import="java.sql.*, java.util.*" %>
<%@ page import="com.MyWifiSite.DatabaseConnection" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>위치 히스토리 목록</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>위치 히스토리 목록</h1>
            <nav>
                <a href="index.jsp">홈</a> |
                <a href="history.jsp">위치 히스토리 목록</a> |
                <a href="#">Open API 와이파이 정보 가져오기</a>
            </nav>
        </header>
        <main>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>X좌표</th>
                        <th>Y좌표</th>
                        <th>조회일자</th>
                        <th>비고</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Connection conn = null;
                        PreparedStatement pstmt = null;
                        ResultSet rs = null;
                        List<Map<String, String>> historyData = new ArrayList<>();
                        try {
                            conn = DatabaseConnection.connect();
                            String sql = "SELECT * FROM history ORDER BY id DESC";
                            pstmt = conn.prepareStatement(sql);
                            rs = pstmt.executeQuery();
                            
                            while (rs.next()) {
                                Map<String, String> data = new HashMap<>();
                                data.put("id", rs.getString("id"));
                                data.put("xCoord", rs.getString("xCoord"));
                                data.put("yCoord", rs.getString("yCoord"));
                                data.put("queryDate", rs.getString("queryDate"));
                                historyData.add(data);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (rs != null) rs.close();
                                if (pstmt != null) pstmt.close();
                                if (conn != null) conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                        for (Map<String, String> data : historyData) {
                    %>
                    <tr>
                        <td><%= data.get("id") %></td>
                        <td><%= data.get("xCoord") %></td>
                        <td><%= data.get("yCoord") %></td>
                        <td><%= data.get("queryDate") %></td>
                        <td><a href="deleteLocation?id=<%= data.get("id") %>">삭제</a></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </main>
    </div>
</body>
</html>

