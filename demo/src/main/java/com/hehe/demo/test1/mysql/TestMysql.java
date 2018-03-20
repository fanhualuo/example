package com.hehe.demo.test1.mysql;

import org.joda.time.DateTime;
import sun.rmi.runtime.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xieqinghe .
 * @date 2017/11/13 上午11:11
 * @email
 */
public class TestMysql {
    public static void main(String[] args) throws SQLException {
        update();
    }


    private static void update() throws SQLException {

        Connection conn = getConn();



        Integer count = 200000;

        DateTime start=DateTime.now();
        for (Integer i = 1; i < count; i++) {
            String sql = "INSERT INTO `haier_point_anomalies` ( `horus_id`, `user_id`, `username`, `get_point`, `use_point`, `freeze_point`, `invalid_point`, `created_at`)\n" +
                    "VALUES\n" +
                    "\t( '00017a93-"+i+"d', 50988515, NULL, 15, NULL, NULL, NULL, NULL);\n";
            PreparedStatement pstmt2 = conn.prepareStatement(sql);
            pstmt2.executeUpdate();
            pstmt2.close();

        }

        DateTime end=DateTime.now();

        System.out.println(end.getMillis()-start.getMillis());
//        PreparedStatement pstmt = conn.prepareStatement(sql);


//        ResultSet res=pstmt.executeQuery(selectSql);
//        while (res.next()){
//            Integer id=res.getInt(1);
//            String statisAt=res.getString(2);
//            if (statisAt.contains("2017-11")){
//                String statisAtNew=statisAt.replaceAll("2017-11","2017-12");
//                String sql2 ="update  haier_user_data_reports set statis_at='"+statisAtNew+"' where id="+id+"";
//                System.out.println(sql2);
//                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
//                pstmt2.executeUpdate();
//                pstmt2.close();
//            }
//
//        }
    }


    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/user";
        String username = "root";
        String password = "anywhere";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
