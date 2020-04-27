package com.alfred.healthylife.Controller.Tip;

import com.alfred.healthylife.Controller.BaseServlet;
import com.alfred.healthylife.Service.TipService;
import com.alfred.healthylife.Util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddTip",urlPatterns = "/tip/add")
public class AddTip extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        String title = request.getParameter("title");
        String summary = request.getParameter("summary");
        String content = request.getParameter("content");

        TipService tipService = new TipService();
        out.append(tipService.create(title, summary, content, Util.getCurrentTime(), current_user, 0));
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }


}
