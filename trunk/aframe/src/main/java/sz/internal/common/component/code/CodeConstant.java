package sz.internal.common.component.code;

/**
 * User: wanggang
 * Date: 1/11/16
 * Time: 2:12 PM
 */
public interface CodeConstant {
    //Code Type
    public static final String CODE_TYPE_YES_NO = "comm_yes_no";
    public static final String CODE_TYPE_TRUE_FALSE = "comm_true_false";

    public static final String CODE_TYPE_SEX = "comm_sex";

    public static final String CODE_TYPE_STATUS = "comm_status";
    public static final String CODE_TYPE_LOCAL = "comm_locale";

    public static final String CODE_TYPE_SYSTEM_CONFIG = "comm_sys_config";

    //Code Int
    //yes_no
    public static final String YES_NO_YES = "Y";
    public static final String YES_NO_NO = "N";

    //true_false
    public static final String TRUE_FALSE_TRUE = "T";
    public static final String TRUE_FALSE_FALSE = "F";

    //sex
    public static final String SEX_MALE= "M";
    public static final String SEX_FEMALE = "F";

    //status
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_INACTIVE = "I";

    //local
    public static final String LOCALE_EN = "en";
    public static final String LOCALE_ZH = "zh";

    //sys_config
    public static final String SYSTEM_CONFIG_SYSTEM_VERSION = "sys_version";
    public static final String SYSTEM_CONFIG_SYSTEM_CONFIG_CLASS = "sys_config_class";
    public static final String SYSTEM_CONFIG_AUDIT_LOG = "sys_audit_log";

    public static final String SYSTEM_CONFIG_MAIL_SMTP_HOST = "mail_smtp_host";
    public static final String SYSTEM_CONFIG_MAIL_SMTP_USERNAME = "mail_smtp_username";
    public static final String SYSTEM_CONFIG_MAIL_SMTP_PASSWORD = "mail_smtp_pwd";
    public static final String SYSTEM_CONFIG_MAIL_SENDER_NAME = "mail_sender_name";
    public static final String SYSTEM_CONFIG_MAIL_SENDER_ADDR = "mail_sender_addr";

}
