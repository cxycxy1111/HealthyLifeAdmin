package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserTagRelDAO extends DAO {

    private SQLHelper helper;

    public UserTagRelDAO() {
        helper = new SQLHelper();
    }

    /**
     * 创建关系
     *
     * @param user_id
     * @param tag_id
     * @param type
     * @param create_time
     * @return
     */
    private boolean create(long user_id, long tag_id, int type, String create_time) {
        String sql = "INSERT INTO user_tag (user_id,tag_id,type,create_time,last_modify_time) VALUES ("
                + user_id + "," + tag_id + "," + type + ",'" + create_time + "','" + create_time + "')";
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除关系
     *
     * @param user_id
     * @param tag_id
     * @param type
     * @return
     */
    private boolean delete(long user_id, long tag_id, int type) {
        String sql =
                "UPDATE user_tag SET del=1 " +
                        "WHERE user_id=" + user_id + " AND tag_id=" + tag_id + " AND type=" + type;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 恢复关系
     *
     * @param user_id
     * @param tag_id
     * @param type
     * @return
     */
    private boolean recover(long user_id, long tag_id, int type) {
        String sql = "UPDATE user_tag SET del=0 " +
                "WHERE user_id=" + user_id + " AND tag_id=" + tag_id + " AND type=" + type;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过标签ID查询用户列表
     *
     * @param tag_id
     * @param type
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryByTag(long tag_id, int type, int page_no, int num_limit) {
        int location = (page_no - 1) * num_limit;
        String sql = "SELECT b.id,b.nick_name FROM user_tag a " +
                "LEFT JOIN user b ON a.user_id=b.id " +
                "WHERE a.del=0 AND a.tag_id=" + tag_id + " a.type=" + type + " " +
                "ORDER BY a.create_time DESC " +
                "LIMIT " + location + "," + num_limit;
        return helper.query(sql);
    }
}
