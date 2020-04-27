package com.alfred.healthylife.DAO;

import com.alfred.healthylife.Util.Util;

public class ImageDAO extends DAO {

    private static final String ADMIN_ICON_PATH = "/usr/local/HealthyLife/AdminIcon/";
    private static final String USER_ICON_PATH = "/usr/local/HealthyLife/UserIcon/";
    private static final String TIP_IMAGE_PATH = "/usr/local/HealthyLife/TipImage/";

    public ImageDAO() {
        super();
    }

    /**
     * 创建管理员头像
     *
     * @param name
     * @param imageString
     */
    public void createAdminIcon(String name, String imageString, String suffix) {
        Util.writeImage(imageString, name, ADMIN_ICON_PATH, suffix);
    }

    /**
     * 创建用户头像
     *
     * @param name
     * @param imageString
     */
    public void createUserIcon(String name, String imageString, String suffix) {
        Util.writeImage(imageString, name, USER_ICON_PATH, suffix);
    }

    /**
     * 创建用户头像
     *
     * @param name
     * @param imageString
     */
    public void createTipImage(String name, String imageString, String suffix) {
        Util.writeImage(imageString, name, TIP_IMAGE_PATH, suffix);
    }

    /**
     * 读取管理员头像
     *
     * @param id
     * @param suffix
     * @return
     */
    public String readAdminIcon(long id, String suffix) {
        return Util.readImage(String.valueOf(id), ADMIN_ICON_PATH, suffix);
    }

    /**
     * 读取用户头像
     *
     * @param id
     * @param suffix
     * @return
     */
    public String readUserIcon(long id, String suffix) {
        return Util.readImage(String.valueOf(id), USER_ICON_PATH, suffix);
    }

    /**
     * 读取用户头像
     *
     * @param file_name
     * @param suffix
     * @return
     */
    public String readTipImage(String file_name, String suffix) {
        return Util.readImage(file_name, TIP_IMAGE_PATH, suffix);
    }


}
