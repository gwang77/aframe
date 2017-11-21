package sz.internal.common.spring;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: wanggang
 * Date: 4/8/16
 * Time: 3:06 PM
 */
public class MessageSourceBaseNamesDefine implements FactoryBean<String[]> {
    static String MESSAGE_FILE_NAME = "messages[a-zA-Z_-]*\\.properties";
    static String[] MESSAGE_FILE_SUFFIX = new String[]{"_zh_CN", "_en_US"};

    @Override
    public String[] getObject() throws Exception {
        return getAllMessageFiles();
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public String[] getAllMessageFiles() throws Exception {
        URL url = this.getClass().getClassLoader().getResource("");
        if (url == null) {
            return new String[0];
        }
        File file = new File(url.getFile());
        IOFileFilter filter = new RegexFileFilter(MESSAGE_FILE_NAME);
        Collection<File> list = FileUtils.listFiles(file, filter, null);

        List<String> baseNameList = new ArrayList<String>();
        for (File fileTmp : list) {
            String baseName = getMessageBaseName(fileTmp.getName());
            if (!StringUtils.isEmpty(baseName) && !baseNameList.contains(baseName)) {
                baseNameList.add(baseName);
            }
        }
        String[] baseNameArr = new String[baseNameList.size()];
        baseNameList.toArray(baseNameArr);
        return baseNameArr;
    }

    private String getMessageBaseName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        fileName = fileName.trim();
        if (!fileName.endsWith(".properties")) {
            return "";
        }
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        for (String aSuffix : MESSAGE_FILE_SUFFIX) {
            fileName = fileName.replaceAll(aSuffix, "");
        }
        return fileName;
    }
}
