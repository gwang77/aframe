package sz.internal.common.base.service;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.mapper.BaseMapper;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.base.to.PageTO;
import sz.internal.common.component.auditlog.AuditLog;
import sz.internal.common.component.seqno.service.SeqNoUtil;
import sz.internal.common.exception.BusinessException;
import sz.internal.common.util.BeanUtils;
import sz.internal.common.util.SysConfigPropertyUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * User: wanggang
 * Date: 12/4/15
 * Time: 9:30 AM
 */
public class BaseService {
    private static final Logger logger = Logger.getLogger(BaseService.class);

    private BaseMapper mapper;

    public BaseMapper getMapper() {
        return mapper;
    }

    public void setMapper(BaseMapper mapper) {
        this.mapper = mapper;
    }

    public void insert(BaseTO baseTO) throws Exception {
        checkInputNull(baseTO);
        Date currDate = new Date();
        if (StringUtils.isEmpty(baseTO.getCreate_by())) {
            baseTO.setCreate_by(getCurrUser());
            baseTO.setCreate_dt(new Timestamp(currDate.getTime()));
        }
        //prepare id
        String generateIDIND = SysConfigPropertyUtil.getProperties("db.table.id.generator.seq_service");
        if ("Y".equalsIgnoreCase(generateIDIND) || "true".equalsIgnoreCase(generateIDIND)) {
            baseTO.setId(SeqNoUtil.getNextID(baseTO));
        }
        prepareInsert(baseTO);
        mapper.insert(baseTO);
        //audit log
        if (baseTO.isNeedAudit()) {
            AuditLog.auditCreate(baseTO);
        }
    }

    protected void prepareInsert(BaseTO baseTO) throws Exception {

    }

    public void update(BaseTO baseTO) throws Exception {
        checkInputIDNull(baseTO);
        BaseTO currTO = baseTO;
        BaseTO toTmp = find(baseTO);
        BaseTO toTmpBean = toTmp;
        if (baseTO.isNeedAudit()) {
            toTmpBean = (BaseTO) BeanUtils.cloneObject(toTmp);
        }
        if (baseTO.getAmendList() != null && baseTO.getAmendList().length > 0) {
            currTO = toTmp;
            BeanUtils.copyOnlyIncludeProperties(baseTO, currTO, baseTO.getAmendList());
        } else {
            if (currTO.getCreate_by() == null) {
                currTO.setCreate_by(toTmp.getCreate_by());
                currTO.setCreate_dt(toTmp.getCreate_dt());
            }
        }
        Date currDate = new Date();
        currTO.setUpdate_by(getCurrUser());
        currTO.setUpdate_dt(new Timestamp(currDate.getTime()));

        //audit log
        if (baseTO.isNeedAudit()) {
            AuditLog.auditUpdate(toTmpBean, currTO);
        }
        mapper.update(currTO);
    }

    public void delete(BaseTO baseTO) throws Exception {
        checkInputIDNull(baseTO);
        //audit log
        if (baseTO.isNeedAudit()) {
            BaseTO toTmp = mapper.find(baseTO);
            AuditLog.auditDelete(toTmp);
        }
        mapper.delete(baseTO);
    }

    public List search(BaseTO baseTO) throws Exception {
        return mapper.search(baseTO);
    }

    public PageTO searchPage(BaseTO baseTO, PageTO pageTO) throws Exception {
        checkInputNull(baseTO);
        List list = search(baseTO);
        Integer totalSize = list.size();
        pageTO.setTotalRecords(totalSize);

        if (pageTO.getPageSize() == 0) {
            pageTO.setPageSize(10);
        }
        if (pageTO.getCurrentPage() == 0) {
            pageTO.setCurrentPage(1);
        }
        if (pageTO.getCurrentPage() > pageTO.getTotalPages() && pageTO.getTotalPages() > 0) {
            pageTO.setCurrentPage(pageTO.getTotalPages());
        }
        int startIdx = pageTO.getStartIdx();
        int endIdx = pageTO.getEndIdx();

        if (startIdx > list.size()) {
            startIdx = list.size();
        }
        if (endIdx > list.size() - 1) {
            endIdx = list.size() - 1;
        }
        List resultList = list.subList(startIdx, endIdx + 1);
        pageTO.setList(resultList);
        return pageTO;
    }

    //to use this method, xml should have condition "limit"
    public PageTO searchPageFast(BaseTO baseTO, PageTO pageTO) throws Exception {
        checkInputNull(baseTO);
        if (pageTO.getPageSize() == 0) {
            pageTO.setPageSize(10);
        }
        if (pageTO.getCurrentPage() == 0) {
            pageTO.setCurrentPage(1);
        }
        baseTO.setCurrentPage(pageTO.getCurrentPage());
        baseTO.setPageSize(pageTO.getPageSize());
        List list = search(baseTO);

        Integer totalSize = searchCount(baseTO);
        pageTO.setTotalRecords(totalSize);

        if (pageTO.getCurrentPage() > pageTO.getTotalPages() && pageTO.getTotalPages() > 0) {
            pageTO.setCurrentPage(pageTO.getTotalPages());
        }
        pageTO.setList(list);
        return pageTO;
    }

    public Integer searchCount(BaseTO baseTO) throws Exception {
        checkInputNull(baseTO);
        int totalSize = 0;
        boolean orgPagination = baseTO.isPagination();
        baseTO.setPagination(true);
        List list = mapper.search(baseTO);
        if (list.size() > 0) {
            BaseTO bTO = (BaseTO) list.get(0);
            totalSize = bTO.getTotalSize();
        }
        baseTO.setPagination(orgPagination);
        return totalSize;
    }

    public BaseTO find(BaseTO baseTO) throws Exception {
        checkInputIDNull(baseTO);
        return mapper.find(baseTO);
    }

    //truncate table, used to delete table all data.
    public void truncateTable(String table_name) throws Exception {
        if (StringUtils.isEmpty(table_name)) {
            logger.info("truncateTable: table name is empty.");
            return;
        }
        mapper.truncateTable(table_name);
    }

    public String getCurrUser() {
        try {
            Object userBean = SpringContextHolder.getBean("security_user_bean");
            if (userBean == null) {
                return "SYSTEM";
            }
            String currUser = (String) PropertyUtils.getProperty(userBean, "currUser");
            if (StringUtils.isEmpty(currUser)) {
                return "SYSTEM";
            }
            return currUser;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "SYSTEM";
    }

    protected void checkInputNull(Object obj) throws Exception {
        if (obj == null) {
            throw new BusinessException("err.input.null");
        }
    }

    protected void checkInputIDNull(BaseTO obj) throws Exception {
        checkInputNull(obj);
        String[] pkFields = obj.getPKFields();
        for (String pkField : pkFields) {
            if (PropertyUtils.getProperty(obj, pkField) == null) {
                throw new BusinessException("err.input.id.null");
            }
        }
    }

    public int getTableCount(String table_name) throws Exception {
        int count = mapper.getTableCount(table_name);
        return count;
    }

}
