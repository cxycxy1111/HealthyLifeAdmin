package com.alfred.healthylife.Service;

import com.alfred.healthylife.DAO.TagDAO;
import com.alfred.healthylife.Util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class TagService extends Service {

    private TagDAO tagDAO;

    public TagService() {
        tagDAO = new TagDAO();
    }

    public String create(String title) {
        ArrayList<HashMap<String, Object>> list_info = tagDAO.query(title);//查查标题是否已经存在
        if (list_info.size() != 0) {
            return DUPLICATE;
        }
        if (tagDAO.create(title)) {
            return SUCCESS;
        }
        return FAIL;
    }

    public String query(String del, int page_no) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list = tagDAO.query(del, page_no, 20);
        return Util.transformFromCollection(list);
    }

    public String query(long id) {
        return Util.transformFromCollection(tagDAO.query(id));
    }

    public String update(long id, String title) {
        if (tagDAO.update(id, title)) {
            return SUCCESS;
        }
        return FAIL;
    }

    public String delete(long id) {
        if (tagDAO.delete(id)) {
            return SUCCESS;
        }
        return FAIL;
    }

    public String recover(long id) {
        if (tagDAO.recover(id)) {
            return SUCCESS;
        }
        return FAIL;
    }

}
