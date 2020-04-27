package com.alfred.healthylife.Controller.Other;

import com.alfred.healthylife.Controller.BaseServlet;
import com.alfred.healthylife.Util.Util;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "UpLoadFile", urlPatterns = "/file/upload")
public class UpLoadFile extends BaseServlet {

    private static final String uploadPath = "/usr/local/HealthyLife/TipImage/";
    File tempPathFile;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long timestamp = Util.getCurrentTimeStamp();
        try {

            ArrayList<String> arrayList = new ArrayList<>();
            DiskFileItemFactory factory = new DiskFileItemFactory();

            factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
            factory.setRepository(tempPathFile);// 设置缓冲区目录

            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB

            List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
            Iterator<FileItem> its = items.iterator();
            int i = 0;
            while (its.hasNext()) {
                FileItem fi = (FileItem) its.next();
                String fileName = fi.getName();
                if (fileName != null) {

                    String series = "";
                    if (i < 10) {
                        series = "0000" + i;
                    } else if (i < 100) {
                        series = "000" + i;
                    } else if (i < 1000) {
                        series = "00" + i;
                    } else if (i < 10000) {
                        series = "0" + i;
                    } else if (i < 100000) {
                        series = String.valueOf(i);
                    }
                    String name = current_user_type + "_" + current_user + "_" + timestamp + "_" + series + ".png";
                    arrayList.add(name);
                    File savedFile = new File(uploadPath, name);
                    fi.write(savedFile);
                }
                i++;
            }
            out.append(Util.transformFromCollection(arrayList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }


}
