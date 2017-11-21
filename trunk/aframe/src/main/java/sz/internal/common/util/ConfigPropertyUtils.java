package sz.internal.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertyUtils {
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

}
