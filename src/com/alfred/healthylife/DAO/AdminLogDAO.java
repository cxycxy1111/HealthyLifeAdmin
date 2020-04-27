package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;

public class AdminLogDAO extends DAO{

    private SQLHelper helper;

    public AdminLogDAO(){
        helper = new SQLHelper();
    }

    private void create(long admin_id,int action,String info,String create_time,long creator,int creator_type) {
        String sql =
                "INSERT INTO admin_log (admin_id,action,info,create_time,creator,creator_type) VALUES (" + admin_id +
                        "," +
                        action + ",'" + info + "','" + create_time + "'," + creator + "," + creator_type + ")";
        try {
            helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建用户
     * @param id
     * @param info 信息
     * @param create_time 创建时间
     * @param creator 创建人
     * @param creator_type 创建人类型
     */
    public void create(long id, String info, String create_time, long creator, int creator_type) {
        create(id, 0, info, create_time, creator, creator_type);
    }

    /**
     * 锁定用户
     * @param admin_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void lock(long admin_id,String create_time,long creator,int creator_type) {
        create(admin_id,1,"",create_time,creator,creator_type);
    }

    /**
     * 解锁用户
     * @param admin_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void unlock(long admin_id,String create_time,long creator,int creator_type) {
        create(admin_id,2,"",create_time,creator,creator_type);
    }

    /**
     * 删除用户
     * @param admin_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void delete(long admin_id,String create_time,long creator,int creator_type) {
        create(admin_id,3,"",create_time,creator,creator_type);
    }

    /**
     * 恢复用户
     * @param admin_id 用户ID
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void recover(long admin_id,String create_time,long creator,int creator_type) {
        create(admin_id,4,"",create_time,creator,creator_type);
    }

    /**
     * 修改密码
     * @param admin_id 用户ID
     * @param info 信息
     * @param create_time 创建时间
     * @param creator 创建人ID
     * @param creator_type 创建人类型
     */
    public void modifyPassword(long admin_id,String info,String create_time,long creator,int creator_type) {
        create(admin_id,5,info,create_time,creator,creator_type);
    }

    /**
     * 修改昵称
     * @param admin_id
     * @param info
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void modifyNickname(long admin_id,String info,String create_time,long creator,int creator_type) {
        create(admin_id,6,info,create_time,creator,creator_type);
    }

    /**
     * 修改头像
     * @param admin_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void modifyLogo(long admin_id,String create_time,long creator,int creator_type) {
        create(admin_id,7,"",create_time,creator,creator_type);
    }

    /**
     * 找回密码
     * @param admin_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void findPassword(long admin_id,String create_time,long creator,int creator_type) {
        create(admin_id,8,"",create_time,creator,creator_type);
    }

    /**
     * 登录
     * @param admin_id
     * @param info
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void login(long admin_id,String info,String create_time,long creator,int creator_type) {
        create(admin_id,9,info,create_time,creator,creator_type);
    }

    /**
     * 登出
     *
     * @param admin_id
     * @param create_time
     * @param creator
     * @param creator_type
     */
    public void logout(long admin_id, String create_time, long creator, int creator_type) {
        create(admin_id, 10, "", create_time, creator, creator_type);
    }


}
