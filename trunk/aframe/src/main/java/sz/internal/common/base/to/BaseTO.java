package sz.internal.common.base.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: wanggang
 * Date: 12/4/15
 * Time: 9:31 AM
 */
public class BaseTO implements Serializable {
    private static final Logger logger = Logger.getLogger(BaseTO.class);

    private Integer id;
    @JsonIgnore
    private String create_by;
    @JsonIgnore
    private Timestamp create_dt;
    @JsonIgnore
    private String update_by;
    @JsonIgnore
    private Timestamp update_dt;
    private Integer version;

    private String sortBy;
    private String sortType;

    @JsonIgnore
    private Integer totalSize;
    @JsonIgnore
    private boolean pagination;
    @JsonIgnore
    private int pageSize;
    @JsonIgnore
    private int currentPage;

    @JsonIgnore
    private String[] amendList;

    @JsonIgnore
    private boolean needAudit = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Timestamp getCreate_dt() {
        return create_dt;
    }

    public void setCreate_dt(Timestamp create_dt) {
        this.create_dt = create_dt;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Timestamp getUpdate_dt() {
        return update_dt;
    }

    public void setUpdate_dt(Timestamp update_dt) {
        this.update_dt = update_dt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String convertSortBy(String sortBy) {
        return sortBy;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public boolean isPagination() {
        return pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
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

    public String[] getAmendList() {
        return amendList;
    }

    public void setAmendList(String[] amendList) {
        this.amendList = amendList;
    }

    public boolean isNeedAudit() {
        return needAudit;
    }

    public void setNeedAudit(boolean needAudit) {
        this.needAudit = needAudit;
    }

    @JsonIgnore
    public String[] getPKFields() {
        return new String[]{"id"};
    }

    public void prepareNeedAudit(boolean audit) {
        try {
            setNeedAudit(audit);
            Map map = PropertyUtils.describe(this);
            Set set = map.entrySet();
            for (Object aSet : set) {
                Map.Entry entry = (Map.Entry) aSet;
                Object obj = entry.getValue();
                if (obj instanceof BaseTO) {
                    ((BaseTO) obj).prepareNeedAudit(audit);
                }
                if (obj instanceof List) {
                    List list = (List) obj;
                    for (Object object : list) {
                        if (object instanceof BaseTO) {
                            ((BaseTO) object).prepareNeedAudit(audit);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public int getStartIdx() {
        return (currentPage - 1) * pageSize;
    }

    public int getEndIdx() {
        return currentPage * pageSize - 1;
    }
}
