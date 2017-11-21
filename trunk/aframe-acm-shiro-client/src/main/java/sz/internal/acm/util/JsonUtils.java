package sz.internal.acm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

/**
 * User: wanggang
 * Date: 1/7/16
 * Time: 11:11 AM
 */
public class JsonUtils {
    private static final Logger logger = Logger.getLogger(JsonUtils.class);

    public static Object readValue(String jsonStr, Class clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String writeValue(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "Json Write Value error:" + e.getMessage();
        }
    }

}
