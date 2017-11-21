package sz.internal.acm.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/acm")
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/ajaxLogin")
    public Object ajaxLogin(@RequestParam String username, @RequestParam String password, @RequestParam boolean rememberMe) {
        Subject currentUser = SecurityUtils.getSubject();
        LoginResult loginResult = new LoginResult();
        if (currentUser.isAuthenticated()) {
            String session_id = (String) currentUser.getSession().getId();
            logger.debug("is Authenticated!" + "sessionID:" + session_id);
            loginResult.setSession_id(session_id);
            loginResult.setStatus(LoginResult.LOGIN_STATUS_SUCCESS);
            return loginResult;
        } else {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe);
            try {
                currentUser.login(token);
                String session_id = (String) currentUser.getSession().getId();
                logger.debug("login success!" + "sessionID:" + session_id);
                loginResult.setSession_id(session_id);
                loginResult.setStatus(LoginResult.LOGIN_STATUS_SUCCESS);
                return loginResult;
            } catch (UnknownAccountException ex) {
                logger.error("inaccurate username or password");
                loginResult.setStatus(LoginResult.LOGIN_STATUS_FAILED);
                loginResult.setErrMsg("inaccurate username or password");
            } catch (IncorrectCredentialsException ex) {
                logger.debug("inaccurate username or password");
                loginResult.setStatus(LoginResult.LOGIN_STATUS_FAILED);
                loginResult.setErrMsg("inaccurate username or password");
            } catch (Exception ex) {
                logger.debug("login failed");
                loginResult.setStatus(LoginResult.LOGIN_STATUS_FAILED);
                loginResult.setErrMsg("Login failed");
            }
            return loginResult;
        }

    }

    @RequestMapping(value = "/ajaxLogout")
    public Object ajaxLogout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "";
    }

    @RequestMapping(value = "/loginname")
    public Object getLoginName() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            String loginName = (String) subject.getPrincipal();
            if (loginName != null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("loginname", loginName);
                return map;
            }
        }
        return "";
    }

    @RequestMapping(value = "/wxauth")
    public Object wxauth() {
        logger.info("WX Auth.");
        return "";
    }

    @RequestMapping(value = "/qqauth")
    public Object qqauth() {
        logger.info("QQ Auth.");
        return "";
    }
}
