package sz.internal.acm.shiro;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * User: wanggang
 * Date: 4/8/16
 * Time: 2:14 PM
 */
public class ChainDefinitionSectionPropertiesMetaSource implements FactoryBean<Ini.Section> {
    private static final Logger logger = Logger.getLogger(ChainDefinitionSectionPropertiesMetaSource.class);

    static String SHIRO_FILE_NAME = "shiro[a-zA-Z_-]*\\.properties";

    private String filterChainDefinitions;

    public String getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Ini.Section getObject() throws Exception {
        //Get all Resource
        List<Resource> list = getAllResources();

        Ini ini = new Ini();
        //add default url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

        //loop Resource url,add to section.
        //key is URL, value is access condition.
        for (Resource resource : list) {
            if (!StringUtils.isEmpty(resource.getKey()) && !StringUtils.isEmpty(resource.getValue())) {
                section.put(resource.getKey(), resource.getValue());
            }
        }
        return section;
    }

    public Class<?> getObjectType() {
        return this.getClass();
    }

    public boolean isSingleton() {
        return false;
    }

    public List<Resource> getAllResources() throws Exception {
        URL url = this.getClass().getClassLoader().getResource("");
        if (url == null) {
            return new ArrayList<Resource>();
        }
        File file = new File(url.getFile());
        IOFileFilter filter = new RegexFileFilter(SHIRO_FILE_NAME);
        Collection<File> fileList = FileUtils.listFiles(file, filter, null);

        List<Resource> anonResourceList = new ArrayList<Resource>();
        List<Resource> otherResourceList = new ArrayList<Resource>();
        List<Resource> filterAllResourceList = new ArrayList<Resource>();
        for (File fileTmp : fileList) {
            List<Resource> resourceList = handlePropertiesFile(fileTmp.getName());
            assignFilter(resourceList, anonResourceList, otherResourceList, filterAllResourceList);
        }
        List<Resource> allResourceList = new ArrayList<Resource>();
        allResourceList.addAll(anonResourceList);
        allResourceList.addAll(otherResourceList);
        allResourceList.addAll(filterAllResourceList);
        return allResourceList;
    }

    private List<Resource> handlePropertiesFile(String fileName) {
        List<Resource> propList = new ArrayList<Resource>();
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                return new ArrayList<Resource>();
            }
            properties.load(is);
            Set set = properties.keySet();
            for (Object aSet : set) {
                String key = (String) aSet;
                if (key.startsWith("filterChain.")) {
                    String keyReal = key.replaceFirst("filterChain.", "");
                    propList.add(new Resource(keyReal, properties.getProperty(key)));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return propList;
    }

    private void assignFilter(List<Resource> resourceList, List<Resource> anonResourceList, List<Resource> otherResourceList, List<Resource> filterAllResourceList) {
        for (Resource resource : resourceList) {
            if ("anon".equals(resource.getValue())) {
                anonResourceList.add(resource);
            } else if ("/**".equals(resource.getKey())) {
                filterAllResourceList.add(resource);
            } else {
                otherResourceList.add(resource);
            }
        }
    }
}
