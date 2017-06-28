package com.wosai.bright.common;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
public class Constants {
    public static final int YES = 1;
    public static final int NO = 0;

    public static final int JSON_RESULT_SUCCESS = 0;
    public static final int JSON_RESULT_FAIL = 1;
    public static final int JSON_RESULT_TOKEN_FAIL = 2;

    /**维修状态，0正常，1待维修，2维修中*/
    public static final int DEVICE_REPAIR_STATUS_NORMAL = 0;
    public static final int DEVICE_REPAIR_STATUS_NEED_REPAIR = 1;
    public static final int DEVICE_REPAIR_STATUS_REPAIRING = 2;

    /** 图层类型：1文字,2数据,3图标 */
    public static final int LAYER_TYPE_TEXT = 1;
    public static final int LAYER_TYPE_DATA = 2;
    public static final int LAYER_TYPE_ICON = 3;

    /** 设备报警状态，0正常，1报警，2误报，3离线 */
    public static final Short DEVICE_WARNING_STATUS_NORMAL = 0;
    public static final Short DEVICE_WARNING_STATUS_WARNING = 1;
    public static final Short DEVICE_WARNING_STATUS_MISINFORMATION = 2;
    public static final Short DEVICE_WARNING_STATUS_OFFLINE = 3;

    /** 报修工单状态，0等待，1开始处理，2处理完成，3关闭 */
    public static final Short REPAIR_WORK_STATUS_WAIT = 1;
    public static final Short REPAIR_WORK_STATUS_BEGIN = 2;
    public static final Short REPAIR_WORK_STATUS_SUCCESS = 3;
    public static final Short REPAIR_WORK_STATUS_CLASE = 4;

    /** web用户类型：1物管 2系统 3实施 */
    public static final Short SYS_USER_TYPE_BASE = 1;
    public static final Short SYS_USER_TYPE_MANAGE = 2;
    public static final Short SYS_USER_TYPE_ARTISAN = 3;
    /** app用户类型：11物管 12系统 13工程 */
    public static final Short SYS_USER_TYPE_BASE_APP = 11;
    public static final Short SYS_USER_TYPE_MANAGE_APP = 12;
    public static final Short SYS_USER_TYPE_ARTISAN_APP = 13;
}
