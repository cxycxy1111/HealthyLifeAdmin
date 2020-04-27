package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CommentDAO extends DAO{

    private SQLHelper helper;

    public CommentDAO() {
        helper = new SQLHelper();
    }

    /**
     * 新增评论
     * @param tip_id
     * @param parent_comment_id
     * @param user_id
     * @param content
     * @param create_time
     * @return
     */
    public boolean create(long tip_id,long parent_comment_id,long user_id,String content,String create_time) {
        String sql = "INSERT INTO (tip_id,parent_comment_id,user_id,content,create_time) VALUES (" +
                tip_id + "," + parent_comment_id + "," + user_id + ",'" + content + "','" + create_time + "')";
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    public boolean delete(long id) {
        String sql = "UPDATE comment SET del=1 WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
