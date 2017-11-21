package sz.internal.common;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.util.ConfigPropertyUtils;
import sz.internal.common.util.SysConfigPropertyUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SystemConfig {
    private static final Logger logger = Logger.getLogger(SystemConfig.class);
    private static String[][] SYS_CONFIG_LABEL_DEFINE = new String[][]{
            {CodeConstant.SYSTEM_CONFIG_AUDIT_LOG, "Allow Audit Log", CodeConstant.CODE_TYPE_YES_NO},
            {CodeConstant.SYSTEM_CONFIG_MAIL_SMTP_HOST, "SMTP Host", ""},
            {CodeConstant.SYSTEM_CONFIG_MAIL_SMTP_USERNAME, "SMTP User Name", ""},
            {CodeConstant.SYSTEM_CONFIG_MAIL_SMTP_PASSWORD, "SMTP Password", ""},
            {CodeConstant.SYSTEM_CONFIG_MAIL_SENDER_NAME, "Default Sender Name", ""},
            {CodeConstant.SYSTEM_CONFIG_MAIL_SENDER_ADDR, "Default Sender EMail", ""},
            {CodeConstant.SYSTEM_CONFIG_SYSTEM_CONFIG_CLASS, "System Config Classes", ""},
            {CodeConstant.SYSTEM_CONFIG_SYSTEM_VERSION, "System Version", ""},
    };

    public static String[][] getSysConfigDefine() {
        return SYS_CONFIG_LABEL_DEFINE;
    }

    public static String[][] getAllSysConfigDefine() {
        Set configLabelSet = new HashSet();
        Collections.addAll(configLabelSet, getSysConfigDefine());

        String sysConfigDefineClasses = getSystemConfigValue(CodeConstant.SYSTEM_CONFIG_SYSTEM_CONFIG_CLASS);
        String[] sysConfigDefineClassArr = sysConfigDefineClasses.split(",");

        try {
            for (String defineClass : sysConfigDefineClassArr) {
                defineClass = defineClass.trim();
                if (StringUtils.isEmpty(defineClass)) {
                    continue;
                }
                Class clazz = Class.forName(defineClass);
                Method method = clazz.getMethod("getSysConfigDefine");
                String[][] defines = (String[][]) method.invoke(null);
                Collections.addAll(configLabelSet, defines);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        String[][] sysConfigArr = new String[][]{};
        return (String[][]) configLabelSet.toArray(sysConfigArr);
    }

    public static String getSystemConfigValue(String systemConfigCodeID) {
        String val = ConfigPropertyUtils.getProperties(systemConfigCodeID);
        if (!StringUtils.isEmpty(val)) {
            return val;
        }
        val = SysConfigPropertyUtil.getProperties(systemConfigCodeID);
        if (!StringUtils.isEmpty(val)) {
            return val;
        }
        try {
            return CodeMgr.getCodeDesc(CodeConstant.CODE_TYPE_SYSTEM_CONFIG, systemConfigCodeID);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isAllowAuditLog() {
        return CodeConstant.YES_NO_YES.equals(getSystemConfigValue(CodeConstant.SYSTEM_CONFIG_AUDIT_LOG));
    }
}