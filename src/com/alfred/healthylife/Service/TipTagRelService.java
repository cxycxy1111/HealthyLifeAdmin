package com.alfred.healthylife.Service;

import com.alfred.healthylife.DAO.TagDAO;
import com.alfred.healthylife.DAO.TipTagRelDAO;
import com.alfred.healthylife.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class TipTagRelService extends Service {

    private TipTagRelDAO tipTagRelDAO;
    private TagDAO tagDAO;

    public TipTagRelService() {
        tipTagRelDAO = new TipTagRelDAO();
        tagDAO = new TagDAO();
    }

    /**
     * 通过TipID查询关联关系
     *
     * @param tip_id
     * @return
     */
    public String queryByTipId(long tip_id) {
        ArrayList<HashMap<String, Object>> list_tag = new ArrayList<>();
        ArrayList<HashMap<String, Object>> list_tip_tag_rel = new ArrayList<>();
        list_tag = tagDAO.query();
        list_tip_tag_rel = tipTagRelDAO.simpleQueryByTip(tip_id);

        for (int i = 0; i < list_tag.size(); i++) {
            HashMap<String, Object> map_tag = new HashMap<>();
            map_tag = list_tag.get(i);
            boolean match = false;
            for (int j = 0; j < list_tip_tag_rel.size(); j++) {
                HashMap<String, Object> map_tip_tag_rel = new HashMap<>();
                map_tip_tag_rel = list_tip_tag_rel.get(j);
                if (Long.parseLong(String.valueOf(map_tip_tag_rel.get("tag_id"))) == Long.valueOf(String.valueOf(map_tag.get("id")))) {
                    match = true;
                }
            }
            if (match) {
                map_tag.put("tip_tag_rel_del", "1");
            } else {
                map_tag.put("tip_tag_rel_del", "0");
            }

        }
        return Util.transformFromCollection(list_tag);
    }

    /**
     * 更新关联关系
     *
     * @param tip_id tip_id
     * @param newIds tag_ids，字符串
     * @return
     */
    public String update(long tip_id, String newIds) {
        String[] strings_new = newIds.split(",");

        ArrayList<HashMap<String, Object>> list_old_tip_tag = new ArrayList<>();
        list_old_tip_tag = tipTagRelDAO.simpleQueryByTipWithDeleted(tip_id);

        //匹配需要新增的标签
        for (int i = 0; i < strings_new.length; i++) {
            long new_id = Long.parseLong(strings_new[i]);
            boolean matched_old_del = false;//匹配到的旧ID的状态
            boolean match = false;//能否匹配到

            for (int j = 0; j < list_old_tip_tag.size(); j++) {
                HashMap<String, Object> map = new HashMap<>();
                map = list_old_tip_tag.get(j);
                long old_id = Long.parseLong(String.valueOf(map.get("tag_id")));
                if (new_id == old_id) {//判断是否可以匹配
                    match = true;
                    matched_old_del = Util.getBoolFromMap(map, "del");
                }
            }
            if (!match) {//未匹配到
                tipTagRelDAO.create(tip_id, new_id);
            } else {//匹配到
                if (matched_old_del) {//已删除时
                    tipTagRelDAO.recover(tip_id, new_id);
                }
            }
        }

        //匹配需要删除的标签
        for (int i = 0; i < list_old_tip_tag.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map = list_old_tip_tag.get(i);
            long old_id = Long.parseLong(String.valueOf(map.get("tag_id")));

            boolean matched_old_del = Util.getBoolFromMap(map, "del");//匹配到的旧ID的状态
            boolean match = false;//能否匹配到
            for (int j = 0; j < strings_new.length; j++) {
                long new_id = Long.parseLong(strings_new[j]);
                if (new_id == old_id) {
                    match = true;
                }
            }
            if (!match) {//未匹配上，也就是老的有，新的没有
                if (!matched_old_del) {//老的处于未删除状态
                    tipTagRelDAO.delete(tip_id, old_id);
                }
            }
        }

        return SUCCESS;
    }

}
