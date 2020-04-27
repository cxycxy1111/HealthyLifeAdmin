package com.alfred.healthylife.Service;

import com.alfred.healthylife.DAO.UserTipRelDAO;
import com.alfred.healthylife.DAO.UserTipLogDAO;
import com.alfred.healthylife.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class UserTipRelService extends Service {

    private UserTipLogDAO userTipLogDAO;
    private UserTipRelDAO userTipRelDAO;

    public UserTipRelService() {
        userTipRelDAO = new UserTipRelDAO();
        userTipLogDAO = new UserTipLogDAO();
    }

    /**
     * 收藏
     * @param user_id
     * @param tip_id
     * @param create_time
     * @return
     */
    public String addFavorite(long user_id,long tip_id,String create_time) {
        ArrayList<HashMap<String, Object>> list_rel = userTipRelDAO.queryByUserAndTip(user_id, tip_id, 0);
        if (list_rel.size() == 0) {//新增
            if (userTipRelDAO.addFavorite(user_id, tip_id, 0, create_time)) {
                userTipLogDAO.addFavorite(user_id,tip_id,create_time,user_id,1);
                return SUCCESS;
            }
            return FAIL;
        }
        if (Util.getBoolFromMapList(list_rel,"del")) {//处于删除状态时，进行恢复操作
            if (userTipRelDAO.recoverFavorite(user_id, tip_id, 0)) {
                userTipLogDAO.addFavorite(user_id,tip_id,create_time,user_id,1);
                return SUCCESS;
            }
            return FAIL;
        }
        return SUCCESS;
    }

    /**
     * 取消收藏
     * @param user_id
     * @param tip_id
     * @param create_time
     * @return
     */
    public String removeFavorite(long user_id,long tip_id,String create_time) {
        ArrayList<HashMap<String, Object>> list_rel = userTipRelDAO.queryByUserAndTip(user_id, tip_id, 0);
        if (list_rel.size() == 0) {//本来没有收藏的话，不需要取消收藏
            return SUCCESS;
        }
        if (!Util.getBoolFromMapList(list_rel,"del")) {//如果正处于收藏状态
            if (userTipRelDAO.removeFavorite(user_id, tip_id, 0)) {
                userTipLogDAO.removeFavorite(user_id,tip_id,create_time,user_id,1);
                return SUCCESS;
            }
            return FAIL;
        }
        return SUCCESS;
    }

    /**
     * 查询用户关注了的贴士列表
     *
     * @param user_id
     * @param type
     * @param page_no
     * @param num_lmt
     * @return
     */
    public String queryByUser(long user_id, int type, int page_no, int num_lmt) {
        return Util.transformFromCollection(userTipRelDAO.queryByUser(user_id, type, page_no, num_lmt));
    }

    /**
     * 查询用户关注了的贴士列表
     *
     * @param tip_id
     * @param type
     * @param page_no
     * @param num_lmt
     * @return
     */
    public String queryByTip(long tip_id, int type, int page_no, int num_lmt) {
        return Util.transformFromCollection(userTipRelDAO.queryByTip(tip_id, type, page_no, num_lmt));
    }



}
