package com.alfred.healthylife.Controller.Other;

import com.alfred.healthylife.Controller.BaseServlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DownloadImage", urlPatterns = "/image/download/*")
public class DownloadImage extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        String image_name = request.getPathInfo();
        image_name = image_name.substring(1, image_name.length());
        System.out.println(image_name);
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        BufferedImage buffImg = null;
        try {
            buffImg = ImageIO.read(new FileInputStream("/usr/local/HealthyLife/TipImage/" + image_name));
            ImageIO.write(buffImg, "png", response.getOutputStream());
        } catch (IOException e) {
            buffImg = ImageIO.read(new FileInputStream("/usr/local/HealthyLife/TipImage/IMG_7762.png"));
            ImageIO.write(buffImg, "png", response.getOutputStream());
        }

    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);

    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }


}
