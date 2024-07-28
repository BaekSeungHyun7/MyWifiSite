package com.MyWifiSite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FetchWifiData {
	//OpenApi
    private static final String API_URL_TEMPLATE = "http://openapi.seoul.go.kr:8088/71545a5a6962616531303078594f7a73/xml/TbPublicWifiInfo/%d/%d/";

    public static int fetchData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int startIndex = 1;
        int endIndex = 1000;
        int totalCount = 0;

        //1000개씩 반복 (최대 1000개)
        try {
            conn = DatabaseConnection.connect();
            String sql = "INSERT INTO WifiInfo (distance, managerNo, district, wifiName, roadAddress, detailAddress, installFloor, installType, installAgency, serviceType, networkType, installYear, indoorOutdoor, wifiEnvironment, xCoord, yCoord, workDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            while (true) {
                String apiUrl = String.format(API_URL_TEMPLATE, startIndex, endIndex);
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

                // XML 파싱
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(sb.toString())));

                NodeList rowList = doc.getElementsByTagName("row");
                int currentCount = rowList.getLength();
                
                //디버깅
                System.out.println("현재까지 가져온 행의 수: " + totalCount);

                // 데이터 삽입
                for (int i = 0; i < rowList.getLength(); i++) {
                    NodeList row = rowList.item(i).getChildNodes();
                    pstmt.setDouble(1, 0.0); // 기본 거리 값
                    pstmt.setString(2, row.item(0).getTextContent());
                    pstmt.setString(3, row.item(1).getTextContent());
                    pstmt.setString(4, row.item(2).getTextContent());
                    pstmt.setString(5, row.item(3).getTextContent());
                    pstmt.setString(6, row.item(4).getTextContent());
                    pstmt.setString(7, row.item(5).getTextContent());
                    pstmt.setString(8, row.item(6).getTextContent());
                    pstmt.setString(9, row.item(7).getTextContent());
                    pstmt.setString(10, row.item(8).getTextContent());
                    pstmt.setString(11, row.item(9).getTextContent());
                    pstmt.setString(12, row.item(10).getTextContent());
                    pstmt.setString(13, row.item(11).getTextContent());
                    pstmt.setString(14, row.item(12).getTextContent());
                    pstmt.setString(15, row.item(13).getTextContent());
                    pstmt.setString(16, row.item(14).getTextContent());
                    pstmt.setString(17, row.item(15).getTextContent());
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

            // 디버깅 17
            System.out.println("전체 WIFI 정보 저장완료");
            return totalCount;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}






