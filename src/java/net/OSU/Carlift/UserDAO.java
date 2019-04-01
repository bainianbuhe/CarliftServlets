/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.OSU.Carlift;

/**
 *
 * @author Administrator
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
/**
 *
 * @author Administrator
 */
public class UserDAO {
    public static User queryUser(String userName) {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM user WHERE UserName=?");

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, userName);
            
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("passWord"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
    }
    public static void addUser(String userName,String passWord)
    {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        int result = 0;
        ResultSet resultSet=null;


        //生成SQL代码
         StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("INSERT INTO user(UserName,Password) VALUES(?,?)");
       

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            result = preparedStatement.executeUpdate();
            //resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        } finally {
            DBManager.closeAll(connection, preparedStatement,resultSet);
        }
    }
      public  static void addRequest(String userName,String startAddr,String destAddr,String startTime)
    {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        int result = 88;
        ResultSet resultSet=null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("UPDATE user SET StartAddress=?,StartTime=?,DestiAddress=?,isActive=? WHERE UserName=?");    
        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
           //preparedStatement.setString(1,startAddr);
           // preparedStatement.setString(2,destAddr);
           
           preparedStatement.setString(1,startAddr);
           preparedStatement.setString(2,startTime);
            preparedStatement.setString(3,destAddr);
             preparedStatement.setString(4,"Y");
              preparedStatement.setString(5,userName);
           //preparedStatement.setString(6,userName);
            result=preparedStatement.executeUpdate();
            //resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        } finally {
           DBManager.closeAll(connection, preparedStatement,resultSet);
         
        }

    }
     public static JSONArray SeeRequest() {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int request_num=0;
        JSONArray requests=new JSONArray();
        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM user WHERE isActive=?");
        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, "Y");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               JSONObject jsonobject=new JSONObject();
               jsonobject.put("StartTime",resultSet.getString("StartTime"));
               jsonobject.put("StartAddress",resultSet.getString("StartAddress"));
               jsonobject.put("DestiAddress",resultSet.getString("DestiAddress"));
               jsonobject.put("UserName",resultSet.getString("UserName")) ;
               requests.add(jsonobject);
               request_num=request_num+1;
               
            } 
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
          
        } finally {
            AddRequestServlet.WriteStringToFile("D:/RQNUM.txt",""+request_num);
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return requests;
    }
       public  static void setIsActive(String userName,String instruction)
    {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        int result;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("UPDATE user SET isActive=? WHERE UserName=?");    
        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1,instruction);
            preparedStatement.setString(2,userName);
            result=preparedStatement.executeUpdate();
 

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        } finally {
           DBManager.closeAll(connection, preparedStatement,resultSet);
         
        }

    }
}

 