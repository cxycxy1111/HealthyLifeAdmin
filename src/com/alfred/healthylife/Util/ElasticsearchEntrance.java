package com.alfred.healthylife.Util;

import com.alfred.healthylife.DAO.TipDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ElasticsearchEntrance {

    /**
     * 匹配查询
     *
     * @param value
     * @param from
     * @return
     * @throws IOException
     */
    public static String matchQuery(String value, int from) throws IOException {
        return ElasticsearchTool.matchQuery(value, from);
    }

    /**
     * 布尔查询
     *
     * @param value
     * @param from
     * @return
     */
    public static String boolQuery(String value, int from) {
        return ElasticsearchTool.boolQuery(value, from);
    }

    /**
     * 新增文档
     *
     * @param arrayList
     */
    public static void addDocument(ArrayList<HashMap<String, Object>> arrayList) {
        try {
            ElasticsearchTool.bulkRequest(arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新文档，不更新del字段
     *
     * @param id
     * @param title
     * @param summary
     * @throws IOException
     */
    public static void updateDocumentWithoutDelete(long id, String title, String summary) throws IOException {
        ElasticsearchTool.updateDocumentWithoutDelete(id, title, summary);
    }

    /**
     * 更新文档，只更新del字段
     *
     * @param id
     * @param del
     * @throws IOException
     */
    public static void updateDocumentDeleteField(long id, int del) throws IOException {
        ElasticsearchTool.updateDocumentWithDelete(id, del);
    }

    /**
     * 初始化索引
     */
    public static void initIndex() {
        TipDAO tipDAO = new TipDAO();
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> arrayList_target = new ArrayList<>();
        arrayList = tipDAO.query("0,1");
        for (int i = 0; i < arrayList.size(); i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap = arrayList.get(i);

            String title = Util.getStringFromMap(hashMap, "title");
            long id = Util.getLongFromMap(hashMap, "id");
            String summary = Util.getStringFromMap(hashMap, "summary");
            int del = Util.getBoolFromMap(hashMap, "del") ? 1 : 0;

            HashMap<String, Object> hashMap_target = new HashMap<>();
            hashMap_target.put("title", title);
            hashMap_target.put("id", id);
            hashMap_target.put("summary", summary);
            hashMap_target.put("del", del);
            arrayList_target.add(hashMap_target);
        }
        /**
         try {
         ElasticsearchTool.bulkRequest(arrayList_target);
         }catch (IOException e) {
         e.printStackTrace();
         }
         **/

    }

}