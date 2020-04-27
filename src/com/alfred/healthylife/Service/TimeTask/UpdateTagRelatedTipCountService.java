package com.alfred.healthylife.Service.TimeTask;

import com.alfred.healthylife.DAO.TagDAO;
import com.alfred.healthylife.DAO.TipTagRelDAO;
import com.alfred.healthylife.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class UpdateTagRelatedTipCountService extends TimerTask{

    private TagDAO tagDAO;
    private TipTagRelDAO tipTagRelDAO;

    public UpdateTagRelatedTipCountService() {
        tagDAO = new TagDAO();
        tipTagRelDAO = new TipTagRelDAO();
    }

    @Override
    public void run() {
        ArrayList<HashMap<String,Object>> list_tags = new ArrayList<>();
        list_tags = tagDAO.query();

        for (int i = 0;i < list_tags.size();i++) {
            long tag_id = Util.getLongFromMap(list_tags.get(i),"id");
            ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
            arrayList = tipTagRelDAO.simpleQueryByTag(tag_id);
            tagDAO.updateTipCount(tag_id,arrayList.size());
        }
    }

}
