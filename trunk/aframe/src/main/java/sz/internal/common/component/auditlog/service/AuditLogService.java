package sz.internal.common.component.auditlog.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import sz.internal.common.base.to.BaseTO;
import sz.internal.common.base.to.PageTO;
import sz.internal.common.component.auditlog.mapper.AuditLogMapper;
import sz.internal.common.component.auditlog.to.AuditLogTO;
import sz.internal.common.component.seqno.service.SeqNoUtil;
import sz.internal.common.exception.BusinessException;
import sz.internal.common.util.SysConfigPropertyUtil;

/**
 * 
 * @author yangdan
 *
 */
@Component
public class AuditLogService {
	
	@Resource(name = "sz.internal.common.component.auditlog.mapper.AuditLogMapper")
	private AuditLogMapper mapper;

	public AuditLogMapper getMapper() {
		return mapper;
	}

	public void setMapper(AuditLogMapper mapper) {
		this.mapper = mapper;
	}

	public void insert(AuditLogTO auditlogTO) throws Exception {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		auditlogTO.setAudit_date(date);
		auditlogTO.setUser_name(getCurrUser());
		String generateIDIND = SysConfigPropertyUtil.getProperties("db.table.id.generator.seq_service");
		if ("Y".equalsIgnoreCase(generateIDIND) || "T".equalsIgnoreCase(generateIDIND) || "true".equalsIgnoreCase(generateIDIND)) {
			auditlogTO.setId(SeqNoUtil.getNextIDByKeyName("tbl_comm_audit_log"));
		}
		mapper.insert(auditlogTO);
	}

	public List search(AuditLogTO auditlogTO) throws Exception {
		return mapper.search(auditlogTO);

	}

	public PageTO searchPage(AuditLogTO auditlogTO, PageTO pageTO) throws Exception {
		checkInputNull(auditlogTO);
		List list = search(auditlogTO);
		Integer totalSize = list.size();
		pageTO.setTotalRecords(totalSize);

		if (pageTO.getCurrentPage() == 0) {
			pageTO.setCurrentPage(1);
		}
		if (pageTO.getCurrentPage() > pageTO.getTotalPages() && pageTO.getTotalPages() > 0) {
			pageTO.setCurrentPage(pageTO.getTotalPages());
		}
		if (pageTO.getPageSize() == 0) {
			pageTO.setPageSize(10);
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

	public Integer searchCount(AuditLogTO auditlogTO) throws Exception {
		checkInputNull(auditlogTO);
		int totalSize = 0;
		boolean orgPagination = auditlogTO.isPagination();
		auditlogTO.setPagination(true);
		List list = mapper.search(auditlogTO);
		if (list.size() > 0) {
			BaseTO bTO = (BaseTO) list.get(0);
			totalSize = bTO.getTotalSize();
		}
		auditlogTO.setPagination(orgPagination);
		return totalSize;
	}

	public AuditLogTO find(AuditLogTO auditlogTO) throws Exception {
		checkInputIDNull(auditlogTO);
		return mapper.find(auditlogTO);
	}

	public String getCurrUser() {
		return "SYSTEM";
	}

	protected void checkInputNull(Object obj) throws Exception {
		if (obj == null) {
			throw new BusinessException("err.input.null");
		}
	}

	protected void checkInputIDNull(AuditLogTO obj) throws Exception {
		checkInputNull(obj);
		String[] pkFields = obj.getPKFields();
		for (String pkField : pkFields) {
			if (PropertyUtils.getProperty(obj, pkField) == null) {
				throw new BusinessException("err.input.id.null");
			}
		}
	}
}
