package com.MyWifiSite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FetchWifiData")
public class FetchWifiDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int totalCount = FetchWifiData.fetchData();

        if (totalCount > 0) {
            request.setAttribute("message", totalCount + "개의 WIFI 정보를 정상적으로 저장하였습니다.");
        } else {
        	
        	//디버깅 13 키 그냥 박기
            request.setAttribute("message", "와이파이 정보를 정상적으로 가져오지 못했습니다.");
        }
        request.getRequestDispatcher("/load-wifi.jsp").forward(request, response);
    }
}

