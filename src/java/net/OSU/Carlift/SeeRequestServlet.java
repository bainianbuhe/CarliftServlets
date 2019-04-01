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
import net.sf.json.JSONArray;
/**
 * 测试登录Servlet
 *
 * @author Implementist
 */
public class SeeRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置响应内容类型  
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {
            JSONArray requests=UserDAO.SeeRequest();
            HashMap<String,JSONArray> params=new HashMap<>();
            params.put("Requests",requests);
            JSONObject jsonobject=new JSONObject();
            jsonobject.put("params",params);
            out.write(jsonobject.toString());
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
    private Boolean verifyLogin(String userName, String password) {
        User user = UserDAO.queryUser(userName);

        //账户密码验证
        return null != user && password.equals(user.getPassword());
    }
}
