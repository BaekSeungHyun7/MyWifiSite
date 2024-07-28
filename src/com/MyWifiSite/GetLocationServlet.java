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

@WebServlet("/getLocation")
public class GetLocationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	//디버깅 22
        System.out.println("GetLocationServlet 호출됨");
        
        String latStr = request.getParameter("lat");
        String lngStr = request.getParameter("lng");
        
        if (latStr == null || lngStr == null || latStr.isEmpty() || lngStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lat lnt 값이 없음");
            return;
        }

        try {
            double xCoord = Double.parseDouble(latStr);
            double yCoord = Double.parseDouble(lngStr);

            Connection conn = null;
            PreparedStatement pstmt = null;
            
            //테이블에 값 넣기
            try {
                conn = DatabaseConnection.connect();
                String sql = "INSERT INTO history (xCoord, yCoord, queryDate) VALUES (?, ?, datetime('now'))";
                pstmt = conn.prepareStatement(sql);
                pstmt.setDouble(1, xCoord);
                pstmt.setDouble(2, yCoord);
                int affectedRows = pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
                return;
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            //ㄹㄹ
            response.sendRedirect("history.jsp");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lat/lng 없음");
        }
    }
}



