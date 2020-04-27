package com.alfred.healthylife.Controller.Admin;

import com.alfred.healthylife.Controller.BaseServlet;
import com.alfred.healthylife.Service.AdminService;
import com.alfred.healthylife.Util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "AdminLogin",urlPatterns = "/admin/login")
public class AdminLogin extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
        AdminService AdminService = new AdminService();
        out.append(AdminService.query(current_user));
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AdminService AdminService = new AdminService();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap = AdminService.loginCheck(email, password, Util.getIPAdress(request), Util.getCurrentTime());
        if (hashMap == null) {
            out.append(NOT_MATCH);
        }else {
            HttpSession session_1 = request.getSession();
            session_1.setMaxInactiveInterval(7*24*60*60);
            session_1.setAttribute("id",Long.parseLong(String.valueOf(hashMap.get("id"))));
            session_1.setAttribute("type", 0);
            ArrayList<HashMap<String,Object>> list = new ArrayList<>();
            list.add(hashMap);
            out.append(Util.transformFromCollection(list));
        }
    }

}
