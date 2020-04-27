package com.alfred.healthylife.Service;

import com.alfred.healthylife.DAO.TipDAO;
import com.alfred.healthylife.DAO.TipLogDAO;
import com.alfred.healthylife.Util.ElasticsearchEntrance;
import com.alfred.healthylife.Util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TipService extends Service{

    private TipDAO tipDAO;
    private TipLogDAO tipLogDAO;

    public TipService() {
        tipDAO = new TipDAO();
        tipLogDAO = new TipLogDAO();
    }

    /**
     * 查询贴士详情
     * @param id
     * @return
     */
    public String query(long id) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list = tipDAO.query(id);
        if (list.size() > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map = list.get(0);
            String content = Util.readTipContent(id);
            map.put("content", content);
        }
        return Util.transformFromCollection(list);
    }

    /**
     * 查询贴士列表
     *
     * @param del
     * @param page_no
     * @param num_lmt
     * @return
     */
    public String query(String del, int page_no, int num_lmt) {
        return Util.transformFromCollection(tipDAO.query(del, page_no, num_lmt));
    }

    /**
     * 创建
     * @param title
     * @param summary
     * @param content
     * @param create_time
     * @param creator
     * @param creator_type
     * @return
     */
    public String create(String title, String summary, String content, String create_time, long creator,
                         int creator_type) {
        content = content.replace("'", "''");
        summary = summary.replace("'","''");
        if (summary.length()>512 || content.length()>5120) {
            return TOO_LONG;
        }
        if (summary.equals("") || content.equals("")) {
            return TOO_SHORT;
        }
        long id_new = tipDAO.create(title, summary, "", create_time, creator, creator_type);
        if (id_new != 0) {
            ArrayList<HashMap<String,Object>> list_tip_log = new ArrayList<>();
            HashMap<String,Object> map_tip_log = new HashMap<>();
            map_tip_log.put("summary",summary);
            map_tip_log.put("content", "");
            list_tip_log.add(map_tip_log);
            tipLogDAO.add(id_new, Util.transformFromCollection(list_tip_log), create_time, creator, creator_type);

            ArrayList<HashMap<String, Object>> list = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", String.valueOf(id_new));
            list.add(map);

            //将内容同步到文件
            Util.writeTipContent(id_new, content);

            //ElasticSearch同步新增
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", id_new);
            hashMap.put("del", 0);
            hashMap.put("title", title);
            hashMap.put("summary", summary);
            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
            arrayList.add(hashMap);
            ElasticsearchEntrance.addDocument(arrayList);

            return Util.transformFromCollection(list);
        }
        return FAIL;
    }

    /**
     * 删除
     * @param id
     * @param creator
     * @param create_time
     * @param creator_type
     * @return
     */
    public String delete(long id,long creator,String create_time,int creator_type) {
        if (tipDAO.delete(id)) {
            tipLogDAO.delete(id,"",create_time,creator,creator_type);

            //ElasticSearch同步删除
            try {
                ElasticsearchEntrance.updateDocumentDeleteField(id, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 恢复
     * @param id
     * @param creator
     * @param create_time
     * @param creator_type
     * @return
     */
    public String recover(long id,long creator,String create_time,int creator_type) {
        if (tipDAO.recover(id)) {
            tipLogDAO.recover(id,"",create_time,creator,creator_type);
            //ElasticSearch同步恢复
            try {
                ElasticsearchEntrance.updateDocumentDeleteField(id, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更新
     * @param id
     * @param summary
     * @param content
     * @param creator
     * @param creator_type
     * @param last_modify_time
     * @return
     */
    public String update(long id, String title, String summary, String content, long creator, int creator_type,
                         String last_modify_time) {
        title = title.replace("'", "''");
        summary = summary.replace("'", "''");
        content = content.replace("'","''");
        if (summary.length()>512 || content.length()>5120) {
            return TOO_LONG;
        }
        if (summary.equals("") || content.equals("")) {
            return TOO_SHORT;
        }
        if (tipDAO.update(id, title, summary, "", last_modify_time)) {

            //更新内容
            Util.writeTipContent(id, content);

            //写入日志
            ArrayList<HashMap<String,Object>> list_tip_log = new ArrayList<>();
            HashMap<String,Object> map_tip_log = new HashMap<>();
            map_tip_log.put("summary",summary);
            map_tip_log.put("content", "");
            list_tip_log.add(map_tip_log);
            tipLogDAO.update(id,Util.transformFromCollection(list_tip_log),last_modify_time,creator,creator_type);

            //更新ElasticSearch索引
            try {
                ElasticsearchEntrance.updateDocumentWithoutDelete(id, title, summary);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 仅更新简要
     *
     * @param id
     * @param summary
     * @param creator
     * @param creator_type
     * @param last_modify_time
     * @return
     */
    public String update(long id, String title, String summary, long creator, int creator_type,
                         String last_modify_time) {
        title = title.replace("'", "''");
        summary = summary.replace("'", "''");
        if (summary.length() > 512) {
            return TOO_LONG;
        }
        if (summary.equals("")) {
            return TOO_SHORT;
        }
        if (tipDAO.update(id, title, summary, last_modify_time)) {
            ArrayList<HashMap<String, Object>> list_tip_log = new ArrayList<>();
            HashMap<String, Object> map_tip_log = new HashMap<>();
            map_tip_log.put("summary", summary);
            list_tip_log.add(map_tip_log);
            tipLogDAO.update(id, Util.transformFromCollection(list_tip_log), last_modify_time, creator, creator_type);
            return SUCCESS;
        }
        return FAIL;
    }

}
