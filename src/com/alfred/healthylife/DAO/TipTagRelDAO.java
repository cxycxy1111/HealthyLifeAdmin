package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TipTagRelDAO extends DAO {

    private SQLHelper helper;

    public TipTagRelDAO() {
        helper = new SQLHelper();
    }

    public boolean create(long tip_id, long tag_id) {
        String sql = "INSERT INTO tip_tag (tip_id,tag_id) VALUES (" + tip_id + "," + tag_id + ")";
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(long tip_id, long tag_id) {
        String sql = "UPDATE tip_tag SET del=1 " +
                "WHERE tip_id=" + tip_id + " AND tag_id=" + tag_id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean recover(long tip_id, long tag_id) {
        String sql = "UPDATE tip_tag SET del=0 " +
                "WHERE tip_id=" + tip_id + " AND tag_id=" + tag_id;
        try {
            return helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<HashMap<String, Object>> query(long tip_id, long tag_id) {
        String sql = "SELECT * FROM tip_tag " +
                "WHERE tip_id=" + tip_id + " AND tag_id=" + tag_id;
        return helper.query(sql);
    }

    public ArrayList<HashMap<String, Object>> simpleQueryByTip(long tip_id) {
        String sql = "SELECT * FROM tip_tag a " +
                "WHERE del=0 AND tip_id=" + tip_id;
        return helper.query(sql);
    }

    public ArrayList<HashMap<String, Object>> simpleQueryByTag(long tag_id) {
        String sql = "SELECT * FROM tip_tag a " +
                "WHERE del=0 AND tag_id=" + tag_id;
        return helper.query(sql);
    }

    public ArrayList<HashMap<String, Object>> simpleQueryByTipWithDeleted(long tip_id) {
        String sql = "SELECT * FROM tip_tag a " +
                "WHERE tip_id=" + tip_id;
        return helper.query(sql);
    }

}
