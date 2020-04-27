package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TagDAO extends DAO{

    private SQLHelper helper;

    public TagDAO() {
        helper = new SQLHelper();
    }

    /**
     * 创建标签
     * @param title
     * @return
     */
    public boolean create(String title) {
        String sql = "INSERT INTO tag (title) VALUES ('" + title + "')";
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    public boolean delete(long id) {
        String sql = "UPDATE tag SET del = 1 " +
                "WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 恢复标签
     * @param id
     * @return
     */
    public boolean recover(long id) {
        String sql = "UPDATE tag SET del = 0 " +
                "WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新标签
     * @param id
     * @param title
     * @return
     */
    public boolean update(long id,String title) {
        String sql = "UPDATE tag SET title = '" + title + "' " +
                "WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新标签
     * @param id
     * @param num
     * @return
     */
    public boolean updateTipCount(long id,int num) {
        String sql = "UPDATE tag SET related_tips_count = " + num + " WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过页码查询标签
     * @param del
     * @param page_no
     * @param num_lmt
     * @return
     */
    public ArrayList<HashMap<String, Object>> query(String del, int page_no, int num_lmt) {
        int location = (page_no - 1) * num_lmt;
        String sql = "SELECT * FROM tag " +
                "WHERE del IN (" + del + ") " +
                "ORDER BY related_tips_count DESC " +
                "LIMIT " + location + "," + num_lmt;
        return helper.query(sql);
    }

    /**
     * 通过标题查询标签
     *
     * @param title
     * @return
     */
    public ArrayList<HashMap<String, Object>> query(String title) {
        String sql = "SELECT * FROM tag " +
                "WHERE title='" + title + "'";
        return helper.query(sql);
    }

    /**
     * 通过id查询标签
     *
     * @param id
     * @return
     */
    public ArrayList<HashMap<String, Object>> query(long id) {
        String sql = "SELECT * FROM tag " +
                "WHERE id=" + id;
        return helper.query(sql);
    }

    /**
     * 查询
     *
     * @return
     */
    public ArrayList<HashMap<String, Object>> query() {
        String sql = "SELECT * FROM tag " +
                "WHERE del = 0";
        return helper.query(sql);
    }

}
