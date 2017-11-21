package sz.internal.common.base.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * User: wanggang
 * Date: 4/27/16
 * Time: 3:35 PM
 */
@Controller
@ResponseBody
@RequestMapping("/json/common")
public class CommonController extends BaseController {
    private static final String[] PROTECTED_PROP = new String[]{"jdbc", "shiro[a-zA-Z_-]*"};

    private static final String MESSAGE_FILE_NAME = "messages[a-zA-Z_-]*\\.properties";
    private static String[][] MESSAGE_FILE_TYPE_SUFFIX = new String[][]{
            {"en", "_en_US"},
            {"cn", "_zh_CN"}
    };

    private boolean isProtectedProp(String propName) {
        for (String exPropNameReg : PROTECTED_PROP) {
            if (Pattern.matches(exPropNameReg, propName)) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/propJson")
    public Object propJson(String propName) {
        if (StringUtils.isEmpty(propName)) {
            return "";
        }
        if (isProtectedProp(propName)) {
            return "";
        }
        InputStream is = null;
        try {
            Properties properties = new Properties();
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propName + ".properties");
            if (is == null) {
                throw new RuntimeException("can't find the config file:" + propName + "");
            }
            properties.load(new InputStreamReader(is, "UTF-8"));
            return properties;
        } catch (Exception e) {
            System.out.println("failed to read " + propName + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }

    @RequestMapping("/msgJson")
    public Object msgJson() {
        URL url = this.getClass().getClassLoader().getResource("");
        if (url == null) {
            return "";
        }
        File file = new File(url.getFile());
        IOFileFilter filter = new RegexFileFilter(MESSAGE_FILE_NAME);
        Collection<File> list = FileUtils.listFiles(file, filter, null);

        Map messageMap = new HashMap();

        for (File fileTmp : list) {
            String fileName = fileTmp.getName();
            String localType = getMessageTypeByName(fileName);
            Map map = getLocalMessageMap(messageMap, localType);
            map.putAll(loadMessageFile(fileName));
        }
        return messageMap;
    }

    private Map loadMessageFile(String fileName) {
        try {
            Map msgMap = new HashMap();

            Properties properties = new Properties();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new RuntimeException("can't find the config file:" + fileName + "");
            }
            properties.load(new InputStreamReader(is, "UTF-8"));

            Set set = properties.keySet();
            for (Object key : set) {
//                String newKey = String.valueOf(key).replaceAll("\\.", "_");
                String newKey = (String) key;
                String value = properties.getProperty((String) key);
                msgMap.put(newKey, value);
            }
            return msgMap;
        } catch (Exception e) {
            System.out.println("failed to read msg files" + e.getMessage());
        }
        return new HashMap();
    }

    private Map getLocalMessageMap(Map messageMap, String localType) {
        Map map = (Map) messageMap.get(localType);
        if (map == null) {
            Map localMap = new HashMap();
            messageMap.put(localType, localMap);
        }
        return (Map) messageMap.get(localType);
    }

    private String getMessageTypeByName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        fileName = fileName.trim();
        if (!fileName.endsWith(".properties")) {
            return "";
        }
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        for (String[] aSuffix : MESSAGE_FILE_TYPE_SUFFIX) {
            if (fileName.endsWith(aSuffix[1])) {
                return aSuffix[0];
            }
        }
        return "en";
    }

}
