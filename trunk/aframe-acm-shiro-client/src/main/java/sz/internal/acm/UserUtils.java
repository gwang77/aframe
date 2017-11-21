package sz.internal.acm;

import org.apache.shiro.SecurityUtils;
import sz.internal.acm.util.SysConfigPropertyUtils;

/**
 * User: wanggang
 * Date: 2/26/16
 * Time: 12:52 PM
 */
public class UserUtils {
    public static String getCurrUser() {
        if (SecurityUtils.getSubject() == null) {
            return "SYSTEM";
        }
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        return userName == null ? "SYSTEM" : userName;
    }

    public static String getCurrApp_id() {
        String app_id = SysConfigPropertyUtils.getProperties("app.id");
        return app_id == null ? "" : app_id;
    }

}
