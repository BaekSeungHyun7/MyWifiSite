package com.MyWifiSite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public static Connection connect() {
        Connection conn = null;
        try {
            // SQLite JDBC 드라이버 로드
            Class.forName("org.sqlite.JDBC");
            
            // 디버깅 8
            //프로젝트 경로 수동 지정 
            String url = "jdbc:sqlite:" + "C:\\Users\\PC\\eclipse-workspace\\MyWifiSite\\WebContent\\Wifi.db";
            conn = DriverManager.getConnection(url);
            
            if (conn != null) {
            	 // 테이블 생성
                createTables(conn);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //테이블 생성
    private static void createTables(Connection conn) {
    	//Wifi.db WifiInfo 테이블
        String createWifiInfoTable = "CREATE TABLE IF NOT EXISTS WifiInfo (\n"
                   + " distance REAL,\n"
                   + " managerNo TEXT,\n"
                   + " district TEXT,\n"
                   + " wifiName TEXT,\n"
                   + " roadAddress TEXT,\n"
                   + " detailAddress TEXT,\n"
                   + " installFloor TEXT,\n"
                   + " installType TEXT,\n"
                   + " installAgency TEXT,\n"
                   + " serviceType TEXT,\n"
                   + " networkType TEXT,\n"
                   + " installYear TEXT,\n"
                   + " indoorOutdoor TEXT,\n"
                   + " wifiEnvironment TEXT,\n"
                   + " xCoord TEXT,\n"
                   + " yCoord TEXT,\n"
                   + " workDate TEXT\n"
                   + ");";

        //Wifi.db history 테이블
        String createHistoryTable = "CREATE TABLE IF NOT EXISTS history (\n"
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                   + " xCoord REAL,\n"
                   + " yCoord REAL,\n"
                   + " queryDate TEXT\n"
                   + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createWifiInfoTable);
            stmt.execute(createHistoryTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}





