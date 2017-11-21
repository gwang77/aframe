package sz.internal.acm.util;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import sz.internal.common.SpringContextHolder;

public class ACMSecurityUtil {
    private static final Logger logger = Logger.getLogger(ACMSecurityUtil.class);

    public static String generateNewSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    public static String encryptPassword(String password, String credentialsSalt) {
        HashedCredentialsMatcher credentialsMatcher;
        try {
            credentialsMatcher = SpringContextHolder.getBean("credentialsMatcher");
        } catch (Exception e) {
            logger.warn("encrypt password error! no credentials matcher", e);
            return password;
        }
        if (credentialsMatcher == null) {
            return password;
        }
        String algorithmName = credentialsMatcher.getHashAlgorithmName();
        int hashIterations = credentialsMatcher.getHashIterations();
        SimpleHash hash = new SimpleHash(algorithmName, password, credentialsSalt, hashIterations);
        return hash.toHex();
    }
}
