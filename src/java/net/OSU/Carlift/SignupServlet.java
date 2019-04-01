/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.OSU.Carlift;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

/**
 * 测试登录Servlet
 *
 * @author Implementist
 */
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置响应内容类型  
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {

            //获得请求中传来的用户名和密码
            String userName = request.getParameter("userName").trim();
            String password = request.getParameter("passWord").trim();

            //是否已存在
            Boolean isExist = verifyExist(userName, password);

            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if (isExist) {
                params.put("Result", "failed");
            } else {
                UserDAO.addUser(userName,password);
                params.put("Result", "success");
            }

            jsonObject.put("params", params);
            out.write(jsonObject.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 验证用户名密码是否正确
     *
     * @param userName
     * @param password
     */
    private Boolean verifyExist(String userName, String password) {
        User user = UserDAO.queryUser(userName);

        //账户密码验证
        if(user==null)
        {
            return false;
        }
        else
        {
            return true;
        }      
                
    }
}
