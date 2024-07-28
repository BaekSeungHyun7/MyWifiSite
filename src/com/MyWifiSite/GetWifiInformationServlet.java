package com.MyWifiSite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetWifiInformation")
public class GetWifiInformationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 사용자가 입력한 최신 LAT와 LNT 값을 받아옴
    	double lat = Double.parseDouble(request.getParameter("lat"));
    	double lnt = Double.parseDouble(request.getParameter("lng"));
    	
    	// 디버깅
    	System.out.println("현재 LAT: " + lat + ", LNT: " + lnt);

        List<WifiInfo> wifiList = getAllWifi();
        
        // 디버깅 23
        wifiList.forEach(wifi -> {
            double distance = calculateDistance(lat, lnt, wifi.getxCoord(), wifi.getyCoord());
            System.out.println("Wifi로부터 거리 계산: " + wifi.getWifiName());
            System.out.println("유저 위치: LAT = " + lat + ", LNT = " + lnt);
            System.out.println("와이파이 위치: LAT = " + wifi.getxCoord() + ", LNT = " + wifi.getyCoord());
            System.out.println("유저-와이파이 간 거리: " + distance);
            wifi.setDistance(distance);
        });

        // 거리 계산 및 정렬
        wifiList.forEach(wifi -> wifi.setDistance(calculateDistance(lat, lnt, wifi.getxCoord(), wifi.getyCoord())));
        wifiList.sort(Comparator.comparingDouble(WifiInfo::getDistance));

        List<WifiInfo> top20WifiList = wifiList.subList(0, Math.min(20, wifiList.size()));

        response.setContentType("text/html;charset=UTF-8");
        StringBuilder htmlResponse = new StringBuilder();

        for (WifiInfo wifi : top20WifiList) {
            htmlResponse.append("<tr>");
            htmlResponse.append(String.format("<td>%.4f</td>", wifi.getDistance())); // 거리 값 포맷팅 -> 4자리까지만
            htmlResponse.append("<td>").append(wifi.getManagerNo()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getDistrict()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getWifiName()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getRoadAddress()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getDetailAddress()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getInstallFloor()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getInstallType()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getInstallAgency()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getServiceType()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getNetworkType()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getInstallYear()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getIndoorOutdoor()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getWifiEnvironment()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getxCoord()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getyCoord()).append("</td>");
            htmlResponse.append("<td>").append(wifi.getWorkDate()).append("</td>");
            htmlResponse.append("</tr>");
        }


        response.getWriter().write(htmlResponse.toString());
    }

    private List<WifiInfo> getAllWifi() {
        List<WifiInfo> wifiList = new ArrayList<>();
        String sql = "SELECT * FROM WifiInfo";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                WifiInfo wifi = new WifiInfo();
                wifi.setManagerNo(rs.getString("managerNo"));
                wifi.setDistrict(rs.getString("district"));
                wifi.setWifiName(rs.getString("wifiName"));
                wifi.setRoadAddress(rs.getString("roadAddress"));
                wifi.setDetailAddress(rs.getString("detailAddress"));
                wifi.setInstallFloor(rs.getString("installFloor"));
                wifi.setInstallType(rs.getString("installType"));
                wifi.setInstallAgency(rs.getString("installAgency"));
                wifi.setServiceType(rs.getString("serviceType"));
                wifi.setNetworkType(rs.getString("networkType"));
                wifi.setInstallYear(rs.getString("installYear"));
                wifi.setIndoorOutdoor(rs.getString("indoorOutdoor"));
                wifi.setWifiEnvironment(rs.getString("wifiEnvironment"));
                wifi.setxCoord(rs.getDouble("xCoord"));
                wifi.setyCoord(rs.getDouble("yCoord"));
                wifi.setWorkDate(rs.getString("workDate"));
                wifiList.add(wifi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wifiList;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) 
                    + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344; // 킬로미터로 변환?
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}







