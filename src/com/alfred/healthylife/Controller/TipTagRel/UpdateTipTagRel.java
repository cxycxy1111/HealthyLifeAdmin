package com.alfred.healthylife.Controller.TipTagRel;

import com.alfred.healthylife.Controller.BaseServlet;
import com.alfred.healthylife.Service.TipTagRelService;
import com.alfred.healthylife.Util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UpdateTipTagRel", urlPatterns = "/tiptagrel/update")
public class UpdateTipTagRel extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long tip_id = Util.getLongFromRequest(request, "tip_id");
        String new_tag_ids = request.getParameter("new_tag_ids");
        TipTagRelService tipTagRelService = new TipTagRelService();
        out.append(tipTagRelService.update(tip_id, new_tag_ids));
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }

}
