package common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConfigUtils {
    public static final String CONFIG_PATH = "config.properties";

    public static Properties properties = null;

    static {
        try {
            properties = new Properties();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PATH);
            if (is == null)
                throw new RuntimeException("can't find the config file:" + CONFIG_PATH + "");
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("failed to read " + CONFIG_PATH + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String name) {
        return properties.getProperty(name);
    }

    public static String getProperties(String name, String defaultValue) {
        String result = properties.getProperty(name);
        if (result == null) {
            return defaultValue;
        } else {
            return result;
        }
    }

    public static Map<String, String> getPropertiesWithPrefix(String prefix) {
        Map<String, String> map = new HashMap<String, String>();
        Set set = properties.keySet();
        for (Object aSet : set) {
            String key = (String) aSet;
            if (key.startsWith(prefix)) {
                String keyReal = key.replaceFirst(prefix, "");
                map.put(keyReal, properties.getProperty(key));
            }
        }
        return map;
    }

}
