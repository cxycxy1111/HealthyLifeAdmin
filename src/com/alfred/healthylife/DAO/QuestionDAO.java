package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionDAO extends DAO {

    SQLHelper helper = new SQLHelper();

    public QuestionDAO() {

    }

    public long create(String title, String create_time) {
        long id = 0;
        String sql = "INSERT INTO question (title,del,create_time) VALUES ('" + title + "',0,'" + create_time + "')";
        try {
            id = helper.insert(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean delete(long id) {
        String sql = "UPDATE question SET del = 1 WHERE id=" + id;
        boolean b = false;
        try {
            b = helper.update(sql);
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return b;
        }
    }

    public boolean recover(long id) {
        String sql = "UPDATE question SET del = 0 WHERE id=" + id;
        boolean b = false;
        try {
            b = helper.update(sql);
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return b;
        }
    }

    public ArrayList<HashMap<String, Object>> query(String del, int page_no, int num_lmt) {
        int location = (page_no - 1) * num_lmt;
        String sql = "SELECT * FROM question " +
                "WHERE del IN (" + del + ") " +
                "ORDER BY id DESC " +
                "LIMIT " + location + "," + num_lmt;
        return helper.query(sql);
    }

}
