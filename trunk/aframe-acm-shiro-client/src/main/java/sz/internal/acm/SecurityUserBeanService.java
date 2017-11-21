package sz.internal.acm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("security_user_bean")
public class SecurityUserBeanService {
    public String getCurrUser() {
        return UserUtils.getCurrUser();
    }

    public Boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject.isPermitted(permission);
    }

    public Map getPermissions(String... permissions) {
        Subject subject = SecurityUtils.getSubject();
        Map<String, String> result = new HashMap<String, String>();
        for (String permission : permissions) {
            result.put(permission, String.valueOf(subject.isPermitted(permission)));
        }
        return result;
    }

}
