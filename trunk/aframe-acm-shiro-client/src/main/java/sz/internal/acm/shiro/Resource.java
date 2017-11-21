package sz.internal.acm.shiro;

/**
 * User: wanggang
 * Date: 4/8/16
 * Time: 4:13 PM
 */
public class Resource {
    private String key;
    private String value;

    public Resource(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
