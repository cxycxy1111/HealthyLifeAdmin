package com.alfred.healthylife.Service;

import com.alfred.healthylife.DAO.UserDAO;
import com.alfred.healthylife.DAO.UserLogDAO;
import com.alfred.healthylife.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class UserService extends Service{

    public UserDAO userDAO;
    public UserLogDAO userLogDAO;

    public UserService(){
       userDAO = new UserDAO();
       userLogDAO = new UserLogDAO();
    }

    /**
     * 创建用户
     * @param email
     * @param password
     * @param creator
     * @param register_time
     * @return
     */
    public String create(String email,String password,String confirm_password,long creator,String register_time) {
        ArrayList<HashMap<String,Object>> list_exist_user = userDAO.queryUserByEmail(email);
        if (!password.equals(confirm_password)) {
            return NOT_MATCH;
        }
        if (Util.isContainIllegalCharCheck(email) || Util.isContainIllegalCharCheck(password)) {
            return ILLEGAL;
        }
        if (email.length()>25 || password.length()>16) {
            return TOO_LONG;
        }
        if (email.length() == 0 || password.length() < 6) {
            return TOO_SHORT;
        }
        if (list_exist_user.size()>0) {
            return DUPLICATE;
        }
        long id_new = userDAO.create(email, Util.md5Parse(password), register_time);
        if (id_new != 0) {
            HashMap<String,Object> map_admin_info = new HashMap<>();//记录日志
            ArrayList<HashMap<String,Object>> list_admin_info = new ArrayList<>();
            map_admin_info.put("email",email);
            map_admin_info.put("password",Util.md5Parse(password));
            map_admin_info.put("register_time",register_time);
            list_admin_info.add(map_admin_info);
            userLogDAO.create(id_new, Util.transformFromCollection(list_admin_info), register_time, creator, 0);
            return SUCCESS;
        }
        return FAIL;

    }

    /**
     * 登录验证
     * @return
     */
    public HashMap<String,Object> loginCheck(String email, String password, String ip_address, String login_time) {
        ArrayList<HashMap<String,Object>> user_info = userDAO.queryUserByEmail(email);
        HashMap<String,Object> map_login_info = new HashMap<>();//存储登录信息
        ArrayList<HashMap<String,Object>> list_login_info = new ArrayList<>();
        map_login_info.put("email",email);
        map_login_info.put("password",password);
        map_login_info.put("ip_address",ip_address);
        map_login_info.put("login_time",login_time);
        list_login_info.add(map_login_info);
        long admin_id = Util.getLongFromMapList(user_info,"id");
        userLogDAO.login(admin_id,Util.transformFromCollection(list_login_info),login_time,admin_id,0);

        if (user_info.size() == 0) {//是否存在用户
            return null;
        }
        if (Util.getBoolFromMapList(user_info, "del")) {//是否已删除
            return null;
        }
        String stored_password = Util.getStringFromMapList(user_info, "password");
        if (!stored_password.equalsIgnoreCase(Util.md5Parse(password))) {//密码是否匹配
            return null;
        }

        return user_info.get(0);//返回用户资料
    }

    public String update(long id, String nick_name, String create_time, long creator, int creator_type) {
        if (nick_name.equalsIgnoreCase("")) {
            return FAIL;
        }
        if (userDAO.updateInfo(id, nick_name)) {
            HashMap<String, Object> map = new HashMap<>();
            ArrayList<HashMap<String, Object>> list = new ArrayList<>();
            map.put("nick_name", nick_name);
            list.add(map);
            userLogDAO.modifyNickname(id, Util.transformFromCollection(list), create_time, creator, creator_type);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 修改密码
     * @param id
     * @param new_password
     * @param create_time
     * @param creator
     * @return
     */
    public String updatePassword(long id,String new_password,String create_time,long creator) {
        ArrayList<HashMap<String,Object>> admin_info = userDAO.queryUserById(id);

        if (new_password.contains("'") || new_password.contains(" ")) {//含有非法字符
            return ILLEGAL;
        }
        if (new_password.length() > 16) {//密码长度过长
            return TOO_LONG;
        }
        if (new_password.length() < 6) {//密码长度不够
            return TOO_SHORT;
        }

        HashMap<String,Object> map_log = new HashMap<>();//记录日志
        ArrayList<HashMap<String,Object>> list_log = new ArrayList<>();
        map_log.put("old_password",Util.getStringFromMapList(admin_info,"password"));
        map_log.put("new_password",Util.md5Parse(new_password));
        list_log.add(map_log);

        if (userDAO.updatePassword(id,Util.md5Parse(new_password))) {
            userLogDAO.modifyPassword(id,Util.transformFromCollection(list_log),create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 锁定用户
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String lock(long id,long creator,String create_time) {
        if (userDAO.lock(id)) {
            userLogDAO.lock(id,create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 解锁用户
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String unlock(long id,long creator,String create_time) {
        if (userDAO.unlock(id)) {
            userLogDAO.unlock(id,create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除用户
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String delete(long id,long creator,String create_time) {
        if (userDAO.delete(id)) {
            userLogDAO.delete(id,create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 恢复用户
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String recover(long id,long creator,String create_time) {
        if (userDAO.recover(id)) {
            userLogDAO.recover(id,create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 查询用户列表
     * @param deleted
     * @param locked
     * @param page_no
     * @param num_limit
     * @return
     */
    public String query(String deleted, String locked, int page_no, int num_limit) {
        return Util.transformFromCollection(userDAO.queryUsers(deleted,locked,page_no,num_limit));
    }

    /**
     * 查询用户
     * @param user_id
     * @return
     */
    public String query(long user_id) {
        return Util.transformFromCollection(userDAO.queryUserById(user_id));
    }

}
