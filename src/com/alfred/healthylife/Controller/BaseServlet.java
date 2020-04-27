package com.alfred.healthylife.Controller;

import com.alfred.healthylife.Util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BaseSevlet")
public class BaseServlet extends HttpServlet {

    protected static final String prefix = "{\"status\":\"";
    protected static final String id_prefix = "{\"id\":";
    protected static final String datasuffix = "}";
    protected static final String dataprefix = "{\"data\":";
    protected static final String suffix = "\"}";
    public static final String NO_SUCH_RECORD = prefix + "no_such_record" + suffix;//查无记录
    public static final String LOCKED = prefix + "locked" + suffix;//已锁定
    public static final String FAIL =prefix +  "fail" + suffix;//失败
    public static final String PARTLY_FAIL =prefix +  "partly_fail" + suffix;//部分失败
    public static final String SUCCESS = prefix + "success" + suffix;//成功
    public static final String ILLEGAL = prefix + "illegal" + suffix;//不合法，含有非法字符等
    public static final String TOO_LONG = prefix + "too_long" + suffix;//太长
    public static final String TOO_SHORT = prefix + "too_short" + suffix;//太短
    public static final String DUPLICATE = prefix + "duplicate" + suffix;//与已有记录重复
    public static final String NOT_MATCH = prefix + "not_match" + suffix;//不匹配
    public static final String QRY_RESULT_EMPTY = prefix + "empty" + suffix;//没有符合条件的记录
    public static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;//session过期
    public static final String AUTHORIZE_FAIL=prefix + "authorize_fail" + suffix;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/text;charset=utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("id") != null) {
            long current_user = Util.transformSessionValueToLong(session,"id");
            int current_user_type = Util.transformSessionValueToInteger(session, "type");
            dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        }else {
            dealWithSessionDead(request,response,out);
        }
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long current_user, int current_user_type) throws IOException {
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        out.append(SESSION_EXPIRED);
    }
}
