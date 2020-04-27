package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;

public class UserTagLogDAO extends DAO {

    private SQLHelper helper;

    public UserTagLogDAO() {
        helper = new SQLHelper();
    }

    /**
     * 创建日志
     *
     * @param user_id
     * @param tag_id
     * @param type
     * @param action
     * @param create_time
     * @param creator
     * @param creator_type
     */
    private void create(long user_id, long tag_id, int type, int action, String create_time, long creator, int creator_type) {
        String sql = "INSERT INTO user_tag_log (user_id,tag_id,type,action,create_time,creator,creator_type) VALUES " +
                "(" + user_id + "," + tag_id + "," + type + "," + action + ",'" + create_time + "'," + creator + "," + creator_type + ")";
        try {
            helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加收藏
     *
     * @param user_id
     * @param tag_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void addFavorite(long user_id, long tag_id, String create_time, long creator, int creator_type) {
        create(user_id, tag_id, 0, 0, create_time, creator, creator_type);
    }

    /**
     * 移除收藏
     *
     * @param user_id
     * @param tag_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void removeFavorite(long user_id, long tag_id, String create_time, long creator, int creator_type) {
        create(user_id, tag_id, 0, 1, create_time, creator, creator_type);
    }


}
