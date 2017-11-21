package sz.internal.common.base.to;

import org.springframework.util.StringUtils;

import java.io.Serializable;

public class BaseSearchTO implements Serializable {
    private int size;
    private int page;
    private int count;
    private int max;
    private String sort;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public PageTO getPageTO() {
        PageTO pageTO = new PageTO();
        pageTO.setPageSize(getSize());
        pageTO.setCurrentPage(getPage());
        pageTO.setPageSize(getSize());
        return pageTO;
    }

    public String getSortBy() {
        if (StringUtils.isEmpty(sort)) {
            return "";
        }
        String[] strArr = sort.split(",");
        return strArr[0];
    }

    public String getSortType() {
        if (StringUtils.isEmpty(sort)) {
            return "";
        }
        String[] strArr = sort.split(",");
        if (strArr.length > 1) {
            return strArr[1];
        }
        return "";
    }
}
