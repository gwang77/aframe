package sz.internal.common.base.to;

import java.io.Serializable;
import java.util.List;

/**
 * User: wanggang
 * Date: 12/21/15
 * Time: 1:50 PM
 */
public class PageTO implements Serializable {
    //Page related info
    private int totalRecords;
    private int pageSize;
    private int currentPage;
    private List list; // Result List.

    public PageTO() {
      
    }

    public PageTO(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getStartIdx() {
        return (currentPage - 1) * pageSize;
    }

    public int getEndIdx() {
        return currentPage * pageSize - 1;
    }

    public int getTotalPages() {
        if (pageSize == 0) {
            return totalRecords == 0 ? 0 : 1;
        }
        return (totalRecords % pageSize == 0) ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
    }
}
