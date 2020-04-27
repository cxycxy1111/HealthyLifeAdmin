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

@WebServlet(name = "QueryAdmins",urlPatterns = "/admin/queryList")
public class QueryAdmins extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        String del = request.getParameter("del");
        String locked = request.getParameter("locked");
        int page_no = Util.getIntFromRequest(request, "page_no");
        int num_lmt = 20;

        AdminService AdminService = new AdminService();
        out.append(AdminService.query(del, locked, page_no, num_lmt));
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }


}
