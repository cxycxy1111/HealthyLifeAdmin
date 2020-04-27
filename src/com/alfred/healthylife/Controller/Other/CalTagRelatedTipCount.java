package com.alfred.healthylife.Controller.Other;


import com.alfred.healthylife.Service.TimeTask.UpdateTagRelatedTipCountService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

//用于统计一个标签下所有文章的数量
@WebServlet(name = "CalTagRelatedTipCount", urlPatterns = "/CalTagRelatedTipCount", loadOnStartup = 1)
public class CalTagRelatedTipCount extends HttpServlet {

    private Timer timer = null;

    public void init(ServletConfig config) throws ServletException {
        //获取下一天
        Date tomorrow = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tomorrow);
        calendar.add(Calendar.DATE, 1);
        tomorrow = calendar.getTime();

        Date today = new Date();
        long tomorrowTime = tomorrow.getTime();
        long todayTime = today.getTime();
        long ms = tomorrowTime - todayTime;
        System.out.println("ms:" + ms);

        super.init(config);
        timer = new Timer(true);
        timer.schedule(new UpdateTagRelatedTipCountService(), ms, 1000*3600*6);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
    }


}
