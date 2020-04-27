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

@WebServlet(name = "UpdateAdmin", urlPatterns = "/admin/update")
public class UpdateAdmin extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long admin_id = Util.getLongFromRequest(request, "admin_id");
        String nick_name = request.getParameter("nick_name");

        AdminService AdminService = new AdminService();
        out.append(AdminService.update(admin_id, nick_name, Util.getCurrentTime(), current_user, current_user_type));
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }


}
