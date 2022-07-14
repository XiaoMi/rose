package com.chen.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;
import net.paoding.rose.web.Invocation;

public class Test2DAO {
  String dstBucket = System.getenv("bucketname");
  String host_name = System.getenv("host_name");
  String user_name = System.getenv("user_name");
  String password = System.getenv("password");
  String dbname = System.getenv("dbname");

  public String handleRequest(Invocation inv) {
    String s = " ";
    int month = Integer.parseInt(inv.getRequest().getAttribute("month").toString());
      int year = Integer.parseInt(inv.getRequest().getAttribute("year").toString());
      int overtime = Integer.parseInt(inv.getRequest().getAttribute("overtime").toString());
      int empid = Integer.parseInt(inv.getRequest().getAttribute("empid").toString());
    try {
      int ctr = 0;
      Connection connect;
      connect = DriverManager.getConnection("jdbc:mysql://" + host_name + ":3306/" + dbname, user_name, password);
      
      Calendar Year = Calendar.getInstance();
      int CurrentYear = Year.get(Year.YEAR);

      if (( month <= 12 && month >= 1)) {
        Statement statement = connect.createStatement();
        // ruleid: tainted-sql-string
        String query = "SELECT emp_name,emp_mail,manager_id FROM employee WHERE emp_id=" + empid;
        ResultSet resultSet = statement.executeQuery(query);

        // ok: tainted-sql-string
        System.out.println("SELECT emp_name,emp_mail,manager_id FROM employee WHERE emp_id=" + empid);

        String foobar = "'Something'";
        // ok: tainted-sql-string
        String query2 = "SELECT emp_name,emp_mail,manager_id FROM employee WHERE emp_id=" + foobar;
        statement.executeQuery(query2);

        // ok: tainted-sql-string
        ResultSet resultSet2 = statement.executeQuery("SELECT * FROM employee");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (s == "") {
      s = "Sucess " + String.format("Added %s %s %s %s %s.", emp_id, month, year, overtime);
    }
    return s;
  }
}