package sz.internal.acm;

import org.apache.log4j.Logger;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.springframework.util.StringUtils;
import sz.internal.acm.to.UserClientTO;
import sz.internal.common.SpringContextHolder;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LDAPUserUtil {
    private static final Logger logger = Logger.getLogger(LDAPUserUtil.class);

    public static UserClientTO getUserInfoFromLdap(String principal, Object credentials) throws Exception {
        LdapContextFactory contextFactory = SpringContextHolder.getBean("ldapContextFactory");
        LdapContext ctx = null;
        UserClientTO userTO = null;
        try {
            ctx = contextFactory.getLdapContext(principal, credentials);
            userTO = getLdapUserInfo(ctx, principal);
        } finally {
            LdapUtils.closeContext(ctx);
        }
        return userTO;
    }

    public static UserClientTO getLdapUserInfo(LdapContext ctx, String userName) {
        List<UserClientTO> list = retrieveUserInfo(ctx, "mail=" + userName);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public static List<UserClientTO> retrieveAllUserInfo(LdapContext ctx) {
        return retrieveUserInfo(ctx, "objectClass=*");
    }

    private static List<UserClientTO> retrieveUserInfo(LdapContext ctx, String searchFilter) {
        List<UserClientTO> resultList = new ArrayList<UserClientTO>();
        // Create the search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String searchBase = "DC=ncs,DC=corp,DC=int-ads";

        String returnedAtts[] = {"url", "whenChanged", "employeeID", "name",
                "userPrincipalName", "physicalDeliveryOfficeName",
                "departmentNumber", "telephoneNumber", "homePhone", "mobile",
                "department", "sAMAccountName", "mail"}; //return attribute

        searchCtls.setReturningAttributes(returnedAtts);

        try {
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            if (answer == null) {
                logger.info("answer is null");
                return resultList;
            }
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                logger.info("name=" + sr.getName());
                Attributes attrs = sr.getAttributes();
                if (attrs != null) {
                    UserClientTO userTO = prepareUserTO(attrs);
                    if (userTO != null) {
                        resultList.add(userTO);
                    }
                }
            }
            logger.info("Number: " + resultList.size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }

    private static UserClientTO prepareUserTO(Attributes attributes) throws Exception {
        if (attributes == null) {
            return null;
        }
        UserClientTO userTO = new UserClientTO();

        String email = getAttributeValue(attributes.get("mail"));
        String accountName = getAttributeValue(attributes.get("sAMAccountName"));
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(accountName)) {
            return null;
        }
        userTO.setUsername(email);
        userTO.setEmail(email);
        userTO.setRealname(getAttributeValue(attributes.get("name")));
        userTO.setStaff_id(getAttributeValue(attributes.get("employeeID")));
        userTO.setTel(getAttributeValue(attributes.get("telephoneNumber")));

        userTO.setUser_type(ACMConstants.USER_TYPE_LDAP);
        userTO.setLocked("N");
        return userTO;
    }

    private static String getAttributeValue(Attribute attr) throws Exception {
        Collection<String> attrVal = LdapUtils.getAllAttributeValues(attr);
        Object[] attrValArr = attrVal.toArray();
        if (attrValArr.length > 0) {
            return String.valueOf(attrValArr[0]);
        }
        return "";
    }

}
