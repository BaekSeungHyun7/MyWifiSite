package com.MyWifiSite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class InsertData {
    public static void main(String[] args) {
        String apiKey = "71545a5a6962616531303078594f7a73";
        int startIndex = 1;
        int endIndex = 1000;
        int totalCount = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 데이터베이스 연결
            conn = DriverManager.getConnection("jdbc:sqlite:" + "C:\\Users\\PC\\eclipse-workspace\\MyWifiSite\\WebContent\\Wifi.db");
            String sql = "INSERT INTO WifiInfo (distance, managerNo, district, wifiName, roadAddress, detailAddress, installFloor, installType, installAgency, serviceType, networkType, installYear, indoorOutdoor, wifiEnvironment, xCoord, yCoord, workDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            while (true) {
                String apiUrl = String.format("http://openapi.seoul.go.kr:8088/%s/json/TbPublicWifiInfo/%d/%d/", apiKey, startIndex, endIndex);
                URL url = new URL(apiUrl);
                HttpURLConnection connApi = (HttpURLConnection) url.openConnection();
                connApi.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(connApi.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                connApi.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(sb.toString());
                JSONObject wifiInfo = (JSONObject) jsonObject.get("TbPublicWifiInfo");
                JSONArray rows = (JSONArray) wifiInfo.get("row");
                int currentCount = rows.size();

                for (Object rowObj : rows) {
                    JSONObject row = (JSONObject) rowObj;
                    pstmt.setDouble(1, 0.0); // 기본 거리 값
                    pstmt.setString(2, (String) row.get("X_SWIFI_MGR_NO"));
                    pstmt.setString(3, (String) row.get("X_SWIFI_WRDOFC"));
                    pstmt.setString(4, (String) row.get("X_SWIFI_MAIN_NM"));
                    pstmt.setString(5, (String) row.get("X_SWIFI_ADRES1"));
                    pstmt.setString(6, (String) row.get("X_SWIFI_ADRES2"));
                    pstmt.setString(7, (String) row.get("X_SWIFI_INSTL_FLOOR"));
                    pstmt.setString(8, (String) row.get("X_SWIFI_INSTL_TY"));
                    pstmt.setString(9, (String) row.get("X_SWIFI_INSTL_MBY"));
                    pstmt.setString(10, (String) row.get("X_SWIFI_SVC_SE"));
                    pstmt.setString(11, (String) row.get("X_SWIFI_CMCWR"));
                    pstmt.setString(12, (String) row.get("X_SWIFI_CNSTC_YEAR"));
                    pstmt.setString(13, (String) row.get("X_SWIFI_INOUT_DOOR"));
                    pstmt.setString(14, (String) row.get("X_SWIFI_REMARS3"));
                    pstmt.setString(15, (String) row.get("LNT"));
                    pstmt.setString(16, (String) row.get("LAT"));
                    pstmt.setString(17, (String) row.get("WORK_DTTM"));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                totalCount += currentCount;

                if (currentCount < 1000) {
                    break;
                }

                startIndex += 1000;
                endIndex += 1000;
            }
            System.out.println(totalCount + "개의 정보를 가져왔습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}


