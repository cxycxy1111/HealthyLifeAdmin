package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;

public class UserLogDAO extends DAO{

    private SQLHelper helper;

    public UserLogDAO(){
        helper = new SQLHelper();
    }

    private void create(long user_id,int action,String info,String create_time,long creator,int creator_type) {
        String sql =
                "INSERT INTO user_log (user_id,action,info,create_time,creator,creator_type) VALUES (" + user_id + "," +
                        action + ",'" + info + "','" + create_time + "'," + creator + "," + creator_type + ");";
        try {
            helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建用户
     * @param user_id 用户ID
     * @param info 信息
     * @param create_time 创建时间
     * @param creator 创建人
     * @param creator_type 创建人类型
     */
    public void create(long user_id, String info, String create_time, long creator, int creator_type) {
        create(user_id, 0, info, create_time, creator, creator_type);
    }

    /**
     * 锁定用户
     * @param user_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void lock(long user_id,String create_time,long creator,int creator_type) {
        create(user_id,1,"",create_time,creator,creator_type);
    }

    /**
     * 解锁用户
     * @param user_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void unlock(long user_id,String create_time,long creator,int creator_type) {
        create(user_id,2,"",create_time,creator,creator_type);
    }

    /**
     * 删除用户
     * @param user_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void delete(long user_id,String create_time,long creator,int creator_type) {
        create(user_id,3,"",create_time,creator,creator_type);
    }

    /**
     * 恢复用户
     * @param user_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void recover(long user_id,String create_time,long creator,int creator_type) {
        create(user_id,4,"",create_time,creator,creator_type);
    }

    /**
     * 修改密码
     * @param user_id 用户ID
     * @param info 信息
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void modifyPassword(long user_id,String info,String create_time,long creator,int creator_type) {
        create(user_id,5,info,create_time,creator,creator_type);
    }

    /**
     * 修改昵称
     * @param user_id
     * @param info
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void modifyNickname(long user_id,String info,String create_time,long creator,int creator_type) {
        create(user_id,6,info,create_time,creator,creator_type);
    }

    /**
     * 修改头像
     * @param user_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void modifyLogo(long user_id,String create_time,long creator,int creator_type) {
        create(user_id,7,"",create_time,creator,creator_type);
    }

    /**
     * 找回密码
     * @param user_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void findPassword(long user_id,String create_time,long creator,int creator_type) {
        create(user_id,8,"",create_time,creator,creator_type);
    }

    /**
     * 登录
     * @param user_id
     * @param info
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void login(long user_id,String info,String create_time,long creator,int creator_type) {
        create(user_id,9,info,create_time,creator,creator_type);
    }

    /**
     * 登出
     *
     * @param user_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void logout(long user_id, String create_time, long creator, int creator_type) {
        create(user_id, 10, "", create_time, creator, creator_type);
    }


}
