package com.MyWifiSite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteLocation")
public class DeleteLocationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청에서 ID 가져오기
        int id = Integer.parseInt(request.getParameter("id"));

        // 데이터베이스 연결 및 삭제 처리
        try (Connection conn = DatabaseConnection.connect()) {
            // ID에 해당하는 행 삭제
            String sql = "DELETE FROM history WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            // ID 재정렬
            String updateSql = "UPDATE history SET id = id - 1 WHERE id > ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            // AUTOINCREMENT 초기화
            String resetSql = "UPDATE sqlite_sequence SET seq = (SELECT MAX(id) FROM history) WHERE name = 'history'";
            try (PreparedStatement pstmt = conn.prepareStatement(resetSql)) {
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 디버깅 ㄹㄹㄹㄹㄹ
        response.sendRedirect("history.jsp");
    }
}

