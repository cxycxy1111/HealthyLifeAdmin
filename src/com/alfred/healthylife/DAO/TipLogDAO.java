package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;

public class TipLogDAO extends DAO{

    private SQLHelper helper;

    public TipLogDAO() {
        helper = new SQLHelper();
    }

    private void create(long tip_id,int action,String content,String create_time,long creator,int creator_type) {
        String sql = "INSERT INTO tip_log (tip_id,action,content,create_time,creator,creator_type) VALUES ("
                        + tip_id + "," + action + ",'" + content + "','" + create_time + "'," + creator + "," + creator_type + ")";
        try {
            helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增帖子
     * @param content
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void add(long tip_id, String content, String create_time, long creator, int creator_type) {
        create(tip_id, 0, content, create_time, creator, creator_type);
    }

    /**
     * 删除帖子
     * @param tip_id
     * @param content
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void delete(long tip_id,String content,String create_time,long creator,int creator_type) {
        create(tip_id,1,content,create_time,creator,creator_type);
    }

    /**
     * 恢复帖子
     * @param tip_id
     * @param content
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void recover(long tip_id,String content,String create_time,long creator,int creator_type) {
        create(tip_id,3,content,create_time,creator,creator_type);
    }

    /**
     * 更新帖子
     * @param tip_id
     * @param content
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void update(long tip_id,String content,String create_time,long creator,int creator_type) {

        create(tip_id,2,content,create_time,creator,creator_type);
    }
}
