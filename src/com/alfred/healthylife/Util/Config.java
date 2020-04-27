package com.alfred.healthylife.Util;

import java.util.ArrayList;

public class Config {

    public static final int ALPHA_NOVEL = 1;
    public static final int EN_BOOK = 2;

    private static final String remotePassword = "CXYcxy11";
    private static final String localPassword = "cxycxy11";

    public Config () {

    }

    private static int getCurrentSite() {
        return ALPHA_NOVEL;
    }

    /**
     * 是否测试
     * @return
     */
    private static boolean isTest() {
        return true;
    }

    static String getDatabaseName() {
        if (getCurrentSite() == ALPHA_NOVEL) {
            return "HealthyLife";
        }else {
            return "HealthyLife";
        }
    }

    static String getDatabasePassword() {
        if (isTest()) {
            return localPassword;
        }else {
            return remotePassword;
        }
    }

}
