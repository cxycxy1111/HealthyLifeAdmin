package com.alfred.healthylife.Service;

public class Service {

    protected static final String prefix = "{\"status\":\"";
    protected static final String id_prefix = "{\"id\":";
    protected static final String datasuffix = "}";
    protected static final String dataprefix = "{\"data\":";
    protected static final String suffix = "\"}";
    static final String NO_SUCH_RECORD = prefix + "no_such_record" + suffix;//查无记录
    static final String LOCKED = prefix + "locked" + suffix;//已锁定
    static final String FAIL =prefix +  "fail" + suffix;//失败
    static final String PARTLY_FAIL =prefix +  "partly_fail" + suffix;//部分失败
    static final String SUCCESS = prefix + "success" + suffix;//成功
    static final String ILLEGAL = prefix + "illegal" + suffix;//不合法，含有非法字符等
    static final String TOO_LONG = prefix + "too_long" + suffix;//太长
    static final String TOO_SHORT = prefix + "too_short" + suffix;//太短
    static final String DUPLICATE = prefix + "duplicate" + suffix;//与已有记录重复
    static final String NOT_MATCH = prefix + "not_match" + suffix;//不匹配
    static final String QRY_RESULT_EMPTY = prefix + "empty" + suffix;//没有符合条件的记录
    static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;//session过期
    static final String AUTHORIZE_FAIL=prefix + "authorize_fail" + suffix;

    public Service() {

    }

}
