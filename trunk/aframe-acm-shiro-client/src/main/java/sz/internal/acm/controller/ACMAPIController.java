package sz.internal.acm.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/acm")
public class ACMAPIController {
    private static final Logger logger = Logger.getLogger(ACMAPIController.class);

    @RequestMapping(value = "/hasPermission/{permission}")
    public Object hasPermission(@PathVariable String permission) {
        Subject subject = SecurityUtils.getSubject();
        Map<String, String> map = new HashMap<String, String>();
        map.put(permission, String.valueOf(subject.isPermitted(permission)));
        return map;
    }

}
