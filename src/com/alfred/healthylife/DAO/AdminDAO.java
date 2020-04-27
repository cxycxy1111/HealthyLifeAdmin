package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;
import com.alfred.healthylife.Util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminDAO extends DAO{

    private SQLHelper helper;

    public AdminDAO() {
        helper = new SQLHelper();
    }

    /**
     * 查询用户列表
     * @param del 是否删除
     * @param locked 是否锁定
     * @param page_no 页码
     * @param num_limit 每页数量限制
     * @return 用户信息
     */
    public ArrayList<HashMap<String,Object>> queryAdmins(String del,String locked,int page_no,int num_limit) {
        int location = (page_no - 1)*num_limit;
        String sql = "SELECT id,nick_name,email FROM admin " +
                "WHERE del IN (" + del + ") AND locked IN (" + locked +") " +
                "ORDER BY id DESC " +
                "LIMIT " + location + "," + num_limit;
        return helper.query(sql);
    }

    /**
     * 通过用户ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    public ArrayList<HashMap<String,Object>> queryAdminById(long id) {
        String sql = "SELECT * FROM admin WHERE id = " + id;
        return helper.query(sql);
    }

    /**
     * 通过电子邮件查询用户
     * @param email 电子邮箱
     * @return 用户信息
     */
    public ArrayList<HashMap<String,Object>> queryAdminByEmail(String email) {
        String sql = "SELECT * FROM admin WHERE email = '" + email + "'";
        return helper.query(sql);
    }

    /**
     * 通过用户ID查询是否已锁定
     * @param id 用户ID
     * @return 是否已锁定
     */
    public boolean isLocked(long id) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = queryAdminById(id);
        return list.size() != 0 && Util.getBoolFromMapList(list, "locked");
    }

    /**
     * 查询用户ID是否已删除
     * @param id 用户ID
     * @return 是否已删除
     */
    public boolean isDeleted(long id) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = queryAdminById(id);
        return list.size() != 0 && Util.getBoolFromMapList(list, "del");
    }

    /**
     * 通过电子邮箱是否已锁定
     * @param email 电子邮箱
     * @return 是否已锁定
     */
    public boolean isLocked(String email) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = queryAdminByEmail(email);
        return list.size() != 0 && Util.getBoolFromMapList(list, "locked");
    }

    /**
     * 通过电子邮箱查询是否已删除
     * @param email 电子邮箱
     * @return 是否已删除
     */
    public boolean isDeleted(String email) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = queryAdminByEmail(email);
        return list.size() != 0 && Util.getBoolFromMapList(list, "del");
    }

    /**
     * 新增用户
     * @param email 电子邮件
     * @param password 密码
     * @param register_time 注册时间
     * @return 新增结果
     */
    public long create(String email, String password, String register_time) {
        String sql = "INSERT INTO admin (nick_name,email,password,register_time,del,locked) VALUES ('" + email +
                "','" + email + "','" + password + "','" + register_time + "',0,0)";
        try {
            return helper.insert(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除结果
     */
    public boolean delete(long id){
        String sql = "UPDATE admin SET del=1 WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 恢复用户
     * @param id 用户ID
     * @return 恢复结果
     */
    public boolean recover(long id){
        String sql = "UPDATE admin SET del=0 WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 锁定用户
     * @param id 用户ID
     * @return 锁定结果
     */
    public boolean lock(long id){
        String sql = "UPDATE admin SET locked=1 WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解锁用户
     * @param id 用户ID
     * @return 解锁结果
     */
    public boolean unlock(long id){
        String sql = "UPDATE admin SET locked=0 WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param nick_name 昵称
     * @return 更新结果
     */
    public boolean updateInfo(long id,String nick_name){
        String sql = "UPDATE admin SET nick_name='" + nick_name + "' WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新最近登录时间
     * @param id 用户ID
     * @param last_login_time 昵称
     * @return 更新结果
     */
    public boolean updateLastLoginTime(long id,String last_login_time){
        String sql = "UPDATE admin SET last_login_time='" + last_login_time + "' WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新密码
     * @param id 用户ID
     * @param password 新密码
     * @return 更新结果
     */
    public boolean updatePassword(long id,String password){
        String sql = "UPDATE admin SET password='" + password + "' WHERE id=" + id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
