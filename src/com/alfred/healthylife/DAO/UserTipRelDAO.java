package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserTipRelDAO extends DAO {

    private SQLHelper helper;

    public UserTipRelDAO() {
        helper = new SQLHelper();
    }

    /**
     * 创建关系
     * @param user_id
     * @param tip_id
     * @param type
     * @param create_time
     * @return
     */
    private boolean create(long user_id,long tip_id,int type,String create_time) {
        String sql = "INSERT INTO user_tip (user_id,tip_id,type,create_time,last_modify_time) VALUES ("
                + user_id + "," + tip_id + "," + type + ",'" + create_time + "','" + create_time + "')";
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除关系
     * @param user_id
     * @param tip_id
     * @param type
     * @return
     */
    private boolean delete(long user_id,long tip_id,int type) {
        String sql =
                "UPDATE user_tip SET del=1 " +
                        "WHERE user_id=" + user_id + " AND tip_id=" + tip_id + " AND type=" + type;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 恢复关系
     * @param user_id
     * @param tip_id
     * @param type
     * @return
     */
    private boolean recover(long user_id,long tip_id,int type) {
        String sql = "UPDATE user_tip SET del=0 " +
                        "WHERE user_id=" + user_id + " AND tip_id=" + tip_id + " AND type=" + type;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 新增收藏
     * @param user_id
     * @param tip_id
     * @param type
     * @param create_time
     * @return
     */
    public boolean addFavorite(long user_id,long tip_id,int type,String create_time) {
        return create(user_id, tip_id, 0, create_time);
    }

    /**
     * 恢复收藏
     * @param user_id
     * @param tip_id
     * @param type
     * @return
     */
    public boolean recoverFavorite(long user_id,long tip_id,int type) {
        return recover(user_id, tip_id, type);
    }

    /**
     * 取消收藏
     * @param user_id
     * @param tip_id
     * @param type
     * @return
     */
    public boolean removeFavorite(long user_id,long tip_id,int type) {
        return delete(user_id, tip_id, type);
    }

    /**
     * 查询
     *
     * @param user_id
     * @param tip_id
     * @param type
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryByUserAndTip(long user_id, long tip_id, int type) {
        String sql = "SELECT * FROM user_tip " +
                "WHERE user_id=" + user_id + " AND tip_id=" + tip_id + " AND type=" + type;
        return helper.query(sql);
    }

    /**
     * 通过用户ID查询帖子列表
     *
     * @param user_id
     * @param type
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryByUser(long user_id, int type, int page_no, int num_limit) {
        int location = (page_no - 1) * num_limit;
        String sql = "SELECT b.id,b.title,b.summary FROM user_tip a " +
                "LEFT JOIN tip b ON a.tip_id=b.id " +
                "WHERE a.del=0 AND a.user_id=" + user_id + " AND a.type=" + type + " " +
                "ORDER BY a.create_time DESC " +
                "LIMIT " + location + "," + num_limit;
        return helper.query(sql);
    }

    /**
     * 通过帖子ID查询用户列表
     *
     * @param tip_id
     * @param type
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryByTip(long tip_id, int type, int page_no, int num_limit) {
        int location = (page_no - 1) * num_limit;
        String sql = "SELECT b.id,b.nick_name FROM user_tip a " +
                "LEFT JOIN user b ON a.user_id=b.id " +
                "WHERE a.del=0 AND a.tip_id=" + tip_id + " a.type=" + type + " " +
                "ORDER BY a.create_time DESC " +
                "LIMIT " + location + "," + num_limit;
        return helper.query(sql);
    }
}
