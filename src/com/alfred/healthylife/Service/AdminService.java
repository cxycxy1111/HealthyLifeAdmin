package com.alfred.healthylife.Service;

import com.alfred.healthylife.DAO.AdminDAO;
import com.alfred.healthylife.DAO.AdminLogDAO;
import com.alfred.healthylife.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminService extends Service {

    public AdminDAO adminDAO;
    public AdminLogDAO adminLogDAO;

    public AdminService() {
        adminDAO = new AdminDAO();
        adminLogDAO = new AdminLogDAO();
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
        ArrayList<HashMap<String,Object>> list_exist_user = adminDAO.queryAdminByEmail(email);
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
        long id_new = adminDAO.create(email, Util.md5Parse(password),
                register_time);
        if (id_new != 0) {
            HashMap<String,Object> map_admin_info = new HashMap<>();//记录日志
            ArrayList<HashMap<String,Object>> list_admin_info = new ArrayList<>();
            map_admin_info.put("email",email);
            map_admin_info.put("password",Util.md5Parse(password));
            map_admin_info.put("register_time",register_time);
            list_admin_info.add(map_admin_info);
            adminLogDAO.create(id_new, Util.transformFromCollection(list_admin_info), register_time, creator, 0);
            return SUCCESS;
        }
        return FAIL;

    }

    /**
     * 登录验证
     * @return
     */
    public HashMap<String,Object> loginCheck(String email, String password, String ip_address, String login_time) {

        ArrayList<HashMap<String,Object>> admin_info = adminDAO.queryAdminByEmail(email);

        HashMap<String,Object> map_login_info = new HashMap<>();//存储登录信息
        ArrayList<HashMap<String,Object>> list_login_info = new ArrayList<>();
        map_login_info.put("email",email);
        map_login_info.put("password",password);
        map_login_info.put("ip_address",ip_address);
        map_login_info.put("login_time",login_time);
        list_login_info.add(map_login_info);
        long admin_id = Util.getLongFromMapList(admin_info,"id");
        adminLogDAO.login(admin_id,Util.transformFromCollection(list_login_info),login_time,admin_id,0);

        if (admin_info.size() == 0) {//是否存在用户
            return null;
        }
        if (Util.getBoolFromMapList(admin_info, "del")) {//是否已删除
            return null;
        }
        String stored_password = Util.getStringFromMapList(admin_info, "password");
        if (!stored_password.equalsIgnoreCase(Util.md5Parse(password))) {//密码是否匹配
            return null;
        }
        adminDAO.updateLastLoginTime(admin_id, login_time);
        return admin_info.get(0);//返回用户资料
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
        ArrayList<HashMap<String,Object>> admin_info = adminDAO.queryAdminById(id);

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

        if (adminDAO.updatePassword(id,Util.md5Parse(new_password))) {
            adminLogDAO.modifyPassword(id,Util.transformFromCollection(list_log),create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    public String update(long id, String nick_name, String create_time, long creator, int creator_type) {
        if (nick_name.equalsIgnoreCase("")) {
            return FAIL;
        }
        if (adminDAO.updateInfo(id, nick_name)) {
            HashMap<String, Object> map = new HashMap<>();
            ArrayList<HashMap<String, Object>> list = new ArrayList<>();
            map.put("nick_name", nick_name);
            list.add(map);
            adminLogDAO.modifyNickname(id, Util.transformFromCollection(list), create_time, creator, creator_type);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 锁定管理员
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String lock(long id, long creator, String create_time, int creator_type) {
        if (adminDAO.lock(id)) {
            adminLogDAO.lock(id, create_time, creator, creator_type);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 解锁管理员
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String unlock(long id,long creator,String create_time) {
        if (adminDAO.unlock(id)) {
            adminLogDAO.unlock(id,create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除管理员
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String delete(long id,long creator,String create_time) {
        if (id == creator) {
            return DUPLICATE;
        }
        if (adminDAO.delete(id)) {
            adminLogDAO.delete(id,create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 恢复管理员
     * @param id
     * @param creator
     * @param create_time
     * @return
     */
    public String recover(long id,long creator,String create_time) {
        if (adminDAO.recover(id)) {
            adminLogDAO.recover(id,create_time,creator,0);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 查询具体用户
     * @param id
     * @return
     */
    public String query(long id) {
        return Util.transformFromCollection(adminDAO.queryAdminById(id));
    }

    /**
     * 查询列表
     * @param del
     * @param locked
     * @param page_no
     * @param num_lmt
     * @return
     */
    public String query(String del,String locked,int page_no,int num_lmt) {
        return Util.transformFromCollection(adminDAO.queryAdmins(del,locked,page_no, num_lmt));
    }

}
