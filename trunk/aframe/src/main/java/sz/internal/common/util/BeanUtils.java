package sz.internal.common.util;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtils {
    /**
     * Copy the property values from the source bean to the destination bean.
     * <p/>
     * If the include property names are specified (<code>includePropertyNames</code>), only the
     * specified property names will be copied over.
     * <p/>
     * If the property names are not specified (<code>null</code> or empty <code>includePropertyNames</code>),
     * all properties from the source bean will be copied to the destination bean.
     *
     * @param src                  the source bean
     * @param dest                 the destination bean
     * @param includePropertyNames the required properties to be copied only
     */
    public static void copyOnlyIncludeProperties(Object src, Object dest, String[] includePropertyNames) {

        if (src == null || dest == null) {
            throw new IllegalArgumentException("source and destination bean should not be null");
        }
        try {
            if (includePropertyNames != null && includePropertyNames.length > 0) {
                for (String includePropertyName : includePropertyNames) {
                    Object var = PropertyUtils.getProperty(src, includePropertyName);
                    PropertyUtils.setProperty(dest, includePropertyName, var);
                }
            } else {
                org.apache.commons.beanutils.BeanUtils.copyProperties(dest, src);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error copying from source to destination bean", e);
        }
    }

    /**
     * Copy all property values from the source bean to the destination bean.
     * <p/>
     * If the exclude property names are specified, (<code>excludePropertyNames</code>), these properties
     * will not be copied over.
     * <p/>
     * If the exclude property names are not specified (<code>null</code> or empty <code>excludePropertyNames</code>),
     * all properties from the source bean will be copied to the destination bean.
     *
     * @param src                  the source bean
     * @param dest                 the destination bean
     * @param excludePropertyNames the properties to be excluded from the copying
     */
    public static void copyProperties(Object src, Object dest, String[] excludePropertyNames) {
        if (src == null || dest == null) {
            throw new IllegalArgumentException("source and destination bean should not be null");
        }

        try {
            if (excludePropertyNames != null && excludePropertyNames.length > 0) {
                for (String excludePropertyName : excludePropertyNames) {
                    Object valArray = PropertyUtils.getProperty(dest, excludePropertyName);
                    PropertyUtils.setProperty(src, excludePropertyName, valArray);
                }
            }
            PropertyUtils.copyProperties(dest, src);
        } catch (Exception e) {
            throw new RuntimeException("Error copying from source to destination bean", e);
        }
    }

    public static Object cloneObject(Object bean) {
        if (bean == null) {
            return null;
        }
        try {
            Object obj = bean.getClass().newInstance();
            PropertyUtils.copyProperties(obj, bean);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Error clone object", e);
        }
    }
}
